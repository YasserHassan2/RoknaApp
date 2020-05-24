package com.yasser.roknaapp.localDatabase;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.yasser.roknaapp.Model.AdBanner;
import com.yasser.roknaapp.Model.Category;
import com.yasser.roknaapp.Model.Event;
import com.yasser.roknaapp.Model.Product;
import com.yasser.roknaapp.Model.Workshop;

import java.util.ArrayList;
import java.util.List;


public class DatabaseLocal extends SQLiteOpenHelper {
    public Context context;
    private static DatabaseLocal Instance;
    public static final String DB_NAME = DatabaseInfo.DB_NAME;
    public static final int DB_VERSION = DatabaseInfo.DB_VERSION;

    public static final String PRODUCTS = DatabaseInfo.PRODUCTS;
    public static final String CATEGORIES = DatabaseInfo.CATEGORIES;
    public static final String ADS_BANNER = DatabaseInfo.ADS_BANNER;
    public static final String WORKSHOPS = DatabaseInfo.WORKSHOPS;
    public static final String EVENTS = DatabaseInfo.EVENTS;


    public static DatabaseLocal getInstance(Context context) {
        if (Instance == null)
            Instance = new DatabaseLocal(context.getApplicationContext(), DB_NAME);

        return Instance;
    }

    public DatabaseLocal(Context context, String dbName) {
        super(context, dbName, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        Log.e("DataBase", "DataBase.onCreate()");


        sqLiteDatabase.execSQL("create table  if not exists " + PRODUCTS +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "parse_server_id TEXT, " +
                "product_name TEXT, " +
                "product_sale TEXT,"+
                "product_description TEXT, " +
                "product_price TEXT, " +
                "product_img1_url TEXT, " +
                "product_img2_url TEXT, " +
                "product_img3_url TEXT, " +
                "product_img4_url TEXT, " +
                "category_id INTEGER)"
        );

        sqLiteDatabase.execSQL("create table  if not exists " + CATEGORIES +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "category_num_id INTEGER, " +
                "category_title TEXT)"
        );
        sqLiteDatabase.execSQL("create table  if not exists " + ADS_BANNER +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ad_url TEXT, " +
                "ad_img TEXT, " +
                "add_snppit TEXT)"
        );
        sqLiteDatabase.execSQL("create table  if not exists " + WORKSHOPS +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "imageURL TEXT, " +
                "title TEXT, " +
                "description TEXT, " +
                "price TEXT, " +
                "phone TEXT, " +
                "location_long TEXT, " +
                "location_lat TEXT)"
        );
        sqLiteDatabase.execSQL("create table  if not exists " + EVENTS +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "eventTitle TEXT, " +
                "eventDates TEXT, " +
                "eventImageURL TEXT, " +
                "eventLocation_long TEXT, " +
                "eventLocation_lat TEXT)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + ADS_BANNER);
        db.execSQL("DROP TABLE IF EXISTS " + WORKSHOPS);
        db.execSQL("DROP TABLE IF EXISTS " + EVENTS);

        // Create tables again
        onCreate(db);
    }


    public void delete() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + PRODUCTS);
        db.execSQL("delete from " + CATEGORIES);
        db.execSQL("delete from " + ADS_BANNER);
        db.execSQL("delete from " + WORKSHOPS);
        db.execSQL("delete from " + EVENTS);
        db.close();
    }

    public Boolean insert_products(Context context, Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("parse_server_id", product.getId());
        contentValues.put("product_name", product.getName());
        contentValues.put("product_sale", product.getSale());
        contentValues.put("product_description", product.getDescription());
        contentValues.put("product_price", product.getPrice());
        contentValues.put("product_img1_url", product.getImgURL1());
        contentValues.put("product_img2_url", product.getImgURL2());
        contentValues.put("product_img3_url", product.getImgURL3());
        contentValues.put("product_img4_url", product.getImgURL4());
        contentValues.put("category_id", product.getCategory_id());

        long insert = db.insert(PRODUCTS, null, contentValues);
        if (insert == -1) {
            Log.e("database", "insert >> false");
            long update = db.update(PRODUCTS, contentValues, "id=?", new String[]{String.valueOf(product.getId())});
            if (update == -1) {
                Log.e("database", "update >> false");
                return false;
            } else {
                Log.e("database", "update >> true");
                return true;
            }
        } else {
            Log.e("database", "insert >> true");
            return true;
        }
    }

    public Boolean insert_categories(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("category_num_id", category.getId());
        contentValues.put("category_title", category.getTitle());


        long insert = db.insert(CATEGORIES, null, contentValues);
        if (insert == -1) {
            Log.e("database", "insert >> false");
            long update = db.update(CATEGORIES, contentValues, "id=?", new String[]{String.valueOf(category.getId())});
            if (update == -1) {
                Log.e("database", "update >> false");
                return false;
            } else {
                Log.e("database", "update >> true");
                return true;
            }
        } else {
            Log.e("database", "insert >> true");
            return true;
        }
    }
    public Boolean insert_ads(Context context, AdBanner adBanner) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("ad_url", adBanner.getAd_url());
        contentValues.put("ad_img", adBanner.getAd_img());
        contentValues.put("add_snppit", adBanner.getAdd_snppit());

        long insert = db.insert(ADS_BANNER, null, contentValues);
        if (insert == -1) {
            Log.e("database", "insert >> false");
            long update = db.update(ADS_BANNER, contentValues, "id=?", new String[]{String.valueOf(adBanner.getAdd_snppit())});
            if (update == -1) {
                Log.e("database", "update >> false");
                return false;
            } else {
                Log.e("database", "update >> true");
                return true;
            }
        } else {
            Log.e("database", "insert >> true");
            return true;
        }
    }
    public Boolean insert_workshops(Workshop workshop) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("imageURL", workshop.getImageURL());
        contentValues.put("title", workshop.getTitle());
        contentValues.put("description", workshop.getDescription());
        contentValues.put("price", workshop.getPrice());
        contentValues.put("phone", workshop.getPhone());
        contentValues.put("location_long", workshop.getLocation().getLongitude());
        contentValues.put("location_lat", workshop.getLocation().getLatitude());


        long insert = db.insert(WORKSHOPS, null, contentValues);
        if (insert == -1) {
            Log.e("database", "insert >> false");
            long update = db.update(WORKSHOPS, contentValues, "id=?", new String[]{String.valueOf(workshop.getTitle())});
            if (update == -1) {
                Log.e("database", "update >> false");
                return false;
            } else {
                Log.e("database", "update >> true");
                return true;
            }
        } else {
            Log.e("database", "insert >> true");
            return true;
        }
    }
    public Boolean insert_events(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("eventTitle", event.getEventTitle());
        contentValues.put("eventDates", event.getEventDates());
        contentValues.put("eventImageURL", event.getEventImageURL());
        contentValues.put("eventLocation_long", event.getEventLocation().getLongitude());
        contentValues.put("eventLocation_lat", event.getEventLocation().getLatitude());

        long insert = db.insert(EVENTS, null, contentValues);
        if (insert == -1) {
            Log.e("database", "insert >> false");
            long update = db.update(EVENTS, contentValues, "id=?", new String[]{String.valueOf(event.getEventTitle())});
            if (update == -1) {
                Log.e("database", "update >> false");
                return false;
            } else {
                Log.e("database", "update >> true");
                return true;
            }
        } else {
            Log.e("database", "insert >> true");
            return true;
        }
    }



}
