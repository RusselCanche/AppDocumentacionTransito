package com.example.appdocumentaciontransito;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.appdocumentaciontransito.modelo.TipoMultaEstado;
import com.example.appdocumentaciontransito.tablas.TablaTipoMulta;

public class RegistrarTipoMultaActivity extends AppCompatActivity {
    private Spinner tiposMulta;
    private EditText monto;
    private EditText descripcion;
    private Spinner estados;
    private TablaTipoMulta tablaTipoMulta;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_tipo_multa);

        tablaTipoMulta = new TablaTipoMulta(this);
        tiposMulta = findViewById(R.id.sp_tipos_multa);
        monto = findViewById(R.id.edit_monto_tm);
        descripcion = findViewById(R.id.edit_descripcion);
        estados = findViewById(R.id.sp_estados);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.infracciones_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tiposMulta.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapterEstados = ArrayAdapter.createFromResource(this, R.array.infracciones_array, android.R.layout.simple_spinner_item);
        adapterEstados.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        estados.setAdapter(adapterEstados);
    }

    public void guardar(View v){
        if (!hayCajasVacias()) {
            TipoMultaEstado tipoMulta = new TipoMultaEstado();
            tipoMulta.setId(0);
            tipoMulta.setTipoMulta(tiposMulta.getSelectedItem().toString());
            tipoMulta.setMonto(monto.getText().toString());
            tipoMulta.setDescripcion(descripcion.getText().toString());
            tipoMulta.setEstado(estados.getSelectedItem().toString());
            tablaTipoMulta.guardar(tipoMulta);

            Toast.makeText(this, "Sus datos se han registrado correctamente", Toast.LENGTH_SHORT).show();
            limpiarCajas();
        } else
            Toast.makeText(this, "¡Debe de ingresar todos los datos!", Toast.LENGTH_SHORT).show();
    }

    private boolean hayCajasVacias(){
        boolean b1 = monto.getText().toString().equals("");
        boolean b2 = descripcion.getText().toString().equals("");
        if(b1||b2)
            return true;
        return false;
    }

    public void limpiarCajas(){
        monto.setText("");
        descripcion.setText("");
    }
}