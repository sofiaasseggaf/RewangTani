package com.rewangTani.rewangtani.upperbar.sudahtanam;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.data.entity.rencanatanam.DatumRencanaTanam;
import com.rewangTani.rewangtani.data.entity.sudahtanam.DatumSudahTanam;
import com.rewangTani.rewangtani.databinding.UpperbarStUpdateSudahTanamABinding;
import com.rewangTani.rewangtani.model.modelnoneditable.komoditas.DatumKomoditas;
import com.rewangTani.rewangtani.model.modelnoneditable.varietas.DatumVarietas;
import com.rewangTani.rewangtani.model.modelprofillahan.DatumProfilLahan;
import com.rewangTani.rewangtani.ui.home.HomeViewModel;
import com.rewangTani.rewangtani.utility.DialogUtil;
import com.rewangTani.rewangtani.utility.Global;

public class UpdateSudahTanamRT extends AppCompatActivity
{

    UpperbarStUpdateSudahTanamABinding binding;
    private HomeViewModel viewModel;
    DatumRencanaTanam datumRencanaTanam;
    String namaRT, idSistemIrigasi;
    boolean isWithPompa;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.upperbar_st_update_sudah_tanam_a);
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
                Toast.makeText(UpdateSudahTanamRT.this, "Data Tidak Ditemukan", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initObserver()
    {
        Intent intent = getIntent();
        String idRencanaTanam = intent.getStringExtra("idRencanaTanam");

        viewModel.fetchAllRencanaTanamData((listRencanaTanamFetched, listProfileLahanFetched,
                                            listKomoditasFetched, listVarietasFetched) ->
        {

            runOnUiThread(() ->
            {
                for (DatumRencanaTanam rencanaTanam : listRencanaTanamFetched) {
                    if (rencanaTanam.getIdRencanaTanam().equalsIgnoreCase(idRencanaTanam)) {
                        binding.txtRencanaTanam.setText(rencanaTanam.getNamaRencanaTanam());
                        datumRencanaTanam = rencanaTanam;
                    }
                }

                if (datumRencanaTanam != null) {
                    for (DatumProfilLahan profilLahan : listProfileLahanFetched) {
                        if(profilLahan.getIdProfileTanah().equalsIgnoreCase(datumRencanaTanam.getIdProfilTanah())) {
                            idSistemIrigasi = profilLahan.getIdSistemIrigasi();
                            binding.txtProfilLahan.setText(profilLahan.getNamaProfilTanah());

                            for (DatumKomoditas komoditas : listKomoditasFetched) {
                                if(komoditas.getIdKomoditas().equalsIgnoreCase(datumRencanaTanam.getIdKomoditas())) {
                                    binding.txtKomoditas.setText(komoditas.getNamaKomoditas());
                                }
                            }

                            for (DatumVarietas varietas : listVarietasFetched) {
                                if(varietas.getIdVarietas().equalsIgnoreCase(datumRencanaTanam.getIdVarietas())) {
                                    binding.txtVarietas.setText(varietas.getNamaVarietas());
                                }
                            }
                        }
                    }
                }
            });
        });
    }

    private void saveLocalData()
    {

        isWithPompa = !idSistemIrigasi.equalsIgnoreCase(Global.SI_B_PERMUKAAN);

        DatumSudahTanam datumSudahTanam = new DatumSudahTanam(datumRencanaTanam.getIdRencanaTanam(), datumRencanaTanam.getIdProfilTanah(), "", "", "", "", "", "", "", "", "", "", "", "", "","", "", "", "", "","", "", "", "", "","", "", "", "", isWithPompa, "");
        ListSudahTanam.getInstance().setDetailSudahTanam(getApplicationContext(), datumSudahTanam);
        moveToB();
    }

    public void setData() {
        /*
        String a = checkDesimal(dataRencanaTanam.getIdBiayaBuruhTanam());
        txt_buruh_tanam2.setText("Biaya Sebelumnya : Rp. "+a);
        String b = checkDesimal(dataRencanaTanam.getIdBiayaBuruhBajak());
        txt_buruh_bajak2.setText("Biaya Sebelumnya : Rp. "+b);
        String c = checkDesimal(dataRencanaTanam.getIdBiayaBuruhSemprot());
        txt_buruh_semprot2.setText("Biaya Sebelumnya : Rp. "+c);
        String d = checkDesimal(dataRencanaTanam.getIdBiayaBuruhMenyiangirumput());
        txt_buruh_menyiangi2.setText("Biaya Sebelumnya : Rp. "+d);
        String e = checkDesimal(dataRencanaTanam.getIdBiayaBuruhGalangan());
        txt_buruh_galengan2.setText("Biaya Sebelumnya : Rp. "+e);
        String f = checkDesimal(dataRencanaTanam.getIdBiayaBuruhPupuk());
        txt_buruh_pupuk2.setText("Biaya Sebelumnya : Rp. "+f);
        String g = checkDesimal(dataRencanaTanam.getIdBiayaBuruhPanen());
        txt_buruh_panen2.setText("Biaya Sebelumnya : Rp. "+g);
        String h = checkDesimal(dataRencanaTanam.getIdSewaMesinBajak());
        txt_mesin_bajak2.setText("Biaya Sebelumnya : Rp. "+h);
        String i = checkDesimal(dataRencanaTanam.getIdSewaMesinPanen());
        txt_mesin_panen2.setText("Biaya Sebelumnya : Rp. "+i);
        String j = checkDesimal(dataRencanaTanam.getIdSewaMesinTanam());
        txt_mesin_tanam2.setText("Biaya Sebelumnya : Rp. "+j);

        if(dataRencanaTanam.getIdSewamesinPompa()!=null){
            String k = checkDesimal(dataRencanaTanam.getIdSewamesinPompa());
            txt_mesin_pompa2.setText("Biaya Sebelumnya : Rp. "+k);
        } else {
            txt_mesin_pompa2.setText("-");
        }

        if (dataRencanaTanam.getIdSewamesinPompaBbm()!=null){
            String l = checkDesimal(dataRencanaTanam.getIdSewamesinPompaBbm());
            txt_mesin_pompa_bbm2.setText("Biaya Sebelumnya : Rp. "+l);
        } else {
            txt_mesin_pompa_bbm2.setText("-");
        }

        String m = checkDesimal(dataRencanaTanam.getIdBiayabibitLocalHet());
        txt_bibit_local2.setText("Biaya Sebelumnya : Rp. "+m);
        String n = checkDesimal(dataRencanaTanam.getIdBiayabibitSubsidi());
        txt_bibit_subsidi2.setText("Biaya Sebelumnya : Rp. "+n);
        //String o = checkDesimal(dataRencanaTanam.getIdBiayapupukKimiaLocalHet());
        //txt_pupuk_kimia_local.setText(o);
        String p = checkDesimal(dataRencanaTanam.getIdBiayapupukKimiaPhonska());
        txt_pupuk_kimia_phonska2.setText("Biaya Sebelumnya : Rp. "+p);
        String q = checkDesimal(dataRencanaTanam.getIdBiayapupukKimiaUrea());
        txt_pupuk_kimia_urea2.setText("Biaya Sebelumnya : Rp. "+q);
        String r = checkDesimal(dataRencanaTanam.getIdBiayapupukKimiaFosfat());
        txt_pupuk_kimia_fosfat2.setText("Biaya Sebelumnya : Rp. "+r);
        String s = checkDesimal(dataRencanaTanam.getIdBiayapupukOrganik());
        txt_pupuk_organik2.setText("Biaya Sebelumnya : Rp. "+s);
*/
    }

    public void moveToB(){
        Intent a = new Intent(UpdateSudahTanamRT.this, InputSudahTanamB.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }

    public  void goToListST(){
        Intent a = new Intent(UpdateSudahTanamRT.this, ListSudahTanam.class);
        startActivity(a);
        finish();
    }

    @Override
    public void onBackPressed()
    {
        DialogUtil.showCustomAlertDialog(
                UpdateSudahTanamRT.this,
                getString(R.string.confirm_batal_input_st),
                okButton -> {
                    viewModel.clearDraftRencanaTanam();
                    goToListST();
                } );
    }
}