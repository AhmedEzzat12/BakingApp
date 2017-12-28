package com.ntl.udacity.bakingapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ntl.udacity.bakingapp.Models.IngredientItem;
import com.ntl.udacity.bakingapp.R;

import java.util.List;


public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>
{

    private List<IngredientItem> ingredientItems;

    public IngredientsAdapter(List<IngredientItem> ingredientItems)
    {
        this.ingredientItems = ingredientItems;
    }

    @Override
    public IngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new IngredientsViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_item, parent, false));
    }

    @Override
    public void onBindViewHolder(IngredientsViewHolder holder, int position)
    {

        holder.quantity.setText(ingredientItems.get(position).getQuantity());
        holder.measure.setText(ingredientItems.get(position).getMeasure());
        holder.ingredient.setText(ingredientItems.get(position).getIngredient());

    }

    @Override
    public int getItemCount()
    {
        if (ingredientItems != null)
            return ingredientItems.size();
        return 0;
    }


    public class IngredientsViewHolder extends RecyclerView.ViewHolder
    {
        TextView quantity;
        TextView measure;
        TextView ingredient;

        public IngredientsViewHolder(View itemView)
        {
            super(itemView);
            quantity = itemView.findViewById(R.id.quanitiy_tv);
            measure = itemView.findViewById(R.id.measure_tv);
            ingredient = itemView.findViewById(R.id.ingredient_tv);

        }
    }
}
