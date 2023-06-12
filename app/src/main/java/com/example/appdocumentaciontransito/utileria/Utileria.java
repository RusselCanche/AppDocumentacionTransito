package com.example.appdocumentaciontransito.utileria;

import android.view.View;
import android.widget.DatePicker;

public class Utileria {
    public static String getFechaPicker(DatePicker dpFecha){
        String dia = String.format("%02d", dpFecha.getDayOfMonth());
        String mes = String.format("%02d", dpFecha.getMonth() + 1);
        String anio = String.format("%04d", dpFecha.getYear());

        return anio + "-" + mes + "-" + dia;
    }

}
