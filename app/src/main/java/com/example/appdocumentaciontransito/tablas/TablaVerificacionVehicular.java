package com.example.appdocumentaciontransito.tablas;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appdocumentaciontransito.BaseDatos;
import com.example.appdocumentaciontransito.modelo.Vehiculo;
import com.example.appdocumentaciontransito.modelo.VerificacionVehicular;

public class TablaVerificacionVehicular {
    private BaseDatos bdSQLite;
    private SQLiteDatabase bd;
    private Cursor fila;
    private Activity contexto;

    public TablaVerificacionVehicular(Activity contexto) {
        this.contexto = contexto;
        bdSQLite = new BaseDatos(contexto);
        bd = bdSQLite.getWritableDatabase();
    }

    public boolean existe(String numeroSerie) {
        fila = bd.rawQuery("SELECT * FROM verificacion_vehicular WHERE numero_serie_vehiculo='" + numeroSerie + "'", null);
        if (fila.moveToFirst()) {
            return true;
        }
        return false;
    }

    public void guardar(VerificacionVehicular verificacion) {
        ContentValues registro = new ContentValues();
        registro.put("numero_verificacion", verificacion.getFolio());
        registro.put("tipo_combustible", verificacion.getTipoCombustible());
        registro.put("resultado", verificacion.getResultado());
        registro.put("fecha_verificacion", verificacion.getFechaVerificacion());
        registro.put("fecha_proxima_verificacion", verificacion.getFechaProximaVerificacion());
        registro.put("certificado_verificacion", verificacion.getImagenVerificacion());
        registro.put("numero_serie_vehiculo", verificacion.getNumeroSerieVehiculo());

        bd.insert("verificacion_vehicular", null, registro);
    }

    public int modificar(VerificacionVehicular verificacion) {
        ContentValues registro = new ContentValues();
        registro.put("numero_verificacion", verificacion.getFolio());
        registro.put("tipo_combustible", verificacion.getTipoCombustible());
        registro.put("resultado", verificacion.getResultado());
        registro.put("fecha_verificacion", verificacion.getFechaVerificacion());
        registro.put("fecha_proxima_verificacion", verificacion.getFechaProximaVerificacion());
        registro.put("certificado_verificacion", verificacion.getImagenVerificacion());
        registro.put("numero_serie_vehiculo", verificacion.getNumeroSerieVehiculo());

        return bd.update("verificacion_vehicular", registro, "numero_verificacion='" + verificacion.getFolio() + "'", null);
    }

    public int eliminar(String numero) {
        int cant = bd.delete("verificacion_vehicular", "numero_serie_vehiculo='" + numero + "'", null);
        return cant;
    }

    public VerificacionVehicular obtenerVerificacion(String numero) {
        fila = bd.rawQuery("SELECT * FROM verificacion_vehicular WHERE numero_serie_vehiculo='" + numero + "'", null);
        if (fila.moveToFirst()) {
            return generarVerificacion(fila);
        }
        return null;
    }

    public VerificacionVehicular generarVerificacion(Cursor fila){
        VerificacionVehicular verificacion = new VerificacionVehicular();
        verificacion.setFolio(fila.getString(0));
        verificacion.setTipoCombustible(fila.getString(1));
        verificacion.setResultado(fila.getString(2));
        verificacion.setFechaVerificacion(fila.getString(3));
        verificacion.setFechaProximaVerificacion(fila.getString(4));
        verificacion.setImagenVerificacion(fila.getString(5));
        verificacion.setNumeroSerieVehiculo(fila.getString(6));
        return verificacion;
    }
}
