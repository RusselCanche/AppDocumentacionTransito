package com.example.appdocumentaciontransito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.appdocumentaciontransito.modelo.Licencia;
import com.example.appdocumentaciontransito.modelo.Usuario;
import com.example.appdocumentaciontransito.tablas.TablaLicencia;
import com.example.appdocumentaciontransito.tablas.UsuarioController;

import java.util.ArrayList;

public class ListadoUsuariosActivity extends AppCompatActivity {
    private ListView lvUsuarios;
    private UsuarioController tablaUsuario;
    private ArrayList<String> usuarios;
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_usuarios);
        tablaUsuario = new UsuarioController(this);
        lvUsuarios = findViewById(R.id.lv_usuarios);
    }
    @Override
    public void onResume() {
        super.onResume();
        // put your code here...
        usuarios = new ArrayList<>();
        for(Usuario usuario : tablaUsuario.obtenerUsuarios()){
            usuarios.add(usuario.getUsername() + "\t\t\t\t/ " + usuario.getCurpPropietario() + "\t\t\t\t/ " + usuario.getContrasenia());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, usuarios);
        lvUsuarios.setAdapter(adapter);

        lvUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), MiPerfilActivity.class);
                intent.putExtra("curp_usuario", tablaUsuario.obtenerUsuarios().get(i).getCurpPropietario());
                startActivity(intent);
            }
        });
    }
}