package com.yasser.roknaapp.Loader;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.parse.ParseGeoPoint;
import com.yasser.roknaapp.Model.Event;
import com.yasser.roknaapp.Model.Workshop;
import com.yasser.roknaapp.localDatabase.DatabaseLocal;

import java.util.ArrayList;
import java.util.List;

public class WorkshopLoader extends AbstractQueryLoader<List<Workshop>> {

    public Context _context;

    public WorkshopLoader(Context context) {
        super(context);
        this._context = context;
    }

    @Override
    public List<Workshop> loadInBackground() {

        DatabaseLocal mydb =  DatabaseLocal.getInstance(getContext());
        SQLiteDatabase db = mydb.getReadableDatabase();
        List<Workshop> results = null;
        Cursor GroupCursor = null;

        try {

            GroupCursor = db.rawQuery("SELECT * FROM workshops ", null);

            if (GroupCursor != null && GroupCursor.moveToFirst()) {
                results = new ArrayList<>();
                do {
                    Workshop list = new Workshop();
                    list.setImageURL(GroupCursor.getString(1));
                    list.setTitle(GroupCursor.getString(2));
                    list.setDescription(GroupCursor.getString(3));
                    list.setPrice(GroupCursor.getString(4));
                    list.setPhone(GroupCursor.getString(5));
                    list.setLocation(new ParseGeoPoint(Double.parseDouble(GroupCursor.getString(7)),Double.parseDouble(GroupCursor.getString(6))));
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
