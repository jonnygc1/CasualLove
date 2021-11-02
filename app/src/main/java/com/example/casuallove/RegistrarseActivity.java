package com.example.casuallove;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class RegistrarseActivity extends AppCompatActivity {

    public final int REQUEST_CODE_REGISTRARSE = 1;
    TextInputEditText textInputEditTextFecha;
    ImageView btFechaNacimiento;

    private TextInputEditText tietNickname;
    private TextInputEditText tietNameUser;
    private TextInputEditText tietApellido;
    private TextInputEditText tietEmail;
    private TextInputEditText tietFechaNac;
    private TextInputEditText tietDireccion;
    private TextInputEditText tietTelefono;
    private TextInputEditText tietPassword;
    private TextInputEditText tietPassword2;

    static boolean fechaVacia = true;
    static int fechaDia = 0;
    static int fechaMes = 0;
    static int fechaAnio = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        tietNickname = findViewById(R.id.teNickname);
        tietNameUser = findViewById(R.id.teNameUser);
        tietApellido = findViewById(R.id.teApellido);
        tietEmail = findViewById(R.id.teEmail);
        tietFechaNac = findViewById(R.id.teEmail);
        tietDireccion = findViewById(R.id.ttDireccion);
        tietTelefono = findViewById(R.id.teTelefono);
        tietPassword = findViewById(R.id.tePassword);
        tietPassword2 = findViewById(R.id.tePasswordConfirmar);

        btFechaNacimiento = findViewById(R.id.btFechaNacimientos);
        textInputEditTextFecha = findViewById(R.id.ttFecha);
    }

    /*public void onClickSiguiente(View view) {

        Intent intent = new Intent(this, RegistrarseActivity2.class);
        startActivityForResult(intent, REQUEST_CODE_REGISTRARSE);
    }*/

    protected void onActivityResult (int requestCode, int resultCode, @Nullable Intent data){

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_REGISTRARSE && resultCode == RESULT_OK){

            //Si vuelve
        }
    }



    private void showDatePickerDialog(ImageView button) {

        RegistrarseActivity.DatePickerFragment newFragment = RegistrarseActivity.DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                //+1 porque enero es 0
                int finalMonth = (month + 1);

                String strDay = digitoCompleto(day + "");
                String strMonth = digitoCompleto(finalMonth + "");

                String selectedDate = strDay  + "/" + strMonth + "/" + year;
                textInputEditTextFecha.setText(selectedDate);

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

    public void onClickSiguiente(View view) {

        if (passwordCorrecta()) {

            ejecutarServicio();
        } else {

            Toast.makeText(getApplicationContext(), R.string.password_no_coincide, Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(this, AficionesActivity.class);
        startActivity(intent);
    }

    //Manda datos al servidor
    private void ejecutarServicio() {

        HttpsTrustManager.allowAllSSL();

        String URL = Uri
                .parse("https://android.casuallove.es/insertar_usuario_cl.php")
                .buildUpon()
                .appendQueryParameter("id", null)
                .appendQueryParameter("nickname", tietNickname.getText().toString())
                .appendQueryParameter("nombre", tietNameUser.getText().toString())
                .appendQueryParameter("apellidos", tietApellido.getText().toString())
                .appendQueryParameter("fecha_nac", tietFechaNac.getText().toString())
                .appendQueryParameter("direccion", tietDireccion.getText().toString())
                .appendQueryParameter("telefono", tietTelefono.getText().toString())
                .appendQueryParameter("email", tietEmail.getText().toString())
                .appendQueryParameter("password", tietPassword.getText().toString())
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

    public boolean passwordCorrecta() {

        String password1 = tietPassword.getText().toString();
        String password2 = tietPassword2.getText().toString();

        if (password1.equals(password2)) {

            Log.d("XYZ", "Strings: " + password1 + " " + password2);
            return true;
        }

        return false;
    }

    public void onClickElegirFechaNacimiento(View view) {

        showDatePickerDialog(btFechaNacimiento);
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