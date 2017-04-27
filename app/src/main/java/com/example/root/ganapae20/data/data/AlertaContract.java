package com.example.root.ganapae20.data.data;

import android.provider.BaseColumns;

import java.util.Date;

/**
 * Created by root on 3/7/17.
 */
public class AlertaContract {

    public static abstract class AlertaEntry implements BaseColumns {
        public static final String TABLE_NAME ="alerta";
        public static final String ID = "id";
        public static final String TEXT = "text";
        public static final String SOUND = "sound";
        public static final String IMAGE = "image";
        public static final String LATITUD = "latitud";
        public static final String LONGITUD= "longitud";
        public static final String CANAL = "canal";
        public static final String USUARIO = "usuario";
        public static final String FECHA= "fecha";
    }
}
