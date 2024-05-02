package com.example.vaiaonde;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vaiaonde.adapter.TravelAdapter;
import com.example.vaiaonde.model.ViagensModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TravelAdapter travelAdapter;
    private ListView travelListView;
    private Button btnNovaViagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnNovaViagem = findViewById(R.id.btnNovaViagem);
        btnNovaViagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NewTravelActivity.class));
            }
        });
        travelListView = findViewById(R.id.viagensPendentes);
        ArrayList<ViagensModel> travelList = new ArrayList<ViagensModel>();
        travelAdapter = new TravelAdapter(MainActivity.this, MainActivity.this);
        ViagensModel travelModel = new ViagensModel();
        travelModel.setDestino("unesc");
        travelList.add(travelModel);
        ViagensModel travelModel2 = new ViagensModel();
        travelModel2.setDestino("satc");
        travelList.add(travelModel2);
        ViagensModel travelModel3 = new ViagensModel();
        travelModel3.setDestino("esucri");
        travelList.add(travelModel3);
        ViagensModel travelModel4 = new ViagensModel();
        travelModel4.setDestino("fodase");
        travelList.add(travelModel4);
        travelAdapter.setItems(travelList);
        travelListView.setAdapter(travelAdapter);


    }
}