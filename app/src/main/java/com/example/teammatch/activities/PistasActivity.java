package com.example.teammatch.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teammatch.AppExecutors;
import com.example.teammatch.R;
import com.example.teammatch.adapters.PistaAdapter;
import com.example.teammatch.objects.Pistum;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PistasActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PistaAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pistas);
        recyclerView = (RecyclerView) findViewById(R.id.idRecyclerPista);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new PistaAdapter(new ArrayList<>(),this);

        //Ejecuta runnable en segundo plano
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                JsonReader reader = new JsonReader(new InputStreamReader(getResources().openRawResource(R.raw.espacios_deportivos)));
                // Parse JsonReader into list of Repo using Gson
                List<Pistum> pistas = Arrays.asList(new Gson().fromJson(reader, Pistum[].class));
                for(Pistum p : pistas){
                    try{
                        Thread.sleep(50);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.swap(pistas);
                    }
                });

            }
        });

        recyclerView.setAdapter(mAdapter);
    }

}
