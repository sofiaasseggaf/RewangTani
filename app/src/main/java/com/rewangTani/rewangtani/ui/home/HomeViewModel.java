package com.rewangTani.rewangtani.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rewangTani.rewangtani.data.entity.profilakun.DatumProfil;
import com.rewangTani.rewangtani.data.entity.rencanatanam.DatumRencanaTanam;
import com.rewangTani.rewangtani.data.repository.ProfileLahanRepo;
import com.rewangTani.rewangtani.data.repository.ProfileRepo;
import com.rewangTani.rewangtani.data.repository.RencanaTanamRepo;
import com.rewangTani.rewangtani.model.modelprofillahan.DatumProfilLahan;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HomeViewModel extends AndroidViewModel
{

    private RencanaTanamRepo rencanaTanamRepo;
    private ProfileLahanRepo profileLahanRepo;
    private ProfileRepo profileRepo;
    private LiveData<List<DatumRencanaTanam>> rencanaTanams;
    private LiveData<List<DatumProfilLahan>> profilLahans;
    public MutableLiveData<Boolean> profileLengkap = new MutableLiveData<>();

    private final Executor executor = Executors.newSingleThreadExecutor();

    public HomeViewModel(@NonNull Application application)
    {
        super(application);

        profileRepo = new ProfileRepo(application);
        rencanaTanamRepo = new RencanaTanamRepo(application);
        profileLahanRepo = new ProfileLahanRepo(application);

        rencanaTanams = rencanaTanamRepo.getAllRencanaTanam();
        profilLahans = profileLahanRepo.getAllProfilLahan();
    }

    public LiveData<List<DatumRencanaTanam>> getAllRencanaTanamById()
    {
        return rencanaTanams;
    }

    public LiveData<List<DatumProfilLahan>> getAllProfileLahanById()
    {
        return profilLahans;
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

}
