package com.example.seanr.usphoto;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class PhotoActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int RESULT_LOAD_IMAGE = 1;

    LinearLayout layout;
    ImageView imageToUpload, imageToUpload2;
    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        imageToUpload = (ImageView) findViewById(R.id.imageToUpload);
        imageToUpload2 = (ImageView) findViewById(R.id.imageToUpload2);
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        layout = (LinearLayout) findViewById(R.id.layout);

        imageToUpload.setOnClickListener(this);
        imageToUpload2.setOnClickListener(this);
        imageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.imageToUpload:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                break;
            case R.id.imageToUpload2:
                Intent galleryIntent2 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent2, RESULT_LOAD_IMAGE);
                break;
            case R.id.imageButton:
                ImageView myImage = new ImageView(this);
                myImage.setImageResource(R.drawable.logo);
                layout.addView(myImage);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            imageToUpload.setImageURI(selectedImage);



            final AlertDialog.Builder dialog = new AlertDialog.Builder(this).setTitle("Uploaded").setMessage("Picture has been uploaded");
            dialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int whichButton) {
                   // exitLauncher();
                }
            });
            final AlertDialog alert = dialog.create();
            alert.show();

// Hide after some seconds
            final Handler handler  = new Handler();
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (alert.isShowing()) {
                        alert.dismiss();
                    }
                }
            };

            alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    handler.removeCallbacks(runnable);
                }
            });

            handler.postDelayed(runnable, 1800);
        }
    }


}
