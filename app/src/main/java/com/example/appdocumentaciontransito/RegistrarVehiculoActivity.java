package com.example.appdocumentaciontransito;

import static com.example.appdocumentaciontransito.utileria.Utileria.getFechaPicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.appdocumentaciontransito.modelo.TarjetaCirculacion;
import com.example.appdocumentaciontransito.modelo.Vehiculo;
import com.example.appdocumentaciontransito.tablas.TablaTarjetaCirculacion;
import com.example.appdocumentaciontransito.tablas.TablaVehiculo;

import java.io.File;

public class RegistrarVehiculoActivity extends AppCompatActivity {
    private EditText numeroSerieV;
    private EditText numeroPlacas;
    private EditText marca;
    private EditText modelo;
    private EditText anio;
    private EditText tipo;
    private EditText numeroMotor;

    private EditText numeroTarjeta;
    private EditText fechaEmision;
    private EditText fechaExpiracion;
    private EditText estadoEmision;
    private TextView urlTarjeta;
    private ImageView imageTarjeta;

    private File file;
    private DatePicker dpFechaEmision;
    private DatePicker dpFechaExpiracion;
    private ImageButton btnFechaEmision;
    private ImageButton btnFechaExpiracion;
    private ImageButton btnExploradorImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_vehiculo);

        numeroSerieV = findViewById(R.id.edit_numero_serie);
        numeroPlacas = findViewById(R.id.edit_numero_placas);
        marca = findViewById(R.id.edit_marca);
        modelo = findViewById(R.id.edit_modelo);
        anio = findViewById(R.id.edit_anio);
        tipo = findViewById(R.id.edit_tipo_vehiculo);
        numeroMotor = findViewById(R.id.edit_numero_motor);

        numeroTarjeta = findViewById(R.id.edit_numero_tarjeta);
        fechaEmision = findViewById(R.id.edit_fecha_emision_circulacion);
        fechaExpiracion = findViewById(R.id.edit_fecha_expiracion_circulacion);
        estadoEmision = findViewById(R.id.edit_estado_tarjeta);
        urlTarjeta = findViewById(R.id.tv_url_tarjeta);
        imageTarjeta = findViewById(R.id.image_tarjeta);

        dpFechaEmision = findViewById(R.id.dpFechaEmisionCirculacion);
        dpFechaExpiracion = findViewById(R.id.dpFechaExpiracionCirculacion);
        btnFechaEmision = findViewById(R.id.btnFechaEmisionCirculacion);
        btnFechaExpiracion = findViewById(R.id.btnFechaExpiracionCirculacion);
        btnExploradorImg = findViewById(R.id.btn_buscar_img_tarjeta);
        btnExploradorImg.setOnClickListener(v -> { this.cargarImagen(); });

        fechaEmision.setText(getFechaPicker(dpFechaEmision));
        dpFechaEmision.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                fechaEmision.setText(getFechaPicker(dpFechaEmision));
                dpFechaEmision.setVisibility(View.GONE);
            }
        });

        fechaExpiracion.setText(getFechaPicker(dpFechaExpiracion));
        dpFechaExpiracion.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                fechaExpiracion.setText(getFechaPicker(dpFechaExpiracion));
                dpFechaExpiracion.setVisibility(View.GONE);
            }
        });
    }

    public void guardar(View v) {
        if (!hayCajasVacias()) {
            SharedPreferences preferences = getSharedPreferences("sesion", Context.MODE_PRIVATE);
            TablaVehiculo tablaVehiculo = new TablaVehiculo(this);
            TablaTarjetaCirculacion tablaTarjetaCirculacion = new TablaTarjetaCirculacion(this);
            Vehiculo vehiculo = new Vehiculo();
            TarjetaCirculacion tarjetaCirculacion = new TarjetaCirculacion();
            if(!tablaVehiculo.existe(numeroSerieV.getText().toString()) && !tablaTarjetaCirculacion.existe(numeroTarjeta.getText().toString())){
                vehiculo.setNumeroSerie(numeroSerieV.getText().toString());
                vehiculo.setNumeroPlacas(numeroPlacas.getText().toString());
                vehiculo.setMarca(marca.getText().toString());
                vehiculo.setModelo(modelo.getText().toString());
                vehiculo.setTipo(tipo.getText().toString());
                vehiculo.setAnio(anio.getText().toString());
                vehiculo.setNumeroMotor(numeroMotor.getText().toString());
                vehiculo.setCurpPropietario(preferences.getString("curp_usuario", ""));
                tablaVehiculo.guardar(vehiculo);

                tarjetaCirculacion.setNumeroSerieTarjeta(numeroTarjeta.getText().toString());
                tarjetaCirculacion.setFechaEmision(fechaEmision.getText().toString());
                tarjetaCirculacion.setFechaExpiracion(fechaExpiracion.getText().toString());
                tarjetaCirculacion.setEstadoEmision(estadoEmision.getText().toString());
                tarjetaCirculacion.setImagenTarjeta(urlTarjeta.getText().toString());
                tarjetaCirculacion.setNumeroSerieVehiculo(numeroSerieV.getText().toString());
                tablaTarjetaCirculacion.registrar(tarjetaCirculacion);

                Toast.makeText(this, "Sus datos se han registrado correctamente", Toast.LENGTH_SHORT).show();
                limpiarCajas();
            } else
                Toast.makeText(this, "Ese número de serie y/o numero de tarjeta ya están registrados, verifica tus datos", Toast.LENGTH_SHORT).show();
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
            urlTarjeta.setText(file.getAbsolutePath());
            imageTarjeta.setImageURI(uri);
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
        boolean b1 = numeroSerieV.getText().toString().equals("");
        boolean b2 = numeroPlacas.getText().toString().equals("");
        boolean b3 = tipo.getText().toString().equals("");
        boolean b4 = marca.getText().toString().equals("");
        boolean b5 = modelo.getText().toString().equals("");
        boolean b6 = anio.getText().toString().equals("");
        boolean b7 = numeroMotor.getText().toString().equals("");

        boolean b8 = numeroTarjeta.getText().toString().equals("");
        boolean b9 = fechaEmision.getText().toString().equals("");
        boolean b10 = fechaExpiracion.getText().toString().equals("");
        boolean b11 = estadoEmision.getText().toString().equals("");
        boolean b12 = urlTarjeta.getText().toString().equals("");
        if(b1||b2||b3||b4||b5||b6||b7||b8||b9||b10||b11||b12)
            return true;
        return false;
    }

    public void limpiarCajas(){
        numeroSerieV.setText("");
        numeroPlacas.setText("");
        tipo.setText("");
        marca.setText("");
        modelo.setText("");
        anio.setText("");
        numeroMotor.setText("");

        numeroTarjeta.setText("");
        fechaEmision.setText("");
        fechaExpiracion.setText("");
        estadoEmision.setText("");
        urlTarjeta.setText("");
        numeroSerieV.requestFocus();
        imageTarjeta.setVisibility(View.GONE);
    }
}