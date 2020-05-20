package com.yasser.roknaapp.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;
import com.stfalcon.imageviewer.StfalconImageViewer;
import com.yasser.roknaapp.Model.DatabaseHelper;
import com.yasser.roknaapp.Model.Product;
import com.yasser.roknaapp.R;

import java.util.ArrayList;

import br.com.felix.imagezoom.ImageZoom;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ozaydin.serkan.com.image_zoom_view.ImageViewZoom;
import ozaydin.serkan.com.image_zoom_view.ImageViewZoomConfig;

public class ProductDetailsActivity extends AppCompatActivity {

    //imageViews
    @BindView(R.id.iv_image1)
    ImageView image1;
    @BindView(R.id.iv_image2)
    ImageView  image2;
    @BindView(R.id.iv_image3)
    ImageView  image3;
    @BindView(R.id.iv_image4)
    ImageView  image4;

    //TextViews
    @BindView(R.id.tv_title)
    TextView title;
    @BindView(R.id.tv_desc)
    TextView description;
    @BindView(R.id.tv_price)
    TextView price;

    //Buttons
    @BindView(R.id.btn_reviewThispro)
    Button btn_review;
    @BindView(R.id.btn_orderNow)
    Button btn_order;
    @BindView(R.id.btn_showReviews)
    Button btn_showReviews;
    String TAG = "ProductDetails";

    DatabaseHelper databaseHelper = new DatabaseHelper(ProductDetailsActivity.this);
    Intent intent_getingData;
    String product_ID = "";
    String prName;
    String prPrice;
    String imgURL11,imgURL22,imgURL33,imgURL44;
    ArrayList<String> images = new ArrayList<String>();
    Product product;
    String contact = "+20 1129759853"; // use country code with your phone number

    boolean isImageFitToScreen;


    @OnClick({R.id.btn_orderNow,R.id.iv_image1})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_orderNow: {
//                new FancyGifDialog.Builder(ProductDetailsActivity.this)
//                                    .setTitle("Contact To Order")
//                                    .setMessage("Want '"+prName+"' , please Contact to deliver")
//                                    .setNegativeBtnText("Cancel")
//                                    .setPositiveBtnBackground("#008000")
//                                    .setPositiveBtnText("Contact")
//                                    .setNegativeBtnBackground("#FFA9A7A8")
//                                    .setGifResource(R.drawable.roknalogo)   //Pass your Gif here
//                                    .isCancellable(true)
//                                    .OnPositiveClicked(new FancyGifDialogListener() {
//                                        @Override
//                                        public void OnClick() {

                                            String url = "https://api.whatsapp.com/send?phone=" + contact + "&text=" +  "طلب " + prName + " بسعر  " + prPrice;
                                            try {
                                                PackageManager pm = getApplicationContext().getPackageManager();
                                                pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                                                Intent i = new Intent(Intent.ACTION_VIEW);
                                                i.setData(Uri.parse(url));
                                                startActivity(i);
                                            } catch (PackageManager.NameNotFoundException e) {
                                                Toast.makeText(ProductDetailsActivity.this, "Whatsapp app not installed in your phone", Toast.LENGTH_LONG).show();
                                            }
//                                        }
//                                    })
//                                    .OnNegativeClicked(new FancyGifDialogListener() {
//                                        @Override
//                                        public void OnClick() {
//
//                                        }
//                                    })
//                                    .build();
                break;
            }
            case R.id.iv_image1: {

                
            break;

            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        ButterKnife.bind(this);
        // Return Image's base 64 code
       // imageViewZoom.getBase64();


        intent_getingData = getIntent();
        if (databaseHelper.LOAD_FROM_LOCAL==true)
        {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Products");
            query.fromLocalDatastore();
            query.whereEqualTo("id",intent_getingData.getStringExtra("prid"));
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if (e==null)
                    {

                        String pr_id = object.getObjectId();
                        prName = object.getString("name");
                        String prDesc = object.getString("description");
                        prPrice = object.getString("price");
                        String prSale = object.getString("sale");

                        String imgURL1 = object.getString("url1");
                        imgURL11 = imgURL1;

                        String imgURL2 = object.getString("url2");
                        imgURL22 = imgURL22;

                        String imgURL3 = object.getString("url3");
                        imgURL33 = imgURL3;

                        String imgURL4 = object.getString("url4");
                        imgURL44 = imgURL4;

                        images.add(imgURL11);
                        images.add(imgURL22);
                        images.add(imgURL33);
                        images.add(imgURL44);
                        product = new Product(pr_id,prName,prDesc,prPrice,prSale,imgURL1,imgURL2,imgURL3,imgURL4);

                        RequestOptions requestOptions = new RequestOptions()
                                .placeholder(R.drawable.roknalogo);



                        Glide.with(ProductDetailsActivity.this)
                                .load(imgURL1)
                                .apply(requestOptions)
                                .into(image1);
                        Glide.with(ProductDetailsActivity.this)
                                .load(imgURL2)
                                .apply(requestOptions)
                                .into(image2);
                        Glide.with(ProductDetailsActivity.this)
                                .load(imgURL3)
                                .apply(requestOptions)
                                .into(image3);
                        Glide.with(ProductDetailsActivity.this)
                                .load(imgURL4)
                                .apply(requestOptions)
                                .into(image4);

                        title.setText(prName);
                        description.setText(prDesc);
                        price.setText(prPrice+" EGP");

                        image1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sendToPreview(imgURL1);
                            }
                        });
                        image2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sendToPreview(imgURL2);
                            }
                        });

                        image3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sendToPreview(imgURL3);
                            }
                        });

                        image4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sendToPreview(imgURL4);
                            }
                        });



                    }else
                    {
                        Log.d(TAG, "done: exception : "+ e);
                        Toast.makeText(ProductDetailsActivity.this, "Sorry, Something Wrong Please try later", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ProductDetailsActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }
}
public void sendToPreview(String imgUrl)
{
    Intent intent = new Intent(ProductDetailsActivity.this,ViewImageActivity.class);
    intent.putExtra("image_url",imgUrl);
    startActivity(intent);
}
}
