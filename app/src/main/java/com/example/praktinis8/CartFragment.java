package com.example.praktinis8;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);


        RecyclerView recyclerView = view.findViewById(R.id.cartRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        List<Dish> cartDishes = new ArrayList<>();
        cartDishes.add(new Dish(R.drawable.pizza_image, "Pizza", "", "$8.99"));
        cartDishes.add(new Dish(R.drawable.burger_image, "Hamburger", "", "$5.99"));


        CartAdapter adapter = new CartAdapter(cartDishes);
        recyclerView.setAdapter(adapter);


        Button orderButton = view.findViewById(R.id.orderButton);
        orderButton.setOnClickListener(v -> showConfirmationDialog());

        return view;
    }

    private void showConfirmationDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Confirm Order")
                .setMessage("Are you sure you want to place the order?")
                .setPositiveButton("Yes", (dialog, which) -> {

                    dialog.dismiss();
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }
}
