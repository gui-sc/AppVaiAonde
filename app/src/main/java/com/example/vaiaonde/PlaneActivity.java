package com.example.vaiaonde;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vaiaonde.database.model.ViagensModel;

public class PlaneActivity extends AppCompatActivity {

    private Button btnVoltar, btnSalvar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plane);
        btnVoltar = findViewById(R.id.btnVoltar);
        btnSalvar = findViewById(R.id.btnSalvar);
        ViagensModel viagem = new ViagensModel();
        viagem.setDestino(getIntent().getStringExtra("destino"));
        viagem.setId(getIntent().getIntExtra("travel", 0));
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlaneActivity.this, TravelActivity.class);
                intent.putExtra("destino", viagem.getDestino());
                intent.putExtra("travel", viagem.getId());
                startActivity(intent);
            }
        });
    }
}