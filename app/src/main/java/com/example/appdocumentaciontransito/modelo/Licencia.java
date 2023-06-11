package com.example.appdocumentaciontransito.modelo;

public class Licencia {
    private String numeroLicencia;
    private String tipo;
    private String fechaEmision;
    private String fechaExpiracion;
    private String estadoEmision;
    private String imagenLicencia;
    private String curpPropietario;
    public Licencia(){super();}
    public Licencia(String numeroLicencia, String tipo, String fechaEmision, String fechaExpiracion, String estadoEmision, String imagenLicencia, String curpPropietario) {
        this.numeroLicencia = numeroLicencia;
        this.tipo = tipo;
        this.fechaEmision = fechaEmision;
        this.fechaExpiracion = fechaExpiracion;
        this.estadoEmision = estadoEmision;
        this.imagenLicencia = imagenLicencia;
        this.curpPropietario = curpPropietario;
    }

    public String getNumeroLicencia() {
        return numeroLicencia;
    }

    public void setNumeroLicencia(String numeroLicencia) {
        this.numeroLicencia = numeroLicencia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(String fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getEstadoEmision() {
        return estadoEmision;
    }

    public void setEstadoEmision(String estadoEmision) {
        this.estadoEmision = estadoEmision;
    }

    public String getImagenLicencia() {
        return imagenLicencia;
    }

    public void setImagenLicencia(String imagenLicencia) {
        this.imagenLicencia = imagenLicencia;
    }

    public String getCurpPropietario() {
        return curpPropietario;
    }

    public void setCurpPropietario(String curpPropietario) {
        this.curpPropietario = curpPropietario;
    }

    @Override
    public String toString() {
        return "Licencia{" +
                "numeroLicencia='" + numeroLicencia + '\'' +
                ", tipo='" + tipo + '\'' +
                ", fechaEmision='" + fechaEmision + '\'' +
                ", fechaExpiracion='" + fechaExpiracion + '\'' +
                ", estadoEmision='" + estadoEmision + '\'' +
                ", imagenLicencia='" + imagenLicencia + '\'' +
                ", curpPropietario='" + curpPropietario + '\'' +
                '}';
    }
}
