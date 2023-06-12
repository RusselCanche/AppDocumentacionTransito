package com.example.appdocumentaciontransito;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.appdocumentaciontransito.modelo.Propietario;
import com.example.appdocumentaciontransito.modelo.Usuario;
import com.example.appdocumentaciontransito.tablas.PropietarioController;
import com.example.appdocumentaciontransito.tablas.UsuarioController;

public class MiPerfilActivity extends AppCompatActivity {
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
    private PropietarioController propietarioController;
    private UsuarioController usuarioController;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_perfil);

        SharedPreferences preferences = getSharedPreferences("sesion", Context.MODE_PRIVATE);
        propietarioController = new PropietarioController(this);
        usuarioController = new UsuarioController(this);
        String curp_usuario = preferences.getString("curp_usuario", "");
        Propietario propietario;
        Usuario usuario;

        curp = findViewById(R.id.edit_curp_mp);
        nombre = findViewById(R.id.edit_nombre_mp);
        materno = findViewById(R.id.edit_materno_mp);
        paterno = findViewById(R.id.edit_paterno_mp);
        dpFecha = (DatePicker) findViewById(R.id.dpFechaMp);
        txtFecha = (EditText) findViewById(R.id.edit_fecha_nac_mp);
        masculino = findViewById(R.id.radio_masculino_mp);
        femenino = findViewById(R.id.radio_femenino_mp);
        telefono = findViewById(R.id.edit_telefono_mp);
        domicilio = findViewById(R.id.edit_direccion_mp);

        user = findViewById(R.id.edit_usuario_mp);
        contrasenia = findViewById(R.id.edit_contrasenia_mp);

        //txtFecha.setText(getFechaPicker());
        dpFecha.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                txtFecha.setText(getFechaPicker());
                //dpFecha.setVisibility(View.GONE);
            }
        });

        propietario = propietarioController.getPropietario(curp_usuario);
        curp.setText(propietario.getCurp());
        nombre.setText(propietario.getNombre());
        paterno.setText(propietario.getPaterno());
        materno.setText(propietario.getMaterno());
        txtFecha.setText(propietario.getFechaNacimiento());
        if(propietario.getSexo().equals("Masculino")){ masculino.setChecked(true); }
        else { femenino.setChecked(true); }
        telefono.setText(propietario.getTelefono());
        domicilio.setText(propietario.getDomicilio());
        usuario = usuarioController.obtenerUsuario(curp_usuario);
        user.setText(usuario.getUsername());
        contrasenia.setText(usuario.getContrasenia());
    }

    public void modificar(View v) {
        if (!hayCajasVacias()) {
            Propietario propietario = new Propietario();
            Usuario usuario = new Usuario();

            propietario.setCurp(curp.getText().toString());
            propietario.setNombre(nombre.getText().toString());
            propietario.setPaterno(paterno.getText().toString());
            propietario.setMaterno(materno.getText().toString());
            propietario.setFechaNacimiento(txtFecha.getText().toString());
            if (masculino.isChecked()) {
                propietario.setSexo("Masculino");
            } else {
                propietario.setSexo("Femenino");
            }
            propietario.setTelefono(telefono.getText().toString());
            propietario.setDomicilio(domicilio.getText().toString());
            propietarioController.modificar(propietario);

            usuario.setUsername(user.getText().toString());
            usuario.setContrasenia(contrasenia.getText().toString());
            usuario.setCurpPropietario(curp.getText().toString());
            usuarioController.modificar(usuario);

            Toast.makeText(this, "Sus datos se han actualizado correctamente", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "¡Debe de ingresar todos los datos!", Toast.LENGTH_SHORT).show();
    }

    public String getFechaPicker(){
        String dia = String.format("%02d", dpFecha.getDayOfMonth());
        String mes = String.format("%02d", dpFecha.getMonth() + 1);
        String anio = String.format("%04d", dpFecha.getYear());

        return anio + "-" + mes + "-" + dia;
    }

    public void muestraCalendarioFnacMp(View view){
        if(dpFecha.getVisibility() == View.VISIBLE){
            dpFecha.setVisibility(View.GONE);
        }else{
            dpFecha.setVisibility(View.VISIBLE);
        }
    }

    private boolean hayCajasVacias(){
        boolean b1 = curp.getText().toString().equals("");
        boolean b2 = nombre.getText().toString().equals("");
        boolean b3 = paterno.getText().toString().equals("");
        boolean b4 = materno.getText().toString().equals("");
        boolean b5 = txtFecha.getText().toString().equals("");
        boolean b6 = telefono.getText().toString().equals("");
        boolean b7 = domicilio.getText().toString().equals("");
        boolean b8 = user.getText().toString().equals("");
        boolean b9 = contrasenia.getText().toString().equals("");
        if(b1||b2||b3||b4||b5||b6||b7||b8||b9)
            return true;
        return false;
    }
}