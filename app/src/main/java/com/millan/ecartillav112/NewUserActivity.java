package com.millan.ecartillav112;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialTextInputPicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;


import java.util.Calendar;
import android.widget.DatePicker;
import android.app.DatePickerDialog;;

public class NewUserActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String CERO = "0";
    private static final String BARRA = "/";
    //Calendario para obtener fecha
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);



    //Widgets
    TextInputEditText etFecha;
    ImageButton ibObtenerFecha;

    Button btn_cancelar,btn_siguiente;
    TextInputEditText emailEditText,confirm_passwordEditText,passwordEditText;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        //Widget EditText donde se mostrara la fecha obtenida
        etFecha = (TextInputEditText) findViewById(R.id.txt_dateEditText);
        //Evento setOnClickListener - click
        etFecha.setOnClickListener(this);

        btn_cancelar = findViewById(R.id.btn_cancelar);
        emailEditText = findViewById(R.id.emailEditTextN);
        confirm_passwordEditText = findViewById(R.id.confirm_passwordEditTextN);
        passwordEditText = findViewById(R.id.passwordEditTextN);
        btn_siguiente = findViewById(R.id.btn_siguiente);

        btn_siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });

        mAuth = FirebaseAuth.getInstance();


        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewUserActivity.this, loginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_dateEditText:
                obtenerFecha();
                break;
        }
    }

    private void obtenerFecha(){
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                etFecha.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);


            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        },anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();

    }

    public void validate (){
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirm_passwordEditText.getText().toString().trim();

        if(email.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("correo invalido");
            return;
        }else{
            emailEditText.setError(null);
        }

        if (password.isEmpty()||password.length()<8){
            passwordEditText.setError("Se necesita más de 8 caracteres");
            return;

        }else if (!Pattern.compile("[0-9]").matcher(password).find()){
            passwordEditText.setError("Al menos un numero");
            return;
        } else{
            passwordEditText.setError(null);
        }

        if (!confirmPassword.equals(password)){
            confirm_passwordEditText.setError("Debe ser igual");
            return;
        } else {
            registrar(email,password);
        }
    }


    //regresar a iniciar sesion
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(NewUserActivity.this, loginActivity.class);
        startActivity(intent);
        finish();
    }
    public void registrar(String email, String password){
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Intent intent = new Intent(NewUserActivity.this, MenuActivity.class);

                            startActivity(intent);
                            finish();
                        }else
                            Toast.makeText(NewUserActivity.this, "fallo en registrarse", Toast.LENGTH_SHORT).show();
                    }
                });

    }


}