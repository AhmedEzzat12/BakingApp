package com.ntl.udacity.bakingapp.Widget;


import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;


public class ListWidgetService extends RemoteViewsService
{

    private static final String TAG = ListWidgetService.class.getSimpleName();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent)
    {
        Log.d(TAG, "onGetViewFactory called");
        return new ListRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}


