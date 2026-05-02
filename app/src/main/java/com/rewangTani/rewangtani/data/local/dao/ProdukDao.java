package com.rewangTani.rewangtani.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.rewangTani.rewangtani.data.entity.product.DatumProduk;

import java.util.List;

@Dao
public interface ProdukDao
{

    @Query("SELECT * FROM produk")
    LiveData<List<DatumProduk>> getAllProducts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DatumProduk> products);

    @Query("DELETE FROM produk")
    void deleteAll();
}
