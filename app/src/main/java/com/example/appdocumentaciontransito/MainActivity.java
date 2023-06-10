package com.example.appdocumentaciontransito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
        if(usuarioController.datosCorrectos(usuario.getText().toString(), contrasenia.getText().toString())){
            Intent intento = new Intent(this, PrincipalUsuarioActivity.class);
            startActivity(intento);
        } else
            Toast.makeText(this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
    }
    public void onRegistroUsuario(View view){
        Intent intento = new Intent(this, RegistroUsuarioActivity.class);
        startActivity(intento);
    }
}