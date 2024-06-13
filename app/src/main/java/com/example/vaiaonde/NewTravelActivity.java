package com.example.vaiaonde;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vaiaonde.api.API;
import com.example.vaiaonde.api.response.Respostas;
import com.example.vaiaonde.database.dao.GastoHospedagemDAO;
import com.example.vaiaonde.database.dao.UsuariosDAO;
import com.example.vaiaonde.database.dao.ViagensDAO;
import com.example.vaiaonde.database.model.GastoAereoModel;
import com.example.vaiaonde.database.model.GastoDiversosModel;
import com.example.vaiaonde.database.model.GastoGasolinaModel;
import com.example.vaiaonde.database.model.GastoHospedagemModel;
import com.example.vaiaonde.database.model.GastoRefeicoesModel;
import com.example.vaiaonde.database.model.UsuariosModel;
import com.example.vaiaonde.database.model.ViagensModel;
import com.example.vaiaonde.shared.Shared;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewTravelActivity extends AppCompatActivity {
    private EditText txtDestino, txtDias, txtPessoas;
    private Button btnSalvar, btnVoltar;
    private TextView lblScreenName;
    private long viagem_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_travel);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(NewTravelActivity.this);
        long usuario_id = preferences.getLong(Shared.KEY_USUARIO_ID, 0);
        viagem_id = getIntent().getLongExtra("travel", 0);

        if(usuario_id == 0){
            startActivity(new Intent(NewTravelActivity.this, LoginActivity.class));
            finish();
            return;
        }
        UsuariosModel usuario = new UsuariosDAO(NewTravelActivity.this).SelectById(usuario_id);
        if(usuario == null){
            startActivity(new Intent(NewTravelActivity.this, LoginActivity.class));
            finish();
            return;
        }
        btnVoltar = findViewById(R.id.btnVoltar);
        btnSalvar = findViewById(R.id.btnSalvar);
        txtDias = findViewById(R.id.txtDias);
        txtPessoas = findViewById(R.id.txtPessoas);
        txtDestino = findViewById(R.id.txtDestino);
        lblScreenName = findViewById(R.id.lblScreenName);
        ViagensModel viagem = null;
        if(viagem_id != 0){
            viagem = new ViagensDAO(NewTravelActivity.this).selectById(viagem_id);
            txtDestino.setText(viagem.getDestino());
            txtPessoas.setText(String.valueOf(viagem.getPessoas()));
            txtDias.setText(String.valueOf(viagem.getDias()));
            lblScreenName.setText(R.string.editar_viagem);
        }
        ViagensModel finalViagem = viagem;

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viagem_id == 0){
                    startActivity(new Intent(NewTravelActivity.this, MainActivity.class));
                }else{
                    Intent intent = new Intent(NewTravelActivity.this, TravelActivity.class);
                    intent.putExtra("travel", viagem_id);
                    startActivity(intent);
                }
                finish();
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String destino = txtDestino.getText().toString();
                String pessoasValue = txtPessoas.getText().toString();
                String diasValue = txtDias.getText().toString();
                if(destino.trim().isEmpty() || pessoasValue.trim().isEmpty() || diasValue.trim().isEmpty()){
                    Toast.makeText(NewTravelActivity.this, "Insira todos os dados!", Toast.LENGTH_SHORT).show();
                    return;
                }
                int pessoas = Integer.parseInt(pessoasValue);
                int dias = Integer.parseInt(diasValue);
                if(pessoas <= 0 || dias <= 0){
                    Toast.makeText(NewTravelActivity.this, "Todos os valores precisam ser maiores do que 0.", Toast.LENGTH_SHORT).show();
                }else{
                    SweetAlertDialog pDialog = new SweetAlertDialog(NewTravelActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Aguarde");
                    pDialog.setContentText("Enviando dados ao servidor ...");
                    pDialog.setCancelable(false);
                    if(viagem_id == 0){
                        ViagensModel viagem = new ViagensModel();
                        viagem.setPessoas(pessoas);
                        viagem.setUsuariosModel(usuario);
                        viagem.setAtiva(true);
                        viagem.setDestino(destino);
                        viagem.setDias(dias);
                        viagem.setUsuario(usuario.getId());
                        viagem.setAereo(new GastoAereoModel(viagem, (int) usuario.getId()));
                        viagem.getAereo().setViagem(null);
                        ArrayList<GastoDiversosModel> gastoDiversosModels = new ArrayList<>();
                        gastoDiversosModels.add(new GastoDiversosModel(viagem, (int) usuario_id));
                        viagem.setDiversos(gastoDiversosModels);
                        viagem.setGasolina(new GastoGasolinaModel(viagem, (int) usuario.getId()));
                        viagem.setGastoRefeicoesModel(new GastoRefeicoesModel(viagem, (int) usuario.getId()));
                        viagem.getGastoRefeicoesModel().setViagem(null);
                        viagem.setHospedagem(new GastoHospedagemModel(viagem, (int) usuario.getId()));
                        pDialog.show();

                        API.postViagem(viagem, new Callback<Respostas>() {
                            @Override
                            public void onResponse(Call<Respostas> call, Response<Respostas> response) {
                                pDialog.dismissWithAnimation();
                                if (response != null && response.isSuccessful()) {
                                    Respostas respostas = response.body();
                                    if(respostas.isSucesso()) {
                                        int id = respostas.getDados();

                                        long retorno = new ViagensDAO(NewTravelActivity.this).Insert(viagem, id);

                                        if (retorno == -1) {
                                            new SweetAlertDialog(NewTravelActivity.this, SweetAlertDialog.ERROR_TYPE)
                                                    .setTitleText("Erro")
                                                    .setContentText("Ocorreu um erro ao salvar no banco de dados local")
                                                    .show();
                                        } else {
                                            new SweetAlertDialog(NewTravelActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                                    .setTitleText("Sucesso")
                                                    .setContentText("Viagem inserida com sucesso!")
                                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                        @Override
                                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                            Intent intent = new Intent(NewTravelActivity.this, TravelActivity.class);
                                                            intent.putExtra("travel", retorno);
                                                            startActivity(intent);
                                                            finish();
                                                        }
                                                    })
                                                    .show();
                                        }
                                    }else{
                                        new SweetAlertDialog(NewTravelActivity.this, SweetAlertDialog.ERROR_TYPE)
                                                .setTitleText("Erro")
                                                .setContentText(respostas.getMensagem())
                                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                        sweetAlertDialog.dismissWithAnimation();
                                                    }
                                                })
                                                .show();
                                    }
                                } else {
                                    new SweetAlertDialog(NewTravelActivity.this, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Erro")
                                            .setContentText("Erro ao tentar inserir viagem")
                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    sweetAlertDialog.dismissWithAnimation();
                                                }
                                            })
                                            .show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Respostas> call, Throwable t) {
                                pDialog.dismissWithAnimation();
                                new SweetAlertDialog(NewTravelActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Erro")
                                        .setContentText("Erro ao tentar inserir viagem")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                sweetAlertDialog.dismissWithAnimation();
                                            }
                                        })
                                        .show();
                            }
                        });
                    }else{
                        finalViagem.setPessoas(pessoas);
                        finalViagem.setDias(dias);
                        finalViagem.setDestino(destino);
                        pDialog.show();
                        API.postViagem(finalViagem, new Callback<Respostas>() {
                            @Override
                            public void onResponse(Call<Respostas> call, Response<Respostas> response) {
                                pDialog.dismissWithAnimation();
                                if (response != null && response.isSuccessful()) {
                                    Respostas respostas = response.body();
                                    if (respostas.isSucesso()) {
                                        long retorno = new ViagensDAO(NewTravelActivity.this).Update(finalViagem);
                                        if(retorno == -1){
                                            new SweetAlertDialog(NewTravelActivity.this, SweetAlertDialog.ERROR_TYPE)
                                                    .setTitleText("Erro")
                                                    .setContentText("Erro ao tentar atualizar viagem no banco de dados")
                                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                        @Override
                                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                            sweetAlertDialog.dismissWithAnimation();
                                                        }
                                                    })
                                                    .show();
                                        }else{
                                            new SweetAlertDialog(NewTravelActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                                    .setTitleText("Sucesso")
                                                    .setContentText("Viagem atualizada com sucesso.")
                                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                        @Override
                                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                            sweetAlertDialog.dismissWithAnimation();
                                                            Intent intent = new Intent(NewTravelActivity.this, TravelActivity.class);
                                                            intent.putExtra("travel", finalViagem.getId());
                                                            startActivity(intent);
                                                            finish();
                                                        }
                                                    })
                                                    .show();
                                        }
                                    }else{
                                        new SweetAlertDialog(NewTravelActivity.this, SweetAlertDialog.ERROR_TYPE)
                                                .setTitleText("Erro")
                                                .setContentText(respostas.getMensagem())
                                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                        sweetAlertDialog.dismissWithAnimation();
                                                    }
                                                })
                                                .show();
                                    }
                                } else {
                                    new SweetAlertDialog(NewTravelActivity.this, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Erro")
                                            .setContentText("Erro na api ao tentar atualizar viagem")
                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    sweetAlertDialog.dismissWithAnimation();
                                                }
                                            })
                                            .show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Respostas> call, Throwable t) {
                                pDialog.dismissWithAnimation();
                                new SweetAlertDialog(NewTravelActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Erro")
                                        .setContentText(t.getMessage())
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                sweetAlertDialog.dismissWithAnimation();
                                            }
                                        })
                                        .show();
                            }
                        });

                    }

                }
                
            }
        });
    }
    @Override
    public void onBackPressed() {
        if(viagem_id == 0){
            startActivity(new Intent(NewTravelActivity.this, MainActivity.class));
        }else{
            Intent intent = new Intent(NewTravelActivity.this, TravelActivity.class);
            intent.putExtra("travel", viagem_id);
            startActivity(intent);
        }
        finish();
    }
}