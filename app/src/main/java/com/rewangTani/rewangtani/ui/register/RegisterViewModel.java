package com.rewangTani.rewangtani.ui.register;

import android.app.Application;
import android.util.ArrayMap;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rewangTani.rewangtani.data.repository.AkunRepo;
import com.rewangTani.rewangtani.data.entity.akun.DatumAkun;
import com.rewangTani.rewangtani.data.entity.akun.ModelAkun;
import com.rewangTani.rewangtani.data.entity.profilakun.DatumProfil;
import com.rewangTani.rewangtani.data.entity.profilakun.ModelProfilAkun;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends AndroidViewModel
{

    private AkunRepo repo;
    private ModelAkun cachedAkun;
    private LiveData<List<DatumAkun>> accounts;
    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<String> errorMessage = new MutableLiveData<>();
    public MutableLiveData<Boolean> registerSuccess = new MutableLiveData<>();

    public RegisterViewModel(@Nonnull Application application)
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

    // 🔥 VALIDASI
    public boolean validate(String username, String email, String password, String repeatPassword)
    {

        if (username.isEmpty()) {
            errorMessage.setValue("Username kosong");
            return false;
        }

        if (email.isEmpty()) {
            errorMessage.setValue("Email kosong");
            return false;
        }

        if (password.length() < 8) {
            errorMessage.setValue("Password kurang dari 8 karakter");
            return false;
        }

        if (!password.equals(repeatPassword)) {
            errorMessage.setValue("Password tidak sama");
            return false;
        }

        boolean hasLetter = password.matches(".*[a-zA-Z].*");
        boolean hasDigit = password.matches(".*\\d.*");

        if (!(hasLetter && hasDigit)) {
            errorMessage.setValue("Password harus kombinasi angka dan huruf");
            return false;
        }

        // cek duplicate
        if (cachedAkun != null) {
            for (DatumAkun akun : cachedAkun.getData()) {
                if (akun.getUserName().equalsIgnoreCase(username)) {
                    errorMessage.setValue("Username sudah dipakai");
                    return false;
                }

                if (akun.getEmail().equalsIgnoreCase(email)) {
                    errorMessage.setValue("Email sudah dipakai");
                    return false;
                }
            }
        }

        return true;
    }

    // 🔥 REGISTER
    public void register(String username, String email, String password, String nama)
    {

        isLoading.setValue(true);
        registerSuccess.setValue(false);

        Map<String, Object> json = new HashMap<>();
        json.put("userName", username);
        json.put("email", email);
        json.put("password", password);
        json.put("namaAkun", nama);
        json.put("token", "");
        json.put("idGoogle", "");

        RequestBody body = RequestBody.create(
                okhttp3.MediaType.parse("application/json"),
                new JSONObject(json).toString()
        );

        repo.sendDataAkun(body, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() != null) {
                    repo.loadAccounts(new Callback<ModelAkun>() {
                        @Override
                        public void onResponse(Call<ModelAkun> call, Response<ModelAkun> res) {

                            if (res.body() != null) {

                                ModelAkun model = res.body();
                                DatumAkun found = null;

                                for (DatumAkun akun : model.getData()) {
                                    if (akun.getEmail().equalsIgnoreCase(email)) {
                                        found = akun;
                                        repo.saveAkun(found);
                                        break;
                                    }
                                }

                                if (found != null) {
                                    registerProfilAkun(found.getIdAkun(), found.getNamaAkun());
                                } else {
                                    errorMessage.setValue("Akun tidak ditemukan setelah register");
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<ModelAkun> call, Throwable t) {
                            isLoading.setValue(false);
                            errorMessage.setValue("Gagal ambil data akun");
                        }
                    });

                } else {
                    isLoading.setValue(false);
                    errorMessage.setValue("Register gagal");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                isLoading.setValue(false);
                errorMessage.setValue("Koneksi error");
            }
        });
    }

    public void registerProfilAkun(String idAkun, String namaAkun)
    {

        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("idAkun", idAkun);
        jsonParams.put("namaDepan", namaAkun);
        jsonParams.put("namaBelakang", "");
        jsonParams.put("alamat", "");
        jsonParams.put("idAlamat", "");
        jsonParams.put("nik", "");
        jsonParams.put("tglLahir", "");
        jsonParams.put("gender", "");
        jsonParams.put("telepon", "");
        jsonParams.put("foto", "");
        jsonParams.put("idStatusPekerja", "");

        RequestBody body = RequestBody.create(
                okhttp3.MediaType.parse("application/json"),
                new JSONObject(jsonParams).toString()
        );

        repo.sendDataProfilAkun(body, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() != null) {
                    repo.loadProfileAccounts(new Callback<ModelProfilAkun>() {
                        @Override
                        public void onResponse(Call<ModelProfilAkun> call, Response<ModelProfilAkun> res)
                        {
                            if (res.body() != null) {

                                ModelProfilAkun model = res.body();
                                DatumProfil found = null;

                                for (DatumProfil akun : model.getData()) {
                                    if (akun.getIdAkun().equalsIgnoreCase(idAkun)) {
                                        found = akun;
                                        break;
                                    }
                                }

                                if (found != null) {
                                    isLoading.setValue(false);
                                    registerSuccess.setValue(true);
                                    repo.saveProfilAkun(found);
                                } else {
                                    errorMessage.setValue("Akun tidak ditemukan setelah register");
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<ModelProfilAkun> call, Throwable t) {
                            isLoading.setValue(false);
                            errorMessage.setValue("Gagal ambil data akun");
                        }
                    });

                } else {
                    isLoading.setValue(false);
                    errorMessage.setValue("Register gagal");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                isLoading.setValue(false);
                errorMessage.setValue("Koneksi error");
            }
        });

    }

    public LiveData<List<DatumAkun>> refresh()
    {
        return repo.getAccounts();
    }

}
