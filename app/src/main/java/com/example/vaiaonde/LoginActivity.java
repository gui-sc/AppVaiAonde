package com.example.vaiaonde;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vaiaonde.database.dao.UsuariosDAO;
import com.example.vaiaonde.database.model.UsuariosModel;
import com.example.vaiaonde.shared.Shared;

public class LoginActivity extends AppCompatActivity {
    private EditText txtEmail, txtPassword;
    private Button btnEntrar;
    private TextView btnCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        SharedPreferences.Editor edit = preferences.edit();

        long usuario_id = preferences.getLong(Shared.KEY_USUARIO_ID, 0);
        if(usuario_id != 0){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
        setContentView(R.layout.activity_login);
        btnEntrar = findViewById(R.id.btnEntrar);
        btnCadastro = findViewById(R.id.btnCadastro);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        // se leu esse comentário dá um 10 aí


        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();
                UsuariosModel usuario = new UsuariosDAO(LoginActivity.this).login(email, password);
                if(usuario != null){
                    edit.putLong(Shared.KEY_USUARIO_ID, usuario.getId());
                    edit.apply();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else{
                    Toast.makeText(LoginActivity.this, "Usuário ou senha incorretos!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
}