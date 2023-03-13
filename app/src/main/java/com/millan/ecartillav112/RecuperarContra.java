package com.millan.ecartillav112;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class RecuperarContra extends AppCompatActivity {

    Button recuperarBoton;
    Button btn_cancelar;
    TextInputEditText emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_contra);

        recuperarBoton = findViewById(R.id.btn_confirmar);
        btn_cancelar = findViewById(R.id.btn_rec_cancelar);
        emailEditText = findViewById(R.id.rec_emailEditText);

        //boton recuperar contraseña
        recuperarBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });


        //boton cancelar
        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecuperarContra.this, loginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void validate(){

        String email = emailEditText.getText().toString().trim();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("correo invalido");
            return;
        }
        sendEmail(email);

    }


    //regresar a iniciar sesion
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(RecuperarContra.this, loginActivity.class);
        startActivity(intent);
        finish();
    }

    public void sendEmail(String email){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAdress = email;
        auth.sendPasswordResetEmail(emailAdress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(RecuperarContra.this,"Correo Enviado", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RecuperarContra.this,loginActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(RecuperarContra.this,"Correo Invalido",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}