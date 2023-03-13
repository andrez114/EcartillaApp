package com.millan.ecartillav112;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;




public class HomeActiviy extends AppCompatActivity {


    TextView txt_most_correo;
    Button btn_cerarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_activiy);
        /*
        txt_most_correo = findViewById(R.id.txt_most_correo);
        btn_cerarSesion = findViewById(R.id.btn_cerrarSesion);
         */
            /*
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            txt_most_correo.setText(user.getEmail());
        }

            btn_cerarSesion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(HomeActiviy.this, loginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });*/


    }
}