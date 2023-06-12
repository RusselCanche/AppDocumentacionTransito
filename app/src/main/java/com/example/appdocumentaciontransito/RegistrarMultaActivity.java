package com.example.appdocumentaciontransito;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.appdocumentaciontransito.modelo.Multa;
import com.example.appdocumentaciontransito.modelo.TipoMultaEstado;
import com.example.appdocumentaciontransito.tablas.TablaMulta;
import com.example.appdocumentaciontransito.tablas.TablaTipoMulta;

import java.util.ArrayList;
import java.util.List;

public class RegistrarMultaActivity extends AppCompatActivity {
    private Spinner spTiposMulta;
    private Spinner spEstados;
    private EditText monto;
    private EditText fechaMulta;
    private EditText fechaLimite;
    private EditText municipio;
    private TablaTipoMulta tablaTipoMulta;
    private TablaMulta tablaMulta;
    private int idTipoMulta;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_multa);

        monto = findViewById(R.id.edit_monto_multa);
        fechaMulta = findViewById(R.id.edit_fecha_multa);
        fechaLimite = findViewById(R.id.edit_fecha_limite);
        municipio = findViewById(R.id.edit_municipio);

        tablaTipoMulta = new TablaTipoMulta(this);
        tablaMulta = new TablaMulta(this);
        spTiposMulta = findViewById(R.id.sp_tipos_multa_registro);
        spEstados = findViewById(R.id.sp_estado_multa);

        ArrayAdapter<CharSequence> adapterEstados = ArrayAdapter.createFromResource(this, R.array.estados_array, android.R.layout.simple_spinner_item);
        adapterEstados.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEstados.setAdapter(adapterEstados);

        // Enlazar el funcionamiento de los spinner
        spEstados.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String estado = spEstados.getSelectedItem().toString();
                List<TipoMultaEstado> listaTiposM = llenarTiposMulta(estado);
                ArrayAdapter<TipoMultaEstado> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listaTiposM);
                spTiposMulta.setAdapter(arrayAdapter);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spTiposMulta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idTipoMulta = ((TipoMultaEstado) spTiposMulta.getSelectedItem()).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void guardar(View v){
        if (!hayCajasVacias()) {
            SharedPreferences preferences = getSharedPreferences("sesion", Context.MODE_PRIVATE);
            Multa multa = new Multa();
            multa.setId(0);
            multa.setMontoMulta(monto.getText().toString());
            multa.setFechaMulta(fechaMulta.getText().toString());
            multa.setFechaLimite(fechaLimite.getText().toString());
            multa.setMunicipio(municipio.getText().toString());
            multa.setStatus("En deuda");
            multa.setMultaEstadoId(idTipoMulta);
            multa.setCurp(preferences.getString("curp_usuario", ""));
            tablaMulta.guardar(multa);

            Toast.makeText(this, "Sus datos se han registrado correctamente", Toast.LENGTH_SHORT).show();
            limpiarCajas();
        } else
            Toast.makeText(this, "¡Debe de ingresar todos los datos!", Toast.LENGTH_SHORT).show();
    }

    private boolean hayCajasVacias(){
        boolean b1 = monto.getText().toString().equals("");
        boolean b2 = fechaMulta.getText().toString().equals("");
        boolean b3 = fechaLimite.getText().toString().equals("");
        boolean b4 = municipio.getText().toString().equals("");
        if(b1||b2||b3||b4)
            return true;
        return false;
    }

    public void limpiarCajas(){
        monto.setText("");
        fechaMulta.setText("");
        fechaLimite.setText("");
        municipio.setText("");
    }

    @SuppressLint("Range")
    private List<TipoMultaEstado> llenarTiposMulta(String estado){
        List<TipoMultaEstado> lista = new ArrayList<>();
        Cursor cursor = tablaTipoMulta.mostrarTiposMultas(estado);
        if(cursor != null){
            if(cursor.moveToFirst()){
                do {
                    TipoMultaEstado tipoMultaEstado = new TipoMultaEstado();
                    tipoMultaEstado.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    tipoMultaEstado.setTipoMulta(cursor.getString(cursor.getColumnIndex("tipo_multa")));
                    lista.add(tipoMultaEstado);
                } while(cursor.moveToNext());
            }
        }
        return lista;
    }
}