package com.example.open_open;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class GalleryActivity extends AppCompatActivity {
    ImageView imageView;
    ImageButton camera, gallery;
    TextView judul, hasil;
    DrawerLayout drawerLayout;
    Uri imageUri;
    Bitmap bitmap;
    Bitmap captureImage;

    private static final int OPEN_CAM =101;
    private static final int PICK_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        camera = (ImageButton) findViewById(R.id.camera);
        gallery = (ImageButton) findViewById(R.id.gallery);
        imageView = (ImageView) findViewById(R.id.imageview);
        judul=findViewById(R.id.judul);
        drawerLayout = findViewById(R.id.drawer_layout);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCamera();
            }
        });

        gallery.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openGallery();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == OPEN_CAM){
            captureImage = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(captureImage);
        }
        else if(requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    private void openCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, OPEN_CAM);
    }

    private void openGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }
}