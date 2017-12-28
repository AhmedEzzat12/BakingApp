package com.ntl.udacity.bakingapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.ntl.udacity.bakingapp.Adapters.CardsAdapter;
import com.ntl.udacity.bakingapp.IdlingResourceTest;
import com.ntl.udacity.bakingapp.Interfaces.transferDataInterface;
import com.ntl.udacity.bakingapp.Models.Recipe;
import com.ntl.udacity.bakingapp.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment implements CardsAdapter.MOnItemListener
{
    private static final String TAG = MainFragment.class.getSimpleName();
    private static final String RECIPES_KEY = "recipes";
    private static final String POSITION_KEY = "position_key";
    private CardsAdapter cardsAdapter;
    private List<Recipe> recipeList;
    private Context context;
    private transferDataInterface anInterface;
    private IdlingResourceTest idlingResourceTest;
    private Parcelable layoutManagerState;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private int scrollPosition;
    private boolean isTablet;
    private TextView emptyView;

    public void setAnInterface(transferDataInterface anInterface)
    {
        this.anInterface = anInterface;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        setHasOptionsMenu(true);
        if (savedInstanceState != null)
        {
            scrollPosition = savedInstanceState.getInt(POSITION_KEY);
            Log.d(TAG + "restored", String.valueOf(scrollPosition));

        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState)
    {
        outState.putInt(POSITION_KEY, scrollPosition);
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onStop()
    {
        if (!isTablet)
            scrollPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        Log.d(TAG, String.valueOf(scrollPosition));
        super.onStop();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        View v = inflater.inflate(R.layout.fragment_main, container, false);
        cardsAdapter = new CardsAdapter(recipeList, this, inflater.getContext());
        emptyView = v.findViewById(R.id.main_empty_view);
        recyclerView = v.findViewById(R.id.main_recyclerview);
        recyclerView.setHasFixedSize(true);
        isTablet = getResources().getBoolean(R.bool.isTablet);
        if (isTablet)
        {
            layoutManager = new GridLayoutManager(inflater.getContext(), 3);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(cardsAdapter);

        } else
        {
            layoutManager = new LinearLayoutManager(inflater.getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(cardsAdapter);
        }
        recyclerView.scrollToPosition(scrollPosition);
        return v;
    }

    private void getRecipes()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.d(TAG, response);
                emptyView.setVisibility(View.GONE);
                parseResponse(response);

                if (idlingResourceTest != null)
                {
                    idlingResourceTest.setIdleState(true);
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.e(TAG, error.toString());
                if (idlingResourceTest != null)
                {
                    idlingResourceTest.setIdleState(true);
                }

            }
        });
        requestQueue.add(request);
    }

    private void parseResponse(String response)
    {
        Gson gson = new GsonBuilder().create();
        try
        {
            Type listType = new TypeToken<ArrayList<Recipe>>()
            {
            }.getType();
            recipeList = gson.fromJson(response, listType);
            cardsAdapter.setRecipes(recipeList);
        } catch (JsonSyntaxException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onListItemClick(int position)
    {
        anInterface.transform(recipeList.get(position), position);
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource()
    {
        if (idlingResourceTest == null)
        {
            idlingResourceTest = new IdlingResourceTest();
        }
        return idlingResourceTest;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.refresh_list:
            {
                if (idlingResourceTest != null)
                {
                    idlingResourceTest.setIdleState(false);
                }
                getRecipes();
                return true;
            }
            default:
                return false;
        }

    }

}
