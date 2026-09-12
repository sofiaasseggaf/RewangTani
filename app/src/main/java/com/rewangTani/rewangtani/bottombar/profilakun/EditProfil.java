package com.rewangTani.rewangtani.bottombar.profilakun;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.ArrayMap;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.data.entity.profilakun.DatumProfil;
import com.rewangTani.rewangtani.data.entity.profilakun.ModelProfilAkun;
import com.rewangTani.rewangtani.data.remote.APIService.APIClient;
import com.rewangTani.rewangtani.data.remote.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.databinding.BottombarPaEditprofilBinding;
import com.rewangTani.rewangtani.model.modelnoneditable.statuspekerja.ModelStatusPekerja;
import com.rewangTani.rewangtani.model.wilayah.City;
import com.rewangTani.rewangtani.model.wilayah.District;
import com.rewangTani.rewangtani.model.wilayah.Province;
import com.rewangTani.rewangtani.model.wilayah.Village;
import com.rewangTani.rewangtani.ui.profilelahan.ProfileLahanViewModel;
import com.rewangTani.rewangtani.utility.DialogUtil;
import com.rewangTani.rewangtani.utility.PreferenceUtils;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfil extends AppCompatActivity {

    BottombarPaEditprofilBinding binding;
    private ProfileLahanViewModel viewModel;
    ModelProfilAkun modelProfilAkun;
    DatumProfil dataProfil;
    ModelStatusPekerja modelStatusPekerja;
    List<String> listStatusPekerja = new ArrayList<>();
    String provinsi, kabkota, kecamatan, kelurahan;
    String idAlamat = "";
    String idAlamat2 = "";
    String jenis_kelamin = "";
    String status_pekerja = "";
    String[] gender;
    Calendar myCalendar;
    ArrayAdapter adapterProvinsi,
            adapterKabKota,
            adapterKec,
            adapterKel;
    int testTelp, testNIK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.bottombar_pa_editprofil);
        viewModel = new ViewModelProvider(this).get(ProfileLahanViewModel.class);


        myCalendar = Calendar.getInstance();
        getData();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();
            }
        };

        binding.spinnerJenisKelamin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                String jk = binding.spinnerJenisKelamin.getSelectedItem().toString();
                if (jk.equalsIgnoreCase("Laki - Laki")) {
                    jenis_kelamin = "l";
                } else if (jk.equalsIgnoreCase("Perempuan")) {
                    jenis_kelamin = "p";

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

/*        binding.spinnerStatusPekerja.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                String sp = binding.spinnerStatusPekerja.getSelectedItem().toString();
                for (int i = 0; i < modelStatusPekerja.getTotalData(); i++) {
                    if (modelStatusPekerja.getData().get(i).getNamaStatusPekerja().equalsIgnoreCase(sp)) {
                        status_pekerja = modelStatusPekerja.getData().get(i).getIdStatusPekerja();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });*/

        binding.spinnerProvinsi.setOnClickListener(
                view -> {
                    binding.spinnerProvinsi.requestFocus();
                    binding.spinnerProvinsi.showDropDown();
                }
        );

        binding.spinnerKabKota.setOnClickListener(
                view -> {
                    binding.spinnerKabKota.requestFocus();
                    binding.spinnerKabKota.showDropDown();
                }
        );

        binding.spinnerKecamatan.setOnClickListener(
                view -> {
                    binding.spinnerKecamatan.requestFocus();
                    binding.spinnerKecamatan.showDropDown();
                }
        );

        binding.spinnerKelurahan.setOnClickListener(
                view -> {
                    binding.spinnerKelurahan.requestFocus();
                    binding.spinnerKelurahan.showDropDown();
                }
        );

        binding.spinnerProvinsi.setOnItemClickListener(
                (parent, view, position, id) -> {

            Province item = (Province) parent.getItemAtPosition(position);
            provinsi = item.getName();

            binding.spinnerKabKota.setText("");
            binding.spinnerKecamatan.setText("");
            binding.spinnerKelurahan.setText("");

            binding.spinnerKabKota.setEnabled(false);
            binding.spinnerKecamatan.setEnabled(false);
            binding.spinnerKelurahan.setEnabled(false);

            viewModel.loadKabupaten(item.getId());
            PreferenceUtils.saveIdProvinsi(item.getId(), this);
        });

        binding.spinnerProvinsi.addTextChangedListener(
                new TextWatcher() {

                    @Override
                    public void beforeTextChanged(
                            CharSequence s,
                            int start,
                            int count,
                            int after) {

                        if (adapterProvinsi != null) {
                            adapterProvinsi
                                    .getFilter()
                                    .filter(s);
                        }
                    }

                    @Override
                    public void onTextChanged(
                            CharSequence s,
                            int start,
                            int before,
                            int count) {
                    }

                    @Override
                    public void afterTextChanged(
                            Editable s) {
                    }
                }
        );

        binding.spinnerKabKota.setOnItemClickListener(
                (parent, view, position, id) -> {

                    City item =
                            (City) parent.getItemAtPosition(position);

                    kabkota = item.getName();

                    binding.spinnerKecamatan.setText("");
                    binding.spinnerKelurahan.setText("");

                    binding.spinnerKecamatan.setEnabled(false);
                    binding.spinnerKelurahan.setEnabled(false);

                    viewModel.loadKecamatan(item.getId());
                    PreferenceUtils.saveIdKabupaten(item.getId(), this);
                }
        );

        binding.spinnerKecamatan.setOnClickListener(
                view -> binding.spinnerKecamatan.showDropDown()
        );

        binding.spinnerKecamatan.setOnItemClickListener(
                (parent, view, position, id) -> {

                    District item =
                            (District) parent.getItemAtPosition(position);

                    kecamatan = item.getName();

                    binding.spinnerKelurahan.setText("");
                    binding.spinnerKelurahan.setEnabled(false);

                    viewModel.loadKelurahan(item.getId());
                    PreferenceUtils.saveIdKecamatan(item.getId(), this);
                }
        );

        binding.spinnerKelurahan.setOnClickListener(
                view -> binding.spinnerKelurahan.showDropDown()
        );

        binding.spinnerKelurahan.setOnItemClickListener(
                (parent, view, position, id) -> {

                    Village item =
                            (Village) parent.getItemAtPosition(position);

                    kelurahan = item.getName();

                    idAlamat2 = item.getId();
                    PreferenceUtils.saveIdKelurahan(item.getId(), this);
                }
        );

/*        binding.spinnerKodepos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.spinnerKodepos.showDropDown();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });*/

        binding.inputTanggalLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(EditProfil.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        binding.btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkNoTelp();
            }
        });

    }

    public void getData() {
        binding.scrollView.setVerticalScrollBarEnabled(false);
        binding.scrollView.setHorizontalScrollBarEnabled(false);
        binding.viewLoading.setVisibility(View.VISIBLE);
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int count = 0;

            @Override
            public void run() {
                count++;
                if (count == 1) {
                    binding.textLoading.setText("Tunggu sebentar ya .");
                } else if (count == 2) {
                    binding.textLoading.setText("Tunggu sebentar ya . .");
                } else if (count == 3) {
                    binding.textLoading.setText("Tunggu sebentar ya . . .");
                }
                if (count == 3)
                    count = 0;
                handler.postDelayed(this, 1500);
            }
        };
        handler.postDelayed(runnable, 1000);
        new Thread(new Runnable() {
            @Override
            public void run() {
                getDataProfile();
            }
        }).start();
    }

    public void getDataProfile() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelProfilAkun> dataProfilAkun = apiInterface.getDataProfilAkun();
        dataProfilAkun.enqueue(new Callback<ModelProfilAkun>() {
            @Override
            public void onResponse(Call<ModelProfilAkun> call, Response<ModelProfilAkun> response) {
                modelProfilAkun = response.body();
                if (response.body() != null) {
                    try {
                        for (int i = 0; i < modelProfilAkun.getTotalData(); i++) {
                            if (modelProfilAkun.getData().get(i).getIdProfile().equalsIgnoreCase(PreferenceUtils.getIdProfil(getApplicationContext()))) {
                                dataProfil = modelProfilAkun.getData().get(i);
                                idAlamat = dataProfil.getIdAlamat();
                                if (dataProfil != null) {
                                    setData();
                                    loadAlamatBinderbyte();
                                    binding.viewLoading.setVisibility(View.GONE);
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.viewLoading.setVisibility(View.GONE);
                            Toast.makeText(EditProfil.this, "Data profil tidak ditemukan", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ModelProfilAkun> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.viewLoading.setVisibility(View.GONE);
                        Toast.makeText(EditProfil.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });

            }
        });
    }

   /* public void getDataStatusPekerja() {
        // masih di new thread
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelStatusPekerja> datasp = apiInterface.getStatusPekerja();
        datasp.enqueue(new Callback<ModelStatusPekerja>() {
            @Override
            public void onResponse(Call<ModelStatusPekerja> call, Response<ModelStatusPekerja> response) {
                modelStatusPekerja = response.body();
                if (response.body() != null) {
                    for (int i = 0; i < modelStatusPekerja.getTotalData(); i++) {
                        listStatusPekerja.add(modelStatusPekerja.getData().get(i).getNamaStatusPekerja());
                    }
                    if (!listStatusPekerja.isEmpty()) {
                        setData();
                        loadAlamatBinderbyte();

                        // Don't block the whole Edit Profile page
                        binding.viewLoading.setVisibility(View.GONE);
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.viewLoading.setVisibility(View.GONE);
                                Toast.makeText(EditProfil.this, "Data status pekerja tidak ditemukan", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelStatusPekerja> call, Throwable t) {
                binding.viewLoading.setVisibility(View.GONE);
                Toast.makeText(EditProfil.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }*/

    private void loadAlamatBinderbyte() {

        // =========================
        // PROVINSI
        // =========================
        viewModel.provList.observe(this, provinces -> {

            if (provinces == null || provinces.isEmpty()) {
                return;
            }

            List<Province> listProvince = new ArrayList<>(provinces);

            adapterProvinsi = new ArrayAdapter<>(
                    EditProfil.this,
                    R.layout.z_spinner_list,
                    listProvince
            );

            binding.spinnerProvinsi.setAdapter(adapterProvinsi);
            binding.spinnerProvinsi.setThreshold(1);

            // Province sudah punya data -> enable
            binding.spinnerProvinsi.setEnabled(true);

            String savedProvinceId = PreferenceUtils.getIdProvinsi(this);

            if (!savedProvinceId.equalsIgnoreCase("")) {

                for (Province province : listProvince) {

                    if (province.getId().equalsIgnoreCase(savedProvinceId)) {

                        binding.spinnerProvinsi.setText(
                                province.getName(),
                                false
                        );

                        provinsi = province.getName();

                        // Kabupaten, Kecamatan, Kelurahan
                        // tetap disabled sampai data parent selesai dipilih/load
                        binding.spinnerKabKota.setEnabled(false);
                        binding.spinnerKecamatan.setEnabled(false);
                        binding.spinnerKelurahan.setEnabled(false);

                        viewModel.loadKabupaten(province.getId());

                        break;
                    }
                }
            }
        });


        // =========================
        // KABUPATEN / KOTA
        // =========================
        viewModel.kabList.observe(this, cities -> {

            if (cities == null || cities.isEmpty()) {
                return;
            }

            List<City> listCity = new ArrayList<>(cities);

            adapterKabKota = new ArrayAdapter<>(
                    EditProfil.this,
                    R.layout.z_spinner_list,
                    listCity
            );

            binding.spinnerKabKota.setAdapter(adapterKabKota);
            binding.spinnerKabKota.setThreshold(1);

            // Kabupaten sudah punya data -> enable
            binding.spinnerKabKota.setEnabled(true);

            String savedKabupatenId = PreferenceUtils.getIdKabupaten(this);

            if (!savedKabupatenId.equalsIgnoreCase("")) {

                for (City city : listCity) {

                    if (city.getId().equalsIgnoreCase(savedKabupatenId)) {

                        binding.spinnerKabKota.setText(
                                city.getName(),
                                false
                        );

                        kabkota = city.getName();

                        viewModel.loadKecamatan(city.getId());

                        break;
                    }
                }
            }
        });


        // =========================
        // KECAMATAN
        // =========================
        viewModel.kecList.observe(this, districts -> {

            if (districts == null || districts.isEmpty()) {
                return;
            }

            List<District> listDistrict = new ArrayList<>(districts);

            adapterKec = new ArrayAdapter<>(
                    EditProfil.this,
                    R.layout.z_spinner_list,
                    listDistrict
            );

            binding.spinnerKecamatan.setAdapter(adapterKec);
            binding.spinnerKecamatan.setThreshold(1);

            // Kecamatan sudah punya data -> enable
            binding.spinnerKecamatan.setEnabled(true);

            String savedKecamatanId = PreferenceUtils.getIdKecamatan(this);

            if (!savedKecamatanId.equalsIgnoreCase("")) {

                for (District district : listDistrict) {

                    if (district.getId().equalsIgnoreCase(savedKecamatanId)) {

                        binding.spinnerKecamatan.setText(
                                district.getName(),
                                false
                        );

                        kecamatan = district.getName();

                        viewModel.loadKelurahan(district.getId());

                        break;
                    }
                }
            }
        });


        // =========================
        // KELURAHAN
        // =========================
        viewModel.kelList.observe(this, villages -> {

            if (villages == null || villages.isEmpty()) {
                return;
            }

            List<Village> listVillages = new ArrayList<>(villages);

            adapterKel = new ArrayAdapter<>(
                    EditProfil.this,
                    R.layout.z_spinner_list,
                    listVillages
            );

            binding.spinnerKelurahan.setAdapter(adapterKel);
            binding.spinnerKelurahan.setThreshold(1);

            // Kelurahan sudah punya data -> enable
            binding.spinnerKelurahan.setEnabled(true);

            String savedKelurahanId = PreferenceUtils.getIdKelurahan(this);

            if (!savedKelurahanId.equalsIgnoreCase("")) {

                for (Village village : listVillages) {

                    if (village.getId().equalsIgnoreCase(savedKelurahanId)) {

                        binding.spinnerKelurahan.setText(
                                village.getName(),
                                false
                        );

                        kelurahan = village.getName();

                        // ID alamat yang akan dikirim saat update
                        idAlamat2 = village.getId();

                        break;
                    }
                }
            }
        });


        // =========================
        // START LOAD PROVINSI
        // =========================
        viewModel.loadProvinsi();
    }

    public void setData() {
        binding.inputNamaDepan.setText(PreferenceUtils.getNamaDepan(getApplicationContext()));
        binding.inputNamaBelakang.setText(PreferenceUtils.getNamaBelakang(getApplicationContext()));
        binding.inputUsername.setText(PreferenceUtils.getUsername(getApplicationContext()));
        binding.inputAlamat.setText(dataProfil.getAlamat());
        binding.inputTanggalLahir.setText(dataProfil.getTglLahir());
        binding.inputNoTelepon.setText(dataProfil.getTelepon());
        binding.inputNik.setText(dataProfil.getNik());
        setSpinnerJk();
    }

/*    public void setSpinnerStatusPekerja() {

        ArrayAdapter<String> adapterStatusPekerja = new ArrayAdapter<String>(EditProfil.this, R.layout.z_spinner_list, listStatusPekerja);
        binding.spinnerStatusPekerja.setAdapter(adapterStatusPekerja);

        if (dataProfil.getIdStatusPekerja() != null) {
            for (int i = 0; i < modelStatusPekerja.getTotalData(); i++) {
                if (dataProfil.getIdStatusPekerja().equalsIgnoreCase(modelStatusPekerja.getData().get(i).getIdStatusPekerja())) {
                    status_pekerja = dataProfil.getIdStatusPekerja();
                    // set di spinner yg sesuai
                    break;
                }
            }
        }

        setSpinnerJk();
    }*/

    public void setSpinnerJk() {
        gender = getResources().getStringArray(R.array.gender);
        ArrayAdapter<String> adapterJK = new ArrayAdapter<String>(EditProfil.this, R.layout.z_spinner_list, gender);
        adapterJK.setDropDownViewResource(R.layout.z_spinner_list);
        binding.spinnerJenisKelamin.setAdapter(adapterJK);

        if (dataProfil.getGender() != null) {
            if (dataProfil.getGender().equalsIgnoreCase("l")) {
                binding.spinnerJenisKelamin.setSelection(0);
                jenis_kelamin = "l";
            } else {
                binding.spinnerJenisKelamin.setSelection(1);
                jenis_kelamin = "p";
            }
        }
    }

    private void checkNoTelp() {

        if (dataProfil.getTelepon() != null) {

            if (!binding.inputNoTelepon.getText().toString().equalsIgnoreCase("")) {
                String notelp = binding.inputNoTelepon.getText().toString();
                for (int i = 0; i < modelProfilAkun.getTotalData(); i++) {
                    if (modelProfilAkun.getData().get(i).getTelepon() != null) {
                        if (!modelProfilAkun.getData().get(i).getTelepon().equalsIgnoreCase("")) {
                            if (modelProfilAkun.getData().get(i).getTelepon().equalsIgnoreCase(notelp) &&
                                    !dataProfil.getTelepon().equalsIgnoreCase(notelp)) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        binding.viewLoading.setVisibility(View.GONE);
                                        Toast.makeText(EditProfil.this, "No telepon sudah terpakai", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                testTelp = 1;
                                break;
                            }
                        }
                    }
                }
            } else if (binding.inputNoTelepon.getText().toString().equalsIgnoreCase("")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.viewLoading.setVisibility(View.GONE);
                        Toast.makeText(EditProfil.this, "Isi no telepon terlebih dahulu !", Toast.LENGTH_SHORT).show();
                    }
                });
                testTelp = 1;
            }

        } else {

            if (!binding.inputNoTelepon.getText().toString().equalsIgnoreCase("")) {
                String notelp = binding.inputNoTelepon.getText().toString();
                for (int i = 0; i < modelProfilAkun.getTotalData(); i++) {
                    if (modelProfilAkun.getData().get(i).getTelepon() != null) {
                        if (!modelProfilAkun.getData().get(i).getTelepon().equalsIgnoreCase("")) {
                            if (modelProfilAkun.getData().get(i).getTelepon().equalsIgnoreCase(notelp)) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        binding.viewLoading.setVisibility(View.GONE);
                                        Toast.makeText(EditProfil.this, "No telepon sudah terpakai", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                testTelp = 1;
                                break;
                            }
                        }
                    }
                }
            } else if (binding.inputNoTelepon.getText().toString().equalsIgnoreCase("")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.viewLoading.setVisibility(View.GONE);
                        Toast.makeText(EditProfil.this, "Isi no telepon terlebih dahulu !", Toast.LENGTH_SHORT).show();
                    }
                });
                testTelp = 1;
            }

        }


        if (testTelp != 1) {
            testTelp = 0;
            if (binding.inputNoTelepon.getText().toString().length() < 10) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.viewLoading.setVisibility(View.GONE);
                        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfil.this);
                        builder.setMessage("NO TELEPON TIDAK BOLEH KURANG DARI 10 DIGIT")
                                .setCancelable(false)
                                .setPositiveButton("OKE", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                });
            } else if (binding.inputNoTelepon.getText().toString().length() > 13) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.viewLoading.setVisibility(View.GONE);
                        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfil.this);
                        builder.setMessage("NO TELEPON TIDAK BOLEH LEBIH DARI 13 DIGIT")
                                .setCancelable(false)
                                .setPositiveButton("OKE", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                });
            } else {
                checkNik();
            }
        } else {
            testTelp = 0;
        }
    }

    public void checkNik() {

        if (dataProfil.getNik() != null) {

            if (!binding.inputNik.getText().toString().equalsIgnoreCase("")) {
                String a = binding.inputNik.getText().toString();
                for (int i = 0; i < modelProfilAkun.getTotalData(); i++) {
                    if (modelProfilAkun.getData().get(i).getNik() != null) {
                        if (!modelProfilAkun.getData().get(i).getNik().equalsIgnoreCase("")) {
                            if (modelProfilAkun.getData().get(i).getNik().equalsIgnoreCase(a) &&
                                    !dataProfil.getNik().equalsIgnoreCase(a)) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        binding.viewLoading.setVisibility(View.GONE);
                                        Toast.makeText(EditProfil.this, "NIK sudah terpakai", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                testNIK = 1;
                                break;
                            }
                        }
                    }
                }
            } else if (binding.inputNik.getText().toString().equalsIgnoreCase("")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.viewLoading.setVisibility(View.GONE);
                        Toast.makeText(EditProfil.this, "Isi NIK terlebih dahulu !", Toast.LENGTH_SHORT).show();
                    }
                });
                testNIK = 1;
            }

        } else {

            if (!binding.inputNik.getText().toString().equalsIgnoreCase("")) {
                String a = binding.inputNik.getText().toString();
                for (int i = 0; i < modelProfilAkun.getTotalData(); i++) {
                    if (modelProfilAkun.getData().get(i).getNik() != null) {
                        if (!modelProfilAkun.getData().get(i).getNik().equalsIgnoreCase("")) {
                            if (modelProfilAkun.getData().get(i).getNik().equalsIgnoreCase(a)) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        binding.viewLoading.setVisibility(View.GONE);
                                        Toast.makeText(EditProfil.this, "NIK sudah terpakai", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                testNIK = 1;
                                break;
                            }
                        }
                    }
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.viewLoading.setVisibility(View.GONE);
                        Toast.makeText(EditProfil.this, "Isi NIK terlebih dahulu !", Toast.LENGTH_SHORT).show();
                    }
                });
                testNIK = 1;
            }

        }


        if (testNIK != 1) {
            testNIK = 0;
            if (binding.inputNik.getText().toString().length() < 16) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.viewLoading.setVisibility(View.GONE);
                        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfil.this);
                        builder.setMessage("NIK TIDAK BOLEH KURANG DARI 16")
                                .setCancelable(false)
                                .setPositiveButton("OKE", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                });
            } else {
                checkTTL();
            }
        } else {
            testNIK = 0;
        }
    }

    public void checkTTL() {
        if (!binding.inputTanggalLahir.getText().toString().equalsIgnoreCase("")) {
            checkJenisKelamin();
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    binding.viewLoading.setVisibility(View.GONE);
                    Toast.makeText(EditProfil.this, "Lengkapi tanggal lahir terlebih dahulu !", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void checkJenisKelamin() {
        if (!jenis_kelamin.equalsIgnoreCase("")) {
            checkIdalamat();
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    binding.viewLoading.setVisibility(View.GONE);
                    Toast.makeText(EditProfil.this, "Pilih jenis kelamin terlebih dahulu !", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void checkIdalamat() {

        if (!idAlamat2.equalsIgnoreCase("")) {
            if (!idAlamat.equalsIgnoreCase("")) {
                if (idAlamat.equalsIgnoreCase(idAlamat2)) {
                    idAlamat2 = idAlamat;
                    checkAlamat();
                } else {
                    checkAlamat();
                }
            } else {
                checkAlamat();
            }
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    binding.viewLoading.setVisibility(View.GONE);
                    Toast.makeText(EditProfil.this, "Lengkapi alamat terlebih dahulu !", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    public void checkAlamat() {
        if (!binding.inputAlamat.getText().toString().equalsIgnoreCase("")) {
            updateDataProfile();
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    binding.viewLoading.setVisibility(View.GONE);
                    Toast.makeText(EditProfil.this, "Lengkapi alamat terlebih dahulu !", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void updateDataProfile() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("idProfile", PreferenceUtils.getIdProfil(getApplicationContext()));
        jsonParams.put("idAkun", PreferenceUtils.getIdAkun(getApplicationContext()));
        jsonParams.put("namaDepan", binding.inputNamaDepan.getText().toString());
        jsonParams.put("namaBelakang", binding.inputNamaBelakang.getText().toString());
        jsonParams.put("alamat", binding.inputAlamat.getText().toString());
        jsonParams.put("idAlamat", idAlamat2);
        jsonParams.put("nik", binding.inputNik.getText().toString());
        jsonParams.put("gender", jenis_kelamin);
        jsonParams.put("tglLahir", binding.inputTanggalLahir.getText().toString());
        jsonParams.put("telepon", binding.inputNoTelepon.getText().toString());
        jsonParams.put("idStatusPekerja", status_pekerja);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = apiInterface.updateDataProfilAkun(body);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> rawResponse) {
                try {
                    //Log.d("tag", rawResponse.body().string());
                    if (rawResponse.body() != null) {
                        getDataProfile2();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.viewLoading.setVisibility(View.GONE);
                                Toast.makeText(EditProfil.this, "Gagal ubah profil", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.viewLoading.setVisibility(View.GONE);
                        Toast.makeText(EditProfil.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                    }
                });
                call.cancel();
            }
        });
    }

    public void getDataProfile2() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelProfilAkun> dataProfilAkun = apiInterface.getDataProfilAkun();
        dataProfilAkun.enqueue(new Callback<ModelProfilAkun>() {
            @Override
            public void onResponse(Call<ModelProfilAkun> call, Response<ModelProfilAkun> response) {
                modelProfilAkun = response.body();
                try {
                    for (int i = 0; i < modelProfilAkun.getTotalData(); i++) {
                        if (modelProfilAkun.getData().get(i).getIdProfile().equalsIgnoreCase(PreferenceUtils.getIdProfil(getApplicationContext()))) {
                            dataProfil = modelProfilAkun.getData().get(i);
                            if (dataProfil != null) {
                                PreferenceUtils.saveNamaDepan(dataProfil.getNamaDepan(), getApplicationContext());
                                PreferenceUtils.saveNamaBelakang(dataProfil.getNamaBelakang(), getApplicationContext());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        binding.viewLoading.setVisibility(View.GONE);
                                        Toast.makeText(EditProfil.this, "Berhasil ubah profil", Toast.LENGTH_LONG).show();
                                        goToBerandaProfil();
                                    }
                                });
                            }
                        }
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<ModelProfilAkun> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.viewLoading.setVisibility(View.GONE);
                        Toast.makeText(EditProfil.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        binding.inputTanggalLahir.setText(dateFormat.format(myCalendar.getTime()));
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void goToBerandaProfil() {
        Intent a = new Intent(EditProfil.this, BerandaProfile.class);
        startActivity(a);
        finish();
    }

    public void onBackPressed() {
        DialogUtil.showCustomAlertDialog(
                EditProfil.this,
                "Batal edit profil ?",
                okButton -> { goToBerandaProfil();}
        );
    }
}