package com.example.casuallove;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.Objects;

public class AficionesActivity extends AppCompatActivity {

    private ScrollView svAficiones;
    private ScrollView svOtros;
    private LinearLayout llOtros;
    private ArrayList<String> alNombreAficiones = new ArrayList<>();

    //private int[] botones = {R.id.llCine, R.id.llVideojuegos, R.id.llMusica, R.id.llPintura, R.id.llComics, R.id.llLiteratura, R.id.llDeporte, R.id.llFiesta};
    private int[] botonesSeleccionados = {0, 0, 0, 0, 0, 0, 0, 0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        svAficiones = findViewById(R.id.svAficiones);
        svOtros = findViewById(R.id.svOtros);
        llOtros = findViewById(R.id.llOtros);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onClickSeleccionar(View view) {

        LinearLayout llGusto = (LinearLayout) view;
        Log.d("XYZ", "Numero de ll: " + svAficiones.getChildAt(llGusto.getId()));

        //Deprecated
        //Objects.equals(llGusto.getBackground().getConstantState(), getResources().getDrawable(R.drawable.bc_seleccionado).getConstantState())

        if (Objects.equals(llGusto.getBackground().getConstantState(), getResources().getDrawable(R.drawable.bc_redondo_verde).getConstantState())) {

            llGusto.setBackgroundResource(R.drawable.bt_redondo_morado);
        } else {

            llGusto.setBackgroundResource(R.drawable.bc_redondo_verde);

            //recorrer si coincide

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
}