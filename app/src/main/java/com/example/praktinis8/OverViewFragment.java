package com.example.praktinis8;

import android.os.Bundle;
import android.util.Log;
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

public class OverViewFragment extends Fragment {

    private List<ItemEntity> filteredItems = new ArrayList<>();
    private OverviewAdapter adapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        recyclerView = view.findViewById(R.id.overviewRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new OverviewAdapter(filteredItems);
        recyclerView.setAdapter(adapter);

        refreshOverview();
        return view;
    }

    public void refreshOverview() {
        if (filteredItems != null && adapter != null) {
            List<ItemEntity> allItems = ItemRepository.getItems();
            Log.d("OverViewFragment", "Pulled " + allItems.size() + " items from DB");

            filteredItems.clear();

            for (ItemEntity item : allItems) {
                Log.d("OverViewFragment", item.name + ": Q=" + item.quantity + ", MinQ=" + item.minQuantity);

                if (item.quantity <= item.minQuantity) {
                    filteredItems.add(item);
                    Log.d("OverViewFragment", "ADDED to filtered: " + item.name);
                }
            }

            Log.d("OverViewFragment", "Filtered size: " + filteredItems.size());

            adapter.notifyDataSetChanged();
        }
    }}
