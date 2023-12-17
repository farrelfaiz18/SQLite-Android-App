package com.example.uass;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 2000; // Durasi tampilan splash screen (2 detik)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Handler untuk menunda perpindahan ke MainActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Intent untuk beralih ke MainActivity setelah SPLASH_TIME_OUT
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);

                // Tutup aktivitas splash screen agar tidak bisa kembali ke sini
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
