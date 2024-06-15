package com.example.vaiaonde;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.vaiaonde.adapter.OtherItemAdapter;
import com.example.vaiaonde.adapter.TravelAdapter;
import com.example.vaiaonde.api.API;
import com.example.vaiaonde.api.ViagemCallback;
import com.example.vaiaonde.api.response.Respostas;
import com.example.vaiaonde.database.dao.GastoAereoDAO;
import com.example.vaiaonde.database.dao.GastoDiversosDAO;
import com.example.vaiaonde.database.dao.ViagensDAO;
import com.example.vaiaonde.database.model.GastoDiversosModel;
import com.example.vaiaonde.database.model.ViagensModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtherActivity extends AppCompatActivity {
    private GastoDiversosModel selectedGasto;
    private ArrayList<GastoDiversosModel> listGastosDiversos;
    private ListView listGastos;
    private OtherItemAdapter otherItemAdapter;
    private EditText txtDescricao, txtValor;
    private Button btnSalvar, btnApagar, btnLimpar, btnVoltar;
    private ViagensModel viagem;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        listGastos = findViewById(R.id.listGastosDiversos);
        txtDescricao = findViewById(R.id.txtDescricao);
        txtValor = findViewById(R.id.txtValor);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnLimpar = findViewById(R.id.btnLimpar);
        btnApagar = findViewById(R.id.btnApagar);
        btnVoltar = findViewById(R.id.btnVoltar);
        long id = getIntent().getLongExtra("travel", 0);
        if(id == 0){
            startActivity(new Intent(OtherActivity.this, MainActivity.class));
            finish();
            return;
        }
        SweetAlertDialog pDialogSearch = new SweetAlertDialog(OtherActivity.this, SweetAlertDialog.PROGRESS_TYPE);
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
                listGastosDiversos = viagem.getDiversos();
                otherItemAdapter = new OtherItemAdapter(OtherActivity.this, OtherActivity.this, OtherActivity.this::setSelectedGasto);
                otherItemAdapter.setItems(listGastosDiversos);
                listGastos.setAdapter(otherItemAdapter);
                pDialogSearch.dismissWithAnimation();
            }

            @Override
            public void onFailure(Throwable t) {
                pDialogSearch.dismissWithAnimation();
                new SweetAlertDialog(OtherActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Erro")
                        .setContentText("Erro ao buscar dados do banco de dados, tente novamente mais tarde.")
                        .setContentText("OK")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {

                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                startActivity(new Intent(OtherActivity.this, MainActivity.class));
                                finish();
                            }
                        })
                        .show();
            }
        });
        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtDescricao.setText("");
                txtValor.setText("");
                selectedGasto = null;
            }
        });
        btnApagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedGasto == null){
                    Toast.makeText(OtherActivity.this, "Você não selecionou nenhum gasto!", Toast.LENGTH_SHORT).show();
                }else{
                    position = listGastosDiversos.indexOf(selectedGasto);
                    listGastosDiversos.remove(selectedGasto);
                    apiCall(selectedGasto, 3);

                    txtDescricao.setText("");
                    txtValor.setText("");
                    selectedGasto = null;
                }
            }
        });
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valor = txtValor.getText().toString();
                valor = valor.replace(",",".");
                if(valor.endsWith(".") || valor.isEmpty()){
                    valor += "0";
                }
                double custo = Double.parseDouble(valor);
                String descricao = txtDescricao.getText().toString();
                if(descricao.trim().isEmpty()){
                    Toast.makeText(OtherActivity.this, "Digite algo como descrição.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(custo <= 0){
                    Toast.makeText(OtherActivity.this, "Insira um valor maior que zero.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(selectedGasto == null){
                    GastoDiversosModel gasto = new GastoDiversosModel();
                    gasto.setViagemId(viagem.getId());
                    gasto.setDescricao(descricao);

                    gasto.setValor(custo);
                    listGastosDiversos.add(gasto);
                    apiCall(gasto, 1);
                }else{
                    position = listGastosDiversos.indexOf(selectedGasto);
                    selectedGasto.setDescricao(descricao);
                    selectedGasto.setValor(custo);
                    apiCall(selectedGasto, 2);
                }

                txtDescricao.setText("");
                txtValor.setText("");
                selectedGasto = null;
            }
        });
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtherActivity.this, TravelActivity.class);
                intent.putExtra("travel", viagem.getId());
                startActivity(intent);
                finish();
            }
        });

    }
    GastoDiversosModel setSelectedGasto(GastoDiversosModel gasto) {
        selectedGasto = gasto;
        DecimalFormat decimalFormat = new DecimalFormat(".##");
        txtDescricao.setText(gasto.getDescricao());
        txtValor.setText(decimalFormat.format(gasto.getValor()));

        return gasto;
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
        Intent intent = new Intent(OtherActivity.this, TravelActivity.class);
        intent.putExtra("travel", viagem.getId());
        startActivity(intent);
        finish();
    }
    
    private void apiCall(GastoDiversosModel gasto, int op){
        viagem.setDiversos(listGastosDiversos);
        SweetAlertDialog pDialog = new SweetAlertDialog(OtherActivity.this, SweetAlertDialog.PROGRESS_TYPE);
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
                        long id = 0;
                        switch (op){
                            case 1:
                                id = new GastoDiversosDAO(OtherActivity.this).Insert(gasto);
                                break;
                            case 2:
                                id = new GastoDiversosDAO(OtherActivity.this).Update(gasto);
                                break;
                            case 3:
                                id = new GastoDiversosDAO(OtherActivity.this).Delete(gasto);
                                break;
                        }

                        if(id == -1){
                            switch (op){
                                case 1:
                                    listGastosDiversos.remove(gasto);
                                    break;
                                case 3:
                                    listGastosDiversos.add(position, selectedGasto);
                                    break;
                            }
                            new SweetAlertDialog(OtherActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Erro")
                                    .setContentText("Erro interno do banco de dados")
                                    .show();
                        } else {
                            switch (op){
                                case 1:
                                case 3:
                                    otherItemAdapter.setItems(listGastosDiversos);
                                    listGastos.setAdapter(otherItemAdapter);
                                    break;
                                case 2:
                                    listGastosDiversos.set(position, selectedGasto);
                                    otherItemAdapter.setItems(listGastosDiversos);
                                    listGastos.setAdapter(otherItemAdapter);
                                    break;
                            }
                            new SweetAlertDialog(OtherActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Sucesso")
                                    .setContentText(op == 1 ? "Informações atualizadas com sucesso!": "Gasto apagado com sucesso!")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismissWithAnimation();
                                        }
                                    }).show();
                        }
                    }else{
                        switch (op){
                            case 1:
                                listGastosDiversos.remove(gasto);
                                break;
                            case 3:
                                listGastosDiversos.add(position, selectedGasto);
                                break;
                        }
                        new SweetAlertDialog(OtherActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Erro")
                                .setContentText("Erro ao realizar atualização")
                                .show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Respostas> call, Throwable t) {
                switch (op){
                    case 1:
                        listGastosDiversos.remove(gasto);
                        break;
                    case 3:
                        listGastosDiversos.add(position, selectedGasto);
                        break;
                }
                new SweetAlertDialog(OtherActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Erro")
                        .setContentText("Erro de conexão com a API")
                        .show();
            }
        });
    }
}