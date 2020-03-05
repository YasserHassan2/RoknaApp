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
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
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
    RecyclerView recyclerView;
    DatabaseHelper databaseHelper;
    TextView tv_PageTitle;
    ArrayList<Product> productList = new ArrayList<Product>();
    int loadList;
    Intent getData;
    CatLoadingView mView;
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
            tv_promoCode.setVisibility(View.VISIBLE);
        }




        databaseHelper = new DatabaseHelper(ListsActivity.this);
        getData = getIntent();
        loadList=getData.getIntExtra("loadLists",-1);
        databaseHelper.connectToDB();

        switch (loadList){

            case 1:
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


                        Product product = new Product(prName, prDesc, prPrice, prSale, imageURL);
                        productList.add(product);

                    }
                    mView.dismiss();
                    recyclerView.setLayoutManager(new LinearLayoutManager(ListsActivity.this));

                    ProductAdapter RecyclerViewAdapter = new ProductAdapter(ListsActivity.this, productList);

                    recyclerView.setAdapter(RecyclerViewAdapter);
                    if (productList.isEmpty()) {
                        dialog.showAlertDialogToMain("No Products Avaliable at moment, stay tuned!");

                    }
                    RecyclerViewAdapter.setOnItemClickListener(new CustomItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            new FancyGifDialog.Builder(ListsActivity.this)
                                    .setTitle("Contact To Order")
                                    .setMessage("Want '"+productList.get(position).getName()+"' , please Contact to deliver")
                                    .setNegativeBtnText("Cancel")
                                    .setPositiveBtnBackground(colorStr)
                                    .setPositiveBtnText("Contact")
                                    .setNegativeBtnBackground("#FFA9A7A8")
                                    .setGifResource(R.drawable.roknalogo)   //Pass your Gif here
                                    .isCancellable(true)
                                    .OnPositiveClicked(new FancyGifDialogListener() {
                                        @Override
                                        public void OnClick() {

                                            String url = "https://api.whatsapp.com/send?phone=" + contact;
                                            try {
                                                PackageManager pm = getApplicationContext().getPackageManager();
                                                pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                                                Intent i = new Intent(Intent.ACTION_VIEW);
                                                i.setData(Uri.parse(url));
                                                startActivity(i);
                                            } catch (PackageManager.NameNotFoundException e) {
                                                Toast.makeText(ListsActivity.this, "Whatsapp app not installed in your phone", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    })
                                    .OnNegativeClicked(new FancyGifDialogListener() {
                                        @Override
                                        public void OnClick() {

                                        }
                                    })
                                    .build();
                        }
                    });

                } else {
                    mView.dismiss();
                    Toast.makeText(ListsActivity.this, "error= " + e, Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}
