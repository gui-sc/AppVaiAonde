package com.example.vaiaonde.model;

public class GastoRefeicoesModel {
    public static final String TABELA_NOME = "tb_gasto_refeicoes";
    public static final String
            COLUNA_ID = "_id",
            COLUNA_VIAGEM_ID = "viagem_id",
            COLUNA_CUSTO_REFEICAO = "custo_refeicao",
            COLUNA_REFEICOES_DIA = "refeicoes_dia",
            COLUNA_UTILIZADO = "utilizado";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABELA_NOME +
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
    private ViagensModel viagem;
    private double custo_refeicao;
    private int refeicoes_dia;
    private int utilizado;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ViagensModel getViagem() {
        return viagem;
    }

    public void setViagem_id(ViagensModel viagem) {
        this.viagem = viagem;
    }

    public double getCusto_refeicao() {
        return custo_refeicao;
    }

    public void setCusto_refeicao(double custo_refeicao) {
        this.custo_refeicao = custo_refeicao;
    }

    public int getRefeicoes_dia() {
        return refeicoes_dia;
    }

    public void setRefeicoes_dia(int refeicoes_dia) {
        this.refeicoes_dia = refeicoes_dia;
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
                + "custo por refeicao: " + this.custo_refeicao
                + "refeicoes por dia: " + this.refeicoes_dia
                + "utilizado: " + this.utilizado;
    }

    public double calcularParcial(){
        return this.refeicoes_dia * this.custo_refeicao;
    }

    public double calcularCustoTotalRefeicoes() {
        return ((this.refeicoes_dia * this.viagem.getPessoas()) * this.custo_refeicao) * this.viagem.getDias();
    }
}
