package com.example.arieahmad.chatfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.arieahmad.chatfirebase.db.SharedPref;
import com.example.arieahmad.chatfirebase.services.ServicePesanFirebase;

public class Splash extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 700;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if(!SharedPref.getInstance(getApplicationContext()).savedName()){
                    Intent iCek = new Intent(getApplicationContext(), InputName.class);
                    startActivity(iCek);
                    finish();
                    //return;
                }else {
                    startService(new Intent(getApplicationContext(), ServicePesanFirebase.class));
                    Intent iCek = new Intent(getApplicationContext(), Chat2.class);
                    startActivity(iCek);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }
}
