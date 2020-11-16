package com.example.graveapp;

import androidx.fragment.app.FragmentActivity;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.graveapp.api.ApiClient;
import com.example.graveapp.api.GraphModel;
import com.example.graveapp.api.GraveyardModel;
import com.example.graveapp.api.MapsAPI;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import retrofit2.Call;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GraphModel graphModel;
    private Map<String, GraphModel.Vertex> vertexMap;
    public static GraveyardModel graveyardModel;
    private final double patrickLat = 45.9840329;
    private final double patrickLong = -112.5408545;
    private String block = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().containsKey("block")) {
            this.block = (String) getIntent().getExtras().get("block");
        }

        final ProgressDialog pd = new ProgressDialog(this.getBaseContext());
        pd.setMessage("Loading...");

        MapsAPI service = ApiClient.getRetrofitInstance(this.getBaseContext()).create(MapsAPI.class);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        Button button = (Button) findViewById(R.id.button_search);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        Button buttonClear = (Button) findViewById(R.id.button_clear);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
            }
        });

        Button buttonReset = (Button) findViewById(R.id.button_reset);
        buttonReset.setId(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng patrickCemetary = new LatLng(patrickLat, patrickLong);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(patrickCemetary, 18.0f));
            }
        });

        Call<GraveyardModel> graveCall = service.getGraveyard();

        try {
            Response<GraveyardModel> res = graveCall.execute();
            this.graveyardModel = res.body();
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "Could not fetch graveyard!", Toast.LENGTH_LONG);
        }

        Call<GraphModel> edgeCall = service.getGraph();

        try {
            Response<GraphModel> res = edgeCall.execute();
            this.graphModel = res.body();
            this.vertexMap = new HashMap();
            for (GraphModel.Vertex v : this.graphModel.getVertices()) {
                this.vertexMap.put(v.getLabel(), v);
            }
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "Could not fetch edges!", Toast.LENGTH_LONG);
        }

        pd.dismiss();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng patrickCemetary = new LatLng(this.patrickLat, this.patrickLong);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(patrickCemetary, 18.0f));

        if (this.block != null) {
            drawPathToBlock(this.block);
        }
    }

    private void drawPathToBlock(String blockID) {
        final String startingID = "p1";
        Stack<String> stack = new Stack();
        HashSet<String> visisted = new HashSet<String>();
        Map<String, String> path = new HashMap<String, String>();
        stack.push(startingID);
        while (!stack.empty()) {
            String top = stack.peek();
            stack.pop();
            visisted.add(top);
            List<String> neighbors = getEdgesFromVertex(top);
            for (String n : neighbors) {
                if (!visisted.contains(n)) {
                    stack.push(n);
                    path.put(n, top);
                }
            }
        }
        List<String> actualPath = new ArrayList<String>();
        String current = blockID;
        while (path.containsKey(current)) {
            actualPath.add(current);
            current = path.get(current);
        }
        actualPath.add(startingID);

        for (int i = 0; i < actualPath.size() - 1; i++) {
            GraphModel.Vertex firstTex = this.vertexMap.get(actualPath.get(i));
            // LLOL
            GraphModel.Vertex sexTex = this.vertexMap.get(actualPath.get(i+1));
            drawLine(mMap, firstTex, sexTex);
        }
        GraphModel.Vertex solution = this.vertexMap.get(blockID);
        LatLng spot = new LatLng(solution.getLatitude(), solution.getLongitude());
        mMap.addMarker(new MarkerOptions().position(spot).title("Your block"));
    }

    private List<String> getEdgesFromVertex(String vertexID) {
        List<String> neighbors = new ArrayList<String>();
        for (GraphModel.Edge e : this.graphModel.getEdges()) {
            if (e.getSource().equals(vertexID) && !e.getSource().equals(e.getDestination())) {
                neighbors.add(e.getDestination());
            }
        }
        return neighbors;
    }

    private void drawLine(GoogleMap googleMap, GraphModel.Vertex v1, GraphModel.Vertex v2) {
        Polyline line = googleMap.addPolyline(new PolylineOptions()
                .add(new LatLng(v1.getLatitude(), v1.getLongitude()), new LatLng(v2.getLatitude(), v2.getLongitude()))
                .width(2)
                .color(Color.RED));
    }
}