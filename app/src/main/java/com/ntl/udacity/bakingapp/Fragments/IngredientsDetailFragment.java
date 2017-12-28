package com.ntl.udacity.bakingapp.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ntl.udacity.bakingapp.Adapters.IngredientsAdapter;
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
        IngredientsAdapter adapter = new IngredientsAdapter(ingredients);
        RecyclerView recyclerView = view.findViewById(R.id.ingredients_lv);
        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        return view ;
    }
}
