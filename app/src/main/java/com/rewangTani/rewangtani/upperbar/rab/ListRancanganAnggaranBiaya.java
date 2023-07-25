package com.rewangTani.rewangtani.upperbar.rab;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.adapter.adapterupperbar.AdapterListRAB;
import com.rewangTani.rewangtani.adapter.adapterupperbar.AdapterListRencanaTanam;
import com.rewangTani.rewangtani.bottombar.Home;
import com.rewangTani.rewangtani.model.modelupperbar.outputrencanatanam.DatumOutputRencanaTanam;
import com.rewangTani.rewangtani.model.modelupperbar.outputrencanatanam.ModelOutputRencanaTanam;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.DatumRencanaTanam;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.ModelRencanaTanam;
import com.rewangTani.rewangtani.upperbar.kendalapertumbuhan.ListKendalaPertumbuhan;
import com.rewangTani.rewangtani.upperbar.panen.ListPanen;
import com.rewangTani.rewangtani.upperbar.rencanatanam.DetailRencanaTanamNonEditable;
import com.rewangTani.rewangtani.upperbar.rencanatanam.ListRencanaTanam;
import com.rewangTani.rewangtani.upperbar.sudahtanam.InputSudahTanam;
import com.rewangTani.rewangtani.upperbar.sudahtanam.ListSudahTanam;
import com.rewangTani.rewangtani.utility.PreferenceUtils;
import com.rewangTani.rewangtani.utility.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListRancanganAnggaranBiaya extends AppCompatActivity {

    AdapterListRAB itemList;
    RecyclerView rvRab;
    ImageButton btn_rt, btn_st, btn_panen, btn_rab;
    ModelRencanaTanam modelRencanaTanam;
    List<DatumRencanaTanam> listRencanaTanam = new ArrayList<>();
    TextView txtload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upperbar_rab_list_rab);

        rvRab = findViewById(R.id.rvRab);
        txtload = findViewById(R.id.textloading);
        btn_rt = findViewById(R.id.btn_rt);
        btn_st = findViewById(R.id.btn_st);
        btn_panen = findViewById(R.id.btn_panen);
        btn_rab = findViewById(R.id.btn_rab);

        getData();

        btn_rt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRT();
            }
        });

        btn_st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListRancanganAnggaranBiaya.this);
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
                    txtload.setText("Tunggu sebentar ya .."); }
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
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                setData();
                            }
                        });
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
                        Toast.makeText(ListRancanganAnggaranBiaya.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    /*public void getOutputRencanaTanam() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelOutputRencanaTanam> dataRT = apiInterface.getDataOutputRT();
        dataRT.enqueue(new Callback<ModelOutputRencanaTanam>() {
            @Override
            public void onResponse(Call<ModelOutputRencanaTanam> call, Response<ModelOutputRencanaTanam> response) {
                modelOutputRencanaTanam = response.body();
                if (response.body()!=null){

                    for (int i = 0; i < modelOutputRencanaTanam.getTotalData(); i++) {
                        for(int j = 0; j < listRencanaTanam.size(); j++){
                            try{
                                if (modelOutputRencanaTanam.getData().get(i).getIdRencanaTanam().equalsIgnoreCase(listRencanaTanam.get(j).getIdRencanaTanam())){
                                    listOutputRencanaTanam.add(modelOutputRencanaTanam.getData().get(i));
                                }
                            }catch (Exception e){}
                        }
                    }
                    if (listOutputRencanaTanam.size()>0){
                        getNamaRencanaTanam();
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
            public void onFailure(Call<ModelOutputRencanaTanam> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(ListRancanganAnggaranBiaya.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }
    public void getNamaRencanaTanam(){
        for (int i = 0; i < listRencanaTanam.size(); i++) {
            for(int j = 0; j < listOutputRencanaTanam.size(); j++){
                try{
                    if (listRencanaTanam.get(i).getIdRencanaTanam().equalsIgnoreCase(listOutputRencanaTanam.get(j).getIdRencanaTanam())){
                        listNamaRencanaTanam.add(listNamaRencanaTanam.get(i));
                    }
                }catch (Exception e){}
            }
        }
    }*/

    public void setData(){
        itemList = new AdapterListRAB(listRencanaTanam);
        rvRab.setLayoutManager(new LinearLayoutManager(ListRancanganAnggaranBiaya.this));
        rvRab.setAdapter(itemList);
        rvRab.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rvRab,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent a = new Intent(ListRancanganAnggaranBiaya.this, DetailRancanganAnggaranBiaya.class);
                        a.putExtra("id", listRencanaTanam.get(position).getIdRencanaTanam());
                        startActivity(a);
                    }
                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));
    }

    public void goToBeranda(){
        Intent a = new Intent(ListRancanganAnggaranBiaya.this, Home.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();
    }

    public void goToRT(){
        Intent a = new Intent(ListRancanganAnggaranBiaya.this, ListRencanaTanam.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();
    }

    public void goToST(){
        Intent a = new Intent(ListRancanganAnggaranBiaya.this, ListSudahTanam.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();
    }

    public void goToKP(){
        Intent a = new Intent(ListRancanganAnggaranBiaya.this, ListKendalaPertumbuhan.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();
    }

    public void goToPanen(){
        Intent a = new Intent(ListRancanganAnggaranBiaya.this, ListPanen.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();
    }

    public void goToRAB(){
        Intent a = new Intent(ListRancanganAnggaranBiaya.this, ListRancanganAnggaranBiaya.class);
        startActivity(a);
        finish();
    }

    @Override
    public void onBackPressed() {
        goToBeranda();
    }
}
