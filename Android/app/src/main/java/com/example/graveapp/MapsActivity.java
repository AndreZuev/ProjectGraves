package com.example.graveapp;

import androidx.fragment.app.FragmentActivity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.example.graveapp.api.ApiClient;
import com.example.graveapp.api.EdgesModel;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private EdgesModel edgesModel;
    private GraveyardModel graveyardModel;
    private final double patrickLat = 45.9840329;
    private final double patrickLong = -112.5408545;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        final ProgressDialog pd = new ProgressDialog(this.getBaseContext());
        pd.setMessage("Loading...");

        MapsAPI service = ApiClient.getRetrofitInstance(this.getBaseContext()).create(MapsAPI.class);

        Call<EdgesModel> edgeCall = service.getEdges();

        try {
            Response<EdgesModel> res = edgeCall.execute();
            this.edgesModel = res.body();
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "Could not fetch edges!", Toast.LENGTH_LONG);
        }

        Call<GraveyardModel> graveCall = service.getGraveyard();

        try {
            Response<GraveyardModel> res = graveCall.execute();
            this.graveyardModel = res.body();
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "Could not fetch graveyard!", Toast.LENGTH_LONG);
        }

        pd.dismiss();
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
        // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(patrickCemetary));
    }

    private void drawLine(GoogleMap googleMap, double lat1, double long1, double lat2, double long2) {
        Polyline line = googleMap.addPolyline(new PolylineOptions()
                .add(new LatLng(lat1, long1), new LatLng(lat2, long2))
                .width(1)
                .color(Color.RED));
    }
}