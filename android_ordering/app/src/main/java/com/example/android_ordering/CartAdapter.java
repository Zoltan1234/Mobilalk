package com.example.android_ordering;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<Shoppingitem> mCartList;
    private List<Shoppingitem> mCartListAll;
    private Context mContext;
    private int lastPosition = -1;
    private boolean animateLeft = false;
    private String animateID = "";

    CartAdapter(Context context, ArrayList<Shoppingitem> cartList) {
        this.mCartList = cartList;
        this.mCartListAll = cartList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.cart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        Shoppingitem product = mCartList.get(position);
        holder.bindToItem(product);

        if(holder.getAdapterPosition() > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_row);
            holder.itemView.startAnimation(animation);
            lastPosition = holder.getAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return mCartList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mCartImage;
        private TextView mPriceText;
        private TextView mNameText;
        private TextView mAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mCartImage = itemView.findViewById(R.id.itemImage);
            mPriceText = itemView.findViewById(R.id.price);
            mNameText = itemView.findViewById(R.id.itemTitle);
            mAmount = itemView.findViewById(R.id.amount);
        }

        private void bindToItem(Shoppingitem item) {
            Glide.with(mContext).load(item.getImageRes()).into(mCartImage);
            mNameText.setText(item.getName());
            mAmount.setText(String.valueOf(item.getAmount()));

            if (animateID.equals(item.getId())) {
                if (animateLeft) {
                    Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_row);
                    itemView.startAnimation(animation);
                } else {
                    Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_row);
                    itemView.startAnimation(animation);
                }
                animateID = "";
            }

        }



    }

}

