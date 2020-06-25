package com.yasser.roknaapp.Loader;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.yasser.roknaapp.Model.AdBanner;
import com.yasser.roknaapp.Model.Category;
import com.yasser.roknaapp.localDatabase.DatabaseLocal;

import java.util.ArrayList;
import java.util.List;

public class Ads_BannerLoader extends AbstractQueryLoader<List<AdBanner>> {

    public Context _context;

    public Ads_BannerLoader(Context context) {
        super(context);
        this._context = context;
    }

    @Override
    public List<AdBanner> loadInBackground() {

        DatabaseLocal mydb =  DatabaseLocal.getInstance(getContext());
        SQLiteDatabase db = mydb.getReadableDatabase();
        List<AdBanner> results = null;
        Cursor GroupCursor = null;

        try {

            GroupCursor = db.rawQuery("SELECT * FROM ads_banner ORDER BY id DESC ", null);

            if (GroupCursor != null && GroupCursor.moveToFirst()) {
                results = new ArrayList<>();
                do {
                    AdBanner list = new AdBanner();
                    list.setAd_url(GroupCursor.getString(1));
                    list.setAd_img(GroupCursor.getString(2));
                    list.setAdd_snppit(GroupCursor.getString(3));
                    Log.d("adsLoader", "loadInBackground: --> " + list.getAdd_snppit());
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
