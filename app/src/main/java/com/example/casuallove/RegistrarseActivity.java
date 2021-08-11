package com.example.casuallove;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class RegistrarseActivity extends AppCompatActivity {

    public final int REQUEST_CODE_REGISTRARSE = 1;
    TextInputEditText btFechaNacimiento;

    static boolean fechaVacia = true;
    static int fechaDia = 0;
    static int fechaMes = 0;
    static int fechaAnio = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        btFechaNacimiento = findViewById(R.id.btFechaNacimientos);
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



    private void showDatePickerDialog(TextInputEditText button) {

        RegistrarseActivity2.DatePickerFragment newFragment = RegistrarseActivity2.DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                //+1 porque enero es 0
                int finalMonth = (month + 1);

                String strDay = digitoCompleto(day + "");
                String strMonth = digitoCompleto(finalMonth + "");

                String selectedDate = strDay  + "/" + strMonth + "/" + year;
                btFechaNacimiento.setText(selectedDate);

                fechaVacia = false;
                fechaDia = Integer.parseInt(strDay);
                //-1 porque antes sumamos porque enero es 0
                fechaMes = Integer.parseInt(strMonth) - 1;
                fechaAnio = year;
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    //Para añadir 0 si hace falta
    public String digitoCompleto(String str) {

        if (str.length() == 1) {

            str = 0 + "" + str;
        }
        return str;
    }

    public void onClickPrueba(View view) {
        Intent intent = new Intent(this, AficionesActivity.class);
        startActivity(intent);
    }

    public void onClickElegirFechaNacimiento(View view) {

        showDatePickerDialog(btFechaNacimiento);
    }

    public static class DatePickerFragment extends DialogFragment {

        private DatePickerDialog.OnDateSetListener listener;

        public static RegistrarseActivity2.DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener) {
            RegistrarseActivity2.DatePickerFragment fragment = new RegistrarseActivity2.DatePickerFragment();
            fragment.setListener(listener);
            return fragment;
        }

        public void setListener(DatePickerDialog.OnDateSetListener listener) {
            this.listener = listener;
        }

        @Override
        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState) {


            if (fechaVacia) {
                Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                return new DatePickerDialog(getActivity(), listener, year - 16, month, day);
            } else {

                return new DatePickerDialog(getActivity(), listener, fechaAnio, fechaMes, fechaDia);
            }

        }

    }

}