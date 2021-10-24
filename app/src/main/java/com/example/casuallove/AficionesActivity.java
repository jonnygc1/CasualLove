package com.example.casuallove;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class AficionesActivity extends AppCompatActivity {

    private ScrollView svAficiones;
    private LinearLayout llAficiones;
    private ScrollView svOtros;
    private LinearLayout llOtros;
    private ArrayList<String> alNombreAficiones = new ArrayList<>();

    private CheckBox cb_cine, cb_videojuegos, cb_literatura, cb_musica, cb_comics, cb_pintura, cb_deporte, cb_fiesta;
    private CheckBox[] checkBoxes;

    //private int[] botones = {R.id.llCine, R.id.llVideojuegos, R.id.llMusica, R.id.llPintura, R.id.llComics, R.id.llLiteratura, R.id.llDeporte, R.id.llFiesta};
    private int[] botonesSeleccionados = {0, 0, 0, 0, 0, 0, 0, 0};
    private ArrayList<CheckBox> alCheckBoxes;

    private String[] otrasAficiones = {"", "", ""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        svAficiones = findViewById(R.id.svAficiones);
        llAficiones = findViewById(R.id.llAficiones);
        svOtros = findViewById(R.id.svOtros);
        llOtros = findViewById(R.id.llOtros);

    }

    public void onClickSeleccionar(View view) {

        CheckBox checkBox = (CheckBox) view;
        LinearLayout linearLayout = (LinearLayout) checkBox.getParent();

        if (checkBox.isChecked()) {

            checkBox.setBackgroundResource(R.drawable.bc_redondo_verde);
            linearLayout.setBackgroundResource(R.drawable.bc_redondo_verde);
        } else {

            checkBox.setBackgroundResource(R.drawable.bc_redondo_morado);
            linearLayout.setBackgroundResource(R.drawable.bc_redondo_morado);
        }

    }

    public void onClickAnadirAficion(View view) {

        LayoutInflater layoutInflater = getLayoutInflater();
        RelativeLayout relativeLayout = (RelativeLayout) layoutInflater.inflate(R.layout.item_aficion, null);

        ImageView ivEliminar;
        ivEliminar = relativeLayout.findViewById(R.id.ivEliminar);
        ivEliminar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                llOtros.removeView(relativeLayout);
            }
        });
        llOtros.addView(relativeLayout);
    }

    public void onClickCargarAficiones(View view) {

        //CheckBox checkBox;

        LinearLayout llAficion;
        for (int i = 0; i < llAficiones.getChildCount(); i++){

            //Recorriendo de parent a child
            llAficion = (LinearLayout) llAficiones.getChildAt(i);
            CheckBox checkBox = (CheckBox) llAficion.getChildAt(0);
            if (checkBox.isChecked()) {

                botonesSeleccionados[i] = 1;
                Log.d("XYZ", "Numero: " + i);
            }
        }

        for (int i = 0; i < llOtros.getChildCount(); i++){

            //Recorriendo de parent a child
            RelativeLayout relativeLayout = (RelativeLayout) llOtros.getChildAt(i);
            LinearLayout linearLayout = (LinearLayout) relativeLayout.getChildAt(0);
            EditText editText = (EditText) linearLayout.getChildAt(1);
            otrasAficiones[i] = editText.getText().toString();
            Log.d("XYZ", "Texto: " + otrasAficiones[i]);
        }

        ejecutarServicio();
    }

    //Manda datos al servidor
    private void ejecutarServicio() {

        HttpsTrustManager.allowAllSSL();

        String URL = Uri
                .parse("https://android.casuallove.es/insertar_aficiones_cl.php")
                .buildUpon()
                .appendQueryParameter("id", null)
                .appendQueryParameter("cine", botonesSeleccionados[0] + "")
                .appendQueryParameter("videojuegos", botonesSeleccionados[1] + "")
                .appendQueryParameter("literatura", botonesSeleccionados[2] + "")
                .appendQueryParameter("musica", botonesSeleccionados[3] + "")
                .appendQueryParameter("comics", botonesSeleccionados[4] + "")
                .appendQueryParameter("pintura", botonesSeleccionados[5] + "")
                .appendQueryParameter("deporte", botonesSeleccionados[6] + "")
                .appendQueryParameter("fiesta", botonesSeleccionados[7] + "")
                .appendQueryParameter("aficion1", otrasAficiones[0])
                .appendQueryParameter("aficion2", otrasAficiones[1])
                .appendQueryParameter("aficion3", otrasAficiones[2])
                .build()
                .toString();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //Si procesa o no procesa
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(getApplicationContext(), R.string.creado_correctamente, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);
    }
}