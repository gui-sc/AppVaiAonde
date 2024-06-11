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

import com.example.vaiaonde.database.dao.GastoGasolinaDAO;
import com.example.vaiaonde.database.dao.ViagensDAO;
import com.example.vaiaonde.database.model.GastoGasolinaModel;
import com.example.vaiaonde.database.model.ViagensModel;

import java.text.DecimalFormat;

public class FuelActivity extends AppCompatActivity {

    private Button btnVoltar, btnSalvar;
    private EditText txtTotalKm, txtMediaLitro,txtCustoLitro, txtTotalVeiculos;
    private TextView txtTotal;
    private ViagensModel viagem;
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
        long id = getIntent().getLongExtra("travel", 0);
        if(id == 0){
            startActivity(new Intent(FuelActivity.this, MainActivity.class));
            finish();
            return;
        }
        viagem = new ViagensDAO(FuelActivity.this).selectById(id);
        if(viagem == null){
            startActivity(new Intent(FuelActivity.this, MainActivity.class));
            finish();
            return;
        }
        GastoGasolinaModel gasto = new GastoGasolinaDAO(FuelActivity.this).SelectByViagem(viagem);
        DecimalFormat decimalFormat = new DecimalFormat("0.##");
        txtTotalVeiculos.setText(String.valueOf(gasto.getTotal_veiculos()));
        txtCustoLitro.setText(String.valueOf(gasto.getCusto_litro()));
        txtTotalKm.setText(String.valueOf(gasto.getKm()));
        txtMediaLitro.setText(String.valueOf(gasto.getKm_litro()));
        txtTotal.setText(decimalFormat.format(gasto.calcularCustoTotal()));
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FuelActivity.this, TravelActivity.class);
                intent.putExtra("travel", viagem.getId());
                startActivity(intent);
                finish();
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
                txtTotal.setText(decimalFormat.format(gasto.calcularCustoTotal()));
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
                if(custo.endsWith(".") || custo.isEmpty()){
                    custo += "0";
                }
                gasto.setKm_litro(Double.parseDouble(custo));
                txtTotal.setText(decimalFormat.format(gasto.calcularCustoTotal()));
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
                txtTotal.setText(decimalFormat.format(gasto.calcularCustoTotal()));
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
                txtTotal.setText(decimalFormat.format(gasto.calcularCustoTotal()));
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long id = -1;
                if(gasto.getKm() <= 0 || gasto.getCusto_litro() <= 0 || gasto.getKm_litro() <= 0 || gasto.getTotal_veiculos() <= 0){
                    Toast.makeText(FuelActivity.this, "Todos os valores precisam ser maiores do que 0.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(gasto.getId() == 0){
                    id = new GastoGasolinaDAO(FuelActivity.this).Insert(gasto);
                }else{
                    id = new GastoGasolinaDAO(FuelActivity.this).Update(gasto);
                }

                if(id == -1){
                    Toast.makeText(FuelActivity.this, "Ocorreu um erro!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FuelActivity.this, "Informações atualizadas com sucesso!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FuelActivity.this, TravelActivity.class);
                    intent.putExtra("travel", viagem.getId());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(FuelActivity.this, TravelActivity.class);
        intent.putExtra("travel", viagem.getId());
        startActivity(intent);
        finish();
    }

}