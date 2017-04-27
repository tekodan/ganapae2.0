package com.example.root.ganapae20.data.data;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.Date;
import java.util.UUID;

/**
 * Created by root on 3/6/17.
 */
public class Alerta {
    private String id;
    private String text;
    private String image;
    private String sound;
    private String latitud;
    private String longitud;
    private String canal;
    private String usuario;
    private String fecha;

    public Alerta(String text, String image, String sound, String latitud, String longitud, String canal, String usuario, String fecha){
        this.id ="'"+UUID.randomUUID().toString()+"'";
        this.text=text;
        this.image=image;
        this.sound=sound;
        this.latitud=latitud;
        this.longitud=longitud;
        this.canal=canal;
        this.usuario=usuario;
        this.fecha=fecha;
    }


    public String getText(){
        return this.text;
    }

    public String getImage(){
        return this.image;
    }

    public String getSound(){
        return this.sound;
    }

    public String getLatitud(){
        return this.latitud;
    }

    public String getLongitud(){return this.longitud; }

    public String getCanal(){return this.canal; }

    public String getUsuario(){return this.usuario; }

    public String getFecha(){return this.fecha; }


    public Alerta(Cursor cursor) {

        id = cursor.getString(cursor.getColumnIndex(AlertaContract.AlertaEntry.ID));
        text = cursor.getString(cursor.getColumnIndex(AlertaContract.AlertaEntry.TEXT));
        image = cursor.getString(cursor.getColumnIndex(AlertaContract.AlertaEntry.IMAGE));
        latitud = cursor.getString(cursor.getColumnIndex(AlertaContract.AlertaEntry.LATITUD));
        longitud = cursor.getString(cursor.getColumnIndex(AlertaContract.AlertaEntry.LONGITUD));
        canal = cursor.getString(cursor.getColumnIndex(AlertaContract.AlertaEntry.CANAL));
        usuario = cursor.getString(cursor.getColumnIndex(AlertaContract.AlertaEntry.USUARIO));
        fecha = cursor.getString(cursor.getColumnIndex(AlertaContract.AlertaEntry.FECHA));
    }
    public  ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(AlertaContract.AlertaEntry.ID, id);
        values.put(AlertaContract.AlertaEntry.TEXT, text);
        values.put(AlertaContract.AlertaEntry.SOUND, sound);
        values.put(AlertaContract.AlertaEntry.IMAGE, image);
        values.put(AlertaContract.AlertaEntry.LATITUD, latitud);
        values.put(AlertaContract.AlertaEntry.LONGITUD, longitud);
        values.put(AlertaContract.AlertaEntry.CANAL,canal );
        values.put(AlertaContract.AlertaEntry.USUARIO, usuario);
        values.put(AlertaContract.AlertaEntry.FECHA, fecha);
        return values;
    }



}
