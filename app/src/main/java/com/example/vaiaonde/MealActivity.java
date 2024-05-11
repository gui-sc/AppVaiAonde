package com.example.vaiaonde;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vaiaonde.database.dao.GastoRefeicoesDAO;
import com.example.vaiaonde.database.dao.ViagensDAO;
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
        long viagemId = getIntent().getLongExtra("travel", 0);
        if(viagemId == 0){
            startActivity(new Intent(MealActivity.this, MainActivity.class));
        }
        ViagensModel viagem = new ViagensDAO(MealActivity.this).selectById(viagemId);
        if(viagem == null){
            startActivity(new Intent(MealActivity.this, MainActivity.class));
            return;
        }
        GastoRefeicoesModel gasto = new GastoRefeicoesDAO(MealActivity.this).SelectByViagem(viagem);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MealActivity.this, TravelActivity.class);
                intent.putExtra("travel", viagem.getId());
                startActivity(intent);
            }
        });
        txtCustoRefeicao.setText(String.valueOf(gasto.getCusto_refeicao()));
        txtRefeicoes.setText(String.valueOf(gasto.getRefeicoes_dia()));
        txtTotal.setText(String.valueOf(gasto.calcularParcial()));
        txtCustoRefeicao.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String custo = s.toString();
                custo = custo.replace(',', '.');
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
                custo = custo.replace(',', '.');
                if(custo.isEmpty()){
                    custo = "0";
                }
                gasto.setRefeicoes_dia(Integer.parseInt(custo));
                txtTotal.setText(String.valueOf(gasto.calcularParcial()));
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long id = -1;
                if(gasto.getId() == 0){
                    id = new GastoRefeicoesDAO(MealActivity.this).Insert(gasto);
                }else{
                    id = new GastoRefeicoesDAO(MealActivity.this).Update(gasto);
                }
                if(id == -1){
                    Toast.makeText(MealActivity.this, "Ocorreu um erro!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MealActivity.this, "Informações atualizadas com sucesso!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MealActivity.this, TravelActivity.class);
                    intent.putExtra("travel", gasto.getViagem().getId());
                    startActivity(intent);
                }
            }
        });
    }
}