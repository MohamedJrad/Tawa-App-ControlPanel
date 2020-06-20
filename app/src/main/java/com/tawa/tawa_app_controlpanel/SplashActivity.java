package com.tawa.tawa_app_controlpanel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.tawa.tawa_app_controlpanel.ui.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ImageView logo =findViewById(R.id.logo);
        logo.setImageResource(R.drawable.tawa_app_logo);



        Thread th = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2000);
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);

                    if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));

                    }else {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    }

                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }
        };
        th.start();



    }
}