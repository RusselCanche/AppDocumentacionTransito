package com.example.appdocumentaciontransito.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.example.appdocumentaciontransito.ListadoLicenciasActivity;
import com.example.appdocumentaciontransito.ListadoMultasActivity;
import com.example.appdocumentaciontransito.ListadoMultasUsuarioActivity;
import com.example.appdocumentaciontransito.ListadoVehiculosActivity;
import com.example.appdocumentaciontransito.MiPerfilActivity;
import com.example.appdocumentaciontransito.R;
import com.example.appdocumentaciontransito.RegistroUsuarioActivity;
import com.example.appdocumentaciontransito.databinding.ActivityInicioUsuarioBinding;
import com.example.appdocumentaciontransito.tablas.PropietarioController;
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


public class InicioUsuarioActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityInicioUsuarioBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityInicioUsuarioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarInicioUsuario.toolbar);
        binding.appBarInicioUsuario.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_inicio_usuario);
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
                        intent = new Intent(InicioUsuarioActivity.this, MiPerfilActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_mis_licencias:
                        intent = new Intent(InicioUsuarioActivity.this, ListadoLicenciasActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_mis_vehiculos:
                        intent = new Intent(InicioUsuarioActivity.this, ListadoVehiculosActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_mis_multas:
                        intent = new Intent(InicioUsuarioActivity.this, ListadoMultasUsuarioActivity.class);
                        startActivity(intent);
                        break;
                }

                // Cerrar el DrawerLayout después de seleccionar una opción
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inicio_usuario, menu);
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
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_inicio_usuario);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}