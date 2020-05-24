package com.yasser.roknaapp.Loader;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yasser.roknaapp.Model.Category;
import com.yasser.roknaapp.Model.Product;
import com.yasser.roknaapp.localDatabase.DatabaseLocal;

import java.util.ArrayList;
import java.util.List;

public class CategoriesLoader extends AbstractQueryLoader<List<Category>> {

    public Context _context;

    public CategoriesLoader(Context context) {
        super(context);
        this._context = context;
    }

    @Override
    public List<Category> loadInBackground() {

        DatabaseLocal mydb =  DatabaseLocal.getInstance(getContext());
        SQLiteDatabase db = mydb.getReadableDatabase();
        List<Category> results = null;
        Cursor GroupCursor = null;

        try {

            GroupCursor = db.rawQuery("SELECT * FROM categories ", null);

            if (GroupCursor != null && GroupCursor.moveToFirst()) {
                results = new ArrayList<>();
                do {
                    Category list = new Category();
                    list.setId(GroupCursor.getInt(1));
                    list.setTitle(GroupCursor.getString(2));
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
