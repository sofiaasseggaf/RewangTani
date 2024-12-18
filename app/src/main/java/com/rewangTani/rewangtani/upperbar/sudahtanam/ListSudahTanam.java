package com.rewangTani.rewangtani.upperbar.sudahtanam;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.adapter.adapterupperbar.AdapterListSudahTanam;
import com.rewangTani.rewangtani.bottombar.Home;
import com.rewangTani.rewangtani.databinding.UpperbarStListSudahTanamBinding;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.DatumRencanaTanam;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.ModelRencanaTanam;
import com.rewangTani.rewangtani.model.modelupperbar.sudahtanam.DatumSudahTanam;
import com.rewangTani.rewangtani.model.modelupperbar.sudahtanam.ModelSudahTanam;
import com.rewangTani.rewangtani.upperbar.kendalapertumbuhan.ListKendalaPertumbuhan;
import com.rewangTani.rewangtani.upperbar.panen.ListPanen;
import com.rewangTani.rewangtani.upperbar.rab.ListRancanganAnggaranBiaya;
import com.rewangTani.rewangtani.upperbar.rencanatanam.ListRencanaTanam;
import com.rewangTani.rewangtani.utility.PreferenceUtils;
import com.rewangTani.rewangtani.utility.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListSudahTanam extends AppCompatActivity {

    UpperbarStListSudahTanamBinding binding;
    static ListSudahTanam classListSudahTanam = new ListSudahTanam();
    AdapterListSudahTanam itemList;
    ModelSudahTanam modelSudahTanam;
    DatumSudahTanam datumSudahTanam;
    List<DatumSudahTanam> listSudahTanam = new ArrayList<>();
    List<DatumSudahTanam> listNewSudahTanam = new ArrayList<>();
    List<String> idST = new ArrayList<>();
    List<String> newidST = new ArrayList<>();
    ModelRencanaTanam modelRencanaTanam;
    List<DatumRencanaTanam> listRencanaTanam = new ArrayList<>();
    List<DatumRencanaTanam> listNewRencanaTanam = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.upperbar_st_list_sudah_tanam);

        getData();
        initializeNewSudahTanam();

        binding.btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToInputSudahTanam();
            }
        });

        binding.btnRt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRT();
            }
        });

        binding.btnSt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListSudahTanam.this);
                builder.setMessage("Apa yang ingin anda perbarui ?")
                        .setCancelable(true)
                        .setPositiveButton("Proses Tanam", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                goToST();
                            }
                        })

                        .setNegativeButton("Kendala Pertumbuhan", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                goToKP();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        binding.btnPanen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPanen();
            }
        });

        binding.btnRab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRAB();
            }
        });

    }

    public void getData() {
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
                getRencanaTanam();
            }
        }).start();
    }

    public void getRencanaTanam() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelRencanaTanam> dataRT = apiInterface.getDataRencanaTanam();
        dataRT.enqueue(new Callback<ModelRencanaTanam>() {
            @Override
            public void onResponse(Call<ModelRencanaTanam> call, Response<ModelRencanaTanam> response) {
                modelRencanaTanam = response.body();
                if (response.body() != null) {

                    for (int i = 0; i < modelRencanaTanam.getTotalData(); i++) {
                        try {
                            if (PreferenceUtils.getIdAkun(getApplicationContext())
                                    .equalsIgnoreCase(modelRencanaTanam.getData().get(i).getIdUser())) {
                                listRencanaTanam.add(modelRencanaTanam.getData().get(i));
                            }
                        } catch (Exception e) {
                        }
                    }
                    if (listRencanaTanam.size() > 0) {
                        getSudahTanam();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.viewLoading.setVisibility(View.GONE);
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelRencanaTanam> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.viewLoading.setVisibility(View.GONE);
                        Toast.makeText(ListSudahTanam.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getSudahTanam() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelSudahTanam> dataRT = apiInterface.getDataSudahTanam();
        dataRT.enqueue(new Callback<ModelSudahTanam>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<ModelSudahTanam> call, Response<ModelSudahTanam> response) {
                modelSudahTanam = response.body();
                if (response.body() != null) {
                    for (int i = 0; i < modelSudahTanam.getTotalData(); i++) {
                        for (int j = 0; j < listRencanaTanam.size(); j++) {
                            try {
                                if (modelSudahTanam.getData().get(i).getIdRencanaTanam()
                                        .equalsIgnoreCase(listRencanaTanam.get(j).getIdRencanaTanam())) {
                                    listSudahTanam.add(modelSudahTanam.getData().get(i));
                                }
                            } catch (Exception e) {
                            }
                        }
                    }

                    if (listSudahTanam.size() > 0) {

                        idST.clear();
                        newidST.clear();

                        for (int i = 0; i < listSudahTanam.size(); i++) {
                            idST.add(listSudahTanam.get(i).getIdRencanaTanam());
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.viewLoading.setVisibility(View.GONE);
                                binding.frameDataNotFound.setVisibility(View.VISIBLE);
                            }
                        });
                    }

                    if (idST.size() > 0) {
                        newidST = idST.stream().distinct().collect(Collectors.toList());
                        getNewSudahTanam();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.viewLoading.setVisibility(View.GONE);
                                binding.frameDataNotFound.setVisibility(View.VISIBLE);
                            }
                        });
                    }

                }
            }

            @Override
            public void onFailure(Call<ModelSudahTanam> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.viewLoading.setVisibility(View.GONE);
                        Toast.makeText(ListSudahTanam.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getNewSudahTanam() {
        for (int i = 0; i < newidST.size(); i++) {
            for (int j = 0; j < listSudahTanam.size(); j++) {
                try {
                    if (newidST.get(i).equalsIgnoreCase(listSudahTanam.get(j).getIdRencanaTanam())) {
                        listNewSudahTanam.add(listSudahTanam.get(j));
                        i++;
                    }
                } catch (Exception e) {
                }
            }
        }
        if (listNewSudahTanam.size() > 0) {
            getNewRencanaTanam();
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    binding.viewLoading.setVisibility(View.GONE);
                }
            });
        }
    }

    public void getNewRencanaTanam() {
        for (int i = 0; i < listRencanaTanam.size(); i++) {
            for (int j = 0; j < listNewSudahTanam.size(); j++) {
                try {
                    if (listRencanaTanam.get(i).getIdRencanaTanam()
                            .equalsIgnoreCase(listNewSudahTanam.get(j).getIdRencanaTanam())) {
                        listNewRencanaTanam.add(listRencanaTanam.get(i));
                    }
                } catch (Exception e) {
                }
            }
        }


        if (listNewRencanaTanam.size() > 0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    binding.viewLoading.setVisibility(View.GONE);
                    binding.scrollView.setVisibility(View.VISIBLE);
                    setData();
                }
            });
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    binding.viewLoading.setVisibility(View.GONE);
                }
            });
        }

    }

    public DatumSudahTanam getDatumSudahTanam() {
        return datumSudahTanam;
    }

    public void initializeNewSudahTanam() {
        this.datumSudahTanam = new DatumSudahTanam( "", "", "", "", "", "", "", "", "", "", "", "", "", "", "","", "", "", "", "","", "", "", "", "","", "", "", "", false, "");
    }

    public void setDetailSudahTanam(Context context, DatumSudahTanam data){
        if (datumSudahTanam == null) {
            initializeNewSudahTanam();
        }

//        if (!data.getIdSudahTanam().equalsIgnoreCase("")){ datumSudahTanam.setIdSudahTanam(data.getIdSudahTanam()); }
        if (!data.getIdRencanaTanam().equalsIgnoreCase("")){ datumSudahTanam.setIdRencanaTanam(data.getIdRencanaTanam()); }
        if (!data.getIdProfilLahan().equalsIgnoreCase("")){ datumSudahTanam.setIdProfilLahan(data.getIdProfilLahan());}
        if (!data.getIdPertumbuhanNormal().equalsIgnoreCase("")){ datumSudahTanam.setIdPertumbuhanNormal(data.getIdPertumbuhanNormal()); }
        if (!data.getIdKendalaPertumbuhan().equalsIgnoreCase("")){ datumSudahTanam.setIdKendalaPertumbuhan(data.getIdKendalaPertumbuhan()); }
        if (!data.getIdBiayaburuhTanam().equalsIgnoreCase("")){ datumSudahTanam.setIdBiayaburuhTanam(data.getIdBiayaburuhTanam()); }
        if (!data.getIdBiayaburuhBajak().equalsIgnoreCase("")){ datumSudahTanam.setIdBiayaburuhBajak(data.getIdBiayaburuhBajak()); }
        if (!data.getIdBiayaburuhSemprot().equalsIgnoreCase("")){ datumSudahTanam.setIdBiayaburuhSemprot(data.getIdBiayaburuhSemprot()); }
        if (!data.getIdBiayaburuhMenyiangirumput().equalsIgnoreCase("")){ datumSudahTanam.setIdBiayaburuhMenyiangirumput(data.getIdBiayaburuhMenyiangirumput()); }
        if (!data.getIdBiayaburuhGalangan().equalsIgnoreCase("")){ datumSudahTanam.setIdBiayaburuhGalangan(data.getIdBiayaburuhGalangan()); }
        if (!data.getIdBiayaburuhPupuk().equalsIgnoreCase("")){ datumSudahTanam.setIdBiayaburuhPupuk(data.getIdBiayaburuhPupuk()); }
        if (!data.getIdBiayaburuhPanen().equalsIgnoreCase("")){ datumSudahTanam.setIdBiayaburuhPanen(data.getIdBiayaburuhPanen()); }

        if (!data.getIdSewamesinBajak().equalsIgnoreCase("")){ datumSudahTanam.setIdSewamesinBajak(data.getIdSewamesinBajak()); }
        if (!data.getIdSewamesinTanam().equalsIgnoreCase("")){ datumSudahTanam.setIdSewamesinTanam(data.getIdSewamesinTanam()); }
        if (!data.getIdSewamesinPanen().equalsIgnoreCase("")){ datumSudahTanam.setIdSewamesinPanen(data.getIdSewamesinPanen()); }
        if (!data.getIdSewamesinPompa().equalsIgnoreCase("")){ datumSudahTanam.setIdSewamesinPompa(data.getIdSewamesinPompa()); }
        if (!data.getIdSewamesinPompaBbm().equalsIgnoreCase("")){ datumSudahTanam.setIdSewamesinPompaBbm(data.getIdSewamesinPompaBbm()); }

        if (!data.getIdBiayabibitLocalHet().equalsIgnoreCase("")){ datumSudahTanam.setIdBiayabibitLocalHet(data.getIdBiayabibitLocalHet()); }
        if (!data.getIdBiayabibitSubsidi().equalsIgnoreCase("")){ datumSudahTanam.setIdBiayabibitSubsidi(data.getIdBiayabibitSubsidi()); }

        if (!data.getIdBiayapupukKimiaLocalHet().equalsIgnoreCase("")){ datumSudahTanam.setIdBiayapupukKimiaLocalHet(data.getIdBiayapupukKimiaLocalHet()); }
        if (!data.getIdBiayapupukKimiaPhonska().equalsIgnoreCase("")){ datumSudahTanam.setIdBiayapupukKimiaPhonska(data.getIdBiayapupukKimiaPhonska()); }
        if (!data.getIdBiayapupukOrganik().equalsIgnoreCase("")){ datumSudahTanam.setIdBiayapupukOrganik(data.getIdBiayapupukOrganik()); }
        if (!data.getIdBiayapupukKimiaUrea().equalsIgnoreCase("")){ datumSudahTanam.setIdBiayapupukKimiaUrea(data.getIdBiayapupukKimiaUrea()); }
        if (!data.getIdBiayapupukKimiaFosfat().equalsIgnoreCase("")){ datumSudahTanam.setIdBiayapupukKimiaFosfat(data.getIdBiayapupukKimiaFosfat()); }

        if (!data.getIdObatKimiaLocal().equalsIgnoreCase("")){ datumSudahTanam.setIdObatKimiaLocal(data.getIdObatKimiaLocal()); }
//        if (!data.getIdObatKimiaSubsidi().equalsIgnoreCase("")){ datumSudahTanam.setIdObatKimiaSubsidi(data.getIdObatKimiaSubsidi()); }
        if (!data.getIdObatOrganik().equalsIgnoreCase("")){ datumSudahTanam.setIdObatOrganik(data.getIdObatOrganik()); }
        if (!data.getIdBiayaobatKimiaLocalHet().equalsIgnoreCase("")){ datumSudahTanam.setIdBiayaobatKimiaLocalHet(data.getIdBiayaobatKimiaLocalHet()); }
//        if (!data.getIdBiayaobatKimiaSubsidi().equalsIgnoreCase("")){ datumSudahTanam.setIdBiayaobatKimiaSubsidi(data.getIdBiayaobatKimiaSubsidi()); }
        if (!data.getIdBiayaobatOrganik().equalsIgnoreCase("")){ datumSudahTanam.setIdBiayaobatOrganik(data.getIdBiayaobatOrganik()); }
        datumSudahTanam.setWithPompa(data.isWithPompa());
        if (!data.getNamaObatOrganik().equalsIgnoreCase("")) { datumSudahTanam.setNamaObatOrganik(data.getNamaObatOrganik()); }
    }

    public static ListSudahTanam getInstance() {
        return classListSudahTanam;
    }

    public void setData() {
        itemList = new AdapterListSudahTanam(listNewSudahTanam, listNewRencanaTanam);
        binding.rvSudahTanam.setLayoutManager(new LinearLayoutManager(ListSudahTanam.this));
        binding.rvSudahTanam.setAdapter(itemList);
        binding.rvSudahTanam.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvSudahTanam,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent a = new Intent(ListSudahTanam.this, UpdateSudahTanamRT.class);
                        a.putExtra("idRencanaTanam", listNewRencanaTanam.get(position).getIdRencanaTanam());
                        startActivity(a);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                    }
                }));
    }

    public void goToInputSudahTanam() {
        Intent a = new Intent(ListSudahTanam.this, InputSudahTanamA.class);
        startActivity(a);
        finish();
    }

    public void goToRT() {
        Intent a = new Intent(ListSudahTanam.this, ListRencanaTanam.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();
    }

    public void goToST() {
        Intent a = new Intent(ListSudahTanam.this, ListSudahTanam.class);
        startActivity(a);
        finish();
    }

    public void goToKP() {
        Intent a = new Intent(ListSudahTanam.this, ListKendalaPertumbuhan.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }

    public void goToPanen() {
        Intent a = new Intent(ListSudahTanam.this, ListPanen.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }

    public void goToRAB() {
        Intent a = new Intent(ListSudahTanam.this, ListRancanganAnggaranBiaya.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }

    public void goToBeranda() {
        Intent a = new Intent(ListSudahTanam.this, Home.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();
    }

    @Override
    public void onBackPressed() {
        goToBeranda();
    }
}