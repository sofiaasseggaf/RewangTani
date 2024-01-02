package com.rewangTani.rewangtani.middlebar.warungsewamesin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
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
import com.rewangTani.rewangtani.adapter.adaptermiddlebar.adapterlistgaris.AdapterListWarungMesinGaris;
import com.rewangTani.rewangtani.adapter.adaptermiddlebar.adapterlistkotak.AdapterListWarungMesin;
import com.rewangTani.rewangtani.adapter.adaptermiddlebar.adapterlistmonitor.AdapterListWarungMesinMonitor;
import com.rewangTani.rewangtani.bottombar.Home;
import com.rewangTani.rewangtani.databinding.MiddlebarListWarungMesinBinding;
import com.rewangTani.rewangtani.middlebar.warungbibitdanpupuk.ListWarungBibitdanPupuk;
import com.rewangTani.rewangtani.middlebar.warungpestisida.ListWarungPestisida;
import com.rewangTani.rewangtani.middlebar.warungtenagakerja.ListWarungTenagaKerja;
import com.rewangTani.rewangtani.model.modelwarungwarung.modelsewamesin.DatumSewaMesin;
import com.rewangTani.rewangtani.model.modelwarungwarung.modelsewamesin.ModelSewaMesin;
import com.rewangTani.rewangtani.utility.RecyclerItemClickListener;
import com.rewangTani.rewangtani.utility.StringDateComparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListWarungSewaMesin extends AppCompatActivity {

    MiddlebarListWarungMesinBinding binding;
    AdapterListWarungMesin itemList;
    AdapterListWarungMesinGaris itemListGaris;
    AdapterListWarungMesinMonitor itemListMonitor;
    ModelSewaMesin modelSewaMesin;
    List<DatumSewaMesin> listSewaMesin = new ArrayList<>();
    List<DatumSewaMesin> listSewaMesinSorted = new ArrayList<>();
    List<DatumSewaMesin> listSewaMesinSorted2 = new ArrayList<>();
    List<DatumSewaMesin> listSewaMesinSortedHargaTerendah = new ArrayList<>();
    List<DatumSewaMesin> listSewaMesinSortedHargaTertinggi = new ArrayList<>();
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
        binding = DataBindingUtil.setContentView(this, R.layout.middlebar_list_warung_mesin);

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
                    Toast.makeText(ListWarungSewaMesin.this, "Fitur belum tersedia", Toast.LENGTH_SHORT).show();
                } else if (namaUrutan.equalsIgnoreCase("Harga Terendah")){
                    urutkan = "Harga Terendah";
                    sortHargaTerendah();
                } else if (namaUrutan.equalsIgnoreCase("Harga Tertinggi")){
                    urutkan = "Harga Tertinggi";
                    sortHargaTertinggi();
                } else {
                    urutkan = "Normal";
                    setDataSewaMesin();
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

        binding.btnPupuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToBibit();
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
                getSewaMesin();
            }
        }).start();
    }

    public void getSewaMesin() {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<ModelSewaMesin> dataRT = apiInterface.getDataWarungSewaMesin();
        dataRT.enqueue(new Callback<ModelSewaMesin>() {
            @Override
            public void onResponse(Call<ModelSewaMesin> call, Response<ModelSewaMesin> response) {
                modelSewaMesin = response.body();
                if (response.body()!=null){
                    listSewaMesin.clear();
                    for (int i = 0; i < modelSewaMesin.getTotalData(); i++) {
                        listSewaMesin.add(modelSewaMesin.getData().get(i));
                    }
                    if (listSewaMesin.size()>0){
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
            public void onFailure(Call<ModelSewaMesin> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(ListWarungSewaMesin.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void setSpinnerUrutan(){
        urutan = getResources().getStringArray(R.array.urutan);
        ArrayAdapter<String> adapterUrutan = new ArrayAdapter<String>(ListWarungSewaMesin.this, R.layout.z_spinner_list_urutan, urutan);
        adapterUrutan.setDropDownViewResource(R.layout.z_spinner_list);
        binding.spUrutkan.setAdapter(adapterUrutan);

        if (listSewaMesin.size()>0){
            setDataSewaMesin();
        } else {

        }

        CustomSpinnerAdapter customAdapter = new CustomSpinnerAdapter(getApplicationContext(),images,tampilan);
        binding.spTampilan.setAdapter(customAdapter);
    }

    public void checkTampilan(){
        if (urutkan.equalsIgnoreCase("Normal")){
            setDataSewaMesin();
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
            setDataSewaMesin();
        }
    }

    public void setDataSewaMesin(){

        urutkan = "Normal";

        binding.rvWarungSewamesin.setVisibility(View.VISIBLE);
        binding.rvWarungSewamesinTerbaru.setVisibility(View.GONE);
        binding.rvWarungSewamesinTerlama.setVisibility(View.GONE);
        binding.rvWarungSewamesinAz.setVisibility(View.GONE);
        binding.rvWarungSewamesinZa.setVisibility(View.GONE);
        binding.rvWarungSewamesinTerdekat.setVisibility(View.GONE);
        binding.rvWarungSewamesinHargaTerendah.setVisibility(View.GONE);
        binding.rvWarungSewamesinHargaTertinggi.setVisibility(View.GONE);

        if (mode.equalsIgnoreCase("Kotak")){
            itemList = new AdapterListWarungMesin(listSewaMesin);
            binding.rvWarungSewamesin.setLayoutManager(new GridLayoutManager(ListWarungSewaMesin.this, 2));
            binding.rvWarungSewamesin.setAdapter(itemList);
            binding.rvWarungSewamesin.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungSewamesin,
                    new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent a = new Intent(ListWarungSewaMesin.this, DetailWarungSewaMesin.class);
                            a.putExtra("id", listSewaMesin.get(position).getIdSewaMesin());
                            startActivity(a);
                        }
                        @Override
                        public void onLongItemClick(View view, int position) {

                        }
                    }));
        } else if(mode.equalsIgnoreCase("Garis")){
            itemListGaris = new AdapterListWarungMesinGaris(listSewaMesin);
            binding.rvWarungSewamesin.setLayoutManager(new LinearLayoutManager(ListWarungSewaMesin.this));
            binding.rvWarungSewamesin.setAdapter(itemListGaris);
            binding.rvWarungSewamesin.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungSewamesin,
                    new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent a = new Intent(ListWarungSewaMesin.this, DetailWarungSewaMesin.class);
                            a.putExtra("id", listSewaMesin.get(position).getIdSewaMesin());
                            startActivity(a);
                        }
                        @Override
                        public void onLongItemClick(View view, int position) {

                        }
                    }));
        } else if(mode.equalsIgnoreCase("Monitor")) {
            itemListMonitor = new AdapterListWarungMesinMonitor(listSewaMesin);
            binding.rvWarungSewamesin.setLayoutManager(new LinearLayoutManager(ListWarungSewaMesin.this));
            binding.rvWarungSewamesin.setAdapter(itemListMonitor);
            binding.rvWarungSewamesin.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungSewamesin,
                    new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent a = new Intent(ListWarungSewaMesin.this, DetailWarungSewaMesin.class);
                            a.putExtra("id", listSewaMesin.get(position).getIdSewaMesin());
                            startActivity(a);
                        }
                        @Override
                        public void onLongItemClick(View view, int position) {

                        }
                    }));
        } else {
            itemList = new AdapterListWarungMesin(listSewaMesin);
            binding.rvWarungSewamesin.setLayoutManager(new GridLayoutManager(ListWarungSewaMesin.this, 2));
            binding.rvWarungSewamesin.setAdapter(itemList);
            binding.rvWarungSewamesin.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungSewamesin,
                    new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent a = new Intent(ListWarungSewaMesin.this, DetailWarungSewaMesin.class);
                            a.putExtra("id", listSewaMesin.get(position).getIdSewaMesin());
                            startActivity(a);
                        }
                        @Override
                        public void onLongItemClick(View view, int position) {

                        }
                    }));
        }

    }

    private void clearData() {
        int size = listSewaMesinSorted.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                listSewaMesinSorted.remove(0);
            }

            itemList.notifyItemRangeRemoved(0, size);
        }

        int size2 = listSewaMesinSorted2.size();
        if (size2 > 0) {
            for (int i = 0; i < size2; i++) {
                listSewaMesinSorted2.remove(0);
            }

            itemList.notifyItemRangeRemoved(0, size2);
        }

        int size3 = listSewaMesinSortedHargaTerendah.size();
        if (size3 > 0) {
            for (int i = 0; i < size3; i++) {
                listSewaMesinSortedHargaTerendah.remove(0);
            }

            itemList.notifyItemRangeRemoved(0, size3);
        }

        int size4 = listSewaMesinSortedHargaTertinggi.size();
        if (size4 > 0) {
            for (int i = 0; i < size4; i++) {
                listSewaMesinSortedHargaTertinggi.remove(0);
            }

            itemList.notifyItemRangeRemoved(0, size4);
        }
    }

    public void sortTanggalTerbaru(){
        clearData();

        binding.rvWarungSewamesin.setVisibility(View.GONE);
        binding.rvWarungSewamesinTerbaru.setVisibility(View.VISIBLE);
        binding.rvWarungSewamesinTerlama.setVisibility(View.GONE);
        binding.rvWarungSewamesinAz.setVisibility(View.GONE);
        binding.rvWarungSewamesinZa.setVisibility(View.GONE);
        binding.rvWarungSewamesinTerdekat.setVisibility(View.GONE);
        binding.rvWarungSewamesinHargaTerendah.setVisibility(View.GONE);
        binding.rvWarungSewamesinHargaTertinggi.setVisibility(View.GONE);

        urutanTanggal.clear();
        if (listSewaMesin.size()>0){
            for(int a=0; a<listSewaMesin.size(); a++){
                String b = listSewaMesin.get(a).getCreatedDate();
                b.substring(0, b.length() - 6);
                urutanTanggal.add(b);
            }
            Collections.sort(urutanTanggal, new StringDateComparator());

            for(int z=urutanTanggal.size()-1; z>=0; z--){
                // i=2
                String dt = urutanTanggal.get(z);
                for (int x=0; x<listSewaMesin.size(); x++){
                    if(listSewaMesin.get(x).getCreatedDate().equalsIgnoreCase(dt)){
                        listSewaMesinSorted.add(listSewaMesin.get(x));
                    }
                }
            }

            if (mode.equalsIgnoreCase("Kotak")){
                itemList = new AdapterListWarungMesin(listSewaMesinSorted);
                binding.rvWarungSewamesinTerbaru.setLayoutManager(new GridLayoutManager(ListWarungSewaMesin.this, 2));
                binding.rvWarungSewamesinTerbaru.setAdapter(itemList);
                binding.rvWarungSewamesinTerbaru.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungSewamesinTerbaru,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungSewaMesin.this, DetailWarungSewaMesin.class);
                                a.putExtra("id", listSewaMesinSorted.get(position).getIdSewaMesin());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if (mode.equalsIgnoreCase("Garis")){
                itemListGaris = new AdapterListWarungMesinGaris(listSewaMesinSorted);
                binding.rvWarungSewamesinTerbaru.setLayoutManager(new LinearLayoutManager(ListWarungSewaMesin.this));
                binding.rvWarungSewamesinTerbaru.setAdapter(itemListGaris);
                binding.rvWarungSewamesinTerbaru.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungSewamesinTerbaru,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungSewaMesin.this, DetailWarungSewaMesin.class);
                                a.putExtra("id", listSewaMesinSorted.get(position).getIdSewaMesin());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if (mode.equalsIgnoreCase("Monitor")){
                itemListMonitor = new AdapterListWarungMesinMonitor(listSewaMesinSorted);
                binding.rvWarungSewamesinTerbaru.setLayoutManager(new LinearLayoutManager(ListWarungSewaMesin.this));
                binding.rvWarungSewamesinTerbaru.setAdapter(itemListMonitor);
                binding.rvWarungSewamesinTerbaru.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungSewamesinTerbaru,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungSewaMesin.this, DetailWarungSewaMesin.class);
                                a.putExtra("id", listSewaMesinSorted.get(position).getIdSewaMesin());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else {
                itemList = new AdapterListWarungMesin(listSewaMesinSorted);
                binding.rvWarungSewamesinTerbaru.setLayoutManager(new GridLayoutManager(ListWarungSewaMesin.this, 2));
                binding.rvWarungSewamesinTerbaru.setAdapter(itemList);
                binding.rvWarungSewamesinTerbaru.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungSewamesinTerbaru,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungSewaMesin.this, DetailWarungSewaMesin.class);
                                a.putExtra("id", listSewaMesinSorted.get(position).getIdSewaMesin());
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
            Toast.makeText(ListWarungSewaMesin.this, "Data warung tidak ada", Toast.LENGTH_SHORT).show();
        }
    }

    public void sortTanggalTerlama(){
        clearData();

        binding.rvWarungSewamesin.setVisibility(View.GONE);
        binding.rvWarungSewamesinTerbaru.setVisibility(View.GONE);
        binding.rvWarungSewamesinTerlama.setVisibility(View.VISIBLE);
        binding.rvWarungSewamesinAz.setVisibility(View.GONE);
        binding.rvWarungSewamesinZa.setVisibility(View.GONE);
        binding.rvWarungSewamesinTerdekat.setVisibility(View.GONE);
        binding.rvWarungSewamesinHargaTerendah.setVisibility(View.GONE);
        binding.rvWarungSewamesinHargaTertinggi.setVisibility(View.GONE);

        urutanTanggal.clear();
        if (listSewaMesin.size()>0){
            for(int a=0; a<listSewaMesin.size(); a++){
                String b = listSewaMesin.get(a).getCreatedDate();
                b.substring(0, b.length() - 6);
                urutanTanggal.add(b);
            }
            Collections.sort(urutanTanggal, new StringDateComparator());

            for(int z=0; z<urutanTanggal.size(); z++){
                // i=2
                String dt = urutanTanggal.get(z);
                for (int x=0; x<listSewaMesin.size(); x++){
                    if(listSewaMesin.get(x).getCreatedDate().equalsIgnoreCase(dt)){
                        listSewaMesinSorted.add(listSewaMesin.get(x));
                    }
                }
            }

            if (mode.equalsIgnoreCase("Kotak")){
                itemList = new AdapterListWarungMesin(listSewaMesinSorted);
                binding.rvWarungSewamesinTerlama.setLayoutManager(new GridLayoutManager(ListWarungSewaMesin.this, 2));
                binding.rvWarungSewamesinTerlama.setAdapter(itemList);
                binding.rvWarungSewamesinTerlama.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungSewamesinTerlama,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungSewaMesin.this, DetailWarungSewaMesin.class);
                                a.putExtra("id", listSewaMesinSorted.get(position).getIdSewaMesin());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if (mode.equalsIgnoreCase("Garis")){
                itemListGaris = new AdapterListWarungMesinGaris(listSewaMesinSorted);
                binding.rvWarungSewamesinTerlama.setLayoutManager(new LinearLayoutManager(ListWarungSewaMesin.this));
                binding.rvWarungSewamesinTerlama.setAdapter(itemListGaris);
                binding.rvWarungSewamesinTerlama.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungSewamesinTerlama,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungSewaMesin.this, DetailWarungSewaMesin.class);
                                a.putExtra("id", listSewaMesinSorted.get(position).getIdSewaMesin());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if (mode.equalsIgnoreCase("Monitor")){
                itemListMonitor = new AdapterListWarungMesinMonitor(listSewaMesinSorted);
                binding.rvWarungSewamesinTerlama.setLayoutManager(new LinearLayoutManager(ListWarungSewaMesin.this));
                binding.rvWarungSewamesinTerlama.setAdapter(itemListMonitor);
                binding.rvWarungSewamesinTerlama.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungSewamesinTerlama,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungSewaMesin.this, DetailWarungSewaMesin.class);
                                a.putExtra("id", listSewaMesinSorted.get(position).getIdSewaMesin());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else {
                itemList = new AdapterListWarungMesin(listSewaMesinSorted);
                binding.rvWarungSewamesinTerlama.setLayoutManager(new GridLayoutManager(ListWarungSewaMesin.this, 2));
                binding.rvWarungSewamesinTerlama.setAdapter(itemList);
                binding.rvWarungSewamesinTerlama.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungSewamesinTerlama,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungSewaMesin.this, DetailWarungSewaMesin.class);
                                a.putExtra("id", listSewaMesinSorted.get(position).getIdSewaMesin());
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
            Toast.makeText(ListWarungSewaMesin.this, "Data warung tidak ada", Toast.LENGTH_SHORT).show();
        }
    }

    public void sortNamaAZ(){
        clearData();

        binding.rvWarungSewamesin.setVisibility(View.GONE);
        binding.rvWarungSewamesinTerbaru.setVisibility(View.GONE);
        binding.rvWarungSewamesinTerlama.setVisibility(View.GONE);
        binding.rvWarungSewamesinAz.setVisibility(View.VISIBLE);
        binding.rvWarungSewamesinZa.setVisibility(View.GONE);
        binding.rvWarungSewamesinTerdekat.setVisibility(View.GONE);
        binding.rvWarungSewamesinHargaTerendah.setVisibility(View.GONE);
        binding.rvWarungSewamesinHargaTertinggi.setVisibility(View.GONE);

        urutanNama.clear();
        if (listSewaMesin.size()>0){
            for(int a=0; a<listSewaMesin.size(); a++){
                urutanNama.add(listSewaMesin.get(a).getNamaProduk());
            }
            java.util.Collections.sort(urutanNama);

            for(int z=0; z<urutanNama.size(); z++){
                // i=2
                String nama = urutanNama.get(z);
                for (int x=0; x<listSewaMesin.size(); x++){
                    if(listSewaMesin.get(x).getNamaProduk().equalsIgnoreCase(nama)){
                        listSewaMesinSorted.add(listSewaMesin.get(x));
                    }
                }
            }

            if(mode.equalsIgnoreCase("Kotak")){
                itemList = new AdapterListWarungMesin(listSewaMesinSorted);
                binding.rvWarungSewamesinAz.setLayoutManager(new GridLayoutManager(ListWarungSewaMesin.this, 2));
                binding.rvWarungSewamesinAz.setAdapter(itemList);
                binding.rvWarungSewamesinAz.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungSewamesinAz,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungSewaMesin.this, DetailWarungSewaMesin.class);
                                a.putExtra("id", listSewaMesinSorted.get(po).getIdSewaMesin());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if (mode.equalsIgnoreCase("Garis")){
                itemListGaris = new AdapterListWarungMesinGaris(listSewaMesinSorted);
                binding.rvWarungSewamesinAz.setLayoutManager(new LinearLayoutManager(ListWarungSewaMesin.this));
                binding.rvWarungSewamesinAz.setAdapter(itemListGaris);
                binding.rvWarungSewamesinAz.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungSewamesinAz,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungSewaMesin.this, DetailWarungSewaMesin.class);
                                a.putExtra("id", listSewaMesinSorted.get(po).getIdSewaMesin());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if (mode.equalsIgnoreCase("Monitor")){
                itemListMonitor = new AdapterListWarungMesinMonitor(listSewaMesinSorted);
                binding.rvWarungSewamesinAz.setLayoutManager(new LinearLayoutManager(ListWarungSewaMesin.this));
                binding.rvWarungSewamesinAz.setAdapter(itemListMonitor);
                binding.rvWarungSewamesinAz.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungSewamesinAz,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungSewaMesin.this, DetailWarungSewaMesin.class);
                                a.putExtra("id", listSewaMesinSorted.get(po).getIdSewaMesin());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else {
                itemList = new AdapterListWarungMesin(listSewaMesinSorted);
                binding.rvWarungSewamesinAz.setLayoutManager(new GridLayoutManager(ListWarungSewaMesin.this, 2));
                binding.rvWarungSewamesinAz.setAdapter(itemList);
                binding.rvWarungSewamesinAz.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungSewamesinAz,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungSewaMesin.this, DetailWarungSewaMesin.class);
                                a.putExtra("id", listSewaMesinSorted.get(po).getIdSewaMesin());
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
            Toast.makeText(ListWarungSewaMesin.this, "Data warung tidak ada", Toast.LENGTH_SHORT).show();
        }
    }

    public void sortNamaZA(){
        clearData();

        binding.rvWarungSewamesin.setVisibility(View.GONE);
        binding.rvWarungSewamesinTerbaru.setVisibility(View.GONE);
        binding.rvWarungSewamesinTerlama.setVisibility(View.GONE);
        binding.rvWarungSewamesinAz.setVisibility(View.GONE);
        binding.rvWarungSewamesinZa.setVisibility(View.VISIBLE);
        binding.rvWarungSewamesinTerdekat.setVisibility(View.GONE);
        binding.rvWarungSewamesinHargaTerendah.setVisibility(View.GONE);
        binding.rvWarungSewamesinHargaTertinggi.setVisibility(View.GONE);

        urutanNama.clear();
        if (listSewaMesin.size()>0){
            for(int a=0; a<listSewaMesin.size(); a++){
                urutanNama.add(listSewaMesin.get(a).getNamaProduk());
            }
            java.util.Collections.sort(urutanNama);

            for(int z=0; z<urutanNama.size(); z++){
                // i=2
                String nama = urutanNama.get(z);
                for (int x=0; x<listSewaMesin.size(); x++){
                    if(listSewaMesin.get(x).getNamaProduk().equalsIgnoreCase(nama)){
                        listSewaMesinSorted.add(listSewaMesin.get(x));
                    }
                }
            }

            for(int y=urutanNama.size()-1; y>=0; y--){
                listSewaMesinSorted2.add(listSewaMesinSorted.get(y));
            }

            if (mode.equalsIgnoreCase("Kotak")){
                itemList = new AdapterListWarungMesin(listSewaMesinSorted2);
                binding.rvWarungSewamesinZa.setLayoutManager(new GridLayoutManager(ListWarungSewaMesin.this, 2));
                binding.rvWarungSewamesinZa.setAdapter(itemList);
                binding.rvWarungSewamesinZa.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungSewamesinZa,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungSewaMesin.this, DetailWarungSewaMesin.class);
                                a.putExtra("id", listSewaMesinSorted2.get(position).getIdSewaMesin());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if (mode.equalsIgnoreCase("Garis")){
                itemListGaris = new AdapterListWarungMesinGaris(listSewaMesinSorted2);
                binding.rvWarungSewamesinZa.setLayoutManager(new LinearLayoutManager(ListWarungSewaMesin.this));
                binding.rvWarungSewamesinZa.setAdapter(itemListGaris);
                binding.rvWarungSewamesinZa.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungSewamesinZa,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungSewaMesin.this, DetailWarungSewaMesin.class);
                                a.putExtra("id", listSewaMesinSorted2.get(position).getIdSewaMesin());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if (mode.equalsIgnoreCase("Monitor")){
                itemListMonitor = new AdapterListWarungMesinMonitor(listSewaMesinSorted2);
                binding.rvWarungSewamesinZa.setLayoutManager(new LinearLayoutManager(ListWarungSewaMesin.this));
                binding.rvWarungSewamesinZa.setAdapter(itemListMonitor);
                binding.rvWarungSewamesinZa.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungSewamesinZa,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungSewaMesin.this, DetailWarungSewaMesin.class);
                                a.putExtra("id", listSewaMesinSorted2.get(position).getIdSewaMesin());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else {
                itemList = new AdapterListWarungMesin(listSewaMesinSorted2);
                binding.rvWarungSewamesinZa.setLayoutManager(new GridLayoutManager(ListWarungSewaMesin.this, 2));
                binding.rvWarungSewamesinZa.setAdapter(itemList);
                binding.rvWarungSewamesinZa.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungSewamesinZa,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent a = new Intent(ListWarungSewaMesin.this, DetailWarungSewaMesin.class);
                                a.putExtra("id", listSewaMesinSorted2.get(position).getIdSewaMesin());
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
            Toast.makeText(ListWarungSewaMesin.this, "Data warung tidak ada", Toast.LENGTH_SHORT).show();
        }
    }

    public void sortHargaTerendah(){
        clearData();

        binding.rvWarungSewamesin.setVisibility(View.GONE);
        binding.rvWarungSewamesinTerbaru.setVisibility(View.GONE);
        binding.rvWarungSewamesinTerlama.setVisibility(View.GONE);
        binding.rvWarungSewamesinAz.setVisibility(View.GONE);
        binding.rvWarungSewamesinZa.setVisibility(View.VISIBLE);
        binding.rvWarungSewamesinTerdekat.setVisibility(View.GONE);
        binding.rvWarungSewamesinHargaTerendah.setVisibility(View.VISIBLE);
        binding.rvWarungSewamesinHargaTertinggi.setVisibility(View.GONE);

        urutanHarga.clear();
        if (listSewaMesin.size()>0){
            for(int a=0; a<listSewaMesin.size(); a++){
                urutanHarga.add(listSewaMesin.get(a).getHargaProduk());
            }

            Collections.sort(urutanHarga);
            //Arrays.sort(new List[]{urutanHarga});

            for(int z=0; z<urutanHarga.size(); z++){
                // i=2
                Integer harga = urutanHarga.get(z);
                for (int x=0; x<listSewaMesin.size(); x++){
                    if(listSewaMesin.get(x).getHargaProduk()==harga){
                        listSewaMesinSortedHargaTerendah.add(listSewaMesin.get(x));
                    }
                }
            }

            if (mode.equalsIgnoreCase("Kotak")){
                itemList = new AdapterListWarungMesin(listSewaMesinSortedHargaTerendah);
                binding.rvWarungSewamesinHargaTerendah.setLayoutManager(new GridLayoutManager(ListWarungSewaMesin.this, 2));
                binding.rvWarungSewamesinHargaTerendah.setAdapter(itemList);
                binding.rvWarungSewamesinHargaTerendah.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungSewamesinHargaTerendah,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungSewaMesin.this, DetailWarungSewaMesin.class);
                                a.putExtra("id", listSewaMesinSortedHargaTerendah.get(po).getIdSewaMesin());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if (mode.equalsIgnoreCase("Garis")){
                itemListGaris = new AdapterListWarungMesinGaris(listSewaMesinSortedHargaTerendah);
                binding.rvWarungSewamesinHargaTerendah.setLayoutManager(new LinearLayoutManager(ListWarungSewaMesin.this));
                binding.rvWarungSewamesinHargaTerendah.setAdapter(itemListGaris);
                binding.rvWarungSewamesinHargaTerendah.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungSewamesinHargaTerendah,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungSewaMesin.this, DetailWarungSewaMesin.class);
                                a.putExtra("id", listSewaMesinSortedHargaTerendah.get(po).getIdSewaMesin());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if (mode.equalsIgnoreCase("Monitor")){
                itemListMonitor = new AdapterListWarungMesinMonitor(listSewaMesinSortedHargaTerendah);
                binding.rvWarungSewamesinHargaTerendah.setLayoutManager(new LinearLayoutManager(ListWarungSewaMesin.this));
                binding.rvWarungSewamesinHargaTerendah.setAdapter(itemListMonitor);
                binding.rvWarungSewamesinHargaTerendah.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungSewamesinHargaTerendah,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungSewaMesin.this, DetailWarungSewaMesin.class);
                                a.putExtra("id", listSewaMesinSortedHargaTerendah.get(po).getIdSewaMesin());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else {
                itemList = new AdapterListWarungMesin(listSewaMesinSortedHargaTerendah);
                binding.rvWarungSewamesinHargaTerendah.setLayoutManager(new GridLayoutManager(ListWarungSewaMesin.this, 2));
                binding.rvWarungSewamesinHargaTerendah.setAdapter(itemList);
                binding.rvWarungSewamesinHargaTerendah.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungSewamesinHargaTerendah,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungSewaMesin.this, DetailWarungSewaMesin.class);
                                a.putExtra("id", listSewaMesinSortedHargaTerendah.get(po).getIdSewaMesin());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            }


        } else {
            Toast.makeText(ListWarungSewaMesin.this, "Data warung tidak ada", Toast.LENGTH_SHORT).show();
        }


    }

    public void sortHargaTertinggi(){
        clearData();

        binding.rvWarungSewamesin.setVisibility(View.GONE);
        binding.rvWarungSewamesinTerbaru.setVisibility(View.GONE);
        binding.rvWarungSewamesinTerlama.setVisibility(View.GONE);
        binding.rvWarungSewamesinAz.setVisibility(View.GONE);
        binding.rvWarungSewamesinZa.setVisibility(View.GONE);
        binding.rvWarungSewamesinTerdekat.setVisibility(View.GONE);
        binding.rvWarungSewamesinHargaTerendah.setVisibility(View.GONE);
        binding.rvWarungSewamesinHargaTertinggi.setVisibility(View.VISIBLE);

        urutanHarga.clear();
        if (listSewaMesin.size()>0){
            for(int a=0; a<listSewaMesin.size(); a++){
                urutanHarga.add(listSewaMesin.get(a).getHargaProduk());
            }

            Collections.sort(urutanHarga);
            Collections.reverse(urutanHarga);

            for(int z=0; z<urutanHarga.size(); z++){
                // i=2
                Integer harga = urutanHarga.get(z);
                for (int x=0; x<listSewaMesin.size(); x++){
                    if(listSewaMesin.get(x).getHargaProduk()==harga){
                        listSewaMesinSortedHargaTertinggi.add(listSewaMesin.get(x));
                    }
                }
            }

            if (mode.equalsIgnoreCase("Kotak")){
                itemList = new AdapterListWarungMesin(listSewaMesinSortedHargaTertinggi);
                binding.rvWarungSewamesinHargaTertinggi.setLayoutManager(new GridLayoutManager(ListWarungSewaMesin.this, 2));
                binding.rvWarungSewamesinHargaTertinggi.setAdapter(itemList);
                binding.rvWarungSewamesinHargaTertinggi.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungSewamesinHargaTertinggi,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungSewaMesin.this, DetailWarungSewaMesin.class);
                                a.putExtra("id", listSewaMesinSortedHargaTertinggi.get(po).getIdSewaMesin());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if (mode.equalsIgnoreCase("Garis")){
                itemListGaris = new AdapterListWarungMesinGaris(listSewaMesinSortedHargaTertinggi);
                binding.rvWarungSewamesinHargaTertinggi.setLayoutManager(new LinearLayoutManager(ListWarungSewaMesin.this));
                binding.rvWarungSewamesinHargaTertinggi.setAdapter(itemListGaris);
                binding.rvWarungSewamesinHargaTertinggi.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungSewamesinHargaTertinggi,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungSewaMesin.this, DetailWarungSewaMesin.class);
                                a.putExtra("id", listSewaMesinSortedHargaTertinggi.get(po).getIdSewaMesin());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else if (mode.equalsIgnoreCase("Monitor")){
                itemListMonitor = new AdapterListWarungMesinMonitor(listSewaMesinSortedHargaTertinggi);
                binding.rvWarungSewamesinHargaTertinggi.setLayoutManager(new LinearLayoutManager(ListWarungSewaMesin.this));
                binding.rvWarungSewamesinHargaTertinggi.setAdapter(itemListMonitor);
                binding.rvWarungSewamesinHargaTertinggi.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungSewamesinHargaTertinggi,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungSewaMesin.this, DetailWarungSewaMesin.class);
                                a.putExtra("id", listSewaMesinSortedHargaTertinggi.get(po).getIdSewaMesin());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            } else {
                itemList = new AdapterListWarungMesin(listSewaMesinSortedHargaTertinggi);
                binding.rvWarungSewamesinHargaTertinggi.setLayoutManager(new GridLayoutManager(ListWarungSewaMesin.this, 2));
                binding.rvWarungSewamesinHargaTertinggi.setAdapter(itemList);
                binding.rvWarungSewamesinHargaTertinggi.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungSewamesinHargaTertinggi,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int po) {
                                Intent a = new Intent(ListWarungSewaMesin.this, DetailWarungSewaMesin.class);
                                a.putExtra("id", listSewaMesinSortedHargaTertinggi.get(po).getIdSewaMesin());
                                startActivity(a);
                            }
                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        }));
            }



        } else {
            Toast.makeText(ListWarungSewaMesin.this, "Data warung tidak ada", Toast.LENGTH_SHORT).show();
        }


    }

    public void goToTenagaKerja(){
        Intent a = new Intent(ListWarungSewaMesin.this, ListWarungTenagaKerja.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();
    }

    public void goToSewaMesin(){
        Intent a = new Intent(ListWarungSewaMesin.this, ListWarungSewaMesin.class);
        startActivity(a);
        finish();
    }

    public void goToBibit(){
        Intent a = new Intent(ListWarungSewaMesin.this, ListWarungBibitdanPupuk.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();
    }

    public void goToPestisida(){
        Intent a = new Intent(ListWarungSewaMesin.this, ListWarungPestisida.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();
    }


    public void goToBeranda(){
        Intent a = new Intent(ListWarungSewaMesin.this, Home.class);
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