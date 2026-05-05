package com.rewangTani.rewangtani.utility;

import androidx.lifecycle.LiveData;

import com.rewangTani.rewangtani.data.entity.rencanatanam.DraftRencanaTanam;
import com.rewangTani.rewangtani.data.local.dao.DraftRencanaTanamDao;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class DraftRencanaTanamManager
{

    private final DraftRencanaTanamDao dao;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public DraftRencanaTanamManager(DraftRencanaTanamDao dao)
    {
        this.dao = dao;
    }

    public LiveData<DraftRencanaTanam> getDraftLive()
    {
        return dao.getDraftLive();
    }

    public void update(Consumer<DraftRencanaTanam> updater)
    {
        executor.execute(() ->
        {
            DraftRencanaTanam draft = dao.getDraftSync();
            if (draft == null) draft = new DraftRencanaTanam();
            updater.accept(draft);
            dao.insert(draft);
        });
    }

    public DraftRencanaTanam getSync()
    {
        return dao.getDraftSync();
    }

    public void clear()
    {
        executor.execute(dao::clearDraft);
    }
}
