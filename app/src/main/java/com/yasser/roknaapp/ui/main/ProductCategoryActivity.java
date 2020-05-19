package com.yasser.roknaapp.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.roger.catloadinglibrary.CatLoadingView;
import com.yasser.roknaapp.Adapter.CategoriesAdapter;
import com.yasser.roknaapp.Adapter.CustomItemClickListener;
import com.yasser.roknaapp.Adapter.ProductAdapter;
import com.yasser.roknaapp.Dialog;
import com.yasser.roknaapp.Model.Category;
import com.yasser.roknaapp.Model.DatabaseHelper;
import com.yasser.roknaapp.Model.Product;
import com.yasser.roknaapp.R;

import java.util.ArrayList;
import java.util.List;

public class ProductCategoryActivity extends AppCompatActivity {
    CatLoadingView mView;
    RecyclerView recyclerView;
    DatabaseHelper databaseHelper;
    Dialog dialog;
    ArrayList<Category> categoriesList = new ArrayList<Category>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category);

        recyclerView = findViewById(R.id.cat_recyclerView);
        dialog = new Dialog(ProductCategoryActivity.this);
        databaseHelper = new DatabaseHelper(ProductCategoryActivity.this);
        databaseHelper.connectToDB();

        loadCategies(recyclerView);
    }

    public void loadCategies(RecyclerView recyclerView) {

        mView = new CatLoadingView();
        mView.setCanceledOnTouchOutside(false);
        mView.show(getSupportFragmentManager(), "");


        final ParseQuery<ParseObject> query = ParseQuery.getQuery("category");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, final ParseException e) {
                if (e == null) {
                    for (ParseObject o : objects) {

                        String cat_id = o.getObjectId();
                        String catName = o.getString("name");
                        int catNumId = o.getInt("category_id");


                        Category category = new Category(catNumId, catName);
                        categoriesList.add(category);

                    }
                    mView.dismiss();

                    recyclerView.setLayoutManager(new LinearLayoutManager(ProductCategoryActivity.this));

                    CategoriesAdapter RecyclerViewAdapter = new CategoriesAdapter(ProductCategoryActivity.this, categoriesList);

                    recyclerView.setAdapter(RecyclerViewAdapter);
                    if (categoriesList.isEmpty()) {
                        dialog.showAlertDialogToMain("No Products Avaliable Now..");


                    }
                    RecyclerViewAdapter.setOnItemClickListener(new CustomItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent intent = new Intent(ProductCategoryActivity.this, ListsActivity.class);
                            intent.putExtra("loadLists",1);
                            intent.putExtra("cat_id",categoriesList.get(position).getId());
                            startActivity(intent);

//
                        }
                    });

                } else {
                    mView.dismiss();
                    Toast.makeText(ProductCategoryActivity.this, "error= " + e, Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
