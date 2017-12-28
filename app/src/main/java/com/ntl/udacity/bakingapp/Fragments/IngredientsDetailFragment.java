package com.ntl.udacity.bakingapp.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
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
    private static final String SCROLL_POSITION_KEY = "position_key";
    private List<IngredientItem> ingredients;
    private int scrollPosition;
    private RecyclerView recyclerView;

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
    public void onPause()
    {
        super.onPause();
        scrollPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState)
    {
        outState.putInt(SCROLL_POSITION_KEY, scrollPosition);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (recyclerView != null)
        {
            recyclerView.getLayoutManager().scrollToPosition(scrollPosition);
            scrollPosition = 0;
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (savedInstanceState != null)
        {
            scrollPosition = savedInstanceState.getInt(SCROLL_POSITION_KEY);
        }


        View view = inflater.inflate(R.layout.fragment_ingredients_detail, container, false);
        IngredientsAdapter adapter = new IngredientsAdapter(ingredients);
        recyclerView = view.findViewById(R.id.ingredients_lv);
        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
