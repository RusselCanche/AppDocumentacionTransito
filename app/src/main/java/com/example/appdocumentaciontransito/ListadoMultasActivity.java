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
import java.util.List;

public class ListadoMultasActivity extends AppCompatActivity {
    private ListView lvTiposMultas;
    private TablaTipoMulta tablaTipoMulta;
    private ArrayList<String> tiposMultas;
    private List<TipoMultaEstado> multas;

    private ArrayAdapter<String> adapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_multas);

        lvTiposMultas = findViewById(R.id.lv_tipos_multas);
        tablaTipoMulta = new TablaTipoMulta(this);
    }
    @Override
    public void onResume(){
        super.onResume();
        // put your code here...
        tiposMultas = new ArrayList<>();
        multas = tablaTipoMulta.obtenerTiposMulta();
        for(TipoMultaEstado tipoMulta : multas){
            tiposMultas.add(tipoMulta.getTipoMulta() + "\t\t\t\t\t\t\t/ " + tipoMulta.getEstado());
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tiposMultas);
        lvTiposMultas.setAdapter(adapter);

        lvTiposMultas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), DatosTipoMultaActivity.class);
                intent.putExtra("id", ""+multas.get(i).getId());
                startActivity(intent);
            }
        });

    }

    public void onRegistrarTipoMulta(View view){
        Intent intento = new Intent(this, RegistrarTipoMultaActivity.class);
        startActivity(intento);
    }
}