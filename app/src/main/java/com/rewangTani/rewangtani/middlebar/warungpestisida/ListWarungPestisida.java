package com.rewangTani.rewangtani.middlebar.warungpestisida;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.adapter.CustomSpinnerAdapter;
import com.rewangTani.rewangtani.adapter.adaptermiddlebar.adapterlistgaris.AdapterListWarungPestisidaGaris;
import com.rewangTani.rewangtani.adapter.adaptermiddlebar.adapterlistkotak.AdapterListWarungPestisida;
import com.rewangTani.rewangtani.adapter.adaptermiddlebar.adapterlistmonitor.AdapterListWarungPestisidaMonitor;
import com.rewangTani.rewangtani.bottombar.Home;
import com.rewangTani.rewangtani.middlebar.warungbibitdanpupuk.ListWarungBibitdanPupuk;
import com.rewangTani.rewangtani.middlebar.warungsewamesin.ListWarungSewaMesin;
import com.rewangTani.rewangtani.middlebar.warungtenagakerja.ListWarungTenagaKerja;
import com.rewangTani.rewangtani.model.modelwarungwarung.modelpupukpestisida.DatumPupukPestisida;
import com.rewangTani.rewangtani.model.modelwarungwarung.modelpupukpestisida.ModelPupukPestisida;
import com.rewangTani.rewangtani.utility.RecyclerItemClickListener;
import com.rewangTani.rewangtani.utility.StringDateComparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListWarungPestisida extends AppCompatActivity {

    AdapterListWarungPestisida itemList;
    AdapterListWarungPestisidaGaris itemListGaris;
    AdapterListWarungPestisidaMonitor itemListMonitor;
    Spinner sp_urutkan, sp_tampilan;
    ImageButton btn_tenaga_kerja, btn_sewa_mesin, btn_bibit, btn_pestisida, btn_back;
    RecyclerView rv_warung_pestisida, rv_warung_pestisida_terbaru, rv_warung_pestisida_terlama, rv_warung_pestisida_az, rv_warung_pestisida_za;
    RecyclerView rv_warung_pestisida_terdekat,rv_warung_pestisida_harga_terendah, rv_warung_pestisida_harga_tertinggi;
    ModelPupukPestisida modelPupukPestisida;
    List<DatumPupukPestisida> listPestisida = new ArrayList<>();
    List<DatumPupukPestisida> listPestisidaSorted = new ArrayList<>();
    List<DatumPupukPestisida> listPestisidaSorted2 = new ArrayList<>();
    List<DatumPupukPestisida> listPestisidaSortedHargaTerendah = new ArrayList<>();
    List<DatumPupukPestisida> listPestisidaSortedHargaTertinggi = new ArrayList<>();
    TextView txtload;
    String namaUrutan;
    String[] urutan;
    List<String> urutanTanggal = new ArrayList<>();
    List<String> urutanNama = new ArrayList<>();
    List<Integer> urutanHarga = new ArrayList<>();
    String mode = "Kotak";
    String urutkan;

    String[] tampilan = {"Kotak","Garis","Monitor"};
    int images[] = {R.drawable.mode_kotak,R.drawable.mode_garis, R.drawable.mode_monitor };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.middlebar_list_warung_pestisida);

        sp_urutkan = findViewById(R.id.sp_urutkan);
        sp_tampilan = findViewById(R.id.sp_tampilan);
        rv_warung_pestisida = findViewById(R.id.rv_warung_pestisida);
        rv_warung_pestisida_terbaru = findViewById(R.id.rv_warung_pestisida_terbaru);
        rv_warung_pestisida_terlama = findViewById(R.id.rv_warung_pestisida_terlama);
        rv_warung_pestisida_az = findViewById(R.id.rv_warung_pestisida_az);
        rv_warung_pestisida_za = findViewById(R.id.rv_warung_pestisida_za);
        rv_warung_pestisida_terdekat = findViewById(R.id.rv_warung_pestisida_terdekat);
        rv_warung_pestisida_harga_terendah = findViewById(R.id.rv_warung_pestisida_harga_terendah);
        rv_warung_pestisida_harga_tertinggi = findViewById(R.id.rv_warung_pestisida_harga_tertinggi);
        btn_tenaga_kerja = findViewById(R.id.btn_tenaga_kerja);
        btn_sewa_mesin = findViewById(R.id.btn_sewa_mesin);
        btn_bibit = findViewById(R.id.btn_bibit);
        btn_pestisida = findViewById(R.id.btn_pestisida);
        btn_back = findViewById(R.id.btn_back);
        txtload = findViewById(R.id.textloading);


        getData();

        sp_urutkan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                namaUrutan = sp_urutkan.getSelectedItem().toString();
                if (namaUrutan.equalsIgnoreCase("Tanggal Terbaru")){
                    urutkan = "Tanggal Terbaru";
                    sortTanggalTerbaru();
                } else if(namaUrutan.equalsIgnoreCase("Tanggal Terlama")){
                    urutkan = "Tanggal Terlama";
                    sortTanggalTerlama();
                } else if(namaUrutan.equalsIgnoreCase("A-Z")){
                    urutkan = "A-Z";
                    sortNamaAZ();
                } else if (namaUrutan.equalsIgnoreCase("Z-A")){
                    urutkan = "Z-A";
                    sortNamaZA();
                } else if(namaUrutan.equalsIgnoreCase("Terdekat")){
                    Toast.makeText(ListWarungPestisida.this, "Fitur belum tersedia", Toast.LENGTH_SHORT).show();
                } else if (namaUrutan.equalsIgnoreCase("Harga Terendah")){
                    urutkan = "Harga Terendah";
                    sortHargaTerendah();
                } else if (namaUrutan.equalsIgnoreCase("Harga Tertinggi")){
                    urutkan = "Harga Tertinggi";
                    sortHargaTertinggi();
                } else {
                    urutkan = "Normal";
                    setDataPestisida();
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) { }
        });

        sp_tampilan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long arg3) {
                namaUrutan = tampilan[pos];
                if (namaUrutan.equalsIgnoreCase("Kotak")){
                    mode = "Kotak";
                    checkTampilan();
                } else if(namaUrutan.equalsIgnoreCase("Garis")){
                    mode = "Garis";
                    checkTampilan();
                } else if(namaUrutan.equalsIgnoreCase("Monitor")){
                    mode = "Monitor";
                    checkTampilan();
                } else {
                    mode = "Kotak";
                    checkTampilan();
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) { }
        });

        btn_tenaga_kerja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTenagaKerja();
            }
        });

        btn_sewa_mesin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSewaMesin();
            }
        });

        btn_bibit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToBibit();
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToBeranda();
            }
        });

    }

    private void getData(){
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
                    txtload.setText("TTunggu sebentar ya . ."); }
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
                getPestisida();
            }
        }).start();
    }


    public void getPestisida() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelPupukPestisida> dataRT = apiInterface.getDataWarungBibitPupukPestisida();
        dataRT.enqueue(new Callback<ModelPupukPestisida>() {
            @Override
            public void onResponse(Call<ModelPupukPestisida> call, Response<ModelPupukPestisida> response) {
                modelPupukPestisida = response.body();
                if (response.body()!=null){
                    listPestisida.clear();
                    for (int i = 0; i < modelPupukPestisida.getTotalData(); i++) {
                        if (modelPupukPestisida.getData().get(i).getIdTipeProduk()
                                .equalsIgnoreCase("ad211570-6943-4e4c-88b2-c7837a0a3b28"))
                        listPestisida.add(modelPupukPestisida.getData().get(i));
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.framelayout).setVisibility(View.GONE);
                            setSpinnerUrutan();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<ModelPupukPestisida> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(ListWarungPestisida.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void setSpinnerUrutan(){
        urutan = getResources().getStringArray(R.array.urutan);
        ArrayAdapter<String> adapterUrutan = new ArrayAdapter<String>(ListWarungPestisida.this, R.layout.z_spinner_list_urutan, urutan);
        adapterUrutan.setDropDownViewResource(R.layout.z_spinner_list);
        sp_urutkan.setAdapter(adapterUrutan);

        if (listPestisida.size()>0){
            setDataPestisida();
        } else {

        }

        CustomSpinnerAdapter customAdapter = new CustomSpinnerAdapter(getApplicationContext(),images,tampilan);
        sp_tampilan.setAdapter(customAdapter);
    }

    public void setDataPestisida(){

        urutkan = "Normal";

        rv_warung_pestisida.setVisibility(View.VISIBLE);
        rv_warung_pestisida_terbaru.setVisibility(View.GONE);
        rv_warung_pestisida_terlama.setVisibility(View.GONE);
        rv_warung_pestisida_az.setVisibility(View.GONE);
        rv_warung_pestisida_za.setVisibility(View.GONE);
        rv_warung_pestisida_terdekat.setVisibility(View.GONE);
        rv_warung_pestisida_harga_terendah.setVisibility(View.GONE);
        rv_warung_pestisida_harga_tertinggi.setVisibility(View.GONE);

        if (mode.equalsIgnoreCase("Kotak")){
            itemList = new AdapterListWarungPestisida(listPestisida);
            rv_warung_pestisida.setLayoutManager(new GridLayoutManager(ListWarungPestisida.this, 2));
            rv_warung_pestisida.setAdapter(itemList);
            rv_warung_pestisida.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_pestisida,
                    new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent a = new Intent(ListWarungPestisida.this, DetailWarungPestisida.class);
                            a.putExtra("id", listPestisida.get(position).getIdWarungBpp());
                            startActivity(a);
                        }
                        @Override
                        public void onLongItemClick(View view, int position) {

                        }
                    }));
        } else if (mode.equalsIgnoreCase("Garis")){
            itemListGaris = new AdapterListWarungPestisidaGaris(listPestisida);
            rv_warung_pestisida.setLayoutManager(new LinearLayoutManager(ListWarungPestisida.this));
            rv_warung_pestisida.setAdapter(itemListGaris);
            rv_warung_pestisida.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_pestisida,
                    new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent a = new Intent(ListWarungPestisida.this, DetailWarungPestisida.class);
                            a.putExtra("id", listPestisida.get(position).getIdWarungBpp());
                            startActivity(a);
                        }
                        @Override
                        public void onLongItemClick(View view, int position) {

                        }
                    }));
        } else if (mode.equalsIgnoreCase("Monitor")){
            itemListMonitor = new AdapterListWarungPestisidaMonitor(listPestisida);
            rv_warung_pestisida.setLayoutManager(new LinearLayoutManager(ListWarungPestisida.this));
            rv_warung_pestisida.setAdapter(itemListMonitor);
            rv_warung_pestisida.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_pestisida,
                    new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent a = new Intent(ListWarungPestisida.this, DetailWarungPestisida.class);
                            a.putExtra("id", listPestisida.get(position).getIdWarungBpp());
                            startActivity(a);
                        }
                        @Override
                        public void onLongItemClick(View view, int position) {

                        }
                    }));
        } else {
            itemList = new AdapterListWarungPestisida(listPestisida);
            rv_warung_pestisida.setLayoutManager(new GridLayoutManager(ListWarungPestisida.this, 2));
            rv_warung_pestisida.setAdapter(itemList);
            rv_warung_pestisida.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_pestisida,
                    new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent a = new Intent(ListWarungPestisida.this, DetailWarungPestisida.class);
                            a.putExtra("id", listPestisida.get(position).getIdWarungBpp());
                            startActivity(a);
                        }
                        @Override
                        public void onLongItemClick(View view, int position) {

                        }
                    }));
        }

    }

    private void clearData() {
        int size = listPestisidaSorted.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                listPestisidaSorted.remove(0);
            }

            itemList.notifyItemRangeRemoved(0, size);
        }

        int size2 = listPestisidaSorted2.size();
        if (size2 > 0) {
            for (int i = 0; i < size2; i++) {
                listPestisidaSorted2.remove(0);
            }

            itemList.notifyItemRangeRemoved(0, size2);
        }

        int size3 = listPestisidaSortedHargaTerendah.size();
        if (size3 > 0) {
            for (int i = 0; i < size3; i++) {
                listPestisidaSortedHargaTerendah.remove(0);
            }

            itemList.notifyItemRangeRemoved(0, size3);
        }

        int size4 = listPestisidaSortedHargaTertinggi.size();
        if (size4 > 0) {
            for (int i = 0; i < size4; i++) {
                listPestisidaSortedHargaTertinggi.remove(0);
            }

            itemList.notifyItemRangeRemoved(0, size4);
        }
    }

    public void sortTanggalTerbaru(){
        clearData();

        rv_warung_pestisida.setVisibility(View.GONE);
        rv_warung_pestisida_terbaru.setVisibility(View.VISIBLE);
        rv_warung_pestisida_terlama.setVisibility(View.GONE);
        rv_warung_pestisida_az.setVisibility(View.GONE);
        rv_warung_pestisida_za.setVisibility(View.GONE);
        rv_warung_pestisida_terdekat.setVisibility(View.GONE);
        rv_warung_pestisida_harga_terendah.setVisibility(View.GONE);
        rv_warung_pestisida_harga_tertinggi.setVisibility(View.GONE);

        urutanTanggal.clear();
        if (listPestisida.size()>0){
            for(int a=0; a<listPestisida.size(); a++){
                String b = listPestisida.get(a).getCreatedDate();
                b.substring(0, b.length() - 6);
                urutanTanggal.add(b);
            }
            Collections.sort(urutanTanggal, new StringDateComparator());

            for(int z=urutanTanggal.size()-1; z>=0; z--){
                // i=2
                String dt = urutanTanggal.get(z);
                for (int x=0; x<listPestisida.size(); x++){
                    if(listPestisida.get(x).getCreatedDate().equalsIgnoreCase(dt)){
                        listPestisidaSorted.add(listPestisida.get(x));
                    }
                }
            }

            if (mode.equalsIgnoreCase("Kotak")){
                itemList = new AdapterListWarungPestisida(listPestisidaSorted);
                rv_warung_pestisida_terbaru.setLayoutManager(new GridLayoutManager(ListWarungPestisida.this, 2));
                rv_warung_pestisida_terbaru.setAdapter(itemList);
                rv_warung_pestisida_terbaru.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_pestisida_terbaru,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungPestisida.this, DetailWarungPestisida.class);
                                a.putExtra("id", listPestisidaSorted.get(position).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if (mode.equalsIgnoreCase("Garis")){
                itemListGaris = new AdapterListWarungPestisidaGaris(listPestisidaSorted);
                rv_warung_pestisida_terbaru.setLayoutManager(new LinearLayoutManager(ListWarungPestisida.this));
                rv_warung_pestisida_terbaru.setAdapter(itemListGaris);
                rv_warung_pestisida_terbaru.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_pestisida_terbaru,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungPestisida.this, DetailWarungPestisida.class);
                                a.putExtra("id", listPestisidaSorted.get(position).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if (mode.equalsIgnoreCase("Monitor")){
                itemListMonitor = new AdapterListWarungPestisidaMonitor(listPestisidaSorted);
                rv_warung_pestisida_terbaru.setLayoutManager(new LinearLayoutManager(ListWarungPestisida.this));
                rv_warung_pestisida_terbaru.setAdapter(itemListMonitor);
                rv_warung_pestisida_terbaru.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_pestisida_terbaru,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungPestisida.this, DetailWarungPestisida.class);
                                a.putExtra("id", listPestisidaSorted.get(position).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else {
                itemList = new AdapterListWarungPestisida(listPestisidaSorted);
                rv_warung_pestisida_terbaru.setLayoutManager(new GridLayoutManager(ListWarungPestisida.this, 2));
                rv_warung_pestisida_terbaru.setAdapter(itemList);
                rv_warung_pestisida_terbaru.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_pestisida_terbaru,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungPestisida.this, DetailWarungPestisida.class);
                                a.putExtra("id", listPestisidaSorted.get(position).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            }



            //listBibitdanPupuk.clear();
            //listBibitdanPupukSorted.clear();

        } else {
            Toast.makeText(ListWarungPestisida.this, "Data warung tidak ada", Toast.LENGTH_SHORT).show();
        }
    }

    public void sortTanggalTerlama(){
        clearData();

        rv_warung_pestisida.setVisibility(View.GONE);
        rv_warung_pestisida_terbaru.setVisibility(View.GONE);
        rv_warung_pestisida_terlama.setVisibility(View.VISIBLE);
        rv_warung_pestisida_az.setVisibility(View.GONE);
        rv_warung_pestisida_za.setVisibility(View.GONE);
        rv_warung_pestisida_terdekat.setVisibility(View.GONE);
        rv_warung_pestisida_harga_terendah.setVisibility(View.GONE);
        rv_warung_pestisida_harga_tertinggi.setVisibility(View.GONE);

        urutanTanggal.clear();
        if (listPestisida.size()>0){
            for(int a=0; a<listPestisida.size(); a++){
                String b = listPestisida.get(a).getCreatedDate();
                b.substring(0, b.length() - 6);
                urutanTanggal.add(b);
            }
            Collections.sort(urutanTanggal, new StringDateComparator());

            for(int z=0; z<urutanTanggal.size(); z++){
                // i=2
                String dt = urutanTanggal.get(z);
                for (int x=0; x<listPestisida.size(); x++){
                    if(listPestisida.get(x).getCreatedDate().equalsIgnoreCase(dt)){
                        listPestisidaSorted.add(listPestisida.get(x));
                    }
                }
            }

            if (mode.equalsIgnoreCase("Kotak")){
                itemList = new AdapterListWarungPestisida(listPestisidaSorted);
                rv_warung_pestisida_terlama.setLayoutManager(new GridLayoutManager(ListWarungPestisida.this, 2));
                rv_warung_pestisida_terlama.setAdapter(itemList);
                rv_warung_pestisida_terlama.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_pestisida_terlama,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungPestisida.this, DetailWarungPestisida.class);
                                a.putExtra("id", listPestisidaSorted.get(position).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if (mode.equalsIgnoreCase("Garis")){
                itemListGaris = new AdapterListWarungPestisidaGaris(listPestisidaSorted);
                rv_warung_pestisida_terlama.setLayoutManager(new LinearLayoutManager(ListWarungPestisida.this));
                rv_warung_pestisida_terlama.setAdapter(itemListGaris);
                rv_warung_pestisida_terlama.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_pestisida_terlama,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungPestisida.this, DetailWarungPestisida.class);
                                a.putExtra("id", listPestisidaSorted.get(position).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if (mode.equalsIgnoreCase("Monitor")){
                itemListMonitor = new AdapterListWarungPestisidaMonitor(listPestisidaSorted);
                rv_warung_pestisida_terlama.setLayoutManager(new LinearLayoutManager(ListWarungPestisida.this));
                rv_warung_pestisida_terlama.setAdapter(itemListMonitor);
                rv_warung_pestisida_terlama.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_pestisida_terlama,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungPestisida.this, DetailWarungPestisida.class);
                                a.putExtra("id", listPestisidaSorted.get(position).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else {
                itemList = new AdapterListWarungPestisida(listPestisidaSorted);
                rv_warung_pestisida_terlama.setLayoutManager(new GridLayoutManager(ListWarungPestisida.this, 2));
                rv_warung_pestisida_terlama.setAdapter(itemList);
                rv_warung_pestisida_terlama.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_pestisida_terlama,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungPestisida.this, DetailWarungPestisida.class);
                                a.putExtra("id", listPestisidaSorted.get(position).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            }

            //listBibitdanPupuk.clear();
            //listBibitdanPupukSorted.clear();

        } else {
            Toast.makeText(ListWarungPestisida.this, "Data warung tidak ada", Toast.LENGTH_SHORT).show();
        }
    }

    public void sortNamaAZ(){
        clearData();

        rv_warung_pestisida.setVisibility(View.GONE);
        rv_warung_pestisida_terbaru.setVisibility(View.GONE);
        rv_warung_pestisida_terlama.setVisibility(View.GONE);
        rv_warung_pestisida_az.setVisibility(View.VISIBLE);
        rv_warung_pestisida_za.setVisibility(View.GONE);
        rv_warung_pestisida_terdekat.setVisibility(View.GONE);
        rv_warung_pestisida_harga_terendah.setVisibility(View.GONE);
        rv_warung_pestisida_harga_tertinggi.setVisibility(View.GONE);

        urutanNama.clear();
        if (listPestisida.size()>0){
            for(int a=0; a<listPestisida.size(); a++){
                urutanNama.add(listPestisida.get(a).getNamaProduk());
            }
            java.util.Collections.sort(urutanNama);

            for(int z=0; z<urutanNama.size(); z++){
                // i=2
                String nama = urutanNama.get(z);
                for (int x=0; x<listPestisida.size(); x++){
                    if(listPestisida.get(x).getNamaProduk().equalsIgnoreCase(nama)){
                        listPestisidaSorted.add(listPestisida.get(x));
                    }
                }
            }

            if (mode.equalsIgnoreCase("Kotak")){
                itemList = new AdapterListWarungPestisida(listPestisidaSorted);
                rv_warung_pestisida_az.setLayoutManager(new GridLayoutManager(ListWarungPestisida.this, 2));
                rv_warung_pestisida_az.setAdapter(itemList);
                rv_warung_pestisida_az.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_pestisida_az,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungPestisida.this, DetailWarungPestisida.class);
                                a.putExtra("id", listPestisidaSorted.get(po).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if (mode.equalsIgnoreCase("Garis")){
                itemListGaris = new AdapterListWarungPestisidaGaris(listPestisidaSorted);
                rv_warung_pestisida_az.setLayoutManager(new LinearLayoutManager(ListWarungPestisida.this));
                rv_warung_pestisida_az.setAdapter(itemListGaris);
                rv_warung_pestisida_az.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_pestisida_az,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungPestisida.this, DetailWarungPestisida.class);
                                a.putExtra("id", listPestisidaSorted.get(po).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if (mode.equalsIgnoreCase("Monitor")){
                itemListMonitor = new AdapterListWarungPestisidaMonitor(listPestisidaSorted);
                rv_warung_pestisida_az.setLayoutManager(new LinearLayoutManager(ListWarungPestisida.this));
                rv_warung_pestisida_az.setAdapter(itemListMonitor);
                rv_warung_pestisida_az.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_pestisida_az,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungPestisida.this, DetailWarungPestisida.class);
                                a.putExtra("id", listPestisidaSorted.get(po).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else {
                itemList = new AdapterListWarungPestisida(listPestisidaSorted);
                rv_warung_pestisida_az.setLayoutManager(new GridLayoutManager(ListWarungPestisida.this, 2));
                rv_warung_pestisida_az.setAdapter(itemList);
                rv_warung_pestisida_az.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_pestisida_az,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungPestisida.this, DetailWarungPestisida.class);
                                a.putExtra("id", listPestisidaSorted.get(po).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            }


            //listBibitdanPupuk.clear();
            //listBibitdanPupukSorted.clear();

        } else {
            Toast.makeText(ListWarungPestisida.this, "Data warung tidak ada", Toast.LENGTH_SHORT).show();
        }
    }

    public void sortNamaZA(){
        clearData();

        rv_warung_pestisida.setVisibility(View.GONE);
        rv_warung_pestisida_terbaru.setVisibility(View.GONE);
        rv_warung_pestisida_terlama.setVisibility(View.GONE);
        rv_warung_pestisida_az.setVisibility(View.GONE);
        rv_warung_pestisida_za.setVisibility(View.VISIBLE);
        rv_warung_pestisida_terdekat.setVisibility(View.GONE);
        rv_warung_pestisida_harga_terendah.setVisibility(View.GONE);
        rv_warung_pestisida_harga_tertinggi.setVisibility(View.GONE);

        urutanNama.clear();
        if (listPestisida.size()>0){
            for(int a=0; a<listPestisida.size(); a++){
                urutanNama.add(listPestisida.get(a).getNamaProduk());
            }
            java.util.Collections.sort(urutanNama);

            for(int z=0; z<urutanNama.size(); z++){
                // i=2
                String nama = urutanNama.get(z);
                for (int x=0; x<listPestisida.size(); x++){
                    if(listPestisida.get(x).getNamaProduk().equalsIgnoreCase(nama)){
                        listPestisidaSorted.add(listPestisida.get(x));
                    }
                }
            }

            for(int y=urutanNama.size()-1; y>=0; y--){
                listPestisidaSorted2.add(listPestisidaSorted.get(y));
            }

            if (mode.equalsIgnoreCase("Kotak")){
                itemList = new AdapterListWarungPestisida(listPestisidaSorted2);
                rv_warung_pestisida_za.setLayoutManager(new GridLayoutManager(ListWarungPestisida.this, 2));
                rv_warung_pestisida_za.setAdapter(itemList);
                rv_warung_pestisida_za.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_pestisida_za,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungPestisida.this, DetailWarungPestisida.class);
                                a.putExtra("id", listPestisidaSorted2.get(position).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if(mode.equalsIgnoreCase("Garis")){
                itemListGaris = new AdapterListWarungPestisidaGaris(listPestisidaSorted2);
                rv_warung_pestisida_za.setLayoutManager(new LinearLayoutManager(ListWarungPestisida.this));
                rv_warung_pestisida_za.setAdapter(itemListGaris);
                rv_warung_pestisida_za.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_pestisida_za,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungPestisida.this, DetailWarungPestisida.class);
                                a.putExtra("id", listPestisidaSorted2.get(position).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if (mode.equalsIgnoreCase("Monitor")){
                itemListMonitor = new AdapterListWarungPestisidaMonitor(listPestisidaSorted2);
                rv_warung_pestisida_za.setLayoutManager(new LinearLayoutManager(ListWarungPestisida.this));
                rv_warung_pestisida_za.setAdapter(itemListMonitor);
                rv_warung_pestisida_za.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_pestisida_za,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungPestisida.this, DetailWarungPestisida.class);
                                a.putExtra("id", listPestisidaSorted2.get(position).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else {
                itemList = new AdapterListWarungPestisida(listPestisidaSorted2);
                rv_warung_pestisida_za.setLayoutManager(new GridLayoutManager(ListWarungPestisida.this, 2));
                rv_warung_pestisida_za.setAdapter(itemList);
                rv_warung_pestisida_za.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_pestisida_za,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungPestisida.this, DetailWarungPestisida.class);
                                a.putExtra("id", listPestisidaSorted2.get(position).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            }




        } else {
            Toast.makeText(ListWarungPestisida.this, "Data warung tidak ada", Toast.LENGTH_SHORT).show();
        }
    }

    public void sortHargaTerendah(){
        clearData();

        rv_warung_pestisida.setVisibility(View.GONE);
        rv_warung_pestisida_terbaru.setVisibility(View.GONE);
        rv_warung_pestisida_terlama.setVisibility(View.GONE);
        rv_warung_pestisida_az.setVisibility(View.GONE);
        rv_warung_pestisida_za.setVisibility(View.GONE);
        rv_warung_pestisida_terdekat.setVisibility(View.GONE);
        rv_warung_pestisida_harga_terendah.setVisibility(View.VISIBLE);
        rv_warung_pestisida_harga_tertinggi.setVisibility(View.GONE);

        urutanHarga.clear();
        if (listPestisida.size()>0){
            for(int a=0; a<listPestisida.size(); a++){
                urutanHarga.add(listPestisida.get(a).getHargaProduk());
            }
            Collections.sort(urutanHarga);

            for(int z=0; z<urutanHarga.size(); z++){
                // i=2
                Integer harga = urutanHarga.get(z);
                for (int x=0; x<listPestisida.size(); x++){
                    if(listPestisida.get(x).getHargaProduk()==harga){
                        listPestisidaSortedHargaTerendah.add(listPestisida.get(x));
                    }
                }
            }

            if (mode.equalsIgnoreCase("Kotak")){
                itemList = new AdapterListWarungPestisida(listPestisidaSortedHargaTerendah);
                rv_warung_pestisida_harga_terendah.setLayoutManager(new GridLayoutManager(ListWarungPestisida.this, 2));
                rv_warung_pestisida_harga_terendah.setAdapter(itemList);
                rv_warung_pestisida_harga_terendah.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_pestisida_harga_terendah,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungPestisida.this, DetailWarungPestisida.class);
                                a.putExtra("id", listPestisidaSortedHargaTerendah.get(po).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if (mode.equalsIgnoreCase("Garis")){
                itemListGaris = new AdapterListWarungPestisidaGaris(listPestisidaSortedHargaTerendah);
                rv_warung_pestisida_harga_terendah.setLayoutManager(new LinearLayoutManager(ListWarungPestisida.this));
                rv_warung_pestisida_harga_terendah.setAdapter(itemListGaris);
                rv_warung_pestisida_harga_terendah.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_pestisida_harga_terendah,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungPestisida.this, DetailWarungPestisida.class);
                                a.putExtra("id", listPestisidaSortedHargaTerendah.get(po).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if (mode.equalsIgnoreCase("Monitor")){
                itemListMonitor = new AdapterListWarungPestisidaMonitor(listPestisidaSortedHargaTerendah);
                rv_warung_pestisida_harga_terendah.setLayoutManager(new LinearLayoutManager(ListWarungPestisida.this));
                rv_warung_pestisida_harga_terendah.setAdapter(itemListMonitor);
                rv_warung_pestisida_harga_terendah.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_pestisida_harga_terendah,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungPestisida.this, DetailWarungPestisida.class);
                                a.putExtra("id", listPestisidaSortedHargaTerendah.get(po).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else {
                itemList = new AdapterListWarungPestisida(listPestisidaSortedHargaTerendah);
                rv_warung_pestisida_harga_terendah.setLayoutManager(new GridLayoutManager(ListWarungPestisida.this, 2));
                rv_warung_pestisida_harga_terendah.setAdapter(itemList);
                rv_warung_pestisida_harga_terendah.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_pestisida_harga_terendah,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungPestisida.this, DetailWarungPestisida.class);
                                a.putExtra("id", listPestisidaSortedHargaTerendah.get(po).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            }



        } else {
            Toast.makeText(ListWarungPestisida.this, "Data warung tidak ada", Toast.LENGTH_SHORT).show();
        }


    }

    public void sortHargaTertinggi(){
        clearData();

        rv_warung_pestisida.setVisibility(View.GONE);
        rv_warung_pestisida_terbaru.setVisibility(View.GONE);
        rv_warung_pestisida_terlama.setVisibility(View.GONE);
        rv_warung_pestisida_az.setVisibility(View.GONE);
        rv_warung_pestisida_za.setVisibility(View.GONE);
        rv_warung_pestisida_terdekat.setVisibility(View.GONE);
        rv_warung_pestisida_harga_terendah.setVisibility(View.GONE);
        rv_warung_pestisida_harga_tertinggi.setVisibility(View.VISIBLE);

        urutanHarga.clear();
        if (listPestisida.size()>0){
            for(int a=0; a<listPestisida.size(); a++){
                urutanHarga.add(listPestisida.get(a).getHargaProduk());
            }

            Collections.sort(urutanHarga);
            Collections.reverse(urutanHarga);

            for(int z=0; z<urutanHarga.size(); z++){
                // i=2
                Integer harga = urutanHarga.get(z);
                for (int x=0; x<listPestisida.size(); x++){
                    if(listPestisida.get(x).getHargaProduk()==harga){
                        listPestisidaSortedHargaTertinggi.add(listPestisida.get(x));
                    }
                }
            }

            if (mode.equalsIgnoreCase("Kotak")){
                itemList = new AdapterListWarungPestisida(listPestisidaSortedHargaTertinggi);
                rv_warung_pestisida_harga_tertinggi.setLayoutManager(new GridLayoutManager(ListWarungPestisida.this, 2));
                rv_warung_pestisida_harga_tertinggi.setAdapter(itemList);
                rv_warung_pestisida_harga_tertinggi.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_pestisida_harga_tertinggi,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungPestisida.this, DetailWarungPestisida.class);
                                a.putExtra("id", listPestisidaSortedHargaTertinggi.get(po).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if (mode.equalsIgnoreCase("Garis")){
                itemListGaris = new AdapterListWarungPestisidaGaris(listPestisidaSortedHargaTertinggi);
                rv_warung_pestisida_harga_tertinggi.setLayoutManager(new LinearLayoutManager(ListWarungPestisida.this));
                rv_warung_pestisida_harga_tertinggi.setAdapter(itemListGaris);
                rv_warung_pestisida_harga_tertinggi.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_pestisida_harga_tertinggi,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungPestisida.this, DetailWarungPestisida.class);
                                a.putExtra("id", listPestisidaSortedHargaTertinggi.get(po).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if (mode.equalsIgnoreCase("Monitor")){
                itemListMonitor = new AdapterListWarungPestisidaMonitor(listPestisidaSortedHargaTertinggi);
                rv_warung_pestisida_harga_tertinggi.setLayoutManager(new LinearLayoutManager(ListWarungPestisida.this));
                rv_warung_pestisida_harga_tertinggi.setAdapter(itemListMonitor);
                rv_warung_pestisida_harga_tertinggi.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_pestisida_harga_tertinggi,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungPestisida.this, DetailWarungPestisida.class);
                                a.putExtra("id", listPestisidaSortedHargaTertinggi.get(po).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else {
                itemList = new AdapterListWarungPestisida(listPestisidaSortedHargaTertinggi);
                rv_warung_pestisida_harga_tertinggi.setLayoutManager(new GridLayoutManager(ListWarungPestisida.this, 2));
                rv_warung_pestisida_harga_tertinggi.setAdapter(itemList);
                rv_warung_pestisida_harga_tertinggi.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_pestisida_harga_tertinggi,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungPestisida.this, DetailWarungPestisida.class);
                                a.putExtra("id", listPestisidaSortedHargaTertinggi.get(po).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            }
        } else {
            Toast.makeText(ListWarungPestisida.this, "Data warung tidak ada", Toast.LENGTH_SHORT).show();
        }


    }

    public void checkTampilan(){
        if (urutkan.equalsIgnoreCase("Normal")){
            setDataPestisida();
        } else if (urutkan.equalsIgnoreCase("Tanggal Terbaru")){
            sortTanggalTerbaru();
        } else if(urutkan.equalsIgnoreCase("Tanggal Terlama")){
            sortTanggalTerlama();
        } else if(urutkan.equalsIgnoreCase("A-Z")){
            sortNamaAZ();
        } else if(urutkan.equalsIgnoreCase("Z-A")){
            sortNamaZA();
        } else if(urutkan.equalsIgnoreCase("Harga Terendah")){
            sortHargaTerendah();
        } else if(urutkan.equalsIgnoreCase("Harga Tertinggi")){
            sortHargaTertinggi();
        } else {
            setDataPestisida();
        }
    }

    public void goToTenagaKerja(){
        Intent a = new Intent(ListWarungPestisida.this, ListWarungTenagaKerja.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();
    }

    public void goToSewaMesin(){
        Intent a = new Intent(ListWarungPestisida.this, ListWarungSewaMesin.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();
    }

    public void goToBibit(){
        Intent a = new Intent(ListWarungPestisida.this, ListWarungBibitdanPupuk.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();
    }

    public void goToPestisida(){
        Intent a = new Intent(ListWarungPestisida.this, ListWarungPestisida.class);
        startActivity(a);
        finish();
    }


    public void goToBeranda(){
        Intent a = new Intent(ListWarungPestisida.this, Home.class);
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