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

import com.example.vaiaonde.database.dao.GastoHospedagemDAO;
import com.example.vaiaonde.database.dao.GastoRefeicoesDAO;
import com.example.vaiaonde.database.dao.ViagensDAO;
import com.example.vaiaonde.database.model.GastoHospedagemModel;
import com.example.vaiaonde.database.model.ViagensModel;

public class HostActivity extends AppCompatActivity {
    private Button btnVoltar, btnSalvar;
    private EditText txtTotalQuartos, txtTotalNoites, txtCustoPorNoite;
            private TextView txtTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);
        btnVoltar = findViewById(R.id.btnVoltar);
        txtCustoPorNoite = findViewById(R.id.txtCustoPorNoite);
        txtTotal = findViewById(R.id.txtTotal);
        txtTotalQuartos = findViewById(R.id.txtTotalQuartos);
        txtCustoPorNoite = findViewById(R.id.txtCustoPorNoite);
        txtTotalNoites = findViewById(R.id.txtTotalNoites);
        btnSalvar = findViewById(R.id.btnSalvar);
        long viagemId = getIntent().getLongExtra("travel", 0);
        if(viagemId == 0) {
            startActivity(new Intent(HostActivity.this, MainActivity.class));
            return;
        }

        ViagensModel viagem = new ViagensDAO(HostActivity.this).selectById(viagemId);
        if(viagem == null){
            startActivity(new Intent(HostActivity.this, MainActivity.class));
            return;
        }
        GastoHospedagemModel gasto = new GastoHospedagemDAO(HostActivity.this).SelectByViagem(viagem);
        txtCustoPorNoite.setText(String.valueOf(gasto.getCusto_noite()));
        txtTotalQuartos.setText(String.valueOf(gasto.getQuartos()));
        txtTotalNoites.setText(String.valueOf(gasto.getNoites()));
        txtTotal.setText(String.valueOf(gasto.calcularGastoHospedagem()));
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HostActivity.this, TravelActivity.class);
                intent.putExtra("destino", viagem.getDestino());
                intent.putExtra("travel", viagem.getId());
                startActivity(intent);
            }
        });
        txtCustoPorNoite.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String custo = s.toString();
                custo = custo.replace(',', '.');
                if(custo.endsWith(".") || custo.isEmpty()){
                    custo += "0";
                }
                gasto.setCusto_noite(Double.parseDouble(custo));
                txtTotal.setText(String.valueOf(gasto.calcularGastoHospedagem()));
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        txtTotalNoites.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String custo = s.toString();
                custo = custo.replace(',', '.');
                if(custo.endsWith(".") || custo.isEmpty()){
                    custo += "0";
                }
                gasto.setNoites(Integer.parseInt(custo));
                txtTotal.setText(String.valueOf(gasto.calcularGastoHospedagem()));
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        txtTotalQuartos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String custo = s.toString();
                custo = custo.replace(',', '.');
                if(custo.endsWith(".") || custo.isEmpty()){
                    custo += "0";
                }
                gasto.setQuartos(Integer.parseInt(custo));
                txtTotal.setText(String.valueOf(gasto.calcularGastoHospedagem()));
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long id = -1;
                if(gasto.getId() == 0){
                    id = new GastoHospedagemDAO(HostActivity.this).Insert(gasto);
                }else{
                    id = new GastoHospedagemDAO(HostActivity.this).Update(gasto);
                }
                if(id == -1){
                    Toast.makeText(HostActivity.this, "Ocorreu um erro!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HostActivity.this, "Informações atualizadas com sucesso!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(HostActivity.this, TravelActivity.class);
                    intent.putExtra("travel", gasto.getViagem().getId());
                    startActivity(intent);
                }
            }
        });
    }

}