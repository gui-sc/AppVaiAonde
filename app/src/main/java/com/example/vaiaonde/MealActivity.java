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

import com.example.vaiaonde.database.model.GastoRefeicoesModel;
import com.example.vaiaonde.database.model.ViagensModel;

public class MealActivity extends AppCompatActivity {


    private Button btnVoltar, btnSalvar;
    private EditText txtCustoRefeicao, txtRefeicoes;
    private TextView txtTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);
        btnVoltar = findViewById(R.id.btnVoltar);
        btnSalvar = findViewById(R.id.btnSalvar);
        txtCustoRefeicao = findViewById(R.id.txtCustoRefeicao);
        txtRefeicoes = findViewById(R.id.txtRefeicoes);
        txtTotal = findViewById(R.id.txtTotal);
        ViagensModel viagem = new ViagensModel();
        viagem.setDestino(getIntent().getStringExtra("destino"));
        viagem.setId(getIntent().getIntExtra("travel", 0));

        GastoRefeicoesModel gasto = new GastoRefeicoesModel();
        gasto.setRefeicoes_dia(0);
        gasto.setCusto_refeicao(0);
        gasto.setViagem(viagem);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MealActivity.this, TravelActivity.class);
                intent.putExtra("destino", viagem.getDestino());
                intent.putExtra("travel", viagem.getId());
                startActivity(intent);
            }
        });

        txtCustoRefeicao.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String custo = s.toString();
                if(custo.endsWith(".") || custo.isEmpty()){
                    custo += "0";
                }
                gasto.setCusto_refeicao(Double.parseDouble(custo));
                txtTotal.setText(String.valueOf(gasto.calcularParcial()));
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        txtRefeicoes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String custo = s.toString();
                if(custo.isEmpty()){
                    custo = "0";
                }
                gasto.setRefeicoes_dia(Integer.parseInt(custo));
                txtTotal.setText(String.valueOf(gasto.calcularParcial()));
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
}