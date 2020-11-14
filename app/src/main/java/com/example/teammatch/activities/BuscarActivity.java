package com.example.teammatch.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teammatch.AppExecutors;
import com.example.teammatch.R;
import com.example.teammatch.adapters.EventAdapter;
import com.example.teammatch.objects.Evento;
import com.example.teammatch.room_db.TeamMatchDataBase;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class BuscarActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private static final String FILE_NAME = "BuscarActivityData.txt";
    private SearchView svSearch;
    private RecyclerView mRecyclerViewSearchName;
    private RecyclerView.LayoutManager mLayoutManagerSearchName;
    private EventAdapter mAdapterSearch;
    private ArrayList<Evento> ListaEventos;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);

        loadItems();

        svSearch = findViewById(R.id.svSearch);
        mRecyclerViewSearchName = findViewById(R.id.rvLista);
        mRecyclerViewSearchName.setHasFixedSize(true);

        mLayoutManagerSearchName = new LinearLayoutManager(this);
        mRecyclerViewSearchName.setLayoutManager(mLayoutManagerSearchName);

        mAdapterSearch = new EventAdapter(new EventAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Evento item) {
                Snackbar.make(mRecyclerViewSearchName, "Evento" +  item.getNombre() + "clicked", Snackbar.LENGTH_SHORT).show(); //TODO enviar a modificar evento
            }
        });

        mRecyclerViewSearchName.setAdapter(mAdapterSearch);

   //     initListener();

        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        Long usuario_id = preferences.getLong("usuario_id", 0);
        String name = preferences.getString("username", null);
        String email = preferences.getString("email", null);
        String password = preferences.getString("password", null);

        //Inicio variable bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        //Pongo seleccion de "Inicio".
        bottomNavigationView.setSelectedItemId(R.id.ic_buscar);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.ic_usuario:
                    if (usuario_id > 0 && name != null && email != null && password != null) {
                        startActivity(new Intent(getApplicationContext(), MyProfileActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    } else {
                        Toast.makeText(getApplicationContext(), "No estas registrado en la aplicación", Toast.LENGTH_LONG).show();

                        return false;
                    }

                case R.id.ic_home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class ));
                    overridePendingTransition(0,0);
                    return true;

                case R.id.ic_buscar:
                    startActivity(new Intent(getApplicationContext(), BuscarActivity.class ));
                    overridePendingTransition(0,0);
                    return true;

                case R.id.ic_equipos:
                    startActivity(new Intent(getApplicationContext(), EquiposActivity.class ));
                    overridePendingTransition(0,0);
                    return true;
            }
            return false;
        });
    }


    @Override
    public void onResume() {
        super.onResume();

        if (mAdapterSearch.getItemCount() == 0)
            loadItems();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveItems();
    }
    @Override
    protected void onDestroy() {
        TeamMatchDataBase.getInstance(this).close();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present
   /*     getMenuInflater().inflate(R.menu.options_menu, menu);
        super.onCreateOptionsMenu(menu);
        MenuItem item = menu.findItem(R.id.svSearch);

        SearchView searchView =
                (SearchView) menu.findItem(R.id.svSearch).getActionView();

        searchView.setOnQueryTextListener(this);
        item.setOnActionExpandListener(item, new MenuItem());*/

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.svSearch).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }


    // Load stored Eventos
    private void loadItems() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                ListaEventos = TeamMatchDataBase.getInstance(BuscarActivity.this).getDao().getAllEventos();
                runOnUiThread(() -> mAdapterSearch.load(ListaEventos));
            }
        });
    }

    private void saveItems() {
        PrintWriter writer = null;
        try {
            FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                    fos)));

            for (int idx = 0; idx < mAdapterSearch.getItemCount(); idx++) {

                writer.println(mAdapterSearch.getItem(idx));

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != writer) {
                writer.close();
            }
        }
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        try {
            ArrayList<Evento>listafiltrada=filter(ListaEventos, newText);
            mAdapterSearch.setFilter(listafiltrada);

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    private ArrayList<Evento> filter(ArrayList<Evento> eventos, String texto){
        ArrayList<Evento>listafiltrada= new ArrayList<>();
        try {
            texto.toLowerCase();
            for(Evento ev: eventos){
                String event = ev.getNombre().toLowerCase();
                if(event.contains(texto)){
                    listafiltrada.add(ev);
                }
            }
        }catch (Exception E){
            E.printStackTrace();
        }
        return listafiltrada;
    }
}
