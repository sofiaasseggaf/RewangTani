package com.rewangTani.rewangtani.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.rewangTani.rewangtani.data.entity.akun.DatumAkun;

import java.util.List;

@Dao
public interface AkunDao
{

    @Query("SELECT * FROM akun")
    LiveData<List<DatumAkun>> getAllAccount();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DatumAkun> accounts);

    @Query("DELETE FROM akun")
    void deleteAll();

}
