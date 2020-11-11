package com.example.teammatch;

import com.example.teammatch.objects.Binding;
import com.example.teammatch.objects.Pistas;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OpenDataService {
    @GET("{url}")
    Call<Pistas> cogerPistas(@Path("url") String url);
}