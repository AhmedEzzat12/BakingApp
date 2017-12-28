package com.ntl.udacity.bakingapp.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ntl.udacity.bakingapp.Models.Step;

import java.util.List;


public class stepsAdapter extends RecyclerView.Adapter<stepsAdapter.stepsViewHolder>
{
    private static final String TAG = stepsAdapter.class.getSimpleName();
    private CardsAdapter.MOnItemListener mOnItemListener;
    private List<Step> steps;

    public stepsAdapter(@NonNull List<Step> objects, CardsAdapter.MOnItemListener mOnItemListener)
    {
        this.mOnItemListener = mOnItemListener;
        this.steps = objects;
    }


    @Override
    public stepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new stepsViewHolder(LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false));
    }

    @Override
    public void onBindViewHolder(final stepsViewHolder holder, int position)
    {
        holder.shortDesc.setText(steps.get(position).getShortDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mOnItemListener.onListItemClick(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount()
    {
        if (steps != null)
            return steps.size();
        return 0;
    }


    class stepsViewHolder extends RecyclerView.ViewHolder
    {
        TextView shortDesc;

        public stepsViewHolder(View itemView)
        {

            super(itemView);
            shortDesc = itemView.findViewById(android.R.id.text1);
        }
    }
}


