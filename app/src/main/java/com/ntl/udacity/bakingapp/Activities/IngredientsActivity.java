package com.ntl.udacity.bakingapp.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.ntl.udacity.bakingapp.Fragments.IngredientsDetailFragment;
import com.ntl.udacity.bakingapp.Models.IngredientItem;
import com.ntl.udacity.bakingapp.R;

import java.util.List;

public class IngredientsActivity extends AppCompatActivity
{

    private static final String INGREDIENT_FRAGMENT_TAG = "ingredient_fragment";
    private static final String INGREDIENTS_KEY = "ingredients_key";
    private List<IngredientItem> ingrednts;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            ingrednts = bundle.getParcelableArrayList(INGREDIENTS_KEY);
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(INGREDIENT_FRAGMENT_TAG);
            if (fragment == null)
            {
                getSupportFragmentManager().beginTransaction().replace(R.id.ingredients_container
                        , IngredientsDetailFragment.getIngredientsDetailFragmentInstance(ingrednts), INGREDIENT_FRAGMENT_TAG).commit();

            } else
            {
                ((IngredientsDetailFragment) fragment).setIngredients(ingrednts);
                getSupportFragmentManager().beginTransaction().show(fragment).commit();
            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {

            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
