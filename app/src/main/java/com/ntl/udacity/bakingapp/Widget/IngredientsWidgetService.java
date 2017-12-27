package com.ntl.udacity.bakingapp.Widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.ntl.udacity.bakingapp.Models.IngredientItem;
import com.ntl.udacity.bakingapp.R;

import java.util.ArrayList;


public class IngredientsWidgetService extends IntentService
{

    public static final String EXTRA_RECIPES = "com.ntl.udacity.bakingapp.EXTRA_RECIPES";
    private static final String ACTION_UPDATE_INGREDIENTS = "com.ntl.udacity.bakingapp.action.ACTION_UPDATE_INGREDIENTS";


    public IngredientsWidgetService()
    {
        super("Update Widget");
    }

    public static void startActionUpdateIngredients(Context context, ArrayList<IngredientItem> ingredientItems)
    {
        Intent intent = new Intent(context, IngredientsWidgetService.class);
        intent.setAction(ACTION_UPDATE_INGREDIENTS);
        intent.putParcelableArrayListExtra(EXTRA_RECIPES, ingredientItems);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        if (intent != null)
        {
            final String action = intent.getAction();
            if (ACTION_UPDATE_INGREDIENTS.equals(action))
            {
                ArrayList<IngredientItem> ingredientItems = intent.getParcelableArrayListExtra(EXTRA_RECIPES);
                handleActionUpdateIngredients(ingredientItems);
            }
        }
    }

    private void handleActionUpdateIngredients(ArrayList<IngredientItem> ingredientItems)
    {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipesWidget.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.appwidget_list);
        RecipesWidget.updateAppWidget(this, appWidgetManager, appWidgetIds, ingredientItems);
    }

}
