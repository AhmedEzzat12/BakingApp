package com.ntl.udacity.bakingapp.Models;

import android.os.Parcel;
import android.os.Parcelable;


public class IngredientItem implements Parcelable
{
    private String quantity;
    private String measure;
    private String ingredient;

    public String getQuantity()
    {
        return quantity;
    }

    public String getMeasure()
    {
        return measure;
    }

    public String getIngredient()
    {
        return ingredient;
    }

    public IngredientItem(Parcel in)
    {
        quantity = in.readString();
        measure = in.readString();
        ingredient = in.readString();
    }

    public static final Creator<IngredientItem> CREATOR = new Creator<IngredientItem>()
    {
        @Override
        public IngredientItem createFromParcel(Parcel in)
        {
            return new IngredientItem(in);
        }

        @Override
        public IngredientItem[] newArray(int size)
        {
            return new IngredientItem[size];
        }
    };

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
    }
}
