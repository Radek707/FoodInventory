package com.example.foodinvetoryapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodinvetoryapp.R;
import com.example.foodinvetoryapp.StorageDetailsActivity;
import com.example.foodinvetoryapp.TAG;
import com.example.foodinvetoryapp.models.FoodProduct;
import com.example.foodinvetoryapp.models.Storage;

import java.util.List;


public class StorageAdapter extends RecyclerView.Adapter<StorageAdapter.StorageViewHolder> {

    private Context context;
    private List<Storage> storages;

    public StorageAdapter(Context context, List<Storage> storages) {
        this.context = context;
        this.storages = storages;
    }

    @NonNull
    @Override
    public StorageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StorageViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.storage_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StorageViewHolder holder, int position) {
        Storage storage = storages.get(position);
        holder.storageTextView.setText(storage.getStorageName());
        int productCount = storage.getFoodProducts().size();
        if (productCount > 0) {
            holder.productsCountTextView.setText(String.valueOf(productCount));
        } else {
            holder.productsCountTextView.setText("");
        }
        holder.storageConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StorageDetailsActivity.class);
                long id = storage.getId();
                intent.putExtra(TAG.STORAGE_ID, id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return storages.size();
    }

    static class StorageViewHolder extends RecyclerView.ViewHolder {
        CardView storageCardView;
        TextView storageTextView, productsCountTextView;
        View storageLayout, storageConstraintLayout;

        public StorageViewHolder(@NonNull View itemView) {
            super(itemView);

            storageCardView = itemView.findViewById(R.id.storageCardView);
            storageLayout = itemView.findViewById(R.id.storageLayout);
            storageTextView = itemView.findViewById(R.id.storageTextView);
            productsCountTextView = itemView.findViewById(R.id.productsCountTextView);
            storageConstraintLayout = itemView.findViewById(R.id.storageConstraintLayout);
        }
    }
}
