package com.yasser.roknaapp.localDatabase;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.yasser.roknaapp.Model.DatabaseVersion;

public class DatabaseManager {

    private int MODE_PRIVATE = 0;
    private String PREF_NAME = "DatabaseManger";
    private Context context;
    private SharedPreferences.Editor editor;
    private SharedPreferences pref;


    public DatabaseManager(Context context) {
        this.context = context;
        this.pref = context.getSharedPreferences(this.PREF_NAME, this.MODE_PRIVATE);
        this.editor = context.getSharedPreferences(this.PREF_NAME, this.MODE_PRIVATE).edit();
    }

    public DatabaseVersion getDatabaseVersion() {

        int databaseVersion = pref.getInt(Constants.DATABASE_VERSION, -1);
        if (databaseVersion == 0) {
            return null;
        }
        return new DatabaseVersion(databaseVersion);
    }

    public void updateDatabaseVersion(DatabaseVersion databaseVersion){
        clearDatabaseVersion();
        createDatabaseVersion(databaseVersion);
    }
    public void createDatabaseVersion(DatabaseVersion databaseVersion) {
        editor.putInt(Constants.DATABASE_VERSION, databaseVersion.getDb_version()).apply();
        editor.commit();
    }

    public void clearDatabaseVersion() {
        pref.edit().clear().apply();
    }



}
