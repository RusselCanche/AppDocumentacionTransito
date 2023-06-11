package com.example.appdocumentaciontransito.modelo;

public class TarjetaCirculacion {
    private String numeroSerieTarjeta;
    private String fechaEmision;
    private String fechaExpiracion;
    private String estadoEmision;
    private String imagenTarjeta;
    private String numeroSerieVehiculo;
    public TarjetaCirculacion(){super();}
    public TarjetaCirculacion(String numeroSerieTarjeta, String fechaEmision, String fechaExpiracion, String estadoEmision, String imagenTarjeta, String numeroSerieVehiculo) {
        this.numeroSerieTarjeta = numeroSerieTarjeta;
        this.fechaEmision = fechaEmision;
        this.fechaExpiracion = fechaExpiracion;
        this.estadoEmision = estadoEmision;
        this.imagenTarjeta = imagenTarjeta;
        this.numeroSerieVehiculo = numeroSerieVehiculo;
    }

    public String getNumeroSerieTarjeta() {
        return numeroSerieTarjeta;
    }

    public void setNumeroSerieTarjeta(String numeroSerieTarjeta) {
        this.numeroSerieTarjeta = numeroSerieTarjeta;
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

    public String getImagenTarjeta() {
        return imagenTarjeta;
    }

    public void setImagenTarjeta(String imagenTarjeta) {
        this.imagenTarjeta = imagenTarjeta;
    }

    public String getNumeroSerieVehiculo() {
        return numeroSerieVehiculo;
    }

    public void setNumeroSerieVehiculo(String numeroSerieVehiculo) {
        this.numeroSerieVehiculo = numeroSerieVehiculo;
    }

    @Override
    public String toString() {
        return "TarjetaCirculacion{" +
                "numeroSerieTarjeta='" + numeroSerieTarjeta + '\'' +
                ", fechaEmision='" + fechaEmision + '\'' +
                ", fechaExpiracion='" + fechaExpiracion + '\'' +
                ", estadoEmision='" + estadoEmision + '\'' +
                ", imagenTarjeta='" + imagenTarjeta + '\'' +
                ", numeroSerieVehiculo='" + numeroSerieVehiculo + '\'' +
                '}';
    }
}
