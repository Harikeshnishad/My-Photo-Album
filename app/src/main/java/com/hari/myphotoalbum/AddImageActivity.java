package com.hari.myphotoalbum;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;

public class AddImageActivity extends AppCompatActivity {

    private ImageView imageViewAddImage;
    private EditText editTextAddTitle,editTextAddDescription;
    private Button buttonSave;

    ActivityResultLauncher<Intent>activityResultLauncherForSelectImage;

    private Bitmap selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_image);

        imageViewAddImage = findViewById(R.id.imageViewAddImage);
        editTextAddTitle = findViewById(R.id.editTextAddTitle);
        editTextAddDescription = findViewById(R.id.editTextAddDescription);
        buttonSave = findViewById(R.id.buttonSave);

        // register
        registerActivityForSelectImage();

        imageViewAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String permission;

                if (Build.VERSION.SDK_INT >= 33){
                    permission = Manifest.permission.READ_MEDIA_IMAGES;
                }else {
                    permission = Manifest.permission.READ_EXTERNAL_STORAGE;
                }
                if (ContextCompat.checkSelfPermission(AddImageActivity.this,permission) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(AddImageActivity.this,new String[]{permission},1);
                }else {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    // before API 30 -> startActivityForResult
                    // ActivityResultLauncher
                    activityResultLauncherForSelectImage.launch(intent);
                }

            }
        });
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            // before API 30 -> startActivityForResult
            // ActivityResultLauncher
            activityResultLauncherForSelectImage.launch(intent);

        }
    }

    public void registerActivityForSelectImage(){

        activityResultLauncherForSelectImage = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                        int resultCode = result.getResultCode();
                        Intent data = result.getData();

                        if (resultCode == RESULT_OK && data != null){

                            try {
                                selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(),data.getData());

                                imageViewAddImage.setImageBitmap(selectedImage);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                        }

                    }
                }
        );

    }
}