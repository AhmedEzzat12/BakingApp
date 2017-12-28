package com.ntl.udacity.bakingapp.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ntl.udacity.bakingapp.Activities.MainActivity;
import com.ntl.udacity.bakingapp.Adapters.CardsAdapter;
import com.ntl.udacity.bakingapp.Adapters.stepsAdapter;
import com.ntl.udacity.bakingapp.Interfaces.transferDataInterface;
import com.ntl.udacity.bakingapp.Models.IngredientItem;
import com.ntl.udacity.bakingapp.Models.Recipe;
import com.ntl.udacity.bakingapp.R;
import com.ntl.udacity.bakingapp.Widget.RecipesWidget;

import java.util.ArrayList;

import static android.view.View.OnClickListener;


public class RecipeDetailFragment extends Fragment implements CardsAdapter.MOnItemListener
{

    private static final String ACTION_UPDATE_INGREDIENTS = "com.ntl.udacity.bakingapp.action.ACTION_UPDATE_INGREDIENTS";
    private static final String EXTRA_RECIPES = "recipes";
    private static final String TAG = RecipeDetailFragment.class.getSimpleName();
    private static final String SCROLL_POSITION_KEY = "position_key";
    private Recipe recipe;
    private transferDataInterface anInterface;
    private Context context;
    private stepsAdapter adapter;
    private int scrollPosition;
    private RecyclerView recyclerView;

    public RecipeDetailFragment()
    {
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
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
        Log.d(TAG, String.valueOf(scrollPosition));
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
            Log.d(TAG + "scrolled", String.valueOf(scrollPosition));
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
            Log.d(TAG + " restored", String.valueOf(scrollPosition));
        }

        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        try
        {
            recipe = getActivity().getIntent().getParcelableExtra(MainActivity.RECIPE_KEY);
        } catch (Exception e)
        {
            e.printStackTrace();
        }


        Button button = view.findViewById(R.id.ingredients_btn);
        Button widgetSetDataBtn = view.findViewById(R.id.show_ingredients_in_widget_btn);
        button.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                anInterface.transform(recipe.getIngredients());
                Log.d("getIngredients size", String.valueOf(recipe.getIngredients().size()));
            }
        });

        widgetSetDataBtn.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                //IngredientsWidgetService.startActionUpdateIngredients(context, (ArrayList<IngredientItem>) recipe.getIngredients());
                Intent intent = new Intent(context, RecipesWidget.class);
                intent.setAction(ACTION_UPDATE_INGREDIENTS);
                intent.putParcelableArrayListExtra(EXTRA_RECIPES, (ArrayList<IngredientItem>) recipe.getIngredients());
                context.sendBroadcast(intent);
                Log.d("getIngredients size", String.valueOf(recipe.getIngredients().size()));

            }
        });
        adapter = new stepsAdapter(recipe.getSteps(), this);
        recyclerView = view.findViewById(R.id.steps_RV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        return view;
    }

    public void setAnInterface(transferDataInterface anInterface)
    {
        this.anInterface = anInterface;
    }

    @Override
    public void onListItemClick(int position)
    {
        Log.d("recipe onListItem", String.valueOf(position));
        anInterface.transform(recipe.getSteps().get(position), position);

    }
}
