package com.example.vaiaonde;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vaiaonde.adapter.OtherItemAdapter;
import com.example.vaiaonde.api.API;
import com.example.vaiaonde.api.ViagemCallback;
import com.example.vaiaonde.database.dao.GastoAereoDAO;
import com.example.vaiaonde.database.dao.GastoDiversosDAO;
import com.example.vaiaonde.database.dao.GastoGasolinaDAO;
import com.example.vaiaonde.database.dao.GastoHospedagemDAO;
import com.example.vaiaonde.database.dao.GastoRefeicoesDAO;
import com.example.vaiaonde.database.dao.ViagensDAO;
import com.example.vaiaonde.database.model.GastoAereoModel;
import com.example.vaiaonde.database.model.GastoDiversosModel;
import com.example.vaiaonde.database.model.GastoGasolinaModel;
import com.example.vaiaonde.database.model.GastoHospedagemModel;
import com.example.vaiaonde.database.model.GastoRefeicoesModel;
import com.example.vaiaonde.database.model.ViagensModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResumeActivity extends AppCompatActivity {
    private TextView txtDestino, txtDias, txtPessoas,
            txtRefeicoes, txtAerea, txtHospedagem, txtGasolina, txtOutros, txtTotal, txtTotalPessoa;
    private TextView lblRefeicoes, lblAerea, lblHospedagem, lblGasolina, lblOutros;
    private Button btnApagar, btnVoltar;
    private ViagensModel viagem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);
        txtDestino = findViewById(R.id.txtDestino);
        txtDias = findViewById(R.id.txtDias);
        txtPessoas = findViewById(R.id.txtPessoas);

        txtRefeicoes = findViewById(R.id.txtRefeicoes);
        txtAerea = findViewById(R.id.txtAerea);
        txtHospedagem = findViewById(R.id.txtHospedagem);
        txtGasolina = findViewById(R.id.txtGasolina);
        txtOutros = findViewById(R.id.txtOutros);
        txtTotal = findViewById(R.id.txtTotal);
        txtTotalPessoa = findViewById(R.id.txtTotalPorPessoa);

        lblRefeicoes = findViewById(R.id.lblRefeicoes);
        lblAerea = findViewById(R.id.lblAerea);
        lblHospedagem = findViewById(R.id.lblHospedagem);
        lblGasolina = findViewById(R.id.lblGasolina);
        lblOutros = findViewById(R.id.lblOutros);
        btnApagar = findViewById(R.id.btnApagar);
        btnVoltar = findViewById(R.id.btnVoltar);
        
        long id = getIntent().getLongExtra("travel", 0);
        if(id == 0) {
            startActivity(new Intent(ResumeActivity.this, MainActivity.class));
            finish();
            return;
        }

        SweetAlertDialog pDialogSearch = new SweetAlertDialog(ResumeActivity.this, SweetAlertDialog.PROGRESS_TYPE);
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
                txtDias.setText(String.valueOf(viagem.getDias()));
                txtPessoas.setText(String.valueOf(viagem.getPessoas()));
                GastoHospedagemModel gastoHospedagem = viagem.getHospedagem();
                GastoGasolinaModel gastoGasolina = viagem.getGasolina();
                GastoRefeicoesModel gastoRefeicoes = viagem.getRefeicao();
                gastoRefeicoes.setViagem(viagem);
                GastoAereoModel gastoAereo = viagem.getAereo();
                gastoAereo.setViagem(viagem);
                ArrayList<GastoDiversosModel> gastoDiversos = viagem.getDiversos();
                DecimalFormat decimalFormat = new DecimalFormat("0.##");
                setText(txtHospedagem, lblHospedagem, gastoHospedagem.calcularGastoHospedagem(), decimalFormat);
                setText(txtGasolina, lblGasolina, gastoGasolina.calcularCustoTotal(), decimalFormat);
                setText(txtRefeicoes, lblRefeicoes, gastoRefeicoes.calcularCustoTotalRefeicoes(), decimalFormat);
                setText(txtAerea, lblAerea, gastoAereo.calcularCustoTotal(), decimalFormat);
                double totalDiversos = 0;
                for (int i = 0; i < gastoDiversos.size(); i++) {
                    totalDiversos += gastoDiversos.get(i).getValor();
                }

                setText(txtOutros, lblOutros, totalDiversos, decimalFormat);
                double total = gastoHospedagem.calcularGastoHospedagem() +
                        gastoGasolina.calcularCustoTotal() +
                        gastoRefeicoes.calcularCustoTotalRefeicoes() +
                        gastoAereo.calcularCustoTotal() +
                        totalDiversos;

                String formatted = decimalFormat.format(total);
                formatted = formatted.replace(".",",");
                if(formatted.split(",").length == 1){
                    formatted += ",00";
                }
                if(formatted.split(",")[1].length() == 1){
                    formatted += "0";
                }
                txtTotal.setText(formatted);
                double totalPessoa = total / Double.parseDouble(String.valueOf(viagem.getPessoas()));
                formatted = decimalFormat.format(totalPessoa);
                formatted = formatted.replace(".",",");
                if(formatted.split(",").length == 1){
                    formatted += ",00";
                }
                if(formatted.split(",")[1].length() == 1){
                    formatted += "0";
                }
                txtTotalPessoa.setText(formatted);
                pDialogSearch.dismissWithAnimation();
            }

            @Override
            public void onFailure(Throwable t) {
                pDialogSearch.dismissWithAnimation();
                new SweetAlertDialog(ResumeActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Erro")
                        .setContentText("Erro ao buscar dados do banco de dados, tente novamente mais tarde.")
                        .setContentText("OK")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {

                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                startActivity(new Intent(ResumeActivity.this, MainActivity.class));
                                finish();
                            }
                        })
                        .show();
            }
        });
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viagem.getAtiva()){
                    Intent intent = new Intent(ResumeActivity.this, TravelActivity.class);
                    intent.putExtra("travel", viagem.getId());
                    startActivity(intent);
                }else{
                    startActivity(new Intent(ResumeActivity.this, MainActivity.class));
                }
                finish();
            }
        });
        btnApagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long id = new ViagensDAO(ResumeActivity.this).Delete(viagem);

                if(id == -1){
                    Toast.makeText(ResumeActivity.this, "Ocorreu um erro!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ResumeActivity.this, "Viagem apagada!", Toast.LENGTH_SHORT).show();
                }

                startActivity(new Intent(ResumeActivity.this, MainActivity.class));
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
                    int isActive = new ViagensDAO(ResumeActivity.this).isActive(viagem.getId());
                    viagem.setAtiva(isActive == 1);
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
    
    private void setText(TextView txt, TextView lbl, double valor, DecimalFormat decimalFormat){
        if(valor == 0){
            txt.setVisibility(View.GONE);
            lbl.setVisibility(View.GONE);
        }else{
            String formatted = decimalFormat.format(valor);
            formatted = formatted.replace(".",",");
            if(formatted.split(",").length == 1){
                formatted += ",00";
            }
            if(formatted.split(",")[1].length() == 1){
                formatted += "0";
            }
            txt.setText(formatted);
        }
    }

    @Override
    public void onBackPressed() {
        if(viagem.getAtiva()){
            Intent intent = new Intent(ResumeActivity.this, TravelActivity.class);
            intent.putExtra("travel", viagem.getId());
            startActivity(intent);
        }else{
            startActivity(new Intent(ResumeActivity.this, MainActivity.class));
        }
        finish();
    }
}