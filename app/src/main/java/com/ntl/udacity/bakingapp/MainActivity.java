package com.ntl.udacity.bakingapp;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ntl.udacity.bakingapp.Models.IngredientItem;

import java.util.List;

public class MainActivity extends AppCompatActivity implements transferDataInterface
{
    public static final String RECIPE_KEY="recipe";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainFragment mainFragment = new MainFragment();
        mainFragment.setAnInterface(this);
        getSupportFragmentManager().beginTransaction().add(R.id.main_container,mainFragment).commit();
    }

    @Override
    public void transform(Parcelable recipe, int position)
    {
        Intent intent=new Intent(this,RecipDetaileActivity.class);
        intent.putExtra(RECIPE_KEY,recipe);
        startActivity(intent);
    }

    @Override
    public void transform(List<IngredientItem> ingredients)
    {

    }
}
