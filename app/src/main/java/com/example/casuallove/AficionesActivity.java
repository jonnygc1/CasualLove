package com.example.casuallove;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.Objects;

public class AficionesActivity extends AppCompatActivity {

    private ScrollView svBotones;

    private int[] botones = {R.id.llCine, R.id.llVideojuegos, R.id.llMusica, R.id.llPintura, R.id.llComics, R.id.llLiteratura, R.id.llDeporte, R.id.llFiesta};
    private int[] botonesSeleccionados = {0, 0, 0, 0, 0, 0, 0, 0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        svBotones = findViewById(R.id.svBotones);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onClickSeleccionar(View view) {

        LinearLayout llGusto = (LinearLayout) view;
        Log.d("XYZ", "Numero de ll: " + svBotones.getChildAt(llGusto.getId()));

        //Deprecated
        //Objects.equals(llGusto.getBackground().getConstantState(), getResources().getDrawable(R.drawable.bc_seleccionado).getConstantState())

        if (Objects.equals(llGusto.getBackground().getConstantState(), getResources().getDrawable(R.drawable.bc_seleccionado).getConstantState())) {

            llGusto.setBackgroundResource(R.drawable.bc_fondo_login);
        } else {

            llGusto.setBackgroundResource(R.drawable.bc_seleccionado);
        }

    }
}