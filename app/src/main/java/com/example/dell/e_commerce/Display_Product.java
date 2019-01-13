package com.example.dell.e_commerce;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Display_Product extends AppCompatActivity {

    List<Products> mFav;
    Products favourites;
    Button cart;
    int count=1;
    ImageView image1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display__product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mFav=new ProductsViewModel(getApplicationContext()).loadFavs();
        long i = getIntent().getLongExtra("product_detail",0);
        for (int j=0;j<mFav.size();j++)
        {
            if(mFav.get(j).mId==i)
                favourites = mFav.get(j);
        }
        TextView name=(TextView)findViewById(R.id.textView) ;
        TextView price=(TextView)findViewById(R.id.textView2);
        TextView Description=(TextView)findViewById(R.id.textView3) ;
        image1=(ImageView)findViewById(R.id.imageView);
        image1.setImageBitmap(BitmapFactory.decodeByteArray(favourites.mImage1,0,favourites.mImage1.length));
        final ImageButton image2=(ImageButton) findViewById(R.id.imageView5);
        image2.setVisibility(View.INVISIBLE);
        final ImageButton image3=(ImageButton) findViewById(R.id.imageView6);
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count==2)
                {
                    count=1;
                    image1.setImageBitmap(BitmapFactory.decodeByteArray(favourites.mImage1,0,favourites.mImage1.length));
                    image3.setVisibility(View.VISIBLE);
                    image2.setVisibility(View.INVISIBLE);
                }
            }
        });
        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count==1)
                {
                    image1.setImageBitmap(BitmapFactory.decodeByteArray(favourites.mImage2,0,favourites.mImage2.length));
                    count=2;
                    image3.setVisibility(View.INVISIBLE);
                    image2.setVisibility(View.VISIBLE);
                }
            }
        });
        cart=(Button)findViewById(R.id.button);
        if(favourites.mCart==0)
            cart.setText("Add to Cart");
        else
            cart.setText("Remove from Cart");
        name.setText(favourites.mName.toString());
        price.setText("Rs. " +String.valueOf(favourites.mPrice));
        Description.setText("This Product is sold by "+favourites.mSeller+". "+favourites.mDescription);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(favourites.mCart==0)
                {
                    favourites.mCart=1;
                    new ProductsViewModel(getApplicationContext()).updateFav(favourites.mId,1,1);
                    cart.setText("Remove From Cart");
                }
                else if(favourites.mCart==1)
                {
                    favourites.mCart=0;
                    new ProductsViewModel(getApplicationContext()).updateFav(favourites.mId,0,0);
                    cart.setText("Add to Cart");
                }
            }
        });
    }

}
