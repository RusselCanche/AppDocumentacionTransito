package com.example.appdocumentaciontransito;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GPSActivity extends AppCompatActivity {
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationManager locationManager;
    private TextView tv_latitude, tv_longitude, tv_fecha, tv_estado;
    private String estado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpsactivity);

        tv_latitude = findViewById(R.id.latitud);
        tv_longitude = findViewById(R.id.longitud);
        tv_fecha = findViewById(R.id.fecha);
        tv_estado = findViewById(R.id.estado);

        estado="";

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(GPSActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(GPSActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(GPSActivity.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                Date fechaHoy = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                sdf.format(fechaHoy);
                /*String hora = new SimpleDateFormat("HH:mm:ss").format(fechaHoy);
                 */
                tv_latitude.setText(String.valueOf(location.getLatitude()));
                tv_longitude.setText(String.valueOf(location.getLongitude()));
                tv_fecha.setText(fechaHoy.toString());
                //tv_hora.setText(hora);

                Geocoder geocoder = new Geocoder(GPSActivity.this, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    if (!addresses.isEmpty()) {
                        Address address = addresses.get(0);
                        estado = address.getAdminArea(); // Obtener el estado desde la dirección
                        switch (estado){
                            case "Mexico City":
                                estado = "Ciudad de México";
                                break;
                            case "Coahuila de Zaragoza":
                                estado = "Coahuila";
                                break;
                            case "State of Mexico":
                                estado = "Estado de México";
                                break;
                            case "Yucatan":
                                estado = "Yucatán";
                                break;

                        }
                        tv_estado.setText("Te encuentras actualmente en: "+estado);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override public void onProviderEnabled(@NonNull String provider) {

            }

            @Override public void onProviderDisabled(@NonNull String provider) {

            }

            @Override public void onStatusChanged(String provider, int status, Bundle extras) {

            }
        });




    }
}