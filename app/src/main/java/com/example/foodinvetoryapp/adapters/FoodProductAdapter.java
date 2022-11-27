package com.example.foodinvetoryapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodinvetoryapp.R;
import com.example.foodinvetoryapp.models.FoodProduct;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FoodProductAdapter extends RecyclerView.Adapter<FoodProductAdapter.FoodProductViewHolder>{

    private Context context;
    private List<FoodProduct> foodProductList;
    private OnFoodProductClickListener mOnFoodProductClickListener;

    public FoodProductAdapter(Context context, List<FoodProduct> foodProductList, OnFoodProductClickListener mOnFoodProductClickListener) {
        this.context = context;
        this.foodProductList = foodProductList;
        this.mOnFoodProductClickListener = mOnFoodProductClickListener;
    }

    @NonNull
    @Override
    public FoodProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FoodProductViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.food_product_layout, parent, false), mOnFoodProductClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodProductViewHolder holder, int position) {
        FoodProduct foodProduct = foodProductList.get(position);
        holder.foodProductNameTextView.setText(foodProduct.getName());
        if (foodProduct.getNutrientScore() != null) {
            holder.nutrientScoreTextView.setVisibility(View.VISIBLE);
            holder.nutrientScoreTextView.setText("Nutrient score: " + foodProduct.getNutrientScore().toUpperCase(Locale.ROOT));
        } else {
            holder.nutrientScoreTextView.setVisibility(View.GONE);
        }
        if (foodProduct.getExpireDate() != null) {
            holder.daysToExpireTextView.setVisibility(View.VISIBLE);
            holder.daysToExpireTextView.setText(daysToExpire(foodProduct));
        } else {
            holder.daysToExpireTextView.setVisibility(View.INVISIBLE);
        }
    }

    private String daysToExpire(FoodProduct foodProduct) {
        Date date = new Date();
        long today = date.getTime();
        long expireDay = foodProduct.getExpireDate().getTime();
        return String.valueOf((expireDay - today) / 1000 / 60 / 60 / 24);
    }

    @Override
    public int getItemCount() {
        return foodProductList.size();
    }

    static class FoodProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView foodProductNameTextView, nutrientScoreTextView, daysToExpireTextView;
        CardView foodProductCardView;
        View foodProductLayout, foodProductConstraintLayout;

        OnFoodProductClickListener onFoodProductClickListener;

        public FoodProductViewHolder(@NonNull View itemView, OnFoodProductClickListener onFoodProductClickListener) {
            super(itemView);

            foodProductNameTextView = itemView.findViewById(R.id.foodProductNameTextView);
            foodProductCardView = itemView.findViewById(R.id.foodProductCardView);
            foodProductLayout = itemView.findViewById(R.id.foodProductLayout);
            nutrientScoreTextView = itemView.findViewById(R.id.nutrientScoreTextView);
            foodProductConstraintLayout = itemView.findViewById(R.id.foodProductConstraintLayout);
            daysToExpireTextView = itemView.findViewById(R.id.daysToExpireTextView);

            this.onFoodProductClickListener = onFoodProductClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onFoodProductClickListener.onClick(getAdapterPosition());
        }
    }

    public interface OnFoodProductClickListener {
        void onClick(int position);
    }
}
