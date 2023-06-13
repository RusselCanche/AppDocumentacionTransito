package com.example.appdocumentaciontransito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.appdocumentaciontransito.modelo.Multa;
import com.example.appdocumentaciontransito.modelo.TipoMultaEstado;
import com.example.appdocumentaciontransito.modelo.Vehiculo;
import com.example.appdocumentaciontransito.tablas.TablaMulta;
import com.example.appdocumentaciontransito.tablas.TablaTipoMulta;

import java.util.ArrayList;
import java.util.List;

public class ListadoMultasUsuarioActivity extends AppCompatActivity {
    private ListView lvMultas;
    private TablaMulta tablaMulta;
    private TablaTipoMulta tablaTipoMulta;
    private List<Multa> multas;
    private List<String> nombresMultas;
    private List<TipoMultaEstado> tiposMultas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_multas_usuario);


        lvMultas = findViewById(R.id.lv_multas_usuario);
        tablaMulta = new TablaMulta(this);
        tablaTipoMulta = new TablaTipoMulta(this);

    }
    @Override
    public void onResume(){
        super.onResume();

        SharedPreferences preferences = getSharedPreferences("sesion", Context.MODE_PRIVATE);

        multas = new ArrayList<>();
        tiposMultas = new ArrayList<>();
        nombresMultas = new ArrayList<>();

        multas = tablaMulta.obtenerMultas(preferences.getString("curp_usuario", ""));

        int i = 0;
        for(Multa multa : multas){
            TipoMultaEstado tipoMulta = tablaTipoMulta.obtenerTipoMulta(String.valueOf(multas.get(i).getMultaEstadoId()));
            nombresMultas.add(tipoMulta.getTipoMulta() + "\t\t/ " + tipoMulta.getEstado() + "\t\t/$" + multa.getMontoMulta());
            i++;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nombresMultas);
        lvMultas.setAdapter(adapter);

        lvMultas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), DatosMultaActivity.class);
                intent.putExtra("id", ""+multas.get(i).getId());
                startActivity(intent);
                onPause();
            }
        });
    }

    public void onRegistrarMulta(View view){
        Intent intento = new Intent(this, RegistrarMultaActivity.class);
        startActivity(intento);
    }
}