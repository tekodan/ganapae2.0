package com.example.root.ganapae20.data.data;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.root.ganapae20.R;

public class AlertaActivity extends AppCompatActivity {
    public static final String EXTRA_ALERTA_ID = "extra_alerta_id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AlertaFragment fragment = (AlertaFragment)
                getSupportFragmentManager().findFragmentById(R.id.alerta_container);

        if (fragment == null) {
            fragment = AlertaFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.alerta_container, fragment)
                    .commit();
        }
    }
}

