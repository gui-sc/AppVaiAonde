package com.example.vaiaonde;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vaiaonde.database.dao.UsuariosDAO;
import com.example.vaiaonde.database.model.UsuariosModel;

public class RegisterActivity extends AppCompatActivity {
    private EditText txtEmail, txtPassword;
    private TextView registerMsg;
    private Button btnCadastrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        registerMsg = findViewById(R.id.btnLogin);
        btnCadastrar = findViewById(R.id.btnCadastrar);

        registerMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();
                if(email.trim().isEmpty() || email.trim().contains(" ") || password.trim().isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Email ou senha inválidos!", Toast.LENGTH_SHORT).show();
                }
                UsuariosModel usuario = new UsuariosModel();
                usuario.setEmail(email);
                usuario.setSenha(password);
                new UsuariosDAO(RegisterActivity.this).Insert(usuario);
            }
        });
    }
}