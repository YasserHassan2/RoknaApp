package com.yasser.roknaapp.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.yasser.roknaapp.Adapter.CustomItemClickListener;
import com.yasser.roknaapp.Adapter.ProductAdapter;
import com.yasser.roknaapp.Model.DatabaseHelper;
import com.yasser.roknaapp.Model.Product;
import com.yasser.roknaapp.R;

import java.util.ArrayList;
import java.util.List;

public class ListsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseHelper databaseHelper;
    int loadList;
    Intent getData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);
        recyclerView = findViewById(R.id.Pr_recyclerView);

        databaseHelper = new DatabaseHelper(ListsActivity.this);
        getData = getIntent();
        loadList=getData.getIntExtra("loadLists",-1);
        databaseHelper.connectToDB();

        switch (loadList){

            case 1:
                databaseHelper.loadProducts(recyclerView);
                break;
            case 2:
                databaseHelper.loadWorkshops(recyclerView);
                break;
            case 3:
                databaseHelper.loadEvents(recyclerView);
                break;
                default:
                    databaseHelper.loadProducts(recyclerView);

        }




    }

}
