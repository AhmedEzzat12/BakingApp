package com.ntl.udacity.bakingapp;

import android.os.Parcelable;

import com.ntl.udacity.bakingapp.Models.IngredientItem;
import com.ntl.udacity.bakingapp.Models.Step;

import java.util.List;


public interface transferDataInterface
{
    void transform(Parcelable recipe, int position);

    void transform(List<IngredientItem> ingredients);

}
