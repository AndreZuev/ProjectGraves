package com.example.graveapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.graveapp.api.GraveyardModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private Toolbar tbMainSearch;
    private ListView lvToolbarSearch;
    private final String TAG = SearchActivity.class.getSimpleName();
    private String[] arrays;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        tbMainSearch = (Toolbar) findViewById(R.id.tb_toolbarsearch);
        lvToolbarSearch = (ListView) findViewById(R.id.lv_toolbarsearch);
        adapter = new ArrayAdapter<String>(this, R.layout.simple_list_item_1, arrays);
        lvToolbarSearch.setAdapter(adapter);
        setSupportActionBar(tbMainSearch);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        lvToolbarSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String block = (String) lvToolbarSearch.getItemAtPosition(position);
                Intent intent = new Intent(SearchActivity.this, MapsActivity.class);
                intent.putExtra("block", block);
                startActivity(intent);
            }
        });
    }

    public SearchActivity() {
        String[] blockNames = new String[MapsActivity.graveyardModel.getBlocks().size()];
        for (int i = 0; i < MapsActivity.graveyardModel.getBlocks().size(); i++) {
            blockNames[i] = MapsActivity.graveyardModel.getBlocks().get(i).getId();
        }
        this.arrays = blockNames;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem mSearchmenuItem = menu.findItem(R.id.menu_toolbarsearch);
        SearchView searchView = (SearchView) mSearchmenuItem.getActionView();
        searchView.setQueryHint("Enter block number");
        searchView.setOnQueryTextListener(this);
        Log.d(TAG, "onCreateOptionsMenu: mSearchmenuItem->" + mSearchmenuItem.getActionView());
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d(TAG, "onQuerySubmit: quert->" + query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d(TAG, "onQueryTextChange: newText->" + newText);
        adapter.getFilter().filter(newText);
        return true;
    }
}