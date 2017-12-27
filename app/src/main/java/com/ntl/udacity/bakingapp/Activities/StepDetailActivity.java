package com.ntl.udacity.bakingapp.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ntl.udacity.bakingapp.Fragments.StepDetailFragment;
import com.ntl.udacity.bakingapp.Models.Step;
import com.ntl.udacity.bakingapp.R;

public class StepDetailActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        Bundle bundle=getIntent().getExtras();

        if (bundle != null)
        {
            Step step = bundle.getParcelable(RecipDetaileActivity.STEP_KEY);
            getSupportFragmentManager().beginTransaction().add(R.id.detail_recipe_container
                    , StepDetailFragment.getStepDetailFragmentInstance(step)).commit();

        }


    }
}
