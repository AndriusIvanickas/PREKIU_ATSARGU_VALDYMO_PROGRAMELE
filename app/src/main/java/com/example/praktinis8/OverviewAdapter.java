package com.example.praktinis8;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class OverviewAdapter extends RecyclerView.Adapter<OverviewAdapter.OverviewViewHolder> {

    private List<ItemEntity> overviewItems;

    public OverviewAdapter(List<ItemEntity> overviewItems) {
        this.overviewItems = overviewItems;
    }

    @NonNull
    @Override
    public OverviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_over_item, parent, false);
        return new OverviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OverviewViewHolder holder, int position) {
        ItemEntity item = overviewItems.get(position);

        Picasso.get().load(Uri.parse(item.imageUri)).into(holder.imageView);
        holder.nameTextView.setText(item.name);
        holder.priceTextView.setText("Kaina: " + item.price);
        holder.quantityTextView.setText("Kiekis: " + item.quantity);
        holder.costPriceTextView.setText("Savikaina: €" + item.costPrice);
        holder.minQuantityTextView.setText("Minimalus: " + item.minQuantity);
        holder.seasonalTextView.setText("Sezoninis: " + (item.seasonal ? "Taip" : "Ne"));
    }

    @Override
    public int getItemCount() {
        return overviewItems.size();
    }

    static class OverviewViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameTextView;
        TextView priceTextView;
        TextView quantityTextView;
        TextView costPriceTextView;
        TextView minQuantityTextView;
        TextView seasonalTextView;

        public OverviewViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cartDishImage);
            nameTextView = itemView.findViewById(R.id.cartDishName);
            priceTextView = itemView.findViewById(R.id.cartDishPrice);
            quantityTextView = itemView.findViewById(R.id.cartDishQuantity);
            costPriceTextView = itemView.findViewById(R.id.cartDishCostPrice);
            minQuantityTextView = itemView.findViewById(R.id.cartDishMinQuantity);
            seasonalTextView = itemView.findViewById(R.id.cartDishSeasonal);
        }
    }
}
