package com.rewangTani.rewangtani.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.rewangTani.rewangtani.data.entity.rencanatanam.DatumRencanaTanam;

import java.util.List;

@Dao
public interface RencanaTanamDao
{

    @Query("SELECT * FROM rencana_tanam")
    LiveData<List<DatumRencanaTanam>> getAllRencanaTanam();

    @Query("SELECT * FROM rencana_tanam")
    List<DatumRencanaTanam> getAllRencanaTanamLocal();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DatumRencanaTanam> rencanaTanams);

    @Query("DELETE FROM rencana_tanam")
    void deleteAll();

    @Query("SELECT rencana_tanam.* FROM rencana_tanam WHERE idProfil = :profileId")
    LiveData<List<DatumRencanaTanam>> getRencanaTanamById(String profileId);
}
