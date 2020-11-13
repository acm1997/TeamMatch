package com.example.teammatch.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teammatch.AppExecutors;
import com.example.teammatch.R;
import com.example.teammatch.adapters.EventAdapter;
import com.example.teammatch.objects.Evento;
import com.example.teammatch.room_db.TeamMatchDataBase;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MyProfileActivity extends AppCompatActivity {

    private static final String TAG = "MY_PROFILE_ACTIVITY";

    private TextView tname;
    private Button btn_EditP;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mlayoutManager;
    private EventAdapter mAdapter;

    private SharedPreferences preferences;

    public static final int EDIT_PROFILE_REQUEST = 0;
    public static final int GO_LOGIN_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        tname = findViewById(R.id.tVUsername);
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        Long usuario_id = preferences.getLong("usuario_id", 0);
        String name = preferences.getString("username", null);
        String email = preferences.getString("email", null);
        String password = preferences.getString("password", null);

        if(usuario_id > 0 && name != null && email != null && password != null){
            tname.setText(name);
        }

        /*String username = getIntent().getStringExtra("username");
        tname.setText(username);*/

        btn_EditP = findViewById(R.id.bEditP);

        btn_EditP.setOnClickListener(v -> {
            Intent intent = new Intent(MyProfileActivity.this, EditUserActivity.class);
            startActivityForResult(intent, EDIT_PROFILE_REQUEST);
        });


        //Todo mostar lista eventos crados





        //Inicio variable bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        //Pongo seleccion de "Inicio".
        bottomNavigationView.setSelectedItemId(R.id.ic_usuario);
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



        loadItems();

        mRecyclerView = findViewById(R.id.my_recycler_view_EventosUser);

        mRecyclerView.setHasFixedSize(true);

        mlayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mlayoutManager);

        mAdapter = new EventAdapter(new EventAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Evento item) {
                Snackbar.make(mRecyclerView, "Evento" +  item.getNombre() + "clicked", Snackbar.LENGTH_SHORT).show(); //TODO enviar a modificar evento
            }
        });

        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();

        // Load saved ToDoItems, if necessary

        if (mAdapter.getItemCount() == 0)
            loadItems();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present
        //  getMenuInflater().inflate(R.menu.menu_main, menu);
        getMenuInflater().inflate(R.menu.menu_perfil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.action_salir) {
            finish();
            System.exit(0);
        } else if(id == R.id.action_cerrar_sesion){
            preferences.edit().clear().apply();
            Toast.makeText(getApplicationContext(), "Se ha cerrado la sesión", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MyProfileActivity.this, LoginActivity.class);
            startActivityForResult(intent, GO_LOGIN_REQUEST);
        }

        return super.onOptionsItemSelected(item);
    }


    // Load stored Eventos
    private void loadItems() {
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        Long usuario_id = preferences.getLong("usuario_id", 0);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<Evento> eventosUser= TeamMatchDataBase.getInstance(MyProfileActivity.this).getDao().getAllEventosByUserId(usuario_id);
                //log("USUARIO CREADOR" + userWithEventos.getUser().getUsername().toString());
                runOnUiThread(() -> mAdapter.load(eventosUser));
            }
        });
    }


    private void log (String msg){
        try {
            Thread.sleep(500);
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, msg);
    }
}
