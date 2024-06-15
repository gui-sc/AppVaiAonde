package com.example.vaiaonde;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vaiaonde.adapter.TravelAdapter;
import com.example.vaiaonde.api.ArrayViagemCallback;
import com.example.vaiaonde.database.dao.ViagensDAO;
import com.example.vaiaonde.database.model.ViagensModel;
import com.example.vaiaonde.shared.Shared;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {

    private TravelAdapter travelActiveAdapter,travelFinishedAdapter;
    private ListView travelListActiveView, travelListFinishedView;
    private Button btnNovaViagem, btnSair;
    private SharedPreferences.Editor edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        edit = preferences.edit();
        long usuario_id = preferences.getLong(Shared.KEY_USUARIO_ID, 0);
        if(usuario_id == 0){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
        btnNovaViagem = findViewById(R.id.btnNovaViagem);
        btnSair = findViewById(R.id.btnSair);
        btnNovaViagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NewTravelActivity.class));
                finish();
            }
        });
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.remove(Shared.KEY_USUARIO_ID);
                edit.apply();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });
        travelListActiveView = findViewById(R.id.viagensPendentes);
        travelListFinishedView = findViewById(R.id.viagensFinalizadas);

        SweetAlertDialog pDialogSearch = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialogSearch.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialogSearch.setTitleText("Aguarde");
        pDialogSearch.setContentText("Buscando dados do servidor ...");
        pDialogSearch.setCancelable(false);
        pDialogSearch.show();
        //viagens em aberto
        new ViagensDAO(MainActivity.this).SelectAllApi(usuario_id, true, new ArrayViagemCallback() {
            @Override
            public void onSuccess(ArrayList<ViagensModel> viagens) {
                travelActiveAdapter = new TravelAdapter(MainActivity.this, MainActivity.this);
                travelActiveAdapter.setItems(viagens);
                travelListActiveView.setAdapter(travelActiveAdapter);
                pDialogSearch.dismissWithAnimation();
            }

            @Override
            public void onFailure(Throwable t) {
                pDialogSearch.dismissWithAnimation();
                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("ERRO")
                        .setContentText("Erro ao buscar viagens ativas")
                        .show();
            }
        });

        pDialogSearch.show();
        //viagens finalizadas
        new ViagensDAO(MainActivity.this).SelectAllApi(usuario_id, false, new ArrayViagemCallback() {
            @Override
            public void onSuccess(ArrayList<ViagensModel> viagens) {
                travelFinishedAdapter = new TravelAdapter(MainActivity.this, MainActivity.this);
                travelFinishedAdapter.setItems(viagens);
                travelListFinishedView.setAdapter(travelFinishedAdapter);
                pDialogSearch.dismissWithAnimation();
            }

            @Override
            public void onFailure(Throwable t) {
                pDialogSearch.dismissWithAnimation();
                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("ERRO")
                        .setContentText("Erro ao buscar viagens encerradas")
                        .show();
            }
        });

    }
}