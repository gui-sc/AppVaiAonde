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

public class RegisterActivity extends AppCompatActivity {
    private EditText txtEmail, txtPassword;
    private TextView registerMsg;
    private Button btnCadastrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(RegisterActivity.this);
        SharedPreferences.Editor edit = preferences.edit();

        long usuario_id = preferences.getLong(Shared.KEY_USUARIO_ID, 0);
        if(usuario_id != 0){
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            finish();
            return;
        }
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        registerMsg = findViewById(R.id.btnLogin);
        btnCadastrar = findViewById(R.id.btnCadastrar);

        registerMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();

                if(email.trim().isEmpty()  || password.trim().isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Email e senha não podem ser vazios!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(email.contains(" ")){
                    Toast.makeText(RegisterActivity.this, "Email inválido!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(new UsuariosDAO(RegisterActivity.this).UserExists(email)){
                    Toast.makeText(RegisterActivity.this, "Já existe um usuário com este email!", Toast.LENGTH_SHORT).show();
                }
                if(password.length() < 6){
                    Toast.makeText(RegisterActivity.this, "A senha precisa conter pelo menos 6 caracteres.", Toast.LENGTH_SHORT).show();
                    return;
                }
                UsuariosModel usuario = new UsuariosModel();
                usuario.setEmail(email);
                usuario.setSenha(password);
                long id = new UsuariosDAO(RegisterActivity.this).Insert(usuario);
                if(id == -1){
                    Toast.makeText(RegisterActivity.this, "Ocorreu um erro!", Toast.LENGTH_SHORT).show();
                }else{
                    edit.putLong(Shared.KEY_USUARIO_ID, id);
                    edit.apply();
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    finish();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}