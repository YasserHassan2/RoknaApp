package com.yasser.roknaapp.Model;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.roger.catloadinglibrary.CatLoadingView;
import com.yasser.roknaapp.Adapter.CustomItemClickListener;
import com.yasser.roknaapp.Adapter.EventsAdapter;
import com.yasser.roknaapp.Adapter.WorkshopAdapter;
import com.yasser.roknaapp.Dialogs;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DatabaseHelper {

    Context mContext;
    CatLoadingView  mView;
    Dialogs dialogs;

    public static Boolean LOAD_FROM_LOCAL=false;


    ArrayList<Event> eventList = new ArrayList<Event>();
    ArrayList<Workshop> workshopsList = new ArrayList<Workshop>();

    public DatabaseHelper(Context mContext) {
        this.mContext = mContext;
        dialogs = new Dialogs(mContext);
    }

    public void connectToDB() {

        //# database connection
        Parse.initialize(new Parse.Configuration.Builder(mContext)
                .applicationId("tbG3gg7UGswBUAi2DYtFm4yj0x9PBMbeUQAmrMvC")
                .clientKey("ZrkL2lgfxaBEE0WL3b53VamVICRHQWqxInA9iUBj")
                .server("https://parseapi.back4app.com")
                .enableLocalDataStore()
                .build()
        );


        //enable localdata store

//        Parse.enableLocalDatastore(mContext);
//        Parse.initialize(mContext);


    }



    public void loadWorkshops(final RecyclerView recyclerView) {
        dialogs.showProgressDialog("Loading","Getting Workshops data..");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("workshops");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, final ParseException e) {
                if (e == null) {
                    for (ParseObject o : objects) {

                        String workshop_id = o.getObjectId();
                        String workshopName = o.getString("title");
                        String workshopDesc = o.getString("description");
                        String workshopPrice = o.getString("price");
                        String phonNumber = o.getString("phone_number");
                        ParseGeoPoint location = o.getParseGeoPoint("location");
                        String avaliable = String.valueOf(o.getBoolean("avaliable"));

                        ParseFile imageFile = o.getParseFile("image");
                        String imageURL = imageFile.getUrl();


                        Workshop workshop = new Workshop(imageURL, workshopName, workshopDesc, workshopPrice, phonNumber, location,avaliable);
                        workshopsList.add(workshop);

                    }

                    dialogs.stopDialog();

                    recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

                    WorkshopAdapter RecyclerViewAdapter = new WorkshopAdapter(mContext, workshopsList);

                    recyclerView.setAdapter(RecyclerViewAdapter);

                    if (workshopsList.isEmpty()) {
                        dialogs.showAlertDialogToMain("No Workshop Avaliable at moment, stay tuned!");

                    }

                    RecyclerViewAdapter.setOnItemClickListener(new CustomItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {


                        }
                    });

                } else {
                    Toast.makeText(mContext, "error= " + e, Toast.LENGTH_LONG).show();
                    dialogs.stopDialog();
                }
            }
        });

    }


    public void loadEvents(final RecyclerView recyclerView) {
        dialogs.showProgressDialog("Loading","Getting Events data..");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("events");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, final ParseException e) {
                if (e == null) {
                    for (ParseObject o : objects) {

                        String event_id = o.getObjectId();
                        String eventTitle = o.getString("title");
                        String eventDates = o.getString("dates");
                        ParseGeoPoint location = o.getParseGeoPoint("location");
                        String avaliable = String.valueOf(o.getBoolean("avaliable"));

                        ParseFile imageFile = o.getParseFile("image");
                        String imageURL = imageFile.getUrl();


                        Event event = new Event(eventTitle,eventDates,imageURL,location,avaliable);
                        eventList.add(event);

                    }
                    dialogs.stopDialog();
                    recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                    if (eventList.isEmpty()) {
                        dialogs.showAlertDialogToMain("No Events Avaliable at moment, stay tuned!");

                    }
                    EventsAdapter RecyclerViewAdapter = new EventsAdapter(mContext, eventList);

                    recyclerView.setAdapter(RecyclerViewAdapter);

                    RecyclerViewAdapter.setOnItemClickListener(new CustomItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {


                        }
                    });

                } else {
                    Toast.makeText(mContext, "error= " + e, Toast.LENGTH_LONG).show();
                    dialogs.stopDialog();
                }
            }
        });

    }

    public void showFeedbackDialog(String title)
    {

    }

    public void loadProuctsFromLocal()
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Products");
        query.fromLocalDatastore();
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                
            }
        });
    }

    public void pinProductsinBackground(List<Product> productArrayList)
    {
        Log.d(TAG, "pinProductsinBackground: Start Pining Prodcuts");

        ParseObject products = new ParseObject("products");



//        for (int i =0 ; i <=productArrayList.size()-1;i++)
//        {
//            ParseObject parseObject = new ParseObject("Products");
//            parseObject.put("id",productArrayList.get(i).getId());
//            parseObject.put("name",productArrayList.get(i).getName());
//            parseObject.put("category_id",productArrayList.get(i).getCategory_id());
//            parseObject.put("description",productArrayList.get(i).getDescription());
//            parseObject.put("price",productArrayList.get(i).getPrice());
//            if (productArrayList.get(i).getSale()!=null)
//            parseObject.put("sale",productArrayList.get(i).getSale());
//            parseObject.put("url1",productArrayList.get(i).getImgURL1());
//            parseObject.put("url2",productArrayList.get(i).getImgURL2());
//            parseObject.put("url3",productArrayList.get(i).getImgURL3());
//            parseObject.put("url4",productArrayList.get(i).getImgURL4());
//
//            Log.d(TAG, "pinProductsinBackground: Done with Product Name " + productArrayList.get(i).getId() + " Position : " + i);
//            parseObject.pinInBackground();
//        }
        LOAD_FROM_LOCAL =true;

    }


}