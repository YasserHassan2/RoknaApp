package com.yasser.roknaapp.Loader;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.parse.ParseGeoPoint;
import com.yasser.roknaapp.Model.AdBanner;
import com.yasser.roknaapp.Model.Event;
import com.yasser.roknaapp.localDatabase.DatabaseLocal;

import java.util.ArrayList;
import java.util.List;

public class EventsLoader  extends AbstractQueryLoader<List<Event>> {

    public Context _context;

    public EventsLoader(Context context) {
        super(context);
        this._context = context;
    }

    @Override
    public List<Event> loadInBackground() {

        DatabaseLocal mydb =  DatabaseLocal.getInstance(getContext());
        SQLiteDatabase db = mydb.getReadableDatabase();
        List<Event> results = null;
        Cursor GroupCursor = null;

        try {

            GroupCursor = db.rawQuery("SELECT * FROM events ORDER BY id DESC", null);

            if (GroupCursor != null && GroupCursor.moveToFirst()) {
                results = new ArrayList<>();
                do {
                    Event list = new Event();
                    list.setEventTitle(GroupCursor.getString(1));
                    list.setEventDates(GroupCursor.getString(2));
                    list.setEventImageURL(GroupCursor.getString(3));
                    list.setAvaliable(GroupCursor.getString(5));
                    list.setEventLocation(new ParseGeoPoint(Double.parseDouble(GroupCursor.getString(4)),Double.parseDouble(GroupCursor.getString(6))));
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
