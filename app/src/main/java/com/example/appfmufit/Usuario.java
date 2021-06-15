package com.example.appfmufit;

import java.io.Serializable;
import java.sql.NClob;
import java.util.Date;


public class Usuario implements Serializable {

    private String nome, rg, email, senha, sexo, dt_nascimento, cel, tel_com, cpf;
    private int cd_usuario;

    public Usuario(){

    }

    public Usuario(String nome, String rg, String email, String senha, String sexo, String dt_nascimento, String cel, String tel_com, String cpf, int cd_usuario) {
        this.nome = nome;
        this.rg = rg;
        this.email = email;
        this.senha = senha;
        this.sexo = sexo;
        this.dt_nascimento = dt_nascimento;
        this.cel = cel;
        this.tel_com = tel_com;
        this.cpf = cpf;
        this.cd_usuario = cd_usuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDt_nascimento() {
        return dt_nascimento;
    }

    public void setDt_nascimento(String dt_nascimento) {
        this.dt_nascimento = dt_nascimento;
    }

    public String getCel() {
        return cel;
    }

    public void setCel(String cel) {
        this.cel = cel;
    }

    public String getTel_com() {
        return tel_com;
    }

    public void setTel_com(String tel_com) {
        this.tel_com = tel_com;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getCd_usuario() {
        return cd_usuario;
    }

    public void setCd_usuario(int cd_usuario) {
        this.cd_usuario = cd_usuario;
    }
}
