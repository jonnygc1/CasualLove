package com.example.casuallove;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegistrarseActivity2 extends AppCompatActivity {

    Button btFechaNacimiento;

    static boolean fechaVacia = true;
    static int fechaDia = 0;
    static int fechaMes = 0;
    static int fechaAnio = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse2);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btFechaNacimiento = findViewById(R.id.btFechaNacimiento);
    }

    public void onClickElegirFechaNacimiento(View view) {

        Button button = (Button) view;
        showDatePickerDialog(button);
    }

    private void showDatePickerDialog(Button button) {

        RegistrarseActivity2.DatePickerFragment newFragment = RegistrarseActivity2.DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                //+1 porque enero es 0
                int finalMonth = (month + 1);

                String strDay = digitoCompleto(day + "");
                String strMonth = digitoCompleto(finalMonth + "");

                String selectedDate = strDay  + "/" + strMonth + "/" + year;
                button.setText(selectedDate);

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

    public static class DatePickerFragment extends DialogFragment {

        private DatePickerDialog.OnDateSetListener listener;

        public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener) {
            DatePickerFragment fragment = new DatePickerFragment();
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