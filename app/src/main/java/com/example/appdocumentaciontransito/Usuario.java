package com.example.appdocumentaciontransito;

public class Usuario {
    private String username;
    private String contrasenia;
    private String curpPropietario;

    public Usuario() {super();}
    public Usuario(String username, String contrasenia, String curpPropietario) {
        this.username = username;
        this.contrasenia = contrasenia;
        this.curpPropietario = curpPropietario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getCurpPropietario() {
        return curpPropietario;
    }

    public void setCurpPropietario(String curpPropietario) {
        this.curpPropietario = curpPropietario;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "username='" + username + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", curpPropietario='" + curpPropietario + '\'' +
                '}';
    }
}
