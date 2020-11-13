package com.example.teammatch.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teammatch.AppExecutors;
import com.example.teammatch.R;
import com.example.teammatch.adapters.EventAdapter;
import com.example.teammatch.objects.Evento;
import com.example.teammatch.room_db.EventoDAO;
import com.example.teammatch.room_db.EventoDataBase;
import com.example.teammatch.room_db.UserDAO;
import com.example.teammatch.room_db.UserDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class BuscarActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private RecyclerView rvLista;
    private SearchView svSearch;
    private EventAdapter adapter;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);

        rvLista = findViewById(R.id.rvLista);
        svSearch = findViewById(R.id.svSearch);

        initListener();
        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }

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

    private void doMySearch(String query) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<Evento> eventos = EventoDataBase.getInstance(BuscarActivity.this).getDao().SearchByName(query);
                runOnUiThread(() -> adapter.load(eventos));
            }
        });
    }

    
    private void initListener() {
        svSearch.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
