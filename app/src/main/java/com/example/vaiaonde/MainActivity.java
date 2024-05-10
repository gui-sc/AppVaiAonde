package com.example.vaiaonde;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vaiaonde.adapter.TravelAdapter;
import com.example.vaiaonde.database.dao.ViagensDAO;
import com.example.vaiaonde.database.model.ViagensModel;
import com.example.vaiaonde.shared.Shared;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TravelAdapter travelActiveAdapter,travelFinishedAdapter;
    private ListView travelListActiveView, travelListFinishedView;
    private Button btnNovaViagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        long usuario_id = preferences.getLong(Shared.KEY_USUARIO_ID, 0);
        if(usuario_id == 0){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        btnNovaViagem = findViewById(R.id.btnNovaViagem);
        btnNovaViagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NewTravelActivity.class));
            }
        });
        //viagens em aberto
        travelListActiveView = findViewById(R.id.viagensPendentes);
        ArrayList<ViagensModel> travelActiveList = new ViagensDAO(MainActivity.this).SelectAll(usuario_id, true);
        travelActiveAdapter = new TravelAdapter(MainActivity.this, MainActivity.this);
        travelActiveAdapter.setItems(travelActiveList);
        travelListActiveView.setAdapter(travelActiveAdapter);

        //viagens finalizadas
        travelListFinishedView = findViewById(R.id.viagensFinalizadas);
        ArrayList<ViagensModel> travelFinishedList = new ViagensDAO(MainActivity.this).SelectAll(usuario_id, false);
        travelFinishedAdapter = new TravelAdapter(MainActivity.this, MainActivity.this);
        travelFinishedAdapter.setItems(travelFinishedList);
        travelListFinishedView.setAdapter(travelFinishedAdapter);


    }
}