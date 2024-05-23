package com.example.vaiaonde;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
        viagem = new ViagensDAO(ResumeActivity.this).selectById(id);
        if(viagem == null){
            startActivity(new Intent(ResumeActivity.this, MainActivity.class));
            finish();
            return;
        }
        txtDestino.setText(viagem.getDestino());
        txtDias.setText(String.valueOf(viagem.getDias()));
        txtPessoas.setText(String.valueOf(viagem.getPessoas()));
        GastoHospedagemModel gastoHospedagem = new GastoHospedagemDAO(ResumeActivity.this).SelectByViagem(viagem);
        GastoGasolinaModel gastoGasolina = new GastoGasolinaDAO(ResumeActivity.this).SelectByViagem(viagem);
        GastoRefeicoesModel gastoRefeicoes = new GastoRefeicoesDAO(ResumeActivity.this).SelectByViagem(viagem);
        GastoAereoModel gastoAereo = new GastoAereoDAO(ResumeActivity.this).SelectByViagem(viagem);
        ArrayList<GastoDiversosModel> gastoDiversos = new GastoDiversosDAO(ResumeActivity.this).SelectAll(viagem);

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