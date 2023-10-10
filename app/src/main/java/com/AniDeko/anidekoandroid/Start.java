package com.AniDeko.anidekoandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        // Установка стиля безрамочного
        getWindow().setStatusBarColor(getResources().getColor(R.color.TransparentColor));
    }
}