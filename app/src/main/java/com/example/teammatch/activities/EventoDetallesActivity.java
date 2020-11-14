package com.example.teammatch.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teammatch.R;
import com.example.teammatch.objects.Evento;

public class EventoDetallesActivity extends AppCompatActivity {

    private static final String TAG = "EVENTO_DETALLES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evento_detalle);

        Evento e = new Evento(getIntent());

        log("EVENTO RECIBIDO: " + e.toString());

/*        String nombreEvento = getIntent().getStringExtra(Evento.NOMBRE);
        String fechaEvento = getIntent().getStringExtra(Evento.FECHA);
        String descripcion = getIntent().getStringExtra(Evento.DESCRIPCION);
        String participantes = getIntent().get(Evento.PARTICIPANTES, 0);
        String deporte = getIntent().getStringExtra(Evento.DEPORTE);
        String pista = getIntent().getStringExtra(Evento.PISTA);


        if ( nombreEvento == null ) nombreEvento="";
        if ( fechaEvento == null ) fechaEvento="";
        if ( descripcion == null ) descripcion="";
        if ( participantes == null ) participantes="";
        if ( deporte == null ) deporte="";
        if ( pista == null ) pista="";*/

        TextView tVMostrarNombreEvento = findViewById(R.id.nomEvento2);
        tVMostrarNombreEvento.setText(e.getNombre());

        TextView tVMostrarDescripcionEvento = findViewById(R.id.descEvento2);
        tVMostrarDescripcionEvento.setText(e.getDescripcion());

        TextView tVMostrarFechaEvento = findViewById(R.id.fechaEvento2);
        tVMostrarFechaEvento.setText(e.getFecha().toString());

        TextView tVMostrarDeporteEvento = findViewById(R.id.deporteEvento2);
        tVMostrarDeporteEvento.setText(e.getDeporte().toString());

        TextView tVMostrarPistaEvento = findViewById(R.id.idPistaDeEvento2);
        tVMostrarPistaEvento.setText(e.getPista());

        TextView tVMostrarParticipantesEvento = findViewById(R.id.participantesEvent2);
        tVMostrarParticipantesEvento.setText(e.getParticipantes().toString());

        final Button cancelButton = (Button) findViewById(R.id.idVolverInicio);
        cancelButton.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
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
