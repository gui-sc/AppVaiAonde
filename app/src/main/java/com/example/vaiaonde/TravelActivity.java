package com.example.vaiaonde;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vaiaonde.model.ViagensModel;

public class TravelActivity extends AppCompatActivity {

    private TextView txtDestino;
    private Button btnEncerrar, btnRefeicao, btnHospedagem, btnGasolina,
            btnAviao, btnOutros, btnApagar, btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set components
        setContentView(R.layout.activity_travel);
        txtDestino = findViewById(R.id.txtDestino);
        btnEncerrar = findViewById(R.id.btnEncerrar);
        btnRefeicao = findViewById(R.id.btnRefeicao);
        btnHospedagem = findViewById(R.id.btnHospedagem);
        btnGasolina = findViewById(R.id.btnGasolina);
        btnAviao = findViewById(R.id.btnAerea);
        btnOutros = findViewById(R.id.btnOutros);
        btnApagar = findViewById(R.id.btnApagar);
        btnVoltar = findViewById(R.id.btnVoltar);
        //get extras
        ViagensModel viagem = new ViagensModel();
        String destino = getIntent().getStringExtra("destino");
        viagem.setDestino(destino);
        txtDestino.setText(viagem.getDestino());
        int id = getIntent().getIntExtra("travel", 0);
        viagem.setId(id);
        btnRefeicao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TravelActivity.this, MealActivity.class);
                intent.putExtra("destino", destino);
                intent.putExtra("travel", viagem.getId());
                startActivity(intent);
            }
        });

        btnHospedagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TravelActivity.this, HostActivity.class);
                startActivity(intent);
            }
        });

        btnGasolina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TravelActivity.this, FuelActivity.class);
                startActivity(intent);
            }
        });
        btnAviao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TravelActivity.this, PlaneActivity.class);
                startActivity(intent);
            }
        });

        btnOutros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TravelActivity.this, "Função não implementada!", Toast.LENGTH_LONG).show();
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TravelActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}