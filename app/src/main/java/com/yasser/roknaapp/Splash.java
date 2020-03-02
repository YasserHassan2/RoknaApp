package com.yasser.roknaapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;
import com.yasser.roknaapp.ui.main.MainActivity;

public class Splash extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 4000;
    ImageView iv_viewMe_logo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        iv_viewMe_logo = findViewById(R.id.profile_image);

        if (isNetworkConnected()){
            YoYo.with(Techniques.FadeInDown)
                    .duration(3000)
                    .repeat(0)
                    .playOn(iv_viewMe_logo);

            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    /* Create an Intent that will start the Menu-Activity. */
                    Intent mainIntent = new Intent(Splash.this, MainActivity.class);
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();
                }
            }, SPLASH_DISPLAY_LENGTH);
        }
    }


    private Boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (! (cm.getActiveNetworkInfo() != null)) {
            new FancyGifDialog.Builder(Splash.this)
                    .setTitle("No Internet")
                    .setMessage("Please Check Internet Connectavity and try again..")
                    .setNegativeBtnText("Cancel")
                    .setPositiveBtnBackground("#FF4081")
                    .setPositiveBtnText("Ok")
                    .setNegativeBtnBackground("#FFA9A7A8")
                    .setGifResource(R.drawable.gif1)   //Pass your Gif here
                    .isCancellable(true)
                    .OnPositiveClicked(new FancyGifDialogListener() {
                        @Override
                        public void OnClick() {
                          finish();
                        }
                    })
                    .OnNegativeClicked(new FancyGifDialogListener() {
                        @Override
                        public void OnClick() {
                         finish();
                        }
                    })
                    .build();

        }
        return true;
    }
}