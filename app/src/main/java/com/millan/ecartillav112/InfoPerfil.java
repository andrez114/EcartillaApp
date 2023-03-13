package com.millan.ecartillav112;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class InfoPerfil extends AppCompatActivity {

    TextInputEditText mUserPerfil, mafiliacionPerfil;
    Button btnCrearDatos, btnEditar;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    FirebaseFirestore mFireStore;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_perfil);

        initDatePicker();
        dateButton =findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());


        //spinner
        Spinner spinnerEntidad=(Spinner) findViewById(R.id.spinner_entidad);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.entidad_federativa,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEntidad.setAdapter(adapter);

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_info);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        getSupportActionBar().setTitle("Información Personal");

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);
        }



        mFireStore = FirebaseFirestore.getInstance();

        mUserPerfil = findViewById(R.id.txt_userPerfilEditText);
        mafiliacionPerfil= findViewById(R.id.afiliacionEditText);
        btnCrearDatos =findViewById(R.id.btn_guardar);
        btnEditar=findViewById(R.id.btn_editar);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnCrearDatos.setEnabled(true);
                mUserPerfil.setEnabled(true);
                mafiliacionPerfil.setEnabled(true);
            }
        });

        btnCrearDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            crearDatos();
            btnCrearDatos.setEnabled(false);
            mUserPerfil.setEnabled(false);
            mafiliacionPerfil.setEnabled(false);

            }
        });

        RecibirDatos();
    }

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);

    }


    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private void crearDatos(){


        String nombre = mUserPerfil.getText().toString().trim();
        String afiliacion = mafiliacionPerfil.getText().toString().trim();

        Map<String,Object> map = new HashMap<>();
        map.put("nombre",nombre);
        map.put("afiliacion",afiliacion);
        map.put("fecha", new Date().getTime());

        mFireStore.collection("Usuarios").document(user.getEmail()).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(InfoPerfil.this,"Se actualizo la informacion", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(InfoPerfil.this,"No se actualizo", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void RecibirDatos(){
        mFireStore.collection("Usuarios").document(user.getEmail()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String nombre = documentSnapshot.getString("nombre");
                    String afiliacion = documentSnapshot.getString("afiliacion");
                    mUserPerfil.setText(nombre);
                    mafiliacionPerfil.setText(afiliacion);
                }
            }
        });
    }

    private void initDatePicker() {

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month+1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);


            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = android.R.style.Theme_Holo_Dialog_MinWidth;

        datePickerDialog = new DatePickerDialog(this, style,dateSetListener,year, month, day);
        //datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());


    }

    private String makeDateString(int day, int month, int year)
    {
    return day+" "+getMothFormat(month)+" "+year;
    
    }

    private String getMothFormat(int month)
    {
        if(month == 1) return "ENERO";
        if(month == 2) return "FEBRERO";
        if(month == 3) return "MARZO";
        if(month == 4) return "ABRIL";
        if(month == 5) return "MAYO";
        if(month == 6) return "JUNIO";
        if(month == 7) return "JULIO";
        if(month == 8) return "AGOSTO";
        if(month == 9) return "SEPTIEMBRE";
        if(month == 10) return "OCTUBRE";
        if(month == 11) return "NOVIEMBRE";
        if(month == 12) return "DICIEMBRE";
        //default should never happen
        return "ENERO";
    }

    public void openDatePicker(View view) {

        datePickerDialog.show();
    }
}