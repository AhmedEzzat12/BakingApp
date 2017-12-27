package com.ntl.udacity.bakingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ntl.udacity.bakingapp.Models.Recipe;
import com.ntl.udacity.bakingapp.R;

import java.util.List;


public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.ViewHolder>
{
    private List<Recipe> recipes;
    private MOnItemListener MOnItemListener;
    private Context context;

    public CardsAdapter(List<Recipe> recipes, MOnItemListener MOnItemListener, Context context)
    {
        this.recipes = recipes;
        this.MOnItemListener = MOnItemListener;
        this.context = context;
    }
    public void setRecipes(List<Recipe> recipes)
    {
        this.recipes=recipes;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_card_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.recipeName.setText(recipes.get(position).getName());
        Log.d("adapter bind",recipes.get(position).getName());
        if (!TextUtils.isEmpty(recipes.get(position).getImage()))
        {
            holder.recipeImage.setVisibility(View.VISIBLE);
            Glide.with(context).load(recipes.get(position).getImage()).into(holder.recipeImage);
        }

    }

    @Override
    public int getItemCount()
    {
        if (recipes != null)
        {
            return recipes.size();
        }
        return 0;
    }

    public interface MOnItemListener
    {
        void onListItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView recipeName;
        private ImageView recipeImage;

        public ViewHolder(View itemView)
        {
            super(itemView);
            recipeName = itemView.findViewById(R.id.recipe_name);
            recipeImage = itemView.findViewById(R.id.recipe_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            MOnItemListener.onListItemClick(getAdapterPosition());
        }
    }
}
