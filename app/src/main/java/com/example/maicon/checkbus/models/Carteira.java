package com.example.maicon.checkbus.models;

/**
 * Created by maico on 12/10/2017.
 */

public class Carteira {
    public String nome;
    public Integer rg;
    public String curso;
    public String sala;
    public Integer telefone;
    public Integer deter;
    public Integer fretamento;
    public Integer situacao;

    public Carteira() {
    }

    public Carteira(String nome, Integer rg, String curso, String sala, Integer telefone, Integer deter, Integer fretamento) {
        this.nome = nome;
        this.rg = rg;
        this.curso = curso;
        this.sala = sala;
        this.telefone = telefone;
        this.deter = deter;
        this.fretamento = fretamento;
    }

    public Carteira(String nome, Integer rg, String curso, Integer telefone) {
        this.nome = nome;
        this.rg = rg;
        this.curso = curso;
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getRg() {
        return rg;
    }

    public void setRg(Integer rg) {
        this.rg = rg;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public Integer getTelefone() {
        return telefone;
    }

    public void setTelefone(Integer telefone) {
        this.telefone = telefone;
    }

    public Integer getDeter() {
        return deter;
    }

    public void setDeter(Integer deter) {
        this.deter = deter;
    }

    public Integer getFretamento() {
        return fretamento;
    }

    public void setFretamento(Integer fretamento) {
        this.fretamento = fretamento;
    }

    public Integer getSituacao() { return situacao; }

    public void setSituacao(Integer situacao) { this.situacao = situacao; }
}
