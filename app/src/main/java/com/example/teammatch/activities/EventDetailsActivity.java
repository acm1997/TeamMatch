package com.example.teammatch.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import com.example.teammatch.R;
import com.example.teammatch.objects.Evento;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EventDetailsActivity extends AppCompatActivity {

    private Button btn_delete;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        Long usuario_id = preferences.getLong("usuario_id", 0);

        Evento e = new Evento(getIntent());

        Toolbar toolbarShowName = (Toolbar) findViewById(R.id.toolbar_nameEvent);
        setSupportActionBar(toolbarShowName);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(e.getNombre());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //Solo se muestra el botón editar, si el usuario ha creado el evento
        if(usuario_id > 0 && usuario_id.equals(e.getUserCreatorId())){
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent eventIntent = new Intent(EventDetailsActivity.this, EditEventActivity.class);
                    Evento.packageIntent(eventIntent,e.getNombre(),e.getFecha().toString(),e.getParticipantes(),e.getDescripcion(),e.getDeporte(),e.getPista(),e.getUserCreatorId(), e.getLatitud(),e.getLongitud());
                    startActivity(eventIntent);
                }
            });
        } else {
            fab.setVisibility(View.INVISIBLE);
        }

        TextView tVMostrarDescripcionEvento = findViewById(R.id.TVDescEvent);
        tVMostrarDescripcionEvento.setText(e.getDescripcion());

        TextView tVMostrarDeporteEvento = findViewById(R.id.TVCatEvent);
        tVMostrarDeporteEvento.setText(e.getDeporte().toString());

        TextView tVMostrarFechaEvento = findViewById(R.id.TVDateEvent);
        tVMostrarFechaEvento.setText(e.FORMAT.format(e.getFecha()));

        TextView tVMostrarPistaEvento = findViewById(R.id.TVPistaEvent);
        tVMostrarPistaEvento.setText(e.getPista());

        TextView tVMostrarParticipantesEvento = findViewById(R.id.TVParticipantsEvent);
        tVMostrarParticipantesEvento.setText(e.getParticipantes().toString());

        final Button map = (Button) findViewById(R.id.btn_map);
        map.setOnClickListener(v -> {
            Uri uri = Uri.parse("geo:"+e.getLatitud()+","+e.getLongitud()+"?z=16&q="+e.getLatitud()+","+e.getLongitud()+"(Caceres)");
            startActivity( new Intent(Intent.ACTION_VIEW, uri));
        });

        final Button deleteEvent = (Button) findViewById(R.id.btn_deleteEvent);
        if(usuario_id > 0 && usuario_id.equals(e.getUserCreatorId())){
            deleteEvent.setVisibility(View.VISIBLE);
            deleteEvent.setOnClickListener(v -> {
                //TODO borrar un evento
            });
        } else {
            deleteEvent.setVisibility(View.INVISIBLE);
        }

    }
}