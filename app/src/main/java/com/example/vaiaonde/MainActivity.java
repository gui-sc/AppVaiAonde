package com.example.vaiaonde;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vaiaonde.adapter.TravelAdapter;
import com.example.vaiaonde.adapter.TravelModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TravelAdapter travelAdapter;
    private ListView travelListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        travelListView = findViewById(R.id.viagensPendentes);
        ArrayList<TravelModel> travelList = new ArrayList<TravelModel>();
        travelAdapter = new TravelAdapter((MainActivity.this));
        TravelModel travelModel = new TravelModel();
        travelModel.setDestino("unesc");
        travelList.add(travelModel);
        TravelModel travelModel2 = new TravelModel();
        travelModel2.setDestino("satc");
        travelList.add(travelModel2);
        TravelModel travelModel3 = new TravelModel();
        travelModel3.setDestino("esucri");
        travelList.add(travelModel3);
        TravelModel travelModel4 = new TravelModel();
        travelModel4.setDestino("fodase");
        travelList.add(travelModel4);
        travelAdapter.setItems(travelList);
        travelListView.setAdapter(travelAdapter);
    }
}