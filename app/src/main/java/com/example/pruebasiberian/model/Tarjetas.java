package com.example.pruebasiberian.model;

public class Tarjetas {
    private String numero;
    private String tipo;

    public Tarjetas() {
        this.numero = "";
        this.tipo = "";
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
