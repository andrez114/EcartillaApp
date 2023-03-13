package com.millan.ecartillav112;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    //animaciones del boton flotante


    //fab
    FloatingActionButton fab, fab1, fab2;
    boolean isOpen = true; //default false

    //animation

    Animation rotateOpen;
    Animation rotateClose ;
    Animation fromBottom ;
    Animation toBottom ;


    //variables
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    Toolbar toolbar;


    HomeFragment homeFragment = new HomeFragment();
    CartillaFragment cartillaFragment = new CartillaFragment();
    NotificationFragment notificationFragment = new NotificationFragment();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //fabs
        fab=(FloatingActionButton) findViewById(R.id.floating_action_button);
        fab1=(FloatingActionButton) findViewById(R.id.float_cartilla);
        fab2=(FloatingActionButton) findViewById(R.id.float_noticias);

        //animation
        rotateOpen = AnimationUtils.loadAnimation(this,R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(this,R.anim.rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(this,R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(this,R.anim.to_bottom_anim);
        //click listener fab
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFab();
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MenuActivity.this, "cartilla", Toast.LENGTH_SHORT).show();
                animateFab();
            }
        });





        /*---------------------------------------hooks------------------------------*/
        final FloatingActionButton fab= (FloatingActionButton) findViewById(R.id.float_noticias);
        fab.setOnClickListener(this);




        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar =findViewById(R.id.toolbar);
        View navHeader = navigationView.getHeaderView(0);
        TextView correo = (TextView) navHeader.findViewById(R.id.nav_header_text_view);
        /*---------------------------------------bar------------------------------*/
        setSupportActionBar(toolbar);
        /*---------------------------------------Navigation Drawer menu------------------------------*/
        navigationView.bringToFront();
        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        navigationView.setNavigationItemSelectedListener(this);


        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(homeFragment);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!= null){
            correo.setText(user.getEmail());
        }


    }

    private void animateFab() {
        if(isOpen){
            fab.startAnimation(rotateOpen);
            fab1.startAnimation(fromBottom);
            fab2.startAnimation(fromBottom);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isOpen=false;
        }else {
            fab.startAnimation(rotateClose);
            fab1.startAnimation(toBottom);
            fab2.startAnimation(toBottom);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isOpen=true;


        }
    }


    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){
                case R.id.homeFragment:
                    loadFragment(homeFragment);
                    return true;
                case R.id.cartillaFragment:
                    loadFragment(cartillaFragment);
                    return true;
                case R.id.notificationFragment:
                    loadFragment(notificationFragment);
                    return true;
            }
            return false;
        }
    };

    public void loadFragment(Fragment fragment){

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container,fragment);
        transaction.commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.nav_perfil:
                Intent intentPerfil = new Intent(MenuActivity.this,InfoPerfil.class);
                startActivity(intentPerfil);
                break;
            case R.id.nav_configuracion:
                Intent intent = new Intent(MenuActivity.this,HomeActiviy.class);
                startActivity(intent);
                break;
            case R.id.nav_medico:
                Toast.makeText(this,"medico",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_qr:
                break;
            case R.id.nav_cerrar:
                FirebaseAuth.getInstance().signOut();
                Intent intentSalir = new Intent(MenuActivity.this,loginActivity.class);
                startActivity(intentSalir);
                finish();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.float_noticias:
                animateFab();
                showCreateHeaderDialog();
                break;
        }
    }




    private void showCreateHeaderDialog(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        HeaderDialogFragment newFragment = new HeaderDialogFragment();

            // The device is smaller, so show the fragment fullscreen
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            // For a little polish, specify a transition animation
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            // To make it fullscreen, use the 'content' root view as the container
            // for the fragment, which is always the root view for the activity
            transaction.add(android.R.id.content, newFragment)
                    .addToBackStack(null).commit();
        }
}