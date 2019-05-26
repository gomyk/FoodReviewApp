package com.example.toyproject.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.example.toyproject.Database.ReviewItem;

@Database(entities = {ReviewItem.class}, version = 1)
public abstract class ReveiwDatabase extends RoomDatabase {
    public abstract ReviewItemDao getItemDAO();
}
