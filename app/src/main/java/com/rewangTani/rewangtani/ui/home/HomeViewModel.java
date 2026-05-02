package com.rewangTani.rewangtani.ui.home;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.rewangTani.rewangtani.data.entity.product.DatumProduk;
import com.rewangTani.rewangtani.data.entity.profilakun.DatumProfil;
import com.rewangTani.rewangtani.data.local.RewangTaniDB;
import com.rewangTani.rewangtani.data.local.dao.ProfilDao;
import com.rewangTani.rewangtani.data.repository.ProdukRepo;
import com.rewangTani.rewangtani.data.repository.ProfilRepo;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HomeViewModel extends AndroidViewModel
{

    private ProfilRepo profilRepo;
    private ProdukRepo produkRepo;
    private ProfilDao profileDao;
    private LiveData<List<DatumProfil>> profiles;
    private LiveData<List<DatumProduk>> products;

    private final Executor executor = Executors.newSingleThreadExecutor();

    public HomeViewModel(@NonNull Application application)
    {
        super(application);
        profilRepo = new ProfilRepo(application);
        produkRepo = new ProdukRepo(application);
        profileDao = RewangTaniDB.getInstance(application).profilDao();
        profiles = profilRepo.getProfiles();

        Log.i("SOFIA", " profile = " + profiles.getValue());
    }

}
