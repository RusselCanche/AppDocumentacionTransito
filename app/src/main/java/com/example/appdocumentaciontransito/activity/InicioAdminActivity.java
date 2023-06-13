package com.example.appdocumentaciontransito.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appdocumentaciontransito.ListadoMultasActivity;
import com.example.appdocumentaciontransito.ListadoUsuariosActivity;
import com.example.appdocumentaciontransito.MiPerfilActivity;
import com.example.appdocumentaciontransito.R;
import com.example.appdocumentaciontransito.databinding.ActivityInicioAdminBinding;
import com.example.appdocumentaciontransito.modelo.Usuario;
import com.example.appdocumentaciontransito.tablas.UsuarioController;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class InicioAdminActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityInicioAdminBinding binding;
    private TextView tituloNombre;
    private TextView tituloCurp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInicioAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarInicioAdmin.toolbar);
        binding.appBarInicioAdmin.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        View headerView = navigationView.getHeaderView(0);
        tituloNombre= headerView.findViewById(R.id.nav_username_admin_title);
        tituloCurp= headerView.findViewById(R.id.nav_curp_admin_title);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_inicio_admin);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Agregar listener al NavigationView
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Identificar la opción seleccionada
                int id = item.getItemId();
                Intent intent=null;
                // Manejar la acción correspondiente
                switch (id) {
                    case R.id.nav_home:
                        Toast.makeText(getApplicationContext(), "HOME", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_mi_perfil:
                        intent = new Intent(InicioAdminActivity.this, MiPerfilActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_usuarios:
                        intent = new Intent(InicioAdminActivity.this, ListadoUsuariosActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_multas:
                        intent = new Intent(InicioAdminActivity.this, ListadoMultasActivity.class);
                        startActivity(intent);
                        break;
                }

                // Cerrar el DrawerLayout después de seleccionar una opción
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });


        UsuarioController usuarioController = new UsuarioController(this);
        SharedPreferences preferences = getSharedPreferences("sesion", Context.MODE_PRIVATE);
        Usuario usuario = usuarioController.obtenerUsuario(preferences.getString("curp_usuario", ""));
        tituloNombre.setText(usuario.getUsername());
        tituloCurp.setText(usuario.getCurpPropietario());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inicio_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                this.logout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void logout() {
        Toast.makeText(getApplicationContext(), "Cerraste sesion", Toast.LENGTH_LONG).show();
        finish();
        Intent intento = new Intent(this, MainActivity.class);
        startActivity(intento);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_inicio_admin);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}