package com.yasser.roknaapp.ui.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.yasser.roknaapp.Adapter.CustomItemClickListener;
import com.yasser.roknaapp.Adapter.EventsAdapter;
import com.yasser.roknaapp.Adapter.WorkshopAdapter;
import com.yasser.roknaapp.Loader.EventsLoader;
import com.yasser.roknaapp.Loader.WorkshopLoader;
import com.yasser.roknaapp.Model.DatabaseHelper;
import com.yasser.roknaapp.Model.Event;
import com.yasser.roknaapp.R;

import java.util.List;

public class EventsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Event>> {

    RecyclerView recyclerView;
    DatabaseHelper databaseHelper;
    EventsAdapter eventsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        recyclerView = findViewById(R.id.Pr_recyclerView);

        getSupportLoaderManager().initLoader(0, null, this);


    }

    @NonNull
    @Override
    public Loader<List<Event>> onCreateLoader(int id, @Nullable Bundle args) {
        return new EventsLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Event>> loader, List<Event> data) {
        Log.d("tag", "onLoadFinished: dddd" +  data.get(0).getEventTitle());


        if (eventsAdapter == null) {

            eventsAdapter = new EventsAdapter(EventsActivity.this, data);
            recyclerView.setLayoutManager(new LinearLayoutManager(EventsActivity.this));
            recyclerView.setAdapter(eventsAdapter);
            eventsAdapter.setOnItemClickListener(new CustomItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                }
            });

        } else {

            eventsAdapter.setData(data);
            recyclerView.setLayoutManager(new LinearLayoutManager(EventsActivity.this));
            recyclerView.setAdapter(eventsAdapter);
            eventsAdapter.setOnItemClickListener(new CustomItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                }
            });

        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Event>> loader) {

    }
}
