package com.yasser.roknaapp.localDatabase;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.yasser.roknaapp.Model.Category;
import com.yasser.roknaapp.Model.Product;

import java.util.ArrayList;
import java.util.List;


public class DatabaseLocal extends SQLiteOpenHelper {
    public Context context;
    private static DatabaseLocal Instance;
    public static final String DB_NAME = DatabaseInfo.DB_NAME;
    public static final int DB_VERSION = DatabaseInfo.DB_VERSION;

    public static final String PRODUCTS = DatabaseInfo.PRODUCTS;
    public static final String CATEGORIES = DatabaseInfo.CATEGORIES;


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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORIES);

        // Create tables again
        onCreate(db);
    }

    public Boolean insert_products(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("parse_server_id", product.getId());
        contentValues.put("product_name", product.getName());
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

    public List<Product> select_all_products() {
        List<Product> list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + PRODUCTS, null);

        if (cursor != null) {
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {
                int id = cursor.getInt(0);
                String parse_server_id = cursor.getString(1);
                String product_name = cursor.getString(2);
                String product_description = cursor.getString(3);
                String product_price = cursor.getString(4);
                String product_img1_url = cursor.getString(5);
                String product_img1_ur2 = cursor.getString(6);
                String product_img1_ur3 = cursor.getString(7);
                String product_img1_ur4 = cursor.getString(8);
                int category_id = cursor.getInt(9);

                Product product = new Product(parse_server_id,product_name,category_id,product_description,product_price,"0",product_img1_url,product_img1_ur2,product_img1_ur3,product_img1_ur4);
                list.add(product);
                cursor.moveToNext();
            }

            cursor.close();
        }

        return list;
    }
    public List<Category> select_all_categories() {
        List<Category> list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + CATEGORIES, null);

        if (cursor != null) {
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {
                int id = cursor.getInt(0);
                int category_num_id = cursor.getInt(1);
                String category_title = cursor.getString(2);


                Category category = new Category(category_num_id,category_title);
                list.add(category);
                cursor.moveToNext();
            }

            cursor.close();
        }

        return list;
    }

}
