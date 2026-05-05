package com.rewangTani.rewangtani.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.rewangTani.rewangtani.data.entity.rencanatanam.DraftRencanaTanam;

@Dao
public interface DraftRencanaTanamDao
{


    @Query("SELECT * FROM draft_rencana_tanam WHERE id = 1 LIMIT 1")
    LiveData<DraftRencanaTanam> getDraftLive();

    @Query("SELECT * FROM draft_rencana_tanam WHERE id = 1 LIMIT 1")
    DraftRencanaTanam getDraftSync();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DraftRencanaTanam draft);

    @Query("DELETE FROM draft_rencana_tanam")
    void clearDraft();
}