package com.example.praktinis8;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sales_records")
public class SalesRecordEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String itemName;
    public int quantitySold;
    public long timestamp;

}
