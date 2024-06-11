package com.example.vaiaonde.database.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ViagensModel implements Serializable {
    private int idConta = 122283;
    public static final String TABELA_NOME = "tb_viagens";
    public static final String
        COLUNA_ID = "_id",
        COLUNA_DIAS = "dias",
        COLUNA_PESSOAS = "pessoas",
        COLUNA_DESTINO = "destino",
        COLUNA_ATIVA = "ativa",
        COLUNA_USUARIO_ID = "usuario_id";

    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABELA_NOME +
                    " ( "
                    + COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUNA_DIAS + " INTEGER NOT NULL, "
                    + COLUNA_PESSOAS + " INTEGER NOT NULL, "
                    + COLUNA_DESTINO + " TEXT NOT NULL, "
                    + COLUNA_ATIVA + " INTEGER DEFAULT 0, "
                    + COLUNA_USUARIO_ID + " INTEGER NOT NULL, "
                    + " FOREIGN KEY (" + COLUNA_USUARIO_ID + ") REFERENCES " + UsuariosModel.TABELA_NOME + "(_id) "
                    + " );";

    public static  final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABELA_NOME;

    private long id;
    private int duracaoViagem;
    private int totalViajantes;
    private String local;
    private boolean ativa;
    private UsuariosModel usuario;
    private long Usuario;
    private GastoAereoModel aereo;
    private ArrayList<GastoDiversosModel> listaEntretenimento;
    private GastoGasolinaModel gasolina;
    private GastoHospedagemModel hospedagem;
    private GastoRefeicoesModel refeicao;
    private double custoTotalViagem;
    private double custoPorPessoa;

    public int getIdConta() {
        return idConta;
    }

    public void setIdConta(int idConta) {
        this.idConta = idConta;
    }

    public int getDuracaoViagem() {
        return duracaoViagem;
    }

    public void setDuracaoViagem(int duracaoViagem) {
        this.duracaoViagem = duracaoViagem;
    }

    public int getTotalViajantes() {
        return totalViajantes;
    }

    public void setTotalViajantes(int totalViajantes) {
        this.totalViajantes = totalViajantes;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public void setUsuario(long usuario) {
        Usuario = usuario;
    }

    public ArrayList<GastoDiversosModel> getListaEntretenimento() {
        return listaEntretenimento;
    }

    public void setListaEntretenimento(ArrayList<GastoDiversosModel> listaEntretenimento) {
        this.listaEntretenimento = listaEntretenimento;
    }

    public GastoRefeicoesModel getRefeicao() {
        return refeicao;
    }

    public void setRefeicao(GastoRefeicoesModel refeicao) {
        this.refeicao = refeicao;
    }

    public double getCustoTotalViagem() {
        return custoTotalViagem;
    }

    public void setCustoTotalViagem(double custoTotalViagem) {
        this.custoTotalViagem = custoTotalViagem;
    }

    public double getCustoPorPessoa() {
        return custoPorPessoa;
    }

    public void setCustoPorPessoa(double custoPorPessoa) {
        this.custoPorPessoa = custoPorPessoa;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public GastoAereoModel getAereo() {
        return aereo;
    }

    public void setAereo(GastoAereoModel aereo) {
        this.aereo = aereo;
    }

    public ArrayList<GastoDiversosModel> getDiversos() {
        return listaEntretenimento;
    }

    public void setDiversos(ArrayList<GastoDiversosModel> diversos) {
        this.listaEntretenimento = diversos;
    }

    public GastoGasolinaModel getGasolina() {
        return gasolina;
    }

    public void setGasolina(GastoGasolinaModel gasolina) {
        this.gasolina = gasolina;
    }

    public GastoHospedagemModel getHospedagem() {
        return hospedagem;
    }

    public void setHospedagem(GastoHospedagemModel hospedagem) {
        this.hospedagem = hospedagem;
    }

    public GastoRefeicoesModel getGastoRefeicoesModel() {
        return refeicao;
    }

    public void setGastoRefeicoesModel(GastoRefeicoesModel gastoRefeicoesModel) {
        this.refeicao = gastoRefeicoesModel;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDias() {
        return duracaoViagem;
    }

    public void setDias(int dias) {
        this.duracaoViagem = dias;
    }

    public int getPessoas() {
        return totalViajantes;
    }

    public void setPessoas(int pessoas) {
        this.totalViajantes = pessoas;
    }

    public String getDestino() {
        return local;
    }

    public void setDestino(String destino) {
        this.local = destino;
    }

    public boolean getAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public UsuariosModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuariosModel usuario_id) {
        this.usuario = usuario_id;
    }

    public Double getCustoTotal(){
        double custo = this.refeicao.calcularCustoTotalRefeicoes() + aereo.calcularCustoTotal()
                + gasolina.calcularCustoTotal() + hospedagem.calcularGastoHospedagem();

        for (int i = 0; i < listaEntretenimento.size(); i++) {
            custo += listaEntretenimento.get(i).getValor();
        }

        return custo;
    }
    @Override
    public String toString() {
        return "tabela: " + this.TABELA_NOME +
                "id: " + this.id +
                "dias: " + this.duracaoViagem +
                "pessoas: " + this.totalViajantes +
                "destino: " + this.local +
                "ativa: " + this.ativa +
                "usuario_id: " + this.usuario.getId();
    }

}
