package com.example.appdocumentaciontransito;

import static com.example.appdocumentaciontransito.utileria.Utileria.getFechaPicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appdocumentaciontransito.modelo.VerificacionVehicular;
import com.example.appdocumentaciontransito.tablas.TablaVerificacionVehicular;

import java.io.File;

public class RegistrarVerificacionActivity extends AppCompatActivity {
    private EditText folio;
    private EditText tipoCombustible;
    private EditText resultado;
    private EditText fechaVerificacion;
    private EditText fechaProxima;
    private TextView urlVerificacion;
    private ImageView imageVerificacion;
    private File file;
    private DatePicker dpFechaVerificacion;
    private DatePicker dpProximaVerificacion;
    private ImageButton btnFechaVerificacion;
    private ImageButton btnProximaVerificacion;
    private ImageButton btnExploradorImgVerificacion;
    private TablaVerificacionVehicular tablaVerificacion;
    private String numeroSerie;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_verificacion);

        VerificacionVehicular verificacion;
        numeroSerie = getIntent().getStringExtra("numeroSerie");
        tablaVerificacion = new TablaVerificacionVehicular(this);

        folio = findViewById(R.id.edit_folio);
        tipoCombustible = findViewById(R.id.edit_tipo_combustible);
        resultado = findViewById(R.id.edit_resultado);
        fechaVerificacion = findViewById(R.id.edit_fecha_verificacion);
        fechaProxima = findViewById(R.id.edit_proxima_fecha_verificacion);
        urlVerificacion = findViewById(R.id.tv_url_verificacion);
        imageVerificacion = findViewById(R.id.image_verificacion);

        dpFechaVerificacion = findViewById(R.id.dpFechaVerificacion);
        dpProximaVerificacion = findViewById(R.id.dpProximaFechaVerificacion);
        btnFechaVerificacion = findViewById(R.id.btnFechaVerificacion);
        btnProximaVerificacion = findViewById(R.id.btnProximaFechaVerificacion);
        btnExploradorImgVerificacion = findViewById(R.id.btn_buscar_img_verificacion);
        btnExploradorImgVerificacion.setOnClickListener(v -> { this.cargarImagen(); });

        fechaVerificacion.setText(getFechaPicker(dpFechaVerificacion));
        dpFechaVerificacion.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                fechaVerificacion.setText(getFechaPicker(dpFechaVerificacion));
                //dpFechaVerificacion.setVisibility(View.GONE);
            }
        });

        fechaProxima.setText(getFechaPicker(dpProximaVerificacion));
        dpProximaVerificacion.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                fechaProxima.setText(getFechaPicker(dpProximaVerificacion));
                //dpProximaVerificacion.setVisibility(View.GONE);
            }
        });
    }

    public void muestraCalendarioVerificacion(View view){
        if(dpFechaVerificacion.getVisibility() == View.VISIBLE){
            dpFechaVerificacion.setVisibility(View.GONE);
        }else{
            dpFechaVerificacion.setVisibility(View.VISIBLE);
        }
    }
    public void muestraCalendarioProxVerificacion(View view){
        if(dpProximaVerificacion.getVisibility() == View.VISIBLE){
            dpProximaVerificacion.setVisibility(View.GONE);
        }else{
            dpProximaVerificacion.setVisibility(View.VISIBLE);
        }
    }
    public void guardar(View v) {
        if (!hayCajasVacias()) {
            TablaVerificacionVehicular tablaVerificacion = new TablaVerificacionVehicular(this);
            VerificacionVehicular verificacion = new VerificacionVehicular();
            if(!tablaVerificacion.existe(numeroSerie)){
                verificacion.setFolio(folio.getText().toString());
                verificacion.setTipoCombustible(tipoCombustible.getText().toString());
                verificacion.setResultado(resultado.getText().toString());
                verificacion.setFechaVerificacion(fechaVerificacion.getText().toString());
                verificacion.setFechaProximaVerificacion(fechaProxima.getText().toString());
                verificacion.setImagenVerificacion(urlVerificacion.getText().toString());
                verificacion.setNumeroSerieVehiculo(numeroSerie);
                tablaVerificacion.guardar(verificacion);

                Toast.makeText(this, "Sus datos se han registrado correctamente", Toast.LENGTH_SHORT).show();
                limpiarCajas();
            } else
                Toast.makeText(this, "Ese folio de verificación ya está registrado, verifica tus datos", Toast.LENGTH_SHORT).show();
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
            urlVerificacion.setText(file.getAbsolutePath());
            imageVerificacion.setImageURI(uri);
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
        boolean b1 = folio.getText().toString().equals("");
        boolean b2 = tipoCombustible.getText().toString().equals("");
        boolean b3 = fechaVerificacion.getText().toString().equals("");
        boolean b4 = fechaProxima.getText().toString().equals("");
        boolean b5 = resultado.getText().toString().equals("");
        boolean b6 = urlVerificacion.getText().toString().equals("");
        if(b1||b2||b3||b4||b5||b6)
            return true;
        return false;
    }

    public void limpiarCajas(){
        folio.setText("");
        tipoCombustible.setText("");
        fechaVerificacion.setText("");
        fechaProxima.setText("");
        resultado.setText("");
        urlVerificacion.setText("");
        folio.requestFocus();
        imageVerificacion.setVisibility(View.GONE);
    }
}