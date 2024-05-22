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
        long viagem_id = getIntent().getLongExtra("travel", 0);

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
        ViagensModel viagem = null;
        if(viagem_id != 0){
            viagem = new ViagensDAO(NewTravelActivity.this).selectById(viagem_id);
            txtDestino.setText(viagem.getDestino());
            txtPessoas.setText(String.valueOf(viagem.getPessoas()));
            txtDias.setText(String.valueOf(viagem.getDias()));
        }
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewTravelActivity.this, MainActivity.class));
                finish();
            }
        });

        ViagensModel finalViagem = viagem;
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
                    if(viagem_id == 0){
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
                            Intent intent = new Intent(NewTravelActivity.this, TravelActivity.class);
                            intent.putExtra("travel", retorno);
                            startActivity(intent);
                            finish();
                        }
                    }else{
                        finalViagem.setPessoas(pessoas);
                        finalViagem.setDias(dias);
                        finalViagem.setDestino(destino);
                        long retorno = new ViagensDAO(NewTravelActivity.this).Update(finalViagem);

                        if(retorno == -1){
                            Toast.makeText(NewTravelActivity.this, "Ocorreu um erro!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(NewTravelActivity.this, "Viagem atualizada com sucesso!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(NewTravelActivity.this, TravelActivity.class);
                            intent.putExtra("travel", retorno);
                            startActivity(intent);
                            finish();
                        }
                    }

                }
                
            }
        });
    }
}