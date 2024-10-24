package org.acme.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "fita")
public class Fita {

    @Id
    @GeneratedValue
    public long id;
    public String nome;
    public String ano;
    public String genero;

    public Fita(String nome, String ano, String genero) {
        this.nome = nome;
        this.ano = ano;
        this.genero = genero;
    }

    public Fita() {
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setId(long id) {
        this.id = id;
    }
}
