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

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<ItemEntity> items;
    private OnItemClickListener clickListener;
    private OnItemLongClickListener longClickListener;

    // Constructor
    public ItemAdapter(List<ItemEntity> items) {
        this.items = items;
    }

    // ViewHolder
    static class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameTextView;
        TextView descriptionTextView;
        TextView priceTextView;
        TextView quantityTextView;
        TextView costPriceTextView;
        TextView minQuantityTextView;
        TextView seasonalTextView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.dish_image);
            nameTextView = itemView.findViewById(R.id.dish_name);
            descriptionTextView = itemView.findViewById(R.id.dish_description);
            priceTextView = itemView.findViewById(R.id.dish_price);
            quantityTextView = itemView.findViewById(R.id.dish_quantity);
            costPriceTextView = itemView.findViewById(R.id.dish_cost_price);
            minQuantityTextView = itemView.findViewById(R.id.dish_min_quantity);
            seasonalTextView = itemView.findViewById(R.id.dish_seasonal);
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ItemEntity item = items.get(position);

        holder.nameTextView.setText(item.name);
        holder.descriptionTextView.setText(item.description);
        holder.priceTextView.setText("Kaina: " + item.price);
        holder.quantityTextView.setText("Kiekis: " + item.quantity);
        holder.costPriceTextView.setText("Savikaina: €" + item.costPrice);
        holder.minQuantityTextView.setText("Minimalus kiekis: " + item.minQuantity);
        holder.seasonalTextView.setText("Sezoninis: " + (item.seasonal ? "Taip" : "Ne"));
        Picasso.get().load(Uri.parse(item.imageUri)).into(holder.imageView);


        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onItemClick(items.get(position));
            }
        });


        holder.itemView.setOnLongClickListener(v -> {
            if (longClickListener != null) {
                longClickListener.onItemLongClick(items.get(position));
                return true;
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public interface OnItemClickListener {
        void onItemClick(ItemEntity item);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.clickListener = listener;
    }


    public interface OnItemLongClickListener {
        void onItemLongClick(ItemEntity item);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.longClickListener = listener;
    }
    public void updateItems(List<ItemEntity> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

}
