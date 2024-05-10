package com.example.vaiaonde.database.model;

public class GastoHospedagemModel {

    public static final String TABELA_NOME = "tb_gasto_hospedagem";
    public static final String
            COLUNA_ID = "_id",
            COLUNA_VIAGEM_ID = "viagem_id",
            COLUNA_CUSTO_NOITE = "custo_noite",
            COLUNA_NOITES = "noites",
            COLUNA_QUARTOS = "quartos",
            COLUNA_UTILIZADO = "utilizado";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABELA_NOME +
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
    private ViagensModel viagem;
    private double custo_noite;
    private int noites;
    private int quartos;
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

    public double getCusto_noite() {
        return custo_noite;
    }

    public void setCusto_noite(double custo_noite) {
        this.custo_noite = custo_noite;
    }

    public int getNoites() {
        return noites;
    }

    public void setNoites(int noites) {
        this.noites = noites;
    }

    public int getQuartos() {
        return quartos;
    }

    public void setQuartos(int quartos) {
        this.quartos = quartos;
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
                + "viagem_id: " + this.viagem.getId()
                + "custo_noite: " + this.custo_noite
                + "noites: " + this.noites
                + "quartos: " + this.quartos
                + "utilizado: " + this.utilizado;
    }

    public double calcularGastoHospedagem(){
        return (this.custo_noite * this.noites) * this.quartos;
    }

}
