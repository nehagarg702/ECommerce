package com.example.dell.e_commerce;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.FavViewHolder> {
List<Products> mFav;
Context mContext;
onclickListener listener;

    public FavAdapter(Context mContext, List<Products> data, onclickListener listener) {
        this.mFav = data;
        this.mContext = mContext;
        this.listener = listener;
    }
    @Override
    public FavViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_row, parent, false);
        return new FavViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FavViewHolder holder, int position) {
        Products favourites = mFav.get(position);
        holder.bind(favourites, listener,position);
        holder.mTxtUrl.setText(favourites.mName);
        holder.mTxtDate.setText("Rs. "+String.valueOf(favourites.mPrice));
        holder.image.setImageBitmap(BitmapFactory.decodeByteArray(favourites.mImage1,0,favourites.mImage1.length));
    }

    @Override
    public int getItemCount() {
        return mFav.size();
    }

    class FavViewHolder extends RecyclerView.ViewHolder {

        TextView mTxtUrl;
        TextView mTxtDate;
        ImageView image;

        FavViewHolder(View itemView) {
            super(itemView);
            mTxtUrl = itemView.findViewById(R.id.tvUrl);
            mTxtDate = itemView.findViewById(R.id.tvDate);
            image = itemView.findViewById(R.id.imageView2);
        }

        public void bind(final Products item, final onclickListener listener, final int Position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item, v, Position);
                }
            });
        }
    }
}

