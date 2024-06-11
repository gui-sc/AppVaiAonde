package com.example.vaiaonde.api.response;

import java.io.Serializable;

public class Respostas implements Serializable {

    private boolean sucesso;
    private String mensagem;
    private String chavePrimaria;
    private int dados;

    public boolean isSucesso() {
        return sucesso;
    }

    public void setSucesso(boolean sucesso) {
        this.sucesso = sucesso;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getChavePrimaria() {
        return chavePrimaria;
    }

    public void setChavePrimaria(String chavePrimaria) {
        this.chavePrimaria = chavePrimaria;
    }

    public int getDados(){return dados;}

    public void setDados(int dados){this.dados = dados;}
}
