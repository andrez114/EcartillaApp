package com.millan.ecartillav112;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.palette.graphics.Palette;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.RoundedCorner;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

public class DescriptionArtActivity extends AppCompatActivity {
    TextView tituloDescriptionTextView;
    TextView infoDescriptionTextView;
    TextView fechaDescriptionTextView;
    ImageView ImaImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_art);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_description);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);



        Articulo element = (Articulo) getIntent().getSerializableExtra("Articulo");


        tituloDescriptionTextView = findViewById(R.id.titulo_articulo);
        infoDescriptionTextView = findViewById(R.id.infoArticulo);
        fechaDescriptionTextView = findViewById(R.id.fechaArticulo);
        ImaImageView = findViewById(R.id.imagenArticulo);


        tituloDescriptionTextView.setText(element.getTitulo());


        infoDescriptionTextView.setText(element.getContenido());

        fechaDescriptionTextView.setText(element.getFecha());


        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop());
        Glide.with(this).load(element.getImageUrl())
                .apply(requestOptions)
                .into(ImaImageView);

        /*
        CollapsingToolbarLayout collapsingToolbarLayout= findViewById(R.id.collapsingToolbarLayout);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.logo);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(@Nullable Palette palette) {
                if(palette != null){
                    collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(R.attr.colorPrimary));
                }
            }
        });
         */


    }
}