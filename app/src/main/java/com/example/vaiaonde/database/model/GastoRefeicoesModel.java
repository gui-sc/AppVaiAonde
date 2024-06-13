package com.example.vaiaonde.database.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class GastoRefeicoesModel implements Serializable {

    private int idConta = 122283;
    public static final String TABELA_NOME = "tb_gasto_refeicoes";
    public static final String
            COLUNA_ID = "_id",
            COLUNA_VIAGEM_ID = "viagem_id",
            COLUNA_CUSTO_REFEICAO = "custo_refeicao",
            COLUNA_REFEICOES_DIA = "refeicoes_dia",
            COLUNA_UTILIZADO = "utilizado";

    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABELA_NOME +
                    " ( "
                    + COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUNA_VIAGEM_ID + " INTEGER NOT NULL, "
                    + COLUNA_CUSTO_REFEICAO + " REAL NOT NULL, "
                    + COLUNA_REFEICOES_DIA + " INTEGER NOT NULL, "
                    + COLUNA_UTILIZADO + " INTEGER DEFAULT 0, "
                    + " FOREIGN KEY (" + COLUNA_VIAGEM_ID + ") REFERENCES " + ViagensModel.TABELA_NOME + "(_id) "
                    + " );";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABELA_NOME;

    private long id;
    @Expose(serialize = false)
    private ViagensModel viagem;
    private long viagemId;
    private double custoRefeicao;
    private int refeicoesDia;
    private int utilizado;
    private int usuario;

    public GastoRefeicoesModel(){}

    public GastoRefeicoesModel(ViagensModel viagem, int usuario) {
        this.id = 0;
        this.viagem = viagem;
        this.viagemId = viagem.getId();
        this.custoRefeicao = 0;
        this.refeicoesDia = 0;
        this.utilizado = 1;
        this.usuario = usuario;
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

    public double getCustoRefeicao() {
        return custoRefeicao;
    }

    public void setCustoRefeicao(double custoRefeicao) {
        this.custoRefeicao = custoRefeicao;
    }

    public int getRefeicoesDia() {
        return refeicoesDia;
    }

    public void setRefeicoesDia(int refeicoesDia) {
        this.refeicoesDia = refeicoesDia;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ViagensModel getViagem() {
        return viagem;
    }

    public void setViagem(ViagensModel viagem) {
        this.viagem = viagem;
    }

    public double getCusto_refeicao() {
        return custoRefeicao;
    }

    public void setCusto_refeicao(double custo_refeicao) {
        this.custoRefeicao = custo_refeicao;
    }

    public int getRefeicoes_dia() {
        return refeicoesDia;
    }

    public void setRefeicoes_dia(int refeicoes_dia) {
        this.refeicoesDia = refeicoes_dia;
    }

    public int getUtilizado() {
        return utilizado;
    }

    public void setUtilizado(int utilizado) {
        this.utilizado = utilizado;
    }

    @Override
    public String toString() {
        return "tabela: " + TABELA_NOME
                + "id: " + this.id
                + "viagem_id: " + this.viagem.getId()
                + "custo por refeicao: " + this.custoRefeicao
                + "refeicoes por dia: " + this.refeicoesDia
                + "utilizado: " + this.utilizado;
    }

    public double calcularParcial(){
        return this.refeicoesDia * this.custoRefeicao;
    }

    public double calcularCustoTotalRefeicoes() {
        return ((this.refeicoesDia * this.viagem.getPessoas()) * this.custoRefeicao) * this.viagem.getDias();
    }
}
