package com.rewangTani.rewangtani.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rewangTani.rewangtani.data.entity.profilakun.DatumProfil;
import com.rewangTani.rewangtani.data.entity.rencanatanam.DatumRencanaTanam;
import com.rewangTani.rewangtani.data.entity.rencanatanam.DraftRencanaTanam;
import com.rewangTani.rewangtani.data.local.RewangTaniDB;
import com.rewangTani.rewangtani.data.local.dao.DraftRencanaTanamDao;
import com.rewangTani.rewangtani.data.repository.MasterRencanaTanamRepo;
import com.rewangTani.rewangtani.data.repository.ProfileLahanRepo;
import com.rewangTani.rewangtani.data.repository.ProfileRepo;
import com.rewangTani.rewangtani.data.repository.TanamRepo;
import com.rewangTani.rewangtani.model.modelprofillahan.DatumProfilLahan;
import com.rewangTani.rewangtani.utility.DraftRencanaTanamManager;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class HomeViewModel extends AndroidViewModel
{

    private MasterRencanaTanamRepo masterRencanaTanamRepo;
    private TanamRepo tanamRepo;
    private ProfileLahanRepo profileLahanRepo;
    private ProfileRepo profileRepo;

    private LiveData<List<DatumRencanaTanam>> rencanaTanams;
    private LiveData<List<DatumProfil>> profiles;
    private LiveData<List<DatumProfilLahan>> profilLahans;

    public MutableLiveData<Boolean> profileLengkap = new MutableLiveData<>();
    private final Executor executor = Executors.newSingleThreadExecutor();

    private final DraftRencanaTanamDao draftRencanaTanamDao;
    private final DraftRencanaTanamManager draftRencanaTanamManager;
    private final LiveData<DraftRencanaTanam> draftRencanaTanamLiveData;

    public HomeViewModel(@NonNull Application application)
    {
        super(application);

        profileRepo = new ProfileRepo(application);
        tanamRepo = new TanamRepo(application);
        profileLahanRepo = new ProfileLahanRepo(application);
        masterRencanaTanamRepo = new MasterRencanaTanamRepo(application);

        rencanaTanams = tanamRepo.getAllRencanaTanam();
        profiles = profileRepo.getAllProfiles();
        profilLahans = profileLahanRepo.getAllProfilLahan();

        draftRencanaTanamDao = RewangTaniDB.getInstance(application).draftDao();
        draftRencanaTanamManager = new DraftRencanaTanamManager(draftRencanaTanamDao);
        draftRencanaTanamLiveData = draftRencanaTanamManager.getDraftLive();

    }

    public LiveData<List<DatumRencanaTanam>> getAllRencanaTanamById()
    {
        return rencanaTanams;
    }

    public LiveData<List<DatumProfilLahan>> getAllProfileLahanById()
    {
        return profilLahans;
    }

    public DraftRencanaTanamManager getDraftRencanaTanamManager()
    {
        return draftRencanaTanamManager;
    }

    public LiveData<DraftRencanaTanam> getDraftRencanaTanamLiveData()
    {
        return draftRencanaTanamLiveData;
    }

    public void updateDraftRencanaTanam(Consumer<DraftRencanaTanam> updater)
    {
        draftRencanaTanamManager.update(updater);
    }

    public void clearDraftRencanaTanam()
    {
        draftRencanaTanamManager.clear();
    }

    public void fetchAllRencanaTanamData(MasterRencanaTanamRepo.Callback callback)
    {
        masterRencanaTanamRepo.loadAll(callback);
    }

    public void fetchAllTanamData(TanamRepo.TanamCallback callback)
    {
        tanamRepo.loadAll(callback);
    }

    public void cekKelengkapanProfile()
    {
        executor.execute(() ->
        {
            DatumProfil modelProfilById = profileRepo.getMyProfile();
            if ( modelProfilById != null )
            {
                if (modelProfilById.getTelepon() != null && modelProfilById.getNik() != null &&
                        modelProfilById.getIdAlamat() != null && modelProfilById.getAlamat() != null &&
                        modelProfilById.getGender() != null && modelProfilById.getTglLahir() != null) {
                    profileLengkap.postValue(true);
                } else {
                    profileLengkap.postValue(false);
                }
            }
        });
    }

    public DatumRencanaTanam mapDraftToDatum(DraftRencanaTanam d)
    {
        return new DatumRencanaTanam(
                d.namaRencanaTanam,
                d.idProfilTanah,
                d.idKomoditas,
                d.idVarietas,
                d.idBiayaBuruhTanam,
                d.idBiayaBuruhBajak,
                d.idBiayaBuruhSemprot,
                d.idBiayaBuruhMenyiangirumput,
                d.idBiayaBuruhGalangan,
                d.idBiayaBuruhPupuk,
                d.idBiayaBuruhPanen,
                d.idSewaMesinBajak,
                d.idSewaMesinTanam,
                d.idSewaMesinPanen,
                d.idSewamesinPompa,
                d.idSewamesinPompaBbm,
                d.idBiayabibitLocalHet,
                d.idBiayabibitSubsidi,
                d.idBiayapupukKimiaLocalHet,
                d.idBiayapupukKimiaPhonska,
                d.idBiayapupukOrganik,
                d.idBiayapupukKimiaUrea,
                d.idBiayapupukKimiaFosfat,
                d.withPompa,
                d.luasLahan,
                d.potensiHasilVarietas
        );
    }

}
