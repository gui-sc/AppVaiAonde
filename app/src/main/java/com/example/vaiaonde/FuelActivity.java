package com.example.vaiaonde;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vaiaonde.database.model.GastoGasolinaModel;
import com.example.vaiaonde.database.model.ViagensModel;

public class FuelActivity extends AppCompatActivity {

    private Button btnVoltar, btnSalvar;
    private EditText txtTotalKm, txtMediaLitro,txtCustoLitro, txtTotalVeiculos;
    private TextView txtTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel);
        btnVoltar = findViewById(R.id.btnVoltar);
        btnSalvar = findViewById(R.id.btnSalvar);
        txtTotalKm = findViewById(R.id.txtTotalKm);
        txtMediaLitro = findViewById(R.id.txtMediaLitro);
        txtCustoLitro = findViewById(R.id.txtCusto);
        txtTotalVeiculos = findViewById(R.id.txtTotalVeiculos);
        txtTotal = findViewById(R.id.txtTotal);
        ViagensModel viagem = new ViagensModel();
        viagem.setDestino(getIntent().getStringExtra("destino"));
        viagem.setId(getIntent().getIntExtra("travel", 0));
        GastoGasolinaModel gasto = new GastoGasolinaModel();
        gasto.setViagem(viagem);
        gasto.setKm(0);
        gasto.setCusto_litro(0);
        gasto.setKm_litro(0);
        gasto.setTotal_veiculos(0);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FuelActivity.this, TravelActivity.class);
                intent.putExtra("destino", viagem.getDestino());
                intent.putExtra("travel", viagem.getId());
                startActivity(intent);
            }
        });

        txtTotalKm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String custo = s.toString();
                if(custo.isEmpty()){
                    custo = "0";
                }
                gasto.setKm(Integer.parseInt(custo));
                txtTotal.setText(String.valueOf(gasto.calcularCustoTotal()));
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        txtMediaLitro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String custo = s.toString();
                if(custo.isEmpty()){
                    custo = "0";
                }
                gasto.setKm_litro(Integer.parseInt(custo));
                txtTotal.setText(String.valueOf(gasto.calcularCustoTotal()));
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        txtCustoLitro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String custo = s.toString();
                if(custo.endsWith(".") || custo.isEmpty()){
                    custo += "0";
                }
                gasto.setCusto_litro(Double.parseDouble(custo));
                txtTotal.setText(String.valueOf(gasto.calcularCustoTotal()));
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        txtTotalVeiculos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String custo = s.toString();
                if(custo.isEmpty()){
                    custo = "0";
                }
                gasto.setTotal_veiculos(Integer.parseInt(custo));
                txtTotal.setText(String.valueOf(gasto.calcularCustoTotal()));
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

}