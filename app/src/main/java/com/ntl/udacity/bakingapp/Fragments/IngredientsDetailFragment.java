package com.ntl.udacity.bakingapp.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ntl.udacity.bakingapp.Adapters.ingredientsAdapter;
import com.ntl.udacity.bakingapp.Models.IngredientItem;
import com.ntl.udacity.bakingapp.R;

import java.util.List;


public class IngredientsDetailFragment extends Fragment
{
    private List<IngredientItem> ingredients;

    public static IngredientsDetailFragment getIngredientsDetailFragmentInstance(List<IngredientItem> ingredients)
    {
        IngredientsDetailFragment ingredientsDetailFragment = new IngredientsDetailFragment();
        ingredientsDetailFragment.setIngredients(ingredients);
        return ingredientsDetailFragment;
    }

    public void setIngredients(List<IngredientItem> ingredients)
    {
        this.ingredients = ingredients;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        //TODO handle rotation
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_ingredients_detail, container, false);
        ListView listView= view.findViewById(R.id.ingredients_lv);
        listView.setAdapter(new ingredientsAdapter(container.getContext(),ingredients));
        return view ;
    }
}
