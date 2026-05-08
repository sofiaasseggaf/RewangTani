package com.rewangTani.rewangtani.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.rewangTani.rewangtani.data.entity.sudahtanam.DatumSudahTanam;

import java.util.List;

@Dao
public interface SudahTanamDao
{

    @Query("SELECT * FROM sudah_tanam")
    LiveData<List<DatumSudahTanam>> getAllSudahTanam(); // all rt belong to user only

    @Query("SELECT * FROM sudah_tanam")
    List<DatumSudahTanam> getAllSudahTanamLocal(); // all rt belong to user only

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DatumSudahTanam> sudahTanams);

    @Query("DELETE FROM sudah_tanam")
    void deleteAll();

    @Query("SELECT sudah_tanam.* FROM sudah_tanam WHERE idRencanaTanam = :rencanaTanamId")
    LiveData<List<DatumSudahTanam>> getSudahTanamByRTId(String rencanaTanamId);
}
