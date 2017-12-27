package com.ntl.udacity.bakingapp;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.ntl.udacity.bakingapp.Models.IngredientItem;

import java.util.List;

import static com.ntl.udacity.bakingapp.IngredientsWidgetService.EXTRA_RECIPES;


public class ListWidgetService extends RemoteViewsService
{

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent)
    {
        return new ListRemoteViewsFactory(this.getApplicationContext(),intent);
    }
}
    class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory
    {

        private static final String TAG = ListRemoteViewsFactory.class.getSimpleName();
        private List<IngredientItem> ingredientItems;
        private Context context;

        public ListRemoteViewsFactory(Context context, Intent intent)
        {
            this.ingredientItems= intent.getParcelableArrayListExtra(EXTRA_RECIPES);
            this.context = context;
            Log.d(TAG, String.valueOf(ingredientItems.size()));

        }

        @Override
        public void onCreate()
        {

        }

        @Override
        public void onDataSetChanged()
        {

        }

        @Override
        public void onDestroy()
        {

        }

        @Override
        public int getCount()
        {
            if(ingredientItems==null)
                return 0;
            return ingredientItems.size();
        }

        @Override
        public RemoteViews getViewAt(int position)
        {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_item);
            views.setTextViewText(R.id.quanitiy_tv,ingredientItems.get(position).getQuantity());
            views.setTextViewText(R.id.measure_tv,ingredientItems.get(position).getMeasure());
            views.setTextViewText(R.id.ingredient_tv,ingredientItems.get(position).getIngredient());
            Log.d(TAG+" getViewAt ", ingredientItems.get(position).getQuantity());
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


