package com.ntl.udacity.bakingapp.Widget;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.ntl.udacity.bakingapp.R;

public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory
{

    private static final String TAG = ListRemoteViewsFactory.class.getSimpleName();
    private String[] quantity;
    private String[] measure;
    private String[] ingredient;
    private Context context;
    private Intent intent;

    public ListRemoteViewsFactory(Context context, Intent intent)
    {
        this.intent = intent;
        this.context = context;
    }

    @Override
    public void onCreate()
    {
        getDataFromIntent();
    }

    private void getDataFromIntent()
    {

        this.quantity = intent.getStringArrayExtra(RecipesWidget.QUANTITY);
        this.measure = intent.getStringArrayExtra(RecipesWidget.MEASURE);
        this.ingredient = intent.getStringArrayExtra(RecipesWidget.INGREDIENT);
        Log.d(TAG, String.valueOf(quantity[0]));
        Log.d(TAG, String.valueOf(measure[0]));
        Log.d(TAG, String.valueOf(ingredient[0]));
    }

    @Override
    public void onDataSetChanged()
    {
        getDataFromIntent();
    }

    @Override
    public void onDestroy()
    {

    }

    @Override
    public int getCount()
    {
        if (quantity == null)
            return 0;
        return quantity.length;
    }

    @Override
    public RemoteViews getViewAt(int position)
    {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_item);
        views.setTextViewText(R.id.quanitiy_tv, quantity[position]);
        views.setTextViewText(R.id.measure_tv, measure[position]);
        views.setTextViewText(R.id.ingredient_tv, ingredient[position]);
        //Log.d(TAG + " getViewAt ", quantity[position]);
        return views;
    }

    @Override
    public RemoteViews getLoadingView()
    {
        return null;
    }

    @Override
    public int getViewTypeCount()
    {
        return 1;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public boolean hasStableIds()
    {
        return true;
    }
}
