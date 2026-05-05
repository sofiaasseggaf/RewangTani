package com.rewangTani.rewangtani.upperbar.rencanatanam;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.databinding.UpperbarRtInputRencanaTanamABinding;
import com.rewangTani.rewangtani.model.modelnoneditable.komoditas.DatumKomoditas;
import com.rewangTani.rewangtani.model.modelnoneditable.varietas.DatumVarietas;
import com.rewangTani.rewangtani.model.modelprofillahan.DatumProfilLahan;
import com.rewangTani.rewangtani.ui.home.HomeViewModel;
import com.rewangTani.rewangtani.ui.profilelahan.ListProfileLahan;
import com.rewangTani.rewangtani.utility.Global;
import com.rewangTani.rewangtani.utility.DialogUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InputRencanaTanamA extends AppCompatActivity
{

    UpperbarRtInputRencanaTanamABinding binding;
    private HomeViewModel viewModel;
//    List<DatumRencanaTanam> listRencanaTanam = new ArrayList<>();
//    List<DatumProfilLahan> listProfileLahan = new ArrayList<>();
//    List<String> listKomoditas = new ArrayList<String>();
//    List<String> listVarietas = new ArrayList<String>();
    ArrayAdapter<String> adapterProfilLahan, adapterKomoditas, adapterVarietas;
    String namaKomoditas, idKomoditas, namaVarietas, idVarietas, potensiHasilVarietas, namaProfilLahan, idProfilLahan, idSistemIrigasi, luasLahan;
    double mdpl;
    int checkNama;
    boolean isWithPompa;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.upperbar_rt_input_rencana_tanam_a);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        initEvent();
        initObserver();
    }

    private void initEvent()
    {

        binding.btnSelanjutnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                checkNama();
                saveLocalData();
            }
        });
    }

    private void initObserver()
    {

        viewModel.fetchAllRencanaTanamData((listRencanaTanam, listProfileLahanFetched,
                                            listKomoditasFetched, listVarietasFetched) ->
        {

            runOnUiThread(() -> {
                setProfileLahan(listProfileLahanFetched);
                setVarietas(listVarietasFetched);
                setKomoditas(listKomoditasFetched, listVarietasFetched);
            });
        });

        viewModel.getDraftRencanaTanamLiveData().observe(this, draft ->
        {
            if (draft == null) return;

            binding.namaRencanaTanam.setText(draft.namaRencanaTanam);
            luasLahan = draft.luasLahan;
            idProfilLahan = draft.idProfilTanah;
            idKomoditas = draft.idKomoditas;
            idVarietas = draft.idVarietas;
            isWithPompa = draft.withPompa;
            potensiHasilVarietas = draft.potensiHasilVarietas;
        });
    }

    private void setProfileLahan(List<DatumProfilLahan> listProfileLahanFetched)
    {
        if ( listProfileLahanFetched.isEmpty() ) {
            DialogUtil.showCustomAlertDialogTwoButtons(
                    InputRencanaTanamA.this,
                    getString(R.string.txt_dialog_msg_profile_lahan),
                    okButton -> goToProfilLahan(),
                    cancelButton -> goToListRT() );
        } else {
            setSpinnerProfileLahan(listProfileLahanFetched);
            setListenerProfileLahan(listProfileLahanFetched);
        }
    }

    private void setSpinnerProfileLahan(List<DatumProfilLahan> listProfileLahanFetched)
    {
        List<String> listNamaProfileLahan = new ArrayList<>();
        for (DatumProfilLahan profilLahan : listProfileLahanFetched)
        {
            listNamaProfileLahan.add(profilLahan.getNamaProfilTanah());
        }

        adapterProfilLahan = new ArrayAdapter<String>(InputRencanaTanamA.this, R.layout.z_spinner_list, listNamaProfileLahan);
        adapterProfilLahan.setDropDownViewResource(R.layout.z_spinner_list);
        binding.spProfilLahan.setAdapter(adapterProfilLahan);
    }

    private void setListenerProfileLahan(List<DatumProfilLahan> listProfileLahanFetched)
    {

        binding.spProfilLahan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
            {
                namaProfilLahan = binding.spProfilLahan.getSelectedItem().toString();
                for (DatumProfilLahan profilLahan : listProfileLahanFetched)
                {
                    if (profilLahan.getNamaProfilTanah().equalsIgnoreCase(namaProfilLahan)) {
                        idProfilLahan = profilLahan.getIdProfileTanah();
                        idSistemIrigasi = profilLahan.getIdSistemIrigasi();
                        luasLahan = profilLahan.getLuasGarapan();
                        String txtLuasLahan = "Luas lahan : " + luasLahan + " m2";
                        binding.txtLuasLahan.setText(txtLuasLahan);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) { }
        });
    }

    private void setVarietas(List<DatumVarietas> listVarietasFetched)
    {
        if ( listVarietasFetched.isEmpty() ) {
            String txtErrorVarietas = "Rekomendasi varietas = Tidak ditemukan";
            binding.txtRekVarietas.setTextColor(getResources().getColor(R.color.red));
            binding.txtRekVarietas.setText(txtErrorVarietas);
        } else {
            getRekomendasiVarietas(listVarietasFetched);
        }
    }

    private void getRekomendasiVarietas(List<DatumVarietas> varietasList)
    {
        List<String> listRekomendasiVarietas = new ArrayList<>();
        for (DatumVarietas varietas : varietasList) {
            if (varietas.getAnjuranKetinggian() != null && varietas.getAnjuranKetinggian() > 600) {
                listRekomendasiVarietas.add(varietas.getNamaVarietas());
            }
        }

        if (!listRekomendasiVarietas.isEmpty())
        {
            Collections.shuffle(listRekomendasiVarietas);
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            @SuppressLint("MissingPermission")
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            mdpl = location.getVerticalAccuracyMeters();
            if (String.valueOf(mdpl) != null || String.valueOf(mdpl).equalsIgnoreCase("")) {
                mdpl = mdpl * 10;
                String a = String.valueOf(mdpl);
                String txtKetinggianLahan = "Ketinggian lahan : " + a + " mdpl";
                binding.txtMdpl.setText(txtKetinggianLahan);
            }

            String txtRekomendasiVarietas = "Rekomendasi varietas = " + listRekomendasiVarietas.get(0) + ", " + listRekomendasiVarietas.get(1) + ", " + listRekomendasiVarietas.get(2);
            binding.txtRekVarietas.setText(txtRekomendasiVarietas);
        }
    }

    private void setKomoditas(List<DatumKomoditas> listKomoditasFetched, List<DatumVarietas> listVarietasFetched)
    {
        if ( !listKomoditasFetched.isEmpty() ) {
            setDataSpinnerKomoditas(listKomoditasFetched);
            setListenerKomoditas(listKomoditasFetched, listVarietasFetched);
        }
    }

    private void setDataSpinnerKomoditas(List<DatumKomoditas> listKomoditasFetched)
    {
        List<String> listNamaKomoditas = new ArrayList<>();
        for (DatumKomoditas komoditas : listKomoditasFetched)
        {
            listNamaKomoditas.add(komoditas.getNamaKomoditas());
        }

        adapterKomoditas = new ArrayAdapter<>(InputRencanaTanamA.this, R.layout.z_spinner_list, listNamaKomoditas);
        adapterKomoditas.setDropDownViewResource(R.layout.z_spinner_list);
        binding.spKomoditas.setAdapter(adapterKomoditas);
    }

    private void setListenerKomoditas(List<DatumKomoditas> listKomoditasFetched, List<DatumVarietas> listVarietasFetched)
    {
        binding.spKomoditas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
            {
                namaKomoditas = binding.spKomoditas.getSelectedItem().toString();
                for (DatumKomoditas komoditas : listKomoditasFetched)
                {
                    if (komoditas.getNamaKomoditas().equalsIgnoreCase(namaKomoditas)) {
                        idKomoditas = komoditas.getIdKomoditas();
                    }
                }

                setSpinnerVarietas(listVarietasFetched);
                setListenerVarietas(listVarietasFetched);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) { }
        });
    }

    private void setSpinnerVarietas(List<DatumVarietas> listVarietasFetched)
    {
        List<String> listNamaVarietas = new ArrayList<>();
        for (DatumVarietas varietas : listVarietasFetched)
        {
            listNamaVarietas.add(varietas.getNamaVarietas());
        }

        Collections.sort(listNamaVarietas);
        adapterVarietas = new ArrayAdapter<String>(InputRencanaTanamA.this, R.layout.z_spinner_list, listNamaVarietas);
        binding.spVarietas.setThreshold(1);
        binding.spVarietas.setAdapter(adapterVarietas);
    }

    private void setListenerVarietas(List<DatumVarietas> listVarietasFetched)
    {
        binding.spVarietas.setOnClickListener( v -> binding.spVarietas.showDropDown() );

        binding.spVarietas.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (adapterVarietas != null) {
                    adapterVarietas.getFilter().filter(charSequence);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                namaVarietas = binding.spVarietas.getText().toString();
                for (DatumVarietas varietas : listVarietasFetched)
                {
                    if ( varietas.getNamaVarietas().equalsIgnoreCase(namaVarietas) )
                    {
                        idVarietas = varietas.getIdVarietas();
                        potensiHasilVarietas = varietas.getPotensiHasil().toString();
                    }
                }
            }
        });
    }

