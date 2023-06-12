package com.example.appdocumentaciontransito;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.appdocumentaciontransito.modelo.Licencia;
import com.example.appdocumentaciontransito.modelo.TipoMultaEstado;
import com.example.appdocumentaciontransito.tablas.TablaLicencia;
import com.example.appdocumentaciontransito.tablas.TablaTipoMulta;

import java.util.ArrayList;

public class ListadoMultasActivity extends AppCompatActivity {
    private ListView lvTiposMultas;
    private TablaTipoMulta tablaTipoMulta;
    private ArrayList<String> tiposMultas;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_multas);

        lvTiposMultas = findViewById(R.id.lv_tipos_multas);
        tablaTipoMulta = new TablaTipoMulta(this);
        tiposMultas = new ArrayList<>();

        for(TipoMultaEstado tipoMulta : tablaTipoMulta.obtenerTiposMulta()){
            tiposMultas.add(tipoMulta.getTipoMulta() + "\t\t\t\t\t\t\t/ " + tipoMulta.getEstado());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tiposMultas);
        lvTiposMultas.setAdapter(adapter);

        lvTiposMultas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), DatosLicenciaActivity.class);
                intent.putExtra("id", tablaTipoMulta.obtenerTiposMulta().get(i).getId());
                startActivity(intent);
            }
        });
    }

    public void onRegistrarTipoMulta(View view){
        Intent intento = new Intent(this, RegistrarTipoMultaActivity.class);
        startActivity(intento);
    }
}