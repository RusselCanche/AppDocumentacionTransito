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
        db.execSQL("CREATE TABLE propietario(curp text PRIMARY KEY, nombre text, paterno text, materno text, fecha_nacimiento text, sexo text, telefono text, domicilio text)");
        db.execSQL("CREATE TABLE usuario(username text PRIMARY KEY, contrasenia text, curp_propietario text, FOREIGN KEY(curp_propietario) REFERENCES propietario(curp))");
        db.execSQL("create table licencia(numero_licencia text primary key, tipo text, fecha_emision text, fecha_expiracion text, estado_emision text, imagen_licencia text, curp_propietario text, foreign key(curp_propietario) references propietario(curp))");
        db.execSQL("create table vehiculo(numero_serie text primary key, numero_placas text, marca text, modelo text, anio text, tipo text, numero_motor text, curp_propietario text, foreign key(curp_propietario) references propietario(curp))");
        db.execSQL("create table tarjeta_circulacion(numero_serie_tarjeta text primary key, fecha_emision text, fecha_expiracion text, estado_emision text, imagen_tarjeta text, numero_serie_vehiculo text, foreign key(numero_serie_vehiculo) references vehiculo(numero_serie))");
        db.execSQL("CREATE TABLE estado(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre text)");
        db.execSQL("CREATE TABLE tipo_multa(id INTEGER PRIMARY KEY AUTOINCREMENT, tipo text)");
        /* SEGÚN EL ESTADO MOSTRAR UN COMBOBOX CON LAS DIFERENTES MULTAS */
        db.execSQL("CREATE TABLE tipo_multa_estado(id INTEGER PRIMARY KEY AUTOINCREMENT, monto real, tipo_multa_id INTEGER, estado_id INTEGER, FOREIGN KEY(tipo_multa_id) REFERENCES tipo_multa(id), FOREIGN KEY(estado_id) REFERENCES estado(id))");
        db.execSQL("CREATE TABLE multa(id INTEGER PRIMARY KEY AUTOINCREMENT, fecha_multa text, fecha_limite text, monto_multa real, municipio text, status INTEGER, multa_estado_id INTEGER, FOREIGN KEY(multa_estado_id) REFERENCES tipo_multa_estado(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS propietario");
        db.execSQL("DROP TABLE IF EXISTS usuario");
        onCreate(db);
    }
}
