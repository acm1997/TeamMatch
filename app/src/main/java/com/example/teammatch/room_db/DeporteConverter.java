package com.example.teammatch.room_db;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.teammatch.objects.Evento;

public class DeporteConverter {

    @TypeConverter
    public static String toString(Evento.Deporte deporte){
        return deporte == null ? null : deporte.name();
    }

    @TypeConverter
    public static Evento.Deporte toDeporte (String deporte){
        return deporte == null ? null : Evento.Deporte.valueOf(deporte);
    }
}
