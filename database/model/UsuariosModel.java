package com.example.bancodedados.database.model;

import androidx.annotation.NonNull;

public class UsuariosModel {

    public static final String TABELA_NOME = "tb_usuarios";
    public static final String
        COLUNA_ID = "_id",
        COLUNA_EMAIL = "email",
        COLUNA_SENHA = "senha";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABELA_NOME +
                    " ( "
                    + COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUNA_EMAIL + " TEXT NOT NULL, "
                    + COLUNA_SENHA + " TEXT NOT NULL "
                    + " );";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABELA_NOME;

    private long id;
    private String email;
    private String senha;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public static String getTabelaNome() {
        return TABELA_NOME;
    }

    @Override
    public String toString() {
        return "tabela: " + this.TABELA_NOME +
                "id: " + this.id +
                "email: " + this.email +
                "senha: " + this.senha;
    }

}
