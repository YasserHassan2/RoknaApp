package com.yasser.roknaapp.Model;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.yasser.roknaapp.Adapter.CustomItemClickListener;
import com.yasser.roknaapp.Adapter.ProductAdapter;
import com.yasser.roknaapp.ui.main.ListsActivity;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    Context mContext;

    ArrayList<Product> productList = new ArrayList<Product>();

    public DatabaseHelper(Context mContext) {
        this.mContext = mContext;
    }

    public void connectToDB(){

        //# database connection
        Parse.initialize(new Parse.Configuration.Builder(mContext)
                .applicationId("tbG3gg7UGswBUAi2DYtFm4yj0x9PBMbeUQAmrMvC")
                .clientKey("ZrkL2lgfxaBEE0WL3b53VamVICRHQWqxInA9iUBj")
                .server("https://parseapi.back4app.com")
                .build()
        );

    }
    public void loadProducts(final RecyclerView recyclerView) {
        final ProgressDialog pd = ProgressDialog.show(mContext, "", "Loading Please wait..", true);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("products");

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

                        ParseFile imageFile = o.getParseFile("image");
                        String imageURL = imageFile.getUrl();


                        Product product = new Product(prName,prDesc,prPrice,prSale,imageURL);
                        productList.add(product);

                    }

                    recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

                    ProductAdapter RecyclerViewAdapter = new ProductAdapter(mContext, productList);

                    recyclerView.setAdapter(RecyclerViewAdapter);

                    RecyclerViewAdapter.setOnItemClickListener(new CustomItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {


                        }
                    });

                } else {
                    Toast.makeText(mContext, "error= " + e, Toast.LENGTH_LONG).show();
                }
                pd.dismiss();
            }
        });

    }



}