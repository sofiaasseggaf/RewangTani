package com.rewangTani.rewangtani.upperbar.kendalapertumbuhan;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.adapter.adapterupperbar.AdapterListKendalaPertumbuhan;
import com.rewangTani.rewangtani.bottombar.Home;
import com.rewangTani.rewangtani.model.modelupperbar.kendalapertumbuhan.DatumKendalaPertumbuhan;
import com.rewangTani.rewangtani.model.modelupperbar.kendalapertumbuhan.ModelKendalaPertumbuhan;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.DatumRencanaTanam;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.ModelRencanaTanam;
import com.rewangTani.rewangtani.upperbar.panen.ListPanen;
import com.rewangTani.rewangtani.upperbar.rab.ListRancanganAnggaranBiaya;
import com.rewangTani.rewangtani.upperbar.rencanatanam.ListRencanaTanam;
import com.rewangTani.rewangtani.upperbar.sudahtanam.ListSudahTanam;
import com.rewangTani.rewangtani.utility.PreferenceUtils;
import com.rewangTani.rewangtani.utility.RecyclerItemClickListener;
import com.rewangTani.rewangtani.utility.StringDateComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListKendalaPertumbuhan extends AppCompatActivity {

    AdapterListKendalaPertumbuhan itemList;
    ModelKendalaPertumbuhan modelKendalaPertumbuhan;
    List<DatumKendalaPertumbuhan> listKendalaPertumbuhan = new ArrayList<>();
    List<DatumKendalaPertumbuhan> listKendalaPertumbuhanSorted = new ArrayList<>();
    List<DatumKendalaPertumbuhan> listNewKendalaPertumbuhan = new ArrayList<>();
    List<String> idKP = new ArrayList<>();
    List<String> newidKP = new ArrayList<>();
    List<String> kendala = new ArrayList<>();
    ModelRencanaTanam modelRencanaTanam;
    List<DatumRencanaTanam> listRencanaTanam = new ArrayList<>();
    List<DatumRencanaTanam> listNewRencanaTanam = new ArrayList<>();
    TextView txtload;
    ImageButton btn_tambah, btn_rt, btn_st, btn_panen, btn_rab;
    RecyclerView rvKendalaPertumbuhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upperbar_kp_list_kendala_pertumbuhan);

        btn_tambah = findViewById(R.id.btn_tambah);
        rvKendalaPertumbuhan = findViewById(R.id.rvKendalaPertumbuhan);
        txtload = findViewById(R.id.textloading);
        btn_rt = findViewById(R.id.btn_rt);
        btn_st = findViewById(R.id.btn_st);
        btn_panen = findViewById(R.id.btn_panen);
        btn_rab = findViewById(R.id.btn_rab);

        getData();

        btn_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToInputKendalaPertumbuhan();
            }
        });

        btn_rt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRT();
            }
        });

        btn_st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListKendalaPertumbuhan.this);
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

        btn_panen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPanen();
            }
        });

        btn_rab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRAB();
            }
        });

    }

    public void getData(){
        findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int count = 0;
            @Override
            public void run() {
                count++;
                if (count == 1) {
                    txtload.setText("Tunggu sebentar ya ."); }
                else if (count == 2) {
                    txtload.setText("Tunggu sebentar ya . ."); }
                else if (count == 3) {
                    txtload.setText("Tunggu sebentar ya . . ."); }
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
                        getKendalaPertumbuhan();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
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
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(ListKendalaPertumbuhan.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void getKendalaPertumbuhan() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelKendalaPertumbuhan> dataRT = apiInterface.getDataKendalaPertumbuhan();
        dataRT.enqueue(new Callback<ModelKendalaPertumbuhan>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<ModelKendalaPertumbuhan> call, Response<ModelKendalaPertumbuhan> response) {
                modelKendalaPertumbuhan = response.body();
                if (response.body()!=null){
                    for (int i = 0; i < modelKendalaPertumbuhan.getTotalData(); i++) {
                        for (int j = 0; j < listRencanaTanam.size(); j++){
                            try {
                                if (modelKendalaPertumbuhan.getData().get(i).getIdSudahTanam()
                                        .equalsIgnoreCase(listRencanaTanam.get(j).getIdRencanaTanam()))
                                {
                                    listKendalaPertumbuhan.add(modelKendalaPertumbuhan.getData().get(i));
                                }
                            } catch (Exception e){ }
                        }
                    }

                    if(listKendalaPertumbuhan.size()>0){

                        sortDataKendalaPertumbuhan();

                        /*runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                setData();
                            }
                        });*/

                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                // gaada kendala pertumbuhan dari id akun ini
                            }
                        });
                    }

                    /*if (listKendalaPertumbuhan.size()>0){
                        idKP.clear();
                        newidKP.clear();
                        for(int i=0; i<listKendalaPertumbuhan.size(); i++){
                            idKP.add(listKendalaPertumbuhan.get(i).getIdSudahTanam());
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                // gaada kendala pertumbuhan dari id akun ini
                            }
                        });
                    }

                    if(idKP.size()>0){
                        newidKP = idKP.stream().distinct().collect(Collectors.toList());
                        getNewKendalaPertumbuhan();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                            }
                        });
                    }*/

                }
            }
            @Override
            public void onFailure(Call<ModelKendalaPertumbuhan> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(ListKendalaPertumbuhan.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void sortDataKendalaPertumbuhan(){
        if (listKendalaPertumbuhan.size()>0){
            for(int a=0; a<listKendalaPertumbuhan.size(); a++){
                String b = listKendalaPertumbuhan.get(a).getCreatedDate();
                b.substring(0, b.length() - 6);
                kendala.add(b);
            }
            Collections.sort(kendala, new StringDateComparator());

            for(int z=kendala.size()-1; z>=0; z--){
                // i=2
                String dt = kendala.get(z);
                for (int x=0; x<listKendalaPertumbuhan.size(); x++){
                    if(listKendalaPertumbuhan.get(x).getCreatedDate().equalsIgnoreCase(dt)){
                        listKendalaPertumbuhanSorted.add(listKendalaPertumbuhan.get(x));
                    }
                }
            }


            if (listKendalaPertumbuhanSorted.size()>0){
                sortDuplicate();
            }
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    findViewById(R.id.framelayout).setVisibility(View.GONE);

                }
            });
        }
    }

    public void sortDuplicate(){
        idKP.clear();
        newidKP.clear();

        for(int i=0; i<listKendalaPertumbuhanSorted.size(); i++){
            idKP.add(listKendalaPertumbuhanSorted.get(i).getIdKendalaPertumbuhan());
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            newidKP = idKP.stream().distinct().collect(Collectors.toList());
        }

        for (int i = 0; i < newidKP.size(); i++) {
            for (int j = 0; j < listKendalaPertumbuhanSorted.size(); j++){
                try {
                    if (newidKP.get(i).equalsIgnoreCase(listKendalaPertumbuhanSorted.get(j).getIdKendalaPertumbuhan())) {
                        listNewKendalaPertumbuhan.add(listKendalaPertumbuhanSorted.get(j));
                        i++;
                    }
                } catch (Exception e){ }
            }
        }

        if (listNewKendalaPertumbuhan.size()>0){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    findViewById(R.id.framelayout).setVisibility(View.GONE);
                    setData();
                }
            });
        }

    }


    public void setData(){
        itemList = new AdapterListKendalaPertumbuhan(listKendalaPertumbuhanSorted, listRencanaTanam);
        //itemList = new AdapterListKendalaPertumbuhan(listNewKendalaPertumbuhan, listNewRencanaTanam);
        rvKendalaPertumbuhan.setLayoutManager(new LinearLayoutManager(ListKendalaPertumbuhan.this));
        rvKendalaPertumbuhan.setAdapter(itemList);
        rvKendalaPertumbuhan.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rvKendalaPertumbuhan,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent a = new Intent(ListKendalaPertumbuhan.this, DetailKendalaPertumbuhan.class);
                        a.putExtra("id", listKendalaPertumbuhanSorted.get(position).getIdKendalaPertumbuhan());
                        startActivity(a);
                    }
                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));
    }


    public void goToInputKendalaPertumbuhan(){
        Intent a = new Intent(ListKendalaPertumbuhan.this, InputKendalaPertumbuhan.class);
        startActivity(a);
        finish();
    }



    public void goToBeranda(){
        Intent a = new Intent(ListKendalaPertumbuhan.this, Home.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();
    }

    public void goToRT(){
        Intent a = new Intent(ListKendalaPertumbuhan.this, ListRencanaTanam.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();
    }

    public void goToST(){
        Intent a = new Intent(ListKendalaPertumbuhan.this, ListSudahTanam.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }

    public void goToKP(){
        Intent a = new Intent(ListKendalaPertumbuhan.this, ListKendalaPertumbuhan.class);
        startActivity(a);
        finish();
    }

    public void goToPanen(){
        Intent a = new Intent(ListKendalaPertumbuhan.this, ListPanen.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }

    public void goToRAB(){
        Intent a = new Intent(ListKendalaPertumbuhan.this, ListRancanganAnggaranBiaya.class);
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