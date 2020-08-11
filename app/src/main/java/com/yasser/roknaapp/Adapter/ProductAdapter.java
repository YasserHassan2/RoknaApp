package com.yasser.roknaapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yasser.roknaapp.Model.Product;
import com.yasser.roknaapp.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.RecyclerViewHolders> {

    private List<Product> productList;
    private Context context;
    CustomItemClickListener listener;

    public ProductAdapter(Context context, List<Product> productList) {
        this.productList = productList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_row, parent,false);
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
                .placeholder(R.drawable.loading);

        Glide.with(context)
                .load(productList.get(position).getImgURL1())
                .apply(requestOptions)
                .into(holder.productImage);

        holder.productName.setText(productList.get(position).getName());
        holder.productName.setTypeface(ResourcesCompat.getFont(context, R.font.jana));


        holder.productDesc.setText(productList.get(position).getDescription());
        holder.productDesc.setTypeface(ResourcesCompat.getFont(context, R.font.jana));


        holder.productprice.setText(productList.get(position).getPrice()+" EGP");
        holder.productprice.setTypeface(ResourcesCompat.getFont(context, R.font.jana));


        holder.productSale.setTypeface(ResourcesCompat.getFont(context, R.font.jana));
        holder.productAvaliable.setTypeface(ResourcesCompat.getFont(context, R.font.jana));

        if (productList.get(position).isAvaliable().equals("false"))
        {
            holder.productAvaliable.setVisibility(View.VISIBLE);
            holder.productAvaliable.setText("Solid out");
        }

        if (productList.get(position).getSale() != null)
        {
            holder.productSale.setVisibility(View.VISIBLE);
            holder.productSale.setText(productList.get(position).getSale() + "% SALE");
        }

    }

    public void setData(List<Product> list) {
        productList = list;
        notifyDataSetChanged();
    }

    //Set method of OnItemClickListener object
    public void setOnItemClickListener(CustomItemClickListener recyclerViewItemClickListner) {
        this.listener = recyclerViewItemClickListner;
    }


    @Override
    public int getItemCount() {
        return this.productList.size();
    }

    public class RecyclerViewHolders extends RecyclerView.ViewHolder {

        /**
         * this class contains onclick listener for the recylcer view home
         */

        public TextView productName, productDesc, productprice, productSale,productAvaliable;
        public ImageView productImage;
        public int position = 0;

        public RecyclerViewHolders(View itemView) {
            super(itemView);
            productName = (TextView) itemView.findViewById(R.id.prName);
            productDesc = (TextView) itemView.findViewById(R.id.prDesc);
            productprice = (TextView) itemView.findViewById(R.id.prPrice);
            productSale = (TextView) itemView.findViewById(R.id.prSale);
            productImage = (ImageView) itemView.findViewById(R.id.pr_image);
            productAvaliable = itemView.findViewById(R.id.prAvaliable);


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