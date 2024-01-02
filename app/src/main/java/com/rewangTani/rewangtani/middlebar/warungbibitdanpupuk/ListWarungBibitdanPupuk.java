package com.rewangTani.rewangtani.middlebar.warungbibitdanpupuk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.adapter.CustomSpinnerAdapter;
import com.rewangTani.rewangtani.adapter.adaptermiddlebar.adapterlistgaris.AdapterListWarungBibitdanPupukGaris;
import com.rewangTani.rewangtani.adapter.adaptermiddlebar.adapterlistkotak.AdapterListWarungBibitdanPupuk;
import com.rewangTani.rewangtani.adapter.adaptermiddlebar.adapterlistmonitor.AdapterListWarungBibitdanPupukMonitor;
import com.rewangTani.rewangtani.bottombar.Home;
import com.rewangTani.rewangtani.bottombar.pesan.InboxPesan;
import com.rewangTani.rewangtani.bottombar.profilakun.BerandaProfile;
import com.rewangTani.rewangtani.bottombar.profilelahan.ListProfileLahan;
import com.rewangTani.rewangtani.bottombar.warungku.PesananWarungku;
import com.rewangTani.rewangtani.databinding.MiddlebarListWarungPupukBinding;
import com.rewangTani.rewangtani.middlebar.warungpestisida.ListWarungPestisida;
import com.rewangTani.rewangtani.middlebar.warungsewamesin.ListWarungSewaMesin;
import com.rewangTani.rewangtani.middlebar.warungtenagakerja.ListWarungTenagaKerja;
import com.rewangTani.rewangtani.model.modelwarungwarung.modelpupukpestisida.DatumPupukPestisida;
import com.rewangTani.rewangtani.model.modelwarungwarung.modelpupukpestisida.ModelPupukPestisida;
import com.rewangTani.rewangtani.upperbar.infoperingatancuaca.BerandaInfoPeringatanCuaca;
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

public class ListWarungBibitdanPupuk extends AppCompatActivity {

    MiddlebarListWarungPupukBinding binding;
    AdapterListWarungBibitdanPupuk itemList;
    AdapterListWarungBibitdanPupukGaris itemListGaris;
    AdapterListWarungBibitdanPupukMonitor itemListMonitor;
    ModelPupukPestisida modelPupukPestisida;
    List<DatumPupukPestisida> listBibitdanPupuk = new ArrayList<>();
    List<DatumPupukPestisida> listBibitdanPupukSorted = new ArrayList<>();
    List<DatumPupukPestisida> listBibitdanPupukSorted2 = new ArrayList<>();
    List<DatumPupukPestisida> listBibitdanPupukSortedHargaTerendah = new ArrayList<>();
    List<DatumPupukPestisida> listBibitdanPupukSortedHargaTertinggi = new ArrayList<>();
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
        binding = DataBindingUtil.setContentView(this, R.layout.middlebar_list_warung_pupuk);

