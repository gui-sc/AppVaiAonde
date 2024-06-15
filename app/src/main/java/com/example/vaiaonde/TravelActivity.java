package com.example.vaiaonde;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vaiaonde.api.API;
import com.example.vaiaonde.api.ViagemCallback;
import com.example.vaiaonde.database.dao.ViagensDAO;
import com.example.vaiaonde.database.model.GastoAereoModel;
import com.example.vaiaonde.database.model.GastoDiversosModel;
import com.example.vaiaonde.database.model.ViagensModel;

import java.util.function.Function;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TravelActivity extends AppCompatActivity {

    private TextView txtDestino;
    private Button btnEncerrar, btnRefeicao, btnHospedagem, btnGasolina,
            btnAviao, btnOutros, btnApagar, btnVoltar, btnEditar, btnResumo;
    private GastoDiversosModel selectedGasto;
    private ViagensModel viagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set components
        setContentView(R.layout.activity_travel);
        txtDestino = findViewById(R.id.txtDestino);
        btnEncerrar = findViewById(R.id.btnEncerrar);
        btnRefeicao = findViewById(R.id.btnRefeicao);
        btnHospedagem = findViewById(R.id.btnHospedagem);
        btnGasolina = findViewById(R.id.btnGasolina);
        btnAviao = findViewById(R.id.btnAerea);
        btnOutros = findViewById(R.id.btnOutros);
        btnApagar = findViewById(R.id.btnApagar);
        btnVoltar = findViewById(R.id.btnVoltar);
        btnEditar = findViewById(R.id.btnEditarViagem);
        btnResumo = findViewById(R.id.btnResumo);
        //get extras
        long id = getIntent().getLongExtra("travel", 0);

        if(id == 0){
            startActivity(new Intent(TravelActivity.this, MainActivity.class));
            finish();
            return;
        }

        SweetAlertDialog pDialogSearch = new SweetAlertDialog(TravelActivity.this, SweetAlertDialog.PROGRESS_TYPE);
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

                txtDestino.setText(viagem.getDestino());
                pDialogSearch.dismissWithAnimation();
            }

            @Override
            public void onFailure(Throwable t) {
                pDialogSearch.dismissWithAnimation();
                new SweetAlertDialog(TravelActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Erro")
                        .setContentText("Erro ao buscar dados do banco de dados, tente novamente mais tarde.")
                        .setContentText("OK")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {

                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                startActivity(new Intent(TravelActivity.this, MainActivity.class));
                                finish();
                            }
                        })
                        .show();
            }
        });

        btnRefeicao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TravelActivity.this, MealActivity.class);
                intent.putExtra("travel", viagem.getId());
                startActivity(intent);
                finish();
            }
        });

        btnHospedagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TravelActivity.this, HostActivity.class);
                intent.putExtra("travel", viagem.getId());
                startActivity(intent);
                finish();
            }
        });

        btnGasolina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TravelActivity.this, FuelActivity.class);
                intent.putExtra("travel", viagem.getId());
                startActivity(intent);
                finish();
            }
        });
        btnAviao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TravelActivity.this, PlaneActivity.class);
                intent.putExtra("travel", viagem.getId());
                startActivity(intent);
                finish();
            }
        });

        btnOutros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TravelActivity.this, OtherActivity.class);
                intent.putExtra("travel", viagem.getId());
                startActivity(intent);
                finish();
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TravelActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnResumo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TravelActivity.this, ResumeActivity.class);
                intent.putExtra("travel", viagem.getId());
                startActivity(intent);
                finish();
            }
        });
        btnApagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long id = new ViagensDAO(TravelActivity.this).Delete(viagem);

                if(id == -1){
                    Toast.makeText(TravelActivity.this, "Ocorreu um erro!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(TravelActivity.this, "Viagem apagada!", Toast.LENGTH_SHORT).show();
                }

                startActivity(new Intent(TravelActivity.this, MainActivity.class));
                finish();
            }
        });

        btnEncerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viagem.setAtiva(false);
                long rowsAffected = new ViagensDAO(TravelActivity.this).Update(viagem);

                if(rowsAffected == -1){
                    Toast.makeText(TravelActivity.this, "Ocorreu um erro!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(TravelActivity.this, "Viagem encerrada!", Toast.LENGTH_SHORT).show();
                }

                startActivity(new Intent(TravelActivity.this, MainActivity.class));
                finish();
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TravelActivity.this, NewTravelActivity.class);
                intent.putExtra("travel", viagem.getId());
                startActivity(intent);
                finish();
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
        Intent intent = new Intent(TravelActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}