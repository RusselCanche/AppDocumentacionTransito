package com.example.appdocumentaciontransito;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

public class RegistroUsuarioActivity extends AppCompatActivity {
    private EditText curp;
    private EditText nombre;
    private EditText paterno;
    private EditText materno;
    private DatePicker dpFecha;
    private ImageButton btnFecha;
    private EditText txtFecha;
    private RadioButton masculino;
    private RadioButton femenino;
    private EditText telefono;
    private EditText domicilio;
    private EditText user;
    private EditText contrasenia;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        curp = findViewById(R.id.edit_curp);
        nombre = findViewById(R.id.edit_nombre);
        materno = findViewById(R.id.edit_materno);
        paterno = findViewById(R.id.edit_paterno);
        dpFecha = (DatePicker) findViewById(R.id.dpFecha);
        btnFecha = (ImageButton) findViewById(R.id.btnFecha);
        txtFecha = (EditText) findViewById(R.id.edit_fecha_nac);
        masculino = findViewById(R.id.radio_masculino);
        femenino = findViewById(R.id.radio_femenino);
        telefono = findViewById(R.id.edit_telefono);
        domicilio = findViewById(R.id.edit_direccion);

        user = findViewById(R.id.edit_usuario);
        contrasenia = findViewById(R.id.edit_contrasenia);

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

    public void guardar(View v) {
        PropietarioController tablaPropietario = new PropietarioController(this);
        UsuarioController tablaUsuario = new UsuarioController(this);
        Propietario propietario = new Propietario();
        Usuario usuario = new Usuario();

        propietario.setCurp(curp.getText().toString());
        propietario.setNombre(nombre.getText().toString());
        propietario.setPaterno(paterno.getText().toString());
        propietario.setMaterno(materno.getText().toString());
        propietario.setFechaNacimiento(txtFecha.getText().toString());
        if(masculino.isChecked()){ propietario.setSexo("Masculino"); }
        else { propietario.setSexo("Femenino"); }
        propietario.setTelefono(telefono.getText().toString());
        propietario.setDomicilio(domicilio.getText().toString());
        tablaPropietario.guardar(propietario);

        usuario.setUsername(user.getText().toString());
        usuario.setContrasenia(contrasenia.getText().toString());
        usuario.setCurpPropietario(curp.getText().toString());
        tablaUsuario.guardar(usuario);

        Toast.makeText(this, "Sus datos se han registrado correctamente", Toast.LENGTH_SHORT).show();
    }
}