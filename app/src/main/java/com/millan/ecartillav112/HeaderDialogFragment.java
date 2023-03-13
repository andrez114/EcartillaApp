package com.millan.ecartillav112;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class HeaderDialogFragment extends DialogFragment {
    TextInputEditText mtituloEditText, mDetalles,mFecha;
    private ImageView imageView;
    private  ProgressDialog mProgressDialog;
    private Uri imageUri;


    private StorageReference mStorage;

    FirebaseFirestore mFireStore;


    /**
     * The system calls this to get the DialogFragment's layout, regardless
     * of whether it's being displayed as a dialog or an embedded fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout to use as dialog or embedded fragment
        mFireStore = FirebaseFirestore.getInstance();

        mStorage = FirebaseStorage.getInstance().getReference();






        View view = inflater.inflate(R.layout.dialog_new_header, container,false);

        Toolbar toolbar =  (Toolbar) view.findViewById(R.id.toolbar_dialog);

        toolbar.setTitle("Registrar nueva noticia");

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);



        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();


        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);
        }

        setHasOptionsMenu(true);

        mtituloEditText = view.findViewById(R.id.txt_AccionEditText);
        mDetalles = view.findViewById(R.id.txt_DescripcionEditText);
        mFecha= view.findViewById(R.id.txt_AccionfechaEditText);

        //boton subir foto
        imageView=(ImageView) view.findViewById(R.id.subirImagen);
        mProgressDialog = new ProgressDialog(getContext());

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 2);
            }
        });


        return view;
    }

    /**
     * The system calls this only when creating the layout in a dialog.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.save_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:
                Toast.makeText(getContext(),"cerrar",Toast.LENGTH_SHORT).show();
                return true;

            case R.id.save:
                if (imageUri != null) {
                    //aqui se envia la informacion al servidor
                    uploadToFirebase(imageUri);
                }else{
                    Toast.makeText(getContext(),"seleccione una imagen",Toast.LENGTH_SHORT).show();
                }

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2 && resultCode== RESULT_OK && data !=null){




            imageUri = data.getData();
            imageView.setImageURI(imageUri);


        }
    }





/*
    private void crearDatos(){
        String titulo = mtituloEditText.getText().toString();
        String detalles= mDetalles.getText().toString();
        String fecha= mFecha.getText().toString();


        Map<String, Object> map= new HashMap<>();
        map.put("titulo",titulo);
        map.put("contenido",detalles);
        map.put("fecha", fecha);

        //mFireStore.collection("Articulos").document().set(map);
        mFireStore.collection("Articulos").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getContext(), "Subida exitosa",Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "No se Realizo la peticion",Toast.LENGTH_SHORT).show();
            }
        });;
    }

         */

    private void uploadToFirebase(Uri uri){
        String titulo = mtituloEditText.getText().toString();
        String detalles= mDetalles.getText().toString();
        String fecha= mFecha.getText().toString();





        StorageReference fileRef = mStorage.child(System.currentTimeMillis()+"."+getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Model model = new Model(uri.toString());
                        String imageUrl = uri.toString();

                        Map<String, Object> map= new HashMap<>();
                        map.put("titulo",titulo);
                        map.put("contenido",detalles);
                        map.put("fecha", fecha);
                        map.put("imageUrl", imageUrl);


                        //mFireStore.collection("Articulos").document().set(map);
                        mFireStore.collection("Articulos").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getContext(), "Subida exitosa",Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "No se Realizo la peticion",Toast.LENGTH_SHORT).show();
                            }
                        });;
                        mProgressDialog.dismiss();
                        Toast.makeText(getContext(),"subida exitosa",Toast.LENGTH_SHORT).show();
                        imageView.setImageResource(R.drawable.ic_baseline_add_photo_alternate_24);
                       mtituloEditText.setText("");
                       mDetalles.setText("");
                       mFecha.setText("");
                    }
                });

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                mProgressDialog.setTitle("subiendo...");
                mProgressDialog.setMessage("Subiendo foto a firebase");
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mProgressDialog.dismiss();
                Toast.makeText(getContext(),"fallo la subida",Toast.LENGTH_SHORT).show();
            }
        });



    }

    private String getFileExtension(Uri mUri){

        ContentResolver cr = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }



}
