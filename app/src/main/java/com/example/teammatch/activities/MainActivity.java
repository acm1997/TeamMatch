package com.example.teammatch.activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.teammatch.R;
import com.example.teammatch.activities.CrearEventoActivity;
import com.example.teammatch.adapters.EventAdapter;
import com.example.teammatch.objects.Evento;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.snackbar.SnackbarContentLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

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
import com.example.teammatch.objects.Evento.Deporte;

import static java.lang.Math.log;


public class MainActivity extends AppCompatActivity {

    private static final String FILE_NAME = "MainActivityData.txt";
    //IDs for menu items
    private static final int MENU_SETTINGS = Menu.FIRST;
    private static final int MENU_LOGIN = Menu.FIRST + 1;

    public static final int ADD_EVENTO_REQUEST = 0;
    public static final int LOGIN_REQUEST = 0;

    private static final String TAG = "UserInterface";

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mlayoutManager;
    private EventAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            mAdapter.add(EventoItem);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(Menu.NONE, MENU_SETTINGS, Menu.NONE, "Settings");
        menu.add(Menu.NONE, MENU_LOGIN, Menu.NONE, "Login");
        // Inflate the menu; this adds items to the action bar if it is present
      //  getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch(item.getItemId()){
            case MENU_SETTINGS:
                log("Item settings funciona");
                //NewSetting()
                return true;
            case MENU_LOGIN:
                log("Item LOGIN funciona");
             /*       Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

;
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    // Load stored ToDoItems
    private void loadItems() {
        BufferedReader reader = null;
        try {
            FileInputStream fis = openFileInput(FILE_NAME);
            reader = new BufferedReader(new InputStreamReader(fis));

            String nombre = null;
            Date fecha = null;
            String participantes =  null;
            String descripcion =  null;
            String deporte =  null;


            while (null != (nombre = reader.readLine())) {
                fecha = Evento.FORMAT.parse(reader.readLine());
                participantes = reader.readLine();
                descripcion = reader.readLine();
                deporte = reader.readLine();
                mAdapter.add(new Evento(nombre,fecha,Integer.parseInt(participantes),
                        descripcion, Deporte.valueOf(deporte)));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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
    private void log(String msg) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, msg);
    }
}