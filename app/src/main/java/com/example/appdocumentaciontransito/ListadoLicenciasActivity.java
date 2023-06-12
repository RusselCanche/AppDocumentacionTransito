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

import com.example.appdocumentaciontransito.modelo.Licencia;
import com.example.appdocumentaciontransito.tablas.TablaLicencia;

import java.util.ArrayList;

public class ListadoLicenciasActivity extends AppCompatActivity {
    private ListView lvLicencias;
    private TablaLicencia tablaLicencia;
    private ArrayList<String> licencias;
    private SharedPreferences preferences;
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_licencias);

        preferences = getSharedPreferences("sesion", Context.MODE_PRIVATE);
        tablaLicencia = new TablaLicencia(this);
        lvLicencias = findViewById(R.id.lv_licencias_usuario);

    }
    @Override
    public void onResume(){
        super.onResume();
        // put your code here...
        licencias = new ArrayList<>();
        String curp = preferences.getString("curp_usuario", "");

        for(Licencia licencia : tablaLicencia.obtenerLicenciasPropietario(curp)){
            licencias.add(licencia.getNumeroLicencia() + "\t\t\t\t/ " + licencia.getTipo() + "\t\t\t\t/ " + licencia.getEstadoEmision());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, licencias);
        lvLicencias.setAdapter(adapter);

        lvLicencias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), DatosLicenciaActivity.class);
                intent.putExtra("numeroLicencia", tablaLicencia.obtenerLicenciasPropietario(curp).get(i).getNumeroLicencia());
                startActivity(intent);
            }
        });
    }
    public void onRegistrarLicencia(View view){
        Intent intento = new Intent(this, RegistrarLicenciaActivity.class);
        startActivity(intento);
    }
}