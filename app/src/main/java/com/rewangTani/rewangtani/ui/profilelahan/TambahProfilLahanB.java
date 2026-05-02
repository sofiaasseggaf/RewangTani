package com.rewangTani.rewangtani.ui.profilelahan;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.model.wilayah.City;
import com.rewangTani.rewangtani.model.wilayah.District;
import com.rewangTani.rewangtani.model.wilayah.Province;
import com.rewangTani.rewangtani.model.wilayah.Village;
import com.rewangTani.rewangtani.databinding.BottombarPlTambahProfilLahanBBinding;
import com.rewangTani.rewangtani.model.modelnoneditable.alamat.DatumAlamat;
import com.rewangTani.rewangtani.utility.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

public class TambahProfilLahanB extends FragmentActivity {

    private ProfileLahanViewModel viewModel;
    BottombarPlTambahProfilLahanBBinding binding;
    String idAlamat;
    List<DatumAlamat> listAlamat = new ArrayList<>();
    List<String> listKec = new ArrayList<String>();
    List<String> listKel = new ArrayList<String>();
    List<String> listkodepos = new ArrayList<String>();

    String provinsi, kabkota, kecamatan, kelurahan;
    Integer kodepos;
    ArrayAdapter<String> adapterProvinsi, adapterKabKota, adapterKec, adapterKel, adapterKodepos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.bottombar_pl_tambah_profil_lahan_b);
        viewModel = new ViewModelProvider(this).get(ProfileLahanViewModel.class);

        initListener();
        setDefaultSpinner();
        checkLocalData();
        observeViewModel();
    }

    private void initListener()
    {
        binding.btnSelanjutnya.setOnClickListener(view -> {
            if (idAlamat != null){
                saveLocalData();
            } else {
                Toast.makeText(TambahProfilLahanB.this, "Lengkapi Alamat Terlebih Dahulu !", Toast.LENGTH_SHORT).show();
            }
        });

        binding.horizontalScrollView.post(new Runnable() {
            @Override
            public void run() {
                binding.horizontalScrollView.scrollTo(binding.btnNamaLahan.getLeft(), binding.btnNamaLahan.getTop());
            }
        });

        binding.spProvinsi.setOnClickListener(view -> binding.spProvinsi.showDropDown());
        binding.spProvinsi.setOnItemClickListener((p, v, pos, id) -> {
            Province item = (Province) p.getItemAtPosition(pos);
            viewModel.loadKabupaten(item.getId());
        });
        binding.spProvinsi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try{
                    if ( adapterProvinsi != null ) {
                        adapterProvinsi.getFilter().filter(charSequence);
                    }
                } catch (Exception e) { }
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                if (binding.spProvinsi.getText().toString().equalsIgnoreCase("")) {
                    viewModel.isKabEnabled.setValue(false);
                }
            }
        });

        binding.spKabKota.setOnClickListener(view -> binding.spKabKota.showDropDown());
        binding.spKabKota.setOnItemClickListener((p, v, pos, id) -> {
            City item = (City) p.getItemAtPosition(pos);
            viewModel.loadKecamatan(item.getId());
        });
        binding.spKabKota.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try{
                    if ( adapterKabKota != null ) {
                        adapterKabKota.getFilter().filter(charSequence);
                    }
                } catch (Exception e) { }
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                if (binding.spKabKota.getText().toString().equalsIgnoreCase("")) {
                    viewModel.isKecEnabled.setValue(false);
                }
            }
        });

        binding.spKecamatan.setOnClickListener(view -> binding.spKecamatan.showDropDown());
        binding.spKecamatan.setOnItemClickListener((p, v, pos, id) -> {
            District item = (District) p.getItemAtPosition(pos);
            viewModel.loadKelurahan(item.getId());
        });
        binding.spKecamatan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try{
                    if ( adapterKec != null ) {
                        adapterKec.getFilter().filter(charSequence);
                    }
                } catch (Exception e) { }
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                if (binding.spKecamatan.getText().toString().equalsIgnoreCase("")) {
                    viewModel.isKelEnabled.setValue(false);
                }
            }
        });

        binding.spKelurahan.setOnClickListener(view -> binding.spKelurahan.showDropDown());
        binding.spKelurahan.setOnItemClickListener((p, v, pos, id) -> {
            Village item = (Village) p.getItemAtPosition(pos);
            idAlamat = item.getId();
        });
        binding.spKelurahan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try{
                    if ( adapterKel != null ) {
                        adapterKel.getFilter().filter(charSequence);
                    }
                } catch (Exception e) { }
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) { }
        });

