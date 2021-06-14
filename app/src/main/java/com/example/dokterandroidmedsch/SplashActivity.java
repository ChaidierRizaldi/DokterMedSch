package com.example.dokterandroidmedsch;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.dokterandroidmedsch.ui.Dashboard;
import com.example.dokterandroidmedsch.ui.login.LoginActivity;
import com.example.dokterandroidmedsch.utils.SharedPreferences;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 3000;

    //Variables
    Animation topAnim, bottomAnim;
    ImageView image,logo;
    SharedPreferences sp_helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);


        //Animmations
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //Hooks
        image = findViewById(R.id.imageView);
        logo = findViewById(R.id.logoView);

        sp_helper = new SharedPreferences(this);

        image.setAnimation(bottomAnim);
        logo.setAnimation(topAnim);

        new Handler().postDelayed(() -> {
            if (!sp_helper.checkLogin()){
                Intent intent = new Intent( SplashActivity.this, LoginActivity.class);

                Pair[] pairs = new Pair[2];
                pairs [0] = new Pair<View,String> (logo, "textView");
                pairs [1] = new Pair<View,String> (image, "imageView");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this,pairs);
                startActivity(intent,options.toBundle());
                finish();
            }else {
                Intent intent = new Intent(SplashActivity.this, Dashboard.class);
                startActivity(intent);
                finish();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }

        },SPLASH_SCREEN);
    }


}