package com.ntl.udacity.bakingapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ntl.udacity.bakingapp.Fragments.IngredientsDetailFragment;
import com.ntl.udacity.bakingapp.Fragments.RecipeDetailFragment;
import com.ntl.udacity.bakingapp.Fragments.StepDetailFragment;
import com.ntl.udacity.bakingapp.Interfaces.transferDataInterface;
import com.ntl.udacity.bakingapp.Models.IngredientItem;
import com.ntl.udacity.bakingapp.Models.Recipe;
import com.ntl.udacity.bakingapp.Models.Step;
import com.ntl.udacity.bakingapp.R;

import java.util.List;

public class RecipDetaileActivity extends AppCompatActivity implements transferDataInterface
{

    public static final String STEP_KEY = "step_key";
    public static final String STEP_POSITION = "step_position";
    private static final String TAG = RecipDetaileActivity.class.getSimpleName();
    private boolean twoPane = false;
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        twoPane = findViewById(R.id.master_recipe_container) != null;
        recipe = getIntent().getParcelableExtra(MainActivity.RECIPE_KEY);

        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        recipeDetailFragment.setAnInterface(this);
        if (twoPane)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.master_recipe_container, recipeDetailFragment).commit();
        } else
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.recipe_detail_container, recipeDetailFragment).commit();
        }

    }

    @Override
    public void transform(Parcelable step, int position)
    {
        // TODO handle tablet
        Step stepObj = (Step) step;
        if (twoPane)
        {
            Log.d(TAG, "tablet mode");
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_recipe_container
                    , StepDetailFragment.getStepDetailFragmentInstance(stepObj)).commit();
        } else
        {
            Intent intent = new Intent(RecipDetaileActivity.this, MultiPagerStepDetailActivity.class);
            intent.putExtra(MainActivity.RECIPE_KEY, recipe);
            intent.putExtra(STEP_POSITION, position);
            startActivity(intent);
        }
    }

    @Override
    public void transform(List<IngredientItem> ingredients)
    {
        // TODO handle tablet
        if (twoPane)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_recipe_container
                    , IngredientsDetailFragment.getIngredientsDetailFragmentInstance(ingredients)).commit();

        } else
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.recipe_detail_container
                    , IngredientsDetailFragment.getIngredientsDetailFragmentInstance(ingredients)).commit();
        }

    }
}
