package com.example.vaiaonde.model;

public class GastoDiversosModel {

    public static final String TABELA_NOME = "tb_gasto_diversos";
    public static final String
            COLUNA_ID = "_id",
            COLUNA_VIAGEM_ID = "viagem_id",
            COLUNA_DESCRICAO = "descricao",
            COLUNA_VALOR = "valor",
            COLUNA_UTILIZADO = "utilizado";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABELA_NOME +
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
    private ViagensModel viagem;
    private String descricao;
    private double valor;
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

    public void setViagem(ViagensModel viagem_id) {
        this.viagem = viagem_id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
                + "viagem_id: " + this.viagem.getId()
                + "descricao: " + this.descricao
                + "valor: " + this.valor
                + "utilizado: " + this.utilizado;
    }
}
