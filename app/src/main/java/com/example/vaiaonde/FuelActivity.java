package com.example.vaiaonde;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vaiaonde.api.API;
import com.example.vaiaonde.api.response.Respostas;
import com.example.vaiaonde.database.dao.GastoGasolinaDAO;
import com.example.vaiaonde.database.dao.ViagensDAO;
import com.example.vaiaonde.database.model.GastoGasolinaModel;
import com.example.vaiaonde.database.model.ViagensModel;

import java.text.DecimalFormat;
import java.util.concurrent.CountDownLatch;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class FuelActivity extends AppCompatActivity {
    private interface ViagemCallback {
        void onSuccess(ViagensModel viagem);
        void onFailure(Throwable t);
    }
    private Button btnVoltar, btnSalvar;
    private EditText txtTotalKm, txtMediaLitro,txtCustoLitro, txtTotalVeiculos;
    private TextView txtTotal;
    private ViagensModel viagem;
    private long id;
    private GastoGasolinaModel gasto;
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

        id = getIntent().getLongExtra("travel", 0);
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
        getViagem(Integer.valueOf(String.valueOf(id)), new ViagemCallback() {
            @Override
            public void onSuccess(ViagensModel viagem) {
                // Acesso seguro ao objeto Viagem
                GastoGasolinaModel gasto = viagem.getGasolina();
                // Atualize a UI ou prossiga com outras operações aqui
                // ...
            }

            @Override
            public void onFailure(Throwable t) {
                // Trate a falha da chamada da API aqui
                // ...
            }
        });

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
                if(gasto.getKm() <= 0 || gasto.getCusto_litro() <= 0 || gasto.getKm_litro() <= 0 || gasto.getTotal_veiculos() <= 0){
                    Toast.makeText(FuelActivity.this, "Todos os valores precisam ser maiores do que 0.", Toast.LENGTH_SHORT).show();
                    return;
                }
                GastoGasolinaModel newGasto = new GastoGasolinaModel();
                viagem.setGasolina(gasto);
                viagem.getGasolina().setId(null);
                SweetAlertDialog pDialog = new SweetAlertDialog(FuelActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Aguarde");
                pDialog.setContentText("Enviando dados ao servidor ...");
                pDialog.setCancelable(false);
                pDialog.show();
                API.postViagem(viagem, new Callback<Respostas>() {
                    @Override
                    public void onResponse(Call<Respostas> call, Response<Respostas> response) {
                        pDialog.dismissWithAnimation();
                        if (response != null && response.isSuccessful()) {
                            Respostas respostas = response.body();
                            if (respostas.isSucesso()) {
                                long id;
                                if(gasto.getId() == 0){
                                    id = new GastoGasolinaDAO(FuelActivity.this).Insert(gasto);
                                }else{
                                    id = new GastoGasolinaDAO(FuelActivity.this).Update(gasto);
                                }

                                if(id == -1){
                                    new SweetAlertDialog(FuelActivity.this, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Erro")
                                            .setContentText("Erro interno do banco de dados")
                                            .show();
                                } else {
                                    new SweetAlertDialog(FuelActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("Sucesso")
                                            .setContentText("Informações atualizadas com sucesso")
                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    sweetAlertDialog.dismissWithAnimation();
                                                    Intent intent = new Intent(FuelActivity.this, TravelActivity.class);
                                                    intent.putExtra("travel", viagem.getId());
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }).show();
                                }
                            }else{
                                new SweetAlertDialog(FuelActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Erro")
                                        .setContentText("Erro ao realizar atualização")
                                        .show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Respostas> call, Throwable t) {
                        new SweetAlertDialog(FuelActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Erro")
                                .setContentText("Erro de conexão com a API")
                                .show();
                    }
                });

            }
        });
    }

    private void getViagem(int id, ViagemCallback callback) {
        // Chama a API para obter a viagem
        API.getViagem(id, new Callback<ViagensModel>() {
            @Override
            public void onResponse(Call<ViagensModel> call, Response<ViagensModel> response) {
                if (response != null && response.isSuccessful()) {
                    ViagensModel viagem = response.body();
                    callback.onSuccess(viagem); // Chama o callback de sucesso
                } else {
                    callback.onFailure(new Exception("Resposta não bem-sucedida"));
                }
            }

            @Override
            public void onFailure(Call<ViagensModel> call, Throwable t) {
                callback.onFailure(t); // Chama o callback de falha
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