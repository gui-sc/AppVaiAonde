package com.example.vaiaonde.model;

public class GastoGasolinaModel {

    public static final String TABELA_NOME = "tb_gasto_gasolina";
    public static final String
        COLUNA_ID = "_id",
        COLUNA_VIAGEM_ID = "viagem_id",
        COLUNA_KM = "km",
        COLUNA_KM_LITRO = "km_litro",
        COLUNA_CUSTO_LITRO = "custo_litro",
        COLUNA_TOTAL_VEICULOS = "total_veiculos",
        COLUNA_UTILIZADO = "utilizado";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABELA_NOME +
                    " ( "
                    + COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUNA_KM + " INTEGER NOT NULL, "
                    + COLUNA_KM_LITRO + " INTEGER NOT NULL, "
                    + COLUNA_CUSTO_LITRO + " REAL NOT NULL, "
                    + COLUNA_TOTAL_VEICULOS + " INTEGER NOT NULL, "
                    + COLUNA_UTILIZADO + " INTEGER DEFAULT 0, "
                    + COLUNA_VIAGEM_ID + " INTEGER NOT NULL, "
                    + " FOREIGN KEY (" + COLUNA_VIAGEM_ID + ") REFERENCES " + ViagensModel.TABELA_NOME + "(_id) "
                    + " );";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABELA_NOME;

    private long id;
    private int km;
    private int km_litro;
    private double custo_litro;
    private int total_veiculos;
    private int utilizado;
    private long viagem_id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

    public int getKm_litro() {
        return km_litro;
    }

    public void setKm_litro(int km_litro) {
        this.km_litro = km_litro;
    }

    public double getCusto_litro() {
        return custo_litro;
    }

    public void setCusto_litro(double custo_litro) {
        this.custo_litro = custo_litro;
    }

    public int getTotal_veiculos() {
        return total_veiculos;
    }

    public void setTotal_veiculos(int total_veiculos) {
        this.total_veiculos = total_veiculos;
    }

    public int getUtilizado() {
        return utilizado;
    }

    public void setUtilizado(int utilizado) {
        this.utilizado = utilizado;
    }

    public long getViagem_id() {
        return viagem_id;
    }

    public void setViagem_id(long viagem_id) {
        this.viagem_id = viagem_id;
    }

    public String toString() {
        return "tabela: " + this.TABELA_NOME +
                "id: " + this.id +
                "km: " + this.km +
                "km por litro: " + this.km_litro +
                "custo do litro: " + this.custo_litro +
                "total de veiculos: " + this.total_veiculos +
                "utilizado: " + this.utilizado +
                "viagem_id: " + this.viagem_id;
    }
}