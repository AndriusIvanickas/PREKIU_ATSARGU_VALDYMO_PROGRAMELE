package com.example.praktinis8;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "items")
public class ItemEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String imageUri;
    public String name;
    public String description;
    public String price;
    public int quantity;
    public double costPrice;
    public boolean seasonal;
    public int minQuantity;

    public void remove(ItemEntity item) {
    }
}
