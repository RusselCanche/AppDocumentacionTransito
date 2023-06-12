package com.example.appdocumentaciontransito;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.appdocumentaciontransito.modelo.Licencia;
import com.example.appdocumentaciontransito.modelo.Vehiculo;
import com.example.appdocumentaciontransito.tablas.TablaLicencia;
import com.example.appdocumentaciontransito.tablas.TablaVehiculo;

import java.util.ArrayList;

public class ListadoVehiculosActivity extends AppCompatActivity {
    private ListView lvVehiculos;
    private TablaVehiculo tablaVehiculo;
    private ArrayList<String> vehiculos;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_vehiculos);

        SharedPreferences preferences = getSharedPreferences("sesion", Context.MODE_PRIVATE);
        tablaVehiculo = new TablaVehiculo(this);
        lvVehiculos = findViewById(R.id.lv_vehiculos_propietario);
        vehiculos = new ArrayList<>();
        String curp = preferences.getString("curp_usuario", "");

        for(Vehiculo vehiculo : tablaVehiculo.obtenerVehiculosPropietario(curp)){
            vehiculos.add(vehiculo.getNumeroSerie() + "\t\t\t\t/ " + vehiculo.getTipo() + "\t\t\t\t/ " + vehiculo.getMarca()+" - "+vehiculo.getModelo());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, vehiculos);
        lvVehiculos.setAdapter(adapter);

        lvVehiculos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), DatosVehiculoActivity.class);
                intent.putExtra("numeroSerie", tablaVehiculo.obtenerVehiculosPropietario(curp).get(i).getNumeroSerie());
                startActivity(intent);
            }
        });
    }

    public void onRegistrarVehiculo(View view){
        Intent intento = new Intent(this, RegistrarVehiculoActivity.class);
        startActivity(intento);
    }
}