package com.example.root.ganapae20.data.data.detail;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.root.ganapae20.R;
import com.example.root.ganapae20.data.data.Alerta;
import com.example.root.ganapae20.data.data.AlertaActivity;
import com.example.root.ganapae20.data.data.AlertaDbHelper;
import com.example.root.ganapae20.data.data.AlertaFragment;
import com.example.root.ganapae20.data.data.addeditAlerta.AddEditAlertaActivity;


public class AlertaDetailFragment extends Fragment {
    private static final String ARG_ALERTA_ID = "alertaId";
    private String mAlertaId;

    private CollapsingToolbarLayout mCollapsingView;
    private ImageView mAvatar;
    private TextView mSound;
    private TextView mLatitud;
    private TextView mLongitud;

    private AlertaDbHelper mAlertaDbHelper;



    public AlertaDetailFragment() {
        // Required empty public constructor
    }

    public static AlertaDetailFragment newInstance(String AlertaId) {
        AlertaDetailFragment fragment = new AlertaDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ALERTA_ID, AlertaId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mAlertaId = getArguments().getString(ARG_ALERTA_ID);
        }

        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_alerta_detail, container, false);
        mCollapsingView = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
        mAvatar = (ImageView) getActivity().findViewById(R.id.iv_avatar);
        mSound =(TextView) root.findViewById(R.id.tv_sound);
        mLatitud =(TextView) root.findViewById(R.id.tv_latitud);
        mLongitud =(TextView) root.findViewById(R.id.tv_longitud);
        mAlertaDbHelper = new AlertaDbHelper(getActivity());

        loadAlerta();

        return root;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                showEditScreen();
                break;
            case R.id.action_delete:
                new DeleteAlertaTask().execute();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class DeleteAlertaTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            return mAlertaDbHelper.deleteAlerta(mAlertaId);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            showAlertaScreen(integer > 0);
        }

    }

    private void showAlertaScreen(boolean requery) {
        if (!requery) {
            showDeleteError();
        }
        getActivity().setResult(requery ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
        getActivity().finish();
    }

    private void showDeleteError() {
        Toast.makeText(getActivity(),
                "Error al eliminar alerta", Toast.LENGTH_SHORT).show();
    }

    private void showEditScreen() {
        Intent intent = new Intent(getActivity(), AddEditAlertaActivity.class);
        intent.putExtra(AlertaActivity.EXTRA_ALERTA_ID, mAlertaId);
        startActivityForResult(intent, AlertaFragment.REQUEST_UPDATE_DELETE_ALERTA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case 2:
                    loadAlerta();
                    break;
            }
        }

    }



    private void loadAlerta() {
        new GetAlertaByIdTask().execute();
    }

    private void showAlerta(Alerta Alerta) {
        mCollapsingView.setTitle(Alerta.getText());
        Glide.with(this)
                .load(Uri.parse("file:///android_asset/" + Alerta.getImage()))
                .centerCrop()
                .into(mAvatar);

        mSound.setText(Alerta.getSound());
        mLatitud.setText(Alerta.getLatitud());
        mLongitud.setText(Alerta.getLongitud());
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al cargar información", Toast.LENGTH_SHORT).show();
    }

    private class GetAlertaByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mAlertaDbHelper.getAlertaById(mAlertaId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showAlerta(new Alerta(cursor));
            } else {
                showLoadError();
            }
        }

    }

}