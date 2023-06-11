package com.example.appdocumentaciontransito.modelo;

public class TipoMultaEstado {
    private String id;
    private String monto;
    private String tipoMultaId;
    private String estadoId;
    public TipoMultaEstado(){super();}
    public TipoMultaEstado(String id, String monto, String tipoMultaId, String estadoId) {
        this.id = id;
        this.monto = monto;
        this.tipoMultaId = tipoMultaId;
        this.estadoId = estadoId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getTipoMultaId() {
        return tipoMultaId;
    }

    public void setTipoMultaId(String tipoMultaId) {
        this.tipoMultaId = tipoMultaId;
    }

    public String getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(String estadoId) {
        this.estadoId = estadoId;
    }

    @Override
    public String toString() {
        return "TipoMultaEstado{" +
                "id='" + id + '\'' +
                ", monto='" + monto + '\'' +
                ", tipoMultaId='" + tipoMultaId + '\'' +
                ", estadoId='" + estadoId + '\'' +
                '}';
    }
}
