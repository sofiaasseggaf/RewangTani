package com.rewangTani.rewangtani.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.rewangTani.rewangtani.data.entity.warungsewamesin.DatumSewaMesin;

import java.util.List;

@Dao
public interface WarungSewaMesinDao
{

    @Query("SELECT * FROM warung_sewa_mesin")
    LiveData<List<DatumSewaMesin>> getAllProducts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DatumSewaMesin> products);

    @Query("DELETE FROM warung_sewa_mesin")
    void deleteAll();

    @Query("SELECT namaProduk FROM warung_sewa_mesin WHERE idProduk = :productId LIMIT 1")
    String getNameByProductId(String productId);
}
