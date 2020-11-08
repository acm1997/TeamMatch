package com.example.teammatch.objects;

import android.content.Intent;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.teammatch.room_db.DeporteConverter;
import com.example.teammatch.room_db.FechaConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity (tableName = "evento")
public class Evento {

    public enum Deporte {
        FUTBOL,BALONCESTO,TENIS,VOLEIBOL
    };

    @Ignore
    public final static String NOMBRE = "nombre";
    @Ignore
    public final static String FECHA = "fecha";
    @Ignore
    public final static String PARTICIPANTES = "participantes";
    @Ignore
    public final static String DESCRIPCION = "descripcion";
    @Ignore
    public final static String DEPORTE = "deporte";

    @Ignore
    public final static SimpleDateFormat FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss", Locale.US);

    @PrimaryKey (autoGenerate = true)
    private long mID;

    @ColumnInfo(name = "nombre")
    private String mNombre = new String();

    @TypeConverters(FechaConverter.class)
    private Date mFecha = new Date();

    private Integer mParticipantes = new Integer(0);

    private String mDescripcion = new String();

    @TypeConverters(DeporteConverter.class)
    private Deporte mDeporte = Deporte.FUTBOL;


    public Evento() {
        this.mNombre = "";
        this.mFecha = null;
        this.mParticipantes = 0;
        this.mDescripcion = "";
        this.mDeporte = null;
    }

    @Ignore
    public Evento(String mNombre, Date mFecha, Integer mParticipantes, String mDescripcion, Deporte mDeporte) {
        this.mNombre = mNombre;
        this.mFecha = mFecha;
        this.mParticipantes = mParticipantes;
        this.mDescripcion = mDescripcion;
        this.mDeporte = mDeporte;
    }

    @Ignore
    public Evento(Intent intent){
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
