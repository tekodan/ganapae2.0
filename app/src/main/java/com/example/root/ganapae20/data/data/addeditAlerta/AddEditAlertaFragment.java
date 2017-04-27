package com.example.root.ganapae20.data.data.addeditAlerta;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.ganapae20.R;
import com.example.root.ganapae20.data.data.Alerta;
import com.example.root.ganapae20.data.data.AlertaDbHelper;

import java.io.File;
import java.io.IOException;

public class AddEditAlertaFragment extends Fragment implements MediaPlayer.OnCompletionListener {
    private static final String ARG_ALERTA_ID = "arg_alerta_id";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int RESULT_OK = -1;

    private String mAlertaId;

    private AlertaDbHelper mAlertaDbHelper;

    private FloatingActionButton mSaveButton;
    private ImageButton mFoto;
    private TextInputEditText mText;
    private ImageButton mSonido;
    private TextInputEditText mImage;
    private TextInputEditText mLatitud;
    private TextInputEditText mLongitud;
    private TextInputLayout mTextLabel;
    private TextInputLayout mImageLabel;
    private TextInputLayout mLatitudLabel;
    private ImageView mImageView;
    private TextInputLayout mLongitudLabel;

    MediaRecorder recorder;
    MediaPlayer player;
    File archivo;
    Button b1, b2, b3;
    TextView tv1;

    private void llamarIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        System.out.println(requestCode);
        System.out.println(resultCode);

        System.out.println(REQUEST_IMAGE_CAPTURE);
        System.out.println(RESULT_OK);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            System.out.println("hola");
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
        }
    }



    public AddEditAlertaFragment() {
        // Required empty public constructor
    }

    public static AddEditAlertaFragment newInstance(String AlertaId) {
        AddEditAlertaFragment fragment = new AddEditAlertaFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_edit_alerta, container, false);

        // Referencias UI
        mSaveButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        mText = (TextInputEditText) root.findViewById(R.id.et_text);
        mImage = (TextInputEditText) root.findViewById(R.id.et_image);
        mLatitud= (TextInputEditText) root.findViewById(R.id.et_latitud);
        mLongitud = (TextInputEditText) root.findViewById(R.id.et_longitud);
        mTextLabel = (TextInputLayout) root.findViewById(R.id.til_text);
        mImageLabel = (TextInputLayout) root.findViewById(R.id.til_image);
        mLatitudLabel = (TextInputLayout) root.findViewById(R.id.til_latitud);
        mLongitudLabel = (TextInputLayout) root.findViewById(R.id.til_longitud);

        //sonido
        tv1 = (TextView) root.findViewById(R.id.textView1);
        b1 = (Button) root.findViewById(R.id.button1);
        b2 = (Button) root.findViewById(R.id.button2);
        b3 = (Button) root.findViewById(R.id.button3);
        //fin sonido

        // Eventos
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditAlerta();
            }
        });
     mSonido =(ImageButton) root.findViewById(R.id.sonido);
        mImageView = (ImageView) root.findViewById(R.id.imageView);
        mFoto = (ImageButton) root.findViewById(R.id.foto);
b1.setOnClickListener(new View.OnClickListener() {
            @Override




            public void onClick(View view) {
                try {
                    System.out.println("hola0");
                    grabar(view);


                } catch (IllegalStateException e) {

                    System.out.println("problema");
                }






            }
        });





        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detener(view);
            }
        });
        mFoto.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                llamarIntent();
            }
        });



        mAlertaDbHelper = new AlertaDbHelper(getActivity());

        // Carga de datos
        if (mAlertaId != null) {
            loadAlerta();
        }

        return root;
    }



    private void loadAlerta() {
        // AsyncTask
    }

        private void addEditAlerta() {
            boolean error = false;

            String text = mText.getText().toString();
            String image = mImage.getText().toString();
            String latitud = mLatitud.getText().toString();
            String longitud = mLongitud.getText().toString();

            if (TextUtils.isEmpty(text)) {
                mTextLabel.setError(getString(R.string.field_error));
                error = true;
            }

            if (TextUtils.isEmpty(image)) {
                mImageLabel.setError(getString(R.string.field_error));
                error = true;
            }

            if (TextUtils.isEmpty(latitud)) {
                mLatitudLabel.setError(getString(R.string.field_error));
                error = true;
            }


            if (TextUtils.isEmpty(longitud)) {
                mLongitud.setError(getString(R.string.field_error));
                error = true;
            }

            if (error) {
                return;
            }

            Alerta alerta = new Alerta(text, image,"sonido", latitud, longitud, "canal","usuario","2016-01-01");

            new AddEditAlertaTask().execute(alerta);
           //siga aqui pofavor
        }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        b1.setEnabled(true);
        b2.setEnabled(true);
        b3.setEnabled(true);
        tv1.setText("Listo");
    }


    private class AddEditAlertaTask extends AsyncTask<Alerta, Void, Boolean> {

            @Override
            protected Boolean doInBackground(Alerta... Alerta) {
                if (mAlertaId != null) {
                    return mAlertaDbHelper.updateAlerta(Alerta[0], mAlertaId) > 0;

                } else {
                    return mAlertaDbHelper.saveAlerta(Alerta[0]) > 0;
                }

            }

            private void showAlertaScreen(Boolean requery) {
                if (!requery) {
                    showAddEditError();
                    getActivity().setResult(Activity.RESULT_CANCELED);
                } else {
                    getActivity().setResult(Activity.RESULT_OK);
                }

                getActivity().finish();
            }

            private void showAddEditError() {
                Toast.makeText(getActivity(),
                        "Error al agregar nueva información", Toast.LENGTH_SHORT).show();
            }


            @Override
            protected void onPostExecute(Boolean result) {
                showAlertaScreen(result);
            }

        }


    public void grabar(View v) {

        recorder = new MediaRecorder();
        System.out.println("hola1");

           recorder.setAudioSource(MediaRecorder.AudioSource.MIC);

System.out.println("hola");
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        File path = new File(Environment.getExternalStorageDirectory()
                .getPath());
        try {
            archivo = File.createTempFile("temporal", ".3gp", path);
        } catch (IOException e) {
        }
        recorder.setOutputFile(archivo.getAbsolutePath());
        try {
            recorder.prepare();
        } catch (IOException e) {
        }
        recorder.start();
        tv1.setText("Grabando");
        b1.setEnabled(false);
        b2.setEnabled(true);
    }

    public void detener(View v) {
        recorder.stop();
        recorder.release();
        player = new MediaPlayer();
        player.setOnCompletionListener(this);
        try {
            player.setDataSource(archivo.getAbsolutePath());
        } catch (IOException e) {
        }
        try {
            player.prepare();
        } catch (IOException e) {
        }
        b1.setEnabled(true);
        b2.setEnabled(false);
        b3.setEnabled(true);
        tv1.setText("Listo para reproducir");
    }

    public void reproducir(View v) {
        player.start();
        b1.setEnabled(false);
        b2.setEnabled(false);
        b3.setEnabled(false);
        tv1.setText("Reproduciendo");
    }







}