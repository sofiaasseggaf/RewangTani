package com.rewangTani.rewangtani.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.rewangTani.rewangtani.data.entity.inbox.DatumInbox;

import java.util.List;

@Dao
public interface InboxDao
{

    @Query("SELECT * FROM inbox")
    LiveData<List<DatumInbox>> getAllInbox();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DatumInbox> inboxes);

    @Query("SELECT COUNT(*) FROM inbox WHERE readFlag = 'N'")
    LiveData<Integer> observeUnreadInbox();

    @Query("DELETE FROM inbox")
    void deleteAll();

}
