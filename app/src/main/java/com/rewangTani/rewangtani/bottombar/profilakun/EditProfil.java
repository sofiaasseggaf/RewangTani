package com.rewangTani.rewangtani.bottombar.profilakun;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
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

import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.model.modelakunprofil.DatumProfil;
import com.rewangTani.rewangtani.model.modelakunprofil.ModelProfilAkun;
import com.rewangTani.rewangtani.model.modelnoneditable.alamat.DatumAlamat;
import com.rewangTani.rewangtani.model.modelnoneditable.alamat.ModelAlamat;
import com.rewangTani.rewangtani.model.modelnoneditable.statuspekerja.ModelStatusPekerja;
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
    ModelProfilAkun modelProfilAkun;
    DatumProfil dataProfil;
    ModelAlamat modelAlamat;
    DatumAlamat dataAlamat;
    ModelStatusPekerja modelStatusPekerja;
    List<String> listStatusPekerja = new ArrayList<>();
    List<DatumAlamat> listAlamat = new ArrayList<>();
    List<String> listKabKota = new ArrayList<String>();
    List<String> listKec = new ArrayList<String>();
    List<String> listKel = new ArrayList<String>();
    List<String> listkodepos = new ArrayList<String>();
    List<String> listProvinsi = new ArrayList<String>();
    String provinsi, kabkota, kecamatan, kelurahan;
    String idAlamat = "";
    String idAlamat2 = "";
    String jenis_kelamin = "";
    String status_pekerja = "";
    String[] gender;
    Calendar myCalendar;
    ArrayAdapter<String> adapterProvinsi, adapterKabKota, adapterKec, adapterKel, adapterKodepos;
    int testTelp, testNIK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.bottombar_pa_editprofil);

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

        binding.spinnerStatusPekerja.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        });

        binding.spinnerProvinsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //rl_kab_kota.setBackgroundResource(R.drawable.bg_spinner_off);
                binding.spinnerKabKota.setEnabled(false);
                binding.spinnerKabKota.setText("");
                //rl_kec.setBackgroundResource(R.drawable.bg_spinner_off);
                binding.spinnerKecamatan.setEnabled(false);
                binding.spinnerKecamatan.setText("");
                //rl_kel.setBackgroundResource(R.drawable.bg_spinner_off);
                binding.spinnerKelurahan.setEnabled(false);
                binding.spinnerKelurahan.setText("");
                //rl_kodepos.setBackgroundResource(R.drawable.bg_spinner_off);
                binding.spinnerKodepos.setEnabled(false);
                binding.spinnerKodepos.setText("");

                binding.spinnerProvinsi.showDropDown();
            }
        });

        binding.spinnerProvinsi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    if (adapterProvinsi != null) {
                        adapterProvinsi.getFilter().filter(charSequence);
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                //rl_kab_kota.setBackgroundResource(R.drawable.bg_spinner_off);
                binding.spinnerKabKota.setEnabled(false);
                binding.spinnerKabKota.setText("");
                //rl_kec.setBackgroundResource(R.drawable.bg_spinner_off);
                binding.spinnerKecamatan.setEnabled(false);
                binding.spinnerKecamatan.setText("");
                //rl_kel.setBackgroundResource(R.drawable.bg_spinner_off);
                binding.spinnerKelurahan.setEnabled(false);
                binding.spinnerKelurahan.setText("");
                //rl_kodepos.setBackgroundResource(R.drawable.bg_spinner_off);
                binding.spinnerKodepos.setEnabled(false);
                binding.spinnerKodepos.setText("");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                provinsi = binding.spinnerProvinsi.getText().toString();
                listKabKota.clear();
                for (int a = 0; a < listAlamat.size(); a++) {
                    if (listAlamat.get(a).getProvinsi().equalsIgnoreCase(provinsi)) {
                        listKabKota.add(listAlamat.get(a).getKota());
                    }
                }
                if (listKabKota.size() > 0) {
                    for (int i = 0; i < listKabKota.size(); i++) {
                        for (int j = i + 1; j < listKabKota.size(); j++) {
                            if (listKabKota.get(i).equalsIgnoreCase(listKabKota.get(j))) {
                                listKabKota.remove(j);
                                j--;
                            }
                        }
                    }

                    //rl_kab_kota.setBackgroundResource(R.drawable.bg_spinner);
                    binding.spinnerKabKota.setEnabled(true);
                    adapterKabKota = new ArrayAdapter<String>(
                            EditProfil.this, R.layout.z_spinner_list, listKabKota);
                    binding.spinnerKabKota.setThreshold(1);
                    binding.spinnerKabKota.setAdapter(adapterKabKota);

                }
            }
        });

        binding.spinnerKabKota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //rl_kec.setBackgroundResource(R.drawable.bg_spinner_off);
                binding.spinnerKecamatan.setEnabled(false);
                binding.spinnerKecamatan.setText("");
                //rl_kel.setBackgroundResource(R.drawable.bg_spinner_off);
                binding.spinnerKelurahan.setEnabled(false);
                binding.spinnerKelurahan.setText("");
                //rl_kodepos.setBackgroundResource(R.drawable.bg_spinner_off);
                binding.spinnerKodepos.setEnabled(false);
                binding.spinnerKodepos.setText("");

                binding.spinnerKabKota.showDropDown();
            }
        });

        binding.spinnerKabKota.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    adapterKabKota.getFilter().filter(charSequence);
                } catch (Exception e) {
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                //rl_kec.setBackgroundResource(R.drawable.bg_spinner_off);
                binding.spinnerKecamatan.setEnabled(false);
                binding.spinnerKecamatan.setText("");
                //rl_kel.setBackgroundResource(R.drawable.bg_spinner_off);
                binding.spinnerKelurahan.setEnabled(false);
                binding.spinnerKelurahan.setText("");
                //rl_kodepos.setBackgroundResource(R.drawable.bg_spinner_off);
                binding.spinnerKodepos.setEnabled(false);
                binding.spinnerKodepos.setText("");

            }

            @Override
            public void afterTextChanged(Editable editable) {
                kabkota = binding.spinnerKabKota.getText().toString();
                listKec.clear();
                for (int a = 0; a < listAlamat.size(); a++) {
                    if (listAlamat.get(a).getKota().equalsIgnoreCase(kabkota)) {
                        listKec.add(listAlamat.get(a).getKecamatan());
                    }
                }

                if (listKec.size() > 0) {
                    for (int i = 0; i < listKec.size(); i++) {
                        for (int j = i + 1; j < listKec.size(); j++) {
                            if (listKec.get(i).equalsIgnoreCase(listKec.get(j))) {
                                listKec.remove(j);
                                j--;
                            }
                        }
                    }

                    //rl_kec.setBackgroundResource(R.drawable.bg_spinner);
                    binding.spinnerKecamatan.setEnabled(true);
                    adapterKec = new ArrayAdapter<String>(
                            EditProfil.this, R.layout.z_spinner_list, listKec);
                    binding.spinnerKecamatan.setThreshold(1);
                    binding.spinnerKecamatan.setAdapter(adapterKec);

                }
            }
        });

        binding.spinnerKecamatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //rl_kel.setBackgroundResource(R.drawable.bg_spinner_off);
                binding.spinnerKelurahan.setEnabled(false);
                binding.spinnerKelurahan.setText("");
                //rl_kodepos.setBackgroundResource(R.drawable.bg_spinner_off);
                binding.spinnerKodepos.setEnabled(false);
                binding.spinnerKodepos.setText("");

                binding.spinnerKecamatan.showDropDown();
            }
        });

        binding.spinnerKecamatan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    if (adapterKec != null) {
                        adapterKec.getFilter().filter(charSequence);
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                //rl_kel.setBackgroundResource(R.drawable.bg_spinner_off);
                binding.spinnerKelurahan.setEnabled(false);
                binding.spinnerKelurahan.setText("");
                //rl_kodepos.setBackgroundResource(R.drawable.bg_spinner_off);
                binding.spinnerKodepos.setEnabled(false);
                binding.spinnerKodepos.setText("");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                kecamatan = binding.spinnerKecamatan.getText().toString();
                listKel.clear();
                for (int a = 0; a < listAlamat.size(); a++) {
                    if (listAlamat.get(a).getKecamatan().equalsIgnoreCase(kecamatan)) {
                        listKel.add(listAlamat.get(a).getKelurahan());
                    }
                }

                if (listKel.size() > 0) {
                    for (int i = 0; i < listKel.size(); i++) {
                        for (int j = i + 1; j < listKel.size(); j++) {
                            if (listKel.get(i).equalsIgnoreCase(listKel.get(j))) {
                                listKel.remove(j);
                                j--;
                            }
                        }
                    }

                    //rl_kel.setBackgroundResource(R.drawable.bg_spinner);
                    binding.spinnerKelurahan.setEnabled(true);
                    adapterKel = new ArrayAdapter<String>(
                            EditProfil.this, R.layout.z_spinner_list, listKel);
                    binding.spinnerKelurahan.setThreshold(1);
                    binding.spinnerKelurahan.setAdapter(adapterKel);

                }
            }
        });

        binding.spinnerKelurahan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //rl_kodepos.setBackgroundResource(R.drawable.bg_spinner_off);
                binding.spinnerKodepos.setEnabled(false);
                binding.spinnerKodepos.setText("");

                binding.spinnerKelurahan.showDropDown();
            }
        });

        binding.spinnerKelurahan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    if (adapterKel != null) {
                        adapterKel.getFilter().filter(charSequence);
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                //rl_kodepos.setBackgroundResource(R.drawable.bg_spinner_off);
                binding.spinnerKodepos.setEnabled(false);
                binding.spinnerKodepos.setText("");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                kelurahan = binding.spinnerKelurahan.getText().toString();
                for (int a = 0; a < listAlamat.size(); a++) {
                    if (listAlamat.get(a).getKelurahan().equalsIgnoreCase(kelurahan)) {
                        idAlamat2 = listAlamat.get(a).getIdAlamat();
                        break;
                    }
                }


                listkodepos.clear();
                for (int a = 0; a < listAlamat.size(); a++) {
                    if (listAlamat.get(a).getKelurahan().equalsIgnoreCase(kelurahan)) {
                        listkodepos.add(listAlamat.get(a).getKodepos().toString());
                    }
                }

                if (listkodepos.size() > 0) {

                    for (int i = 0; i < listkodepos.size(); i++) {
                        for (int j = i + 1; j < listkodepos.size(); j++) {
                            if (listkodepos.get(i).equalsIgnoreCase(listkodepos.get(j))) {
                                listkodepos.remove(j);
                                j--;
                            }
                        }
                    }

                    //rl_kodepos.setBackgroundResource(R.drawable.bg_spinner);
                    binding.spinnerKodepos.setEnabled(true);
                    adapterKodepos = new ArrayAdapter<String>(
                            EditProfil.this, R.layout.z_spinner_list, listkodepos);
                    binding.spinnerKodepos.setThreshold(1);
                    binding.spinnerKodepos.setAdapter(adapterKodepos);

                }
            }
        });

        binding.spinnerKodepos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.spinnerKodepos.showDropDown();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });

        binding.inputTanggalLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(EditProfil.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        binding.btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.viewLoading).setVisibility(View.VISIBLE);
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
                handler.postDelayed(runnable, 1 * 1000);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        checkNoTelp();
                    }
                }).start();
            }
        });

    }

    public void getData() {
        binding.scrollView.setVerticalScrollBarEnabled(false);
        binding.scrollView.setHorizontalScrollBarEnabled(false);
        findViewById(R.id.viewLoading).setVisibility(View.VISIBLE);
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
        handler.postDelayed(runnable, 1 * 1000);
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
                                if (dataProfil != null) {
                                    getDataStatusPekerja();
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.viewLoading).setVisibility(View.GONE);
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
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(EditProfil.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });

            }
        });
    }

    public void getDataStatusPekerja() {
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
                    if (listStatusPekerja != null) {
                        getDataAlamat();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(EditProfil.this, "Data status pekerja tidak ditemukan", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelStatusPekerja> call, Throwable t) {
                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                Toast.makeText(EditProfil.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    public void getDataAlamat() {
        // masih di new thread
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelAlamat> data = apiInterface.getDataAlamat();
        data.enqueue(new Callback<ModelAlamat>() {
            @Override
            public void onResponse(Call<ModelAlamat> call, Response<ModelAlamat> response) {
                modelAlamat = response.body();
                if (response.body() != null) {
                    listAlamat.clear();
                    for (int i = 0; i < modelAlamat.getTotalData(); i++) {
                        listAlamat.add(modelAlamat.getData().get(i));
                        listProvinsi.add(modelAlamat.getData().get(i).getProvinsi());
                    }
                    if (listAlamat != null && listProvinsi != null) {
                        for (int i = 0; i < listProvinsi.size(); i++) {
                            for (int j = i + 1; j < listProvinsi.size(); j++) {
                                if (listProvinsi.get(i).equalsIgnoreCase(listProvinsi.get(j))) {
                                    listProvinsi.remove(j);
                                    j--;
                                }
                            }
                        }
                        if (dataProfil.getIdAlamat() != null) {
                            if (!dataProfil.getIdAlamat().equalsIgnoreCase("")) {
                                for (int a = 0; a < listAlamat.size(); a++) {
                                    if (listAlamat.get(a).getIdAlamat().equalsIgnoreCase(dataProfil.getIdAlamat())) {
                                        dataAlamat = listAlamat.get(a);
                                        idAlamat = listAlamat.get(a).getIdAlamat();
                                    }
                                }
                            }
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                setData();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                Toast.makeText(EditProfil.this, "Data alamat tidak ditemukan", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelAlamat> call, Throwable t) {
                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                Toast.makeText(EditProfil.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    public void setData() {
        binding.inputNamaBelakang.setText(PreferenceUtils.getNamaDepan(getApplicationContext()));
        binding.inputNamaBelakang.setText(PreferenceUtils.getNamaBelakang(getApplicationContext()));
        binding.inputUsername.setText(PreferenceUtils.getUsername(getApplicationContext()));
        binding.inputAlamat.setText(dataProfil.getAlamat());
        binding.inputTanggalLahir.setText(dataProfil.getTglLahir());
        binding.inputNoTelepon.setText(dataProfil.getTelepon());
        binding.inputNik.setText(dataProfil.getNik());
        setSpinnerStatusPekerja();
        //hideKeyboard(getParent());
    }

    public void setSpinnerStatusPekerja() {

        ArrayAdapter<String> adapterStatusPekerja = new ArrayAdapter<String>(EditProfil.this, R.layout.z_spinner_list, listStatusPekerja);
        binding.spinnerStatusPekerja.setAdapter(adapterStatusPekerja);

        if (dataProfil.getIdStatusPekerja() != null) {
            for (int i = 0; i < modelStatusPekerja.getTotalData(); i++) {
                if (dataProfil.getIdStatusPekerja().equalsIgnoreCase(modelStatusPekerja.getData().get(i).getIdStatusPekerja())) {
                    status_pekerja = dataProfil.getIdStatusPekerja();
                    // set di spinner yg sesuai
                }
            }
        }

        setSpinnerJk();
    }

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

        if (idAlamat.equalsIgnoreCase("")) {
            setSpinnerProvinsi();
        } else {
            setdataSpinner();
        }

    }

    public void setdataSpinner() {
        binding.spinnerProvinsi.setText(dataAlamat.getProvinsi());
        binding.spinnerKabKota.setText(dataAlamat.getKota());
        binding.spinnerKecamatan.setText(dataAlamat.getKecamatan());
        binding.spinnerKelurahan.setText(dataAlamat.getKelurahan());
        binding.spinnerKodepos.setText(dataAlamat.getKodepos().toString());
        setSpinnerProvinsi();
    }

    private void setSpinnerProvinsi() {
        adapterProvinsi = new ArrayAdapter<String>(EditProfil.this, R.layout.z_spinner_list, listProvinsi);
        binding.spinnerProvinsi.setThreshold(1);
        binding.spinnerProvinsi.setAdapter(adapterProvinsi);
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
                                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
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
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
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
                                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
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
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
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
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
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
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
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
                                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
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
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
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
                                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
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
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
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
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
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
                    findViewById(R.id.viewLoading).setVisibility(View.GONE);
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
                    findViewById(R.id.viewLoading).setVisibility(View.GONE);
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
                    findViewById(R.id.viewLoading).setVisibility(View.GONE);
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
                    findViewById(R.id.viewLoading).setVisibility(View.GONE);
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
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                try {
                    //Log.d("tag", rawResponse.body().string());
                    if (rawResponse.body() != null) {
                        getDataProfile2();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
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
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
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
                                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
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
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Batal Edit Profil ?")
                .setCancelable(false)
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        goToBerandaProfil();
                    }
                })

                .setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}