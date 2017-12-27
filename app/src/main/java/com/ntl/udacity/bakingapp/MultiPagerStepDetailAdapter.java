package com.ntl.udacity.bakingapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ntl.udacity.bakingapp.Models.Recipe;


public class MultiPagerStepDetailAdapter extends FragmentPagerAdapter
{
    private Recipe recipe;

    public void setRecipe(Recipe recipe)
    {
        this.recipe = recipe;
    }

    public MultiPagerStepDetailAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        return StepDetailFragment.getStepDetailFragmentInstance(recipe.getSteps().get(position));
    }

    @Override
    public int getCount()
    {
        if (recipe != null)
            return recipe.getSteps().size();
        return 0;
    }
}
