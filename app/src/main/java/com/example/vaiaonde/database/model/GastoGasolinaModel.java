package com.example.vaiaonde.database.model;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class GastoGasolinaModel implements Serializable {

    private int idConta = 122283;
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
            "CREATE TABLE IF NOT EXISTS " + TABELA_NOME +
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
    @Nullable
    private Long id;
    private int totalEstimadoKM;
    private double mediaKMLitro;
    private double custoMedioLitro;
    private int totalVeiculos;
    private int utilizado;
    private long viagemId;
    private int usuario;

    public GastoGasolinaModel(){}
    public GastoGasolinaModel(ViagensModel viagem, int usuario) {
        this.totalEstimadoKM = 0;
        this.mediaKMLitro = 0;
        this.custoMedioLitro = 0;
        this.totalVeiculos = 0;
        this.utilizado = 1;
        this.viagemId = viagem.getId();
        this.usuario = usuario;
    }

    public int getIdConta() {
        return idConta;
    }

    public void setIdConta(int idConta) {
        this.idConta = idConta;
    }

    public int getTotalEstimadoKM() {
        return totalEstimadoKM;
    }

    public void setTotalEstimadoKM(int totalEstimadoKM) {
        this.totalEstimadoKM = totalEstimadoKM;
    }

    public double getMediaKMLitro() {
        return mediaKMLitro;
    }

    public void setMediaKMLitro(double mediaKMLitro) {
        this.mediaKMLitro = mediaKMLitro;
    }

    public double getCustoMedioLitro() {
        return custoMedioLitro;
    }

    public void setCustoMedioLitro(double custoMedioLitro) {
        this.custoMedioLitro = custoMedioLitro;
    }

    public int getTotalVeiculos() {
        return totalVeiculos;
    }

    public void setTotalVeiculos(int totalVeiculos) {
        this.totalVeiculos = totalVeiculos;
    }

    public long getViagemId() {
        return viagemId;
    }

    public void setViagemId(long viagemId) {
        this.viagemId = viagemId;
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

    public void setId(@Nullable Long id) {
        this.id = id;
    }

    public int getKm() {
        return totalEstimadoKM;
    }

    public void setKm(int km) {
        this.totalEstimadoKM = km;
    }

    public double getKm_litro() {
        return mediaKMLitro;
    }

    public void setKm_litro(double km_litro) {
        this.mediaKMLitro = km_litro;
    }

    public double getCusto_litro() {
        return custoMedioLitro;
    }

    public void setCusto_litro(double custo_litro) {
        this.custoMedioLitro = custo_litro;
    }

    public int getTotal_veiculos() {
        return totalVeiculos;
    }

    public void setTotal_veiculos(int total_veiculos) {
        this.totalVeiculos = total_veiculos;
    }

    public int getUtilizado() {
        return utilizado;
    }

    public void setUtilizado(int utilizado) {
        this.utilizado = utilizado;
    }

    public String toString() {
        return "tabela: " + this.TABELA_NOME +
                "id: " + this.id +
                "km: " + this.totalEstimadoKM +
                "km por litro: " + this.mediaKMLitro +
                "custo do litro: " + this.custoMedioLitro +
                "total de veiculos: " + this.totalVeiculos +
                "utilizado: " + this.utilizado;
    }

    public double calcularCustoTotal() {
        System.out.println(Double.parseDouble(String.valueOf(this.totalEstimadoKM)));
        System.out.println(Double.parseDouble(String.valueOf(this.mediaKMLitro)));
        System.out.println(Double.parseDouble(String.valueOf(this.totalVeiculos)));
        System.out.println(this.custoMedioLitro);
        if(this.mediaKMLitro == 0){
            return 0;
        }
        return (Double.parseDouble(String.valueOf(this.totalEstimadoKM)) / Double.parseDouble(String.valueOf(this.mediaKMLitro))) * this.custoMedioLitro * Double.parseDouble(String.valueOf(this.totalVeiculos));
    }
}