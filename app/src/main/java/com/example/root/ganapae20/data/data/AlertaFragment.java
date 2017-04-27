package com.example.root.ganapae20.data.data;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.root.ganapae20.R;
import com.example.root.ganapae20.data.data.detail.AlertaDetailActivity;

public class AlertaFragment extends Fragment {


    public static final int REQUEST_UPDATE_DELETE_ALERTA = 2;
    private AlertaDbHelper mAlertaDbHelper;

    private ListView mAlertaList;
    private AlertaCursorAdapter mAlertaAdapter;
    private FloatingActionButton mAddButton;

    public AlertaFragment() {
        // Required empty public constructor
    }

    public static AlertaFragment newInstance() {
        return new AlertaFragment();
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_alerta, container, false);

        // Referencias UI
        mAlertaList = (ListView) root.findViewById(R.id.alertas_list);
        mAlertaAdapter = new AlertaCursorAdapter(getActivity(), null);
        mAddButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        // Setup
        mAlertaList.setAdapter(mAlertaAdapter);

        // Instancia de helper
        mAlertaDbHelper = new AlertaDbHelper(getActivity());

        // Carga de datos
        loadAlerta();

        mAlertaList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentItem = (Cursor) mAlertaAdapter.getItem(i);
                String currentAlertaId = currentItem.getString(
                        currentItem.getColumnIndex(AlertaContract.AlertaEntry.ID));

                showDetailScreen(currentAlertaId);
            }
        });



        return root;


        // Eventos


    }




    private void showDetailScreen(String alertaId) {
        Intent intent = new Intent(getActivity(), AlertaDetailActivity.class);
        intent.putExtra(AlertaActivity.EXTRA_ALERTA_ID, alertaId);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_ALERTA);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case REQUEST_UPDATE_DELETE_ALERTA:
                    loadAlerta();
                    break;
            }
        }

    }

    private void loadAlerta() {
        new AlertaLoadTask().execute();
    }

    private class AlertaLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mAlertaDbHelper.getAllAlertas();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                mAlertaAdapter.swapCursor(cursor);
            } else {
                // Mostrar empty state
            }
        }
    }

}
