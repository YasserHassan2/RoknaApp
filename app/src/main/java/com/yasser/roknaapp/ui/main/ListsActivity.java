package com.yasser.roknaapp.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.roger.catloadinglibrary.CatLoadingView;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;
import com.yasser.roknaapp.Adapter.CustomItemClickListener;
import com.yasser.roknaapp.Adapter.ProductAdapter;
import com.yasser.roknaapp.Dialog;
import com.yasser.roknaapp.Model.DatabaseHelper;
import com.yasser.roknaapp.Model.Product;
import com.yasser.roknaapp.R;
import com.yasser.roknaapp.Splash;

import java.util.ArrayList;
import java.util.List;

import static com.yasser.roknaapp.ui.main.MainActivity.promoCode;

public class ListsActivity extends AppCompatActivity {
    private static final String TAG ="ListsActvity" ;
    RecyclerView recyclerView;
    DatabaseHelper databaseHelper;
    TextView tv_PageTitle;
    ArrayList<Product> productList = new ArrayList<Product>();
    int loadList;
    Intent getData;
    CatLoadingView mView;
    int product_category_id;
    Dialog dialog;
    String colorStr="";
    MainActivity mainActivity;
    TextView tv_promoCode;
    String contact = "+20 1129759853"; // use country code with your phone number
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);

        recyclerView = findViewById(R.id.Pr_recyclerView);
        tv_PageTitle = findViewById(R.id.tv_title);
        colorStr = getResources().getString(R.string.greencolor);
        dialog = new Dialog(ListsActivity.this);
        tv_promoCode = findViewById(R.id.tv_promoCode);
        mainActivity = new MainActivity();


        if (promoCode!=-1)
        {
            tv_promoCode.setText("Your Promo Code\n"+promoCode+"\nUse This When Order a Product");
            tv_promoCode.setVisibility(View.GONE);
        }




        databaseHelper = new DatabaseHelper(ListsActivity.this);
        getData = getIntent();
        loadList=getData.getIntExtra("loadLists",-1);
        product_category_id = getData.getIntExtra("cat_id",1);
        databaseHelper.connectToDB();

        switch (loadList){

            case 1:
                if (databaseHelper.LOAD_FROM_LOCAL==true)
                loadProducts(recyclerView);
                else
                loadProducts(recyclerView);
                break;
            case 2:
                databaseHelper.loadWorkshops(recyclerView);
                tv_PageTitle.setText("Workshops");
                break;
            case 3:
                databaseHelper.loadEvents(recyclerView);
                tv_PageTitle.setText("Events");
                break;
                default:
                    loadProducts(recyclerView);

        }




    }
    public void loadProducts(final RecyclerView recyclerView) {
                        mView = new CatLoadingView();
                        mView.setCanceledOnTouchOutside(false);
                        mView.show(getSupportFragmentManager(),"");
                        final ParseQuery<ParseObject> query = ParseQuery.getQuery("products");
                        query.whereEqualTo("category_id",product_category_id);
                        query.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> objects, final ParseException e) {
                                if (e == null) {
                                    for (ParseObject o : objects) {

                                        String pr_id = o.getObjectId();
                                        String prName = o.getString("name");
                                        String prDesc = o.getString("description");
                                        String prPrice = o.getString("price");
                                        String prSale = o.getString("sale");

                                        ParseFile imageFile1 = o.getParseFile("image");
                                        String imageURL1 = imageFile1.getUrl();
                                        ParseFile imageFile2 = o.getParseFile("imageURL1");
                                        String imageURL2 = imageFile2.getUrl();
                                        ParseFile imageFile3 = o.getParseFile("imageURL2");
                                        String imageURL3 = imageFile3.getUrl();
                                        ParseFile imageFile4 = o.getParseFile("imageURL3");
                                        String imageURL4= imageFile4.getUrl();


                                        Product product = new Product(pr_id,prName,prDesc,prPrice,prSale,imageURL1,imageURL2,imageURL3,imageURL4);
                                        productList.add(product);

                                    }
                                    mView.dismiss();
                                    databaseHelper.pinProductsinBackground(productList);

                                    recyclerView.setLayoutManager(new LinearLayoutManager(ListsActivity.this));

                                    ProductAdapter RecyclerViewAdapter = new ProductAdapter(ListsActivity.this, productList);

                    recyclerView.setAdapter(RecyclerViewAdapter);
                    if (productList.isEmpty()) {
                       dialog.showAlertDialogToMain("No Products Avaliable at moment, stay tuned!");


                    }
                    RecyclerViewAdapter.setOnItemClickListener(new CustomItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Log.d(TAG, "onItemClick: prid " + productList.get(position).getId());
                            Intent intent = new Intent(ListsActivity.this,ProductDetailsActivity.class);
                            intent.putExtra("prid",productList.get(position).getId());
                            startActivity(intent);

//
                        }
                    });

                } else {
                    mView.dismiss();
                    Toast.makeText(ListsActivity.this, "error= " + e, Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    @Override
    public void onBackPressed() {
    finish();
    }

}
