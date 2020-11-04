package com.example.teammatch;

import android.content.Intent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Evento {

    public enum Deporte {
        FUTBOL,BALONCESTO,TENIS,VOLEIBOL
    };

    public final static String NOMBRE = "nombre";
    public final static String FECHA = "fecha";
    public final static String PARTICIPANTES = "participantes";
    public final static String DESCRIPCION = "descripcion";
    public final static String DEPORTE = "deporte";

    public final static SimpleDateFormat FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss", Locale.US);

    private String mNombre = new String();
    private Date mFecha = new Date();
    private Integer mParticipantes = new Integer(0);
    private String mDescripcion = new String();
    private Deporte mDeporte = Deporte.FUTBOL;


    Evento(String mNombre, Date mFecha, Integer mParticipantes, String mDescripcion, Deporte mDeporte) {
        this.mNombre = mNombre;
        this.mFecha = mFecha;
        this.mParticipantes = mParticipantes;
        this.mDescripcion = mDescripcion;
        this.mDeporte = mDeporte;
    }

    Evento(Intent intent){
        mNombre = intent.getStringExtra(Evento.NOMBRE);
        mParticipantes = intent.getIntExtra(Evento.PARTICIPANTES, 0);
        mDescripcion = intent.getStringExtra(Evento.DESCRIPCION);
        mDeporte = Deporte.valueOf(intent.getStringExtra(Evento.DEPORTE));

        try {
            mFecha = Evento.FORMAT.parse(intent.getStringExtra(Evento.FECHA));
        } catch (ParseException e) {
            mFecha = new Date();
        }


    }

    public String getmNombre() {
        return mNombre;
    }

    public Date getmFecha() {
        return mFecha;
    }

    public Integer getmParticipantes() {
        return mParticipantes;
    }

    public String getmDescripcion() {
        return mDescripcion;
    }

    public Deporte getmDeporte() {
        return mDeporte;
    }

    public void setmNombre(String mNombre) {
        this.mNombre = mNombre;
    }

    public void setmFecha(Date mFecha) {
        this.mFecha = mFecha;
    }

    public void setmParticipantes(Integer mParticipantes) {
        this.mParticipantes = mParticipantes;
    }

    public void setmDescripcion(String mDescripcion) {
        this.mDescripcion = mDescripcion;
    }

    public void setmDeporte(Deporte mDeporte) {
        this.mDeporte = mDeporte;
    }

    public static void packageIntent(Intent intent, String mNombre, Date mFecha, Integer mParticipantes, String mDescripcion, Deporte mDeporte) {
        intent.putExtra(Evento.NOMBRE, mNombre);
        intent.putExtra(Evento.DESCRIPCION, mDescripcion);
        intent.putExtra(Evento.FECHA, mFecha);
        intent.putExtra(Evento.PARTICIPANTES, mParticipantes);
        intent.putExtra(Evento.DEPORTE, mDeporte);
    }
}
