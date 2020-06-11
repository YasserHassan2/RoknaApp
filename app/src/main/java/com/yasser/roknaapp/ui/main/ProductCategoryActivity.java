package com.yasser.roknaapp.ui.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.roger.catloadinglibrary.CatLoadingView;
import com.yasser.roknaapp.Adapter.CategoriesAdapter;
import com.yasser.roknaapp.Adapter.CustomItemClickListener;
import com.yasser.roknaapp.Dialogs;
import com.yasser.roknaapp.Loader.CategoriesLoader;
import com.yasser.roknaapp.Model.Category;
import com.yasser.roknaapp.Model.DatabaseHelper;
import com.yasser.roknaapp.R;

import java.util.ArrayList;
import java.util.List;

public class ProductCategoryActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Category>> {
    private static final String TAG = "productCat";
    CatLoadingView mView;
    RecyclerView recyclerView;
    DatabaseHelper databaseHelper;
    Dialogs dialogs;
    static List<Category> categoriesList = new ArrayList<Category>();
    CategoriesAdapter RecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category);
        getSupportLoaderManager().initLoader(0, null, this);

        recyclerView = findViewById(R.id.cat_recyclerView);
        dialogs = new Dialogs(ProductCategoryActivity.this);
        databaseHelper = new DatabaseHelper(ProductCategoryActivity.this);
        databaseHelper.connectToDB();








    }



    @NonNull
    @Override
    public Loader<List<Category>> onCreateLoader(int id, @Nullable Bundle args) {
        return new CategoriesLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Category>> loader, List<Category> data) {


        RecyclerViewAdapter = new CategoriesAdapter(this, data);

        RecyclerViewAdapter.setData(data);


        recyclerView.setLayoutManager(new LinearLayoutManager(ProductCategoryActivity.this));
        recyclerView.setAdapter(RecyclerViewAdapter);

        RecyclerViewAdapter.setOnItemClickListener(new CustomItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {



                Intent intent = new Intent(ProductCategoryActivity.this, ListsActivity.class);
                intent.putExtra("loadLists",1);
                intent.putExtra("cat_id",data.get(position).getId());
                startActivity(intent);

//
            }
        });
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Category>> loader) {

    }
}
