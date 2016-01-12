package com.example.wvd.practivity.Data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by walterjgsp on 11/01/16.
 */
public class Entities implements Serializable{

    private int id;
    private String nome;
    private String endereco;
    private String site;
    private String telefone;
    private String email;
    private double ratio;
    ArrayList<Integer> atividades;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Integer> getAtividades() {
        return atividades;
    }

    public void setAtividades(ArrayList<Integer> atividades) {
        this.atividades = atividades;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }
}
