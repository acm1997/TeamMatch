package com.example.teammatch.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import com.example.teammatch.R;
import com.example.teammatch.objects.Evento;
import com.example.teammatch.room_db.TeamMatchDAO;
import com.example.teammatch.room_db.TeamMatchDataBase;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

public class EventDetailsActivity extends AppCompatActivity {

    private Button btn_deleteEvent;
    private SharedPreferences preferences;
    public static final int GO_HOME_DELETE_EVENT_REQUEST = 0;
    private static final String TAG = "DETALLES_ACTIVITY";

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

        btn_deleteEvent = findViewById(R.id.btn_deleteEvent);
        if(usuario_id > 0 && usuario_id.equals(e.getUserCreatorId())){
            btn_deleteEvent.setVisibility(View.VISIBLE);

            btn_deleteEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TeamMatchDataBase eventodatabase = TeamMatchDataBase.getInstance(getApplicationContext());
                    TeamMatchDAO eventodao = eventodatabase.getDao();
            //        Evento eventodelete = new Evento(e.getId(), e.getNombre(), e.getFecha(),e.getParticipantes(),e.getDescripcion(),e.getDeporte(),e.getPista(),e.getUserCreatorId(),e.getLatitud(),e.getLongitud());

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            log("ID Evento a borrar: "+e.getId());
                            e.setId(getIntent().getLongExtra("ID", 0));
                            eventodao.deleteEvento(e);
                            setResult(RESULT_OK);
                            finish();
   //                         Intent intentdelete = new Intent(EventDetailsActivity.this, MainActivity.class);
   //                         startActivityForResult(intentdelete, GO_HOME_DELETE_EVENT_REQUEST);
                        }
                    }).start();
                }
            });
        }
        else {
            btn_deleteEvent.setVisibility(View.INVISIBLE);
        }
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