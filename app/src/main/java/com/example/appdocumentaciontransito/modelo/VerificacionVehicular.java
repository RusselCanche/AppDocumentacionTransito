package com.example.appdocumentaciontransito.modelo;

public class VerificacionVehicular {
    private String folio;
    private String tipoCombustible;
    private String resultado;
    private String fechaVerificacion;
    private String fechaProximaVerificacion;
    private String imagenVerificacion;
    private String numeroSerieVehiculo;

    public VerificacionVehicular(){super();}

    public VerificacionVehicular(String folio, String tipoCombustible, String resultado, String fechaVerificacion, String fechaProximaVerificacion, String imagenVerificacion, String numeroSerieVehiculo) {
        this.folio = folio;
        this.tipoCombustible = tipoCombustible;
        this.resultado = resultado;
        this.fechaVerificacion = fechaVerificacion;
        this.fechaProximaVerificacion = fechaProximaVerificacion;
        this.imagenVerificacion = imagenVerificacion;
        this.numeroSerieVehiculo = numeroSerieVehiculo;
    }

    public String getFolio() {
        return folio;
    }

    public String getTipoCombustible() {
        return tipoCombustible;
    }

    public String getResultado() {
        return resultado;
    }

    public String getFechaVerificacion() {
        return fechaVerificacion;
    }

    public String getFechaProximaVerificacion() {
        return fechaProximaVerificacion;
    }

    public String getImagenVerificacion() {
        return imagenVerificacion;
    }

    public String getNumeroSerieVehiculo() {
        return numeroSerieVehiculo;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public void setTipoCombustible(String tipoCombustible) {
        this.tipoCombustible = tipoCombustible;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public void setFechaVerificacion(String fechaVerificacion) {
        this.fechaVerificacion = fechaVerificacion;
    }

    public void setFechaProximaVerificacion(String fechaProximaVerificacion) {
        this.fechaProximaVerificacion = fechaProximaVerificacion;
    }

    public void setImagenVerificacion(String imagenVerificacion) {
        this.imagenVerificacion = imagenVerificacion;
    }

    public void setNumeroSerieVehiculo(String numeroSerieVehiculo) {
        this.numeroSerieVehiculo = numeroSerieVehiculo;
    }

    @Override
    public String toString() {
        return "VerificacionVehicular{" +
                "folio='" + folio + '\'' +
                ", tipoCombustible='" + tipoCombustible + '\'' +
                ", resultado='" + resultado + '\'' +
                ", fechaVerificacion='" + fechaVerificacion + '\'' +
                ", fechaProximaVerificacion='" + fechaProximaVerificacion + '\'' +
                ", imagenVerificacion='" + imagenVerificacion + '\'' +
                ", numeroSerieVehiculo='" + numeroSerieVehiculo + '\'' +
                '}';
    }
}
