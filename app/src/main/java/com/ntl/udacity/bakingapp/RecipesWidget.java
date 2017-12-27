package com.ntl.udacity.bakingapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.ntl.udacity.bakingapp.Models.IngredientItem;

import java.util.ArrayList;


public class RecipesWidget extends AppWidgetProvider
{

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int[] appWidgetId, ArrayList<IngredientItem> ingredientItems)
    {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipes_widget);
        // Set the GridWidgetService intent to act as the adapter for the GridView
        Intent intent = new Intent(context, ListWidgetService.class);
        intent.putParcelableArrayListExtra(IngredientsWidgetService.EXTRA_RECIPES,ingredientItems);
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

    }
}

