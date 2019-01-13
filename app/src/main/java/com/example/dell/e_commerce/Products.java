package com.example.dell.e_commerce;

public class Products {

    public long mId;
    public String mName;
	public String mDescription;
	public String mSeller;
public String mCategory;
public long mPrice;
public byte[] mImage1;
public byte[] mImage2;
    public Integer mQuantity;
    public Integer mCart;


    public Products(long id, String name, String Description, String category, Integer cart, long price, byte[] Image1, byte[] Image2, Integer quantity, String Seller) {
        mId = id;
        mName = name;
		mCategory=category;
mCart=cart;
mPrice=price;
mImage1=Image1;
mImage2=Image2;
mQuantity=quantity;
        mDescription = Description;
        mSeller=Seller;
    }

    public Products(Products products) {
        mId = products.mId;
        mName = products.mName;
		mCategory=products.mCategory;
		mPrice=products.mPrice;
		mImage1=products.mImage1;
		mImage2=products.mImage2;
		mQuantity=products.mQuantity;
		mDescription=products.mDescription;
mCart=products.mCart;
mSeller=products.mSeller;
    }

}