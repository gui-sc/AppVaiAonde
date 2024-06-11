package com.example.vaiaonde.database.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class GastoAereoModel implements Serializable {
    private int idConta = 122283;
    public static final String TABELA_NOME = "tb_gasto_aereo";
    public static final String
        COLUNA_ID = "_id",
        COLUNA_VIAGEM_ID = "viagem_id",
        COLUNA_CUSTO_PESSOA = "custo_pessoa",
        COLUNA_ALUGUEL_VEICULO = "aluguel_veiculo",
        COLUNA_UTILIZADO = "utilizado";

    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABELA_NOME +
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
    private double custoPessoa;
    private double custoAluguelVeiculo;
    private int utilizado;
    @Expose(serialize = false)
    private ViagensModel viagem;
    private long viagemId;
    private int Usuario;
    public GastoAereoModel(){}
    public GastoAereoModel(ViagensModel viagem, int usuario) {
        this.id = 0;
        this.custoPessoa = 0;
        this.custoAluguelVeiculo = 0;
        this.utilizado = 0;
        this.viagem = viagem;
        this.viagemId = viagem.getId();
        Usuario = usuario;
    }

    public int getIdConta() {
        return idConta;
    }

    public void setIdConta(int idConta) {
        this.idConta = idConta;
    }

    public double getCustoPessoa() {
        return custoPessoa;
    }

    public void setCustoPessoa(double custoPessoa) {
        this.custoPessoa = custoPessoa;
    }

    public double getCustoAluguelVeiculo() {
        return custoAluguelVeiculo;
    }

    public void setCustoAluguelVeiculo(double custoAluguelVeiculo) {
        this.custoAluguelVeiculo = custoAluguelVeiculo;
    }

    public long getViagemId() {
        return viagemId;
    }

    public void setViagemId(long viagemId) {
        this.viagemId = viagemId;
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

    public double getCusto_pessoa() {
        return custoPessoa;
    }

    public void setCusto_pessoa(double custo_pessoa) {
        this.custoPessoa = custo_pessoa;
    }

    public double getAluguel_veiculo() {
        return custoAluguelVeiculo;
    }

    public void setAluguel_veiculo(double aluguel_veiculo) {
        this.custoAluguelVeiculo = aluguel_veiculo;
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
                "custo por pessoa: " + this.custoPessoa +
                "aluguel de veiculo: " + this.custoAluguelVeiculo +
                "utilizado: " + this.utilizado;
    }

    public double calcularCustoTotal() {
        return (this.custoPessoa * this.viagem.getPessoas()) + this.custoAluguelVeiculo;
    }

}

