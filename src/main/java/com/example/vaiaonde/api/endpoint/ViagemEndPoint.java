package com.example.vaiaonde.api.endpoint;

import com.example.vaiaonde.api.response.Respostas;
import com.example.vaiaonde.database.model.ViagensModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ViagemEndPoint {

    @POST("api/cadastro/viagem")
    Call<Respostas> postViagem(@Body ViagensModel viagem);

    @GET("api/listar/viagem")
    Call<ViagensModel> getViagem(@Query("viagemId") String viagemId);

    @GET("api/listar/viagem/conta")
    Call<ArrayList<ViagensModel>> getViagens(@Query("contaId") String idConta);

}
