package com.yasser.roknaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.roger.catloadinglibrary.CatLoadingView;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;
import com.yasser.roknaapp.Model.AdBanner;
import com.yasser.roknaapp.Model.DatabaseHelper;
import com.yasser.roknaapp.Model.DatabaseVersion;
import com.yasser.roknaapp.Model.Promo;
import com.yasser.roknaapp.localDatabase.DatabaseInterface;
import com.yasser.roknaapp.localDatabase.DatabaseManager;
import com.yasser.roknaapp.localDatabase.PreferencesHelper;
import com.yasser.roknaapp.ui.main.MainActivity;

import java.util.List;
import java.util.Random;

public class Splash extends AppCompatActivity {
    private static final String TAG = "SplashActivity" ;
    private final int SPLASH_DISPLAY_LENGTH = 8000;
    ImageView iv_viewMe_logo;
    Boolean allow_promo=false;
    int codes_number=0;
    Promo promo;
    int promoCode,used_codes;
    DatabaseHelper databaseHelper;
    int databaseVerisonFromServer;
    DatabaseManager databaseManager;
    SplashPresenter splashPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        iv_viewMe_logo = findViewById(R.id.profile_image);
        databaseHelper = new DatabaseHelper(Splash.this);
        databaseHelper.connectToDB();
        databaseManager = new DatabaseManager(this);
        splashPresenter = new SplashPresenter(Splash.this);


        if (isNetworkConnected()){
            YoYo.with(Techniques.FadeInDown)
                    .duration(3000)
                    .repeat(0)
                    .playOn(iv_viewMe_logo);
           getPromoFromDatabase();

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



    private void updateUsedPromocodes(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("promo_codes");

        // Retrieve the object by id
        query.getInBackground("sERzbSQzVb", new GetCallback<ParseObject>() {
            public void done(ParseObject promoCodeRaw, ParseException e) {
                if (e == null) {
                    // Now let's update it with some new data. In this case, only cheatMode and score
                    // will get sent to your Parse Server. playerName hasn't changed.
                    promoCodeRaw.put("used_Codes",promo.getused_codes()+1);
                    promoCodeRaw.put("codes_number", promo.getCodes_numbers()-1);
                    promoCodeRaw.saveInBackground();
                }
            }
        });


    }   private void lockPromoCodes(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("promo_codes");

        // Retrieve the object by id
        query.getInBackground("sERzbSQzVb", new GetCallback<ParseObject>() {
            public void done(ParseObject promoCodeRaw, ParseException e) {
                if (e == null) {
                    // Now let's update it with some new data. In this case, only cheatMode and score
                    // will get sent to your Parse Server. playerName hasn't changed.
                    promoCodeRaw.put("allow_promo",false);
                    promoCodeRaw.saveInBackground();
                }
            }
        });




    }


    public void getPromoFromDatabase() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("promo_codes");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, final ParseException e) {
                if (e == null) {
                    for (ParseObject o : objects) {
                    allow_promo = o.getBoolean("allow_promo");
                    codes_number = o.getInt("codes_number");
                    used_codes = o.getInt("used_Codes");
                    allow_promo = o.getBoolean("allow_promo");
                    databaseVerisonFromServer = o.getInt("database_version");
                    }

                    if (SplashPresenter.checkUpdate(databaseVerisonFromServer, new DatabaseInterface() {
                        @Override
                        public void onStart() {
                            Log.d(TAG, "onStart: ");

                            Log.d(TAG, "done: installing database");

                        }

                        @Override
                        public void onSuccess(@NonNull Boolean value) {
                            Log.d(TAG, "onSuccess: sql successed");
                            new Handler().postDelayed(new Runnable(){
                                @Override
                                public void run() {
                                    /* Create an Intent that will start the Menu-Activity. */
                                    IntentToMainActivity();
                                }
                            }, SPLASH_DISPLAY_LENGTH);


                        }

                        @Override
                        public void onError(@NonNull Throwable throwable) {


                        }
                    }));
                    else {
                        Log.d(TAG, "done: database is already installed");


                        new Handler().postDelayed(new Runnable(){
                            @Override
                            public void run() {
                                /* Create an Intent that will start the Menu-Activity. */
                                IntentToMainActivity();
                            }
                        }, 4000);

                    }





//                    promo = new Promo(allow_promo,codes_number,used_codes);
//                    if (promo.getAllow_promo()==true&&promo.getCodes_numbers()!=0)
//                          {
//                                 promoCode = getRandomNumber(1000,5000);
//                                 updateUsedPromocodes();
//                                 Intent intent = new Intent(Splash.this,MainActivity.class);
//                                 intent.putExtra("promo_code",promoCode);
//                                 startActivity(intent);
//                                 finish();
//                          }
//
//
//                    else {
//                        lockPromoCodes();
//                    }
                } else {

                    Toast.makeText(splashPresenter, "Something Went Wrong", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

    private void IntentToMainActivity() {
        Intent intent = new Intent(Splash.this,MainActivity.class);
        Log.d(TAG, "onSuccess: sql success");

        startActivity(intent);
        finish();
    }

    private int getRandomNumber(int min,int max) {
        return (new Random()).nextInt((max - min) + 1) + min;
    }
}