package com.example.teammatch;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MisEventos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_eventos);

        String nombreEvento = getIntent().getStringExtra("NOMBRE EVENTO");
        if ( nombreEvento == null ) nombreEvento="";
        TextView tVMostrarNombreEvento = findViewById(R.id.nomEvento);
        tVMostrarNombreEvento.setText("El evento creado es:  "+nombreEvento);
    }
}
