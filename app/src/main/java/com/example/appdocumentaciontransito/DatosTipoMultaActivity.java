package com.example.appdocumentaciontransito;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.appdocumentaciontransito.modelo.TipoMultaEstado;
import com.example.appdocumentaciontransito.tablas.TablaTipoMulta;

public class DatosTipoMultaActivity extends AppCompatActivity {
    private Spinner tiposMulta;
    private EditText monto;
    private EditText descripcion;
    private Spinner estados;
    private TablaTipoMulta tablaTipoMulta;
    private TipoMultaEstado tipoMultaEstado;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_tipo_multa);

        id = getIntent().getStringExtra("id");
        System.out.println("id: " + id);
        tablaTipoMulta = new TablaTipoMulta(this);
        tiposMulta = findViewById(R.id.sp_tipos_multa_me);
        monto = findViewById(R.id.edit_monto_tm_me);
        descripcion = findViewById(R.id.edit_descripcion_me);
        estados = findViewById(R.id.sp_estados_me);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.infracciones_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tiposMulta.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapterEstados = ArrayAdapter.createFromResource(this, R.array.estados_array, android.R.layout.simple_spinner_item);
        adapterEstados.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        estados.setAdapter(adapterEstados);

        tipoMultaEstado = tablaTipoMulta.obtenerTipoMulta(id);

        tiposMulta.setSelection(adapter.getPosition(tipoMultaEstado.getTipoMulta()));
        estados.setSelection(adapterEstados.getPosition(tipoMultaEstado.getEstado()));
        monto.setText(tipoMultaEstado.getMonto());
        descripcion.setText(tipoMultaEstado.getDescripcion());
    }

    public void modificarTipoMultaEstado(View v) {
        if (!hayCajasVacias()) {
            TipoMultaEstado multaEstado = new TipoMultaEstado();
            multaEstado.setId(Integer.parseInt(id));
            multaEstado.setTipoMulta(tiposMulta.getSelectedItem().toString());
            multaEstado.setMonto(monto.getText().toString());
            multaEstado.setDescripcion(descripcion.getText().toString());
            multaEstado.setEstado(estados.getSelectedItem().toString());
            tablaTipoMulta.modificar(multaEstado);
            Toast.makeText(this, "Sus datos se han actualizado correctamente", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "¡Debe de ingresar todos los datos!", Toast.LENGTH_SHORT).show();
    }

    public void eliminarTipoMultaEstado(View v){
        int n = tablaTipoMulta.eliminar(id);
        Toast.makeText(this, "Tipo de multa eliminada exitosamente", Toast.LENGTH_LONG).show();
    }

    private boolean hayCajasVacias(){
        boolean b1 = monto.getText().toString().equals("");
        boolean b2 = descripcion.getText().toString().equals("");
        if(b1||b2)
            return true;
        return false;
    }
}