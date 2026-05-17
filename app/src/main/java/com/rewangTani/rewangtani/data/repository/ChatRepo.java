package com.rewangTani.rewangtani.data.repository;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import com.rewangTani.rewangtani.data.entity.inbox.DatumInbox;
import com.rewangTani.rewangtani.data.entity.inbox.ModelInbox;
import com.rewangTani.rewangtani.data.entity.profilakun.DatumProfil;
import com.rewangTani.rewangtani.data.local.RewangTaniDB;
import com.rewangTani.rewangtani.data.local.dao.InboxDao;
import com.rewangTani.rewangtani.data.local.dao.ProfilDao;
import com.rewangTani.rewangtani.data.remote.APIService.APIClient;
import com.rewangTani.rewangtani.data.remote.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.model.modelchatdaninbox.modelchat.DatumChat;
import com.rewangTani.rewangtani.model.modelchatdaninbox.modelchat.ModelChat;
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
    private ProfilDao profilDao;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public ChatRepo(Context context)
    {

        RewangTaniDB db = RewangTaniDB.getInstance(context);
        apiInterface = APIClient.getClient().create(APIInterfacesRest.class);

        this.context = context.getApplicationContext();
        this.inboxDao = db.inboxDao();
        this.profilDao = db.profilDao();
    }

    public interface ChatDataCallback {
        void onLoaded(List<DatumProfil> localProfile,
                      List<DatumInbox> localInboxes,
                      List<DatumInboxParticipant> remoteParticipants,
                      List<DatumChat> remoteChats);
        void onError(String error);
    }

    public LiveData<List<DatumInbox>> getAllInbox()
    {
        return inboxDao.getAllInbox();
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

    public LiveData<Integer> getUnreadCount(String idProfile)
    {
        return inboxDao.observeUnreadInbox(idProfile);
    }

    public void loadChatData(String myId, ChatDataCallback callback)
    {
        executor.execute(() ->
        {
            List<DatumProfil> localProfile = profilDao.getAllProfilesLocal();

            CountDownLatch latch = new CountDownLatch(3);
            final ModelInbox[] inboxRes = new ModelInbox[1];
            final ModelInboxParticipant[] partRes = new ModelInboxParticipant[1];
            final ModelChat[] chatRes = new ModelChat[1];

            // API Call 1: Inbox
            apiInterface.getDataInbox().enqueue(new Callback<ModelInbox>() {
                @Override
                public void onResponse(Call<ModelInbox> c, Response<ModelInbox> r) {
                    inboxRes[0] = r.body();
                    latch.countDown();
                }
                @Override
                public void onFailure(Call<ModelInbox> c, Throwable t) { latch.countDown(); }
            });

            // API Call 2: Participants
            apiInterface.getDataInboxParticipant().enqueue(new Callback<ModelInboxParticipant>() {
                @Override
                public void onResponse(Call<ModelInboxParticipant> c, Response<ModelInboxParticipant> r) {
                    partRes[0] = r.body();
                    latch.countDown();
                }
                @Override
                public void onFailure(Call<ModelInboxParticipant> c, Throwable t) { latch.countDown(); }
            });

            // API Call 3: Chats
            apiInterface.getDataChat().enqueue(new Callback<ModelChat>() {
                @Override
                public void onResponse(Call<ModelChat> c, Response<ModelChat> r) {
                    chatRes[0] = r.body();
                    latch.countDown();
                }
                @Override
                public void onFailure(Call<ModelChat> c, Throwable t) { latch.countDown(); }
            });

            try {
                // Wait up to 15 seconds for all APIs to finish
                latch.await(15, TimeUnit.SECONDS);

                if (inboxRes[0] != null && partRes[0] != null && chatRes[0] != null) {

                    // 3. Filtering Logic
                    // Find Participant IDs where I am involved
                    List<String> myParticipantIds = new ArrayList<>();
                    for (DatumInboxParticipant p : partRes[0].getData()) {
                        if (myId.equalsIgnoreCase(p.getIdProfilA()) || myId.equalsIgnoreCase(p.getIdProfilB())) {
                            myParticipantIds.add(p.getIdInboxParticipant());
                        }
                    }

                    // Filter Inboxes based on those Participant IDs
                    List<DatumInbox> filteredInboxes = new ArrayList<>();
                    for (DatumInbox i : inboxRes[0].getData()) {
                        if (myParticipantIds.contains(i.getIdInboxParticipant())) {
                            filteredInboxes.add(i);
                        }
                    }

                    // 4. Sync Filtered Inboxes to Local Room
                    if (!filteredInboxes.isEmpty()) {

                        inboxDao.deleteAll();
                        inboxDao.insertAll(filteredInboxes);
                    }

                    // 5. Return everything to UI Thread
                    new Handler(Looper.getMainLooper()).post(() -> {
                        callback.onLoaded(
                                localProfile,        // Local
                                filteredInboxes,     // API -> Saved to Local
                                partRes[0].getData(),// API
                                chatRes[0].getData() // API
                        );
                    });

                } else {
                    new Handler(Looper.getMainLooper()).post(() -> callback.onError("Gagal mengambil data terbaru"));
                }

            } catch (InterruptedException e) {
                new Handler(Looper.getMainLooper()).post(() -> callback.onError(e.getMessage()));
            }
        });
    }

}
