package com.example.teammatch;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MisEventosAtivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_eventos);

        String nombreEvento = getIntent().getStringExtra("NOMBRE EVENTO");
        String fechaEvento = getIntent().getStringExtra("FECHA");
        if ( nombreEvento == null ) nombreEvento="";
        if ( fechaEvento == null ) fechaEvento="";
        TextView tVMostrarNombreEvento = findViewById(R.id.nomEvento);
        tVMostrarNombreEvento.setText("El evento creado es:  "+nombreEvento);
    }
}