        //getData();

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
                    Toast.makeText(ListWarungBibitdanPupuk.this, "Fitur belum tersedia", Toast.LENGTH_SHORT).show();
                } else if (namaUrutan.equalsIgnoreCase("Harga Terendah")){
                    urutkan = "Harga Terendah";
                    sortHargaTerendah();
                } else if (namaUrutan.equalsIgnoreCase("Harga Tertinggi")){
                    urutkan = "Harga Tertinggi";
                    sortHargaTertinggi();
                } else {
                    urutkan = "Normal";
                    setDataBibitdanPupuk();
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

        binding.btnTenagaKerja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTenagaKerja();
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

        binding.btnWarungku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToWarungku();
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
                getPupuk();
            }
        }).start();
    }

    public void getPupuk() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelPupukPestisida> dataRT = apiInterface.getDataWarungBibitPupukPestisida();
        dataRT.enqueue(new Callback<ModelPupukPestisida>() {
            @Override
            public void onResponse(Call<ModelPupukPestisida> call, Response<ModelPupukPestisida> response) {
                modelPupukPestisida = response.body();
                if (response.body()!=null){
                    listBibitdanPupuk.clear();
                    for (int i = 0; i < modelPupukPestisida.getTotalData(); i++) {
                        if (modelPupukPestisida.getData().get(i).getIdTipeProduk()
                                .equalsIgnoreCase("4f54e40a-04a2-4569-8a82-860f193e321b") ||
                                modelPupukPestisida.getData().get(i).getIdTipeProduk()
                                        .equalsIgnoreCase("49944852-6f8c-4185-aa08-4407d99f3f8c"))
                            listBibitdanPupuk.add(modelPupukPestisida.getData().get(i));
                    }
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
            @Override
            public void onFailure(Call<ModelPupukPestisida> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(ListWarungBibitdanPupuk.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void setSpinnerUrutan(){
        urutan = getResources().getStringArray(R.array.urutan);
        ArrayAdapter<String> adapterUrutan = new ArrayAdapter<String>(ListWarungBibitdanPupuk.this, R.layout.z_spinner_list_urutan, urutan);
        adapterUrutan.setDropDownViewResource(R.layout.z_spinner_list);
        binding.spUrutkan.setAdapter(adapterUrutan);

        if (listBibitdanPupuk.size()>0){
            setDataBibitdanPupuk();
        } else {

        }

        CustomSpinnerAdapter customAdapter = new CustomSpinnerAdapter(getApplicationContext(),images,tampilan);
        binding.spTampilan.setAdapter(customAdapter);
    }

    public void setDataBibitdanPupuk(){

        urutkan = "Normal";

        binding.rvWarungPupuk.setVisibility(View.VISIBLE);
        binding.rvWarungPupukTerbaru.setVisibility(View.GONE);
        binding.rvWarungPupukTerlama.setVisibility(View.GONE);
        binding.rvWarungPupukAz.setVisibility(View.GONE);
        binding.rvWarungPupukZa.setVisibility(View.GONE);
        binding.rvWarungPupukTerdekat.setVisibility(View.GONE);
        binding.rvWarungPupukHargaTerendah.setVisibility(View.GONE);
        binding.rvWarungPupukHargaTertinggi.setVisibility(View.GONE);

        if (mode.equalsIgnoreCase("Kotak")){
            itemList = new AdapterListWarungBibitdanPupuk(listBibitdanPupuk);
            binding.rvWarungPupuk.setLayoutManager(new GridLayoutManager(ListWarungBibitdanPupuk.this, 2));
            binding.rvWarungPupuk.setAdapter(itemList);
            binding.rvWarungPupuk.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPupuk,
                    new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent a = new Intent(ListWarungBibitdanPupuk.this, DetailWarungBibitdanPupuk.class);
                            a.putExtra("id", listBibitdanPupuk.get(position).getIdWarungBpp());
                            startActivity(a);
                        }
                        @Override
                        public void onLongItemClick(View view, int position) {

                        }
                    }));
        } else if(mode.equalsIgnoreCase("Garis")){
            itemListGaris = new AdapterListWarungBibitdanPupukGaris(listBibitdanPupuk);
            binding.rvWarungPupuk.setLayoutManager(new LinearLayoutManager(ListWarungBibitdanPupuk.this));
            binding.rvWarungPupuk.setAdapter(itemListGaris);
            binding.rvWarungPupuk.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPupuk,
                    new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent a = new Intent(ListWarungBibitdanPupuk.this, DetailWarungBibitdanPupuk.class);
                            a.putExtra("id", listBibitdanPupuk.get(position).getIdWarungBpp());
                            startActivity(a);
                        }
                        @Override
                        public void onLongItemClick(View view, int position) {

                        }
                    }));
        } else if(mode.equalsIgnoreCase("Monitor")){
            itemListMonitor = new AdapterListWarungBibitdanPupukMonitor(listBibitdanPupuk);
            binding.rvWarungPupuk.setLayoutManager(new LinearLayoutManager(ListWarungBibitdanPupuk.this));
            binding.rvWarungPupuk.setAdapter(itemListMonitor);
            binding.rvWarungPupuk.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPupuk,
                    new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent a = new Intent(ListWarungBibitdanPupuk.this, DetailWarungBibitdanPupuk.class);
                            a.putExtra("id", listBibitdanPupuk.get(position).getIdWarungBpp());
                            startActivity(a);
                        }
                        @Override
                        public void onLongItemClick(View view, int position) {

                        }
                    }));
        } else {
            itemList = new AdapterListWarungBibitdanPupuk(listBibitdanPupuk);
            binding.rvWarungPupuk.setLayoutManager(new GridLayoutManager(ListWarungBibitdanPupuk.this, 2));
            binding.rvWarungPupuk.setAdapter(itemList);
            binding.rvWarungPupuk.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPupuk,
                    new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent a = new Intent(ListWarungBibitdanPupuk.this, DetailWarungBibitdanPupuk.class);
                            a.putExtra("id", listBibitdanPupuk.get(position).getIdWarungBpp());
                            startActivity(a);
                        }
                        @Override
                        public void onLongItemClick(View view, int position) {

                        }
                    }));
        }

    }

    private void clearData() {
        int size = listBibitdanPupukSorted.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                listBibitdanPupukSorted.remove(0);
            }

            itemList.notifyItemRangeRemoved(0, size);
        }

        int size2 = listBibitdanPupukSorted2.size();
        if (size2 > 0) {
            for (int i = 0; i < size2; i++) {
                listBibitdanPupukSorted2.remove(0);
            }

            itemList.notifyItemRangeRemoved(0, size2);
        }

        int size3 = listBibitdanPupukSortedHargaTerendah.size();
        if (size3 > 0) {
            for (int i = 0; i < size3; i++) {
                listBibitdanPupukSortedHargaTerendah.remove(0);
            }

            itemList.notifyItemRangeRemoved(0, size3);
        }

        int size4 = listBibitdanPupukSortedHargaTertinggi.size();
        if (size4 > 0) {
            for (int i = 0; i < size4; i++) {
                listBibitdanPupukSortedHargaTertinggi.remove(0);
            }

            itemList.notifyItemRangeRemoved(0, size4);
        }
    }

    public void sortTanggalTerbaru(){
        clearData();

        binding.rvWarungPupuk.setVisibility(View.GONE);
        binding.rvWarungPupukTerbaru.setVisibility(View.VISIBLE);
        binding.rvWarungPupukTerlama.setVisibility(View.GONE);
        binding.rvWarungPupukAz.setVisibility(View.GONE);
        binding.rvWarungPupukZa.setVisibility(View.GONE);
        binding.rvWarungPupukTerdekat.setVisibility(View.GONE);
        binding.rvWarungPupukHargaTerendah.setVisibility(View.GONE);
        binding.rvWarungPupukHargaTertinggi.setVisibility(View.GONE);

        urutanTanggal.clear();
        if (listBibitdanPupuk.size()>0){
            for(int a=0; a<listBibitdanPupuk.size(); a++){
                String b = listBibitdanPupuk.get(a).getCreatedDate();
                b.substring(0, b.length() - 6);
                urutanTanggal.add(b);
            }
            Collections.sort(urutanTanggal, new StringDateComparator());

            for(int z=urutanTanggal.size()-1; z>=0; z--){
                // i=2
                String dt = urutanTanggal.get(z);
                for (int x=0; x<listBibitdanPupuk.size(); x++){
                    if(listBibitdanPupuk.get(x).getCreatedDate().equalsIgnoreCase(dt)){
                        listBibitdanPupukSorted.add(listBibitdanPupuk.get(x));
                    }
                }
            }

            if (mode.equalsIgnoreCase("Kotak")){
                itemList = new AdapterListWarungBibitdanPupuk(listBibitdanPupukSorted);
                binding.rvWarungPupukTerbaru.setLayoutManager(new GridLayoutManager(ListWarungBibitdanPupuk.this, 2));
                binding.rvWarungPupukTerbaru.setAdapter(itemList);
                binding.rvWarungPupukTerbaru.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPupukTerbaru,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungBibitdanPupuk.this, DetailWarungBibitdanPupuk.class);
                                a.putExtra("id", listBibitdanPupukSorted.get(position).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if(mode.equalsIgnoreCase("Garis")){
                itemListGaris = new AdapterListWarungBibitdanPupukGaris(listBibitdanPupukSorted);
                binding.rvWarungPupukTerbaru.setLayoutManager(new LinearLayoutManager(ListWarungBibitdanPupuk.this));
                binding.rvWarungPupukTerbaru.setAdapter(itemListGaris);
                binding.rvWarungPupukTerbaru.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPupukTerbaru,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungBibitdanPupuk.this, DetailWarungBibitdanPupuk.class);
                                a.putExtra("id", listBibitdanPupukSorted.get(position).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if(mode.equalsIgnoreCase("Monitor")){
                itemListMonitor = new AdapterListWarungBibitdanPupukMonitor(listBibitdanPupukSorted);
                binding.rvWarungPupukTerbaru.setLayoutManager(new LinearLayoutManager(ListWarungBibitdanPupuk.this));
                binding.rvWarungPupukTerbaru.setAdapter(itemListMonitor);
                binding.rvWarungPupukTerbaru.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPupukTerbaru,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungBibitdanPupuk.this, DetailWarungBibitdanPupuk.class);
                                a.putExtra("id", listBibitdanPupukSorted.get(position).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else {
                itemList = new AdapterListWarungBibitdanPupuk(listBibitdanPupukSorted);
                binding.rvWarungPupukTerbaru.setLayoutManager(new GridLayoutManager(ListWarungBibitdanPupuk.this, 2));
                binding.rvWarungPupukTerbaru.setAdapter(itemList);
                binding.rvWarungPupukTerbaru.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPupukTerbaru,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungBibitdanPupuk.this, DetailWarungBibitdanPupuk.class);
                                a.putExtra("id", listBibitdanPupukSorted.get(position).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            }



        } else {
            Toast.makeText(ListWarungBibitdanPupuk.this, "Data Warung Tidak Ada", Toast.LENGTH_SHORT).show();
        }
    }

    public void sortTanggalTerlama(){

        clearData();

        binding.rvWarungPupuk.setVisibility(View.GONE);
        binding.rvWarungPupukTerbaru.setVisibility(View.GONE);
        binding.rvWarungPupukTerlama.setVisibility(View.VISIBLE);
        binding.rvWarungPupukAz.setVisibility(View.GONE);
        binding.rvWarungPupukZa.setVisibility(View.GONE);
        binding.rvWarungPupukTerdekat.setVisibility(View.GONE);
        binding.rvWarungPupukHargaTerendah.setVisibility(View.GONE);
        binding.rvWarungPupukHargaTertinggi.setVisibility(View.GONE);

        urutanTanggal.clear();
        if (listBibitdanPupuk.size()>0){
            for(int a=0; a<listBibitdanPupuk.size(); a++){
                String b = listBibitdanPupuk.get(a).getCreatedDate();
                b.substring(0, b.length() - 6);
                urutanTanggal.add(b);
            }
            Collections.sort(urutanTanggal, new StringDateComparator());

            for(int z=0; z<urutanTanggal.size(); z++){
                // i=2
                String dt = urutanTanggal.get(z);
                for (int x=0; x<listBibitdanPupuk.size(); x++){
                    if(listBibitdanPupuk.get(x).getCreatedDate().equalsIgnoreCase(dt)){
                        listBibitdanPupukSorted.add(listBibitdanPupuk.get(x));
                    }
                }
            }

            if (mode.equalsIgnoreCase("Kotak")){
                itemList = new AdapterListWarungBibitdanPupuk(listBibitdanPupukSorted);
                binding.rvWarungPupukTerlama.setLayoutManager(new GridLayoutManager(ListWarungBibitdanPupuk.this, 2));
                binding.rvWarungPupukTerlama.setAdapter(itemList);
                binding.rvWarungPupukTerlama.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPupukTerlama,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungBibitdanPupuk.this, DetailWarungBibitdanPupuk.class);
                                a.putExtra("id", listBibitdanPupukSorted.get(position).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if(mode.equalsIgnoreCase("Garis")){
                itemListGaris = new AdapterListWarungBibitdanPupukGaris(listBibitdanPupukSorted);
                binding.rvWarungPupukTerlama.setLayoutManager(new LinearLayoutManager(ListWarungBibitdanPupuk.this));
                binding.rvWarungPupukTerlama.setAdapter(itemListGaris);
                binding.rvWarungPupukTerlama.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPupukTerlama,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungBibitdanPupuk.this, DetailWarungBibitdanPupuk.class);
                                a.putExtra("id", listBibitdanPupukSorted.get(position).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if(mode.equalsIgnoreCase("Monitor")){
                itemListMonitor = new AdapterListWarungBibitdanPupukMonitor(listBibitdanPupukSorted);
                binding.rvWarungPupukTerlama.setLayoutManager(new LinearLayoutManager(ListWarungBibitdanPupuk.this));
                binding.rvWarungPupukTerlama.setAdapter(itemListMonitor);
                binding.rvWarungPupukTerlama.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPupukTerlama,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungBibitdanPupuk.this, DetailWarungBibitdanPupuk.class);
                                a.putExtra("id", listBibitdanPupukSorted.get(position).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else {
                itemList = new AdapterListWarungBibitdanPupuk(listBibitdanPupukSorted);
                binding.rvWarungPupukTerlama.setLayoutManager(new GridLayoutManager(ListWarungBibitdanPupuk.this, 2));
                binding.rvWarungPupukTerlama.setAdapter(itemList);
                binding.rvWarungPupukTerlama.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPupukTerlama,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungBibitdanPupuk.this, DetailWarungBibitdanPupuk.class);
                                a.putExtra("id", listBibitdanPupukSorted.get(position).getIdWarungBpp());
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
            Toast.makeText(ListWarungBibitdanPupuk.this, "Data warung tidak ada", Toast.LENGTH_SHORT).show();
        }
    }

    public void sortNamaAZ(){
        clearData();

        binding.rvWarungPupuk.setVisibility(View.GONE);
        binding.rvWarungPupukTerbaru.setVisibility(View.GONE);
        binding.rvWarungPupukTerlama.setVisibility(View.GONE);
        binding.rvWarungPupukAz.setVisibility(View.VISIBLE);
        binding.rvWarungPupukZa.setVisibility(View.GONE);
        binding.rvWarungPupukTerdekat.setVisibility(View.GONE);
        binding.rvWarungPupukHargaTerendah.setVisibility(View.GONE);
        binding.rvWarungPupukHargaTertinggi.setVisibility(View.GONE);

        urutanNama.clear();
        if (listBibitdanPupuk.size()>0){
            for(int a=0; a<listBibitdanPupuk.size(); a++){
                urutanNama.add(listBibitdanPupuk.get(a).getNamaProduk());
            }
            java.util.Collections.sort(urutanNama);

            for(int z=0; z<urutanNama.size(); z++){
                // i=2
                String nama = urutanNama.get(z);
                for (int x=0; x<listBibitdanPupuk.size(); x++){
                    if(listBibitdanPupuk.get(x).getNamaProduk().equalsIgnoreCase(nama)){
                        listBibitdanPupukSorted.add(listBibitdanPupuk.get(x));
                    }
                }
            }

            if (mode.equalsIgnoreCase("Kotak")){
                itemList = new AdapterListWarungBibitdanPupuk(listBibitdanPupukSorted);
                binding.rvWarungPupukAz.setLayoutManager(new GridLayoutManager(ListWarungBibitdanPupuk.this, 2));
                binding.rvWarungPupukAz.setAdapter(itemList);
                binding.rvWarungPupukAz.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPupukAz,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungBibitdanPupuk.this, DetailWarungBibitdanPupuk.class);
                                a.putExtra("id", listBibitdanPupukSorted.get(po).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if(mode.equalsIgnoreCase("Garis")){
                itemListGaris = new AdapterListWarungBibitdanPupukGaris(listBibitdanPupukSorted);
                binding.rvWarungPupukAz.setLayoutManager(new LinearLayoutManager(ListWarungBibitdanPupuk.this));
                binding.rvWarungPupukAz.setAdapter(itemListGaris);
                binding.rvWarungPupukAz.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPupukAz,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungBibitdanPupuk.this, DetailWarungBibitdanPupuk.class);
                                a.putExtra("id", listBibitdanPupukSorted.get(po).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if(mode.equalsIgnoreCase("Monitor")){
                itemListMonitor = new AdapterListWarungBibitdanPupukMonitor(listBibitdanPupukSorted);
                binding.rvWarungPupukAz.setLayoutManager(new LinearLayoutManager(ListWarungBibitdanPupuk.this));
                binding.rvWarungPupukAz.setAdapter(itemListMonitor);
                binding.rvWarungPupukAz.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPupukAz,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungBibitdanPupuk.this, DetailWarungBibitdanPupuk.class);
                                a.putExtra("id", listBibitdanPupukSorted.get(po).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else {
                itemList = new AdapterListWarungBibitdanPupuk(listBibitdanPupukSorted);
                binding.rvWarungPupukAz.setLayoutManager(new GridLayoutManager(ListWarungBibitdanPupuk.this, 2));
                binding.rvWarungPupukAz.setAdapter(itemList);
                binding.rvWarungPupukAz.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPupukAz,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungBibitdanPupuk.this, DetailWarungBibitdanPupuk.class);
                                a.putExtra("id", listBibitdanPupukSorted.get(po).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            }


        } else {
            Toast.makeText(ListWarungBibitdanPupuk.this, "Data warung tidak ada", Toast.LENGTH_SHORT).show();
        }
    }

    public void sortNamaZA(){
        clearData();

        binding.rvWarungPupuk.setVisibility(View.GONE);
        binding.rvWarungPupukTerbaru.setVisibility(View.GONE);
        binding.rvWarungPupukTerlama.setVisibility(View.GONE);
        binding.rvWarungPupukAz.setVisibility(View.GONE);
        binding.rvWarungPupukZa.setVisibility(View.VISIBLE);
        binding.rvWarungPupukTerdekat.setVisibility(View.GONE);
        binding.rvWarungPupukHargaTerendah.setVisibility(View.GONE);
        binding.rvWarungPupukHargaTertinggi.setVisibility(View.GONE);

        urutanNama.clear();
        if (listBibitdanPupuk.size()>0){
            for(int a=0; a<listBibitdanPupuk.size(); a++){
                urutanNama.add(listBibitdanPupuk.get(a).getNamaProduk());
            }
            java.util.Collections.sort(urutanNama);

            for(int z=0; z<urutanNama.size(); z++){
                // i=2
                String nama = urutanNama.get(z);
                for (int x=0; x<listBibitdanPupuk.size(); x++){
                    if(listBibitdanPupuk.get(x).getNamaProduk().equalsIgnoreCase(nama)){
                        listBibitdanPupukSorted.add(listBibitdanPupuk.get(x));
                    }
                }
            }

            for(int y=urutanNama.size()-1; y>=0; y--){
                listBibitdanPupukSorted2.add(listBibitdanPupukSorted.get(y));
            }

            if(mode.equalsIgnoreCase("Kotak")){
                itemList = new AdapterListWarungBibitdanPupuk(listBibitdanPupukSorted2);
                binding.rvWarungPupukZa.setLayoutManager(new GridLayoutManager(ListWarungBibitdanPupuk.this, 2));
                binding.rvWarungPupukZa.setAdapter(itemList);
                binding.rvWarungPupukZa.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPupukZa,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungBibitdanPupuk.this, DetailWarungBibitdanPupuk.class);
                                a.putExtra("id", listBibitdanPupukSorted2.get(position).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if(mode.equalsIgnoreCase("Garis")){
                itemListGaris = new AdapterListWarungBibitdanPupukGaris(listBibitdanPupukSorted2);
                binding.rvWarungPupukZa.setLayoutManager(new LinearLayoutManager(ListWarungBibitdanPupuk.this));
                binding.rvWarungPupukZa.setAdapter(itemListGaris);
                binding.rvWarungPupukZa.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPupukZa,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungBibitdanPupuk.this, DetailWarungBibitdanPupuk.class);
                                a.putExtra("id", listBibitdanPupukSorted2.get(position).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if(mode.equalsIgnoreCase("Monitor")){
                itemListMonitor = new AdapterListWarungBibitdanPupukMonitor(listBibitdanPupukSorted2);
                binding.rvWarungPupukZa.setLayoutManager(new LinearLayoutManager(ListWarungBibitdanPupuk.this));
                binding.rvWarungPupukZa.setAdapter(itemListMonitor);
                binding.rvWarungPupukZa.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPupukZa,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungBibitdanPupuk.this, DetailWarungBibitdanPupuk.class);
                                a.putExtra("id", listBibitdanPupukSorted2.get(position).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else {
                itemList = new AdapterListWarungBibitdanPupuk(listBibitdanPupukSorted2);
                binding.rvWarungPupukZa.setLayoutManager(new GridLayoutManager(ListWarungBibitdanPupuk.this, 2));
                binding.rvWarungPupukZa.setAdapter(itemList);
                binding.rvWarungPupukZa.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPupukZa,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungBibitdanPupuk.this, DetailWarungBibitdanPupuk.class);
                                a.putExtra("id", listBibitdanPupukSorted2.get(position).getIdWarungBpp());
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
            Toast.makeText(ListWarungBibitdanPupuk.this, "Data warung tidak ada", Toast.LENGTH_SHORT).show();
        }
    }

    public void sortHargaTerendah(){
        clearData();

        binding.rvWarungPupuk.setVisibility(View.GONE);
        binding.rvWarungPupukTerbaru.setVisibility(View.GONE);
        binding.rvWarungPupukTerlama.setVisibility(View.GONE);
        binding.rvWarungPupukAz.setVisibility(View.GONE);
        binding.rvWarungPupukZa.setVisibility(View.GONE);
        binding.rvWarungPupukTerdekat.setVisibility(View.GONE);
        binding.rvWarungPupukHargaTerendah.setVisibility(View.VISIBLE);
        binding.rvWarungPupukHargaTertinggi.setVisibility(View.GONE);

        urutanHarga.clear();
        if (listBibitdanPupuk.size()>0){
            for(int a=0; a<listBibitdanPupuk.size(); a++){
                urutanHarga.add(listBibitdanPupuk.get(a).getHargaProduk());
            }
            Collections.sort(urutanHarga);

            for(int z=0; z<urutanHarga.size(); z++){
                // i=2
                Integer harga = urutanHarga.get(z);
                for (int x=0; x<listBibitdanPupuk.size(); x++){
                    if(listBibitdanPupuk.get(x).getHargaProduk()==harga){
                        listBibitdanPupukSortedHargaTerendah.add(listBibitdanPupuk.get(x));
                    }
                }
            }

            if (mode.equalsIgnoreCase("Kotak")){
                itemList = new AdapterListWarungBibitdanPupuk(listBibitdanPupukSortedHargaTerendah);
                binding.rvWarungPupukHargaTerendah.setLayoutManager(new GridLayoutManager(ListWarungBibitdanPupuk.this, 2));
                binding.rvWarungPupukHargaTerendah.setAdapter(itemList);
                binding.rvWarungPupukHargaTerendah.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPupukHargaTerendah,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungBibitdanPupuk.this, DetailWarungBibitdanPupuk.class);
                                a.putExtra("id", listBibitdanPupukSortedHargaTerendah.get(po).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));

            } else if(mode.equalsIgnoreCase("Garis")){
                itemListGaris = new AdapterListWarungBibitdanPupukGaris(listBibitdanPupukSortedHargaTerendah);
                binding.rvWarungPupukHargaTerendah.setLayoutManager(new LinearLayoutManager(ListWarungBibitdanPupuk.this));
                binding.rvWarungPupukHargaTerendah.setAdapter(itemListGaris);
                binding.rvWarungPupukHargaTerendah.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPupukHargaTerendah,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungBibitdanPupuk.this, DetailWarungBibitdanPupuk.class);
                                a.putExtra("id", listBibitdanPupukSortedHargaTerendah.get(po).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));

            } else if(mode.equalsIgnoreCase("Monitor")){
                itemListMonitor = new AdapterListWarungBibitdanPupukMonitor(listBibitdanPupukSortedHargaTerendah);
                binding.rvWarungPupukHargaTerendah.setLayoutManager(new LinearLayoutManager(ListWarungBibitdanPupuk.this));
                binding.rvWarungPupukHargaTerendah.setAdapter(itemListMonitor);
                binding.rvWarungPupukHargaTerendah.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPupukHargaTerendah,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungBibitdanPupuk.this, DetailWarungBibitdanPupuk.class);
                                a.putExtra("id", listBibitdanPupukSortedHargaTerendah.get(po).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));

            } else {
                itemList = new AdapterListWarungBibitdanPupuk(listBibitdanPupukSortedHargaTerendah);
                binding.rvWarungPupukHargaTerendah.setLayoutManager(new GridLayoutManager(ListWarungBibitdanPupuk.this, 2));
                binding.rvWarungPupukHargaTerendah.setAdapter(itemList);
                binding.rvWarungPupukHargaTerendah.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPupukHargaTerendah,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungBibitdanPupuk.this, DetailWarungBibitdanPupuk.class);
                                a.putExtra("id", listBibitdanPupukSortedHargaTerendah.get(po).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));

            }


        } else {
            Toast.makeText(ListWarungBibitdanPupuk.this, "Data warung tidak ada", Toast.LENGTH_SHORT).show();
        }


    }

    public void sortHargaTertinggi(){
        clearData();

        binding.rvWarungPupuk.setVisibility(View.GONE);
        binding.rvWarungPupukTerbaru.setVisibility(View.GONE);
        binding.rvWarungPupukTerlama.setVisibility(View.GONE);
        binding.rvWarungPupukAz.setVisibility(View.GONE);
        binding.rvWarungPupukZa.setVisibility(View.GONE);
        binding.rvWarungPupukTerdekat.setVisibility(View.GONE);
        binding.rvWarungPupukHargaTerendah.setVisibility(View.GONE);
        binding.rvWarungPupukHargaTertinggi.setVisibility(View.VISIBLE);

        urutanHarga.clear();
        if (listBibitdanPupuk.size()>0){
            for(int a=0; a<listBibitdanPupuk.size(); a++){
                urutanHarga.add(listBibitdanPupuk.get(a).getHargaProduk());
            }

            Collections.sort(urutanHarga);
            Collections.reverse(urutanHarga);

            for(int z=0; z<urutanHarga.size(); z++){
                // i=2
                Integer harga = urutanHarga.get(z);
                for (int x=0; x<listBibitdanPupuk.size(); x++){
                    if(listBibitdanPupuk.get(x).getHargaProduk()==harga){
                        listBibitdanPupukSortedHargaTertinggi.add(listBibitdanPupuk.get(x));
                    }
                }
            }

            if (mode.equalsIgnoreCase("Kotak")){
                itemList = new AdapterListWarungBibitdanPupuk(listBibitdanPupukSortedHargaTertinggi);
                binding.rvWarungPupukHargaTertinggi.setLayoutManager(new GridLayoutManager(ListWarungBibitdanPupuk.this, 2));
                binding.rvWarungPupukHargaTertinggi.setAdapter(itemList);
                binding.rvWarungPupukHargaTertinggi.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPupukHargaTertinggi,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungBibitdanPupuk.this, DetailWarungBibitdanPupuk.class);
                                a.putExtra("id", listBibitdanPupukSortedHargaTertinggi.get(po).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if(mode.equalsIgnoreCase("Garis")){
                itemListGaris = new AdapterListWarungBibitdanPupukGaris(listBibitdanPupukSortedHargaTertinggi);
                binding.rvWarungPupukHargaTertinggi.setLayoutManager(new LinearLayoutManager(ListWarungBibitdanPupuk.this));
                binding.rvWarungPupukHargaTertinggi.setAdapter(itemListGaris);
                binding.rvWarungPupukHargaTertinggi.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPupukHargaTertinggi,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungBibitdanPupuk.this, DetailWarungBibitdanPupuk.class);
                                a.putExtra("id", listBibitdanPupukSortedHargaTertinggi.get(po).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if(mode.equalsIgnoreCase("Monitor")){
                itemListMonitor = new AdapterListWarungBibitdanPupukMonitor(listBibitdanPupukSortedHargaTertinggi);
                binding.rvWarungPupukHargaTertinggi.setLayoutManager(new LinearLayoutManager(ListWarungBibitdanPupuk.this));
                binding.rvWarungPupukHargaTertinggi.setAdapter(itemListMonitor);
                binding.rvWarungPupukHargaTertinggi.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPupukHargaTertinggi,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungBibitdanPupuk.this, DetailWarungBibitdanPupuk.class);
                                a.putExtra("id", listBibitdanPupukSortedHargaTertinggi.get(po).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else {
                itemList = new AdapterListWarungBibitdanPupuk(listBibitdanPupukSortedHargaTertinggi);
                binding.rvWarungPupukHargaTertinggi.setLayoutManager(new GridLayoutManager(ListWarungBibitdanPupuk.this, 2));
                binding.rvWarungPupukHargaTertinggi.setAdapter(itemList);
                binding.rvWarungPupukHargaTertinggi.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPupukHargaTertinggi,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungBibitdanPupuk.this, DetailWarungBibitdanPupuk.class);
                                a.putExtra("id", listBibitdanPupukSortedHargaTertinggi.get(po).getIdWarungBpp());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            }



        } else {
            Toast.makeText(ListWarungBibitdanPupuk.this, "Data warung tidak ada", Toast.LENGTH_SHORT).show();
        }


    }

    public void checkTampilan(){
        if (urutkan.equalsIgnoreCase("Normal")){
            setDataBibitdanPupuk();
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
            setDataBibitdanPupuk();
        }
    }

    public void goToTenagaKerja(){
        Intent a = new Intent(ListWarungBibitdanPupuk.this, ListWarungTenagaKerja.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }

    public void goToSewaMesin(){
        Intent a = new Intent(ListWarungBibitdanPupuk.this, ListWarungSewaMesin.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }

    public void goToPestisida(){
        Intent a = new Intent(ListWarungBibitdanPupuk.this, ListWarungPestisida.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }

    public void goToBeranda(){
        Intent a = new Intent(ListWarungBibitdanPupuk.this, Home.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();
    }

    public void goToWarungku(){
        Intent a = new Intent(ListWarungBibitdanPupuk.this, PesananWarungku.class);
        startActivity(a);
        finish();
    }

    public void goToPesan(){
        Intent a = new Intent(ListWarungBibitdanPupuk.this, InboxPesan.class);
        startActivity(a);
        finish();
    }

    public void goToProfilLahan(){
        Intent a = new Intent(ListWarungBibitdanPupuk.this, ListProfileLahan.class);
        startActivity(a);
        finish();
    }

    public void goToProfilAkun(){
        Intent a = new Intent(ListWarungBibitdanPupuk.this, BerandaProfile.class);
        startActivity(a);
        finish();
    }

    @Override
    public void onBackPressed() {
        goToBeranda();
    }
}