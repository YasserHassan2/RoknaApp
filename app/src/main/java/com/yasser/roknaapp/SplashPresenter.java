package com.yasser.roknaapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.denzcoskun.imageslider.models.SlideModel;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.yasser.roknaapp.Adapter.CategoriesAdapter;
import com.yasser.roknaapp.Adapter.CustomItemClickListener;
import com.yasser.roknaapp.Adapter.EventsAdapter;
import com.yasser.roknaapp.Adapter.WorkshopAdapter;
import com.yasser.roknaapp.Model.AdBanner;
import com.yasser.roknaapp.Model.Category;
import com.yasser.roknaapp.Model.DatabaseVersion;
import com.yasser.roknaapp.Model.Event;
import com.yasser.roknaapp.Model.Product;
import com.yasser.roknaapp.Model.Workshop;
import com.yasser.roknaapp.localDatabase.Constants;
import com.yasser.roknaapp.localDatabase.DatabaseInterface;
import com.yasser.roknaapp.localDatabase.DatabaseLocal;
import com.yasser.roknaapp.localDatabase.DatabaseManager;
import com.yasser.roknaapp.ui.main.ListsActivity;
import com.yasser.roknaapp.ui.main.MainActivity;
import com.yasser.roknaapp.ui.main.ProductCategoryActivity;

import java.util.ArrayList;
import java.util.List;

public class SplashPresenter extends AppCompatActivity {

    private static final String TAG = "Splash_Presenter";
    static Context context;
    static DatabaseManager databaseManager;
    static DatabaseLocal mydb;
    static ProgressDialog Dialog;



    public SplashPresenter(Context context) {
        this.context = context;
        databaseManager = new DatabaseManager(context);
    }

    public static Boolean checkUpdate(int last_databaseVersion, DatabaseInterface databaseInterface) {


        String local_databaseVersion = String.valueOf(databaseManager.getDatabaseVersion().getDb_version());
        Log.d(TAG, "checkUpdate:  local databse version " + local_databaseVersion + " last database " + last_databaseVersion);


        if ((local_databaseVersion != null) && (Integer.parseInt(local_databaseVersion) >= -1)) {

            if (Integer.parseInt(local_databaseVersion) != last_databaseVersion) {
                Log.d(TAG, "checkUpdate:  local databse version " + local_databaseVersion + " last database " + last_databaseVersion);


                databaseManager.updateDatabaseVersion(new DatabaseVersion(last_databaseVersion));
                db_request(context, databaseInterface);


                return true;
            } else {
                Log.d(TAG, "checkUpdate: false");
                return false;
            }
        } else {
            databaseManager.updateDatabaseVersion(new DatabaseVersion(last_databaseVersion));
            db_request(context, databaseInterface);
            return true;
        }


    }


