package com.example.appdocumentaciontransito.tablas;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appdocumentaciontransito.BaseDatos;
import com.example.appdocumentaciontransito.modelo.Multa;
import com.example.appdocumentaciontransito.modelo.TipoMultaEstado;

import java.util.ArrayList;
import java.util.List;

public class TablaMulta {
    private BaseDatos bdSQLite;
    private SQLiteDatabase bd;
    private Cursor fila;
    private Activity contexto;

    public TablaMulta(Activity contexto){
        this.contexto = contexto;
        bdSQLite= new BaseDatos(contexto);
        bd = bdSQLite.getWritableDatabase();
    }

    public void guardar(Multa multa){
        ContentValues registro = new ContentValues();
        //registro.put("id", tipoMulta.getId());
        registro.put("fecha_multa", multa.getFechaMulta());
        registro.put("fecha_limite", multa.getFechaLimite());
        registro.put("monto_multa", multa.getMontoMulta());
        registro.put("municipio", multa.getMunicipio());
        registro.put("status", multa.getStatus());
        registro.put("multa_estado_id", multa.getMultaEstadoId());
        registro.put("curp_propietario", multa.getCurp());

        bd.insert("multa", null, registro);
    }
    public int modificar(Multa multa){
        ContentValues registro = new ContentValues();
        //registro.put("id", tipoMulta.getId());
        registro.put("fecha_multa", multa.getFechaMulta());
        registro.put("fecha_limite", multa.getFechaLimite());
        registro.put("monto_multa", multa.getMontoMulta());
        registro.put("municipio", multa.getMunicipio());
        registro.put("status", multa.getStatus());
        registro.put("multa_estado_id", multa.getMultaEstadoId());
        registro.put("curp_propietario", multa.getCurp());

        return bd.update("multa", registro, "id=" + multa.getId()+"", null);
    }

    public int eliminar(String id) {
        int cant = bd.delete("multa", "id='" + id+"'", null);
        //actualizarCursor();
        return cant;
    }
    public Multa obtenerMulta(String numero){
        fila = bd.rawQuery("SELECT * FROM multa WHERE id='" +numero+"'", null);
        if (fila.moveToFirst()) {
            return generarMulta(fila);
        }
        return null;
    }
    public List<Multa> obtenerMultas(String curp){
        fila = bd.rawQuery("SELECT * FROM multa WHERE status='En deuda' AND curp_propietario='"+curp+"'", null);
        List<Multa> multas = new ArrayList<>();
        if (fila.moveToFirst()) {
            do {
                multas.add(generarMulta(fila));
            } while(fila.moveToNext());
        }
        return multas;
    }

    public Multa generarMulta(Cursor fila){
        Multa multa = new Multa();
        multa.setId(fila.getInt(0));
        multa.setFechaMulta(fila.getString(1));
        multa.setFechaLimite(fila.getString(2));
        multa.setMontoMulta(fila.getString(3));
        multa.setMunicipio(fila.getString(4));
        multa.setStatus(fila.getString(5));
        multa.setMultaEstadoId(fila.getInt(6));
        multa.setCurp(fila.getString(7));
        return multa;
    }
}
