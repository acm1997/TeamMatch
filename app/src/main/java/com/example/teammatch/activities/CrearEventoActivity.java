package com.example.teammatch.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teammatch.R;
import com.example.teammatch.objects.Evento;
import com.example.teammatch.objects.Evento.Deporte;

import java.util.Calendar;
import java.util.Date;

public class CrearEventoActivity extends AppCompatActivity {

    // 7 days in milliseconds - 7 * 24 * 60 * 60 * 1000
    private static final int SEVEN_DAYS = 604800000;

    private static final String TAG = "Lab-UserInterface";

    private static String horaString;
    private static String fechaString;
    private static TextView fechaView;
    private static TextView horaView;

    private EditText mNombre;
    private Date mFecha;
    private EditText mParticipantes;
    private EditText mDescripcion;
    private RadioGroup mDeportes;
    private RadioButton mDefaultDeporte;
    public static final int BUSCAR_EVENTO_REQUEST = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuevo_evento);
        mNombre = (EditText) findViewById(R.id.nombreEvento);
        mParticipantes =(EditText) findViewById(R.id.numParticipantes);
        mDescripcion = (EditText) findViewById(R.id.descEvento);
        mDeportes = (RadioGroup) findViewById(R.id.radioGroup);
        mDefaultDeporte = (RadioButton) findViewById(R.id.radioFutbol);
        fechaView = (TextView) findViewById(R.id.fecha);
        horaView = (TextView) findViewById(R.id.hora);

        // Set the default date and time

        setDefaultDateTime();

        // OnClickListener for the Date button, calls showDatePickerDialog() to show
        // the Date dialog

        final Button datePickerButton = (Button) findViewById(R.id.botonfecha);
        datePickerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // OnClickListener for the Time button, calls showTimePickerDialog() to show
        // the Time Dialog

        final Button timePickerButton = (Button) findViewById(R.id.botonhora);
        timePickerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });


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
                String d = fechaString +"-" + horaString;
                Integer p = Integer.parseInt(mParticipantes.getText().toString()) ;
                String desc = mDescripcion.getText().toString();
                Deporte dep = getDeporte();


                Intent i = new Intent();
                Evento.packageIntent(i,n,d,p,desc,dep);


                setResult(RESULT_OK, i);
                finish();
            }
        });

    }

    private void setDefaultDateTime() {

        // Default is current time + 7 days
        mFecha = new Date();
        mFecha = new Date(mFecha.getTime() + SEVEN_DAYS);

        Calendar c = Calendar.getInstance();
        c.setTime(mFecha);

        setDateString(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH));

        fechaView.setText(fechaString);

        setTimeString(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),
                c.get(Calendar.MILLISECOND));

        horaView.setText(horaString);
    }

    private static void setDateString(int year, int monthOfYear, int dayOfMonth) {

        // Increment monthOfYear for Calendar/Date -> Time Format setting
        monthOfYear++;
        String mon = "" + monthOfYear;
        String day = "" + dayOfMonth;

        if (monthOfYear < 10)
            mon = "0" + monthOfYear;
        if (dayOfMonth < 10)
            day = "0" + dayOfMonth;

        fechaString = year + "-" + mon + "-" + day;
    }

    private static void setTimeString(int hourOfDay, int minute, int mili) {
        String hour = "" + hourOfDay;
        String min = "" + minute;

        if (hourOfDay < 10)
            hour = "0" + hourOfDay;
        if (minute < 10)
            min = "0" + minute;

        horaString = hour + ":" + min + ":00";
    }

    private void showDatePickerDialog() {
        DatePickerFragment dpf = new DatePickerFragment();
        dpf.show(getFragmentManager(),"dpf");

    }

    private void showTimePickerDialog() {
        TimePickerFragment tpf = new TimePickerFragment();
        tpf.show(getFragmentManager(),"tpf");


    }

    // DialogFragment used to pick a ToDoItem deadline date

    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Use the current date as the default date in the picker

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            setDateString(year, monthOfYear, dayOfMonth);

            fechaView.setText(fechaString);
        }

    }

    // DialogFragment used to pick a ToDoItem deadline time

    public static class TimePickerFragment extends DialogFragment implements
            TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    true);
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            setTimeString(hourOfDay, minute, 0);

            horaView.setText(horaString);
        }
    }

    private Deporte getDeporte() {

        switch (mDeportes.getCheckedRadioButtonId()) {
            case R.id.radioFutbol: {
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
