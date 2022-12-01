package com.example.foodinvetoryapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodinvetoryapp.R;
import com.example.foodinvetoryapp.models.FoodProduct;
import com.example.foodinvetoryapp.models.Storage;

import java.util.List;


public class StorageAdapter extends RecyclerView.Adapter<StorageAdapter.StorageViewHolder> {

    private Context context;
    private List<Storage> storages;
    private OnStorageClickListener mOnStorageClickListener;

    public StorageAdapter(Context context, List<Storage> storages, OnStorageClickListener mOnStorageClickListener) {
        this.context = context;
        this.storages = storages;
        this.mOnStorageClickListener = mOnStorageClickListener;
    }

    @NonNull
    @Override
    public StorageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StorageViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.storage_layout, parent, false), mOnStorageClickListener);
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
    }

    @Override
    public int getItemCount() {
        return storages.size();
    }

    static class StorageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView storageCardView;
        TextView storageTextView, productsCountTextView;
        View storageLayout, storageConstraintLayout;

        OnStorageClickListener onStorageClickListener;

        public StorageViewHolder(@NonNull View itemView, OnStorageClickListener onStorageClickListener) {
            super(itemView);

            storageCardView = itemView.findViewById(R.id.storageCardView);
            storageLayout = itemView.findViewById(R.id.storageLayout);
            storageTextView = itemView.findViewById(R.id.storageTextView);
            productsCountTextView = itemView.findViewById(R.id.productsCountTextView);
            storageConstraintLayout = itemView.findViewById(R.id.storageConstraintLayout);

            this.onStorageClickListener = onStorageClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onStorageClickListener.onClick(getAdapterPosition());
        }
    }

    public interface OnStorageClickListener {
        void onClick(int position);
    }
}
