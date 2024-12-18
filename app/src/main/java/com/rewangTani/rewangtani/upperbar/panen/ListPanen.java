package com.rewangTani.rewangtani.upperbar.panen;

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
import com.rewangTani.rewangtani.adapter.adapterupperbar.AdapterListPanen;
import com.rewangTani.rewangtani.bottombar.Home;
import com.rewangTani.rewangtani.databinding.UpperbarPListPanenBinding;
import com.rewangTani.rewangtani.model.modelupperbar.panen.DatumPanen;
import com.rewangTani.rewangtani.model.modelupperbar.panen.ModelPanen;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.DatumRencanaTanam;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.ModelRencanaTanam;
import com.rewangTani.rewangtani.upperbar.kendalapertumbuhan.ListKendalaPertumbuhan;
import com.rewangTani.rewangtani.upperbar.rab.ListRancanganAnggaranBiaya;
import com.rewangTani.rewangtani.upperbar.rencanatanam.ListRencanaTanam;
import com.rewangTani.rewangtani.upperbar.sudahtanam.ListSudahTanam;
import com.rewangTani.rewangtani.utility.PreferenceUtils;
import com.rewangTani.rewangtani.utility.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListPanen extends AppCompatActivity {

    UpperbarPListPanenBinding binding;
    AdapterListPanen itemList;
    ModelPanen modelPanen;
    List<DatumPanen> listPanen = new ArrayList<>();
    List<DatumPanen> listNewPanen = new ArrayList<>();
    List<String> idP = new ArrayList<>();
    List<String> newidP = new ArrayList<>();
    ModelRencanaTanam modelRencanaTanam;
    List<DatumRencanaTanam> listRencanaTanam = new ArrayList<>();
    List<DatumRencanaTanam> listNewRencanaTanam = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.upperbar_p_list_panen);

        getData();

        binding.btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToInputPanen();
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
                AlertDialog.Builder builder = new AlertDialog.Builder(ListPanen.this);
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

        binding.btnRab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRAB();
            }
        });

    }

    private void getData(){
        binding.viewLoading.setVisibility(View.VISIBLE);
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

    public void getRencanaTanam(){
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelRencanaTanam> dataRT = apiInterface.getDataRencanaTanam();
        dataRT.enqueue(new Callback<ModelRencanaTanam>() {
            @Override
            public void onResponse(Call<ModelRencanaTanam> call, Response<ModelRencanaTanam> response) {
                modelRencanaTanam = response.body();
                if (response.body()!=null){

                    for (int i = 0; i < modelRencanaTanam.getTotalData(); i++) {
                        try {
                            if (PreferenceUtils.getIdAkun(getApplicationContext())
                                    .equalsIgnoreCase(modelRencanaTanam.getData().get(i).getIdUser())) {
                                listRencanaTanam.add(modelRencanaTanam.getData().get(i));
                            }
                        } catch (Exception e){ }
                    }
                    if (listRencanaTanam.size()>0){
                        getPanen();
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
                        Toast.makeText(ListPanen.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getPanen() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelPanen> dataRT = apiInterface.getDataPanen();
        dataRT.enqueue(new Callback<ModelPanen>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<ModelPanen> call, Response<ModelPanen> response) {
                modelPanen = response.body();
                if (response.body()!=null){
                    for (int i = 0; i < modelPanen.getTotalData(); i++) {
                        for (int j = 0; j < listRencanaTanam.size(); j++){
                            try {
                                if (modelPanen.getData().get(i).getIdRencanaTanam()
                                        .equalsIgnoreCase(listRencanaTanam.get(j).getIdRencanaTanam())) {
                                    listPanen.add(modelPanen.getData().get(i));
                                }
                            } catch (Exception e){ }

                        }
                    }
                    if (listPanen.size()>0){
                        for (int i=0; i<listPanen.size(); i++){
                            idP.add(listPanen.get(i).getIdPanen());
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.viewLoading.setVisibility(View.GONE);
                                binding.frameDataNotFound.setVisibility(View.VISIBLE);
                                binding.scrollView.setVisibility(View.GONE);
                            }
                        });
                    }

                    if (idP.size()>0){
                        newidP = idP.stream().distinct().collect(Collectors.toList());
                        getNewPanen();
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
            public void onFailure(Call<ModelPanen> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.viewLoading.setVisibility(View.GONE);
                        Toast.makeText(ListPanen.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getNewPanen() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelPanen> dataRT = apiInterface.getDataPanen();
        dataRT.enqueue(new Callback<ModelPanen>() {
            @Override
            public void onResponse(Call<ModelPanen> call, Response<ModelPanen> response) {
                modelPanen = response.body();
                if (response.body()!=null){
                    for (int i = 0; i < modelPanen.getTotalData(); i++) {
                        for (int j = 0; j < listRencanaTanam.size(); j++){
                            try {
                                if (modelPanen.getData().get(i).getIdPanen()
                                        .equalsIgnoreCase(newidP.get(j))) {
                                    listNewPanen.add(modelPanen.getData().get(i));
                                }
                            } catch (Exception e){ }
                        }
                    }
                    if (listNewPanen.size()>0){
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
            }
            @Override
            public void onFailure(Call<ModelPanen> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.viewLoading.setVisibility(View.GONE);
                        Toast.makeText(ListPanen.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getNewRencanaTanam(){
        for (int i = 0; i < listNewPanen.size(); i++) {
            for(int j = 0; j < listRencanaTanam.size(); j++){
                try{
                    if (listNewPanen.get(i).getIdRencanaTanam()
                            .equalsIgnoreCase(listRencanaTanam.get(j).getIdRencanaTanam())){
                        listNewRencanaTanam.add(listRencanaTanam.get(i));
                    }
                }catch (Exception e){}
            }
        }
        if (listNewRencanaTanam.size()>0){
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
                    binding.frameDataNotFound.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    public void setData(){
        itemList = new AdapterListPanen(listNewPanen, listNewRencanaTanam);
        binding.rvHasilPanen.setLayoutManager(new LinearLayoutManager(ListPanen.this));
        binding.rvHasilPanen.setAdapter(itemList);
        binding.rvHasilPanen.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvHasilPanen,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent a = new Intent(ListPanen.this, DetailPanen.class);
                        a.putExtra("idPanen", listPanen.get(position).getIdPanen());
                        startActivity(a);
                    }
                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));
    }

    public void goToInputPanen(){
        Intent a = new Intent(ListPanen.this, InputPanen.class);
        startActivity(a);
        finish();
    }

    public void goToBeranda(){
        Intent a = new Intent(ListPanen.this, Home.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();
    }

    public void goToRT(){
        Intent a = new Intent(ListPanen.this, ListRencanaTanam.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();
    }

    public void goToST(){
        Intent a = new Intent(ListPanen.this, ListSudahTanam.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();
    }

    public void goToKP(){
        Intent a = new Intent(ListPanen.this, ListKendalaPertumbuhan.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();
    }

    public void goToRAB(){
        Intent a = new Intent(ListPanen.this, ListRancanganAnggaranBiaya.class);
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