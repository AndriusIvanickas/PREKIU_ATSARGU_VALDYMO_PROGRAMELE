package com.example.praktinis8;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Dish> dishes = new ArrayList<>();
        dishes.add(new Dish(R.drawable.pizza_image, "Pizza", "test1", "100"));
        dishes.add(new Dish(R.drawable.burger_image, "Hamburger", "Borgir", "200"));
        dishes.add(new Dish(R.drawable.salad_image, "Salad", "test", "300"));

        DishAdapter adapter = new DishAdapter(dishes);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
