package com.example.galleryapplicationunsplash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class FullImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

        ImageButton imageButton = findViewById(R.id.backBtnf);

        ImageView imageView = findViewById(R.id.myZoomageViewId);
        Glide.with(this).load(getIntent().getStringExtra("image"))
                .into(imageView);

        imageButton.setOnClickListener(view -> {

           goBack();
        });


    }

    private void goBack() {
        Intent intent = new Intent(FullImageActivity.this, MainActivity.class);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(FullImageActivity.this, MainActivity.class);
        startActivity(intent);
    }
}