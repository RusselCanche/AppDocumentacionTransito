package com.example.appdocumentaciontransito.tablas;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appdocumentaciontransito.BaseDatos;
import com.example.appdocumentaciontransito.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioController {
    private BaseDatos bdSQLite;
    private SQLiteDatabase bd;
    private Cursor fila;
    private Activity contexto;
    public UsuarioController(Activity contexto){
        this.contexto = contexto;
        bdSQLite= new BaseDatos(contexto);
        bd = bdSQLite.getWritableDatabase();
    }

    public boolean existe(String clave){
        fila = bd.rawQuery("SELECT * FROM usuario WHERE username='" + clave+"'", null);
        if (fila.moveToFirst()) {
            return true;
        }
        return false;
    }
    public Usuario getUsuario(String clave){
        fila = bd.rawQuery("SELECT * FROM usuario WHERE username='" + clave+"'", null);
        if (fila.moveToFirst()) {
            return generarUsuario(fila);
        }
        return null;
    }

    public boolean datosCorrectos(String user, String pass){
        if(existe(user)){
            Usuario usuario = new Usuario();
            usuario = getUsuario(user);
            if(usuario.getUsername().equals(user) && usuario.getContrasenia().equals(pass)){
                System.out.println(usuario.getUsername());
                System.out.println(usuario.getContrasenia());
                return true;
            }
        }
        return false;
    }

    public List<Usuario> obtenerUsuarios(){
        fila = bd.rawQuery("SELECT * FROM usuario", null);
        List<Usuario> usuarios = new ArrayList<>();
        if (fila.moveToFirst()) {
            do {
                usuarios.add(new Usuario(fila.getString(0),fila.getString(1),fila.getString(2)));
                // Do something Here with values
            } while(fila.moveToNext());
        }
        return usuarios;
    }
    public void guardar(Usuario usuario){
        ContentValues registro = new ContentValues();
        registro.put("username", usuario.getUsername());
        registro.put("contrasenia", usuario.getContrasenia());
        registro.put("curp_propietario", usuario.getCurpPropietario());
        bd.insert("usuario", null, registro);
    }
    public Usuario generarUsuario(Cursor fila){
        Usuario usuario=new Usuario();
        usuario.setUsername(fila.getString(0));
        usuario.setContrasenia(fila.getString(1));
        usuario.setCurpPropietario(fila.getString(2));
        return usuario;
    }
    public void actualizarCursor(){
        fila.close();
        fila = bd.rawQuery("SELECT * FROM usuario", null);
    }

    public void cerrar(){
        bd.close();
    }

}
