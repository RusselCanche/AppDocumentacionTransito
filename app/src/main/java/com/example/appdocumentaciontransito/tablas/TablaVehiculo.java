package com.example.appdocumentaciontransito.tablas;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appdocumentaciontransito.BaseDatos;
import com.example.appdocumentaciontransito.modelo.Vehiculo;

import java.util.ArrayList;
import java.util.List;

public class TablaVehiculo {
    private BaseDatos bdSQLite;
    private SQLiteDatabase bd;
    private Cursor fila;
    private Activity contexto;

    public TablaVehiculo(Activity contexto){
        this.contexto = contexto;
        bdSQLite= new BaseDatos(contexto);
        bd = bdSQLite.getWritableDatabase();
    }

    public boolean existe(String numeroSerie){
        fila = bd.rawQuery("SELECT * FROM vehiculo WHERE numero_serie='" +numeroSerie+"'", null);
        if (fila.moveToFirst()) {
            return true;
        }
        return false;
    }

    public void guardar(Vehiculo vehiculo){
        ContentValues registro = new ContentValues();
        registro.put("numero_serie", vehiculo.getNumeroSerie());
        registro.put("numero_placas", vehiculo.getNumeroPlacas());
        registro.put("marca", vehiculo.getMarca());
        registro.put("modelo", vehiculo.getModelo());
        registro.put("anio", vehiculo.getAnio());
        registro.put("tipo", vehiculo.getTipo());
        registro.put("numero_motor", vehiculo.getNumeroMotor());
        registro.put("curp_propietario", vehiculo.getCurpPropietario());

        bd.insert("vehiculo", null, registro);
    }

    public int modificar(Vehiculo vehiculo){
        ContentValues registro = new ContentValues();
        registro.put("numero_serie", vehiculo.getNumeroSerie());
        registro.put("numero_placas", vehiculo.getNumeroPlacas());
        registro.put("marca", vehiculo.getMarca());
        registro.put("modelo", vehiculo.getModelo());
        registro.put("anio", vehiculo.getAnio());
        registro.put("tipo", vehiculo.getTipo());
        registro.put("numero_motor", vehiculo.getNumeroMotor());
        registro.put("curp_propietario", vehiculo.getCurpPropietario());

        return bd.update("vehiculo", registro, "numero_serie='" + vehiculo.getNumeroSerie()+"'", null);
    }

    public int eliminar(String numero) {
        int cant = bd.delete("vehiculo", "numero_serie='" + numero+"'", null);
        //actualizarCursorPropietario(curp);
        return cant;
    }

    public Vehiculo obtenerVehiculo(String numero){
        fila = bd.rawQuery("SELECT * FROM vehiculo WHERE numero_serie='" +numero+"'", null);
        if (fila.moveToFirst()) {
            return generarVehiculo(fila);
        }
        return null;
    }

    public List<Vehiculo> obtenerVehiculosPropietario(String curp){
        fila = bd.rawQuery("SELECT * FROM vehiculo WHERE curp_propietario = '"+curp+"'", null);
        List<Vehiculo> vehiculos = new ArrayList<>();
        if (fila.moveToFirst()) {
            do {
                vehiculos.add(generarVehiculo(fila));
            } while(fila.moveToNext());
        }
        return vehiculos;
    }

    public boolean tieneVerificacionVehicular(String numeroSerie){
        fila = bd.rawQuery("SELECT * FROM vehiculo join verificacion_vehicular on verificacion_vehicular.numero_serie_vehiculo = vehiculo.numero_serie WHERE numero_serie='" +numeroSerie+"'", null);
        if (fila.moveToFirst()) {
            return true;
        }
        return false;
    }

    public Vehiculo generarVehiculo(Cursor fila){
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setNumeroSerie(fila.getString(0));
        vehiculo.setNumeroPlacas(fila.getString(1));
        vehiculo.setMarca(fila.getString(2));
        vehiculo.setModelo(fila.getString(3));
        vehiculo.setAnio(fila.getString(4));
        vehiculo.setTipo(fila.getString(5));
        vehiculo.setNumeroMotor(fila.getString(6));
        vehiculo.setCurpPropietario(fila.getString(7));
        return vehiculo;
    }

    public void actualizarCursorPropietario(String curp) {
        fila = bd.rawQuery("SELECT * FROM vehiculo WHERE curp_propietario = '"+curp+"'", null);
    }
}
