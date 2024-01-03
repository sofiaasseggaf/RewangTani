package com.rewangTani.rewangtani.bottombar.warungku;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.adapter.adapterbottombar.AdapterListWarungku;
import com.rewangTani.rewangtani.bottombar.Home;
import com.rewangTani.rewangtani.bottombar.pesan.InboxPesan;
import com.rewangTani.rewangtani.bottombar.profilakun.BerandaProfile;
import com.rewangTani.rewangtani.bottombar.profilakun.EditProfil;
import com.rewangTani.rewangtani.bottombar.profilelahan.ListProfileLahan;
import com.rewangTani.rewangtani.databinding.BottombarWarungkuPesananwarungkuBinding;
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

public class PesananWarungku extends AppCompatActivity {

    BottombarWarungkuPesananwarungkuBinding binding;
    DataProfilById dataProfilById;
    ModelProduk modelProduk;
    List<DatumProduk> listDataProduk = new ArrayList<DatumProduk>();
    List<DatumSewaMesin> sewaMesinList = new ArrayList<>();
    List<DatumTenagaKerja> tenagaKerjaList = new ArrayList<>();
    List<DatumPupukPestisida> pupukPestisidaList = new ArrayList<>();
    AdapterListWarungku itemList;
    int checkKelengkapan = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.bottombar_warungku_pesananwarungku);

        //start();

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

        binding.btnEtalase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToEtalase();
            }
        });

        binding.btnTambahProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToTambahWarungku();
//                if (checkKelengkapan == 1) {
//                    goToTambahWarungku();
//                } else if (checkKelengkapan == 0) {
//                    binding.viewBelumPunya.setVisibility(View.GONE);
//                    View customLayout = getLayoutInflater().inflate(R.layout.dialog_lengkapi_profil, null);
//                    AlertDialog.Builder builder = new AlertDialog.Builder(PesananWarungku.this);
//                    builder.setView(customLayout);
//                    RelativeLayout buttonOk = customLayout.findViewById(R.id.btn_lengkapi_data_profil);
//                    RelativeLayout buttonCancel = customLayout.findViewById(R.id.btn_kembali);
//                    buttonOk.setOnClickListener(v -> {
//                        goToEditProfil();
//                    });
//                    buttonCancel.setOnClickListener(v -> {
//                        goToPesananWarungku();
//                    });
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//                }
            }
        });
    }

    private void start() {
        findViewById(R.id.view_loading).setVisibility(View.VISIBLE);
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int count = 0;

            @Override
            public void run() {
                count++;
                if (count == 1) {
                    binding.textloading.setText("Tunggu sebentar ya .");
                } else if (count == 2) {
                    binding.textloading.setText("Tunggu sebentar ya . .");
                } else if (count == 3) {
                    binding.textloading.setText("Tunggu sebentar ya . . .");
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
                    getDataPesanan();
                } else {
                    checkKelengkapan = 0;
                    getDataPesanan();

                }
            }

            @Override
            public void onFailure(Call<DataProfilById> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.view_loading).setVisibility(View.GONE);
                        Toast.makeText(PesananWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
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
                        getDataSewaMesin();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.view_loading).setVisibility(View.GONE);
                                Toast.makeText(PesananWarungku.this, "Anda belum memiliki produk", Toast.LENGTH_SHORT).show();
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
                        findViewById(R.id.view_loading).setVisibility(View.GONE);
                        Toast.makeText(PesananWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
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
                        findViewById(R.id.view_loading).setVisibility(View.GONE);
                        Toast.makeText(PesananWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
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
                        findViewById(R.id.view_loading).setVisibility(View.GONE);
                        Toast.makeText(PesananWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
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
                            findViewById(R.id.view_loading).setVisibility(View.GONE);
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
                        findViewById(R.id.view_loading).setVisibility(View.GONE);
                        Toast.makeText(PesananWarungku.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getDataPesanan() {
        // if ada pesanan ->
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.view_loading).setVisibility(View.GONE);
            }
        });

        // if ga ada pesanan ->
        //  binding.viewBelumPunya.setVisibility(View.VISIBLE);
    }

/*    public void setDataProfil() {
        if (dataProfilById.getData().getNamaBelakang() != null) {
            txt_nama.setText(dataProfilById.getData().getNamaDepan() + " " + dataProfilById.getData().getNamaBelakang());
        } else {
            txt_nama.setText(dataProfilById.getData().getNamaDepan());
        }
        if (dataProfilById.getData().getAlamat() != null) {
            txt_alamat.setText(dataProfilById.getData().getAlamat());
        } else {
            txt_alamat.setText("LENGKAPI DATA ALAMAT");
        }
        if (dataProfilById.getData().getTelepon() != null) {
            txt_telepon.setText(dataProfilById.getData().getTelepon());
        } else {
            txt_telepon.setText("LENGKAPI DATA NO TELEPON");
        }
        //txt_terjual.setText("Terjual : -");
    }*/

    public void setAllData() {

        itemList = new AdapterListWarungku(listDataProduk, sewaMesinList, tenagaKerjaList, pupukPestisidaList);
        binding.rvPesanan.setLayoutManager(new GridLayoutManager(PesananWarungku.this, 2));
        binding.rvPesanan.setAdapter(itemList);
//        binding.rvPesanan.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvPesanan,
//                new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(View view, int position) {
//                        Intent a = new Intent(PesananWarungku.this, EditWarungku.class);
//                        a.putExtra("id", listDataProduk.get(position).getIdProduk());
//                        a.putExtra("tipe", listDataProduk.get(position).getIdTipeProduk());
//                        startActivity(a);
//                    }
//
//                    @Override
//                    public void onLongItemClick(View view, int position) {
//
//                    }
//                }));
        //setDataProfil();
    }

    public void goToBeranda() {
        Intent a = new Intent(PesananWarungku.this, Home.class);
        startActivity(a);
        finish();
    }

    public void goToPesananWarungku() {
        Intent a = new Intent(PesananWarungku.this, PesananWarungku.class);
        startActivity(a);
        finish();
    }

    public void goToProfilLahan() {
        Intent a = new Intent(PesananWarungku.this, ListProfileLahan.class);
        startActivity(a);
        finish();
    }

    public void goToProfilAkun() {
        Intent a = new Intent(PesananWarungku.this, BerandaProfile.class);
        startActivity(a);
        finish();
    }

    public void goToEtalase() {
        Intent a = new Intent(PesananWarungku.this, EtalaseWarungku.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }

    public void goToEditProfil() {
        Intent a = new Intent(PesananWarungku.this, EditProfil.class);
        startActivity(a);
        finish();
    }

    public void goToPesan() {
        Intent a = new Intent(PesananWarungku.this, InboxPesan.class);
        startActivity(a);
        finish();
    }

    public void goToTambahWarungku() {
        Intent a = new Intent(PesananWarungku.this, TambahWarungku.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }

    public void onBackPressed() {
        goToEtalase();
    }

}