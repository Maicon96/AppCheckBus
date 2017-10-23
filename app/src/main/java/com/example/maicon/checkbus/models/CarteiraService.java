package com.example.maicon.checkbus.models;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by maicon on 12/10/2017.
 */

public interface CarteiraService {
    public static final String BASE_URL = "http://192.168.0.105:8080/ConexaoCarteira/webresources/carteira.carteira/";

    //chamada que vai retornar um bus
    @GET("find/all")
    Call<Bus> listAlunos();

    //chamada que insere uma carteira
    @POST("create")
    Call<Void> insereCarteira(@Body Carteira carteira);

    public static final Retrofit retrofit =
            new Retrofit.Builder()
            .baseUrl(CarteiraService.BASE_URL)//minha url
            .addConverterFactory(GsonConverterFactory.create())//converte em um objeto gson
            .build();
}
