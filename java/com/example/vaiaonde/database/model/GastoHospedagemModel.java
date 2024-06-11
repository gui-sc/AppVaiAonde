package com.example.vaiaonde.database.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class GastoHospedagemModel implements Serializable {

    private int idConta = 122283;
    public static final String TABELA_NOME = "tb_gasto_hospedagem";
    public static final String
            COLUNA_ID = "_id",
            COLUNA_VIAGEM_ID = "viagem_id",
            COLUNA_CUSTO_NOITE = "custo_noite",
            COLUNA_NOITES = "noites",
            COLUNA_QUARTOS = "quartos",
            COLUNA_UTILIZADO = "utilizado";

    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABELA_NOME +
                    " ( "
                    + COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUNA_VIAGEM_ID + " INTEGER NOT NULL, "
                    + COLUNA_CUSTO_NOITE + " REAL NOT NULL, "
                    + COLUNA_NOITES + " INTEGER NOT NULL, "
                    + COLUNA_QUARTOS + " INTEGER NOT NULL, "
                    + COLUNA_UTILIZADO + " INTEGER DEFAULT 0, "
                    + " FOREIGN KEY (" + COLUNA_VIAGEM_ID + ") REFERENCES " + ViagensModel.TABELA_NOME + "(_id) "
                    + " );";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABELA_NOME;

    private long id;
    private long viagemId;
    private double custoMedioNoite;
    private int totalNoite;
    private int totalQuartos;
    private int utilizado;
    private int Usuario;

    public GastoHospedagemModel(){}

    public GastoHospedagemModel(ViagensModel viagem, int usuario) {
        this.id = 0;
        this.viagemId = viagem.getId();
        this.custoMedioNoite = 0;
        this.totalNoite = 0;
        this.totalQuartos = 0;
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

    public double getCustoMedioNoite() {
        return custoMedioNoite;
    }

    public void setCustoMedioNoite(double custoMedioNoite) {
        this.custoMedioNoite = custoMedioNoite;
    }

    public int getTotalNoite() {
        return totalNoite;
    }

    public void setTotalNoite(int totalNoite) {
        this.totalNoite = totalNoite;
    }

    public int getTotalQuartos() {
        return totalQuartos;
    }

    public void setTotalQuartos(int totalQuartos) {
        this.totalQuartos = totalQuartos;
    }

    public int getUsuario() {
        return Usuario;
    }

    public void setUsuario(int usuario) {
        Usuario = usuario;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getCusto_noite() {
        return custoMedioNoite;
    }

    public void setCusto_noite(double custo_noite) {
        this.custoMedioNoite = custo_noite;
    }

    public int getNoites() {
        return totalNoite;
    }

    public void setNoites(int noites) {
        this.totalNoite = noites;
    }

    public int getQuartos() {
        return totalQuartos;
    }

    public void setQuartos(int quartos) {
        this.totalQuartos = quartos;
    }

    public int getUtilizado() {
        return utilizado;
    }

    public void setUtilizado(int utilizado) {
        this.utilizado = utilizado;
    }

    public String toString(){
        return "tabela: " + TABELA_NOME
                + "id: " + this.id
                + "custo_noite: " + this.custoMedioNoite
                + "noites: " + this.totalNoite
                + "quartos: " + this.totalQuartos
                + "utilizado: " + this.utilizado;
    }

    public double calcularGastoHospedagem(){
        return (this.custoMedioNoite * this.totalNoite) * this.totalQuartos;
    }

}
