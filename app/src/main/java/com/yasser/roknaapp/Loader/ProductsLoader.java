package com.yasser.roknaapp.Loader;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yasser.roknaapp.Model.Product;
import com.yasser.roknaapp.localDatabase.DatabaseLocal;

import java.util.ArrayList;
import java.util.List;

public class ProductsLoader  extends AbstractQueryLoader<List<Product>> {

    public Context _context;

    public ProductsLoader(Context context) {
        super(context);
        this._context = context;
    }

    @Override
    public List<Product> loadInBackground() {

        DatabaseLocal mydb =  DatabaseLocal.getInstance(getContext());
        SQLiteDatabase db = mydb.getReadableDatabase();
        List<Product> results = null;
        Cursor GroupCursor = null;

        try {

            GroupCursor = db.rawQuery("SELECT * FROM products ORDER BY id DESC", null);

            if (GroupCursor != null && GroupCursor.moveToFirst()) {
                results = new ArrayList<>();
                do {
                    Product list = new Product();
                    list.setId(GroupCursor.getString(1));
                    list.setName(GroupCursor.getString(2));
                    list.setSale(GroupCursor.getString(3));
                    list.setDescription(GroupCursor.getString(4));
                    list.setPrice(GroupCursor.getString(5));
                    list.setImgURL1(GroupCursor.getString(6));
                    list.setImgURL2(GroupCursor.getString(7));
                    list.setImgURL3(GroupCursor.getString(8));
                    list.setImgURL4(GroupCursor.getString(9));
                    list.setAvaliable(GroupCursor.getString(10));
                    list.setCategory_id(Integer.parseInt(GroupCursor.getString(11)));
                    results.add(list);

                } while (GroupCursor.moveToNext());
            }
        } finally {
            if (GroupCursor != null) {
                GroupCursor.close();
            }
        }

        return results;
    }
}