/*        binding.spKodePos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.spKodePos.showDropDown();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });
        binding.spKodePos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if (!binding.spKodePos.getText().toString().equalsIgnoreCase("")) {
                    kodepos = Integer.valueOf(binding.spKodePos.getText().toString());
                    for (int a=0; a<listAlamat.size(); a++){
                        if (listAlamat.get(a).getKodepos().equals(kodepos)){
                            idAlamat = listAlamat.get(a).getIdAlamat();
                            break;
                        }
                    }
                }
            }
        });*/
    }

    private void setDefaultSpinner()
    {
        binding.rlKabKota.setBackgroundResource(R.drawable.rect_blank_fill_gray_radius);
        binding.spKabKota.setEnabled(false);
        binding.spKabKota.setText("");
        binding.rlKecamatan.setBackgroundResource(R.drawable.rect_blank_fill_gray_radius);
        binding.spKecamatan.setEnabled(false);
        binding.spKecamatan.setText("");
        binding.rlKelurahan.setBackgroundResource(R.drawable.rect_blank_fill_gray_radius);
        binding.spKelurahan.setEnabled(false);
        binding.spKelurahan.setText("");
        binding.rlKodePos.setBackgroundResource(R.drawable.rect_blank_fill_gray_radius);
        binding.spKodePos.setEnabled(false);
        binding.spKodePos.setText("");
    }

    private void observeViewModel()
    {
        viewModel.loadProvinsi();

        viewModel.isKabEnabled.observe(this, b -> {
            binding.spKabKota.setEnabled(b);
            if (!b) {
                binding.rlKabKota.setBackgroundResource(R.drawable.rect_blank_fill_gray_radius);
                binding.spKabKota.setText("");
                viewModel.isKecEnabled.setValue(false);
                viewModel.isKelEnabled.setValue(false);
            } else {
                binding.rlKabKota.setBackgroundResource(R.drawable.rect_input_blank_green_radius);
            }
        });

        viewModel.isKecEnabled.observe(this, b -> {
            binding.spKecamatan.setEnabled(b);
            if (!b) {
                binding.rlKecamatan.setBackgroundResource(R.drawable.rect_blank_fill_gray_radius);
                binding.spKecamatan.setText("");
                viewModel.isKelEnabled.setValue(false);
            } else {
                binding.rlKecamatan.setBackgroundResource(R.drawable.rect_input_blank_green_radius);
            }
        });

        viewModel.isKelEnabled.observe(this, b -> {
            binding.spKelurahan.setEnabled(b);
            if (!b) {
                binding.rlKelurahan.setBackgroundResource(R.drawable.rect_blank_fill_gray_radius);
                binding.spKelurahan.setText("");
            } else {
                binding.rlKelurahan.setBackgroundResource(R.drawable.rect_input_blank_green_radius);
            }
        });

        viewModel.provList.observe(this, provinces -> {
            List<Province> listProvince = new ArrayList<>();
            if (!provinces.isEmpty()) {
                listProvince.addAll(provinces);
            }
            binding.spProvinsi.setAdapter(new ArrayAdapter<>(this, R.layout.z_spinner_list, listProvince));
            binding.spProvinsi.setThreshold(1);
        });

        viewModel.kabList.observe(this, cities -> {
            List<City> listCity = new ArrayList<>();
            if (cities.size() > 0) {
                listCity.addAll(cities);
            }
            binding.spKabKota.setAdapter(new ArrayAdapter<>(this, R.layout.z_spinner_list, listCity));
            binding.spKabKota.setThreshold(1);
        });

        viewModel.kecList.observe(this, districts -> {
            List<District> listDistrict = new ArrayList<>();
            if (districts.size() > 0) {
                listDistrict.addAll(districts);
            }
            binding.spKecamatan.setAdapter(new ArrayAdapter<>(this, R.layout.z_spinner_list, listDistrict));
            binding.spKecamatan.setThreshold(1);
        });

        viewModel.kelList.observe(this, villages -> {
            List<Village> listVillages = new ArrayList<>();
            if (villages.size() > 0) {
                listVillages.addAll(villages);
            }
            binding.spKelurahan.setAdapter(new ArrayAdapter<>(this, R.layout.z_spinner_list, listVillages));
            binding.spKelurahan.setThreshold(1);
        });
    }

    private void checkLocalData()
    {
        if (!PreferenceUtils.getPLProvinsi(getApplicationContext()).equalsIgnoreCase("")){
            binding.spProvinsi.setText(PreferenceUtils.getPLProvinsi(getApplicationContext()));
        } else {
            viewModel.loadProvinsi();
        }
        if (!PreferenceUtils.getPLKabupaten(getApplicationContext()).equalsIgnoreCase("")){
            binding.spKabKota.setText(PreferenceUtils.getPLKabupaten(getApplicationContext()));
        }
        if (!PreferenceUtils.getPLKecamatan(getApplicationContext()).equalsIgnoreCase("")){
            binding.spKecamatan.setText(PreferenceUtils.getPLKecamatan(getApplicationContext()));
        }
        if (!PreferenceUtils.getPLKelurahan(getApplicationContext()).equalsIgnoreCase("")){
            binding.spKelurahan.setText(PreferenceUtils.getPLKelurahan(getApplicationContext()));
        }
    }

    private void saveLocalData()
    {
        PreferenceUtils.savePLidAlamat(idAlamat, getApplicationContext());
        PreferenceUtils.savePLProvinsi(provinsi, getApplicationContext());
        PreferenceUtils.savePLKabupaten(kabkota, getApplicationContext());
        PreferenceUtils.savePLKecamatan(kecamatan, getApplicationContext());
        PreferenceUtils.savePLKelurahan(kelurahan, getApplicationContext());
        PreferenceUtils.savePLKodepos(String.valueOf(kodepos), getApplicationContext());
        goToTambahProfilLahanC();
    }

    public void goToTambahProfilLahanC()
    {
        Intent a = new Intent(TambahProfilLahanB.this, TambahProfilLahanC.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
    }

    public void goToTambahProfilLahanA()
    {
        Intent a = new Intent(TambahProfilLahanB.this, TambahProfilLahanA.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed()
    {
        goToTambahProfilLahanA();
    }

}