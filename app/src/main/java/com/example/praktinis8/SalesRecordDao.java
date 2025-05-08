package com.example.praktinis8;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SalesRecordDao {
    @Query("SELECT * FROM sales_records")
    List<SalesRecordEntity> getAllSales();

    @Insert
    void insertSale(SalesRecordEntity sale);

    @Query("DELETE FROM sales_records")
    void clearSales();
}
