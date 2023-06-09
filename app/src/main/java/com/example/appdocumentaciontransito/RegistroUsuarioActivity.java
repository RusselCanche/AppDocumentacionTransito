package com.example.appdocumentaciontransito;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

public class RegistroUsuarioActivity extends AppCompatActivity {
    private DatePicker dpFecha;
    private ImageButton btnFecha;
    private EditText txtFecha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        dpFecha = (DatePicker) findViewById(R.id.dpFecha);
        btnFecha = (ImageButton) findViewById(R.id.btnFecha);
        txtFecha = (EditText) findViewById(R.id.ruTxtFecha);

        txtFecha.setText(getFechaPicker());
        dpFecha.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                txtFecha.setText(getFechaPicker());
                dpFecha.setVisibility(View.GONE);
            }
        });
    }

    public String getFechaPicker(){
        String dia = String.format("%02d", dpFecha.getDayOfMonth());
        String mes = String.format("%02d", dpFecha.getMonth() + 1);
        String anio = String.format("%04d", dpFecha.getYear());

        return anio + "-" + mes + "-" + dia;
    }

    public void muestraCalendario(View view){
        dpFecha.setVisibility(View.VISIBLE);
    }
}