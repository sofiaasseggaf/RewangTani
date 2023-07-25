package com.rewangTani.rewangtani.middlebar.warungtenagakerja;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.adapter.CustomSpinnerAdapter;
import com.rewangTani.rewangtani.adapter.adaptermiddlebar.adapterlistgaris.AdapterListWarungTenagaKerjaGaris;
import com.rewangTani.rewangtani.adapter.adaptermiddlebar.adapterlistkotak.AdapterListWarungTenagaKerja;
import com.rewangTani.rewangtani.adapter.adaptermiddlebar.adapterlistmonitor.AdapterListWarungTenagaKerjaMonitor;
import com.rewangTani.rewangtani.bottombar.Home;
import com.rewangTani.rewangtani.middlebar.warungbibitdanpupuk.ListWarungBibitdanPupuk;
import com.rewangTani.rewangtani.middlebar.warungpestisida.ListWarungPestisida;
import com.rewangTani.rewangtani.middlebar.warungsewamesin.ListWarungSewaMesin;
import com.rewangTani.rewangtani.model.modelwarungwarung.modeltenagakerja.DatumTenagaKerja;
import com.rewangTani.rewangtani.model.modelwarungwarung.modeltenagakerja.ModelTenagaKerja;
import com.rewangTani.rewangtani.utility.RecyclerItemClickListener;
import com.rewangTani.rewangtani.utility.StringDateComparator;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListWarungTenagaKerja extends AppCompatActivity {

    AdapterListWarungTenagaKerja itemList;
    AdapterListWarungTenagaKerjaGaris itemListGaris;
    AdapterListWarungTenagaKerjaMonitor itemListMonitor;
    Spinner sp_urutkan, sp_tampilan;
    ImageButton btn_filter, btn_urutkan, btn_tenaga_kerja, btn_sewa_mesin, btn_bibit, btn_pestisida, btn_back;
    RecyclerView rv_warung_tenaga_kerja, rv_warung_tenaga_kerja_terbaru, rv_warung_tenaga_kerja_terlama, rv_warung_tenaga_kerja_az, rv_warung_tenaga_kerja_za;
    RecyclerView rv_warung_tenaga_kerja_terdekat,rv_warung_tenaga_kerja_harga_terendah,rv_warung_tenaga_kerja_harga_tertinggi;
    ModelTenagaKerja modelTenagaKerja;
    List<DatumTenagaKerja> listTenagaKerja = new ArrayList<>();
    List<DatumTenagaKerja> listTenagaKerjaSorted = new ArrayList<>();
    List<DatumTenagaKerja> listTenagaKerjaSorted2 = new ArrayList<>();
    List<DatumTenagaKerja> listTenagaKerjaSortedHargaTerendah = new ArrayList<>();
    List<DatumTenagaKerja> listTenagaKerjaSortedHargaTertinggi = new ArrayList<>();
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
        setContentView(R.layout.middlebar_list_warung_tenaga_kerja);

        sp_urutkan = findViewById(R.id.sp_urutkan);
        sp_tampilan = findViewById(R.id.sp_tampilan);
        rv_warung_tenaga_kerja = findViewById(R.id.rv_warung_tenaga_kerja);
        rv_warung_tenaga_kerja_terbaru = findViewById(R.id.rv_warung_tenaga_kerja_terbaru);
        rv_warung_tenaga_kerja_terlama = findViewById(R.id.rv_warung_tenaga_kerja_terlama);
        rv_warung_tenaga_kerja_az = findViewById(R.id.rv_warung_tenaga_kerja_az);
        rv_warung_tenaga_kerja_za = findViewById(R.id.rv_warung_tenaga_kerja_za);
        rv_warung_tenaga_kerja_terdekat = findViewById(R.id.rv_warung_tenaga_kerja_terdekat);
        rv_warung_tenaga_kerja_harga_terendah = findViewById(R.id.rv_warung_tenaga_kerja_harga_terendah);
        rv_warung_tenaga_kerja_harga_tertinggi = findViewById(R.id.rv_warung_tenaga_kerja_harga_tertinggi);
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
                    Toast.makeText(ListWarungTenagaKerja.this, "Fitur belum tersedia", Toast.LENGTH_SHORT).show();
                } else if (namaUrutan.equalsIgnoreCase("Harga Terendah")){
                    urutkan = "Harga Terendah";
                    sortHargaTerendah();
                } else if (namaUrutan.equalsIgnoreCase("Harga Tertinggi")){
                    urutkan = "Harga Tertinggi";
                    sortHargaTertinggi();
                } else {
                    urutkan = "Normal";
                    setDataTenagaKerja();
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

        btn_bibit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToBibit();
            }
        });

        btn_sewa_mesin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSewaMesin();
            }
        });

        btn_pestisida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPestisida();
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
                getTenagaKerja();
            }
        }).start();
    }

    public void getTenagaKerja() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelTenagaKerja> dataRT = apiInterface.getDataWarungTenagaKerja();
        dataRT.enqueue(new Callback<ModelTenagaKerja>() {
            @Override
            public void onResponse(Call<ModelTenagaKerja> call, Response<ModelTenagaKerja> response) {
                modelTenagaKerja = response.body();
                if (response.body()!=null){
                    listTenagaKerja.clear();
                    for (int i = 0; i < modelTenagaKerja.getTotalData(); i++) {
                        listTenagaKerja.add(modelTenagaKerja.getData().get(i));
                    }
                    if (listTenagaKerja.size()>0){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                setSpinnerUrutan();
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
            public void onFailure(Call<ModelTenagaKerja> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        Toast.makeText(ListWarungTenagaKerja.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void setSpinnerUrutan(){
        urutan = getResources().getStringArray(R.array.urutan);
        ArrayAdapter<String> adapterUrutan = new ArrayAdapter<String>(ListWarungTenagaKerja.this, R.layout.z_spinner_list_urutan, urutan);
        adapterUrutan.setDropDownViewResource(R.layout.z_spinner_list);
        sp_urutkan.setAdapter(adapterUrutan);

        if (listTenagaKerja.size()>0){
            setDataTenagaKerja();
        } else {

        }

        CustomSpinnerAdapter customAdapter = new CustomSpinnerAdapter(getApplicationContext(),images,tampilan);
        sp_tampilan.setAdapter(customAdapter);
    }

    public void checkTampilan(){
        if (urutkan.equalsIgnoreCase("Normal")){
            setDataTenagaKerja();
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
            setDataTenagaKerja();
        }
    }

    public void setDataTenagaKerja(){

        urutkan = "Normal";

        rv_warung_tenaga_kerja.setVisibility(View.VISIBLE);
        rv_warung_tenaga_kerja_terbaru.setVisibility(View.GONE);
        rv_warung_tenaga_kerja_terlama.setVisibility(View.GONE);
        rv_warung_tenaga_kerja_az.setVisibility(View.GONE);
        rv_warung_tenaga_kerja_za.setVisibility(View.GONE);
        rv_warung_tenaga_kerja_terdekat.setVisibility(View.GONE);
        rv_warung_tenaga_kerja_harga_terendah.setVisibility(View.GONE);
        rv_warung_tenaga_kerja_harga_tertinggi.setVisibility(View.GONE);

        if (mode.equalsIgnoreCase("Kotak")){
            itemList = new AdapterListWarungTenagaKerja(listTenagaKerja);
            rv_warung_tenaga_kerja.setLayoutManager(new GridLayoutManager(ListWarungTenagaKerja.this, 2));
            rv_warung_tenaga_kerja.setAdapter(itemList);
            rv_warung_tenaga_kerja.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_tenaga_kerja,
                    new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent a = new Intent(ListWarungTenagaKerja.this, DetailWarungTenagaKerja.class);
                            a.putExtra("id", listTenagaKerja.get(position).getIdTenagaKerja());
                            startActivity(a);
                        }
                        @Override
                        public void onLongItemClick(View view, int position) {

                        }
                    }));
        } else if (mode.equalsIgnoreCase("Garis")){
            itemListGaris = new AdapterListWarungTenagaKerjaGaris(listTenagaKerja);
            rv_warung_tenaga_kerja.setLayoutManager(new LinearLayoutManager(ListWarungTenagaKerja.this));
            rv_warung_tenaga_kerja.setAdapter(itemListGaris);
            rv_warung_tenaga_kerja.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_tenaga_kerja,
                    new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent a = new Intent(ListWarungTenagaKerja.this, DetailWarungTenagaKerja.class);
                            a.putExtra("id", listTenagaKerja.get(position).getIdTenagaKerja());
                            startActivity(a);
                        }
                        @Override
                        public void onLongItemClick(View view, int position) {

                        }
                    }));
        } else if (mode.equalsIgnoreCase("Monitor")){
            itemListMonitor = new AdapterListWarungTenagaKerjaMonitor(listTenagaKerja);
            rv_warung_tenaga_kerja.setLayoutManager(new LinearLayoutManager(ListWarungTenagaKerja.this));
            rv_warung_tenaga_kerja.setAdapter(itemListMonitor);
            rv_warung_tenaga_kerja.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_tenaga_kerja,
                    new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent a = new Intent(ListWarungTenagaKerja.this, DetailWarungTenagaKerja.class);
                            a.putExtra("id", listTenagaKerja.get(position).getIdTenagaKerja());
                            startActivity(a);
                        }
                        @Override
                        public void onLongItemClick(View view, int position) {

                        }
                    }));
        } else {
            itemList = new AdapterListWarungTenagaKerja(listTenagaKerja);
            rv_warung_tenaga_kerja.setLayoutManager(new GridLayoutManager(ListWarungTenagaKerja.this, 2));
            rv_warung_tenaga_kerja.setAdapter(itemList);
            rv_warung_tenaga_kerja.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_tenaga_kerja,
                    new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent a = new Intent(ListWarungTenagaKerja.this, DetailWarungTenagaKerja.class);
                            a.putExtra("id", listTenagaKerja.get(position).getIdTenagaKerja());
                            startActivity(a);
                        }
                        @Override
                        public void onLongItemClick(View view, int position) {

                        }
                    }));
        }

    }

    private void clearData() {
        int size = listTenagaKerjaSorted.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                listTenagaKerjaSorted.remove(0);
            }

            itemList.notifyItemRangeRemoved(0, size);
        }

        int size2 = listTenagaKerjaSorted2.size();
        if (size2 > 0) {
            for (int i = 0; i < size2; i++) {
                listTenagaKerjaSorted2.remove(0);
            }

            itemList.notifyItemRangeRemoved(0, size2);
        }

        int size3 = listTenagaKerjaSortedHargaTerendah.size();
        if (size3 > 0) {
            for (int i = 0; i < size3; i++) {
                listTenagaKerjaSortedHargaTerendah.remove(0);
            }

            itemList.notifyItemRangeRemoved(0, size3);
        }

        int size4 = listTenagaKerjaSortedHargaTertinggi.size();
        if (size4 > 0) {
            for (int i = 0; i < size4; i++) {
                listTenagaKerjaSortedHargaTertinggi.remove(0);
            }

            itemList.notifyItemRangeRemoved(0, size4);
        }
    }

    public void sortTanggalTerbaru(){
        clearData();

        rv_warung_tenaga_kerja.setVisibility(View.GONE);
        rv_warung_tenaga_kerja_terbaru.setVisibility(View.VISIBLE);
        rv_warung_tenaga_kerja_terlama.setVisibility(View.GONE);
        rv_warung_tenaga_kerja_az.setVisibility(View.GONE);
        rv_warung_tenaga_kerja_za.setVisibility(View.GONE);
        rv_warung_tenaga_kerja_terdekat.setVisibility(View.GONE);
        rv_warung_tenaga_kerja_harga_terendah.setVisibility(View.GONE);
        rv_warung_tenaga_kerja_harga_tertinggi.setVisibility(View.GONE);

        urutanTanggal.clear();
        if (listTenagaKerja.size()>0){
            for(int a=0; a<listTenagaKerja.size(); a++){
                String b = listTenagaKerja.get(a).getCreatedDate();
                b.substring(0, b.length() - 6);
                urutanTanggal.add(b);
            }
            Collections.sort(urutanTanggal, new StringDateComparator());

            for(int z=urutanTanggal.size()-1; z>=0; z--){
                // i=2
                String dt = urutanTanggal.get(z);
                for (int x=0; x<listTenagaKerja.size(); x++){
                    if(listTenagaKerja.get(x).getCreatedDate().equalsIgnoreCase(dt)){
                        listTenagaKerjaSorted.add(listTenagaKerja.get(x));
                    }
                }
            }

            if (mode.equalsIgnoreCase("Kotak")){
                itemList = new AdapterListWarungTenagaKerja(listTenagaKerjaSorted);
                rv_warung_tenaga_kerja_terbaru.setLayoutManager(new GridLayoutManager(ListWarungTenagaKerja.this, 2));
                rv_warung_tenaga_kerja_terbaru.setAdapter(itemList);
                rv_warung_tenaga_kerja_terbaru.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_tenaga_kerja_terbaru,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungTenagaKerja.this, DetailWarungTenagaKerja.class);
                                a.putExtra("id", listTenagaKerjaSorted.get(position).getIdTenagaKerja());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if (mode.equalsIgnoreCase("Garis")){
                itemListGaris = new AdapterListWarungTenagaKerjaGaris(listTenagaKerjaSorted);
                rv_warung_tenaga_kerja_terbaru.setLayoutManager(new LinearLayoutManager(ListWarungTenagaKerja.this));
                rv_warung_tenaga_kerja_terbaru.setAdapter(itemListGaris);
                rv_warung_tenaga_kerja_terbaru.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_tenaga_kerja_terbaru,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungTenagaKerja.this, DetailWarungTenagaKerja.class);
                                a.putExtra("id", listTenagaKerjaSorted.get(position).getIdTenagaKerja());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if (mode.equalsIgnoreCase("Monitor")){
                itemListMonitor = new AdapterListWarungTenagaKerjaMonitor(listTenagaKerjaSorted);
                rv_warung_tenaga_kerja_terbaru.setLayoutManager(new LinearLayoutManager(ListWarungTenagaKerja.this));
                rv_warung_tenaga_kerja_terbaru.setAdapter(itemListMonitor);
                rv_warung_tenaga_kerja_terbaru.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_tenaga_kerja_terbaru,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungTenagaKerja.this, DetailWarungTenagaKerja.class);
                                a.putExtra("id", listTenagaKerjaSorted.get(position).getIdTenagaKerja());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else {
                itemList = new AdapterListWarungTenagaKerja(listTenagaKerjaSorted);
                rv_warung_tenaga_kerja_terbaru.setLayoutManager(new GridLayoutManager(ListWarungTenagaKerja.this, 2));
                rv_warung_tenaga_kerja_terbaru.setAdapter(itemList);
                rv_warung_tenaga_kerja_terbaru.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_tenaga_kerja_terbaru,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungTenagaKerja.this, DetailWarungTenagaKerja.class);
                                a.putExtra("id", listTenagaKerjaSorted.get(position).getIdTenagaKerja());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            }



        } else {
            Toast.makeText(ListWarungTenagaKerja.this, "Data warung tidak ada", Toast.LENGTH_SHORT).show();
        }
    }

    public void sortTanggalTerlama(){
        clearData();

        rv_warung_tenaga_kerja.setVisibility(View.GONE);
        rv_warung_tenaga_kerja_terbaru.setVisibility(View.GONE);
        rv_warung_tenaga_kerja_terlama.setVisibility(View.VISIBLE);
        rv_warung_tenaga_kerja_az.setVisibility(View.GONE);
        rv_warung_tenaga_kerja_za.setVisibility(View.GONE);
        rv_warung_tenaga_kerja_terdekat.setVisibility(View.GONE);
        rv_warung_tenaga_kerja_harga_terendah.setVisibility(View.GONE);
        rv_warung_tenaga_kerja_harga_tertinggi.setVisibility(View.GONE);

        urutanTanggal.clear();
        if (listTenagaKerja.size()>0){
            for(int a=0; a<listTenagaKerja.size(); a++){
                String b = listTenagaKerja.get(a).getCreatedDate();
                b.substring(0, b.length() - 6);
                urutanTanggal.add(b);
            }
            Collections.sort(urutanTanggal, new StringDateComparator());

            for(int z=0; z<urutanTanggal.size(); z++){
                // i=2
                String dt = urutanTanggal.get(z);
                for (int x=0; x<listTenagaKerja.size(); x++){
                    if(listTenagaKerja.get(x).getCreatedDate().equalsIgnoreCase(dt)){
                        listTenagaKerjaSorted.add(listTenagaKerja.get(x));
                    }
                }
            }

            if (mode.equalsIgnoreCase("Kotak")){
                itemList = new AdapterListWarungTenagaKerja(listTenagaKerjaSorted);
                rv_warung_tenaga_kerja_terlama.setLayoutManager(new GridLayoutManager(ListWarungTenagaKerja.this, 2));
                rv_warung_tenaga_kerja_terlama.setAdapter(itemList);
                rv_warung_tenaga_kerja_terlama.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_tenaga_kerja_terlama,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungTenagaKerja.this, DetailWarungTenagaKerja.class);
                                a.putExtra("id", listTenagaKerjaSorted.get(position).getIdTenagaKerja());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if (mode.equalsIgnoreCase("Garis")){
                itemListGaris = new AdapterListWarungTenagaKerjaGaris(listTenagaKerjaSorted);
                rv_warung_tenaga_kerja_terlama.setLayoutManager(new LinearLayoutManager(ListWarungTenagaKerja.this));
                rv_warung_tenaga_kerja_terlama.setAdapter(itemListGaris);
                rv_warung_tenaga_kerja_terlama.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_tenaga_kerja_terlama,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungTenagaKerja.this, DetailWarungTenagaKerja.class);
                                a.putExtra("id", listTenagaKerjaSorted.get(position).getIdTenagaKerja());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if (mode.equalsIgnoreCase("Monitor")){
                itemListMonitor = new AdapterListWarungTenagaKerjaMonitor(listTenagaKerjaSorted);
                rv_warung_tenaga_kerja_terlama.setLayoutManager(new LinearLayoutManager(ListWarungTenagaKerja.this));
                rv_warung_tenaga_kerja_terlama.setAdapter(itemListMonitor);
                rv_warung_tenaga_kerja_terlama.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_tenaga_kerja_terlama,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungTenagaKerja.this, DetailWarungTenagaKerja.class);
                                a.putExtra("id", listTenagaKerjaSorted.get(position).getIdTenagaKerja());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else {
                itemList = new AdapterListWarungTenagaKerja(listTenagaKerjaSorted);
                rv_warung_tenaga_kerja_terlama.setLayoutManager(new GridLayoutManager(ListWarungTenagaKerja.this, 2));
                rv_warung_tenaga_kerja_terlama.setAdapter(itemList);
                rv_warung_tenaga_kerja_terlama.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_tenaga_kerja_terlama,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungTenagaKerja.this, DetailWarungTenagaKerja.class);
                                a.putExtra("id", listTenagaKerjaSorted.get(position).getIdTenagaKerja());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            }



        } else {
            Toast.makeText(ListWarungTenagaKerja.this, "Data warung tidak ada", Toast.LENGTH_SHORT).show();
        }
    }

    public void sortNamaAZ(){
        clearData();

        rv_warung_tenaga_kerja.setVisibility(View.GONE);
        rv_warung_tenaga_kerja_terbaru.setVisibility(View.GONE);
        rv_warung_tenaga_kerja_terlama.setVisibility(View.GONE);
        rv_warung_tenaga_kerja_az.setVisibility(View.VISIBLE);
        rv_warung_tenaga_kerja_za.setVisibility(View.GONE);
        rv_warung_tenaga_kerja_terdekat.setVisibility(View.GONE);
        rv_warung_tenaga_kerja_harga_terendah.setVisibility(View.GONE);
        rv_warung_tenaga_kerja_harga_tertinggi.setVisibility(View.GONE);

        urutanNama.clear();
        if (listTenagaKerja.size()>0){
            for(int a=0; a<listTenagaKerja.size(); a++){
                urutanNama.add(listTenagaKerja.get(a).getNamaTenagaKerja());
            }
            java.util.Collections.sort(urutanNama);

            for(int z=0; z<urutanNama.size(); z++){
                // i=2
                String nama = urutanNama.get(z);
                for (int x=0; x<listTenagaKerja.size(); x++){
                    if(listTenagaKerja.get(x).getNamaTenagaKerja().equalsIgnoreCase(nama)){
                        listTenagaKerjaSorted.add(listTenagaKerja.get(x));
                    }
                }
            }

            if (mode.equalsIgnoreCase("Kotak")){
                itemList = new AdapterListWarungTenagaKerja(listTenagaKerjaSorted);
                rv_warung_tenaga_kerja_az.setLayoutManager(new GridLayoutManager(ListWarungTenagaKerja.this, 2));
                rv_warung_tenaga_kerja_az.setAdapter(itemList);
                rv_warung_tenaga_kerja_az.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_tenaga_kerja_az,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungTenagaKerja.this, DetailWarungTenagaKerja.class);
                                a.putExtra("id", listTenagaKerjaSorted.get(po).getIdTenagaKerja());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if (mode.equalsIgnoreCase("Garis")){
                itemListGaris = new AdapterListWarungTenagaKerjaGaris(listTenagaKerjaSorted);
                rv_warung_tenaga_kerja_az.setLayoutManager(new LinearLayoutManager(ListWarungTenagaKerja.this));
                rv_warung_tenaga_kerja_az.setAdapter(itemListGaris);
                rv_warung_tenaga_kerja_az.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_tenaga_kerja_az,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungTenagaKerja.this, DetailWarungTenagaKerja.class);
                                a.putExtra("id", listTenagaKerjaSorted.get(po).getIdTenagaKerja());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if (mode.equalsIgnoreCase("Monitor")){
                itemListMonitor = new AdapterListWarungTenagaKerjaMonitor(listTenagaKerjaSorted);
                rv_warung_tenaga_kerja_az.setLayoutManager(new LinearLayoutManager(ListWarungTenagaKerja.this));
                rv_warung_tenaga_kerja_az.setAdapter(itemListMonitor);
                rv_warung_tenaga_kerja_az.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_tenaga_kerja_az,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungTenagaKerja.this, DetailWarungTenagaKerja.class);
                                a.putExtra("id", listTenagaKerjaSorted.get(po).getIdTenagaKerja());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else {
                itemList = new AdapterListWarungTenagaKerja(listTenagaKerjaSorted);
                rv_warung_tenaga_kerja_az.setLayoutManager(new GridLayoutManager(ListWarungTenagaKerja.this, 2));
                rv_warung_tenaga_kerja_az.setAdapter(itemList);
                rv_warung_tenaga_kerja_az.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_tenaga_kerja_az,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungTenagaKerja.this, DetailWarungTenagaKerja.class);
                                a.putExtra("id", listTenagaKerjaSorted.get(po).getIdTenagaKerja());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            }


        } else {
            Toast.makeText(ListWarungTenagaKerja.this, "Data warung tidak ada", Toast.LENGTH_SHORT).show();
        }
    }

    public void sortNamaZA(){
        clearData();

        rv_warung_tenaga_kerja.setVisibility(View.GONE);
        rv_warung_tenaga_kerja_terbaru.setVisibility(View.GONE);
        rv_warung_tenaga_kerja_terlama.setVisibility(View.GONE);
        rv_warung_tenaga_kerja_az.setVisibility(View.GONE);
        rv_warung_tenaga_kerja_za.setVisibility(View.VISIBLE);
        rv_warung_tenaga_kerja_terdekat.setVisibility(View.GONE);
        rv_warung_tenaga_kerja_harga_terendah.setVisibility(View.GONE);
        rv_warung_tenaga_kerja_harga_tertinggi.setVisibility(View.GONE);

        urutanNama.clear();
        if (listTenagaKerja.size()>0){
            for(int a=0; a<listTenagaKerja.size(); a++){
                urutanNama.add(listTenagaKerja.get(a).getNamaTenagaKerja());
            }
            java.util.Collections.sort(urutanNama);

            for(int z=0; z<urutanNama.size(); z++){
                // i=2
                String nama = urutanNama.get(z);
                for (int x=0; x<listTenagaKerja.size(); x++){
                    if(listTenagaKerja.get(x).getNamaTenagaKerja().equalsIgnoreCase(nama)){
                        listTenagaKerjaSorted.add(listTenagaKerja.get(x));
                    }
                }
            }

            for(int y=urutanNama.size()-1; y>=0; y--){
                listTenagaKerjaSorted2.add(listTenagaKerjaSorted.get(y));
            }

            if (mode.equalsIgnoreCase("Kotak")){
                itemList = new AdapterListWarungTenagaKerja(listTenagaKerjaSorted2);
                rv_warung_tenaga_kerja_za.setLayoutManager(new GridLayoutManager(ListWarungTenagaKerja.this, 2));
                rv_warung_tenaga_kerja_za.setAdapter(itemList);
                rv_warung_tenaga_kerja_za.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_tenaga_kerja_za,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungTenagaKerja.this, DetailWarungTenagaKerja.class);
                                a.putExtra("id", listTenagaKerjaSorted2.get(position).getIdTenagaKerja());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if (mode.equalsIgnoreCase("Garis")){
                itemListGaris = new AdapterListWarungTenagaKerjaGaris(listTenagaKerjaSorted2);
                rv_warung_tenaga_kerja_za.setLayoutManager(new LinearLayoutManager(ListWarungTenagaKerja.this));
                rv_warung_tenaga_kerja_za.setAdapter(itemListGaris);
                rv_warung_tenaga_kerja_za.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_tenaga_kerja_za,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungTenagaKerja.this, DetailWarungTenagaKerja.class);
                                a.putExtra("id", listTenagaKerjaSorted2.get(position).getIdTenagaKerja());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if (mode.equalsIgnoreCase("Monitor")){
                itemListMonitor = new AdapterListWarungTenagaKerjaMonitor(listTenagaKerjaSorted2);
                rv_warung_tenaga_kerja_za.setLayoutManager(new LinearLayoutManager(ListWarungTenagaKerja.this));
                rv_warung_tenaga_kerja_za.setAdapter(itemListMonitor);
                rv_warung_tenaga_kerja_za.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_tenaga_kerja_za,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungTenagaKerja.this, DetailWarungTenagaKerja.class);
                                a.putExtra("id", listTenagaKerjaSorted2.get(position).getIdTenagaKerja());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else {
                itemList = new AdapterListWarungTenagaKerja(listTenagaKerjaSorted2);
                rv_warung_tenaga_kerja_za.setLayoutManager(new GridLayoutManager(ListWarungTenagaKerja.this, 2));
                rv_warung_tenaga_kerja_za.setAdapter(itemList);
                rv_warung_tenaga_kerja_za.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_tenaga_kerja_za,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungTenagaKerja.this, DetailWarungTenagaKerja.class);
                                a.putExtra("id", listTenagaKerjaSorted2.get(position).getIdTenagaKerja());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            }



        } else {
            Toast.makeText(ListWarungTenagaKerja.this, "Data warung tidak ada", Toast.LENGTH_SHORT).show();
        }
    }

    public void sortHargaTerendah(){
        clearData();

        rv_warung_tenaga_kerja.setVisibility(View.GONE);
        rv_warung_tenaga_kerja_terbaru.setVisibility(View.GONE);
        rv_warung_tenaga_kerja_terlama.setVisibility(View.GONE);
        rv_warung_tenaga_kerja_az.setVisibility(View.GONE);
        rv_warung_tenaga_kerja_za.setVisibility(View.GONE);
        rv_warung_tenaga_kerja_terdekat.setVisibility(View.GONE);
        rv_warung_tenaga_kerja_harga_terendah.setVisibility(View.VISIBLE);
        rv_warung_tenaga_kerja_harga_tertinggi.setVisibility(View.GONE);

        urutanHarga.clear();
        if (listTenagaKerja.size()>0){
            for(int a=0; a<listTenagaKerja.size(); a++){
                urutanHarga.add(listTenagaKerja.get(a).getBiaya());
            }

            Collections.sort(urutanHarga);

            for(int z=0; z<urutanHarga.size(); z++){
                // i=2
                Integer harga = urutanHarga.get(z);
                for (int x=0; x<listTenagaKerja.size(); x++){
                    if(listTenagaKerja.get(x).getBiaya()==harga){
                        listTenagaKerjaSortedHargaTerendah.add(listTenagaKerja.get(x));
                    }
                }
            }

            if (mode.equalsIgnoreCase("Kotak")){
                itemList = new AdapterListWarungTenagaKerja(listTenagaKerjaSortedHargaTerendah);
                rv_warung_tenaga_kerja_harga_terendah.setLayoutManager(new GridLayoutManager(ListWarungTenagaKerja.this, 2));
                rv_warung_tenaga_kerja_harga_terendah.setAdapter(itemList);
                rv_warung_tenaga_kerja_harga_terendah.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_tenaga_kerja_harga_terendah,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungTenagaKerja.this, DetailWarungTenagaKerja.class);
                                a.putExtra("id", listTenagaKerjaSortedHargaTerendah.get(po).getIdTenagaKerja());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if (mode.equalsIgnoreCase("Garis")){
                itemListGaris = new AdapterListWarungTenagaKerjaGaris(listTenagaKerjaSortedHargaTerendah);
                rv_warung_tenaga_kerja_harga_terendah.setLayoutManager(new LinearLayoutManager(ListWarungTenagaKerja.this));
                rv_warung_tenaga_kerja_harga_terendah.setAdapter(itemListGaris);
                rv_warung_tenaga_kerja_harga_terendah.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_tenaga_kerja_harga_terendah,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungTenagaKerja.this, DetailWarungTenagaKerja.class);
                                a.putExtra("id", listTenagaKerjaSortedHargaTerendah.get(po).getIdTenagaKerja());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if (mode.equalsIgnoreCase("Monitor")){
                itemListMonitor = new AdapterListWarungTenagaKerjaMonitor(listTenagaKerjaSortedHargaTerendah);
                rv_warung_tenaga_kerja_harga_terendah.setLayoutManager(new LinearLayoutManager(ListWarungTenagaKerja.this));
                rv_warung_tenaga_kerja_harga_terendah.setAdapter(itemListMonitor);
                rv_warung_tenaga_kerja_harga_terendah.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_tenaga_kerja_harga_terendah,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungTenagaKerja.this, DetailWarungTenagaKerja.class);
                                a.putExtra("id", listTenagaKerjaSortedHargaTerendah.get(po).getIdTenagaKerja());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else {
                itemList = new AdapterListWarungTenagaKerja(listTenagaKerjaSortedHargaTerendah);
                rv_warung_tenaga_kerja_harga_terendah.setLayoutManager(new GridLayoutManager(ListWarungTenagaKerja.this, 2));
                rv_warung_tenaga_kerja_harga_terendah.setAdapter(itemList);
                rv_warung_tenaga_kerja_harga_terendah.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_tenaga_kerja_harga_terendah,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungTenagaKerja.this, DetailWarungTenagaKerja.class);
                                a.putExtra("id", listTenagaKerjaSortedHargaTerendah.get(po).getIdTenagaKerja());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            }



        } else {
            Toast.makeText(ListWarungTenagaKerja.this, "Data warung tidak ada", Toast.LENGTH_SHORT).show();
        }


    }

    public void sortHargaTertinggi(){
        clearData();

        rv_warung_tenaga_kerja.setVisibility(View.GONE);
        rv_warung_tenaga_kerja_terbaru.setVisibility(View.GONE);
        rv_warung_tenaga_kerja_terlama.setVisibility(View.GONE);
        rv_warung_tenaga_kerja_az.setVisibility(View.GONE);
        rv_warung_tenaga_kerja_za.setVisibility(View.GONE);
        rv_warung_tenaga_kerja_terdekat.setVisibility(View.GONE);
        rv_warung_tenaga_kerja_harga_terendah.setVisibility(View.GONE);
        rv_warung_tenaga_kerja_harga_tertinggi.setVisibility(View.VISIBLE);

        urutanHarga.clear();
        if (listTenagaKerja.size()>0){
            for(int a=0; a<listTenagaKerja.size(); a++){
                urutanHarga.add(listTenagaKerja.get(a).getBiaya());
            }

            Collections.sort(urutanHarga);
            Collections.reverse(urutanHarga);

            for(int z=0; z<urutanHarga.size(); z++){
                // i=2
                Integer harga = urutanHarga.get(z);
                for (int x=0; x<listTenagaKerja.size(); x++){
                    if(listTenagaKerja.get(x).getBiaya()==harga){
                        listTenagaKerjaSortedHargaTertinggi.add(listTenagaKerja.get(x));
                    }
                }
            }

            if (mode.equalsIgnoreCase("Kotak")){
                itemList = new AdapterListWarungTenagaKerja(listTenagaKerjaSortedHargaTertinggi);
                rv_warung_tenaga_kerja_harga_tertinggi.setLayoutManager(new GridLayoutManager(ListWarungTenagaKerja.this, 2));
                rv_warung_tenaga_kerja_harga_tertinggi.setAdapter(itemList);
                rv_warung_tenaga_kerja_harga_tertinggi.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_tenaga_kerja_harga_tertinggi,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungTenagaKerja.this, DetailWarungTenagaKerja.class);
                                a.putExtra("id", listTenagaKerjaSortedHargaTertinggi.get(po).getIdTenagaKerja());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if (mode.equalsIgnoreCase("Garis")){
                itemListGaris = new AdapterListWarungTenagaKerjaGaris(listTenagaKerjaSortedHargaTertinggi);
                rv_warung_tenaga_kerja_harga_tertinggi.setLayoutManager(new LinearLayoutManager(ListWarungTenagaKerja.this));
                rv_warung_tenaga_kerja_harga_tertinggi.setAdapter(itemListGaris);
                rv_warung_tenaga_kerja_harga_tertinggi.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_tenaga_kerja_harga_tertinggi,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungTenagaKerja.this, DetailWarungTenagaKerja.class);
                                a.putExtra("id", listTenagaKerjaSortedHargaTertinggi.get(po).getIdTenagaKerja());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if (mode.equalsIgnoreCase("Monitor")){
                itemListMonitor = new AdapterListWarungTenagaKerjaMonitor(listTenagaKerjaSortedHargaTertinggi);
                rv_warung_tenaga_kerja_harga_tertinggi.setLayoutManager(new LinearLayoutManager(ListWarungTenagaKerja.this));
                rv_warung_tenaga_kerja_harga_tertinggi.setAdapter(itemListMonitor);
                rv_warung_tenaga_kerja_harga_tertinggi.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_tenaga_kerja_harga_tertinggi,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungTenagaKerja.this, DetailWarungTenagaKerja.class);
                                a.putExtra("id", listTenagaKerjaSortedHargaTertinggi.get(po).getIdTenagaKerja());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else {
                itemList = new AdapterListWarungTenagaKerja(listTenagaKerjaSortedHargaTertinggi);
                rv_warung_tenaga_kerja_harga_tertinggi.setLayoutManager(new GridLayoutManager(ListWarungTenagaKerja.this, 2));
                rv_warung_tenaga_kerja_harga_tertinggi.setAdapter(itemList);
                rv_warung_tenaga_kerja_harga_tertinggi.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), rv_warung_tenaga_kerja_harga_tertinggi,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungTenagaKerja.this, DetailWarungTenagaKerja.class);
                                a.putExtra("id", listTenagaKerjaSortedHargaTertinggi.get(po).getIdTenagaKerja());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            }

        } else {
            Toast.makeText(ListWarungTenagaKerja.this, "Data warung tidak ada", Toast.LENGTH_SHORT).show();
        }


    }

    public void goToSewaMesin(){
        Intent a = new Intent(ListWarungTenagaKerja.this, ListWarungSewaMesin.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }

    public void goToBibit(){
        Intent a = new Intent(ListWarungTenagaKerja.this, ListWarungBibitdanPupuk.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }

    public void goToPestisida(){
        Intent a = new Intent(ListWarungTenagaKerja.this, ListWarungPestisida.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }


    public void goToBeranda(){
        Intent a = new Intent(ListWarungTenagaKerja.this, Home.class);
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