package com.ntl.udacity.bakingapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;

import com.ntl.udacity.bakingapp.Fragments.MainFragment;
import com.ntl.udacity.bakingapp.Interfaces.transferDataInterface;
import com.ntl.udacity.bakingapp.Models.IngredientItem;
import com.ntl.udacity.bakingapp.R;

import java.util.List;

public class MainActivity extends AppCompatActivity implements transferDataInterface
{
    public static final String RECIPE_KEY = "recipe";
    private static final String MAIN_FRAGMENT_TAG = "main_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android.support.v4.app.Fragment fragment = getSupportFragmentManager().findFragmentByTag(MAIN_FRAGMENT_TAG);
        if (fragment == null)
        {
            MainFragment mainFragment = new MainFragment();
            mainFragment.setAnInterface(this);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, mainFragment, MAIN_FRAGMENT_TAG).commit();
        } else
        {
            ((MainFragment) fragment).setAnInterface(this);
            getSupportFragmentManager().beginTransaction().show(fragment).commit();
        }

    }

    @Override
    public void transform(Parcelable recipe, int position)
    {
        Intent intent = new Intent(this, RecipDetaileActivity.class);
        intent.putExtra(RECIPE_KEY, recipe);
        startActivity(intent);
    }

    @Override
    public void transform(List<IngredientItem> ingredients)
    {

    }


}
