package com.example.teammatch;

import android.util.Log;

import com.example.teammatch.objects.Pistas;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.InputStream;
import java.io.InputStreamReader;

public class PistasLoaderRunnable implements Runnable{

    private final InputStream mInFile;
    private final OnReposLoadedListener mOnReposLoadedListener;
    private static final String TAG = "Pistas: ";

    public PistasLoaderRunnable(InputStream inFile, OnReposLoadedListener onReposLoadedListener){
        mInFile = inFile;
        mOnReposLoadedListener = onReposLoadedListener;
    }

    @Override
    public void run() {
        // Obtención de los datos a partir del InputStream
        JsonReader reader = new JsonReader(new InputStreamReader(mInFile));
        // Parse JsonReader into list of Repo using Gson
        Pistas pistas = new Gson().fromJson(reader, Pistas.class);
        log("Texto pistaaaaaaaaaaaaaa: "+ pistas.toString());
/*                for(Pistum p : pistas){
                    try{
                        Thread.sleep(50);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }*/
        // Llamada al Listener con los datos obtenidos
        AppExecutors.getInstance().mainThread().execute(new Runnable() {
            @Override
            public void run() {
               mOnReposLoadedListener.onReposLoaded(pistas);
            }
        });


    }
    private void log(String msg) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, msg);
    }
}

