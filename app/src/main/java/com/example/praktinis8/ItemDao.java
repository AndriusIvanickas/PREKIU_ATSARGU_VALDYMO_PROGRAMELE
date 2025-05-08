package com.example.praktinis8;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ItemDao {
    @Query("SELECT * FROM items")
    List<ItemEntity> getAllItems();

    @Insert
    long insertItem(ItemEntity item);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int updateItem(ItemEntity item);

    @Delete
    void deleteItem(ItemEntity item);

    @Query("DELETE FROM items")
    void clearItems();
}
