package com.ntl.udacity.bakingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ntl.udacity.bakingapp.Models.Step;

import java.util.List;

import static com.ntl.udacity.bakingapp.CardsAdapter.*;


public class stepsAdapter extends ArrayAdapter<Step>
{
    private int position;
    private MOnItemListener mOnItemListener;
    private static final String TAG=stepsAdapter.class.getSimpleName();
    private Step step;

    public stepsAdapter(@NonNull Context context, @NonNull List<Step> objects,MOnItemListener  mOnItemListener)
    {
        super(context,0, objects);
        this.mOnItemListener=mOnItemListener;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent)
    {
        step = getItem(position);
        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        TextView shortDesc = convertView.findViewById(android.R.id.text1);
        shortDesc.setText(step != null ? step.getShortDescription() : null);
        convertView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mOnItemListener.onListItemClick(position);
            }
        });
        return convertView;
    }
}


