package com.example.teammatch.activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.teammatch.AppExecutors;
import com.example.teammatch.R;
import com.example.teammatch.activities.CrearEventoActivity;
import com.example.teammatch.adapters.EventAdapter;
import com.example.teammatch.objects.Evento;
import com.example.teammatch.objects.User;
import com.example.teammatch.room_db.EventoDataBase;
import com.example.teammatch.room_db.UserDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.snackbar.SnackbarContentLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.example.teammatch.objects.Evento.Deporte;

import static java.lang.Math.log;


public class MainActivity extends AppCompatActivity {

    private static final String FILE_NAME = "MainActivityData.txt";
    //IDs for menu items
    private static final int MENU_SETTINGS = Menu.FIRST;
    private static final int MENU_LOGIN = Menu.FIRST + 1;

    public static final int ADD_EVENTO_REQUEST = 0;
    public static final int REGISTER_REQUEST = 1;
    public static final int ADD_EQUIPO_REQUEST = 2;


    private static final String TAG = "UserInterface";

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mlayoutManager;
    private EventAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fabNuevoEvento);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MainActivity.this es el MainActivity,IMPORTANTE porque en este caso si ponemos solo this se refiere a la clase creada OneClickListener
                Intent intent = new Intent(MainActivity.this, CrearEventoActivity.class);
                startActivityForResult(intent, ADD_EVENTO_REQUEST);
            }
        });

        //Inicio variable bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        //Pongo seleccion de "Inicio".
        bottomNavigationView.setSelectedItemId(R.id.ic_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.ic_usuario:
                    startActivity(new Intent(getApplicationContext(), MyProfileActivity.class ));
                    overridePendingTransition(0,0);
                    return true;

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


        mRecyclerView = findViewById(R.id.my_recycler_view);

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_EVENTO_REQUEST && resultCode == RESULT_OK){
            Evento EventoItem = new Evento(data);
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    //añadir evento en la BD
                    EventoDataBase evento_dataBase = EventoDataBase.getInstance(MainActivity.this);
                    long id_evento = evento_dataBase.getDao().insert(EventoItem);

                    //actualizar evento
                    EventoItem.setId(id_evento);

                    //insertar evento en la lista
                    runOnUiThread(() -> mAdapter.add(EventoItem) );
                }
            });
        }

        if(requestCode == REGISTER_REQUEST && resultCode == RESULT_OK){
            User UserItem = new User(data);
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    //Añadir usuario a la BD
                    UserDatabase usuario_database = UserDatabase.getInstance(MainActivity.this);
                    long id_user = usuario_database.userDao().insert(UserItem);

                    //Actualizar user
                    UserItem.setId(id_user);

                    Intent intent = new Intent(MainActivity.this, MyProfileActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // Load saved ToDoItems, if necessary

        if (mAdapter.getItemCount() == 0)
            loadItems();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Save ToDoItems
        saveItems();
    }
    @Override
    protected void onDestroy() {
        EventoDataBase.getInstance(this).close();
        super.onDestroy();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        // Inflate the menu; this adds items to the action bar if it is present
         getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        if(id == R.id.action_settings) {
            //accion Setting
        } else if(id == R.id.action_text_login){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        } else if(id == R.id.action_text_share){
            compartirApp();
        }
        return super.onOptionsItemSelected(item);

       /* switch(item.getItemId()){
            case MENU_SETTINGS:
                log("Item settings funciona");
                //NewSetting()
                return true;
            case MENU_LOGIN:
                log("Item LOGIN funciona");*/
             /*       Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

              /*  Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }*/
    }


    // Load stored ToDoItems
    private void loadItems() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<Evento> eventos = EventoDataBase.getInstance(MainActivity.this).getDao().getAll();
                runOnUiThread(() -> mAdapter.load(eventos));
            }
        });
    }


    // Save ToDoItems to file
    private void saveItems() {
        PrintWriter writer = null;
        try {
            FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                    fos)));

            for (int idx = 0; idx < mAdapter.getItemCount(); idx++) {

                writer.println(mAdapter.getItem(idx));

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != writer) {
                writer.close();
            }
        }
    }

    private void compartirApp(){
        try{
            Intent ishare = new Intent(Intent.ACTION_SEND);
            ishare.setType("text/plain");
            ishare.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
            String aux = "Descarga la aplicación Team Match\n";
            aux += "https://play.google.com/store/apps/details?id=il.talent.winner&hl=es_419";
            ishare.putExtra(Intent.EXTRA_TEXT, aux);
            startActivity(ishare);
        }catch (Exception e){ }
    }
}