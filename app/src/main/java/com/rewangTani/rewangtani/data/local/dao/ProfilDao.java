package com.rewangTani.rewangtani.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.rewangTani.rewangtani.data.entity.profilakun.DatumProfil;

import java.util.List;

@Dao
public interface ProfilDao
{

    @Query("SELECT * FROM profil")
    LiveData<List<DatumProfil>> getAllProfiles();

    @Query("SELECT profil.* FROM profil " +
            "INNER JOIN produk ON produk.idProfil = profil.idProfile " +
            "WHERE produk.idProduk = :productId LIMIT 1")
    DatumProfil getProfileByProductIdDirect(String productId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DatumProfil> accounts);

    @Query("DELETE FROM profil")
    void deleteAll();
}
