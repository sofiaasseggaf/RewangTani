package com.rewangTani.rewangtani.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.rewangTani.rewangtani.data.entity.inbox.DatumInbox;
import com.rewangTani.rewangtani.data.entity.inbox.ModelInbox;
import com.rewangTani.rewangtani.data.local.RewangTaniDB;
import com.rewangTani.rewangtani.data.local.dao.InboxDao;
import com.rewangTani.rewangtani.data.remote.APIService.APIClient;
import com.rewangTani.rewangtani.data.remote.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.model.modelchatdaninbox.modelinboxparticipant.DatumInboxParticipant;
import com.rewangTani.rewangtani.model.modelchatdaninbox.modelinboxparticipant.ModelInboxParticipant;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatRepo
{

    private Context context;
    private APIInterfacesRest apiInterface;
    private InboxDao inboxDao;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public ChatRepo(Context context)
    {

        RewangTaniDB db = RewangTaniDB.getInstance(context);
        apiInterface = APIClient.getClient().create(APIInterfacesRest.class);

        this.context = context.getApplicationContext();
        this.inboxDao = db.inboxDao();
    }

    public interface InboxSyncCallback
    {
        void onSyncComplete(List<DatumInbox> inboxes);
        void onError(String message);
    }

    public void loadAndSyncInbox(String myId)
    {
        executor.execute(() -> {

            CountDownLatch latch = new CountDownLatch(2);
            final ModelInbox[] inboxResponse = new ModelInbox[1];
            final ModelInboxParticipant[] participantResponse = new ModelInboxParticipant[1];

            // START API 1: Get Participants
            apiInterface.getDataInboxParticipant().enqueue(new Callback<ModelInboxParticipant>() {
                @Override
                public void onResponse(Call<ModelInboxParticipant> call, Response<ModelInboxParticipant> response) {
                    participantResponse[0] = response.body();
                    latch.countDown();
                }
                @Override
                public void onFailure(Call<ModelInboxParticipant> call, Throwable t) { latch.countDown(); }
            });

            // START API 2: Get Inboxes
            apiInterface.getDataInbox().enqueue(new Callback<ModelInbox>() {
                @Override
                public void onResponse(Call<ModelInbox> call, Response<ModelInbox> response) {
                    inboxResponse[0] = response.body();
                    latch.countDown();
                }
                @Override
                public void onFailure(Call<ModelInbox> call, Throwable t) { latch.countDown(); }
            });

            try {
                // Wait for both network calls to finish (or timeout after 10s)
                latch.await(10, TimeUnit.SECONDS);

                if (inboxResponse[0] != null && participantResponse[0] != null) {

                    // 3. Filtering Logic
                    List<DatumInboxParticipant> myParts = new ArrayList<>();
                    for (DatumInboxParticipant p : participantResponse[0].getData()) {
                        if (myId.equalsIgnoreCase(p.getIdProfilA()) || myId.equalsIgnoreCase(p.getIdProfilB())) {
                            myParts.add(p);
                        }
                    }

                    List<DatumInbox> filteredInboxes = new ArrayList<>();
                    for (DatumInbox i : inboxResponse[0].getData()) {
                        for (DatumInboxParticipant p : myParts) {
                            if (i.getIdInboxParticipant().equalsIgnoreCase(p.getIdInboxParticipant())) {
                                filteredInboxes.add(i);
                            }
                        }
                    }

                    // 4. SAVE TO LOCAL ROOM DB
                    inboxDao.deleteAll();
                    inboxDao.insertAll(filteredInboxes);

                }

            } catch (InterruptedException e) { e.printStackTrace(); }
        });
    }

    public LiveData<Integer> getUnreadCount()
    {
        return inboxDao.observeUnreadInbox();
    }
}
