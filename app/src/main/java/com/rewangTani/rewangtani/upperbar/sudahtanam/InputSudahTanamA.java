package com.rewangTani.rewangtani.upperbar.sudahtanam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.data.entity.rencanatanam.DatumRencanaTanam;
import com.rewangTani.rewangtani.databinding.UpperbarStInputSudahTanamABinding;
import com.rewangTani.rewangtani.model.modelnoneditable.komoditas.DatumKomoditas;
import com.rewangTani.rewangtani.model.modelnoneditable.varietas.DatumVarietas;
import com.rewangTani.rewangtani.model.modelprofillahan.DatumProfilLahan;
import com.rewangTani.rewangtani.data.entity.sudahtanam.DatumSudahTanam;
import com.rewangTani.rewangtani.ui.home.HomeViewModel;
import com.rewangTani.rewangtani.upperbar.rencanatanam.ListRencanaTanam;
import com.rewangTani.rewangtani.utility.DialogUtil;
import com.rewangTani.rewangtani.utility.Global;

import java.util.ArrayList;
import java.util.List;

public class InputSudahTanamA extends AppCompatActivity
{

    UpperbarStInputSudahTanamABinding binding;
    private HomeViewModel viewModel;
    List<DatumProfilLahan> profilLahanList;
    List<DatumKomoditas> komoditasList;
    List<DatumVarietas> varietasList;
    DatumRencanaTanam datumRencanaTanam;
    String namaRT, idSistemIrigasi;
    boolean isWithPompa;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.upperbar_st_input_sudah_tanam_a);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        initEvent();
        initObserver();
    }

    private void initEvent()
    {

        binding.btnSelanjutnya.setOnClickListener( v -> {
            if ( datumRencanaTanam != null ) {
                saveLocalData();
            } else {
                Toast.makeText(InputSudahTanamA.this, "Data Tidak Ditemukan", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initObserver()
    {

        viewModel.fetchAllRencanaTanamData((listRencanaTanamFetched, listProfileLahanFetched,
                                            listKomoditasFetched, listVarietasFetched) ->
        {

            runOnUiThread(() -> {
                setRencanaTanam(listRencanaTanamFetched);
                this.profilLahanList = listProfileLahanFetched;
                this.komoditasList = listKomoditasFetched;
                this.varietasList = listVarietasFetched;
            });
        });
    }

    private void setRencanaTanam(List<DatumRencanaTanam> rencanaTanamList)
    {
        if (rencanaTanamList != null && !rencanaTanamList.isEmpty())
        {
            List<String> listNamaRencanaTanam = new ArrayList<>();
            for (DatumRencanaTanam rencanaTanam : rencanaTanamList)
            {
                listNamaRencanaTanam.add(rencanaTanam.getNamaRencanaTanam());
            }

            ArrayAdapter<String> adapterRT = new ArrayAdapter<>(InputSudahTanamA.this, R.layout.z_spinner_list, listNamaRencanaTanam);
            adapterRT.setDropDownViewResource(R.layout.z_spinner_list);
            binding.spRencanaTanam.setAdapter(adapterRT);

            initListenerSpinnerRencanaTanam(rencanaTanamList);
        }
    }

    private void initListenerSpinnerRencanaTanam(List<DatumRencanaTanam> rencanaTanamList)
    {

        binding.spRencanaTanam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                namaRT = binding.spRencanaTanam.getSelectedItem().toString();
                for (DatumRencanaTanam rencanaTanam : rencanaTanamList){
                    try {
                        if (rencanaTanam.getNamaRencanaTanam().equalsIgnoreCase(namaRT)){
                            datumRencanaTanam = rencanaTanam;
                            setDataPL();
                        }
                    } catch (Exception e){}
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) { }
        });
    }

    private void setDataPL()
    {
        for (DatumProfilLahan profilLahan : profilLahanList)
        {
            if(profilLahan.getIdProfileTanah().equalsIgnoreCase(datumRencanaTanam.getIdProfilTanah()))
            {
                idSistemIrigasi = profilLahan.getIdSistemIrigasi();
                binding.txtProfilLahan.setText(profilLahan.getNamaProfilTanah());
                setData();
            }
        }
    }

    private void setData()
    {
        for (DatumKomoditas komoditas : komoditasList)
        {
            if(komoditas.getIdKomoditas().equalsIgnoreCase(datumRencanaTanam.getIdKomoditas()))
            {
                binding.txtKomoditas.setText(komoditas.getNamaKomoditas());
            }
        }

        for (DatumVarietas varietas : varietasList)
        {
            if(varietas.getIdVarietas().equalsIgnoreCase(datumRencanaTanam.getIdVarietas()))
            {
                binding.txtVarietas.setText(varietas.getNamaVarietas());
            }
        }
    }

    private void saveLocalData()
    {

        isWithPompa = !idSistemIrigasi.equalsIgnoreCase(Global.SI_B_PERMUKAAN);

        DatumSudahTanam datumSudahTanam = new DatumSudahTanam(datumRencanaTanam.getIdRencanaTanam(), datumRencanaTanam.getIdProfilTanah(), "", "", "", "", "", "", "", "", "", "", "", "", "","", "", "", "", "","", "", "", "", "","", "", "", "", isWithPompa, "");
        ListSudahTanam.getInstance().setDetailSudahTanam(getApplicationContext(), datumSudahTanam);
        moveToB();
    }

    public void moveToB(){
        Intent a = new Intent(InputSudahTanamA.this, InputSudahTanamB.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }

    public  void goToListST(){
        Intent a = new Intent(InputSudahTanamA.this, ListSudahTanam.class);
        startActivity(a);
        finish();
    }

    public  void goToRencanaTanam(){
        Intent a = new Intent(InputSudahTanamA.this, ListRencanaTanam.class);
        startActivity(a);
        finish();
    }

    @Override
    public void onBackPressed()
    {
        DialogUtil.showCustomAlertDialog(
                InputSudahTanamA.this,
                getString(R.string.confirm_batal_input_st),
                okButton -> {
                    viewModel.clearDraftRencanaTanam();
                    goToListST();
                } );
    }

}