package com.yasser.roknaapp.Loader;

import android.content.Context;

import androidx.loader.content.AsyncTaskLoader;

import com.yasser.roknaapp.localDatabase.DatabaseLocal;

public abstract class AbstractQueryLoader<T> extends AsyncTaskLoader<T> {
    protected T mData;
    protected final DatabaseLocal mDbHelper;

    public AbstractQueryLoader(Context context) {
        super(context);
        mDbHelper = DatabaseLocal.getInstance(context);
    }

    @Override
    public void deliverResult(T data) {
        if (isReset()) {
            return;
        }

        mData = data;
        if (isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    protected void onStartLoading() {
        if (mData != null) {
            deliverResult(mData);
        }

        if (takeContentChanged() || mData == null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        mData = null;
    }
}
