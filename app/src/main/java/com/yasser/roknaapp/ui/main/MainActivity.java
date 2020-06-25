package com.yasser.roknaapp.ui.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.roger.catloadinglibrary.CatLoadingView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.yasser.roknaapp.Adapter.SliderAdapterExample;
import com.yasser.roknaapp.Loader.Ads_BannerLoader;
import com.yasser.roknaapp.Model.AdBanner;
import com.yasser.roknaapp.Model.DatabaseHelper;
import com.yasser.roknaapp.Model.Product;
import com.yasser.roknaapp.R;

import java.util.ArrayList;
import java.util.List;

import mehdi.sakout.aboutpage.AboutPage;


public class MainActivity extends AppCompatActivity implements  LoaderManager.LoaderCallbacks<List<AdBanner>>{

    SliderView sliderView;
    private SliderAdapterExample adapter;

    private static final String TAG = "MainActivity" ;
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
    TextView tv_products,tv_workshops,tv_events;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper.connectToDB();
        getPromoIntent = getIntent();
        sliderView = findViewById(R.id.imageSlider);
//        imageSlider = new ImageSlider(MainActivity.this);
//        imageSlider = findViewById(R.id.image_slider);
//        imageSlider.setClickable(false);

        cvProducts = findViewById(R.id.cv_product);
        cvWorkshops = findViewById(R.id.cv_workshops);
        cvEvents = findViewById(R.id.cv_Events);
        cvAbout_us = findViewById(R.id.cv_aboutUs);
        tv_promoCode = findViewById(R.id.tv_promoCode);

        tv_products = findViewById(R.id.tv_products);
        tv_events = findViewById(R.id.tv_events);
        tv_workshops = findViewById(R.id.tv_workshops);

        tv_products.setTypeface(ResourcesCompat.getFont(MainActivity.this, R.font.jana));
        tv_events.setTypeface(ResourcesCompat.getFont(MainActivity.this, R.font.jana));
        tv_workshops.setTypeface(ResourcesCompat.getFont(MainActivity.this, R.font.jana));

        getSupportLoaderManager().initLoader(0, null, this);

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
                Intent intent = new Intent(MainActivity.this,WorkshopActivity.class);
                intent.putExtra("loadLists",2);
                startActivity(intent);
            }
        });
        cvEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,EventsActivity.class);
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

      //  loadAds();
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
//
//                    imageSlider.setItemClickListener(new ItemClickListener() {
//                        @Override
//                        public void onItemSelected(int i) {
//                            String adURL = adBanners.get(i).getAd_url();
//                            Intent intent = new Intent(Intent.ACTION_VIEW);
//                            intent.setData(Uri.parse(adURL));
//                            startActivity(intent);
//                        }
//                    });


                } else {
                    Toast.makeText(MainActivity.this, "error: " + e, Toast.LENGTH_LONG).show();
                    mView.dismiss();
                }
            }
        });


    }

    @NonNull
    @Override
    public Loader<List<AdBanner>> onCreateLoader(int id, @Nullable Bundle args) {
        return new Ads_BannerLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<AdBanner>> loader, List<AdBanner> data) {


        adapter = new SliderAdapterExample(this);
        sliderView.setSliderAdapter(adapter);
        adapter.renewItems(data);
        sliderView.setIndicatorAnimation(IndicatorAnimations.THIN_WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.startAutoCycle();
        sliderView.setScrollTimeInMillis(6000);
        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);

//        if (data!=null) {
//            Log.d(TAG, "onLoadFinished: data.size()"+ data.size());
//
//            for (int i = 0; i < data.size(); i++) {
//
//
//                Log.d(TAG, "onLoadFinished: adbanner list " + data.get(i).getAdd_snppit());
//                imageList.add(new SlideModel(data.get(i).getAd_img(), data.get(i).getAdd_snppit(), false));
//
//
//            }
//            imageSlider.setImageList(imageList, false);
//            data.clear();
//        }
//        else
//        {
//            Toast.makeText(this, "database corruption!", Toast.LENGTH_SHORT).show();
//
//        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<AdBanner>> loader) {

    }
}
