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
import android.util.Log;
import android.view.View;

import com.yasser.roknaapp.Adapter.CustomItemClickListener;
import com.yasser.roknaapp.Adapter.ProductAdapter;
import com.yasser.roknaapp.Adapter.WorkshopAdapter;
import com.yasser.roknaapp.Loader.WorkshopLoader;
import com.yasser.roknaapp.Model.DatabaseHelper;
import com.yasser.roknaapp.Model.Product;
import com.yasser.roknaapp.Model.Workshop;
import com.yasser.roknaapp.R;

import java.util.List;

public class WorkshopActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Workshop>> {
    RecyclerView recyclerView;
    DatabaseHelper databaseHelper;
    WorkshopAdapter workshopAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop);
        recyclerView = findViewById(R.id.Pr_recyclerView);

        getSupportLoaderManager().initLoader(0, null, this);




    }

    @NonNull
    @Override
    public Loader<List<Workshop>> onCreateLoader(int id, @Nullable Bundle args) {
        return new WorkshopLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Workshop>> loader, List<Workshop> data) {

        Log.d("tag", "onLoadFinished: dddd" +  data.get(0).getTitle());


        if (workshopAdapter == null) {

            workshopAdapter = new WorkshopAdapter(WorkshopActivity.this, data);
            recyclerView.setLayoutManager(new LinearLayoutManager(WorkshopActivity.this));
            recyclerView.setAdapter(workshopAdapter);
            workshopAdapter.setOnItemClickListener(new CustomItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                }
            });

        } else {

            workshopAdapter.setData(data);
            recyclerView.setLayoutManager(new LinearLayoutManager(WorkshopActivity.this));
            recyclerView.setAdapter(workshopAdapter);
            workshopAdapter.setOnItemClickListener(new CustomItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                }
            });
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Workshop>> loader) {

    }
}
