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

public class DatosLicenciaActivity extends AppCompatActivity {
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
    private TablaLicencia tablaLicencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_licencia);
        tablaLicencia = new TablaLicencia(this);
        Licencia licencia;
        String numero = getIntent().getStringExtra("numeroLicencia");

        numeroLicencia = findViewById(R.id.edit_numero_licencia_cons);
        tipo = findViewById(R.id.edit_tipo_lic_cons);
        estadoEmision = findViewById(R.id.edit_estado_emision_cons);

        dpFechaEmision = (DatePicker) findViewById(R.id.dpFechaEmisionCons);
        btnFechaEmision = (Button) findViewById(R.id.btnFechaEmisionCons);
        fechaEmision = (EditText) findViewById(R.id.edit_fecha_emision_cons);

        dpFechaExpiracion = (DatePicker) findViewById(R.id.dpFechaExpiracionCons);
        btnFechaExpiracion = (Button) findViewById(R.id.btnFechaExpiracionCons);
        fechaExpiracion = (EditText) findViewById(R.id.edit_fecha_expiracion_cons);

        tvURL = findViewById(R.id.tv_url_cons);
        imageLicencia = findViewById(R.id.image_licencia_cons);
        btnExplorador = findViewById(R.id.btnBuscarImgCons);
        btnExplorador.setOnClickListener(v -> { this.cargarImagen(); });

        dpFechaEmision.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                fechaEmision.setText(getFechaPicker(dpFechaEmision));
                dpFechaEmision.setVisibility(View.GONE);
            }
        });

        dpFechaExpiracion.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                fechaExpiracion.setText(getFechaPicker(dpFechaExpiracion));
                dpFechaExpiracion.setVisibility(View.GONE);
            }
        });

        licencia = tablaLicencia.obtenerLicencia(numero);
        numeroLicencia.setText(licencia.getNumeroLicencia());
        numeroLicencia.setEnabled(false);
        tipo.setText(licencia.getTipo());
        fechaEmision.setText(licencia.getFechaEmision());
        fechaExpiracion.setText(licencia.getFechaExpiracion());
        estadoEmision.setText(licencia.getEstadoEmision());
        tvURL.setText(licencia.getImagenLicencia());

        Uri myUri = (Uri.parse(licencia.getImagenLicencia()));
        imageLicencia.setImageURI(myUri);
        imageLicencia.setVisibility(View.VISIBLE);
    }

    public void modificar(View v) {
        if (!hayCajasVacias()) {
            SharedPreferences preferences = getSharedPreferences("sesion", Context.MODE_PRIVATE);
            String curp = preferences.getString("curp_usuario", "");
            TablaLicencia tablaLicencia = new TablaLicencia(this);
            Licencia licencia = new Licencia();

            licencia.setNumeroLicencia(numeroLicencia.getText().toString());
            licencia.setTipo(tipo.getText().toString());
            licencia.setFechaEmision(fechaEmision.getText().toString());
            licencia.setFechaExpiracion(fechaExpiracion.getText().toString());
            licencia.setEstadoEmision(estadoEmision.getText().toString());
            licencia.setImagenLicencia(tvURL.getText().toString());
            licencia.setCurpPropietario(curp);
            tablaLicencia.modificar(licencia);
            tablaLicencia.actualizarCursorPropietario(curp);

            Toast.makeText(this, "Sus datos se han actualizado correctamente", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "¡Debe de ingresar todos los datos!", Toast.LENGTH_SHORT).show();
    }

    public void eliminar(View v){
        String numero = numeroLicencia.getText().toString();
        SharedPreferences preferences = getSharedPreferences("sesion", Context.MODE_PRIVATE);
        int n = tablaLicencia.eliminar(numero, preferences.getString("curp_usuario", ""));
        Toast.makeText(this, "Licencia eliminada exitosamente", Toast.LENGTH_LONG).show();
    }

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
}