package com.example.appdocumentaciontransito.tablas;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appdocumentaciontransito.BaseDatos;
import com.example.appdocumentaciontransito.modelo.TipoMultaEstado;
import com.example.appdocumentaciontransito.modelo.Vehiculo;

import java.util.ArrayList;
import java.util.List;

public class TablaTipoMulta {
    private BaseDatos bdSQLite;
    private SQLiteDatabase bd;
    private Cursor fila;
    private Activity contexto;

    public TablaTipoMulta(Activity contexto){
        this.contexto = contexto;
        bdSQLite= new BaseDatos(contexto);
        bd = bdSQLite.getWritableDatabase();
    }

    public void guardar(TipoMultaEstado tipoMulta){
        ContentValues registro = new ContentValues();
        //registro.put("id", tipoMulta.getId());
        registro.put("monto", tipoMulta.getMonto());
        registro.put("tipo_multa", tipoMulta.getTipoMulta());
        registro.put("descripcion", tipoMulta.getDescripcion());
        registro.put("estado", tipoMulta.getEstado());

        bd.insert("tipo_multa_estado", null, registro);
    }

    public List<TipoMultaEstado> obtenerTiposMulta(){
        fila = bd.rawQuery("SELECT * FROM tipo_multa_estado", null);
        List<TipoMultaEstado> tiposMulta = new ArrayList<>();
        if (fila.moveToFirst()) {
            do {
                tiposMulta.add(generarTipoMulta(fila));
            } while(fila.moveToNext());
        }
        return tiposMulta;
    }

    public TipoMultaEstado generarTipoMulta(Cursor fila){
        TipoMultaEstado tipoMulta = new TipoMultaEstado();
        tipoMulta.setId(fila.getInt(0));
        tipoMulta.setMonto(fila.getString(1));
        tipoMulta.setTipoMulta(fila.getString(2));
        tipoMulta.setDescripcion(fila.getString(3));
        tipoMulta.setEstado(fila.getString(4));
        return tipoMulta;
    }
}
