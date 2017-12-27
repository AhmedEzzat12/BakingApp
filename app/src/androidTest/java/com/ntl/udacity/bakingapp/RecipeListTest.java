package com.ntl.udacity.bakingapp;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.ntl.udacity.bakingapp.Activities.MainActivity;
import com.ntl.udacity.bakingapp.Fragments.MainFragment;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecipeListTest
{

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    private IdlingResource idlingResource;
    private MainFragment mainFragment;

    @Before
    public void registerFragment()
    {
        mActivityTestRule.getActivity().runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                MainFragment mainFragment = getMainFragment();
                registerIdlingResource();
            }
        });
    }

    public void registerIdlingResource()
    {
        idlingResource = mainFragment.getIdlingResource();
        Espresso.registerIdlingResources(idlingResource);
    }

    @Test
    public void clickOnItem()
    {
        onView(withId(R.id.refresh_list)).perform(click());
        onView(withId(R.id.main_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.fragment_recipe_detail))
                .check(matches(isDisplayed()));
    }

    public MainFragment getMainFragment()
    {
        if (mainFragment != null)
            return mainFragment;

        mainFragment = new MainFragment();
        mainFragment.setAnInterface(mActivityTestRule.getActivity());
        mActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, mainFragment).commit();

        return mainFragment;
    }

    @After
    public void unregisterIdlingResource()
    {
        if (idlingResource != null)
        {
            Espresso.unregisterIdlingResources(idlingResource);
        }
    }

}
