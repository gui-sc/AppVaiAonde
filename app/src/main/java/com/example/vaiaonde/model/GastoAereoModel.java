package com.example.vaiaonde.model;

import com.example.vaiaonde.model.ViagensModel;

public class GastoAereoModel {
    public static final String TABELA_NOME = "tb_gasto_aereo";
    public static final String
        COLUNA_ID = "_id",
        COLUNA_VIAGEM_ID = "viagem_id",
        COLUNA_CUSTO_PESSOA = "custo_pessoa",
        COLUNA_ALUGUEL_VEICULO = "aluguel_veiculo",
        COLUNA_UTILIZADO = "utilizado";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABELA_NOME +
                    " ( "
                    + COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUNA_CUSTO_PESSOA + " REAL NOT NULL, "
                    + COLUNA_ALUGUEL_VEICULO + " REAL NOT NULL, "
                    + COLUNA_UTILIZADO + " INTEGER DEFAULT 0, "
                    + COLUNA_VIAGEM_ID + " INTEGER NOT NULL, "
                    + " FOREIGN KEY (" + COLUNA_VIAGEM_ID + ") REFERENCES " + ViagensModel.TABELA_NOME + "(_id) "
                    + " );";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABELA_NOME;

    private long id;
    private double custo_pessoa;
    private double aluguel_veiculo;
    private int utilizado;
    private ViagensModel viagem;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getCusto_pessoa() {
        return custo_pessoa;
    }

    public void setCusto_pessoa(double custo_pessoa) {
        this.custo_pessoa = custo_pessoa;
    }

    public double getAluguel_veiculo() {
        return aluguel_veiculo;
    }

    public void setAluguel_veiculo(double aluguel_veiculo) {
        this.aluguel_veiculo = aluguel_veiculo;
    }

    public int getUtilizado() {
        return utilizado;
    }

    public void setUtilizado(int utilizado) {
        this.utilizado = utilizado;
    }

    public ViagensModel getViagem() {
        return viagem;
    }

    public void setViagem(ViagensModel viagem) {
        this.viagem = viagem;
    }

    @Override
    public String toString() {
        return "tabela: " + this.TABELA_NOME +
                "id: " + this.id +
                "viagem_id: " + this.viagem.getId() +
                "custo por pessoa: " + this.custo_pessoa +
                "aluguel de veiculo: " + this.aluguel_veiculo +
                "utilizado: " + this.utilizado;
    }

    public double calcularCustoTotal() {
        return (this.custo_pessoa * this.viagem.getPessoas()) + this.aluguel_veiculo;
    }

}

