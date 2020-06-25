package com.yasser.roknaapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yasser.roknaapp.Model.Category;
import com.yasser.roknaapp.Model.Product;
import com.yasser.roknaapp.R;
import com.yasser.roknaapp.ui.main.ProductCategoryActivity;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.RecyclerViewHolders> {

    private List<Category> CategoryList;
    private Context context;
    CustomItemClickListener listener;

    public CategoriesAdapter(Context context, List<Category> CategoryList) {
        this.CategoryList = CategoryList;
        this.context = context;
    }

    public CategoriesAdapter() {
    }

    @Override
    public CategoriesAdapter.RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_row, parent,false);
        final CategoriesAdapter.RecyclerViewHolders holder = new CategoriesAdapter.RecyclerViewHolders(layoutView);
        layoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, holder.getPosition());

            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(CategoriesAdapter.RecyclerViewHolders holder, int position) {

        holder.CategoryName.setText(CategoryList.get(position).getTitle());

    }


    //Set method of OnItemClickListener object
    public void setOnItemClickListener(CustomItemClickListener recyclerViewItemClickListner) {
        this.listener = recyclerViewItemClickListner;
    }
    public void setData(List<Category> list) {
        CategoryList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.CategoryList.size();
    }

    public class RecyclerViewHolders extends RecyclerView.ViewHolder {

        /**
         * this class contains onclick listener for the recylcer view home
         */

        public TextView CategoryName;
        public int position = 0;

        public RecyclerViewHolders(View itemView) {
            super(itemView);
            CategoryName = (TextView) itemView.findViewById(R.id.cat_title);
            CategoryName.setTypeface(ResourcesCompat.getFont(context, R.font.jana));



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