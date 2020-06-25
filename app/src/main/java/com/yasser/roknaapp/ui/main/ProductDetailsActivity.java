package com.yasser.roknaapp.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.yasser.roknaapp.localDatabase.DatabaseLocal;

import java.util.ArrayList;
import java.util.List;

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

                                            String url = "https://api.whatsapp.com/send?phone=" + contact + "&text=" +  "طلب " + product.getName() + " بسعر  " + product.getPrice();
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

        title.setTypeface(ResourcesCompat.getFont(ProductDetailsActivity.this, R.font.jana));
        description.setTypeface(ResourcesCompat.getFont(ProductDetailsActivity.this, R.font.jana));
        price.setTypeface(ResourcesCompat.getFont(ProductDetailsActivity.this, R.font.jana));






        intent_getingData = getIntent();

        Log.d(TAG, "onCreate: id from intent  " + intent_getingData.getStringExtra("prid"));
        selectProdcutByID(intent_getingData.getStringExtra("prid"));
        if (product!=null)
        {

                        RequestOptions requestOptions = new RequestOptions()
                                .placeholder(R.drawable.loading);



                        Glide.with(ProductDetailsActivity.this)
                                .load(product.getImgURL1())
                                .apply(requestOptions)
                                .into(image1);
                        Glide.with(ProductDetailsActivity.this)
                                .load(product.getImgURL2())
                                .apply(requestOptions)
                                .into(image2);
                        Glide.with(ProductDetailsActivity.this)
                                .load(product.getImgURL3())
                                .apply(requestOptions)
                                .into(image3);
                        Glide.with(ProductDetailsActivity.this)
                                .load(product.getImgURL4())
                                .apply(requestOptions)
                                .into(image4);

                        title.setText(product.getName());
                        description.setText(product.getDescription());
                        price.setText(product.getPrice()+" EGP");

                        image1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sendToPreview(product.getImgURL1());
                            }
                        });
                        image2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sendToPreview(product.getImgURL2());
                            }
                        });

                        image3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sendToPreview(product.getImgURL3());
                            }
                        });

                        image4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sendToPreview(product.getImgURL4());
                            }
                        });

            
        }
        else {
            Toast.makeText(this, "Something went wrong..try again later", Toast.LENGTH_SHORT).show();
        }
}
public void sendToPreview(String imgUrl)
{
    Intent intent = new Intent(ProductDetailsActivity.this,ViewImageActivity.class);
    intent.putExtra("image_url",imgUrl);
    startActivity(intent);
}

public void selectProdcutByID(String id){

    DatabaseLocal mydb =  DatabaseLocal.getInstance(ProductDetailsActivity.this);
    SQLiteDatabase db = mydb.getReadableDatabase();
    Product result = new Product();
    product = new Product();
    Cursor GroupCursor = null;
    try {
    GroupCursor = db.rawQuery("select * from " + "products" + " where " + "parse_server_id" + "='" + id + "'" , null);

    if (GroupCursor != null && GroupCursor.moveToFirst()) {


        Log.d(TAG, "selectProdcutByID: SUCESS!!");
        Log.d(TAG, "selectProdcutByID: product id " + GroupCursor.getString(1) );
        product.setId(GroupCursor.getString(1));
        product.setName(GroupCursor.getString(2));

        product.setSale(GroupCursor.getString(3));

        product.setDescription(GroupCursor.getString(4));


        product.setPrice(GroupCursor.getString(5));
        product.setImgURL1(GroupCursor.getString(6));
        product.setImgURL2(GroupCursor.getString(7));
        product.setImgURL3(GroupCursor.getString(8));
        product.setImgURL4(GroupCursor.getString(9));
        product.setCategory_id(GroupCursor.getInt(10));
    }

    } finally {
        if (GroupCursor != null) {
            GroupCursor.close();
        }

    }
}

}
