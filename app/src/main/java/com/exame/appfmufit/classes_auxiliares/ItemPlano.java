package com.exame.appfmufit.classes_auxiliares;

public class ItemPlano {

    private int imagem_id;
    private String titulo;
    private String descricao;

    public ItemPlano(int imagem_id, String titulo, String descricao){
        this.imagem_id = imagem_id;
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public int getImagem_id() {
        return imagem_id;
    }

    public void setImagem_id(int imagem_id) {
        this.imagem_id = imagem_id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
