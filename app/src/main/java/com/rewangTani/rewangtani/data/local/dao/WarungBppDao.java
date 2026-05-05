package com.rewangTani.rewangtani.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.rewangTani.rewangtani.data.entity.warungbpp.DatumBpp;

import java.util.List;

@Dao
public interface WarungBppDao
{

    @Query("SELECT * FROM warung_bpp")
    LiveData<List<DatumBpp>> getAllProducts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DatumBpp> products);

    @Query("DELETE FROM warung_bpp")
    void deleteAll();

    @Query("SELECT namaProduk FROM warung_bpp WHERE idProduk = :productId LIMIT 1")
    String getNameByProductId(String productId);
}
