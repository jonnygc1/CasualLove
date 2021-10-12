package com.example.casuallove;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
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

    //private int[] botones = {R.id.llCine, R.id.llVideojuegos, R.id.llMusica, R.id.llPintura, R.id.llComics, R.id.llLiteratura, R.id.llDeporte, R.id.llFiesta};
    private int[] botonesSeleccionados = {0, 0, 0, 0, 0, 0, 0, 0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        svAficiones = findViewById(R.id.svAficiones);
        llAficiones = findViewById(R.id.llAficiones);
        svOtros = findViewById(R.id.svOtros);
        llOtros = findViewById(R.id.llOtros);

        cb_cine = findViewById(R.id.cb_cine);
        cb_videojuegos = findViewById(R.id.cb_videojuegos);
        cb_literatura = findViewById(R.id.cb_literatura);
        cb_musica = findViewById(R.id.cb_musica);
        cb_comics = findViewById(R.id.cb_comics);
        cb_pintura = findViewById(R.id.cb_pintura);
        cb_deporte = findViewById(R.id.cb_deporte);
        cb_fiesta = findViewById(R.id.cb_fiesta);

    }


    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onClickSeleccionar(View view) {

        LinearLayout llGusto = (LinearLayout) view;
        Log.d("XYZ", "Numero de ll: " + llAficiones.getChildAt(llGusto.getId()));

        //Deprecated
        /*if (Objects.equals(view.getBackground().getConstantState(), AficionesActivity.getResources().getDrawable(R.drawable.bc_redondo_verde).getConstantState())) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                llGusto.setBackground(getDrawable(R.drawable.bc_redondo_morado);
            }
        } else {

            llGusto.setBackgroundResource(R.drawable.bc_redondo_verde);
        }*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (llGusto.getBackground().getConstantState().equals(llGusto.getContext().getDrawable(R.drawable.bc_redondo_verde).getConstantState())) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    llGusto.setBackground(getDrawable(R.drawable.bc_redondo_morado));
                }
            } else {

                llGusto.setBackgroundResource(R.drawable.bc_redondo_verde);
            }
        }
        /*Drawable drawable1 = llGusto.getBackground();
        Drawable drawable2 = ContextCompat.getDrawable(getApplicationContext(), R.drawable.bc_redondo_verde);

        if (drawable1 == drawable2) {

            Log.d("XYZ", "Dentro2");
            llGusto.setBackgroundResource(R.drawable.bc_redondo_morado);
        } else {
            Log.d("XYZ", "Dentro1");
            llGusto.setBackgroundResource(R.drawable.bc_redondo_verde);
        }*/
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

        LinearLayout llGusto;
        for (int i = 0; i < llAficiones.getChildCount(); i++){

            llGusto = (LinearLayout) llAficiones.getChildAt(i);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (!(llGusto.getBackground().getConstantState().equals(llGusto.getContext().getDrawable(R.drawable.bc_redondo_morado).getConstantState()))) {

                    botonesSeleccionados[i] = 1;
                }
            }
        }

        ejecutarServicio();
    }

    //Manda datos al servidor
    private void ejecutarServicio() {

        HttpsTrustManager.allowAllSSL();

        String URL = "";
        URL = Uri
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