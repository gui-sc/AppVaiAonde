package com.example.vaiaonde;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vaiaonde.database.dao.UsuariosDAO;
import com.example.vaiaonde.database.dao.ViagensDAO;
import com.example.vaiaonde.database.model.UsuariosModel;
import com.example.vaiaonde.database.model.ViagensModel;
import com.example.vaiaonde.shared.Shared;

public class NewTravelActivity extends AppCompatActivity {
    private EditText txtDestino, txtDias, txtPessoas;
    private Button btnSalvar, btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_travel);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(NewTravelActivity.this);
        long usuario_id = preferences.getLong(Shared.KEY_USUARIO_ID, 0);
        
        if(usuario_id == 0){
            startActivity(new Intent(NewTravelActivity.this, LoginActivity.class));
        }
        UsuariosModel usuario = new UsuariosDAO(NewTravelActivity.this).SelectById(usuario_id);
        if(usuario == null){
            startActivity(new Intent(NewTravelActivity.this, LoginActivity.class));
        }
        btnVoltar = findViewById(R.id.btnVoltar);
        btnSalvar = findViewById(R.id.btnSalvar);
        txtDias = findViewById(R.id.txtDias);
        txtPessoas = findViewById(R.id.txtPessoas);
        txtDestino = findViewById(R.id.txtDestino);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewTravelActivity.this, MainActivity.class));
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
                }else{
                    int pessoas = Integer.parseInt(pessoasValue);
                    int dias = Integer.parseInt(diasValue);
                    ViagensModel viagem = new ViagensModel();
                    viagem.setPessoas(pessoas);
                    viagem.setUsuario(usuario);
                    viagem.setAtiva(true);
                    viagem.setDestino(destino);
                    viagem.setDias(dias);
                    long retorno = new ViagensDAO(NewTravelActivity.this).Insert(viagem);
                    if(retorno == -1){
                        Toast.makeText(NewTravelActivity.this, "Erro ao cadastrar viagem!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(NewTravelActivity.this, "Viagem inserida com sucesso!", Toast.LENGTH_SHORT).show();
                    }
                    startActivity(new Intent(NewTravelActivity.this, MainActivity.class));
                }
                
            }
        });
    }
}