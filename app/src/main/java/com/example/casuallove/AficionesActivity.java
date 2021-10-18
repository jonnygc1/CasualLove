package com.example.casuallove;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        svAficiones = findViewById(R.id.svAficiones);
        llAficiones = findViewById(R.id.llAficiones);
        svOtros = findViewById(R.id.svOtros);
        llOtros = findViewById(R.id.llOtros);

        /*cb_cine = findViewById(R.id.cb_cine);
        cb_videojuegos = findViewById(R.id.cb_videojuegos);
        cb_literatura = findViewById(R.id.cb_literatura);
        cb_musica = findViewById(R.id.cb_musica);
        cb_comics = findViewById(R.id.cb_comics);
        cb_pintura = findViewById(R.id.cb_pintura);
        cb_deporte = findViewById(R.id.cb_deporte);
        cb_fiesta = findViewById(R.id.cb_fiesta);

        alCheckBoxes = (ArrayList<CheckBox>) Arrays.asList(cb_cine, cb_videojuegos, cb_literatura, cb_musica, cb_comics, cb_pintura, cb_deporte, cb_fiesta);*/
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

        for (int i = 0; i < llAficiones.getChildCount(); i++){

            CheckBox checkBox = (CheckBox) llAficiones.getChildAt(i);
            if (checkBox.isChecked()) {

                botonesSeleccionados[i] = 1;
            }
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
                .appendQueryParameter("litaratura", botonesSeleccionados[2] + "")
                .appendQueryParameter("musica", botonesSeleccionados[3] + "")
                .appendQueryParameter("comics", botonesSeleccionados[4] + "")
                .appendQueryParameter("pintura", botonesSeleccionados[5] + "")
                .appendQueryParameter("deporte", botonesSeleccionados[6] + "")
                .appendQueryParameter("fiesta", botonesSeleccionados[7] + "")
                .appendQueryParameter("aficion1", "")
                .appendQueryParameter("aficion2", "")
                .appendQueryParameter("aficion3", "")
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