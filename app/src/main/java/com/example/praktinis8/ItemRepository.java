package com.example.praktinis8;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemRepository {

    private static AppDatabase database;


    public static void init(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "item-database"
                    )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries() // only for testing — remove later
                    .build();
        }
    }



    public static List<ItemEntity> getItems() {
        return database.itemDao().getAllItems();
    }

    public static void addItem(ItemEntity item) {
        long id = database.itemDao().insertItem(item);
        item.id = (int) id;
    }

    public static void updateItem(ItemEntity item) {
        int rows = database.itemDao().updateItem(item);
        Log.d("ItemRepository", "Updated item rows = " + rows);
    }

    public static void deleteItem(ItemEntity item) {
        database.itemDao().deleteItem(item);
    }

    public static void clearItems() {
        database.itemDao().clearItems();
    }



    public static List<SalesRecordEntity> getSales() {
        return database.salesRecordDao().getAllSales();
    }

    public static void recordSale(SalesRecordEntity sale) {
        database.salesRecordDao().insertSale(sale);
    }

    public static void clearSales() {
        database.salesRecordDao().clearSales();
    }


    public static void startNextDay() {
        clearSales();
    }



    public static List<String> getOrderRecommendations() {
        List<String> recommendations = new ArrayList<>();

        List<SalesRecordEntity> allSales = database.salesRecordDao().getAllSales();
        List<ItemEntity> allItems = database.itemDao().getAllItems();

        if (allSales.isEmpty()) {
            recommendations.add("No sales history available for recommendation.");
            return recommendations;
        }


        Map<String, Integer> totalSoldMap = new HashMap<>();

        long currentTime = System.currentTimeMillis();
        long oneWeekMillis = 7L * 24L * 60L * 60L * 1000L;

        for (SalesRecordEntity sale : allSales) {
            if (currentTime - sale.timestamp <= oneWeekMillis) {
                totalSoldMap.put(sale.itemName, totalSoldMap.getOrDefault(sale.itemName, 0) + sale.quantitySold);
            }
        }

        for (ItemEntity item : allItems) {
            String name = item.name;
            int sold = totalSoldMap.getOrDefault(name, 0);
            int initialStock = item.quantity + sold;

            if (initialStock == 0) {
                recommendations.add(" " + name + ": No stock data available.");
                continue;
            }

            double soldPercentage = (sold * 100.0) / initialStock;

            if (soldPercentage > 50) {
                recommendations.add(" " + name + ": High demand! Sold " + String.format("%.1f", soldPercentage) + "% of stock. Recommend ordering extra.");
                recommendations.add("   ");
            } else if (soldPercentage >= 20) {
                recommendations.add(" " + name + ": Normal demand. Sold " + String.format("%.1f", soldPercentage) + "% of stock. Maintain usual order.");
                recommendations.add("   ");
            } else {
                recommendations.add(" " + name + ": Low demand. Sold only " + String.format("%.1f", soldPercentage) + "% of stock. Consider ordering less.");
                recommendations.add("   ");
            }
        }

        return recommendations;
    }
}
