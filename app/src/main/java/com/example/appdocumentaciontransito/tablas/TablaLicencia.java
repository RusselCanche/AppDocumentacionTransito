package com.example.appdocumentaciontransito.tablas;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appdocumentaciontransito.BaseDatos;
import com.example.appdocumentaciontransito.modelo.Licencia;

import java.util.ArrayList;
import java.util.List;

public class TablaLicencia {
    private BaseDatos bdSQLite;
    private SQLiteDatabase bd;
    private Cursor fila;
    private Activity contexto;

    public TablaLicencia(Activity contexto){
        this.contexto = contexto;
        bdSQLite= new BaseDatos(contexto);
        bd = bdSQLite.getWritableDatabase();
    }

    public boolean existe(String numero){
        fila = bd.rawQuery("SELECT * FROM licencia WHERE numero_licencia='" +numero+"'", null);
        if (fila.moveToFirst()) {
            return true;
        }
        return false;
    }

    public void guardar(Licencia licencia){
        ContentValues registro = new ContentValues();
        registro.put("numero_licencia", licencia.getNumeroLicencia());
        registro.put("tipo", licencia.getTipo());
        registro.put("fecha_emision", licencia.getFechaEmision());
        registro.put("fecha_expiracion", licencia.getFechaExpiracion());
        registro.put("estado_emision", licencia.getEstadoEmision());
        registro.put("imagen_licencia", licencia.getImagenLicencia());
        registro.put("curp_propietario", licencia.getCurpPropietario());

        bd.insert("licencia", null, registro);
    }

    public int modificar(Licencia licencia){
        ContentValues registro = new ContentValues();
        registro.put("numero_licencia", licencia.getNumeroLicencia());
        registro.put("tipo", licencia.getTipo());
        registro.put("fecha_emision", licencia.getFechaEmision());
        registro.put("fecha_expiracion", licencia.getFechaExpiracion());
        registro.put("estado_emision", licencia.getEstadoEmision());
        registro.put("imagen_licencia", licencia.getImagenLicencia());
        registro.put("curp_propietario", licencia.getCurpPropietario());

        return bd.update("licencia", registro, "numero_licencia='" + licencia.getNumeroLicencia()+"'", null);
    }

    public int eliminar(String numero, String curp) {
        int cant = bd.delete("licencia", "numero_licencia='" + numero+"'", null);
        actualizarCursorPropietario(curp);
        return cant;
    }

    public Licencia obtenerLicencia(String numero){
        fila = bd.rawQuery("SELECT * FROM licencia WHERE numero_licencia='" +numero+"'", null);
        if (fila.moveToFirst()) {
            return generarLicencia(fila);
        }
        return null;
    }

    public List<Licencia> obtenerLicenciasPropietario(String curp){
        fila = bd.rawQuery("SELECT * FROM licencia WHERE curp_propietario = '"+curp+"'", null);
        List<Licencia> licencias = new ArrayList<>();
        if (fila.moveToFirst()) {
            do {
                licencias.add(generarLicencia(fila));
            } while(fila.moveToNext());
        }
        return licencias;
    }

    public Licencia generarLicencia(Cursor fila){
        Licencia licencia = new  Licencia();
        licencia.setNumeroLicencia(fila.getString(0));
        licencia.setTipo(fila.getString(1));
        licencia.setFechaEmision(fila.getString(2));
        licencia.setFechaExpiracion(fila.getString(3));
        licencia.setEstadoEmision(fila.getString(4));
        licencia.setImagenLicencia(fila.getString(5));
        licencia.setCurpPropietario(fila.getString(6));
        return licencia;
    }

    public void actualizarCursorPropietario(String curp) {
        //fila.close();
        fila = bd.rawQuery("SELECT * FROM licencia WHERE curp_propietario = '"+curp+"'", null);
    }
}
