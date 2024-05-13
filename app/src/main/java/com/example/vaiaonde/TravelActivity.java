package com.example.vaiaonde;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vaiaonde.database.dao.ViagensDAO;
import com.example.vaiaonde.database.model.GastoAereoModel;
import com.example.vaiaonde.database.model.GastoDiversosModel;
import com.example.vaiaonde.database.model.ViagensModel;

import java.util.function.Function;

public class TravelActivity extends AppCompatActivity {

    private TextView txtDestino;
    private Button btnEncerrar, btnRefeicao, btnHospedagem, btnGasolina,
            btnAviao, btnOutros, btnApagar, btnVoltar, btnEditar, btnResumo;
    private GastoDiversosModel selectedGasto;

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
        //get extras
        long id = getIntent().getLongExtra("travel", 0);

        if(id == 0){
            startActivity(new Intent(TravelActivity.this, MainActivity.class));
            return;
        }
        ViagensModel viagem = new ViagensDAO(TravelActivity.this).selectById(id);
        if(viagem == null){
            startActivity(new Intent(TravelActivity.this, MainActivity.class));
            return;
        }

        txtDestino.setText(viagem.getDestino());

        btnRefeicao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TravelActivity.this, MealActivity.class);
                intent.putExtra("travel", viagem.getId());
                startActivity(intent);
            }
        });

        btnHospedagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TravelActivity.this, HostActivity.class);
                intent.putExtra("travel", viagem.getId());
                startActivity(intent);
            }
        });

        btnGasolina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TravelActivity.this, FuelActivity.class);
                intent.putExtra("travel", viagem.getId());
                startActivity(intent);
            }
        });
        btnAviao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TravelActivity.this, PlaneActivity.class);
                intent.putExtra("travel", viagem.getId());
                startActivity(intent);
            }
        });

        btnOutros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TravelActivity.this, OtherActivity.class);
                intent.putExtra("travel", viagem.getId());
                startActivity(intent);
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TravelActivity.this, MainActivity.class);
                startActivity(intent);
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
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TravelActivity.this, NewTravelActivity.class);
                intent.putExtra("travel", viagem.getId());
                startActivity(intent);
            }
        });
    }

}