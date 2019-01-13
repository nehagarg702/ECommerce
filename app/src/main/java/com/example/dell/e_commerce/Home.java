package com.example.dell.e_commerce;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    private FavAdapter mFavAdapter;
    private ProductsViewModel mFavViewModel;
    private List<Products> mFav=new ArrayList<>();
    private float downRawX, downRawY;
    private float dX, dY;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbartitle = findViewById(R.id.titleToolbar);
        toolbartitle.setText("Home");
        fab = findViewById(R.id.fab);
        mFavViewModel=new ProductsViewModel(getApplicationContext());
        final RecyclerView recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFav = mFavViewModel.loadFavs();
        mFavAdapter = new FavAdapter(getApplicationContext(), mFav, new onclickListener() {
            @Override
            public void onItemClick(Products item, View v, int position) {
                Intent i=new Intent(getApplicationContext(),Display_Product.class);
                i.putExtra("product_detail",mFav.get(position).mId);
                startActivity(i);
            }
        });
        recyclerView.setAdapter(mFavAdapter);

        fab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            /**
             * The purpose of this function to to slide the button when users try to slide the button and go to next activity when button is clicked.
             */
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {

                    downRawX = motionEvent.getRawX();
                    downRawY = motionEvent.getRawY();
                    dX = view.getX() - downRawX;
                    dY = view.getY() - downRawY;

                    return true; // Consumed

                }
                else if (action == MotionEvent.ACTION_MOVE) {

                    int viewWidth = view.getWidth();
                    int viewHeight = view.getHeight();

                    View viewParent = (View)view.getParent();
                    int parentWidth = viewParent.getWidth();
                    int parentHeight = viewParent.getHeight();

                    float newX = motionEvent.getRawX() + dX;
                    newX = Math.max(0, newX); // Don't allow the FAB past the left hand side of the parent
                    newX = Math.min(parentWidth - viewWidth, newX); // Don't allow the FAB past the right hand side of the parent

                    float newY = motionEvent.getRawY() + dY;
                    newY = Math.max(0, newY); // Don't allow the FAB past the top of the parent
                    newY = Math.min(parentHeight - viewHeight, newY); // Don't allow the FAB past the bottom of the parent

                    view.animate()
                            .x(newX)
                            .y(newY)
                            .setDuration(0)
                            .start();

                    return true; // Consumed

                }
                else if (action == MotionEvent.ACTION_UP) {

                    float upRawX = motionEvent.getRawX();
                    float upRawY = motionEvent.getRawY();

                    float upDX = upRawX - downRawX;
                    float upDY = upRawY - downRawY;

                    if (Math.abs(upDX) < 10 && Math.abs(upDY) < 10) { // A click
                        Intent i = new Intent(getApplicationContext(), Add_Product.class);
                        startActivity(i);
                        finish();
                        return false;
                    } else { // A drag
                        return true; // Consumed
                    }
                }
                return false;
            }
        });

        setToolbarIconsClickListeners();
        ImageView cart = findViewById(R.id.imageView8);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Filter.class);
                startActivity(i);
            }
        });
        ImageView search = findViewById(R.id.imageView9);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Search.class);
                startActivity(i);
            }
        });

    }

    private void setToolbarIconsClickListeners() {
        ImageView cart = findViewById(R.id.cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mFavViewModel.cartcount()==0)
                {
                    Toast.makeText(getApplicationContext(),"Cart is Empty",Toast.LENGTH_LONG).show();
                }
                else{
                    Intent i=new Intent(getApplicationContext(),Cart.class);
                    startActivity(i);}
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Update Cart Count
        int cartCount = mFavViewModel.cartcount();
        TextView count = findViewById(R.id.count);
        if (cartCount > 0) {
            count.setVisibility(View.VISIBLE);
            count.setText(String.valueOf(cartCount));
        } else {
            count.setVisibility(View.GONE);
        }
    }

}
