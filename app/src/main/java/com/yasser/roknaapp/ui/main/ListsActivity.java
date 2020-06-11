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
import android.widget.TextView;

import com.roger.catloadinglibrary.CatLoadingView;
import com.yasser.roknaapp.Adapter.CustomItemClickListener;
import com.yasser.roknaapp.Adapter.ProductAdapter;
import com.yasser.roknaapp.Dialogs;
import com.yasser.roknaapp.Loader.ProductsLoader;
import com.yasser.roknaapp.Model.DatabaseHelper;
import com.yasser.roknaapp.Model.Product;
import com.yasser.roknaapp.R;

import java.util.ArrayList;
import java.util.List;

import static com.yasser.roknaapp.ui.main.MainActivity.promoCode;

public class ListsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Product>> {
    private static final String TAG = "ListsActvity";
    RecyclerView recyclerView;
    DatabaseHelper databaseHelper;
    TextView tv_PageTitle;
    int loadList;
    Intent getData;
    CatLoadingView mView;
    int product_category_id;
    Dialogs dialogs;
    String colorStr = "";
    MainActivity mainActivity;
    TextView tv_promoCode;
    ProductAdapter RecyclerViewAdapter_loader;
    String contact = "+20 1129759853"; // use country code with your phone number

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);

        recyclerView = findViewById(R.id.Pr_recyclerView);
        tv_PageTitle = findViewById(R.id.tv_title);
        colorStr = getResources().getString(R.string.greencolor);
        dialogs = new Dialogs(ListsActivity.this);
        tv_promoCode = findViewById(R.id.tv_promoCode);
        mainActivity = new MainActivity();


        if (promoCode != -1) {
            tv_promoCode.setText("Your Promo Code\n" + promoCode + "\nUse This When Order a Product");
            tv_promoCode.setVisibility(View.GONE);
        }


        databaseHelper = new DatabaseHelper(ListsActivity.this);
        getData = getIntent();
        loadList = getData.getIntExtra("loadLists", -1);
        product_category_id = getData.getIntExtra("cat_id", 1);
        databaseHelper.connectToDB();

        switch (loadList) {

            case 1:
                getSupportLoaderManager().initLoader(0, null, this);
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
                getSupportLoaderManager().initLoader(0, null, this);

        }


    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @NonNull
    @Override
    public Loader<List<Product>> onCreateLoader(int id, @Nullable Bundle args) {
        return new ProductsLoader(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        RecyclerViewAdapter_loader.notifyDataSetChanged();

    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Product>> loader, List<Product> data) {

        List<Product> products = new ArrayList<Product>();


        if (RecyclerViewAdapter_loader == null) {


            for (int i = 0; i < data.size(); i++) {

                if (data.get(i).getCategory_id() == getData.getIntExtra("cat_id", 0)) {
                    Log.d(TAG, "onLoadFinished: add " + data.get(i).getName());
                    products.add(data.get(i));

                } else {
                    Log.d(TAG, "onLoadFinished: else");
                }

            }


            RecyclerViewAdapter_loader = new ProductAdapter(this, products);
            recyclerView.setLayoutManager(new LinearLayoutManager(ListsActivity.this));
            recyclerView.setAdapter(RecyclerViewAdapter_loader);
            RecyclerViewAdapter_loader.setData(products);
            RecyclerViewAdapter_loader.setOnItemClickListener(new CustomItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(ListsActivity.this, ProductDetailsActivity.class);
                    intent.putExtra("prid", products.get(position).getId());
                    Log.d(TAG, "onItemClick: " + products.get(position).getId());
                    startActivity(intent);

//
                }
            });
        }


    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Product>> loader) {

    }

    public static void loadProductsWithCategoriesID(List<Product> data, int product_category_id) {

    }

}
