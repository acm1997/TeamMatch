package com.example.teammatch.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.teammatch.R;
import com.example.teammatch.objects.Binding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.Bidi;

public class PistaDetallesActivity extends AppCompatActivity {

    private static final String TAG = "Lab-UserInterface";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pista_detalles);

        String nombrePista = getIntent().getStringExtra(Binding.NOMBRE);
        String ciudadPista = getIntent().getStringExtra(Binding.CIUDAD);
        String callePista = getIntent().getStringExtra(Binding.CALLE);
        if ( nombrePista == null ) nombrePista="";
        if ( ciudadPista == null ) ciudadPista="";
        if ( callePista == null ) callePista="";
        TextView nombrePistaa = findViewById(R.id.nombrePista);
        nombrePistaa.setText(nombrePista);
        TextView ciudadPistaa = findViewById(R.id.nombreCiudad);
        ciudadPistaa.setText( ciudadPista);
        TextView callePistaa = findViewById(R.id.nombreCalle);
        callePistaa.setText( callePista);

        Button map = findViewById(R.id.button_map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    private void log(String msg) {
        try {
            Thread.sleep(500);
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, msg);
    }

}
