package com.example.praktinis8;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Dish> cartDishes;

    public CartAdapter(List<Dish> cartDishes) {
        this.cartDishes = cartDishes;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_dish, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Dish dish = cartDishes.get(position);
        holder.imageView.setImageResource(dish.getImageResource());
        holder.nameTextView.setText(dish.getName());
        holder.priceTextView.setText(dish.getPrice());
    }

    @Override
    public int getItemCount() {
        return cartDishes.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameTextView;
        TextView priceTextView;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cartDishImage);
            nameTextView = itemView.findViewById(R.id.cartDishName);
            priceTextView = itemView.findViewById(R.id.cartDishPrice);
        }
    }
}
