package com.example.teammatch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class crearEventoActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuevo_evento);
      //  TextView tVSaludo = findViewById(R.id.tVSaludo);

        final EditText tVNombreEvento = findViewById(R.id.nomEvento);
        final EditText tVFechaEvento = findViewById(R.id.fechaEvento);
        Button botonCrear = findViewById(R.id.buttonCrear);

        botonCrear.setOnClickListener(new View.OnClickListener() { //estamso definiendo otra clase dentro de MainActivity
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(crearEventoActivity.this, MisEventosAtivity.class); //MainActivity.this es el MainActivity,IMPORTANTE porque en este caso si ponemos solo this se refiere a la clase creada OneClickListener
                String nombreEvento = tVNombreEvento.getText().toString();
                String fechaEvento = tVFechaEvento.getText().toString();
                intent.putExtra("NOMBRE EVENTO", nombreEvento);
                intent.putExtra("FECHA", fechaEvento);
                startActivity(intent);
            }
        });
    }




}
