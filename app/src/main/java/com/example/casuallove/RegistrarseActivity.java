package com.example.casuallove;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

public class RegistrarseActivity extends AppCompatActivity {

    public final int REQUEST_CODE_REGISTRARSE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void onClickSiguiente(View view) {

        Intent intent = new Intent(this, RegistrarseActivity2.class);
        startActivityForResult(intent, REQUEST_CODE_REGISTRARSE);
    }

    protected void onActivityResult (int requestCode, int resultCode, @Nullable Intent data){

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_REGISTRARSE && resultCode == RESULT_OK){

            //Si vuelve
        }
    }

}