package com.example.dell.e_commerce;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Filter extends AppCompatActivity {
    LayoutInflater ll;
    View itemView,itemView1;
    ListView cartegory;
    EditText min,max;
    int k=0;
    LinearLayout layout;
    ArrayAdapter<String> adapter1;
    List<String> Category=new ArrayList<String>();
    List<Long> minMax=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ListView list = (ListView) findViewById(R.id.listView);
        minMax=new ProductsViewModel(getApplicationContext()).loadprice();
        String list1[] = new String[]{"Price", "Category"};
        Category = new ProductsViewModel(getApplicationContext()).loadcategory();
        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, Category);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, Arrays.asList(list1));
        list.setAdapter(adapter);
        layout = (LinearLayout) findViewById(R.id.linearLayout2);
        ll = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        itemView = ll.inflate(R.layout.layout, null);
        itemView1 = ll.inflate(R.layout.category_list, null);
        layout.addView(itemView);
        final RangeSeekBar seekBar = (RangeSeekBar)itemView.findViewById(R.id.seekBar);
        seekBar.setRangeValues(minMax.get(0),minMax.get(1));
        min = (EditText)itemView.findViewById(R.id.editText);
        max = (EditText)itemView.findViewById(R.id.editText3);
        min.setText(String.valueOf(minMax.get(0)));
        max.setText(String.valueOf(minMax.get(1)));
        cartegory = (ListView)itemView1.findViewById(R.id.listView);
        cartegory.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        cartegory.setAdapter(adapter1);
        cartegory.setItemChecked(0,true);
        min.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()==0)
                {
Toast.makeText(getApplicationContext(),s.toString(),Toast.LENGTH_SHORT).show();
                }
                else {

                    if(Integer.parseInt(s.toString())>Integer.parseInt(max.getText().toString()))
                    {
                        Toast.makeText(getApplicationContext(),"Minimum value can not be greater than maximu value",Toast.LENGTH_SHORT).show();
                        min.setText(String.valueOf(minMax.get(0)));
                        seekBar.setSelectedMinValue(minMax.get(0));
                    }
                    if(Integer.parseInt(s.toString())<minMax.get(0))
                    {
                        Toast.makeText(getApplicationContext(),"Invalid Minimum Value",Toast.LENGTH_SHORT).show();
                        min.setText(String.valueOf(minMax.get(0)));
                        seekBar.setSelectedMinValue(minMax.get(0));

                    }
                    else if(Integer.parseInt(s.toString())>minMax.get(1))
                    {
                        Toast.makeText(getApplicationContext(),"Invalid Minimum Value",Toast.LENGTH_SHORT).show();
                        min.setText(String.valueOf(minMax.get(0)));
                        seekBar.setSelectedMinValue(minMax.get(0));

                    }
                        if(Integer.parseInt(s.toString())>=minMax.get(0))
                    seekBar.setSelectedMinValue(Integer.parseInt(s.toString()));
                }
            }
        });
        max.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()==0)
                {
                    Toast.makeText(getApplicationContext(),s.toString(),Toast.LENGTH_SHORT).show();
                }
                else {
                    if(Integer.parseInt(s.toString())<Integer.parseInt(min.getText().toString()))
                    {
                        Toast.makeText(getApplicationContext(),"Minimum value can not be greater than maximu value",Toast.LENGTH_SHORT).show();
                        max.setText(String.valueOf(minMax.get(1)));
                        seekBar.setSelectedMaxValue(minMax.get(1));
                    }
                    if(Integer.parseInt(s.toString())<minMax.get(0))
                    {
                        Toast.makeText(getApplicationContext(),"Invalid Minimum Value",Toast.LENGTH_SHORT).show();
                        max.setText(String.valueOf(minMax.get(1)));
                        seekBar.setSelectedMaxValue(minMax.get(1));

                    }
                    else if(Integer.parseInt(s.toString())>minMax.get(1))
                    {
                        Toast.makeText(getApplicationContext(),"Invalid Minimum Value",Toast.LENGTH_SHORT).show();
                        max.setText(String.valueOf(minMax.get(1)));
                        seekBar.setSelectedMaxValue(minMax.get(1));
                }
                if(Integer.parseInt(s.toString())<=minMax.get(1))
                seekBar.setSelectedMaxValue(Integer.parseInt(s.toString()));}
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    if (k == 1) {
                        k = 0;
                        layout.removeView(itemView1);
                    } else
                        layout.removeView(itemView);
                    layout.addView(itemView);
                    } else {
                    if (!(k == 1)) {
                        layout.removeView(itemView);
                        k = 1;
                        layout.addView(itemView1);
                        cartegory = (ListView)itemView1.findViewById(R.id.listView);
                        cartegory.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                        cartegory.setAdapter(adapter1);
                        cartegory.setItemChecked(0,true);
                    }
                }
            }
        });

        seekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Long>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Long minValue, Long maxValue) {
                //Now you have the minValue and maxValue of your RangeSeekbar
                min.setText(String.valueOf(minValue));
                max.setText(String.valueOf(maxValue));
            }
        });
        seekBar.setNotifyWhileDragging(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.done)
        {
            SparseBooleanArray checked = cartegory.getCheckedItemPositions();
            ArrayList<String> selectedItems = new ArrayList<String>();
            for (int i = 0; i < checked.size(); i++) {
                int position = checked.keyAt(i);
                if (checked.valueAt(i))
                    selectedItems.add(adapter1.getItem(position));
            }
            String[] outputStrArr = new String[selectedItems.size()];
            for (int i = 0; i < selectedItems.size(); i++) {
                outputStrArr[i] = selectedItems.get(i);
            }

            Intent returnIntent = new Intent(getApplicationContext(),Filtered_Product.class);
            returnIntent.putExtra("result",outputStrArr);
            returnIntent.putExtra("min",Long.parseLong(min.getText().toString()));
            returnIntent.putExtra("max",Long.parseLong(max.getText().toString()));
            startActivity(returnIntent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
