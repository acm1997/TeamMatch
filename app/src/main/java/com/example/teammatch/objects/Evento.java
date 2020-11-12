package com.example.teammatch.objects;

import android.content.Intent;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
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
    //todo Falta pista

    @Ignore
    public final static SimpleDateFormat FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss", Locale.US);

    @PrimaryKey (autoGenerate = true)
    private long id;

    @ColumnInfo(name = "nombre")
    private String nombre;

    @TypeConverters(FechaConverter.class)
    private Date fecha = new Date();

    private Integer participantes = 0;

    private String descripcion;

    @TypeConverters(DeporteConverter.class)
    private Deporte deporte = Deporte.FUTBOL;

    public Evento() {
        this.nombre = "";
        this.fecha = null;
        this.participantes = 0;
        this.descripcion = "";
        this.deporte = null;
    }

    @Ignore
    public Evento(String mNombre, Date mFecha, Integer mParticipantes, String mDescripcion, Deporte mDeporte) {
        this.nombre = mNombre;
        this.fecha = mFecha;
        this.participantes = mParticipantes;
        this.descripcion = mDescripcion;
        this.deporte = mDeporte;
    }

    @Ignore
    public Evento(Intent intent){
        nombre = intent.getStringExtra(Evento.NOMBRE);
        participantes = intent.getIntExtra(Evento.PARTICIPANTES, 0);
        descripcion = intent.getStringExtra(Evento.DESCRIPCION);
        deporte = Deporte.valueOf(intent.getStringExtra(Evento.DEPORTE));

        try {
            fecha = Evento.FORMAT.parse(intent.getStringExtra(Evento.FECHA));
        } catch (ParseException e) {
            fecha = new Date();
        }


    }

    public Evento(long id, String nombre, Date fecha, Integer participantes, String descripcion, Deporte deporte) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.participantes = participantes;
        this.descripcion = descripcion;
        this.deporte = deporte;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public Integer getParticipantes() {
        return participantes;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Deporte getDeporte() {
        return deporte;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setParticipantes(Integer participantes) {
        this.participantes = participantes;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setDeporte(Deporte deporte) {
        this.deporte = deporte;
    }

    public static void packageIntent(Intent intent, String mNombre, String mFecha, Integer mParticipantes, String mDescripcion, Deporte mDeporte) {
        intent.putExtra(Evento.NOMBRE, mNombre);
        intent.putExtra(Evento.DESCRIPCION, mDescripcion);
        intent.putExtra(Evento.FECHA, mFecha);
        intent.putExtra(Evento.PARTICIPANTES, mParticipantes);
        intent.putExtra(Evento.DEPORTE, mDeporte.toString());
    }
}
