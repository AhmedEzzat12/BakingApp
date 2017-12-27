package com.ntl.udacity.bakingapp;


import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

public class IdlingResourceTest implements IdlingResource
{
    @Nullable
    private volatile ResourceCallback resourceCallback;
    private AtomicBoolean isIdle = new AtomicBoolean(true);

    @Override
    public String getName()
    {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow()
    {
        return isIdle.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback)
    {
        this.resourceCallback = callback;
    }

    public void setIdleState(boolean isIdleNow)
    {
        isIdle.set(isIdleNow);
        if (isIdleNow && resourceCallback != null)
        {
            resourceCallback.onTransitionToIdle();
        }
    }

}
