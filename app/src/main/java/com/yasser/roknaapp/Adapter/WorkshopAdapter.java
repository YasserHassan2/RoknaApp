package com.yasser.roknaapp.Adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yasser.roknaapp.Model.Product;
import com.yasser.roknaapp.Model.Workshop;
import com.yasser.roknaapp.R;

import java.util.List;

public class WorkshopAdapter extends RecyclerView.Adapter<WorkshopAdapter.RecyclerViewHolders> {

    private List<Workshop> workshopList;
    private Context context;
    CustomItemClickListener listener;

    public WorkshopAdapter(Context context, List<Workshop> WorkshopList) {
        this.workshopList = WorkshopList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.workshop_item, parent, false);
        final RecyclerViewHolders holder = new RecyclerViewHolders(layoutView);
        layoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, holder.getPosition());

            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.roknalogo);

        Glide.with(context)
                .load(workshopList.get(position).getImageURL())
                .apply(requestOptions)
                .into(holder.productImage);

        holder.workshopTitle.setText(workshopList.get(position).getTitle());
        holder.workshopDesc.setText(workshopList.get(position).getDescription());
        holder.Workshopprice.setText(workshopList.get(position).getPrice() + " per person");

    }
    public void setData(List<Workshop> list) {
        workshopList = list;
        notifyDataSetChanged();
    }

    //Set method of OnItemClickListener object
    public void setOnItemClickListener(CustomItemClickListener recyclerViewItemClickListner) {
        this.listener = recyclerViewItemClickListner;
    }


    @Override
    public int getItemCount() {
        return this.workshopList.size();
    }

    public class RecyclerViewHolders extends RecyclerView.ViewHolder {

        /**
         * this class contains onclick listener for the recylcer view home
         */

        public TextView workshopTitle, workshopDesc, Workshopprice;
        public ImageView productImage;
        Button btn_location, btn_call;

        public int position = 0;

        public RecyclerViewHolders(View itemView) {
            super(itemView);
            workshopTitle = (TextView) itemView.findViewById(R.id.workshop_title);
            workshopDesc = (TextView) itemView.findViewById(R.id.workshopDesc);
            Workshopprice = (TextView) itemView.findViewById(R.id.workshopPrice);
            productImage = (ImageView) itemView.findViewById(R.id.workshop_image);
            btn_call = itemView.findViewById(R.id.btn_booknow);
            btn_location = itemView.findViewById(R.id.btn_location);

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
                            .appendQueryParameter("destination", workshopList.get(getAdapterPosition()).getLocation().getLatitude() + "," + workshopList.get(getAdapterPosition()).getLocation().getLongitude());
                    Toast.makeText(context, "Finiding Best Route", Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(Intent.ACTION_VIEW, directionsBuilder.build()));
                }
            });
            btn_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+workshopList.get(getAdapterPosition()).getPhone()));
                    context.startActivity(intent);

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