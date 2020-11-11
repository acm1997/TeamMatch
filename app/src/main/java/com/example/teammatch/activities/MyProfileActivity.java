package com.example.teammatch.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teammatch.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MyProfileActivity extends AppCompatActivity {

    private TextView tname;
    private Button btn_EditP;

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

        btn_EditP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProfileActivity.this, EditUserActivity.class);
                startActivityForResult(intent, EDIT_PROFILE_REQUEST);
            }
        });

        //Inicio variable bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        //Pongo seleccion de "Inicio".
        bottomNavigationView.setSelectedItemId(R.id.ic_usuario);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
                }
                return false;
            }
        });
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
    }
