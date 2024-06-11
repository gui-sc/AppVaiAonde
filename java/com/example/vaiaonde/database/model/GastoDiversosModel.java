package com.example.vaiaonde.database.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class GastoDiversosModel implements Serializable {

    private int idConta = 122283;
    public static final String TABELA_NOME = "tb_gasto_diversos";
    public static final String
            COLUNA_ID = "_id",
            COLUNA_VIAGEM_ID = "viagem_id",
            COLUNA_DESCRICAO = "descricao",
            COLUNA_VALOR = "valor",
            COLUNA_UTILIZADO = "utilizado";

    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABELA_NOME +
                    " ( "
                    + COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUNA_VIAGEM_ID + " INTEGER NOT NULL, "
                    + COLUNA_DESCRICAO + " TEXT NOT NULL, "
                    + COLUNA_VALOR + " REAL NOT NULL, "
                    + COLUNA_UTILIZADO + " INTEGER DEFAULT 0, "
                    + "FOREIGN KEY (" + COLUNA_VIAGEM_ID + ") REFERENCES " + ViagensModel.TABELA_NOME + "(_id) "
                    + " );";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABELA_NOME;

    private long id;
    private long viagemId;
    private String entretenimento;
    private double valor;
    private int utilizado;
    private long Usuario;

    public GastoDiversosModel(){}

    public GastoDiversosModel(ViagensModel viagem, long usuario) {
        this.id = 0;
        this.viagemId = viagem.getId();
        this.entretenimento = "";
        this.valor = 0;
        this.utilizado = 1;
        Usuario = usuario;
    }

    public int getIdConta() {
        return idConta;
    }

    public void setIdConta(int idConta) {
        this.idConta = idConta;
    }

    public long getViagemId() {
        return viagemId;
    }

    public void setViagemId(long viagemId) {
        this.viagemId = viagemId;
    }

    public String getEntretenimento() {
        return entretenimento;
    }

    public void setEntretenimento(String entretenimento) {
        this.entretenimento = entretenimento;
    }

    public long getUsuario() {
        return Usuario;
    }

    public void setUsuario(long usuario) {
        Usuario = usuario;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getDescricao() {
        return entretenimento;
    }

    public void setDescricao(String descricao) {
        this.entretenimento = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getUtilizado() {
        return utilizado;
    }

    public void setUtilizado(int utilizado) {
        this.utilizado = utilizado;
    }

    @Override
    public String toString(){
        return "tabela: " + TABELA_NOME
                + "id: " + this.id
                + "descricao: " + this.entretenimento
                + "valor: " + this.valor
                + "utilizado: " + this.utilizado;
    }
}
