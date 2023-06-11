package com.example.appdocumentaciontransito;

public class Vehiculo {
    private String numeroSerie;
    private String numeroPlacas;
    private String marca;
    private String modelo;
    private String anio;
    private String tipo;
    private String numeroMotor;
    private String curpPropietario;
    public Vehiculo(){super();}
    public Vehiculo(String numeroSerie, String numeroPlacas, String marca, String modelo, String anio, String tipo, String numeroMotor, String curpPropietario) {
        this.numeroSerie = numeroSerie;
        this.numeroPlacas = numeroPlacas;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.tipo = tipo;
        this.numeroMotor = numeroMotor;
        this.curpPropietario = curpPropietario;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getNumeroPlacas() {
        return numeroPlacas;
    }

    public void setNumeroPlacas(String numeroPlacas) {
        this.numeroPlacas = numeroPlacas;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNumeroMotor() {
        return numeroMotor;
    }

    public void setNumeroMotor(String numeroMotor) {
        this.numeroMotor = numeroMotor;
    }

    public String getCurpPropietario() {
        return curpPropietario;
    }

    public void setCurpPropietario(String curpPropietario) {
        this.curpPropietario = curpPropietario;
    }

    @Override
    public String toString() {
        return "Vehiculo{" +
                "numeroSerie='" + numeroSerie + '\'' +
                ", numeroPlacas='" + numeroPlacas + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", anio='" + anio + '\'' +
                ", tipo='" + tipo + '\'' +
                ", numeroMotor='" + numeroMotor + '\'' +
                ", curpPropietario='" + curpPropietario + '\'' +
                '}';
    }
}