//    private void checkNama()
//    {
//        if (!binding.namaRencanaTanam.getText().toString().equalsIgnoreCase(""))
//        {
//            if (listRencanaTanam.size() > 0) {
//                for (int i = 0; i < listRencanaTanam.size(); i++) {
//                    if (binding.namaRencanaTanam.getText().toString().equalsIgnoreCase(listRencanaTanam.get(i).getNamaRencanaTanam())) {
//                        Toast.makeText(this, "Nama rencana tanam sudah dipakai", Toast.LENGTH_SHORT).show();
//                        checkNama = 1;
//                        break;
//                    }
//                }
//            } else {
//                checkNama = 0;
//            }
//
//            if (checkNama != 1) {
//                checkNama = 0;
//                if (idProfilLahan != null && idKomoditas != null && idSistemIrigasi != null && idVarietas != null) {
//                    saveLocalData();
//                } else {
//                    Toast.makeText(this, "Lengkapi fields terlebih dahulu", Toast.LENGTH_SHORT).show();
//                }
//            } else {
//                checkNama = 0;
//            }
//
//        } else {
//            Toast.makeText(this, "Isi nama rencana tanam terlebih dahulu !", Toast.LENGTH_SHORT).show();
//        }
//    }

    private void saveLocalData()
    {
        if (!namaProfilLahan.equalsIgnoreCase("") &&
                idProfilLahan != null && idKomoditas != null &&
                idSistemIrigasi != null && idVarietas != null)
        {

            isWithPompa = !idSistemIrigasi.equalsIgnoreCase(Global.SI_B_PERMUKAAN);
            viewModel.updateDraftRencanaTanam(draft -> {
                draft.namaRencanaTanam = binding.namaRencanaTanam.getText().toString();
                draft.idProfilTanah = idProfilLahan;
                draft.idKomoditas = idKomoditas;
                draft.idVarietas = idVarietas;
                draft.withPompa = isWithPompa;
                draft.luasLahan = luasLahan;
                draft.potensiHasilVarietas = potensiHasilVarietas;
            });

            moveToB();
        }
        else
        {
            Toast.makeText(this, "Lengkapi fields terlebih dahulu", Toast.LENGTH_SHORT).show();
        }
    }

    public void moveToB() {
        Intent a = new Intent(InputRencanaTanamA.this, InputRencanaTanamB.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }

    public void goToListRT() {
        Intent a = new Intent(InputRencanaTanamA.this, ListRencanaTanam.class);
        startActivity(a);
        finish();
    }

    public void goToProfilLahan() {
        Intent a = new Intent(InputRencanaTanamA.this, ListProfileLahan.class);
        startActivity(a);
        finish();
    }

    @Override
    public void onBackPressed()
    {
        DialogUtil.showCustomAlertDialog(
                InputRencanaTanamA.this,
                getString(R.string.confirm_batal_input_rt),
                okButton -> {
                    viewModel.clearDraftRencanaTanam();
                    goToListRT();
                } );
    }

}