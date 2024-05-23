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

import com.example.vaiaonde.database.dao.GastoAereoDAO;
import com.example.vaiaonde.database.dao.GastoHospedagemDAO;
import com.example.vaiaonde.database.dao.ViagensDAO;
import com.example.vaiaonde.database.model.GastoAereoModel;
import com.example.vaiaonde.database.model.ViagensModel;

import java.text.DecimalFormat;

public class PlaneActivity extends AppCompatActivity {
    private EditText txtAluguel, txtCustoPessoa;
    private TextView txtTotal;
    private Button btnVoltar, btnSalvar;
    private ViagensModel viagem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plane);
        btnVoltar = findViewById(R.id.btnVoltar);
        btnSalvar = findViewById(R.id.btnSalvar);
        txtTotal = findViewById(R.id.txtTotal);
        txtAluguel = findViewById(R.id.txtAluguel);
        txtCustoPessoa = findViewById(R.id.txtCustoPessoa);
        long id = getIntent().getLongExtra("travel", 0);
        viagem = new ViagensDAO(PlaneActivity.this).selectById(id);

        if(viagem == null){
            startActivity(new Intent(PlaneActivity.this, MainActivity.class));
            finish();
            return;
        }
        GastoAereoModel gasto = new GastoAereoDAO(PlaneActivity.this).SelectByViagem(viagem);
        DecimalFormat decimalFormat = new DecimalFormat("0.##");
        txtCustoPessoa.setText(decimalFormat.format(gasto.getCusto_pessoa()));
        txtAluguel.setText(decimalFormat.format(gasto.getAluguel_veiculo()));
        txtTotal.setText(decimalFormat.format(gasto.calcularCustoTotal()));
        txtCustoPessoa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String custo = s.toString();
                custo = custo.replace(',', '.');
                if(custo.endsWith(".") || custo.isEmpty()){
                    custo += "0";
                }
                gasto.setCusto_pessoa(Double.parseDouble(custo));
                txtTotal.setText(decimalFormat.format(gasto.calcularCustoTotal()));
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        txtAluguel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String custo = s.toString();
                custo = custo.replace(',', '.');
                if(custo.endsWith(".") || custo.isEmpty()){
                    custo += "0";
                }
                gasto.setAluguel_veiculo(Double.parseDouble(custo));
                txtTotal.setText(decimalFormat.format(gasto.calcularCustoTotal()));
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlaneActivity.this, TravelActivity.class);
                intent.putExtra("travel", viagem.getId());
                startActivity(intent);
                finish();
            }
        });
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long id = -1;
                if(gasto.getCusto_pessoa() <= 0){
                    Toast.makeText(PlaneActivity.this, "O custo por pessoa precisa ser maior que 0.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(gasto.getId() == 0){
                    id = new GastoAereoDAO(PlaneActivity.this).Insert(gasto);
                }else{
                    id = new GastoAereoDAO(PlaneActivity.this).Update(gasto);
                }

                if(id == -1){
                    Toast.makeText(PlaneActivity.this, "Ocorreu um erro!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PlaneActivity.this, "Informações atualizadas com sucesso!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PlaneActivity.this, TravelActivity.class);
                    intent.putExtra("travel", viagem.getId());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PlaneActivity.this, TravelActivity.class);
        intent.putExtra("travel", viagem.getId());
        startActivity(intent);
        finish();
    }
}