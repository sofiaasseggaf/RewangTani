package com.rewangTani.rewangtani.middlebar.warungtenagakerja;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
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
import com.rewangTani.rewangtani.databinding.MiddlebarListWarungTenagaKerjaBinding;
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

    MiddlebarListWarungTenagaKerjaBinding binding;
    AdapterListWarungTenagaKerja itemList;
    AdapterListWarungTenagaKerjaGaris itemListGaris;
    AdapterListWarungTenagaKerjaMonitor itemListMonitor;
    ModelTenagaKerja modelTenagaKerja;
    List<DatumTenagaKerja> listTenagaKerja = new ArrayList<>();
    List<DatumTenagaKerja> listTenagaKerjaSorted = new ArrayList<>();
    List<DatumTenagaKerja> listTenagaKerjaSorted2 = new ArrayList<>();
    List<DatumTenagaKerja> listTenagaKerjaSortedHargaTerendah = new ArrayList<>();
    List<DatumTenagaKerja> listTenagaKerjaSortedHargaTertinggi = new ArrayList<>();
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
        binding = DataBindingUtil.setContentView(this, R.layout.middlebar_list_warung_tenaga_kerja);

        getData();

        binding.spUrutkan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                namaUrutan = binding.spUrutkan.getSelectedItem().toString();
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

        binding.spTampilan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        binding.btnPupuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToBibit();
            }
        });

        binding.btnSewaMesin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSewaMesin();
            }
        });

        binding.btnPestisida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPestisida();
            }
        });

    }

    private void getData(){
        findViewById(R.id.viewLoading).setVisibility(View.VISIBLE);
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int count = 0;
            @Override
            public void run() {
                count++;
                if (count == 1) {
                    binding.textloading.setText("Tunggu sebentar ya ."); }
                else if (count == 2) {
                    binding.textloading.setText("Tunggu sebentar ya . ."); }
                else if (count == 3) {
                    binding.textloading.setText("Tunggu sebentar ya . . ."); }
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
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
                                setSpinnerUrutan();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.viewLoading).setVisibility(View.GONE);
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
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
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
        binding.spUrutkan.setAdapter(adapterUrutan);

        if (listTenagaKerja.size()>0){
            setDataTenagaKerja();
        } else {

        }

        CustomSpinnerAdapter customAdapter = new CustomSpinnerAdapter(getApplicationContext(),images,tampilan);
        binding.spTampilan.setAdapter(customAdapter);
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

        binding.rvWarungTenagakerja.setVisibility(View.VISIBLE);
        binding.rvWarungTenagakerjaTerbaru.setVisibility(View.GONE);
        binding.rvWarungTenagakerjaTerlama.setVisibility(View.GONE);
        binding.rvWarungTenagakerjaAz.setVisibility(View.GONE);
        binding.rvWarungTenagakerjaZa.setVisibility(View.GONE);
        binding.rvWarungTenagakerjaTerdekat.setVisibility(View.GONE);
        binding.rvWarungTenagakerjaHargaTerendah.setVisibility(View.GONE);
        binding.rvWarungTenagakerjaHargaTertinggi.setVisibility(View.GONE);

        if (mode.equalsIgnoreCase("Kotak")){
            itemList = new AdapterListWarungTenagaKerja(listTenagaKerja);
            binding.rvWarungTenagakerja.setLayoutManager(new GridLayoutManager(ListWarungTenagaKerja.this, 2));
            binding.rvWarungTenagakerja.setAdapter(itemList);
            binding.rvWarungTenagakerja.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungTenagakerja,
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
            binding.rvWarungTenagakerja.setLayoutManager(new LinearLayoutManager(ListWarungTenagaKerja.this));
            binding.rvWarungTenagakerja.setAdapter(itemListGaris);
            binding.rvWarungTenagakerja.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungTenagakerja,
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
            binding.rvWarungTenagakerja.setLayoutManager(new LinearLayoutManager(ListWarungTenagaKerja.this));
            binding.rvWarungTenagakerja.setAdapter(itemListMonitor);
            binding.rvWarungTenagakerja.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungTenagakerja,
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
            binding.rvWarungTenagakerja.setLayoutManager(new GridLayoutManager(ListWarungTenagaKerja.this, 2));
            binding.rvWarungTenagakerja.setAdapter(itemList);
            binding.rvWarungTenagakerja.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungTenagakerja,
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

        binding.rvWarungTenagakerja.setVisibility(View.GONE);
        binding.rvWarungTenagakerjaTerbaru.setVisibility(View.VISIBLE);
        binding.rvWarungTenagakerjaTerlama.setVisibility(View.GONE);
        binding.rvWarungTenagakerjaAz.setVisibility(View.GONE);
        binding.rvWarungTenagakerjaZa.setVisibility(View.GONE);
        binding.rvWarungTenagakerjaTerdekat.setVisibility(View.GONE);
        binding.rvWarungTenagakerjaHargaTerendah.setVisibility(View.GONE);
        binding.rvWarungTenagakerjaHargaTertinggi.setVisibility(View.GONE);

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
                binding.rvWarungTenagakerjaTerbaru.setLayoutManager(new GridLayoutManager(ListWarungTenagaKerja.this, 2));
                binding.rvWarungTenagakerjaTerbaru.setAdapter(itemList);
                binding.rvWarungTenagakerjaTerbaru.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungTenagakerjaTerbaru,
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
                binding.rvWarungTenagakerjaTerbaru.setLayoutManager(new LinearLayoutManager(ListWarungTenagaKerja.this));
                binding.rvWarungTenagakerjaTerbaru.setAdapter(itemListGaris);
                binding.rvWarungTenagakerjaTerbaru.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungTenagakerjaTerbaru,
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
                binding.rvWarungTenagakerjaTerbaru.setLayoutManager(new LinearLayoutManager(ListWarungTenagaKerja.this));
                binding.rvWarungTenagakerjaTerbaru.setAdapter(itemListMonitor);
                binding.rvWarungTenagakerjaTerbaru.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungTenagakerjaTerbaru,
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
                binding.rvWarungTenagakerjaTerbaru.setLayoutManager(new GridLayoutManager(ListWarungTenagaKerja.this, 2));
                binding.rvWarungTenagakerjaTerbaru.setAdapter(itemList);
                binding.rvWarungTenagakerjaTerbaru.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungTenagakerjaTerbaru,
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

        binding.rvWarungTenagakerja.setVisibility(View.GONE);
        binding.rvWarungTenagakerjaTerbaru.setVisibility(View.GONE);
        binding.rvWarungTenagakerjaTerlama.setVisibility(View.VISIBLE);
        binding.rvWarungTenagakerjaAz.setVisibility(View.GONE);
        binding.rvWarungTenagakerjaZa.setVisibility(View.GONE);
        binding.rvWarungTenagakerjaTerdekat.setVisibility(View.GONE);
        binding.rvWarungTenagakerjaHargaTerendah.setVisibility(View.GONE);
        binding.rvWarungTenagakerjaHargaTertinggi.setVisibility(View.GONE);

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
                binding.rvWarungTenagakerjaTerlama.setLayoutManager(new GridLayoutManager(ListWarungTenagaKerja.this, 2));
                binding.rvWarungTenagakerjaTerlama.setAdapter(itemList);
                binding.rvWarungTenagakerjaTerlama.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungTenagakerjaTerlama,
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
                binding.rvWarungTenagakerjaTerlama.setLayoutManager(new LinearLayoutManager(ListWarungTenagaKerja.this));
                binding.rvWarungTenagakerjaTerlama.setAdapter(itemListGaris);
                binding.rvWarungTenagakerjaTerlama.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungTenagakerjaTerlama,
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
                binding.rvWarungTenagakerjaTerlama.setLayoutManager(new LinearLayoutManager(ListWarungTenagaKerja.this));
                binding.rvWarungTenagakerjaTerlama.setAdapter(itemListMonitor);
                binding.rvWarungTenagakerjaTerlama.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungTenagakerjaTerlama,
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
                binding.rvWarungTenagakerjaTerlama.setLayoutManager(new GridLayoutManager(ListWarungTenagaKerja.this, 2));
                binding.rvWarungTenagakerjaTerlama.setAdapter(itemList);
                binding.rvWarungTenagakerjaTerlama.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungTenagakerjaTerlama,
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

        binding.rvWarungTenagakerja.setVisibility(View.GONE);
        binding.rvWarungTenagakerjaTerbaru.setVisibility(View.GONE);
        binding.rvWarungTenagakerjaTerlama.setVisibility(View.GONE);
        binding.rvWarungTenagakerjaAz.setVisibility(View.VISIBLE);
        binding.rvWarungTenagakerjaZa.setVisibility(View.GONE);
        binding.rvWarungTenagakerjaTerdekat.setVisibility(View.GONE);
        binding.rvWarungTenagakerjaHargaTerendah.setVisibility(View.GONE);
        binding.rvWarungTenagakerjaHargaTertinggi.setVisibility(View.GONE);

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
                binding.rvWarungTenagakerjaAz.setLayoutManager(new GridLayoutManager(ListWarungTenagaKerja.this, 2));
                binding.rvWarungTenagakerjaAz.setAdapter(itemList);
                binding.rvWarungTenagakerjaAz.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungTenagakerjaAz,
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
                binding.rvWarungTenagakerjaAz.setLayoutManager(new LinearLayoutManager(ListWarungTenagaKerja.this));
                binding.rvWarungTenagakerjaAz.setAdapter(itemListGaris);
                binding.rvWarungTenagakerjaAz.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungTenagakerjaAz,
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
                binding.rvWarungTenagakerjaAz.setLayoutManager(new LinearLayoutManager(ListWarungTenagaKerja.this));
                binding.rvWarungTenagakerjaAz.setAdapter(itemListMonitor);
                binding.rvWarungTenagakerjaAz.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungTenagakerjaAz,
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
                binding.rvWarungTenagakerjaAz.setLayoutManager(new GridLayoutManager(ListWarungTenagaKerja.this, 2));
                binding.rvWarungTenagakerjaAz.setAdapter(itemList);
                binding.rvWarungTenagakerjaAz.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungTenagakerjaAz,
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

        binding.rvWarungTenagakerja.setVisibility(View.GONE);
        binding.rvWarungTenagakerjaTerbaru.setVisibility(View.GONE);
        binding.rvWarungTenagakerjaTerlama.setVisibility(View.GONE);
        binding.rvWarungTenagakerjaAz.setVisibility(View.GONE);
        binding.rvWarungTenagakerjaZa.setVisibility(View.VISIBLE);
        binding.rvWarungTenagakerjaTerdekat.setVisibility(View.GONE);
        binding.rvWarungTenagakerjaHargaTerendah.setVisibility(View.GONE);
        binding.rvWarungTenagakerjaHargaTertinggi.setVisibility(View.GONE);

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
                binding.rvWarungTenagakerjaZa.setLayoutManager(new GridLayoutManager(ListWarungTenagaKerja.this, 2));
                binding.rvWarungTenagakerjaZa.setAdapter(itemList);
                binding.rvWarungTenagakerjaZa.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungTenagakerjaZa,
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
                binding.rvWarungTenagakerjaZa.setLayoutManager(new LinearLayoutManager(ListWarungTenagaKerja.this));
                binding.rvWarungTenagakerjaZa.setAdapter(itemListGaris);
                binding.rvWarungTenagakerjaZa.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungTenagakerjaZa,
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
                binding.rvWarungTenagakerjaZa.setLayoutManager(new LinearLayoutManager(ListWarungTenagaKerja.this));
                binding.rvWarungTenagakerjaZa.setAdapter(itemListMonitor);
                binding.rvWarungTenagakerjaZa.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungTenagakerjaZa,
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
                binding.rvWarungTenagakerjaZa.setLayoutManager(new GridLayoutManager(ListWarungTenagaKerja.this, 2));
                binding.rvWarungTenagakerjaZa.setAdapter(itemList);
                binding.rvWarungTenagakerjaZa.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungTenagakerjaZa,
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

        binding.rvWarungTenagakerja.setVisibility(View.GONE);
        binding.rvWarungTenagakerjaTerbaru.setVisibility(View.GONE);
        binding.rvWarungTenagakerjaTerlama.setVisibility(View.GONE);
        binding.rvWarungTenagakerjaAz.setVisibility(View.GONE);
        binding.rvWarungTenagakerjaZa.setVisibility(View.GONE);
        binding.rvWarungTenagakerjaTerdekat.setVisibility(View.GONE);
        binding.rvWarungTenagakerjaHargaTerendah.setVisibility(View.VISIBLE);
        binding.rvWarungTenagakerjaHargaTertinggi.setVisibility(View.GONE);

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
                binding.rvWarungTenagakerjaHargaTerendah.setLayoutManager(new GridLayoutManager(ListWarungTenagaKerja.this, 2));
                binding.rvWarungTenagakerjaHargaTerendah.setAdapter(itemList);
                binding.rvWarungTenagakerjaHargaTerendah.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungTenagakerjaHargaTerendah,
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
                binding.rvWarungTenagakerjaHargaTerendah.setLayoutManager(new LinearLayoutManager(ListWarungTenagaKerja.this));
                binding.rvWarungTenagakerjaHargaTerendah.setAdapter(itemListGaris);
                binding.rvWarungTenagakerjaHargaTerendah.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungTenagakerjaHargaTerendah,
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
                binding.rvWarungTenagakerjaHargaTerendah.setLayoutManager(new LinearLayoutManager(ListWarungTenagaKerja.this));
                binding.rvWarungTenagakerjaHargaTerendah.setAdapter(itemListMonitor);
                binding.rvWarungTenagakerjaHargaTerendah.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungTenagakerjaHargaTerendah,
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
                binding.rvWarungTenagakerjaHargaTerendah.setLayoutManager(new GridLayoutManager(ListWarungTenagaKerja.this, 2));
                binding.rvWarungTenagakerjaHargaTerendah.setAdapter(itemList);
                binding.rvWarungTenagakerjaHargaTerendah.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungTenagakerjaHargaTerendah,
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

        binding.rvWarungTenagakerja.setVisibility(View.GONE);
        binding.rvWarungTenagakerjaTerbaru.setVisibility(View.GONE);
        binding.rvWarungTenagakerjaTerlama.setVisibility(View.GONE);
        binding.rvWarungTenagakerjaAz.setVisibility(View.GONE);
        binding.rvWarungTenagakerjaZa.setVisibility(View.GONE);
        binding.rvWarungTenagakerjaTerdekat.setVisibility(View.GONE);
        binding.rvWarungTenagakerjaHargaTerendah.setVisibility(View.GONE);
        binding.rvWarungTenagakerjaHargaTertinggi.setVisibility(View.VISIBLE);

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
                binding.rvWarungTenagakerjaHargaTertinggi.setLayoutManager(new GridLayoutManager(ListWarungTenagaKerja.this, 2));
                binding.rvWarungTenagakerjaHargaTertinggi.setAdapter(itemList);
                binding.rvWarungTenagakerjaHargaTertinggi.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungTenagakerjaHargaTertinggi,
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
                binding.rvWarungTenagakerjaHargaTertinggi.setLayoutManager(new LinearLayoutManager(ListWarungTenagaKerja.this));
                binding.rvWarungTenagakerjaHargaTertinggi.setAdapter(itemListGaris);
                binding.rvWarungTenagakerjaHargaTertinggi.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungTenagakerjaHargaTertinggi,
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
                binding.rvWarungTenagakerjaHargaTertinggi.setLayoutManager(new LinearLayoutManager(ListWarungTenagaKerja.this));
                binding.rvWarungTenagakerjaHargaTertinggi.setAdapter(itemListMonitor);
                binding.rvWarungTenagakerjaHargaTertinggi.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungTenagakerjaHargaTertinggi,
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
                binding.rvWarungTenagakerjaHargaTertinggi.setLayoutManager(new GridLayoutManager(ListWarungTenagaKerja.this, 2));
                binding.rvWarungTenagakerjaHargaTertinggi.setAdapter(itemList);
                binding.rvWarungTenagakerjaHargaTertinggi.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungTenagakerjaHargaTertinggi,
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