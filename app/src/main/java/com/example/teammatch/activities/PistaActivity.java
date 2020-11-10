package com.example.teammatch.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teammatch.R;
import com.example.teammatch.objects.Pistum;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class PistaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);
        JsonReader reader = new JsonReader(new InputStreamReader(getResources().openRawResource(R.raw.espacios_deportivos)));
        // Parse JsonReader into list of Repo using Gson
        List<Pistum> pistas = Arrays.asList(new Gson().fromJson(reader, Pistum[].class));

    }

    }
