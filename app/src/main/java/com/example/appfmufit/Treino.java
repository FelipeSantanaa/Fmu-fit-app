package com.example.appfmufit;

public class Treino {

    private String data, hora, ds_modalidade;
    private Integer cd_treino, cd_modalidade, cd_usuario;

    public Treino(Integer cd_treino, Integer cd_usuario, Integer cd_modalidade, String data, String hora, String ds_modalidade) {
        this.cd_treino = cd_treino;
        this.cd_usuario = cd_usuario;
        this.cd_modalidade = cd_modalidade;
        this.data = data;
        this.hora = hora;
        this.ds_modalidade = ds_modalidade;
    }

    public Treino (){};

    public Integer getCd_treino() {
        return cd_treino;
    }

    public void setCd_treino(Integer cd_treino) {
        this.cd_treino = cd_treino;
    }

    public Integer getCd_usuario() {
        return cd_usuario;
    }

    public void setCd_usuario(Integer cd_usuario) {
        this.cd_usuario = cd_usuario;
    }

    public Integer getCd_modalidade() {
        return cd_modalidade;
    }

    public void setCd_modalidade(Integer cd_modalidade) {
        this.cd_modalidade = cd_modalidade;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setDs_modalidade(String ds_modalidade){
        this.ds_modalidade = ds_modalidade;
    }

    public String getDs_modalidade(){
        return this.ds_modalidade;
    }
}
