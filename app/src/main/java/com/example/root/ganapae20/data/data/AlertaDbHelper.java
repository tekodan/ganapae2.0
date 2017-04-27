package com.example.root.ganapae20.data.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by root on 3/7/17.
 */
public class AlertaDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Alerta24.db2";

    public AlertaDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No hay operaciones
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + AlertaContract.AlertaEntry.TABLE_NAME + " ("
                + AlertaContract.AlertaEntry.ID + " TEXT NOT NULL,"
                + AlertaContract.AlertaEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + AlertaContract.AlertaEntry.TEXT + " TEXT NOT NULL,"
                + AlertaContract.AlertaEntry.SOUND + " TEXT NOT NULL,"
                + AlertaContract.AlertaEntry.IMAGE + " TEXT NOT NULL,"
                + AlertaContract.AlertaEntry.LATITUD + " TEXT NOT NULL,"
                + AlertaContract.AlertaEntry.LONGITUD + " TEXT,"
                + AlertaContract.AlertaEntry.CANAL + " TEXT NOT NULL,"
                + AlertaContract.AlertaEntry.USUARIO + " TEXT NOT NULL,"
                + AlertaContract.AlertaEntry.FECHA + " DATE,"
                + "UNIQUE (" + AlertaContract.AlertaEntry.ID + "))");

        mockData(db);



    }


    private void mockData(SQLiteDatabase sqLiteDatabase) {
        mockAlerta(sqLiteDatabase, new Alerta("'hola esto es una alerta'", "'sonido'",
                "'imagen'", "'latitud'",
                "'longitud'","'canal'","'usuario'","'2016-01-01'"));
        mockAlerta(sqLiteDatabase, new Alerta("'hola esto es una alerta'", "'sonido'",
                "'imagen'", "'latitud'",
                "'longitud'","'canal'","'usuario'","'2016-01-01'"));

    }

    public long mockAlerta(SQLiteDatabase db, Alerta Alerta) {
        return db.insert(
                AlertaContract.AlertaEntry.TABLE_NAME,
                null,
                Alerta.toContentValues());
    }


    public int deleteAlerta(String AlertaId) {
        return getWritableDatabase().delete(
                AlertaContract.AlertaEntry.TABLE_NAME,
                AlertaContract.AlertaEntry.ID + " LIKE ?",
                new String[]{AlertaId});
    }


    public long saveAlerta(Alerta Alerta) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                AlertaContract.AlertaEntry.TABLE_NAME,
                null,
                Alerta.toContentValues());

    }


    public Cursor getAllAlertas() {
        return getReadableDatabase()
                .query(
                        AlertaContract.AlertaEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }


    public Cursor getAlertaById(String alertaId) {
        Cursor c = getReadableDatabase().query(
                AlertaContract.AlertaEntry.TABLE_NAME,
                null,
                AlertaContract.AlertaEntry.ID + " LIKE ?",
                new String[]{alertaId},
                null,
                null,
                null);
        return c;
    }

    public int updateAlerta(Alerta alerta, String alertaId) {
        return getWritableDatabase().update(
                AlertaContract.AlertaEntry.TABLE_NAME,
                alerta.toContentValues(),
                AlertaContract.AlertaEntry.ID + " LIKE ?",
                new String[]{alertaId}
        );
    }


}

