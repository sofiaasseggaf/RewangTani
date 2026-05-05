package com.rewangTani.rewangtani.data.repository;

import android.content.Context;

import com.rewangTani.rewangtani.data.entity.rencanatanam.DatumRencanaTanam;
import com.rewangTani.rewangtani.data.local.RewangTaniDB;
import com.rewangTani.rewangtani.data.local.dao.ProfilLahanDao;
import com.rewangTani.rewangtani.data.local.dao.RencanaTanamDao;
import com.rewangTani.rewangtani.data.remote.APIService.APIClient;
import com.rewangTani.rewangtani.data.remote.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.model.modelnoneditable.komoditas.DatumKomoditas;
import com.rewangTani.rewangtani.model.modelnoneditable.komoditas.ModelKomoditas;
import com.rewangTani.rewangtani.model.modelnoneditable.varietas.DatumVarietas;
import com.rewangTani.rewangtani.model.modelnoneditable.varietas.ModelVarietas;
import com.rewangTani.rewangtani.model.modelprofillahan.DatumProfilLahan;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Response;

public class MasterRencanaTanamRepo
{

    private Context context;
    private APIInterfacesRest apiInterface;
    RencanaTanamDao rencanaTanamDao;
    ProfilLahanDao profilLahanDao;
    private List<DatumKomoditas> datumKomoditasList;
    private List<DatumVarietas> datumVarietasList;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public MasterRencanaTanamRepo(Context context)
    {
        RewangTaniDB db = RewangTaniDB.getInstance(context);
        apiInterface = APIClient.getClient().create(APIInterfacesRest.class);

        this.context = context.getApplicationContext();
        this.rencanaTanamDao = db.rencanaTanamDao();
        this.profilLahanDao = db.profilLahanDao();
    }

    public interface Callback
    {
        void onLoaded(
                List<DatumRencanaTanam> listRencanaTanam,
                List<DatumProfilLahan> listProfileLahan,
                List<DatumKomoditas> listKomoditas,
                List<DatumVarietas> listVarietas
        );
    }

    public void loadAll(Callback callback)
    {

        executor.execute(() -> {

            List<DatumRencanaTanam> listRencanaTanam = rencanaTanamDao.getAllRencanaTanamLocal();
            List<DatumProfilLahan> listProfileLahan = profilLahanDao.getAllProfilLahanLocal();

            if (datumKomoditasList != null && datumKomoditasList != null) {

                callback.onLoaded(listRencanaTanam, listProfileLahan, datumKomoditasList, datumVarietasList);
                return;
            }

            apiInterface.getDataKomoditas().enqueue(new retrofit2.Callback<ModelKomoditas>() {
                @Override
                public void onResponse(Call<ModelKomoditas> call, Response<ModelKomoditas> response) {
                    if (response.body() != null) {
                        datumKomoditasList = response.body().getData();
                    }

                    apiInterface.getDataVarietas().enqueue(new retrofit2.Callback<ModelVarietas>() {
                        @Override
                        public void onResponse(Call<ModelVarietas> call, Response<ModelVarietas> response) {
                            if (response.body() != null) {
                                datumVarietasList = response.body().getData();
                                callback.onLoaded(
                                        listRencanaTanam,
                                        listProfileLahan,
                                        datumKomoditasList,
                                        datumVarietasList
                                );
                            }
                        }

                        @Override
                        public void onFailure(Call<ModelVarietas> call, Throwable t) {

                        }
                    });

                }

                @Override
                public void onFailure(Call<ModelKomoditas> call, Throwable t) {

                }
            });
        });
    }

}

