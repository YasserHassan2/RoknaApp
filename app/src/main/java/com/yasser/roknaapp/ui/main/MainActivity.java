package com.yasser.roknaapp.ui.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
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
import com.yasser.roknaapp.Model.AdBanner;
import com.yasser.roknaapp.Model.DatabaseHelper;
import com.yasser.roknaapp.R;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    CardView cvProducts;
    ArrayList<SlideModel> imageList = new ArrayList<SlideModel>();
    ImageSlider imageSlider;
    ArrayList<AdBanner> adBanners = new ArrayList<AdBanner>();
    DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper.connectToDB();

        imageSlider = new ImageSlider(MainActivity.this);
        imageSlider = findViewById(R.id.image_slider);

        cvProducts = findViewById(R.id.cv_product);
        cvProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ListsActivity.class);
                startActivity(intent);
            }
        });

        loadAds();
    }

    public void loadAds() {


        final ProgressDialog pd = ProgressDialog.show(MainActivity.this, "", "Loading Please wait..", true);


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
                    pd.dismiss();
                    imageSlider.setImageList(imageList,true);

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
                    pd.dismiss();
                }
            }
        });


    }
}
