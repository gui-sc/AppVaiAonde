package com.example.vaiaonde;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vaiaonde.api.API;
import com.example.vaiaonde.api.ViagemCallback;
import com.example.vaiaonde.api.response.Respostas;
import com.example.vaiaonde.database.dao.GastoHospedagemDAO;
import com.example.vaiaonde.database.dao.GastoRefeicoesDAO;
import com.example.vaiaonde.database.dao.ViagensDAO;
import com.example.vaiaonde.database.model.GastoRefeicoesModel;
import com.example.vaiaonde.database.model.ViagensModel;

import java.text.DecimalFormat;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealActivity extends AppCompatActivity {


    private Button btnVoltar, btnSalvar;
    private EditText txtCustoRefeicao, txtRefeicoes;
    private TextView txtTotal;
    private ViagensModel viagem;
    private GastoRefeicoesModel gasto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);
        btnVoltar = findViewById(R.id.btnVoltar);
        btnSalvar = findViewById(R.id.btnSalvar);
        txtCustoRefeicao = findViewById(R.id.txtCustoRefeicao);
        txtRefeicoes = findViewById(R.id.txtRefeicoes);
        txtTotal = findViewById(R.id.txtTotal);
        DecimalFormat decimalFormat = new DecimalFormat("0.##");
        long id = getIntent().getLongExtra("travel", 0);
        if(id == 0){
            startActivity(new Intent(MealActivity.this, MainActivity.class));
            finish();
            return;
        }

        SweetAlertDialog pDialogSearch = new SweetAlertDialog(MealActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialogSearch.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialogSearch.setTitleText("Aguarde");
        pDialogSearch.setContentText("Buscando dados do servidor ...");
        pDialogSearch.setCancelable(false);
        pDialogSearch.show();

        getViagem(Integer.parseInt(String.valueOf(id)), new ViagemCallback() {
            @Override
            public void onSuccess(ViagensModel viagemResponse) {
                // Acesso seguro ao objeto Viagem
                viagem = viagemResponse;
                gasto = viagem.getRefeicao();
                txtCustoRefeicao.setText(String.valueOf(gasto.getCusto_refeicao()));
                txtRefeicoes.setText(String.valueOf(gasto.getRefeicoes_dia()));
                txtTotal.setText(String.valueOf(gasto.calcularParcial()));
                pDialogSearch.dismissWithAnimation();
            }

            @Override
            public void onFailure(Throwable t) {
                pDialogSearch.dismissWithAnimation();
                new SweetAlertDialog(MealActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Erro")
                        .setContentText("Erro ao buscar dados do banco de dados, tente novamente mais tarde.")
                        .setContentText("OK")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {

                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                startActivity(new Intent(MealActivity.this, MainActivity.class));
                                finish();
                            }
                        })
                        .show();
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MealActivity.this, TravelActivity.class);
                intent.putExtra("travel", viagem.getId());
                startActivity(intent);
                finish();
            }
        });

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
                txtTotal.setText(decimalFormat.format(gasto.calcularParcial()));
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
                txtTotal.setText(decimalFormat.format(gasto.calcularParcial()));
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gasto.getCusto_refeicao() <= 0 || gasto.getRefeicoes_dia() <= 0) {
                    Toast.makeText(MealActivity.this, "Todos os valores precisam ser maiores do que 0.", Toast.LENGTH_SHORT).show();
                    return;
                }
                viagem.setRefeicao(gasto);
                viagem.getRefeicao().setUsuario((int)viagem.getUsuario());
                SweetAlertDialog pDialog = new SweetAlertDialog(MealActivity.this, SweetAlertDialog.PROGRESS_TYPE);
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
                                    id = new GastoRefeicoesDAO(MealActivity.this).Insert(gasto);
                                }else{
                                    id = new GastoRefeicoesDAO(MealActivity.this).Update(gasto);
                                }

                                if(id == -1){
                                    new SweetAlertDialog(MealActivity.this, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Erro")
                                            .setContentText("Erro interno do banco de dados")
                                            .show();
                                } else {
                                    new SweetAlertDialog(MealActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("Sucesso")
                                            .setContentText("Informações atualizadas com sucesso")
                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    sweetAlertDialog.dismissWithAnimation();
                                                    Intent intent = new Intent(MealActivity.this, TravelActivity.class);
                                                    intent.putExtra("travel", viagem.getId());
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }).show();
                                }
                            }else{
                                new SweetAlertDialog(MealActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Erro")
                                        .setContentText("Erro ao realizar atualização")
                                        .show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Respostas> call, Throwable t) {
                        new SweetAlertDialog(MealActivity.this, SweetAlertDialog.ERROR_TYPE)
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
        Intent intent = new Intent(MealActivity.this, TravelActivity.class);
        intent.putExtra("travel", viagem.getId());
        startActivity(intent);
        finish();
    }
}