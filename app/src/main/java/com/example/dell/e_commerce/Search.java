package com.example.dell.e_commerce;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {
    EditText searchtext;
    RecyclerView recyclerView;
    private FavAdapter mFavAdapter;
    private ProductsViewModel mFavViewModel;
    private List<Products> mFav=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        searchtext = findViewById(R.id.editText6);
        ImageView back=(ImageView)findViewById(R.id.imageView10);
        final ImageView search=(ImageView)findViewById(R.id.imageView11);
        recyclerView = findViewById(R.id.recycleView);
        mFavViewModel=new ProductsViewModel(getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        searchtext.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            dowork();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dowork();

            }
        });

    }

    public void dowork()
    {

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput (InputMethodManager.RESULT_HIDDEN,0);
        String str=searchtext.getText().toString();
        mFav = mFavViewModel.loadsearchdata(str);
        if(str.length()==0)
        {
            mFav=new ArrayList<>();
            mFavAdapter = new FavAdapter(getApplicationContext(), mFav, new onclickListener() {
                @Override
                public void onItemClick(Products item, View v, int position) {
                }
            });
            recyclerView.setAdapter(mFavAdapter);
            Toast.makeText(getApplicationContext(), "Please enter the name of product to be searched", Toast.LENGTH_SHORT).show();
        }
        else
        {
            //search.setImageIcon();
            if(mFav.size()==0) {
                mFavAdapter = new FavAdapter(getApplicationContext(), mFav, new onclickListener() {
                    @Override
                    public void onItemClick(Products item, View v, int position) {
                    }
                });
                recyclerView.setAdapter(mFavAdapter);
                Toast.makeText(getApplicationContext(), "No matching Product", Toast.LENGTH_SHORT).show();
            }
            else
            {
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
    }
}
