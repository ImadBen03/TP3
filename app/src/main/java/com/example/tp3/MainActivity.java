package com.example.tp3;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    DBHandler dbHandler = new DBHandler(this);
    byte[] photo;
    ImageView imageView;

    EditText emat, enom, eprenom;

    Button afficher, ajouter, supprimer, chercher, modifier,camera,gallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emat = findViewById(R.id.e1);
        enom = findViewById(R.id.e2);
        eprenom = findViewById(R.id.e3);
        afficher = findViewById(R.id.afficher);
        ajouter = findViewById(R.id.ajouter);
        supprimer = findViewById(R.id.supprimer);
        chercher = findViewById(R.id.chercher);
        modifier = findViewById(R.id.modifier);
        camera = findViewById(R.id.camera);
        gallery = findViewById(R.id.gallery);
        imageView = findViewById(R.id.image);
        imageView.setImageResource(R.drawable.ic_launcher_background);

        ajouter.setOnClickListener(this::ajouter);
        afficher.setOnClickListener(this::afficher);
        supprimer.setOnClickListener(this::supprimer);
        chercher.setOnClickListener(this::chercher);
        modifier.setOnClickListener(this::modifier);
        camera.setOnClickListener(this::openCamera);
        gallery.setOnClickListener(this::openGallery);

    }

    public byte[] convertimage(){
        Bitmap bmp=((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private void modifier(View view) {
        String mat = emat.getText().toString();
        String nom = enom.getText().toString();
        String prenom = eprenom.getText().toString();
        if (mat.matches("") || nom.matches("") || prenom.matches("")) {
            Toast.makeText(this, "Please fill all text fields", Toast.LENGTH_SHORT).show();
        } else {
            Student s = new Student(mat, nom, prenom);
            dbHandler.updateStudent(s);
            Toast.makeText(this, "Student edited successfully if found", Toast.LENGTH_SHORT).show();
        }

    }

    private void chercher(View view) {
        Student s;
        String mat = emat.getText().toString();
        if (mat.matches("")) {
            Toast.makeText(this, "Enter a value for Matricule", Toast.LENGTH_SHORT).show();
        } else {
            s = dbHandler.getStudent(mat);
            if (s == null) {
                Toast.makeText(this, "Student not found in database", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Student found", Toast.LENGTH_SHORT).show();
                Dialog dialog=new Dialog(this);
                dialog.setContentView(R.layout.itemlist);
                ImageView img;
                TextView text1,text2,text3;
                text1=dialog.findViewById(R.id.matricule);
                text2=dialog.findViewById(R.id.nom);
                text3=dialog.findViewById(R.id.prenom);
                img=dialog.findViewById(R.id.imageView);
                text1.setText(s.matricule);
                text2.setText(s.nom);
                text3.setText(s.prenom);
                Bitmap bmp= BitmapFactory.decodeByteArray(s.photo, 0 , s.photo.length);
                Bitmap resized = Bitmap.createScaledBitmap(bmp, 100,100, true);
                img.setImageBitmap(resized);
                dialog.show();

            }
        }

    }

    public void afficher(View view) {
        startActivity(new Intent(this, ListActivity.class));
    }

    public void ajouter(View view) {
        String mat = emat.getText().toString();
        String nom = enom.getText().toString();
        String prenom = eprenom.getText().toString();
        if (mat.matches("") || nom.matches("") || prenom.matches("")) {
            Toast.makeText(this, "Please fill all text fields", Toast.LENGTH_SHORT).show();
        } else {
            Student s = new Student(emat.getText().toString(), enom.getText().toString(), eprenom.getText().toString(),convertimage());
            dbHandler.addStudent(s);
            Toast.makeText(this, "Student added successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public void supprimer(View view) {
        Student s;
        String mat = emat.getText().toString();
        if (mat.matches("")) {
            Toast.makeText(this, "Enter a value for Matricule", Toast.LENGTH_SHORT).show();
        } else {
            dbHandler.deleteStudent(mat);
            Toast.makeText(this, "Student deleted", Toast.LENGTH_SHORT).show();
        }

    }

    public void openCamera(View view) {
        Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, 0);
    }

    public void openGallery(View view) {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    assert bundle != null;
                    Bitmap bmp = (Bitmap) bundle.get("data");
                    assert bmp != null;
                    Bitmap resized = Bitmap.createScaledBitmap(bmp, 100, 100, true);
                    imageView.setImageBitmap(resized);
                }
                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    imageView.setImageURI(selectedImage);
                }
                break;
        }
    }
}