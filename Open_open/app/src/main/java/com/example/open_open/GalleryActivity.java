package com.example.open_open;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.open_open.connection.ApiInterface;
import com.example.open_open.connection.RetrofitBuilder;
import com.example.open_open.pojo.SubmitData;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

//import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GalleryActivity extends AppCompatActivity {
    static final int REQUEST_TAKE_PHOTO = 1;
    ImageView imageView;
    ImageButton camera, gallery, benar;
    TextView judul, hasil;
    DrawerLayout drawerLayout;
    Uri imageUri;
    Bitmap bitmap;
    Bitmap captureImage;
    String path;
    String currentPhotoPath;

    private static final int OPEN_CAM =101;
    private static final int PICK_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        camera = (ImageButton) findViewById(R.id.camera);
        gallery = (ImageButton) findViewById(R.id.gallery);
        imageView = (ImageView) findViewById(R.id.imageview);
        benar = (ImageButton) findViewById(R.id.benar);
        judul=findViewById(R.id.judul);
        drawerLayout = findViewById(R.id.drawer_layout);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        gallery.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openGallery();
            }
        });

        benar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            imageUri = data.getData();
            path = data.getData().toString();
            Toast.makeText(this, path, Toast.LENGTH_LONG).show();
            imageView.setImageURI(imageUri);
        } else if(requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            imageView.setImageURI(Uri.parse(currentPhotoPath));
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.open_open.FileProvider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
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

    public void uploadImage() {
//        Toast.makeText(this, "testing", Toast.LENGTH_LONG).show();
        File file = new File(Objects.requireNonNull(Uri.parse(currentPhotoPath).getPath()));
        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);

        MultipartBody.Part foto = MultipartBody.Part.createFormData("foto", file.getName(), mFile);
        RequestBody tes = RequestBody.create(MediaType.parse("text/plain"), "bumn");

        ApiInterface apiInterface = RetrofitBuilder.getClient().create(ApiInterface.class);
        Call<SubmitData> caller = apiInterface.update_data(foto, tes);
        caller.enqueue(new Callback<SubmitData>() {
            @Override
            public void onResponse(Call<SubmitData> call, Response<SubmitData> response) {
//                response.body().getPesan()
                Log.e("testing", response.message().toString());
            }

            @Override
            public void onFailure(Call<SubmitData> call, Throwable t) {
                Log.e("wakaka", t.getMessage());
            }
        });
//        caller.enqueue(new Callback<SubmitData>() {
//            @Override
//            public void onResponse(retrofit2.Call<SubmitData> call, Response<SubmitData> response) {
//                if (response.isSuccessful()){
////                    Toast.makeText(this, "Berhasil", Toast.LENGTH_LONG).show();
//                }else{
////                    Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(retrofit2.Call<SubmitData> call, Throwable t) {
////                activityInputBinding.loadingKirim.setVisibility(View.GONE);
////                    Log.e("Kesalahan ", t.getMessage());
////                activityInputBinding.errornya.setText(t.getMessage().toString());
////                Toast.makeText(Existing.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}