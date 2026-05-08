package com.rewangTani.rewangtani.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.rewangTani.rewangtani.data.entity.rencanatanam.DatumRencanaTanam;
import com.rewangTani.rewangtani.data.entity.rencanatanam.ModelRencanaTanam;
import com.rewangTani.rewangtani.data.entity.sudahtanam.DatumSudahTanam;
import com.rewangTani.rewangtani.data.entity.sudahtanam.ModelSudahTanam;
import com.rewangTani.rewangtani.data.local.RewangTaniDB;
import com.rewangTani.rewangtani.data.local.dao.RencanaTanamDao;
import com.rewangTani.rewangtani.data.local.dao.SudahTanamDao;
import com.rewangTani.rewangtani.data.remote.APIService.APIClient;
import com.rewangTani.rewangtani.data.remote.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.utility.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TanamRepo
{

    private Context context;
    private APIInterfacesRest apiInterface;
    private RencanaTanamDao rencanaTanamDao;
    private SudahTanamDao sudahTanamDao;
    private List<DatumRencanaTanam> datumRencanaTanamList;
    private List<DatumSudahTanam> datumSudahTanamList;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public TanamRepo(Context context)
    {
        RewangTaniDB db = RewangTaniDB.getInstance(context);
        this.context = context.getApplicationContext();

        rencanaTanamDao = db.rencanaTanamDao();
        sudahTanamDao = db.sudahTanamDao();
        apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
    }

    public LiveData<List<DatumRencanaTanam>> getAllRencanaTanam()
    {
        refreshFromApiRencanaTanam();
        return rencanaTanamDao.getAllRencanaTanam();
    }

    public LiveData<List<DatumSudahTanam>> getAllSudahTanam()
    {
        refreshFromApiSudahTanam();
        return sudahTanamDao.getAllSudahTanam();
    }

    private void refreshFromApiRencanaTanam()
    {
        String idProfil = PreferenceUtils.getIdProfil(context);
        apiInterface.getDataRencanaTanamByProfilId(idProfil).enqueue(new Callback<ModelRencanaTanam>() {
            @Override
            public void onResponse(Call<ModelRencanaTanam> call, Response<ModelRencanaTanam> response) {
                if (response.isSuccessful() && response.body() != null) {
                    new Thread(() -> {
                        List<DatumRencanaTanam> rencanaTanams = response.body().getData();
                        long now = System.currentTimeMillis();
                        for (DatumRencanaTanam p : rencanaTanams) {
                            p.lastUpdated = now;
                            Log.i("SOFIA", "AkunRepo - now = " + response.body().getTotalData());
                        }

                        rencanaTanamDao.deleteAll();
                        rencanaTanamDao.insertAll(rencanaTanams);
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<ModelRencanaTanam> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void refreshFromApiSudahTanam()
    {
        apiInterface.getDataSudahTanam().enqueue(new Callback<ModelSudahTanam>() {
            @Override
            public void onResponse(Call<ModelSudahTanam> call, Response<ModelSudahTanam> response) {
                if (response.isSuccessful() && response.body() != null) {
                    new Thread(() -> {
                        List<DatumSudahTanam> sudahTanams = response.body().getData();
                        sudahTanamDao.deleteAll();
                        sudahTanamDao.insertAll(sudahTanams);
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<ModelSudahTanam> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    public interface TanamCallback
    {
        void onLoaded(
                List<DatumRencanaTanam> listRencanaTanam,
                List<DatumSudahTanam> listSudahTanam
        );
    }

    public void loadAll(TanamRepo.TanamCallback callback)
    {

        executor.execute(() -> {

            if (datumRencanaTanamList != null && datumSudahTanamList != null) {

                callback.onLoaded(datumRencanaTanamList, datumSudahTanamList);
                return;
            }

            String idProfil = PreferenceUtils.getIdProfil(context);
            apiInterface.getDataRencanaTanamByProfilId(idProfil).enqueue(new retrofit2.Callback<ModelRencanaTanam>() {
                @Override
                public void onResponse(Call<ModelRencanaTanam> call, Response<ModelRencanaTanam> response) {
                    if (response.body() != null) {
                        datumRencanaTanamList = response.body().getData();
                        new Thread(() -> {
                            List<DatumRencanaTanam> rencanaTanams = response.body().getData();
                            long now = System.currentTimeMillis();
                            for (DatumRencanaTanam p : rencanaTanams) {
                                p.lastUpdated = now;
                            }
                            rencanaTanamDao.deleteAll();
                            rencanaTanamDao.insertAll(rencanaTanams);
                        }).start();
                    }

                    apiInterface.getDataSudahTanam().enqueue(new retrofit2.Callback<ModelSudahTanam>() {
                        @Override
                        public void onResponse(Call<ModelSudahTanam> call, Response<ModelSudahTanam> response) {
                            if (response.body() != null) {
                                datumSudahTanamList = response.body().getData();
                                new Thread(() -> {
                                    List<DatumSudahTanam> sudahTanams = response.body().getData();
                                    sudahTanamDao.deleteAll();
                                    sudahTanamDao.insertAll(sudahTanams);
                                }).start();

                                List<DatumSudahTanam> sudahTanamList = new ArrayList<>();
                                for (DatumSudahTanam sudahTanam : datumSudahTanamList) {
                                    for (DatumRencanaTanam rencanaTanam : datumRencanaTanamList) {
                                        if (rencanaTanam.getIdRencanaTanam()
                                                .equalsIgnoreCase(sudahTanam.getIdRencanaTanam())) {
                                            sudahTanamList.add(sudahTanam);
                                        }
                                    }
                                }

                                // 1. Create a Map of RencanaTanam for instant name lookup (ID -> Name)
                                Map<String, String> nameMap = datumRencanaTanamList.stream()
                                        .collect(Collectors.toMap(
                                                DatumRencanaTanam::getIdRencanaTanam,
                                                DatumRencanaTanam::getNamaRencanaTanam,
                                                (existing, replacement) -> existing // Keep first if IDs are duplicate
                                        ));

                                List<DatumSudahTanam> sudahTanamListFiltered = datumSudahTanamList.stream()
                                        .filter(st -> nameMap.containsKey(st.getIdRencanaTanam())) // Only keep if exists in Rencana
                                        .collect(Collectors.toMap(
                                                DatumSudahTanam::getIdRencanaTanam,
                                                st -> st,
                                                (existing, replacement) -> replacement // This is the magic: always take the NEWER one
                                        ))
                                        .values() // Get just the items
                                        .stream()
                                        .peek(st ->
                                        {
                                            String name = nameMap.get(st.getIdRencanaTanam());
                                            st.setIdRencanaTanam(st.getIdRencanaTanam() + "-" + name);
                                        })
                                        .collect(Collectors.toList());

                                callback.onLoaded(
                                        datumRencanaTanamList,
                                        sudahTanamListFiltered
                                );
                            }
                        }

                        @Override
                        public void onFailure(Call<ModelSudahTanam> call, Throwable t) {

                        }
                    });

                }

                @Override
                public void onFailure(Call<ModelRencanaTanam> call, Throwable t) {

                }
            });
        });
    }

}
