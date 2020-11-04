package com.example.teammatch;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import com.example.teammatch.Evento.Deporte;

import java.util.Date;

public class crearEventoActivity extends AppCompatActivity {

    private EditText mNombre;
    private Date mFecha;
    private EditText mParticipantes;
    private EditText mDescripcion;
    private RadioGroup mDeportes;
    private RadioButton mDefaultDeporte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuevo_evento);
      //  TextView tVSaludo = findViewById(R.id.tVSaludo);

        final EditText tVNombreEvento = findViewById(R.id.deportEvent);
        final EditText tVFechaEvento = findViewById(R.id.fechaEvento);



        final Button cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        final  Button submitButton = findViewById(R.id.buttonCrear);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String n = mNombre.getText().toString();
                Date d = mFecha;
                Integer p = mParticipantes.getTextAlignment();
                String desc = mDescripcion.getText().toString();
                Deporte dep = getDeporte();

                Intent i = new Intent();
                Evento.packageIntent(i,n,d,p,desc,dep);

                setResult(RESULT_OK, i);
                finish();
            }
        });

    }

    private Deporte getDeporte() {

        switch (mDeportes.getCheckedRadioButtonId()) {
            case R.id.radioFutboll: {
                return Deporte.FUTBOL;
            }
            case R.id.radioBaloncesto: {
                return Deporte.BALONCESTO;
            }

            case R.id.radioVoleibol: {
                return Deporte.VOLEIBOL;
            }
            default: {
                return Deporte.TENIS;
            }
        }
    }

}
