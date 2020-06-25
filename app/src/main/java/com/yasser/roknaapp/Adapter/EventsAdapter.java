package com.yasser.roknaapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;
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

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.roknalogo);

        Glide.with(context)
                .load(eventList.get(position).getEventImageURL())
                .apply(requestOptions)
                .into(holder.eventImage);

        holder.tv_eventTitle.setTypeface(ResourcesCompat.getFont(context, R.font.jana));
        holder.tv_eventDates.setTypeface(ResourcesCompat.getFont(context, R.font.jana));
        holder.tv_eventAvlaiable.setTypeface(ResourcesCompat.getFont(context, R.font.jana));
        holder.btn_location.setTypeface(ResourcesCompat.getFont(context, R.font.jana));

        holder.tv_eventTitle.setText(eventList.get(position).getEventTitle());
        holder.tv_eventDates.setText(eventList.get(position).getEventDates());

        if (eventList.get(position).isAvaliable().equals("false"))
        {
            holder.tv_eventAvlaiable.setVisibility(View.VISIBLE);
            holder.tv_eventAvlaiable.setText("Finished");
        }


    }

    public void setData(List<Event> list) {
        eventList = list;
        notifyDataSetChanged();
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

        ImageView eventImage;
        TextView tv_eventTitle,tv_eventDates,tv_eventAvlaiable;
        Button btn_location;
        public int position = 0;
        /**
         * this class contains onclick listener for the recylcer view home
         */

        public EventsViewHolder(View itemView) {
            super(itemView);
           eventImage= itemView.findViewById(R.id.eventImage);
           tv_eventTitle = itemView.findViewById(R.id.eventTitle);
           tv_eventDates = itemView.findViewById(R.id.tv_eventDates);
           btn_location = itemView.findViewById(R.id.btn_Event_location);
           tv_eventAvlaiable = itemView.findViewById(R.id.event_Avaliable);



            btn_location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri.Builder directionsBuilder = new Uri.Builder()
                            .scheme("https")
                            .authority("www.google.com")
                            .appendPath("maps")
                            .appendPath("dir")
                            .appendPath("")
                            .appendQueryParameter("api", "1")
                            .appendQueryParameter("destination", eventList.get(getAdapterPosition()).getEventLocation().getLatitude() + "," + eventList.get(getAdapterPosition()).getEventLocation().getLongitude());
                    Toast.makeText(context, "Finiding Best Route", Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(Intent.ACTION_VIEW, directionsBuilder.build()));
                }
            });
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