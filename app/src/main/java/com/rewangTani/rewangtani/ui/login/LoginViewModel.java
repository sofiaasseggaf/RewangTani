package com.rewangTani.rewangtani.ui.login;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rewangTani.rewangtani.data.repository.AkunRepo;
import com.rewangTani.rewangtani.data.entity.akun.DatumAkun;
import com.rewangTani.rewangtani.data.entity.akun.ModelAkun;
import com.rewangTani.rewangtani.data.entity.profilakun.DatumProfil;
import com.rewangTani.rewangtani.data.entity.profilakun.ModelProfilAkun;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel
{

    private AkunRepo repo;
    private LiveData<List<DatumAkun>> accounts;
    private ModelAkun cachedAkun;
    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<String> errorMessage = new MutableLiveData<>();
    public MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>();


    public LoginViewModel(@NonNull Application application)
    {
        super(application);
        repo = new AkunRepo(application);
        accounts = repo.getAccounts();
    }

    public LiveData<List<DatumAkun>> getAccounts()
    {
        return accounts;
    }

    public void loadAccounts()
    {
        isLoading.setValue(true);
        repo.loadAccounts(new Callback<ModelAkun>() {
            @Override
            public void onResponse(Call<ModelAkun> call, Response<ModelAkun> response) {
                isLoading.setValue(false);

                if (response.body() != null) {
                    cachedAkun = response.body();
                    Log.i("SOFIA", "RegisterVM - cachedAkun = " + cachedAkun.getTotalData());
                } else {
                    errorMessage.setValue("Data akun kosong");
                }
            }

            @Override
            public void onFailure(Call<ModelAkun> call, Throwable t) {
                isLoading.setValue(false);
                errorMessage.setValue("Koneksi error");
            }
        });
    }

    public void validateLogin(String inputUsername, String inputPassword)
    {

        DatumAkun datumAkun = null;
        isLoading.setValue(true);

        if (inputUsername.isEmpty() || inputPassword.isEmpty()) {
            isLoading.setValue(false);
            errorMessage.setValue("Silahkan isi kolom yang kosong");
            return;
        }

        // 🔹 cek data
        if (cachedAkun == null || cachedAkun.getData() == null) {
            isLoading.setValue(false);
            errorMessage.setValue("Data akun belum tersedia");
            return;
        }

        for (DatumAkun akun : cachedAkun.getData()) {
            if (akun.getUserName().equalsIgnoreCase(inputUsername) &&
                    akun.getPassword().equalsIgnoreCase(inputPassword)) {
                datumAkun = akun;
                break;
            }
        }

        if (datumAkun == null) {
            isLoading.setValue(false);
            errorMessage.setValue("Akun belum terdaftar");
        } else {
            DatumAkun finalDatumAkun = datumAkun;
            repo.loadProfileAccounts(new Callback<ModelProfilAkun>() {
            @Override
            public void onResponse(Call<ModelProfilAkun> call, Response<ModelProfilAkun> response)
            {
                if (response.body() != null) {
                    ModelProfilAkun modelProfilAkun = response.body();
                    DatumProfil datumProfil = null;

                    for (DatumProfil data: modelProfilAkun.getData()) {
                        if (data.getIdAkun().equalsIgnoreCase(finalDatumAkun.getIdAkun())) {
                            datumProfil = data;
                            break;
                        }
                    }

                    isLoading.setValue(false);
                    if (datumProfil == null) {
                        errorMessage.setValue("Akun belum terdaftar");
                    } else {
                        repo.saveAkun(finalDatumAkun);
                        repo.saveProfilAkun(datumProfil);
                        loginSuccess.setValue(true);
                    }
                }

            }

            @Override
            public void onFailure(Call<ModelProfilAkun> call, Throwable t) {
                isLoading.setValue(false);
                errorMessage.setValue("Terjadi gangguan koneksi");
            }
        });
    }
    }

}
