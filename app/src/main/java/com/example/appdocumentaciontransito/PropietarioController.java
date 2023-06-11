package com.example.appdocumentaciontransito;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.security.Policy;
import java.util.ArrayList;
import java.util.List;

public class PropietarioController {
    private BaseDatos bdSQLite;
    private SQLiteDatabase bd;
    private Cursor fila;
    private Activity contexto;

    public PropietarioController(Activity contexto){
        this.contexto = contexto;
        bdSQLite= new BaseDatos(contexto);
        bd = bdSQLite.getWritableDatabase();
    }
    public Propietario existe(String clave){
        fila = bd.rawQuery("SELECT * FROM propietario WHERE curp='" + clave+"'", null);
        if (fila.moveToFirst()) {
            return generarPropietario(fila);
        }
        return null;
    }
    public List<Propietario> obtenerPropietarios(){
        fila = bd.rawQuery("SELECT * FROM propietario", null);
        List<Propietario> propietarios = new ArrayList<>();
        if (fila.moveToFirst()) {
            do {
                propietarios.add(new Propietario(fila.getString(0),fila.getString(1),fila.getString(2),fila.getString(3),fila.getString(4),fila.getString(5),fila.getString(6),fila.getString(7)));
                // Do something Here with values
            } while(fila.moveToNext());
        }
        return propietarios;
    }
    public void guardar(Propietario propietario){
        ContentValues registro = new ContentValues();
        registro.put("curp", propietario.getCurp());
        registro.put("nombre", propietario.getNombre());
        registro.put("paterno", propietario.getPaterno());
        registro.put("materno", propietario.getMaterno());
        registro.put("fecha_nacimiento", propietario.getFechaNacimiento());
        registro.put("sexo", propietario.getSexo());
        registro.put("telefono", propietario.getTelefono());
        registro.put("domicilio", propietario.getDomicilio());

        bd.insert("propietario", null, registro);
    }

    public Propietario getPropietario(String curp){
        Propietario propietario = new Propietario();
        Cursor fila = bd.rawQuery("select * from propietario where curp = '"+curp+"'",null);
        if(fila.moveToFirst()){
            propietario = generarPropietario(fila);
        }
        return propietario;
    }

    public Propietario getPropietarioUsuario(String usuario){
        Propietario propietario = new Propietario();
        Cursor fila = bd.rawQuery("select * from propietario JOIN usuario on usuario.curp_propietario = propietario.curp WHERE username = '"+usuario+"'",null);
        if(fila.moveToFirst()){
            propietario = generarPropietario(fila);
        }
        return propietario;
    }

    public Propietario generarPropietario(Cursor fila){
        Propietario propietario=new Propietario();
        propietario.setCurp(fila.getString(0));
        propietario.setNombre(fila.getString(1));
        propietario.setPaterno(fila.getString(2));
        propietario.setMaterno(fila.getString(3));
        propietario.setFechaNacimiento(fila.getString(4));
        propietario.setSexo(fila.getString(5));
        propietario.setTelefono(fila.getString(6));
        propietario.setDomicilio(fila.getString(7));
        return propietario;
    }
    public void actualizarCursor(){
        fila.close();
        fila = bd.rawQuery("SELECT * FROM propietario", null);
    }

    public void cerrar(){
        bd.close();
    }
}
