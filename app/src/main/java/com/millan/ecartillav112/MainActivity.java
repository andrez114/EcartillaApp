package com.millan.ecartillav112;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Animation animacion1 = AnimationUtils.loadAnimation(this,R.anim.desplazamiento_abajo);
        Animation animacion2 = AnimationUtils.loadAnimation(this,R.anim.desplazamiento_arriba);

        TextView  txt_titulo_1 = findViewById(R.id.txt_titulo_1);
        LottieAnimationView logoAnimacion = findViewById(R.id.animationView);

        logoAnimacion.setAnimation(animacion2);
        txt_titulo_1.setAnimation(animacion1);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(MainActivity.this);
                if (user != null && account!=null){
                    Intent intent = new Intent(MainActivity.this,MenuActivity.class);
                    startActivity(intent);
                    finish();
                }else {

                    Intent intent = new Intent(MainActivity.this,loginActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, 3000);

    }
}