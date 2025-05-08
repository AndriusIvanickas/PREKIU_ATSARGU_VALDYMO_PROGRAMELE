package com.example.praktinis8;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ItemEntity.class, SalesRecordEntity.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ItemDao itemDao();
    public abstract SalesRecordDao salesRecordDao();
}
