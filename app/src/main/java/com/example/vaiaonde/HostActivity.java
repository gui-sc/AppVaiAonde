package com.example.vaiaonde;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vaiaonde.model.ViagensModel;

public class HostActivity extends AppCompatActivity {
    private Button btnVoltar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);
        btnVoltar = findViewById(R.id.btnVoltar);
        ViagensModel viagem = new ViagensModel();
        viagem.setDestino(getIntent().getStringExtra("destino"));
        viagem.setId(getIntent().getIntExtra("travel", 0));
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HostActivity.this, TravelActivity.class);
                intent.putExtra("destino", viagem.getDestino());
                intent.putExtra("travel", viagem.getId());
                startActivity(intent);
            }
        });
    }

}