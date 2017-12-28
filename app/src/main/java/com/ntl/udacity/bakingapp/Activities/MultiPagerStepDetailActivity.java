package com.ntl.udacity.bakingapp.Activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.ntl.udacity.bakingapp.Adapters.MultiPagerStepDetailAdapter;
import com.ntl.udacity.bakingapp.Models.Recipe;
import com.ntl.udacity.bakingapp.R;

public class MultiPagerStepDetailActivity extends AppCompatActivity
{

    private static final String CURRENT_POSITION = "current_positiom";
    private Recipe recipe;
    private int position;

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        outState.putInt(CURRENT_POSITION, position);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_pager_step_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState != null)
            position = savedInstanceState.getInt(CURRENT_POSITION);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            findViewById(R.id.navigation_buttons_steps).setVisibility(View.GONE);
            getSupportActionBar().hide();
        }
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            recipe = bundle.getParcelable(MainActivity.RECIPE_KEY);
            position = bundle.getInt(RecipDetaileActivity.STEP_POSITION);
        }
        final ViewPager viewPager = findViewById(R.id.steps_view_pager);
        MultiPagerStepDetailAdapter multiPagerStepDetailAdapter = new MultiPagerStepDetailAdapter(getSupportFragmentManager());
        multiPagerStepDetailAdapter.setRecipe(recipe);
        viewPager.setAdapter(multiPagerStepDetailAdapter);
        viewPager.setCurrentItem(position);
        Button nextStep = findViewById(R.id.next_step_btn);
        Button prevStep = findViewById(R.id.previous_step_btn);
        nextStep.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        });
        prevStep.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });
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
