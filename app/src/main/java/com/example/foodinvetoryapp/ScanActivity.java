package com.example.foodinvetoryapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;

import java.util.List;

public class ScanActivity extends AppCompatActivity {
    private MaterialButton cameraButton;
    private MaterialButton galleryButton;
    private MaterialButton scanButton;
    private TextView resultTextView;
    private ImageView imageView;
    private long storageId;
    private int foodProductPosition;

    public static final int CAMERA_REQUEST_CODE = 100;
    public static final int STORAGE_REQUEST_CODE = 101;

    private String[] cameraPermisions;
    private String[] storagePermisions;

    private Uri imageUri = null;

    public static final String LOG_TAG = "MAIN_TAG";

    private BarcodeScannerOptions barcodeScannerOptions;
    private BarcodeScanner barcodeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        cameraButton = findViewById(R.id.cameraButton);
        galleryButton = findViewById(R.id.galleryButton);
        scanButton = findViewById(R.id.scanButton);
        resultTextView = findViewById(R.id.resultTextView);
        imageView = findViewById(R.id.imageView);

        Intent intent = getIntent();
        storageId = intent.getLongExtra(TAG.STORAGE_ID, -1);
        foodProductPosition = intent.getIntExtra(TAG.FOOD_PRODUCT_POSITION, -1);

        cameraPermisions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermisions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        barcodeScannerOptions = new BarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
                .build();
        barcodeScanner = BarcodeScanning.getClient(barcodeScannerOptions);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    pickImageCamera();
                } else {
                    requestCameraPermission();
                }
            }
        });

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkStoragePermission()) {
                    pickImageGallery();
                } else {
                    requestStoragePermission();
                }
            }
        });

        scanButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageUri == null) {
                    Toast.makeText(ScanActivity.this, "Pick image first", Toast.LENGTH_SHORT).show();
                } else {
                    detectResultFromImage();
                }
            }
        }));

    }

    private void detectResultFromImage() {
        try {
            InputImage inputImage = InputImage.fromFilePath(this, imageUri);
            Task<List<Barcode>> barcodeResults = barcodeScanner.process(inputImage)
                    .addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                        @Override
                        public void onSuccess(List<Barcode> barcodes) {
                            extractCode(barcodes);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ScanActivity.this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (Exception e) {
            Toast.makeText(this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SetTextI18n")
    private void extractCode(List<Barcode> barcodes) {
        String rawValue = null;
        for (Barcode barcode : barcodes) {
            Rect bounds = barcode.getBoundingBox();
            Point[] corners = barcode.getCornerPoints();

            rawValue = barcode.getRawValue();
            Log.d(LOG_TAG, "extractCode: " + rawValue);

            resultTextView.setText("raw value: " + rawValue);
        }
        Intent intent = new Intent(this, AddFoodProductActivity.class);
        intent.putExtra(TAG.BARCODE_VALUE, rawValue);
        intent.putExtra(TAG.STORAGE_ID, storageId);
        intent.putExtra(TAG.FOOD_PRODUCT_POSITION, foodProductPosition);
        startActivity(intent);
        finish();
    }

    private void pickImageGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        galleryActivityResultLauncher.launch(intent);
    }

    private final ActivityResultLauncher<Intent> galleryActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        imageUri = data.getData();
                        Log.d(LOG_TAG, "onActivityResult: imageUri: " + imageUri);
                        imageView.setImageURI(imageUri);
                    } else {
                        Toast.makeText(ScanActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    private void pickImageCamera() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, "Sample title");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "Sample description");

        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        cameraActivityResultLauncher.launch(intent);
    }

    private final ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Log.d(LOG_TAG, "onActivityResult: imageUri: " + imageUri);
                        imageView.setImageURI(imageUri);
                    } else {
                        Toast.makeText(ScanActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        return result;
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, storagePermisions, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        boolean resultCamera = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        boolean resultStorage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        return resultCamera && resultStorage;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, cameraPermisions, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && storageAccepted) {
                        pickImageCamera();
                    } else {
                        Toast.makeText(this, "Camera & Storage permissions are required...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
            case STORAGE_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted) {
                        pickImageGallery();
                    } else {
                        Toast.makeText(this, "Storage permission is required", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }
    }
}