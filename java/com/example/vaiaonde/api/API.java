package com.example.vaiaonde.api;

import com.example.vaiaonde.api.endpoint.ViagemEndPoint;
import com.example.vaiaonde.api.response.Respostas;
import com.example.vaiaonde.database.model.ViagensModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {

    private static final String URL_ROOT = "http://api.genialsaude.com.br/";

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL_ROOT)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static void postViagem(ViagensModel viagens, Callback<Respostas> callback) {
        ViagemEndPoint endpoint = retrofit.create(ViagemEndPoint.class);
        Call<Respostas> call = endpoint.postViagem(viagens);
        call.enqueue(callback);
    }


}
