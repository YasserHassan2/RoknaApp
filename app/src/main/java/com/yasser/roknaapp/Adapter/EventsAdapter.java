package com.yasser.roknaapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yasser.roknaapp.Model.Event;
import com.yasser.roknaapp.Model.Workshop;
import com.yasser.roknaapp.R;

import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewHolder> {

    private List<Event> eventList;
    private Context context;
    CustomItemClickListener listener;

    public EventsAdapter(Context context, List<Event> eventList) {
        this.eventList = eventList;
        this.context = context;
    }

    @Override
    public EventsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        final EventsViewHolder holder = new EventsViewHolder(layoutView);
        layoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, holder.getPosition());

            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(EventsViewHolder holder, int position) {


    }


    //Set method of OnItemClickListener object
    public void setOnItemClickListener(CustomItemClickListener recyclerViewItemClickListner) {
        this.listener = recyclerViewItemClickListner;
    }


    @Override
    public int getItemCount() {
        return this.eventList.size();
    }

    public class EventsViewHolder extends RecyclerView.ViewHolder {
        public int position = 0;
        /**
         * this class contains onclick listener for the recylcer view home
         */

        public EventsViewHolder(View itemView) {
            super(itemView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //When item view is clicked, trigger the itemclicklistener
                    //Because that itemclicklistener is indicated in MainActivity
                    listener.onItemClick(v, position);
                }
            });
        }


    }
}