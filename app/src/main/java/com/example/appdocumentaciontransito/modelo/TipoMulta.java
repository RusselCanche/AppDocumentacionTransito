package com.example.appdocumentaciontransito.modelo;

public class TipoMulta {
    private String id;
    private String tipo;
    public TipoMulta(){super();}
    public TipoMulta(String id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "TipoMulta{" +
                "id='" + id + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
