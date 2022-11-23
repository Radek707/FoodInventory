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

import java.util.List;

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
    }

    @Override
    public int getItemCount() {
        return foodProductList.size();
    }

    static class FoodProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView foodProductNameTextView;
        CardView foodProductCardView;
        View foodProductLayout;

        OnFoodProductClickListener onFoodProductClickListener;

        public FoodProductViewHolder(@NonNull View itemView, OnFoodProductClickListener onFoodProductClickListener) {
            super(itemView);

            foodProductNameTextView = itemView.findViewById(R.id.foodProductNameTextView);
            foodProductCardView = itemView.findViewById(R.id.foodProductCardView);
            foodProductLayout = itemView.findViewById(R.id.foodProductLayout);

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
