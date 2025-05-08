package com.example.praktinis8;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

public class InsightFragment extends Fragment {

    private TextView insightTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_insight, container, false);

        insightTextView = view.findViewById(R.id.insightText);

        displaySalesData();

        return view;
    }


    public void refreshInsight() {
        if (insightTextView != null) {
            displaySalesData();
        }
    }
    private void displaySalesData() {
        List<SalesRecordEntity> allSales = ItemRepository.getSales();
        List<String> recommendations = ItemRepository.getOrderRecommendations();

        StringBuilder salesText = new StringBuilder();

        salesText.append("Sales:\n");

        if (allSales.isEmpty()) {
            salesText.append("No sales yet.\n");
        } else {
            long currentTime = System.currentTimeMillis();
            long oneWeekMillis = 7L * 24L * 60L * 60L * 1000L;

            for (SalesRecordEntity record : allSales) {
                if (currentTime - record.timestamp <= oneWeekMillis) {
                    salesText.append("Sold ")
                            .append(record.quantitySold)
                            .append(" x ")
                            .append(record.itemName)
                            .append("\n");
                }
            }
        }

        salesText.append("\n Order Recommendations:\n");
        for (String recommendation : recommendations) {
            salesText.append(recommendation).append("\n");
        }

        insightTextView.setText(salesText.toString());
    }

}
