package com.example.android_ordering;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ViewHolder> implements Filterable {
    private ArrayList<Shoppingitem> ShoppingItemData;
    private ArrayList<Shoppingitem> ShoppingItemDataAll;
    private Context mcontext;
    private int lastposition = -1;

    ShoppingAdapter(Context context, ArrayList<Shoppingitem> itemsData){
        this.ShoppingItemData=itemsData;
        this.ShoppingItemDataAll=itemsData;
        this.mcontext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.itemlist,parent,false));
    }

    @Override
    public void onBindViewHolder(ShoppingAdapter.ViewHolder holder, int position) {
        Shoppingitem currenttimeItem = ShoppingItemData.get(position);
        holder.bindTo(currenttimeItem);

        if (holder.getAdapterPosition()>lastposition){
            Animation animation = AnimationUtils.loadAnimation(mcontext,R.anim.slide_in_row);
            holder.itemView.startAnimation(animation);
            lastposition = holder.getAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return ShoppingItemData.size();
    }

    @Override
    public Filter getFilter() {
        return ShoppingFilter;
    }
    public Filter ShoppingFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Shoppingitem> filteredlist = new ArrayList<>();
            FilterResults result = new FilterResults();
            if (charSequence==null||charSequence.length()==0){
                result.count = ShoppingItemDataAll.size();
                result.values = ShoppingItemDataAll;
            }else {
                String filterP = charSequence.toString().toLowerCase().trim();
                for (Shoppingitem item : ShoppingItemDataAll){
                    if (item.getName().toLowerCase().contains(filterP)){
                        filteredlist.add(item);
                    }
                }
                result.count = filteredlist.size();
                result.values = filteredlist;
            }
            return result;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ShoppingItemData = (ArrayList) filterResults.values;
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView Titletext;
        private TextView Infotext;
        private TextView Pricetext;
        private ImageView mItemimage;
        private RatingBar mRatingbar;

        public ViewHolder(View itemView){
            super(itemView);
            Titletext = itemView.findViewById(R.id.itemTitle);
            Infotext = itemView.findViewById(R.id.subTitle);
            Pricetext = itemView.findViewById(R.id.price);
            mItemimage = itemView.findViewById(R.id.itemImage);
            mRatingbar = itemView.findViewById(R.id.ratingBar);

            itemView.findViewById(R.id.add_to_cart).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Activity","Kosarhoz ad");
                    ((shop)mcontext).updateAlertIcon();
                }
            });
        }
        public void bindTo(Shoppingitem currenttime){
            Titletext.setText(currenttime.getName());
            Infotext.setText(currenttime.getInfo());
            Pricetext.setText(currenttime.getPrice());
            mRatingbar.setRating(currenttime.getRated());
            Glide.with(mcontext).load(currenttime.getImageRes()).into(mItemimage);
        }
    }
}


