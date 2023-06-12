package com.example.appdocumentaciontransito.modelo;

public class TipoMultaEstado {
    private int id;
    private String monto;
    private String tipoMulta;
    private String descripcion;
    private String estado;
    public TipoMultaEstado(){super();}
    public TipoMultaEstado(int id, String monto, String tipoMultaId, String descripcion, String estadoId) {
        this.id = id;
        this.monto = monto;
        this.tipoMulta = tipoMultaId;
        this.descripcion = descripcion;
        this.estado = estadoId;
    }

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getTipoMulta() {
        return tipoMulta;
    }

    public void setTipoMulta(String tipoMultaId) {
        this.tipoMulta = tipoMultaId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estadoId) {
        this.estado = estadoId;
    }

    public String getDescripcion() { return descripcion; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion;}

    @Override
    public String toString() {
        return tipoMulta;
    }
}
