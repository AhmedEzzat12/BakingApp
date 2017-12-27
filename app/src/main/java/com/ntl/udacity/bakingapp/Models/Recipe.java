package com.ntl.udacity.bakingapp.Models;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Recipe implements Parcelable
{
    private String id;
    private String name;
    private String servings;
    private String image;
    private List<IngredientItem> ingredients;
    private List<Step> steps;


    public Recipe()
    {
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>()
    {
        @Override
        public Recipe createFromParcel(Parcel in)
        {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size)
        {
            return new Recipe[size];
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
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(servings);
        dest.writeString(image);
        dest.writeList(ingredients);
        dest.writeList(steps);
    }
    private Recipe(Parcel in)
    {
        id = in.readString();
        name = in.readString();
        servings = in.readString();
        image = in.readString();
        ingredients = new ArrayList<>();
        in.readList(ingredients,IngredientItem.class.getClassLoader());
        steps = new ArrayList<>();
        in.readList(steps,Step.class.getClassLoader());

    }

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getServings()
    {
        return servings;
    }

    public String getImage()
    {
        return image;
    }

    public List<IngredientItem> getIngredients()
    {
        return ingredients;
    }

    public List<Step> getSteps()
    {
        return steps;
    }
}
