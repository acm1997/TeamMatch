package com.example.teammatch.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.teammatch.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MyProfileActivity extends AppCompatActivity {

    TextView tname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        tname = findViewById(R.id.tVUsername);
        String username = getIntent().getStringExtra("username");
        tname.setText(username);



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
}