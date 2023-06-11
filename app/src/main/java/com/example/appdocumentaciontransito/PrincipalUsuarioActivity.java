package com.example.appdocumentaciontransito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.example.appdocumentaciontransito.activity.MainActivity;

public class PrincipalUsuarioActivity extends AppCompatActivity {
    private TextView textCurp;
    private TextView textNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_usuario);

        SharedPreferences preferences = getSharedPreferences("sesion", Context.MODE_PRIVATE);
        textCurp = findViewById(R.id.text_curp);
        textNombre = findViewById(R.id.text_nombre);

        textCurp.setText(preferences.getString("curp_usuario", ""));
        textNombre.setText(preferences.getString("nombre_usuario", ""));

        /* VALIDACIÓN PARA MANTENER SESIÓN ACTIVA */
        if(preferences.getBoolean("estado_usuario", false) == false){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intento = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intento);
                    finish();
                }
            }, 4000);
        } /*EN EL VIDEO HAY UN ELSE, DONDE REDIRECCIONA A UNA VISTA (ESTA)*/
    }

    public void onVerLicencias(View view){
        Intent intento = new Intent(this, ListadoLicenciasActivity.class);
        startActivity(intento);
    }

    public void onVerVehiculos(View view){
        Intent intento = new Intent(this, ListadoVehiculosActivity.class);
        startActivity(intento);
    }

    public void onCerrarSesion(View view){
        Intent intento = new Intent(this, MainActivity.class);
        startActivity(intento);
        finish();
    }
}