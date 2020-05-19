package com.yasser.roknaapp.ui.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.roger.catloadinglibrary.CatLoadingView;
import com.yasser.roknaapp.Model.AdBanner;
import com.yasser.roknaapp.Model.DatabaseHelper;
import com.yasser.roknaapp.R;

import java.util.ArrayList;
import java.util.List;

import mehdi.sakout.aboutpage.AboutPage;


public class MainActivity extends AppCompatActivity {
    CardView cvProducts,cvWorkshops,cvEvents,cvAbout_us;
    ArrayList<SlideModel> imageList = new ArrayList<SlideModel>();
    ImageSlider imageSlider;
    ArrayList<AdBanner> adBanners = new ArrayList<AdBanner>();
    DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
    private final int NETWORK_CONNECTIVTY_LENGTH = 2000;
    Intent getPromoIntent;
    CatLoadingView mView;
    TextView tv_promoCode;
    public static int promoCode;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper.connectToDB();
        getPromoIntent = getIntent();
        imageSlider = new ImageSlider(MainActivity.this);
        imageSlider = findViewById(R.id.image_slider);

        cvProducts = findViewById(R.id.cv_product);
        cvWorkshops = findViewById(R.id.cv_workshops);
        cvEvents = findViewById(R.id.cv_Events);
        cvAbout_us = findViewById(R.id.cv_aboutUs);
        tv_promoCode = findViewById(R.id.tv_promoCode);


       promoCode = getPromoIntent.getIntExtra("promo_code",-1);

        if (promoCode!=-1)
        {
            tv_promoCode.setText("Your Promo Code\n"+promoCode+"\nUse This When Order a Product");
            tv_promoCode.setVisibility(View.GONE);
        }





        cvProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ProductCategoryActivity.class);
                intent.putExtra("loadLists",1);
                startActivity(intent);
            }
        });
        cvWorkshops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ListsActivity.class);
                intent.putExtra("loadLists",2);
                startActivity(intent);
            }
        });
        cvEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ListsActivity.class);
                intent.putExtra("loadLists",3);
                startActivity(intent);
            }
        });
        cvAbout_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AboutUsActivity.class);
                startActivity(intent);
            }
        });

        loadAds();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    public void loadAds() {

        mView = new CatLoadingView();
        mView.setCanceledOnTouchOutside(false);
        mView.show(getSupportFragmentManager(),"");



        ParseQuery<ParseObject> query = ParseQuery.getQuery("Ads_Banners");


        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, final ParseException e) {
                if (e == null) {
                    for (ParseObject o : objects) {

                        String adUrl = o.getString("ad_url");
                        ParseFile adImage = o.getParseFile("ad_img");

                        String imgUrl = adImage.getUrl();

                        String ad_snppiet = o.getString("ad_snippet");


                        adBanners.add(new AdBanner(adUrl, imgUrl, ad_snppiet));
                        imageList.add(new SlideModel(imgUrl, ad_snppiet, false));


                    }
                    mView.dismiss();
                    imageSlider.setImageList(imageList,false);

                    imageSlider.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onItemSelected(int i) {
                            String adURL = adBanners.get(i).getAd_url();
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(adURL));
                            startActivity(intent);
                        }
                    });


                } else {
                    Toast.makeText(MainActivity.this, "error: " + e, Toast.LENGTH_LONG).show();
                    mView.dismiss();
                }
            }
        });


    }
}
