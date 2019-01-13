package com.example.dell.e_commerce;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Filtered_Product extends AppCompatActivity {

    Long min,max;
    String[] outputStrArr;
    private FavAdapter mFavAdapter;
    private ProductsViewModel mFavViewModel;
    private List<Products> mFav=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered__product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        min=getIntent().getLongExtra("min",0);
        max=getIntent().getLongExtra("max",0);
        outputStrArr=getIntent().getStringArrayExtra("result");
        mFavViewModel=new ProductsViewModel(getApplicationContext());
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFav = mFavViewModel.loadfiltereddata(min,max,outputStrArr);
        mFavAdapter = new FavAdapter(getApplicationContext(), mFav, new onclickListener() {
            @Override
            public void onItemClick(Products item, View v, int position) {
                Intent i=new Intent(getApplicationContext(),Display_Product.class);
                i.putExtra("product_detail",mFav.get(position).mId);
                startActivity(i);
            }
        });
        recyclerView.setAdapter(mFavAdapter);
    }

}
