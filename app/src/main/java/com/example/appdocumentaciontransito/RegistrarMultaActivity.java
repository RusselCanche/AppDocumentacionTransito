package com.example.appdocumentaciontransito;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.appdocumentaciontransito.modelo.TipoMultaEstado;
import com.example.appdocumentaciontransito.tablas.TablaTipoMulta;

import java.util.ArrayList;
import java.util.List;

public class RegistrarMultaActivity extends AppCompatActivity {
    private Spinner spTiposMulta;
    private TablaTipoMulta tablaTipoMulta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_multa);

        tablaTipoMulta = new TablaTipoMulta(this);
        spTiposMulta = findViewById(R.id.sp_tipos_multa_registro);
        List<TipoMultaEstado> listaTiposM = llenarTiposMulta();
        ArrayAdapter<TipoMultaEstado> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listaTiposM);
        spTiposMulta.setAdapter(arrayAdapter);
    }

    @SuppressLint("Range")
    private List<TipoMultaEstado> llenarTiposMulta(){
        List<TipoMultaEstado> lista = new ArrayList<>();
        Cursor cursor = tablaTipoMulta.mostrarTiposMultas();
        if(cursor != null){
            if(cursor.moveToFirst()){
                do {
                    TipoMultaEstado tipoMultaEstado = new TipoMultaEstado();
                    tipoMultaEstado.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    tipoMultaEstado.setTipoMulta(cursor.getString(cursor.getColumnIndex("tipo_multa")));
                    lista.add(tipoMultaEstado);
                }while(cursor.moveToNext());
            }
        }
        return lista;
    }
}