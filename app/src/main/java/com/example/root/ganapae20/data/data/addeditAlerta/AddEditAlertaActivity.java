package com.example.root.ganapae20.data.data.addeditAlerta;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.root.ganapae20.R;
import com.example.root.ganapae20.data.data.AlertaActivity;



    public class AddEditAlertaActivity extends AppCompatActivity {

        public static final int REQUEST_ADD_ALERTA = 1;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_edit_alerta);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            String AlertaId = getIntent().getStringExtra(AlertaActivity.EXTRA_ALERTA_ID);

            setTitle(AlertaId == null ? "Añadir Alerta" : "Editar Alerta");

            AddEditAlertaFragment addEditAlertaFragment = (AddEditAlertaFragment)
                    getSupportFragmentManager().findFragmentById(R.id.add_edit_alerta_container);
            if (addEditAlertaFragment == null) {
                addEditAlertaFragment = AddEditAlertaFragment.newInstance(AlertaId);
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.add_edit_alerta_container, addEditAlertaFragment)
                        .commit();
            }
        }

        @Override
        public boolean onSupportNavigateUp() {
            onBackPressed();
            return true;
        }
    }


