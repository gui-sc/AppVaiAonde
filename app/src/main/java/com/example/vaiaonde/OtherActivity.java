package com.example.vaiaonde;

import android.content.Intent;
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
import com.example.vaiaonde.database.dao.GastoDiversosDAO;
import com.example.vaiaonde.database.dao.ViagensDAO;
import com.example.vaiaonde.database.model.GastoDiversosModel;
import com.example.vaiaonde.database.model.ViagensModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class OtherActivity extends AppCompatActivity {
    private GastoDiversosModel selectedGasto;
    private ArrayList<GastoDiversosModel> listGastosDiversos;
    private ListView listGastos;
    private OtherItemAdapter otherItemAdapter;
    private EditText txtDescricao, txtValor;
    private Button btnSalvar, btnApagar, btnLimpar, btnVoltar;
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
            return;
        }
        ViagensModel viagem = new ViagensDAO(OtherActivity.this).selectById(id);
        if(viagem == null){
            startActivity(new Intent(OtherActivity.this, MainActivity.class));
            return;
        }


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
                    long id = new GastoDiversosDAO(OtherActivity.this).Delete(selectedGasto);
                    if(id != -1){
                        listGastosDiversos.remove(selectedGasto);
                        otherItemAdapter.setItems(listGastosDiversos);
                        listGastos.setAdapter(otherItemAdapter);
                        Toast.makeText(OtherActivity.this, "Removido!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(OtherActivity.this, "Ocorreu um erro!", Toast.LENGTH_SHORT).show();
                    }
                    txtDescricao.setText("");
                    txtValor.setText("");
                    selectedGasto = null;
                }
            }
        });
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String custo = txtValor.getText().toString();
                custo = custo.replace(",",".");
                if(custo.endsWith(".") || custo.isEmpty()){
                    custo += "0";
                }
                long id = -1;
                if(selectedGasto == null){
                    GastoDiversosModel gasto = new GastoDiversosModel();
                    gasto.setViagem(viagem);
                    gasto.setDescricao(txtDescricao.getText().toString());

                    gasto.setValor(Double.parseDouble(custo));
                    id = new GastoDiversosDAO(OtherActivity.this).Insert(gasto);
                    if(id != -1){
                        Toast.makeText(OtherActivity.this, "Inserido com sucesso!", Toast.LENGTH_SHORT).show();
                        listGastosDiversos.add(gasto);
                        otherItemAdapter.setItems(listGastosDiversos);
                        listGastos.setAdapter(otherItemAdapter);
                    }
                }else{
                    int position = listGastosDiversos.indexOf(selectedGasto);
                    selectedGasto.setDescricao(txtDescricao.getText().toString());
                    selectedGasto.setValor(Double.parseDouble(custo));
                    id = new GastoDiversosDAO(OtherActivity.this).Update(selectedGasto);
                    if(id != -1){
                        Toast.makeText(OtherActivity.this, "Alterado com sucesso!", Toast.LENGTH_SHORT).show();
                        listGastosDiversos.set(position, selectedGasto);
                        otherItemAdapter.setItems(listGastosDiversos);
                        listGastos.setAdapter(otherItemAdapter);
                    }
                }
                if(id == -1){
                    Toast.makeText(OtherActivity.this, "Ocorreu um erro!", Toast.LENGTH_SHORT).show();
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
            }
        });
        listGastosDiversos = new GastoDiversosDAO(OtherActivity.this).SelectAll(viagem);
        otherItemAdapter = new OtherItemAdapter(OtherActivity.this, OtherActivity.this, this::setSelectedGasto);
        otherItemAdapter.setItems(listGastosDiversos);
        listGastos.setAdapter(otherItemAdapter);
    }
    GastoDiversosModel setSelectedGasto(GastoDiversosModel gasto) {
        selectedGasto = gasto;
        DecimalFormat decimalFormat = new DecimalFormat(".##");
        txtDescricao.setText(gasto.getDescricao());
        txtValor.setText(decimalFormat.format(gasto.getValor()));

        return gasto;
    }
}