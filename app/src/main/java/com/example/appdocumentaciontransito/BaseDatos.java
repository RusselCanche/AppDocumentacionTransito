package com.example.appdocumentaciontransito;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDatos extends SQLiteOpenHelper {
    private static final String NOMBRE_BASE_DATOS = "documentacion-transito.db";
    private final Context contexto;
    private static final int VERSION_ACTUAL = 1;

    public BaseDatos(Context contexto){
        super(contexto, NOMBRE_BASE_DATOS, null, VERSION_ACTUAL);
        this.contexto= contexto;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE usuario(username text PRIMARY KEY, contrasenia text,curp_propietario text)");
        db.execSQL("CREATE TABLE propietario(curp text PRIMARY KEY, nombre text,paterno text, materno text, fecha_nacimiento text, sexo text, telefono text, domicilio text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS usuario");
        db.execSQL("DROP TABLE IF EXISTS propietario");
        onCreate(db);
    }
}
