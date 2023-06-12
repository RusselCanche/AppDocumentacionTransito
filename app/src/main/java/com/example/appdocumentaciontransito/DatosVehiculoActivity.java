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

import com.example.appdocumentaciontransito.modelo.TarjetaCirculacion;
import com.example.appdocumentaciontransito.modelo.Vehiculo;
import com.example.appdocumentaciontransito.modelo.VerificacionVehicular;
import com.example.appdocumentaciontransito.tablas.TablaTarjetaCirculacion;
import com.example.appdocumentaciontransito.tablas.TablaVehiculo;
import com.example.appdocumentaciontransito.tablas.TablaVerificacionVehicular;

import java.io.File;

public class DatosVehiculoActivity extends AppCompatActivity {
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

    private EditText folio;
    private EditText tipoCombustible;
    private EditText resultado;
    private EditText fechaVerificacion;
    private EditText fechaProxima;
    private TextView urlVerificacion;
    private ImageView imageVerificacion;

    private File file;
    private File fileVerificacion;
    private DatePicker dpFechaEmision;
    private DatePicker dpFechaExpiracion;
    private DatePicker dpFechaVerificacion;
    private DatePicker dpProximaVerificacion;
    private ImageButton btnFechaEmision;
    private ImageButton btnFechaExpiracion;
    private ImageButton btnFechaVerificacion;
    private ImageButton btnProximaVerificacion;
    private ImageButton btnExploradorImg;
    private ImageButton btnExploradorImgVerificacion;
    private TablaVehiculo tablaVehiculo;
    private TablaTarjetaCirculacion tablaTarjetaCirculacion;
    private TablaVerificacionVehicular tablaVerificacion;
    private Button btnVerificacion;
    private TextView tvTituloSeccion;
    private TextView tvFolio;
    private TextView tvTipoCombustible;
    private TextView tvResultado;
    private TextView tvFechaVerificacion;
    private TextView tvProximaVerificacion;
    private TextView tvImagen;
    private boolean tieneVerificacion;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_vehiculo);

        Vehiculo vehiculo;
        TarjetaCirculacion tarjetaCirculacion;
        VerificacionVehicular verificacion;
        String numeroSerie = getIntent().getStringExtra("numeroSerie");

        tablaVehiculo = new TablaVehiculo(this);
        tablaTarjetaCirculacion = new TablaTarjetaCirculacion(this);
        tablaVerificacion = new TablaVerificacionVehicular(this);
        tieneVerificacion = tablaVehiculo.tieneVerificacionVehicular(numeroSerie);
        btnVerificacion = findViewById(R.id.btn_verificacion_vehicular);

        tvTituloSeccion = findViewById(R.id.textView39);
        tvFolio = findViewById(R.id.textView40);
        tvTipoCombustible = findViewById(R.id.textView41);
        tvResultado = findViewById(R.id.textView42);
        tvFechaVerificacion = findViewById(R.id.textView43);
        tvProximaVerificacion = findViewById(R.id.textView44);
        tvImagen = findViewById(R.id.textView45);

        numeroSerieV = findViewById(R.id.edit_numero_serie_cons);
        numeroPlacas = findViewById(R.id.edit_numero_placas_cons);
        marca = findViewById(R.id.edit_marca_cons);
        modelo = findViewById(R.id.edit_modelo_cons);
        anio = findViewById(R.id.edit_anio_cons);
        tipo = findViewById(R.id.edit_tipo_vehiculo_cons);
        numeroMotor = findViewById(R.id.edit_numero_motor_cons);

        numeroTarjeta = findViewById(R.id.edit_numero_tarjeta_cons);
        fechaEmision = findViewById(R.id.edit_fecha_emision_circulacion_cons);
        fechaExpiracion = findViewById(R.id.edit_fecha_expiracion_circulacion_cons);
        estadoEmision = findViewById(R.id.edit_estado_tarjeta_cons);
        urlTarjeta = findViewById(R.id.tv_url_tarjeta_cons);
        imageTarjeta = findViewById(R.id.image_tarjeta_cons);

        folio = findViewById(R.id.edit_folio_verificacion_cons);
        tipoCombustible = findViewById(R.id.edit_tipo_combustible_cons);
        resultado = findViewById(R.id.edit_resultado_cons);
        fechaVerificacion = findViewById(R.id.edit_fecha_verificacion_cons);
        fechaProxima = findViewById(R.id.edit_proxima_fecha_verificacion_cons);
        urlVerificacion = findViewById(R.id.tv_url_verificacion_cons);
        imageVerificacion = findViewById(R.id.image_verificacion_cons);

        dpFechaEmision = findViewById(R.id.dpFechaEmisionCirculacionCons);
        dpFechaExpiracion = findViewById(R.id.dpFechaExpiracionCirculacionCons);
        dpFechaVerificacion = findViewById(R.id.dpFechaVerificacionCons);
        dpProximaVerificacion = findViewById(R.id.dpProximaFechaVerificacionCons);

        btnFechaEmision = findViewById(R.id.btnFechaEmisionCirculacionCons);
        btnFechaExpiracion = findViewById(R.id.btnFechaExpiracionCirculacionCons);
        btnFechaVerificacion = findViewById(R.id.btnFechaVerificacionCons);
        btnProximaVerificacion = findViewById(R.id.btnProximaFechaVerificacionCons);
        btnExploradorImg = findViewById(R.id.btn_buscar_img_tarjeta_cons);
        btnExploradorImg.setOnClickListener(v -> { this.cargarImagen(); });
        btnExploradorImgVerificacion = findViewById(R.id.btn_buscar_img_verificacion_cons);
        btnExploradorImgVerificacion.setOnClickListener(v -> { this.cargarImagen(); });

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

        fechaVerificacion.setText(getFechaPicker(dpFechaVerificacion));
        dpFechaVerificacion.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                fechaVerificacion.setText(getFechaPicker(dpFechaVerificacion));
                dpFechaVerificacion.setVisibility(View.GONE);
            }
        });

        fechaProxima.setText(getFechaPicker(dpProximaVerificacion));
        dpProximaVerificacion.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                fechaProxima.setText(getFechaPicker(dpProximaVerificacion));
                dpProximaVerificacion.setVisibility(View.GONE);
            }
        });

        vehiculo = tablaVehiculo.obtenerVehiculo(numeroSerie);
        numeroSerieV.setText(vehiculo.getNumeroSerie());
        numeroPlacas.setText(vehiculo.getNumeroPlacas());
        marca.setText(vehiculo.getMarca());
        modelo.setText(vehiculo.getModelo());
        anio.setText(vehiculo.getAnio());
        tipo.setText(vehiculo.getTipo());
        numeroMotor.setText(vehiculo.getNumeroMotor());

        tarjetaCirculacion = tablaTarjetaCirculacion.getTarjetaCirculacion(numeroSerie);
        numeroTarjeta.setText(tarjetaCirculacion.getNumeroSerieTarjeta());
        fechaEmision.setText(tarjetaCirculacion.getFechaEmision());
        fechaExpiracion.setText(tarjetaCirculacion.getFechaExpiracion());
        estadoEmision.setText(tarjetaCirculacion.getEstadoEmision());
        urlTarjeta.setText(tarjetaCirculacion.getImagenTarjeta());
        Uri myUri = (Uri.parse(tarjetaCirculacion.getImagenTarjeta()));
        imageTarjeta.setImageURI(myUri);
        imageTarjeta.setVisibility(View.VISIBLE);

        if(!tieneVerificacion){
            btnVerificacion.setVisibility(View.VISIBLE);
        } else {
            /* SÍ TIENE VERIFICACIÓN VEHICULAR: Mostrar elementos de verificación vehicular, con sus datos */
            verificacion = tablaVerificacion.obtenerVerificacion(numeroSerie);
            folio.setText(verificacion.getFolio());
            tipoCombustible.setText(verificacion.getTipoCombustible());
            resultado.setText(verificacion.getResultado());
            fechaVerificacion.setText(verificacion.getFechaVerificacion());
            fechaProxima.setText(verificacion.getFechaProximaVerificacion());
            urlVerificacion.setText(verificacion.getImagenVerificacion());
            Uri uri = (Uri.parse(verificacion.getImagenVerificacion()));
            imageVerificacion.setImageURI(uri);
            mostrarElementos();
        }
    }

    public void modificar(View v) {
        // DE FORMA OBLIGATORIA SE MODIFICA VEHICULO Y TARJETA_CIRCULACION, VERIFICACION_VEHICULAR SOLO SI TIENE REGISTRO
        if (!hayCajasVacias(tieneVerificacion)) {
            SharedPreferences preferences = getSharedPreferences("sesion", Context.MODE_PRIVATE);
            Vehiculo vehiculo = new Vehiculo();
            TarjetaCirculacion tarjetaCirculacion = new TarjetaCirculacion();
            VerificacionVehicular verificacion = new VerificacionVehicular();

            vehiculo.setNumeroSerie(numeroSerieV.getText().toString());
            vehiculo.setNumeroPlacas(numeroPlacas.getText().toString());
            vehiculo.setMarca(marca.getText().toString());
            vehiculo.setModelo(modelo.getText().toString());
            vehiculo.setTipo(tipo.getText().toString());
            vehiculo.setAnio(anio.getText().toString());
            vehiculo.setNumeroMotor(numeroMotor.getText().toString());
            vehiculo.setCurpPropietario(preferences.getString("curp_usuario", ""));
            tablaVehiculo.modificar(vehiculo);

            tarjetaCirculacion.setNumeroSerieTarjeta(numeroTarjeta.getText().toString());
            tarjetaCirculacion.setFechaEmision(fechaEmision.getText().toString());
            tarjetaCirculacion.setFechaExpiracion(fechaExpiracion.getText().toString());
            tarjetaCirculacion.setEstadoEmision(estadoEmision.getText().toString());
            tarjetaCirculacion.setImagenTarjeta(urlTarjeta.getText().toString());
            tarjetaCirculacion.setNumeroSerieVehiculo(numeroSerieV.getText().toString());
            tablaTarjetaCirculacion.modificar(tarjetaCirculacion);

            if(tieneVerificacion){
                verificacion.setFolio(folio.getText().toString());
                verificacion.setTipoCombustible(tipoCombustible.getText().toString());
                verificacion.setResultado(resultado.getText().toString());
                verificacion.setFechaVerificacion(fechaVerificacion.getText().toString());
                verificacion.setFechaProximaVerificacion(fechaProxima.getText().toString());
                verificacion.setImagenVerificacion(urlVerificacion.getText().toString());
                verificacion.setNumeroSerieVehiculo(numeroSerieV.getText().toString());
                tablaVerificacion.modificar(verificacion);
            }

            Toast.makeText(this, "Sus datos se han actualizado correctamente", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "¡Debe de ingresar todos los datos!", Toast.LENGTH_SHORT).show();
    }

    public void eliminar(View v){
        String numeroSerieVehiculo = numeroSerieV.getText().toString();
        tablaTarjetaCirculacion.eliminar(numeroSerieVehiculo);
        if(tieneVerificacion){ tablaVerificacion.eliminar(numeroSerieVehiculo); }
        tablaVehiculo.eliminar(numeroSerieVehiculo);

        Toast.makeText(this, "Se ha eliminado exitosamente", Toast.LENGTH_LONG).show();
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
            /* IDENTIFICAR CUÁL DE LAS DOS IMAGENES SE CAMBIÓ */
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

    public void onRegistrarVerificacion(View view){
        Intent intento = new Intent(this, RegistrarVerificacionActivity.class);
        intento.putExtra("numeroSerie", numeroSerieV.getText().toString());
        startActivity(intento);
    }

    public void mostrarElementos(){
        tvTituloSeccion.setVisibility(View.VISIBLE);
        tvFolio.setVisibility(View.VISIBLE);
        folio.setVisibility(View.VISIBLE);

        tvTipoCombustible.setVisibility(View.VISIBLE);
        tipoCombustible.setVisibility(View.VISIBLE);

        tvResultado.setVisibility(View.VISIBLE);
        resultado.setVisibility(View.VISIBLE);

        tvFechaVerificacion.setVisibility(View.VISIBLE);
        fechaVerificacion.setVisibility(View.VISIBLE);
        btnFechaVerificacion.setVisibility(View.VISIBLE);

        tvProximaVerificacion.setVisibility(View.VISIBLE);
        fechaProxima.setVisibility(View.VISIBLE);
        btnProximaVerificacion.setVisibility(View.VISIBLE);

        tvImagen.setVisibility(View.VISIBLE);
        urlVerificacion.setVisibility(View.VISIBLE);
        btnExploradorImgVerificacion.setVisibility(View.VISIBLE);
        imageVerificacion.setVisibility(View.VISIBLE);
    }

    private boolean hayCajasVacias(boolean v){
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

        if(v) {
            boolean b13 = folio.getText().toString().equals("");
            boolean b14 = tipoCombustible.getText().toString().equals("");
            boolean b15 = resultado.getText().toString().equals("");
            boolean b16 = fechaVerificacion.getText().toString().equals("");
            boolean b17 = fechaProxima.getText().toString().equals("");
            boolean b18 = urlVerificacion.getText().toString().equals("");

            if (b1||b2||b3||b4||b5||b6||b7||b8||b9||b10||b11||b12||b13||b14||b15||b16||b17||b18)
                return true;
        } else
            if(b1||b2||b3||b4||b5||b6||b7||b8||b9||b10||b11||b12)
                return true;
        return false;
    }

    public void limpiarCajas(boolean v){
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
        imageTarjeta.setVisibility(View.GONE);

        if(v){
            folio.setText("");
            tipoCombustible.setText("");
            resultado.setText("");
            fechaVerificacion.setText("");
            fechaProxima.setText("");
            urlVerificacion.setText("");
            imageVerificacion.setVisibility(View.GONE);
        }

        numeroSerieV.requestFocus();
    }
}