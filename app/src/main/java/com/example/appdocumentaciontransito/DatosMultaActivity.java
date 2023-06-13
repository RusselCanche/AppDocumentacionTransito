package com.example.appdocumentaciontransito;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.appdocumentaciontransito.modelo.Multa;
import com.example.appdocumentaciontransito.modelo.TipoMultaEstado;
import com.example.appdocumentaciontransito.tablas.TablaMulta;
import com.example.appdocumentaciontransito.tablas.TablaTipoMulta;

import java.util.ArrayList;
import java.util.List;

public class DatosMultaActivity extends AppCompatActivity {
    private Spinner spTiposMulta;
    private Spinner spEstados;
    private EditText monto;
    private EditText fechaMulta;
    private EditText fechaLimite;
    private EditText municipio;
    private TablaTipoMulta tablaTipoMulta;
    private TablaMulta tablaMulta;
    private DatePicker dpFecha;
    private DatePicker dpFechaPago;
    private String id;
    private int idTipoMulta;
    private Multa multa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_multa);
        id = getIntent().getStringExtra("id");

        monto = findViewById(R.id.edit_monto_multa_dat);
        fechaMulta = findViewById(R.id.edit_fecha_multa_dat);
        fechaLimite = findViewById(R.id.edit_fecha_limite_dat);
        municipio = findViewById(R.id.edit_municipio_dat);

        dpFecha = (DatePicker) findViewById(R.id.dpFechaMulta);
        dpFechaPago = (DatePicker) findViewById(R.id.dpFechaLimite);

        tablaTipoMulta = new TablaTipoMulta(this);
        tablaMulta = new TablaMulta(this);

        multa = tablaMulta.obtenerMulta(id);

        monto.setText(multa.getMontoMulta());
        fechaMulta.setText(multa.getFechaMulta());
        fechaLimite.setText(multa.getFechaLimite());
        municipio.setText(multa.getMunicipio());

        spTiposMulta = findViewById(R.id.sp_tipos_multa_registro_dat);
        spEstados = findViewById(R.id.sp_estado_multa_dat);

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

        spEstados.setSelection(adapterEstados.getPosition(tablaTipoMulta.obtenerTipoMulta(String.valueOf(multa.getMultaEstadoId())).getEstado()));
        dpFecha.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                fechaMulta.setText(getFechaPicker());
                //dpFecha.setVisibility(View.GONE);
            }
        });
        dpFechaPago.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                fechaLimite.setText(getFechaPicker2());
                //dpFecha.setVisibility(View.GONE);
            }
        });
    }
    public String getFechaPicker(){
        String dia = String.format("%02d", dpFecha.getDayOfMonth());
        String mes = String.format("%02d", dpFecha.getMonth() + 1);
        String anio = String.format("%04d", dpFecha.getYear());

        return anio + "-" + mes + "-" + dia;
    }
    public String getFechaPicker2(){
        String dia = String.format("%02d", dpFechaPago.getDayOfMonth());
        String mes = String.format("%02d", dpFechaPago.getMonth() + 1);
        String anio = String.format("%04d", dpFechaPago.getYear());

        return anio + "-" + mes + "-" + dia;
    }
    public void muestraCalendarioFmulta(View view){
        if(dpFecha.getVisibility() == View.VISIBLE){
            dpFecha.setVisibility(View.GONE);
        }else{
            dpFecha.setVisibility(View.VISIBLE);
        }
    }
    public void muestraCalendarioFmultaLimitePago(View view){
        if(dpFechaPago.getVisibility() == View.VISIBLE){
            dpFechaPago.setVisibility(View.GONE);
        }else{
            dpFechaPago.setVisibility(View.VISIBLE);
        }
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