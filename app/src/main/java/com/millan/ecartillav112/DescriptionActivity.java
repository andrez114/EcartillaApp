package com.millan.ecartillav112;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class DescriptionActivity extends AppCompatActivity {
    TextView titleDescriptionTextView;
    TextView infoDescriptionTextView;
    TextView fechaDescriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        ListElement element = (ListElement) getIntent().getSerializableExtra("ListElement");


        titleDescriptionTextView = findViewById(R.id.titleDescriptionTextView);
        infoDescriptionTextView = findViewById(R.id.infoDescriptionTextView);
        fechaDescriptionTextView = findViewById(R.id.fechaDescriptionTextView);


        titleDescriptionTextView.setText(element.getAccion());
        titleDescriptionTextView.setTextColor(Color.parseColor(element.getColor()));

        infoDescriptionTextView.setText(element.getInformacion());

        fechaDescriptionTextView.setText(element.getFecha());
        fechaDescriptionTextView.setTextColor(Color.GRAY);
    }
}