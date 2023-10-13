package com.AniDeko.anidekoandroid;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;


public class Start extends AppCompatActivity {

    FrameLayout BlurFrame;
    Button startButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        // Установка стиля безрамочного
        getWindow().setStatusBarColor(getResources().getColor(R.color.TransparentColor));

        //Создания и установка blur эффекта на frame
        BlurFrame = findViewById(R.id.BlurFrame);
        RenderEffect blurEffect = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            blurEffect = RenderEffect.createBlurEffect(20f,20f, Shader.TileMode.MIRROR);
            BlurFrame.setRenderEffect(blurEffect);
        }


        //Кнопка старта
        startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Start.this,MainActivity.class));
                finish();
            }
        });

    }
}