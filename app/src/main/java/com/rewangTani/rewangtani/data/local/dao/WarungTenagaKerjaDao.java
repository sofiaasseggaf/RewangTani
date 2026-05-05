package com.rewangTani.rewangtani.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.rewangTani.rewangtani.data.entity.warungtenagakerja.DatumTenagaKerja;

import java.util.List;

@Dao
public interface WarungTenagaKerjaDao
{

    @Query("SELECT * FROM warung_tenaga_kerja")
    LiveData<List<DatumTenagaKerja>> getAllProducts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DatumTenagaKerja> products);

    @Query("DELETE FROM warung_tenaga_kerja")
    void deleteAll();

    @Query("SELECT namaTenagaKerja FROM warung_tenaga_kerja WHERE idProduk = :productId LIMIT 1")
    String getNameByProductId(String productId);
}