    public static void db_request(Context context, final @Nullable DatabaseInterface callbacks) {
        Log.d(TAG, "database is loading");


        Dialog = new ProgressDialog(context);

        Dialog.setMessage("Loading Components");
        Dialog.show();
        callbacks.onStart();

        mydb = DatabaseLocal.getInstance(context);
        ArrayList<Product> productList = new ArrayList<>();
        ArrayList<Category> categoriesList = new ArrayList<>();
        ArrayList<Event> eventList = new ArrayList<Event>();
        ArrayList<Workshop> workshopsList = new ArrayList<Workshop>();
        ArrayList<AdBanner> adBanners = new ArrayList<AdBanner>();




        try {
            mydb.delete();
            final ParseQuery<ParseObject> query = ParseQuery.getQuery("products");
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, final ParseException e) {
                    if (e == null) {
                        for (ParseObject o : objects) {

                            Log.d(TAG, "done: loading --- > " + o.getString("name"));

                            String pr_id = o.getObjectId();
                            String prName = o.getString("name");
                            String prDesc = o.getString("description");
                            String prPrice = o.getString("price");
                            String prSale = o.getString("sale");
                            int category_id = o.getInt("category_id");

                            ParseFile imageFile1 = o.getParseFile("image");
                            String imageURL1 = imageFile1.getUrl();
                            ParseFile imageFile2 = o.getParseFile("imageURL1");
                            String imageURL2 = imageFile2.getUrl();
                            ParseFile imageFile3 = o.getParseFile("imageURL2");
                            String imageURL3 = imageFile3.getUrl();
                            ParseFile imageFile4 = o.getParseFile("imageURL3");
                            String imageURL4 = imageFile4.getUrl();


                            Product product = new Product(pr_id, prName, category_id, prDesc, prPrice, prSale, imageURL1, imageURL2, imageURL3, imageURL4);
                            productList.add(product);

                        }
                        for (int i = 0; i < productList.size(); i++) {
                            Log.d(TAG, "run: " + productList.get(i).getName());
                            mydb.insert_products(context, new Product(
                                    productList.get(i).getId(),
                                    productList.get(i).getName(),
                                    productList.get(i).getCategory_id(),
                                    productList.get(i).getDescription(),
                                    productList.get(i).getPrice(),
                                    productList.get(i).getSale(),
                                    productList.get(i).getImgURL1(),
                                    productList.get(i).getImgURL2(),
                                    productList.get(i).getImgURL3(),
                                    productList.get(i).getImgURL4()));


                        }

                    } else {
                        Toast.makeText(context, "Please Try Again later.", Toast.LENGTH_SHORT).show();
                    }
                }
            });


            final ParseQuery<ParseObject> query1 = ParseQuery.getQuery("category");
            query1.findInBackground(new FindCallback<ParseObject>() {
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
                        for (int i = 0; i < categoriesList.size(); i++) {
                            Log.d(TAG, "run: " + categoriesList.get(i).getTitle());
                            mydb.insert_categories(new Category(
                                    categoriesList.get(i).getId(),
                                    categoriesList.get(i).getTitle()
                            ));


                        }

                    } else {
                        Toast.makeText(context, "Please Try Again later." + e, Toast.LENGTH_LONG).show();
                    }
                }
            });

            ParseQuery<ParseObject> query_ad_banner = ParseQuery.getQuery("Ads_Banners");
            query_ad_banner.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, final ParseException e) {
                    if (e == null) {
                        for (ParseObject o : objects) {

                            String adUrl = o.getString("ad_url");
                            ParseFile adImage = o.getParseFile("ad_img");

                            String imgUrl = adImage.getUrl();

                            String ad_snppiet = o.getString("ad_snippet");


                            adBanners.add(new AdBanner(adUrl, imgUrl, ad_snppiet));
                        }

                        for (int i = 0; i < adBanners.size(); i++) {
                            Log.d(TAG, "run: inserting ads data");
                            Log.d(TAG, "run: " + adBanners.get(i).getAdd_snppit());
                            mydb.insert_ads(context, new AdBanner(adBanners.get(i).getAd_url(), adBanners.get(i).getAd_img()
                                    , adBanners.get(i).getAdd_snppit()));


                        }

                    } else {
                    }
                }
            });

            ParseQuery<ParseObject> query_workshops = ParseQuery.getQuery("workshops");

            query_workshops.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, final ParseException e) {
                    if (e == null) {
                        for (ParseObject o : objects) {

                            String workshop_id = o.getObjectId();
                            String workshopName = o.getString("title");
                            String workshopDesc = o.getString("description");
                            String workshopPrice = o.getString("price");
                            String phonNumber = o.getString("phone_number");
                            ParseGeoPoint location = o.getParseGeoPoint("location");

                            ParseFile imageFile = o.getParseFile("image");
                            String imageURL = imageFile.getUrl();


                            Workshop workshop = new Workshop(imageURL, workshopName, workshopDesc, workshopPrice, phonNumber, location);
                            workshopsList.add(workshop);

                        }

                        for (int i = 0; i < workshopsList.size(); i++) {
                            Log.d(TAG, "run: inserting workshops data");
                            Log.d(TAG, "run: " + workshopsList.get(i).getTitle());
                            mydb.insert_workshops(new Workshop(
                                    workshopsList.get(i).getImageURL(),
                                    workshopsList.get(i).getTitle(),
                                    workshopsList.get(i).getDescription(),
                                    workshopsList.get(i).getPrice(),
                                    workshopsList.get(i).getPhone(),
                                    workshopsList.get(i).getLocation()
                            ));


                        }

                    } else {

                    }
                }
            });

            ParseQuery<ParseObject> quer_events = ParseQuery.getQuery("events");

            quer_events.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, final ParseException e) {
                    if (e == null) {
                        for (ParseObject o : objects) {

                            String event_id = o.getObjectId();
                            String eventTitle = o.getString("title");
                            String eventDates = o.getString("dates");
                            ParseGeoPoint location = o.getParseGeoPoint("location");

                            ParseFile imageFile = o.getParseFile("image");
                            String imageURL = imageFile.getUrl();


                            Event event = new Event(eventTitle, eventDates, imageURL, location);
                            eventList.add(event);

                        }


                        for (int i = 0; i < eventList.size(); i++) {
                            Log.d(TAG, "run: inserting events data");
                            Log.d(TAG, "run: " + eventList.get(i).getEventTitle());
                            mydb.insert_events(new Event(
                                    eventList.get(i).getEventTitle(),
                                    eventList.get(i).getEventDates(),
                                    eventList.get(i).getEventImageURL(),
                                    eventList.get(i).getEventLocation()));


                        }


                    } else {
                    }
                }
            });




            if (callbacks != null) {

                callbacks.onSuccess(true);



            }
        } catch (final Exception ex) {

            Log.d(TAG, "run: ex " + ex.getLocalizedMessage());
        }

        new Thread() {
            @Override
            public void run() {
                Looper.prepare();//Call looper.prepare()

            }
        }.start();


    }

    public static void pause(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            System.err.format("IOException: %s%n", e);
        }
    }
}

