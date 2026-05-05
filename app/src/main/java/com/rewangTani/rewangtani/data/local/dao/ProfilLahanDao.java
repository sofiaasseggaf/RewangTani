package com.rewangTani.rewangtani.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.rewangTani.rewangtani.model.modelprofillahan.DatumProfilLahan;

import java.util.List;

@Dao
public interface ProfilLahanDao
{

    @Query("SELECT * FROM profil_lahan")
    LiveData<List<DatumProfilLahan>> getAllProfilLahan(); // all pl belong to user only

    @Query("SELECT * FROM profil_lahan")
    List<DatumProfilLahan> getAllProfilLahanLocal(); // all pl belong to user only

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DatumProfilLahan> profilLahans);

    @Query("DELETE FROM profil_lahan")
    void deleteAll();

}
