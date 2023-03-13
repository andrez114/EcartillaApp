package com.millan.ecartillav112;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.regex.Pattern;

public class loginActivity extends AppCompatActivity {
        TextView oldiasteContrasena;
        Button btn_crear_cuenta,btn_iniciarSesion;
        TextInputEditText emailEditText,passwordEditText;
        private FirebaseAuth mAuth;

        //
    Button signButton;
    GoogleSignInClient mGoogleSignClient;
    public static final int RC_SIGN_IN = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_crear_cuenta = findViewById(R.id.btn_crearCuenta);
        btn_iniciarSesion = findViewById(R.id.btn_Inciar);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        oldiasteContrasena = findViewById(R.id.btn_olvi_contra);

        mAuth = FirebaseAuth.getInstance();



        //boton en crear cuenta
        btn_crear_cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loginActivity.this, NewUserActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //boton para iniciar sesion
        btn_iniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });
        //boton olvidar contraseña
        oldiasteContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loginActivity.this,RecuperarContra.class);
                startActivity(intent);
                finish();
            }
        });

        // iniciar con google
        signButton = findViewById(R.id.btn_google);
        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInWithGoogle();
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();


        mGoogleSignClient = GoogleSignIn.getClient(this,gso);

    }

    private void signInWithGoogle(){
        Intent signInIntent = mGoogleSignClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);


    }

    @Override
    public void onActivityResult(int requestCode,int resultcode, Intent data){
        super.onActivityResult(requestCode,resultcode,data);

        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e){
                Toast.makeText(loginActivity.this,"Fallo google",Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void firebaseAuthWithGoogle(String idtoken){
        AuthCredential credential = GoogleAuthProvider.getCredential(idtoken,null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent =  new Intent(loginActivity.this,MenuActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(loginActivity.this,"fallo iniciar Sesion",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    public void validate (){
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if(email.isEmpty()|| !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("correo invalido");
            return;
        }else{
            emailEditText.setError(null);
        }

        if (password.isEmpty() || password.length()<8){
            passwordEditText.setError("Se necesita más de 8 caracteres");
            return;

        }else if (!Pattern.compile("[0-9]").matcher(password).find()){
            passwordEditText.setError("Al menos un numero");
            return;
        } else{
            passwordEditText.setError(null);
        }
            iniciarSesion(email,password);
    }


    public void iniciarSesion(String email,String password){
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(loginActivity.this,MenuActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(loginActivity.this, "Credenciales equivocadas, Intenta de Nuevo", Toast.LENGTH_LONG).show();
                        }
                    }
                });


    }

}