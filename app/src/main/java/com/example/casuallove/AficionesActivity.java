package com.example.casuallove;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.Objects;

public class AficionesActivity extends AppCompatActivity {

    private ScrollView svAficiones;
    private LinearLayout llAficiones;
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
        llAficiones = findViewById(R.id.llAficiones);
        svOtros = findViewById(R.id.svOtros);
        llOtros = findViewById(R.id.llOtros);
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
}