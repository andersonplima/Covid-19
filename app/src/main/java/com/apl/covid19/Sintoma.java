package com.apl.covid19;

import java.io.Serializable;

public class Sintoma implements Serializable {
    private String id;
    private String nome;
    private String telefone;
    private String sintomas;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSintomas() {
        return sintomas;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    @Override
    public String toString() {
        return String.format("%s%4$s✆ %s%4$s%s", nome, telefone, sintomas, System.lineSeparator());
    }
}
