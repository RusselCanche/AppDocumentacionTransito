package com.example.appdocumentaciontransito;

import static com.example.appdocumentaciontransito.utileria.Utileria.getFechaPicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appdocumentaciontransito.modelo.Licencia;
import com.example.appdocumentaciontransito.tablas.TablaLicencia;

import java.io.File;

public class RegistrarLicenciaActivity extends AppCompatActivity {
    private EditText numeroLicencia;
    private EditText tipo;
    private EditText fechaEmision;
    private EditText fechaExpiracion;
    private EditText estadoEmision;
    private DatePicker dpFechaEmision;
    private DatePicker dpFechaExpiracion;
    private Button btnFechaEmision;
    private Button btnFechaExpiracion;

    private File file;
    private Button btnExplorador;
    private TextView tvURL;
    private ImageView imageLicencia;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_licencia);

        numeroLicencia = findViewById(R.id.edit_numero_licencia);
        tipo = findViewById(R.id.edit_tipo_lic);
        estadoEmision = findViewById(R.id.edit_estado_emision);

        dpFechaEmision = (DatePicker) findViewById(R.id.dpFechaEmision);
        btnFechaEmision = (Button) findViewById(R.id.btnFechaEmision);
        fechaEmision = (EditText) findViewById(R.id.edit_fecha_emision);

        dpFechaExpiracion = (DatePicker) findViewById(R.id.dpFechaExpiracion);
        btnFechaExpiracion = (Button) findViewById(R.id.btnFechaExpiracion);
        fechaExpiracion = (EditText) findViewById(R.id.edit_fecha_expiracion);

        tvURL = findViewById(R.id.tv_url);
        imageLicencia = findViewById(R.id.image_licencia);
        btnExplorador = findViewById(R.id.btn);
        btnExplorador.setOnClickListener(v -> { this.cargarImagen(); });

        fechaEmision.setText(getFechaPicker(dpFechaEmision));
        dpFechaEmision.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                fechaEmision.setText(getFechaPicker(dpFechaEmision));
                //dpFechaEmision.setVisibility(View.GONE);
            }
        });

        fechaExpiracion.setText(getFechaPicker(dpFechaExpiracion));
        dpFechaExpiracion.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                fechaExpiracion.setText(getFechaPicker(dpFechaExpiracion));
                //dpFechaExpiracion.setVisibility(View.GONE);
            }
        });
    }

    public void guardar(View v) {
        if (!hayCajasVacias()) {
            SharedPreferences preferences = getSharedPreferences("sesion", Context.MODE_PRIVATE);
            TablaLicencia tablaLicencia = new TablaLicencia(this);
            Licencia licencia = new Licencia();
            if(!tablaLicencia.existe(numeroLicencia.getText().toString())){
                licencia.setNumeroLicencia(numeroLicencia.getText().toString());
                licencia.setTipo(tipo.getText().toString());
                licencia.setFechaEmision(fechaEmision.getText().toString());
                licencia.setFechaExpiracion(fechaExpiracion.getText().toString());
                licencia.setEstadoEmision(estadoEmision.getText().toString());
                licencia.setImagenLicencia(tvURL.getText().toString());
                licencia.setCurpPropietario(preferences.getString("curp_usuario", ""));
                tablaLicencia.guardar(licencia);

                Toast.makeText(this, "Sus datos se han registrado correctamente", Toast.LENGTH_SHORT).show();
                limpiarCajas();
            } else
                Toast.makeText(this, "Ese número de licencia ya está registrado, verifica tus datos", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "¡Debe de ingresar todos los datos!", Toast.LENGTH_SHORT).show();
    }

    /* MANIPULA EL EXPLORADOR DE ARCHIVOS */
    public void cargarImagen() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/");
        startActivityForResult(Intent.createChooser(i, "Seleccione la aplicación"), 10);
    }

    /* FUNCIONA EN TIEMPO REAL, AGREGA LA URL Y LA IMAGEN UNA VEZ SELECCIONADA */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            final String realPath = getRealPathFromURI(uri);
            this.file = new File(realPath);
            tvURL.setText(file.getAbsolutePath());
            imageLicencia.setImageURI(uri);
        }
    }

    /* DEVUELVE EL PATH DE LA IMAGEN, PARA PODER ENVIARLO A LA BD */
    public String getRealPathFromURI(Uri contentUri) {
        String result;
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            result = contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    private boolean hayCajasVacias(){
        boolean b1 = numeroLicencia.getText().toString().equals("");
        boolean b2 = tipo.getText().toString().equals("");
        boolean b3 = fechaEmision.getText().toString().equals("");
        boolean b4 = fechaExpiracion.getText().toString().equals("");
        boolean b5 = estadoEmision.getText().toString().equals("");
        boolean b6 = tvURL.getText().toString().equals("");
        if(b1||b2||b3||b4||b5||b6)
            return true;
        return false;
    }

    public void limpiarCajas(){
        numeroLicencia.setText("");
        tipo.setText("");
        fechaEmision.setText("");
        fechaExpiracion.setText("");
        estadoEmision.setText("");
        tvURL.setText("");
        numeroLicencia.requestFocus();
        imageLicencia.setVisibility(View.GONE);
    }

    public void muestraCalendarioFemLicencia(View view){
        if(dpFechaEmision.getVisibility() == View.VISIBLE){
            dpFechaEmision.setVisibility(View.GONE);
        }else{
            dpFechaEmision.setVisibility(View.VISIBLE);
        }
    }
    public void muestraCalendarioFexLicencia(View view){
        if(dpFechaExpiracion.getVisibility() == View.VISIBLE){
            dpFechaExpiracion.setVisibility(View.GONE);
        }else{
            dpFechaExpiracion.setVisibility(View.VISIBLE);
        }
    }
}