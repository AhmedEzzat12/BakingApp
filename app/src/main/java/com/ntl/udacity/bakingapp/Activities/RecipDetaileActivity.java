package com.ntl.udacity.bakingapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.ntl.udacity.bakingapp.Fragments.IngredientsDetailFragment;
import com.ntl.udacity.bakingapp.Fragments.RecipeDetailFragment;
import com.ntl.udacity.bakingapp.Fragments.StepDetailFragment;
import com.ntl.udacity.bakingapp.Interfaces.transferDataInterface;
import com.ntl.udacity.bakingapp.Models.IngredientItem;
import com.ntl.udacity.bakingapp.Models.Recipe;
import com.ntl.udacity.bakingapp.Models.Step;
import com.ntl.udacity.bakingapp.R;

import java.util.ArrayList;
import java.util.List;

public class RecipDetaileActivity extends AppCompatActivity implements transferDataInterface
{

    public static final String STEP_KEY = "step_key";
    public static final String STEP_POSITION = "step_position";
    private static final String TAG = RecipDetaileActivity.class.getSimpleName();
    private static final String INGREDIENTS_KEY = "ingredients_key";
    private static final String RECIPE_FRAGMENT_TAG = "recipe_tag";
    private boolean twoPane = false;
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        twoPane = findViewById(R.id.master_recipe_container) != null;
        recipe = getIntent().getParcelableExtra(MainActivity.RECIPE_KEY);

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(RECIPE_FRAGMENT_TAG);

        if (fragment == null)
        {
            Log.d(TAG, "create new fragment");
            RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
            recipeDetailFragment.setAnInterface(this);
            int commitId = (twoPane) ? getSupportFragmentManager().beginTransaction().replace(R.id.master_recipe_container
                    , recipeDetailFragment, RECIPE_FRAGMENT_TAG).commit() :
                    getSupportFragmentManager().beginTransaction().add(R.id.recipe_detail_container, recipeDetailFragment,
                            RECIPE_FRAGMENT_TAG).commit();

        } else
        {
            Log.d(TAG, "use existing fragment");
            ((RecipeDetailFragment) fragment).setAnInterface(this);
            int commitId = (twoPane) ? getSupportFragmentManager().beginTransaction().show(fragment).commit() :
                    getSupportFragmentManager().beginTransaction().show(fragment).commit();
        }

    }

    @Override
    public void transform(Parcelable step, int position)
    {

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

        if (twoPane)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_recipe_container
                    , IngredientsDetailFragment.getIngredientsDetailFragmentInstance(ingredients)).commit();
        } else
        {
            Intent intent = new Intent(RecipDetaileActivity.this, IngredientsActivity.class);
            intent.putParcelableArrayListExtra(INGREDIENTS_KEY, (ArrayList<IngredientItem>) ingredients);
            startActivity(intent);

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
