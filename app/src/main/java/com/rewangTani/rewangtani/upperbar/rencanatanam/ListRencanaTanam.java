package com.rewangTani.rewangtani.upperbar.rencanatanam;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.adapter.adapterupperbar.AdapterListRencanaTanam;
import com.rewangTani.rewangtani.bottombar.Home;
import com.rewangTani.rewangtani.databinding.UpperbarRtListRencanaTanamBinding;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.DatumRencanaTanam;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.ModelRencanaTanam;
import com.rewangTani.rewangtani.upperbar.kendalapertumbuhan.ListKendalaPertumbuhan;
import com.rewangTani.rewangtani.upperbar.panen.ListPanen;
import com.rewangTani.rewangtani.upperbar.rab.ListRancanganAnggaranBiaya;
import com.rewangTani.rewangtani.upperbar.sudahtanam.ListSudahTanam;
import com.rewangTani.rewangtani.utility.PreferenceUtils;
import com.rewangTani.rewangtani.utility.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListRencanaTanam extends AppCompatActivity {

    UpperbarRtListRencanaTanamBinding binding;
    AdapterListRencanaTanam itemList;
    ModelRencanaTanam modelRencanaTanam;
    List<DatumRencanaTanam> listRencanaTanam = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.upperbar_rt_list_rencana_tanam);

        getData();

        binding.btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToInputRencanaTanam();
            }
        });

        binding.btnSt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListRencanaTanam.this);
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
                AlertDialog alertDialog =builder.create();
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


    private void getData(){
        binding.frameLoading.setVisibility(View.VISIBLE);
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int count = 0;
            @Override
            public void run() {
                count++;
                if (count == 1) {
                    binding.textLoading.setText("Tunggu sebentar ya ."); }
                else if (count == 2) {
                    binding.textLoading.setText("Tunggu sebentar ya . ."); }
                else if (count == 3) {
                    binding.textLoading.setText("Tunggu sebentar ya . . ."); }
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
        String idProfil = PreferenceUtils.getIdProfil(getApplicationContext());
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelRencanaTanam> dataRT = apiInterface.getDataRencanaTanam();
        dataRT.enqueue(new Callback<ModelRencanaTanam>() {
            @Override
            public void onResponse(Call<ModelRencanaTanam> call, Response<ModelRencanaTanam> response) {
                modelRencanaTanam = response.body();
                if (response.body()!=null){
                    for (int i = 0; i < modelRencanaTanam.getTotalData(); i++) {
//                        listRencanaTanam.add(modelRencanaTanam.getData().get(i));
                        try {
                            if (PreferenceUtils.getIdAkun(getApplicationContext())
                                    .equalsIgnoreCase(modelRencanaTanam.getData().get(i).getIdUser())) {
                                listRencanaTanam.add(modelRencanaTanam.getData().get(i));
                            }
                        } catch (Exception e){ }
                    }
                    if (listRencanaTanam.size()>0){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.frameLayout.setVisibility(View.GONE);
                                binding.scrollView.setVisibility(View.VISIBLE);
                                setData();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.frameLoading.setVisibility(View.GONE);
                                binding.frameDataNotFound.setVisibility(View.VISIBLE);
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
                        binding.frameLayout.setVisibility(View.GONE);
                        Toast.makeText(ListRencanaTanam.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void setData(){
        itemList = new AdapterListRencanaTanam(listRencanaTanam);
        binding.rvRencanaTanam.setLayoutManager(new LinearLayoutManager(ListRencanaTanam.this));
        binding.rvRencanaTanam.setAdapter(itemList);
        binding.rvRencanaTanam.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvRencanaTanam,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent a = new Intent(ListRencanaTanam.this, DetailRencanaTanamNonEditable.class);
                        a.putExtra("id", listRencanaTanam.get(position).getIdRencanaTanam());
                        startActivity(a);
                    }
                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));
    }

    public void goToInputRencanaTanam(){
        Intent a = new Intent(ListRencanaTanam.this, InputRencanaTanamA.class);
        startActivity(a);
        finish();
    }

    public void goToBeranda(){
        Intent a = new Intent(ListRencanaTanam.this, Home.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();
    }


    public void goToST(){
        Intent a = new Intent(ListRencanaTanam.this, ListSudahTanam.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }

    public void goToKP(){
        Intent a = new Intent(ListRencanaTanam.this, ListKendalaPertumbuhan.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }

    public void goToPanen(){
        Intent a = new Intent(ListRencanaTanam.this, ListPanen.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }

    public void goToRAB(){
        Intent a = new Intent(ListRencanaTanam.this, ListRancanganAnggaranBiaya.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }

    @Override
    public void onBackPressed() {
        goToBeranda();
    }
}