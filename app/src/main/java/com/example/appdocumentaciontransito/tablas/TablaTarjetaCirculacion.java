package com.example.appdocumentaciontransito.tablas;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appdocumentaciontransito.BaseDatos;
import com.example.appdocumentaciontransito.modelo.TarjetaCirculacion;

public class TablaTarjetaCirculacion {
    private BaseDatos bdSQLite;
    private SQLiteDatabase bd;
    private Cursor fila;
    private Activity contexto;

    public TablaTarjetaCirculacion(Activity contexto) {
        this.contexto = contexto;
        bdSQLite = new BaseDatos(contexto);
        bd = bdSQLite.getWritableDatabase();
    }

    public boolean existe(String numeroTarjeta){
        fila = bd.rawQuery("SELECT * FROM tarjeta_circulacion WHERE numero_serie_tarjeta='" +numeroTarjeta+"'", null);
        if (fila.moveToFirst()) {
            return true;
        }
        return false;
    }

    public void registrar(TarjetaCirculacion tarjetaCirculacion){
        ContentValues registro = new ContentValues();
        registro.put("numero_serie_tarjeta", tarjetaCirculacion.getNumeroSerieTarjeta());
        registro.put("fecha_emision", tarjetaCirculacion.getFechaEmision());
        registro.put("fecha_expiracion", tarjetaCirculacion.getFechaExpiracion());
        registro.put("estado_emision", tarjetaCirculacion.getEstadoEmision());
        registro.put("imagen_tarjeta", tarjetaCirculacion.getImagenTarjeta());
        registro.put("numero_serie_vehiculo", tarjetaCirculacion.getNumeroSerieVehiculo());
        bd.insert("tarjeta_circulacion", null, registro);
    }

    public int modificar(TarjetaCirculacion tarjetaCirculacion){
        ContentValues registro = new ContentValues();
        registro.put("numero_serie_tarjeta", tarjetaCirculacion.getNumeroSerieTarjeta());
        registro.put("fecha_emision", tarjetaCirculacion.getFechaEmision());
        registro.put("fecha_expiracion", tarjetaCirculacion.getFechaExpiracion());
        registro.put("estado_emision", tarjetaCirculacion.getEstadoEmision());
        registro.put("imagen_tarjeta", tarjetaCirculacion.getImagenTarjeta());
        registro.put("numero_serie_vehiculo", tarjetaCirculacion.getNumeroSerieVehiculo());
        return bd.update("tarjeta_circulacion", registro, "numero_serie_tarjeta='" + tarjetaCirculacion.getNumeroSerieTarjeta() +"'", null);
    }

    public int eliminar(String numero) {
        int cant = bd.delete("tarjeta_circulacion", "numero_serie_vehiculo='" + numero+"'", null);
        return cant;
    }

    public TarjetaCirculacion generarTarjetaCirculacion(Cursor fila) {
        TarjetaCirculacion tarjetaCirculacion = new TarjetaCirculacion();
        tarjetaCirculacion.setNumeroSerieTarjeta(fila.getString(0));
        tarjetaCirculacion.setFechaEmision(fila.getString(1));
        tarjetaCirculacion.setFechaExpiracion(fila.getString(2));
        tarjetaCirculacion.setEstadoEmision(fila.getString(3));
        tarjetaCirculacion.setImagenTarjeta(fila.getString(4));
        tarjetaCirculacion.setNumeroSerieVehiculo(fila.getString(5));
        return tarjetaCirculacion;
    }

    public TarjetaCirculacion getTarjetaCirculacion(String numeroSerieVehiculo){
        TarjetaCirculacion tarjetaCirculacion = new TarjetaCirculacion();
        Cursor fila = bd.rawQuery("select * from tarjeta_circulacion where numero_serie_vehiculo = '"+numeroSerieVehiculo+"'",null);
        if(fila.moveToFirst()){
            tarjetaCirculacion = generarTarjetaCirculacion(fila);
        }
        return tarjetaCirculacion;
    }
}
