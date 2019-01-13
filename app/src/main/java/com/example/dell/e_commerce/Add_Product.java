package com.example.dell.e_commerce;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Add_Product extends AppCompatActivity {

    ImageView image1,image2;
    byte [] image11,image12;
    private float dX, dY, downRawX, downRawY;
    EditText name,Description, Price, Category,Seller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        name=(EditText)findViewById(R.id.editText2);
        Description=(EditText)findViewById(R.id.editText4);
        Price=(EditText)findViewById(R.id.editText5);
        Category=(EditText)findViewById(R.id.editText7);
        Seller=(EditText)findViewById(R.id.editText8);
        image1=(ImageView)findViewById(R.id.imageView3);
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i,"Select Picture"),1);
            }
        });
        image2=(ImageView)findViewById(R.id.imageView4);
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i,"Select Picture"),2);
            }
        });

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
                        perform_click();
                        return false;
                    } else { // A drag
                        return true; // Consumed
                    }
                }
                return false;
            }
        });


    }

    public void perform_click()
    {
        if(name.getText().toString().length()==0)
        {
            Toast.makeText(getApplicationContext(),"Name is empty",Toast.LENGTH_LONG).show();
        }
        else if(Description.getText().toString().length()==0)
        {
            Toast.makeText(getApplicationContext(),"Description is empty",Toast.LENGTH_LONG).show();
        }
        else if(Category.getText().toString().length()==0)
        {
            Toast.makeText(getApplicationContext(),"Category is empty",Toast.LENGTH_LONG).show();
        }
        else if(Price.getText().toString().length()==0)
        {
            Toast.makeText(getApplicationContext(),"Price is empty",Toast.LENGTH_LONG).show();
        }
        else if(Seller.getText().toString().length()==0)
            Toast.makeText(getApplicationContext(),"Please enter seller details",Toast.LENGTH_LONG).show();
        else if(image11==null)
            Toast.makeText(getApplicationContext(),"Image1 is empty",Toast.LENGTH_LONG).show();
        else if(image12==null)
            Toast.makeText(getApplicationContext(),"Image2 is empty",Toast.LENGTH_LONG).show();
        else{
            new ProductsViewModel(getApplicationContext()).addFav(name.getText().toString(),Description.getText().toString(),Category.getText().toString(),0,Integer.parseInt(Price.getText().toString()),image11,image12,1,Seller.getText().toString());
            Intent i=new Intent(getApplicationContext(),Home.class);
            startActivity(i);
            finish();}
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            final Uri imageUri = data.getData();
            final InputStream imageStream = getContentResolver().openInputStream(imageUri);
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            selectedImage.compress(Bitmap.CompressFormat.PNG, 10, stream);
            if(requestCode==1) {
                image1.setImageBitmap(selectedImage);
                image11=stream.toByteArray();
            }
            if(requestCode==2) {
                image12=stream.toByteArray();
                image2.setImageBitmap(selectedImage);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(Add_Product.this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(getApplicationContext(),Home.class);
        startActivity(i);
    }
}
