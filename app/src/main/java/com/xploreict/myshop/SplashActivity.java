package com.xploreict.myshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        Intent regiintent = new Intent(SplashActivity.this, RegisterActivity.class);
        startActivity(regiintent);
        finish();
    }
}