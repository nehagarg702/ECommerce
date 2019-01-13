package com.example.dell.e_commerce;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity {

    private cartAdapter mFavAdapter;
    private List<Products> mFav = new ArrayList<>();
    Products favourites;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFav = new ProductsViewModel(getApplicationContext()).loadcart();
        if(mFav.size()==0)
        {
            finish();
        }
        mFavAdapter = new cartAdapter(getApplicationContext(), mFav, new onclickListener() {
            @Override
            public void onItemClick(Products item, View v, int position) {
                Toast.makeText(getApplicationContext(), item.mDescription+" abcd "+item.mCategory, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(mFavAdapter);
        button=(Button)findViewById(R.id.button2);
        button.setText("Total Price is Rs. "+String.valueOf(new ProductsViewModel(getApplicationContext()).totalsum()));
    }

    public class cartAdapter extends RecyclerView.Adapter<cartAdapter.cartViewHolder> {

        private Context context;
        private List<Products> mFavItems;
        onclickListener mlistener;

        public cartAdapter(Context context, List<Products> navDrawerItems, onclickListener listener) {
            this.context = context;
            this.mFavItems = navDrawerItems;
            this.mlistener = listener;
        }

        @Override
        public int getItemCount() {
            return mFavItems.size();
        }

        @Override
        public cartAdapter.cartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_row, parent, false);
            return new cartAdapter.cartViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final cartAdapter.cartViewHolder holder, final int position) {
            favourites = mFavItems.get(position);
            holder.mTxtTitle.setText(favourites.mName);
            holder.mTxtCount.setText("Rs. "+String.valueOf(favourites.mPrice));
            holder.quantity.setText(String.valueOf(favourites.mQuantity));
            if(favourites.mQuantity<=1)
                holder.minus.setVisibility(View.INVISIBLE);
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    favourites=mFavItems.get(position);
                    Long pos=favourites.mId;
                    new deletedata().execute(String.valueOf(pos));
                }
            });
            holder.plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    favourites=mFavItems.get(position);
                    favourites.mQuantity++;
                    new ProductsViewModel(getApplicationContext()).updateQuantity(favourites.mId, favourites.mQuantity);
                    holder.quantity.setText(String.valueOf(favourites.mQuantity));
                    holder.minus.setVisibility(View.VISIBLE);
                    button.setText("Total Price is Rs. "+String.valueOf(new ProductsViewModel(getApplicationContext()).totalsum()));
                }
            });
            holder.minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    favourites=mFavItems.get(position);
                    favourites.mQuantity--;
                    new ProductsViewModel(getApplicationContext()).updateQuantity(favourites.mId, favourites.mQuantity);
                    holder.quantity.setText(String.valueOf(favourites.mQuantity));
                    if(favourites.mQuantity==1)
                        holder.minus.setVisibility(View.INVISIBLE);
                    button.setText("Total Price is Rs. "+String.valueOf(new ProductsViewModel(getApplicationContext()).totalsum()));
                }
            });
            holder.image.setImageBitmap(BitmapFactory.decodeByteArray(favourites.mImage1, 0, favourites.mImage1.length));
        }


        class cartViewHolder extends RecyclerView.ViewHolder {

            TextView mTxtTitle;
            TextView mTxtCount;
            ImageView image;
            TextView quantity;
            ImageView plus;
            ImageView minus;
            ImageView delete;

            cartViewHolder(View itemView) {
                super(itemView);

                mTxtTitle = itemView.findViewById(R.id.name);
                mTxtCount = itemView.findViewById(R.id.price);
                image = itemView.findViewById(R.id.list_image);
                quantity = itemView.findViewById(R.id.quantity);
                plus = itemView.findViewById(R.id.cart_plus_img);
                minus = itemView.findViewById(R.id.cart_minus_img);
                delete = itemView.findViewById(R.id.imageView7);

            }
        }
    }

    public class deletedata extends AsyncTask<String,String,String>
    {
        ProgressDialog progressDialog=new ProgressDialog(Cart.this);
        Intent i=new Intent(getApplicationContext(),Cart.class);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {
            new ProductsViewModel(getApplicationContext()).removeFav(mFav,Integer.parseInt(strings[0]));
            return "abcdf";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            startActivity(i);
            finish();

        }
    }

}
