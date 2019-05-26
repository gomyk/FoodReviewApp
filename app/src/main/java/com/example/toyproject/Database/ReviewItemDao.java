package com.example.toyproject.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ReviewItemDao {
    @Insert
    public void insert(ReviewItem... items);

    @Update
    public void update(ReviewItem... items);

    @Delete
    public void delete(ReviewItem item);

    @Query("SELECT * FROM items")
    public List<ReviewItem> getItems();

    // @Query("SELECT * FROM items WHERE id = :id")
    // public Item getItemById(Long id);

}
