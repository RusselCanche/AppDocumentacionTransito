package com.example.appdocumentaciontransito.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdocumentaciontransito.PrincipalUsuarioActivity;
import com.example.appdocumentaciontransito.R;
import com.example.appdocumentaciontransito.RegistroUsuarioActivity;
import com.example.appdocumentaciontransito.modelo.Propietario;
import com.example.appdocumentaciontransito.tablas.PropietarioController;
import com.example.appdocumentaciontransito.tablas.UsuarioController;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    private TextInputEditText usuario;
    private TextInputEditText contrasenia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuario = findViewById(R.id.edit_usuario_login);
        contrasenia = findViewById(R.id.edit_contrasenia_login);
    }

    public void onIniciarSesion(View view){
        UsuarioController usuarioController = new UsuarioController(this);
        PropietarioController propietarioController = new PropietarioController(this);
        String usuario_valor = usuario.getText().toString();
        Propietario propietario = propietarioController.getPropietarioUsuario(usuario_valor);

        if(usuarioController.datosCorrectos(usuario_valor, contrasenia.getText().toString())){
            Toast.makeText(getApplicationContext(), "Bienvenido " + propietario.getNombre(), Toast.LENGTH_LONG).show();
            guardarEstado(propietario.getCurp(), propietario.getNombre());
            finish(); // CERRAR FORM DE INICIO DE SESIÓN
            Intent intento = null;
            if(usuario.getText().toString().equals("admin")&&contrasenia.getText().toString().equals("admin")){
                intento = new Intent(this, InicioAdminActivity.class);
            }else{
                intento = new Intent(this, InicioUsuarioActivity.class);
            }
            startActivity(intento);
        } else
            Toast.makeText(this, "Sus datos son incorrectos", Toast.LENGTH_SHORT).show();
    }
    public void onRegistroUsuario(View view){
        Intent intento = new Intent(this, RegistroUsuarioActivity.class);
        startActivity(intento);
    }

    /* MÉTODO PARA "CREAR" SESION */
    public void guardarEstado(String curp, String nombre){
        SharedPreferences preferences = getSharedPreferences("sesion", Context.MODE_PRIVATE);
        boolean estado = true;
        /* Creamos un "archivo" donde se almacenará esa información */
        SharedPreferences.Editor editor = preferences.edit();
        /* COMPROBAR SI LA SESIÓN ESTÁ ACTIVA O INACTIVA */
        editor.putBoolean("estado_usuario", estado);
        editor.putString("curp_usuario", curp);
        editor.putString("nombre_usuario", nombre);
        editor.commit();
    }
}