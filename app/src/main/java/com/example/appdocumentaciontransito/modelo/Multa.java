package com.example.appdocumentaciontransito.modelo;

public class Multa {
    private int id;
    private String fechaMulta;
    private String fechaLimite;
    private String montoMulta;
    private String municipio;
    private String status;
    private int multaEstadoId;
    private String curp;

    public Multa(){super();}
    public Multa(int id, String fechaMulta, String fechaLimite, String montoMulta, String municipio, String status, int multaEstadoId) {
        this.id = id;
        this.fechaMulta = fechaMulta;
        this.fechaLimite = fechaLimite;
        this.montoMulta = montoMulta;
        this.municipio = municipio;
        this.status = status;
        this.multaEstadoId = multaEstadoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFechaMulta() {
        return fechaMulta;
    }

    public void setFechaMulta(String fechaMulta) {
        this.fechaMulta = fechaMulta;
    }

    public String getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(String fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public String getMontoMulta() {
        return montoMulta;
    }

    public void setMontoMulta(String montoMulta) {
        this.montoMulta = montoMulta;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getMultaEstadoId() {
        return multaEstadoId;
    }

    public void setMultaEstadoId(int multaEstadoId) {
        this.multaEstadoId = multaEstadoId;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    @Override
    public String toString() {
        return "Multa{" +
                "id='" + id + '\'' +
                ", fechaMulta='" + fechaMulta + '\'' +
                ", fechaLimite='" + fechaLimite + '\'' +
                ", montoMulta='" + montoMulta + '\'' +
                ", municipio='" + municipio + '\'' +
                ", status='" + status + '\'' +
                ", multaEstadoId='" + multaEstadoId + '\'' +
                '}';
    }
}
