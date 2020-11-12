package com.example.teammatch.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teammatch.AppExecutors;
import com.example.teammatch.OnReposLoadedListener;
import com.example.teammatch.OpenDataService;
import com.example.teammatch.PistasLoaderRunnable;
import com.example.teammatch.R;
import com.example.teammatch.adapters.PistaAdapter;
import com.example.teammatch.objects.Binding;
import com.example.teammatch.objects.Pistas;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PistasActivity extends AppCompatActivity implements PistaAdapter.OnListInteractionListener{
    private static final String TAG = "Pistas: ";

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
        mAdapter = new PistaAdapter(new ArrayList<Binding>(),this);

// Create a very simple REST adapter which points to the API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://opendata.caceres.es/GetData/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenDataService service = retrofit.create(OpenDataService.class);//Retrofit me crea instancia de interfaz de arriba


        service.cogerPistas().enqueue(new Callback<Pistas>() {
            @Override
            public void onResponse(Call<Pistas> call, Response<Pistas> response) {

                Pistas p = response.body();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.swap(p.getResults().getBindings());//Se mete esta llamada aquí porque es una llamada asincrona.
                    }
                });
            }

            @Override
            public void onFailure(Call<Pistas> call, Throwable t) {
                t.printStackTrace();
            }
        });



        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onListInteraction(String url) {
         Uri webpage = Uri.parse("http://opendata.ayto-caceres.es/dataset/instalaciones-deportivas-caceres");
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(webIntent);
    }


}
