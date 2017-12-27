package com.ntl.udacity.bakingapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ntl.udacity.bakingapp.Models.IngredientItem;
import com.ntl.udacity.bakingapp.R;

import java.util.List;


public class ingredientsAdapter extends ArrayAdapter<IngredientItem>
{

    public ingredientsAdapter(@NonNull Context context, @NonNull List<IngredientItem> objects)
    {
        super(context,0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        IngredientItem ingredientItem=getItem(position);
        if(convertView==null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ingredient_item, parent, false);

        TextView quantity=convertView.findViewById(R.id.quanitiy_tv);
        TextView measure=convertView.findViewById(R.id.measure_tv);
        TextView ingredient=convertView.findViewById(R.id.ingredient_tv);
        quantity.setText(ingredientItem != null ? ingredientItem.getQuantity() : "0");
        measure.setText(ingredientItem != null ? ingredientItem.getMeasure() : "0");
        ingredient.setText(ingredientItem != null ? ingredientItem.getIngredient() : "0");
        return convertView;
    }
}
