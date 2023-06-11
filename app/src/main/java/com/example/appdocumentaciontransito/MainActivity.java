package com.example.appdocumentaciontransito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText usuario;
    private EditText contrasenia;

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
            Intent intento = new Intent(this, PrincipalUsuarioActivity.class);
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