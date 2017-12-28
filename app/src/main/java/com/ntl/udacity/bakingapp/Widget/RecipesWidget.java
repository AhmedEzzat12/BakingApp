package com.ntl.udacity.bakingapp.Widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.ntl.udacity.bakingapp.Models.IngredientItem;
import com.ntl.udacity.bakingapp.R;

import java.util.ArrayList;



public class RecipesWidget extends AppWidgetProvider
{
    public static final String QUANTITY = "quantity";
    public static final String MEASURE = "measure";
    public static final String INGREDIENT = "ingredient";
    private static final String ACTION_UPDATE_INGREDIENTS = "com.ntl.udacity.bakingapp.action.ACTION_UPDATE_INGREDIENTS";
    private static final String EXTRA_RECIPES = "recipes";

    public void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int[] appWidgetId, ArrayList<IngredientItem> ingredientItems)
    {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipes_widget);
        // Set the GridWidgetService intent to act as the adapter for the GridView
        Intent intent = new Intent(context, ListWidgetService.class);
        //intent.putParcelableArrayListExtra(IngredientsWidgetService.EXTRA_RECIPES,ingredientItems);
        String[] quantity = new String[ingredientItems.size()];
        String[] measure = new String[ingredientItems.size()];
        String[] ingredient = new String[ingredientItems.size()];
        for (int i = 0; i < ingredientItems.size(); ++i)
        {
            quantity[i] = ingredientItems.get(i).getQuantity();
            measure[i] = ingredientItems.get(i).getMeasure();
            ingredient[i] = ingredientItems.get(i).getIngredient();
        }
        intent.putExtra(QUANTITY, quantity);
        intent.putExtra(MEASURE, measure);
        intent.putExtra(INGREDIENT, ingredient);
        views.setRemoteAdapter(R.id.appwidget_list, intent);
        views.setEmptyView(R.id.appwidget_list, R.id.empty_recipes);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {

    }

    @Override
    public void onEnabled(Context context)
    {

    }

    @Override
    public void onDisabled(Context context)
    {

    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        super.onReceive(context, intent);
        Bundle extras = intent.getExtras();
        if (ACTION_UPDATE_INGREDIENTS.equals(intent.getAction()))
        {
            if (extras != null)
            {
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                ComponentName thisAppWidget = new ComponentName(context.getPackageName(), RecipesWidget.class.getName());
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);
                ArrayList<IngredientItem> ingredientItems = intent.getParcelableArrayListExtra(EXTRA_RECIPES);
                updateAppWidget(context, appWidgetManager, appWidgetIds, ingredientItems);

            }
        }
    }
}

