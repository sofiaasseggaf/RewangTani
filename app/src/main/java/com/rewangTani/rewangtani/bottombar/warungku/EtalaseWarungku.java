package com.rewangTani.rewangtani.bottombar.warungku;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.adapter.adapterbottombar.AdapterListWarungku;
import com.rewangTani.rewangtani.bottombar.Home;
import com.rewangTani.rewangtani.bottombar.pesan.InboxPesan;
import com.rewangTani.rewangtani.bottombar.profilakun.BerandaProfile;
import com.rewangTani.rewangtani.bottombar.profilakun.EditProfil;
import com.rewangTani.rewangtani.bottombar.profilelahan.ListProfileLahan;
import com.rewangTani.rewangtani.databinding.BottombarWarungkuEtalaseWarungkuBinding;
import com.rewangTani.rewangtani.model.modelakunprofil.DataProfilById;
import com.rewangTani.rewangtani.model.modelproduk.DatumProduk;
import com.rewangTani.rewangtani.model.modelproduk.ModelProduk;
import com.rewangTani.rewangtani.model.modelwarungwarung.modelpupukpestisida.DatumPupukPestisida;
import com.rewangTani.rewangtani.model.modelwarungwarung.modelpupukpestisida.ModelPupukPestisida;
import com.rewangTani.rewangtani.model.modelwarungwarung.modelsewamesin.DatumSewaMesin;
import com.rewangTani.rewangtani.model.modelwarungwarung.modelsewamesin.ModelSewaMesin;
import com.rewangTani.rewangtani.model.modelwarungwarung.modeltenagakerja.DatumTenagaKerja;
import com.rewangTani.rewangtani.model.modelwarungwarung.modeltenagakerja.ModelTenagaKerja;
import com.rewangTani.rewangtani.utility.PreferenceUtils;
import com.rewangTani.rewangtani.utility.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EtalaseWarungku extends AppCompatActivity {

    BottombarWarungkuEtalaseWarungkuBinding binding;
    DataProfilById dataProfilById;
    ModelProduk modelProduk;
    List<DatumProduk> listDataProduk = new ArrayList<DatumProduk>();
    List<DatumProduk> listDataProdukSorted = new ArrayList<DatumProduk>();
    List<DatumProduk> listDataProdukSortednoDuplicate = new ArrayList<DatumProduk>();
    List<DatumSewaMesin> sewaMesinList = new ArrayList<>();
    List<DatumTenagaKerja> tenagaKerjaList = new ArrayList<>();
    List<DatumPupukPestisida> pupukPestisidaList = new ArrayList<>();
    List<Integer> urutanJumlah = new ArrayList<>();
    AdapterListWarungku itemList;
    int checkKelengkapan = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.bottombar_warungku_etalase_warungku);

        getData();

        binding.btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToBeranda();
            }
        });

        binding.btnLahan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToProfilLahan();
            }
        });

        binding.btnPesan.setOnClickListener(v->{
            goToPesan();
        });

        binding.btnAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToProfilAkun();
            }
        });

        binding.btnPesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToPesanan();
            }
        });

        binding.btnTambahProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToTambahWarungku();
                if (checkKelengkapan == 1) {
                    goToTambahWarungku();
                } else if (checkKelengkapan == 0) {
                    binding.viewBelumPunya.setVisibility(View.GONE);
                    View customLayout = getLayoutInflater().inflate(R.layout.dialog_lengkapi_profil, null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(EtalaseWarungku.this);
                    builder.setView(customLayout);
                    RelativeLayout buttonOk = customLayout.findViewById(R.id.btn_lengkapi_data_profil);
                    RelativeLayout buttonCancel = customLayout.findViewById(R.id.btn_kembali);
                    buttonOk.setOnClickListener(v -> {
                        goToEditProfil();
                    });
                    buttonCancel.setOnClickListener(v -> {
                        goToEtalase();
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

    }

    private void getData() {
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
        handler.postDelayed(runnable, 1 * 1000);
        new Thread(new Runnable() {
            @Override
            public void run() {
                getDataProfileWithId();
            }
        }).start();
    }

    public void getDataProfileWithId() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<DataProfilById> dataRT = apiInterface.getDatumProfilAkun(PreferenceUtils.getIdProfil(getApplicationContext()));
        dataRT.enqueue(new Callback<DataProfilById>() {
            @Override
            public void onResponse(Call<DataProfilById> call, Response<DataProfilById> response) {
                dataProfilById = response.body();
                if (dataProfilById.getData().getTelepon() != null && dataProfilById.getData().getNik() != null &&
                        dataProfilById.getData().getIdAlamat() != null && dataProfilById.getData().getAlamat() != null &&
                        dataProfilById.getData().getGender() != null && dataProfilById.getData().getTglLahir() != null) {
                    checkKelengkapan = 1;
                    getDataEtalase();
                } else {
                    checkKelengkapan = 0;
                    getDataEtalase();

                }
            }

            @Override
            public void onFailure(Call<DataProfilById> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(EtalaseWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getDataEtalase() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelProduk> dataRT = apiInterface.getDataProduk();
        dataRT.enqueue(new Callback<ModelProduk>() {
            @Override
            public void onResponse(Call<ModelProduk> call, Response<ModelProduk> response) {
                modelProduk = response.body();
                if (response.body() != null) {
                    try {
                        for (int i = 0; i < modelProduk.getTotalData(); i++) {
                            String idp = modelProduk.getData().get(i).getIdProfil();
                            if (idp.equalsIgnoreCase(PreferenceUtils.getIdProfil(getApplicationContext()))) {
                                listDataProduk.add(modelProduk.getData().get(i));
                            }
                        }
                    } catch (Exception e) {
                    }
                    if (listDataProduk.size() > 0) {
                        //sortData();
                        getDataSewaMesin();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                findViewById(R.id.view_belum_punya).setVisibility(View.VISIBLE);
                                Toast.makeText(EtalaseWarungku.this, "Anda belum memiliki produk", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelProduk> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(EtalaseWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getDataSewaMesin() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelSewaMesin> dataRT = apiInterface.getDataWarungSewaMesin();
        dataRT.enqueue(new Callback<ModelSewaMesin>() {
            @Override
            public void onResponse(Call<ModelSewaMesin> call, Response<ModelSewaMesin> response) {
                ModelSewaMesin modelSewaMesin = response.body();
                if (response.body() != null) {
                    sewaMesinList.clear();
//                    for (int i = 0; i < listDataProdukSortednoDuplicate.size(); i++) {
//                        for (int j = 0; j < modelSewaMesin.getTotalData(); j++) {
//                            if (listDataProdukSortednoDuplicate.get(i).getIdProduk().equalsIgnoreCase(modelSewaMesin.getData().get(j).getIdProduk())) {
//                                sewaMesinList.add(modelSewaMesin.getData().get(j));
//                            }
//                        }
//                    }
                    for (int i = 0; i < listDataProduk.size(); i++) {
                        for (int j = 0; j < modelSewaMesin.getTotalData(); j++) {
                            if (listDataProduk.get(i).getIdProduk().equalsIgnoreCase(modelSewaMesin.getData().get(j).getIdProduk())) {
                                sewaMesinList.add(modelSewaMesin.getData().get(j));
                            }
                        }
                    }
                    getDataTenagaKerja();
                }
            }

            @Override
            public void onFailure(Call<ModelSewaMesin> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(EtalaseWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getDataTenagaKerja() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelTenagaKerja> dataRT = apiInterface.getDataWarungTenagaKerja();
        dataRT.enqueue(new Callback<ModelTenagaKerja>() {
            @Override
            public void onResponse(Call<ModelTenagaKerja> call, Response<ModelTenagaKerja> response) {
                ModelTenagaKerja modelTenagaKerja = response.body();
                if (response.body() != null) {
                    tenagaKerjaList.clear();
//                    for (int i = 0; i < listDataProdukSortednoDuplicate.size(); i++) {
//                        for (int j = 0; j < modelTenagaKerja.getTotalData(); j++) {
//                            if (listDataProdukSortednoDuplicate.get(i).getIdProduk().equalsIgnoreCase(modelTenagaKerja.getData().get(j).getIdProduk())) {
//                                tenagaKerjaList.add(modelTenagaKerja.getData().get(j));
//                            }
//                        }
//                    }
                    for (int i = 0; i < listDataProduk.size(); i++) {
                        for (int j = 0; j < modelTenagaKerja.getTotalData(); j++) {
                            if (listDataProduk.get(i).getIdProduk().equalsIgnoreCase(modelTenagaKerja.getData().get(j).getIdProduk())) {
                                tenagaKerjaList.add(modelTenagaKerja.getData().get(j));
                            }
                        }
                    }
                    getDataPupukPestisida();
                }
            }

            @Override
            public void onFailure(Call<ModelTenagaKerja> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(EtalaseWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getDataPupukPestisida() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelPupukPestisida> dataRT = apiInterface.getDataWarungBibitPupukPestisida();
        dataRT.enqueue(new Callback<ModelPupukPestisida>() {
            @Override
            public void onResponse(Call<ModelPupukPestisida> call, Response<ModelPupukPestisida> response) {
                ModelPupukPestisida modelPupukPestisida = response.body();
                if (response.body() != null) {
                    pupukPestisidaList.clear();
//                    for (int i = 0; i < listDataProdukSortednoDuplicate.size(); i++) {
//                        for (int j = 0; j < modelPupukPestisida.getTotalData(); j++) {
//                            if (listDataProdukSortednoDuplicate.get(i).getIdProduk().equalsIgnoreCase(modelPupukPestisida.getData().get(j).getIdProduk())) {
//                                pupukPestisidaList.add(modelPupukPestisida.getData().get(j));
//                            }
//                        }
//                    }
                    for (int i = 0; i < listDataProduk.size(); i++) {
                        for (int j = 0; j < modelPupukPestisida.getTotalData(); j++) {
                            if (listDataProduk.get(i).getIdProduk().equalsIgnoreCase(modelPupukPestisida.getData().get(j).getIdProduk())) {
                                pupukPestisidaList.add(modelPupukPestisida.getData().get(j));
                            }
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.viewLoading).setVisibility(View.GONE);
                            setAllData();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ModelPupukPestisida> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(EtalaseWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }


/*    public void sortData() {

        urutanJumlah.clear();
        listDataProdukSorted.clear();

        listDataProdukSorted = new ArrayList<>();

        if (listDataProduk.size() > 0) {
            for (int a = 0; a < listDataProduk.size(); a++) {
                urutanJumlah.add(listDataProduk.get(a).getJmlTerjual());
            }

            Collections.sort(urutanJumlah);
            Collections.reverse(urutanJumlah);

            for (int z = 0; z < urutanJumlah.size(); z++) {
                for (int x = 0; x < listDataProduk.size(); x++) {
                    if (listDataProduk.get(x).getJmlTerjual() == urutanJumlah.get(z)) {
                        listDataProdukSorted.add(listDataProduk.get(x));
                    }
                }
            }
        }
        if (listDataProdukSorted.size() > 0) {
            delDuplicate();
        }
    }

    private void delDuplicate() {
        List<String> listId = new ArrayList<>();
        listId.clear();
        for (int i = 0; i < listDataProdukSorted.size(); i++) {
            listId.add(listDataProdukSorted.get(i).getIdProduk());
        }
        Set<String> s = new LinkedHashSet<String>(listId);
        List<String> listIdNew = new ArrayList<>();
        listIdNew.addAll(s);
        for (int j = 0; j < listDataProduk.size(); j++) {
            for (int k = 0; k < listIdNew.size(); k++) {
                if (listDataProduk.get(j).getIdProduk() == listIdNew.get(k)) {
                    listDataProdukSortednoDuplicate.add(listDataProduk.get(j));
                }
            }
        }
        if (listDataProdukSortednoDuplicate.size() > 0) {
            getDataSewaMesin();
        }
    }*/

    public void setAllData() {

        //itemList = new AdapterListWarungku(listDataProdukSortednoDuplicate, sewaMesinList, tenagaKerjaList, pupukPestisidaList);
        itemList = new AdapterListWarungku(listDataProduk, sewaMesinList, tenagaKerjaList, pupukPestisidaList);
        binding.rvEtalase.setLayoutManager(new GridLayoutManager(EtalaseWarungku.this, 2));
        binding.rvEtalase.setAdapter(itemList);
        binding.rvEtalase.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvEtalase,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent a = new Intent(EtalaseWarungku.this, EditWarungku.class);
                        //a.putExtra("id", listDataProdukSortednoDuplicate.get(position).getIdProduk());
                        //a.putExtra("tipe", listDataProdukSortednoDuplicate.get(position).getIdTipeProduk());
                        a.putExtra("id", listDataProduk.get(position).getIdProduk());
                        a.putExtra("tipe", listDataProduk.get(position).getIdTipeProduk());
                        startActivity(a);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));
    }

    public void goToPesanan() {
        Intent a = new Intent(EtalaseWarungku.this, PesananWarungku.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();
    }

    public void goToEtalase() {
        Intent a = new Intent(EtalaseWarungku.this, EtalaseWarungku.class);
        startActivity(a);
        finish();
    }

    public void goToEditProfil() {
        Intent a = new Intent(EtalaseWarungku.this, EditProfil.class);
        startActivity(a);
        finish();
    }

    public void goToTambahWarungku() {
        Intent a = new Intent(EtalaseWarungku.this, TambahWarungku.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }

    public void goToBeranda() {
        Intent a = new Intent(EtalaseWarungku.this, Home.class);
        startActivity(a);
        finish();
    }

    public void goToProfilLahan() {
        Intent a = new Intent(EtalaseWarungku.this, ListProfileLahan.class);
        startActivity(a);
        finish();
    }

    public void goToPesan() {
        Intent a = new Intent(EtalaseWarungku.this, InboxPesan.class);
        startActivity(a);
        finish();
    }

    public void goToProfilAkun() {
        Intent a = new Intent(EtalaseWarungku.this, BerandaProfile.class);
        startActivity(a);
        finish();
    }

    public void onBackPressed() {
        goToBeranda();
    }

}