package com.rewangTani.rewangtani.middlebar.warungpestisida;

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
import com.rewangTani.rewangtani.adapter.adaptermiddlebar.adapterlistgaris.AdapterListWarungPestisidaGaris;
import com.rewangTani.rewangtani.adapter.adaptermiddlebar.adapterlistkotak.AdapterListWarungPestisida;
import com.rewangTani.rewangtani.adapter.adaptermiddlebar.adapterlistmonitor.AdapterListWarungPestisidaMonitor;
import com.rewangTani.rewangtani.bottombar.Home;
import com.rewangTani.rewangtani.databinding.MiddlebarListWarungPestisidaBinding;
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

    MiddlebarListWarungPestisidaBinding binding;
    AdapterListWarungPestisida itemList;
    AdapterListWarungPestisidaGaris itemListGaris;
    AdapterListWarungPestisidaMonitor itemListMonitor;
    ModelPupukPestisida modelPupukPestisida;
    List<DatumPupukPestisida> listPestisida = new ArrayList<>();
    List<DatumPupukPestisida> listPestisidaSorted = new ArrayList<>();
    List<DatumPupukPestisida> listPestisidaSorted2 = new ArrayList<>();
    List<DatumPupukPestisida> listPestisidaSortedHargaTerendah = new ArrayList<>();
    List<DatumPupukPestisida> listPestisidaSortedHargaTertinggi = new ArrayList<>();
    String namaUrutan;
    String[] urutan;
    List<String> urutanTanggal = new ArrayList<>();
    List<String> urutanNama = new ArrayList<>();
    List<Integer> urutanHarga = new ArrayList<>();
    String mode = "Kotak";
    String urutkan;
    String[] tampilan = {"Kotak", "Garis", "Monitor"};
    int images[] = {R.drawable.mode_kotak, R.drawable.mode_garis, R.drawable.mode_monitor};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.middlebar_list_warung_pestisida);

        //getData();

        binding.spUrutkan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                namaUrutan = binding.spUrutkan.getSelectedItem().toString();
                if (namaUrutan.equalsIgnoreCase("Tanggal Terbaru")) {
                    urutkan = "Tanggal Terbaru";
                    sortTanggalTerbaru();
                } else if (namaUrutan.equalsIgnoreCase("Tanggal Terlama")) {
                    urutkan = "Tanggal Terlama";
                    sortTanggalTerlama();
                } else if (namaUrutan.equalsIgnoreCase("A-Z")) {
                    urutkan = "A-Z";
                    sortNamaAZ();
                } else if (namaUrutan.equalsIgnoreCase("Z-A")) {
                    urutkan = "Z-A";
                    sortNamaZA();
                } else if (namaUrutan.equalsIgnoreCase("Terdekat")) {
                    Toast.makeText(ListWarungPestisida.this, "Fitur belum tersedia", Toast.LENGTH_SHORT).show();
                } else if (namaUrutan.equalsIgnoreCase("Harga Terendah")) {
                    urutkan = "Harga Terendah";
                    sortHargaTerendah();
                } else if (namaUrutan.equalsIgnoreCase("Harga Tertinggi")) {
                    urutkan = "Harga Tertinggi";
                    sortHargaTertinggi();
                } else {
                    urutkan = "Normal";
                    setDataPestisida();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        binding.spTampilan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long arg3) {
                namaUrutan = tampilan[pos];
                if (namaUrutan.equalsIgnoreCase("Kotak")) {
                    mode = "Kotak";
                    checkTampilan();
                } else if (namaUrutan.equalsIgnoreCase("Garis")) {
                    mode = "Garis";
                    checkTampilan();
                } else if (namaUrutan.equalsIgnoreCase("Monitor")) {
                    mode = "Monitor";
                    checkTampilan();
                } else {
                    mode = "Kotak";
                    checkTampilan();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
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

        binding.btnPupuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToBibit();
            }
        });

//        btn_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                goToBeranda();
//            }
//        });

    }

    private void getData() {
        findViewById(R.id.viewLoading).setVisibility(View.VISIBLE);
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int count = 0;

            @Override
            public void run() {
                count++;
                if (count == 1) {
                    binding.textLoading.setText("Tunggu sebentar ya .");
                } else if (count == 2) {
                    binding.textLoading.setText("TTunggu sebentar ya . .");
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
                if (response.body() != null) {
                    listPestisida.clear();
                    for (int i = 0; i < modelPupukPestisida.getTotalData(); i++) {
                        if (modelPupukPestisida.getData().get(i).getIdTipeProduk()
                                .equalsIgnoreCase("ad211570-6943-4e4c-88b2-c7837a0a3b28"))
                            listPestisida.add(modelPupukPestisida.getData().get(i));
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.viewLoading).setVisibility(View.GONE);
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
                        findViewById(R.id.viewLoading).setVisibility(View.GONE);
                        Toast.makeText(ListWarungPestisida.this, "Terjadi Gangguan Koneksi", Toast.LENGTH_LONG).show();
                        call.cancel();
                    }
                });
            }
        });
    }

    public void setSpinnerUrutan() {
        urutan = getResources().getStringArray(R.array.urutan);
        ArrayAdapter<String> adapterUrutan = new ArrayAdapter<String>(ListWarungPestisida.this, R.layout.z_spinner_list_urutan, urutan);
        adapterUrutan.setDropDownViewResource(R.layout.z_spinner_list);
        binding.spUrutkan.setAdapter(adapterUrutan);

        if (listPestisida.size() > 0) {
            setDataPestisida();
        } else {

        }

        CustomSpinnerAdapter customAdapter = new CustomSpinnerAdapter(getApplicationContext(), images, tampilan);
        binding.spTampilan.setAdapter(customAdapter);
    }

    public void setDataPestisida() {

        urutkan = "Normal";

        binding.rvWarungPestisida.setVisibility(View.VISIBLE);
        binding.rvWarungPestisidaTerbaru.setVisibility(View.GONE);
        binding.rvWarungPestisidaTerlama.setVisibility(View.GONE);
        binding.rvWarungPestisidaAz.setVisibility(View.GONE);
        binding.rvWarungPestisidaZa.setVisibility(View.GONE);
        binding.rvWarungPestisidaTerdekat.setVisibility(View.GONE);
        binding.rvWarungPestisidaHargaTerendah.setVisibility(View.GONE);
        binding.rvWarungPestisidaHargaTertinggi.setVisibility(View.GONE);

        if (mode.equalsIgnoreCase("Kotak")) {
            itemList = new AdapterListWarungPestisida(listPestisida);
            binding.rvWarungPestisida.setLayoutManager(new GridLayoutManager(ListWarungPestisida.this, 2));
            binding.rvWarungPestisida.setAdapter(itemList);
            binding.rvWarungPestisida.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPestisida,
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
        } else if (mode.equalsIgnoreCase("Garis")) {
            itemListGaris = new AdapterListWarungPestisidaGaris(listPestisida);
            binding.rvWarungPestisida.setLayoutManager(new LinearLayoutManager(ListWarungPestisida.this));
            binding.rvWarungPestisida.setAdapter(itemListGaris);
            binding.rvWarungPestisida.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPestisida,
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
        } else if (mode.equalsIgnoreCase("Monitor")) {
            itemListMonitor = new AdapterListWarungPestisidaMonitor(listPestisida);
            binding.rvWarungPestisida.setLayoutManager(new LinearLayoutManager(ListWarungPestisida.this));
            binding.rvWarungPestisida.setAdapter(itemListMonitor);
            binding.rvWarungPestisida.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPestisida,
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
            binding.rvWarungPestisida.setLayoutManager(new GridLayoutManager(ListWarungPestisida.this, 2));
            binding.rvWarungPestisida.setAdapter(itemList);
            binding.rvWarungPestisida.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPestisida,
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

    public void sortTanggalTerbaru() {
        clearData();

        binding.rvWarungPestisida.setVisibility(View.GONE);
        binding.rvWarungPestisidaTerbaru.setVisibility(View.VISIBLE);
        binding.rvWarungPestisidaTerlama.setVisibility(View.GONE);
        binding.rvWarungPestisidaAz.setVisibility(View.GONE);
        binding.rvWarungPestisidaZa.setVisibility(View.GONE);
        binding.rvWarungPestisidaTerdekat.setVisibility(View.GONE);
        binding.rvWarungPestisidaHargaTerendah.setVisibility(View.GONE);
        binding.rvWarungPestisidaHargaTertinggi.setVisibility(View.GONE);

        urutanTanggal.clear();
        if (listPestisida.size() > 0) {
            for (int a = 0; a < listPestisida.size(); a++) {
                String b = listPestisida.get(a).getCreatedDate();
                b.substring(0, b.length() - 6);
                urutanTanggal.add(b);
            }
            Collections.sort(urutanTanggal, new StringDateComparator());

            for (int z = urutanTanggal.size() - 1; z >= 0; z--) {
                // i=2
                String dt = urutanTanggal.get(z);
                for (int x = 0; x < listPestisida.size(); x++) {
                    if (listPestisida.get(x).getCreatedDate().equalsIgnoreCase(dt)) {
                        listPestisidaSorted.add(listPestisida.get(x));
                    }
                }
            }

            if (mode.equalsIgnoreCase("Kotak")) {
                itemList = new AdapterListWarungPestisida(listPestisidaSorted);
                binding.rvWarungPestisidaTerbaru.setLayoutManager(new GridLayoutManager(ListWarungPestisida.this, 2));
                binding.rvWarungPestisidaTerbaru.setAdapter(itemList);
                binding.rvWarungPestisidaTerbaru.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPestisidaTerbaru,
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
            } else if (mode.equalsIgnoreCase("Garis")) {
                itemListGaris = new AdapterListWarungPestisidaGaris(listPestisidaSorted);
                binding.rvWarungPestisidaTerbaru.setLayoutManager(new LinearLayoutManager(ListWarungPestisida.this));
                binding.rvWarungPestisidaTerbaru.setAdapter(itemListGaris);
                binding.rvWarungPestisidaTerbaru.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPestisidaTerbaru,
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
            } else if (mode.equalsIgnoreCase("Monitor")) {
                itemListMonitor = new AdapterListWarungPestisidaMonitor(listPestisidaSorted);
                binding.rvWarungPestisidaTerbaru.setLayoutManager(new LinearLayoutManager(ListWarungPestisida.this));
                binding.rvWarungPestisidaTerbaru.setAdapter(itemListMonitor);
                binding.rvWarungPestisidaTerbaru.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPestisidaTerbaru,
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
                binding.rvWarungPestisidaTerbaru.setLayoutManager(new GridLayoutManager(ListWarungPestisida.this, 2));
                binding.rvWarungPestisidaTerbaru.setAdapter(itemList);
                binding.rvWarungPestisidaTerbaru.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPestisidaTerbaru,
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

    public void sortTanggalTerlama() {
        clearData();

        binding.rvWarungPestisida.setVisibility(View.GONE);
        binding.rvWarungPestisidaTerbaru.setVisibility(View.GONE);
        binding.rvWarungPestisidaTerlama.setVisibility(View.VISIBLE);
        binding.rvWarungPestisidaAz.setVisibility(View.GONE);
        binding.rvWarungPestisidaZa.setVisibility(View.GONE);
        binding.rvWarungPestisidaTerdekat.setVisibility(View.GONE);
        binding.rvWarungPestisidaHargaTerendah.setVisibility(View.GONE);
        binding.rvWarungPestisidaHargaTertinggi.setVisibility(View.GONE);

        urutanTanggal.clear();
        if (listPestisida.size() > 0) {
            for (int a = 0; a < listPestisida.size(); a++) {
                String b = listPestisida.get(a).getCreatedDate();
                b.substring(0, b.length() - 6);
                urutanTanggal.add(b);
            }
            Collections.sort(urutanTanggal, new StringDateComparator());

            for (int z = 0; z < urutanTanggal.size(); z++) {
                // i=2
                String dt = urutanTanggal.get(z);
                for (int x = 0; x < listPestisida.size(); x++) {
                    if (listPestisida.get(x).getCreatedDate().equalsIgnoreCase(dt)) {
                        listPestisidaSorted.add(listPestisida.get(x));
                    }
                }
            }

            if (mode.equalsIgnoreCase("Kotak")) {
                itemList = new AdapterListWarungPestisida(listPestisidaSorted);
                binding.rvWarungPestisidaTerlama.setLayoutManager(new GridLayoutManager(ListWarungPestisida.this, 2));
                binding.rvWarungPestisidaTerlama.setAdapter(itemList);
                binding.rvWarungPestisidaTerlama.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPestisidaTerlama,
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
            } else if (mode.equalsIgnoreCase("Garis")) {
                itemListGaris = new AdapterListWarungPestisidaGaris(listPestisidaSorted);
                binding.rvWarungPestisidaTerlama.setLayoutManager(new LinearLayoutManager(ListWarungPestisida.this));
                binding.rvWarungPestisidaTerlama.setAdapter(itemListGaris);
                binding.rvWarungPestisidaTerlama.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPestisidaTerlama,
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
            } else if (mode.equalsIgnoreCase("Monitor")) {
                itemListMonitor = new AdapterListWarungPestisidaMonitor(listPestisidaSorted);
                binding.rvWarungPestisidaTerlama.setLayoutManager(new LinearLayoutManager(ListWarungPestisida.this));
                binding.rvWarungPestisidaTerlama.setAdapter(itemListMonitor);
                binding.rvWarungPestisidaTerlama.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPestisidaTerlama,
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
                binding.rvWarungPestisidaTerlama.setLayoutManager(new GridLayoutManager(ListWarungPestisida.this, 2));
                binding.rvWarungPestisidaTerlama.setAdapter(itemList);
                binding.rvWarungPestisidaTerlama.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPestisidaTerlama,
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

    public void sortNamaAZ() {
        clearData();

        binding.rvWarungPestisida.setVisibility(View.GONE);
        binding.rvWarungPestisidaTerbaru.setVisibility(View.GONE);
        binding.rvWarungPestisidaTerlama.setVisibility(View.GONE);
        binding.rvWarungPestisidaAz.setVisibility(View.VISIBLE);
        binding.rvWarungPestisidaZa.setVisibility(View.GONE);
        binding.rvWarungPestisidaTerdekat.setVisibility(View.GONE);
        binding.rvWarungPestisidaHargaTerendah.setVisibility(View.GONE);
        binding.rvWarungPestisidaHargaTertinggi.setVisibility(View.GONE);

        urutanNama.clear();
        if (listPestisida.size() > 0) {
            for (int a = 0; a < listPestisida.size(); a++) {
                urutanNama.add(listPestisida.get(a).getNamaProduk());
            }
            java.util.Collections.sort(urutanNama);

            for (int z = 0; z < urutanNama.size(); z++) {
                // i=2
                String nama = urutanNama.get(z);
                for (int x = 0; x < listPestisida.size(); x++) {
                    if (listPestisida.get(x).getNamaProduk().equalsIgnoreCase(nama)) {
                        listPestisidaSorted.add(listPestisida.get(x));
                    }
                }
            }

            if (mode.equalsIgnoreCase("Kotak")) {
                itemList = new AdapterListWarungPestisida(listPestisidaSorted);
                binding.rvWarungPestisidaAz.setLayoutManager(new GridLayoutManager(ListWarungPestisida.this, 2));
                binding.rvWarungPestisidaAz.setAdapter(itemList);
                binding.rvWarungPestisidaAz.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPestisidaAz,
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
            } else if (mode.equalsIgnoreCase("Garis")) {
                itemListGaris = new AdapterListWarungPestisidaGaris(listPestisidaSorted);
                binding.rvWarungPestisidaAz.setLayoutManager(new LinearLayoutManager(ListWarungPestisida.this));
                binding.rvWarungPestisidaAz.setAdapter(itemListGaris);
                binding.rvWarungPestisidaAz.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPestisidaAz,
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
            } else if (mode.equalsIgnoreCase("Monitor")) {
                itemListMonitor = new AdapterListWarungPestisidaMonitor(listPestisidaSorted);
                binding.rvWarungPestisidaAz.setLayoutManager(new LinearLayoutManager(ListWarungPestisida.this));
                binding.rvWarungPestisidaAz.setAdapter(itemListMonitor);
                binding.rvWarungPestisidaAz.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPestisidaAz,
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
                binding.rvWarungPestisidaAz.setLayoutManager(new GridLayoutManager(ListWarungPestisida.this, 2));
                binding.rvWarungPestisidaAz.setAdapter(itemList);
                binding.rvWarungPestisidaAz.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPestisidaAz,
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

    public void sortNamaZA() {
        clearData();

        binding.rvWarungPestisida.setVisibility(View.GONE);
        binding.rvWarungPestisidaTerbaru.setVisibility(View.GONE);
        binding.rvWarungPestisidaTerlama.setVisibility(View.GONE);
        binding.rvWarungPestisidaAz.setVisibility(View.GONE);
        binding.rvWarungPestisidaZa.setVisibility(View.VISIBLE);
        binding.rvWarungPestisidaTerdekat.setVisibility(View.GONE);
        binding.rvWarungPestisidaHargaTerendah.setVisibility(View.GONE);
        binding.rvWarungPestisidaHargaTertinggi.setVisibility(View.GONE);

        urutanNama.clear();
        if (listPestisida.size() > 0) {
            for (int a = 0; a < listPestisida.size(); a++) {
                urutanNama.add(listPestisida.get(a).getNamaProduk());
            }
            java.util.Collections.sort(urutanNama);

            for (int z = 0; z < urutanNama.size(); z++) {
                // i=2
                String nama = urutanNama.get(z);
                for (int x = 0; x < listPestisida.size(); x++) {
                    if (listPestisida.get(x).getNamaProduk().equalsIgnoreCase(nama)) {
                        listPestisidaSorted.add(listPestisida.get(x));
                    }
                }
            }

            for (int y = urutanNama.size() - 1; y >= 0; y--) {
                listPestisidaSorted2.add(listPestisidaSorted.get(y));
            }

            if (mode.equalsIgnoreCase("Kotak")) {
                itemList = new AdapterListWarungPestisida(listPestisidaSorted2);
                binding.rvWarungPestisidaZa.setLayoutManager(new GridLayoutManager(ListWarungPestisida.this, 2));
                binding.rvWarungPestisidaZa.setAdapter(itemList);
                binding.rvWarungPestisidaZa.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPestisidaZa,
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
            } else if (mode.equalsIgnoreCase("Garis")) {
                itemListGaris = new AdapterListWarungPestisidaGaris(listPestisidaSorted2);
                binding.rvWarungPestisidaZa.setLayoutManager(new LinearLayoutManager(ListWarungPestisida.this));
                binding.rvWarungPestisidaZa.setAdapter(itemListGaris);
                binding.rvWarungPestisidaZa.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPestisidaZa,
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
            } else if (mode.equalsIgnoreCase("Monitor")) {
                itemListMonitor = new AdapterListWarungPestisidaMonitor(listPestisidaSorted2);
                binding.rvWarungPestisidaZa.setLayoutManager(new LinearLayoutManager(ListWarungPestisida.this));
                binding.rvWarungPestisidaZa.setAdapter(itemListMonitor);
                binding.rvWarungPestisidaZa.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPestisidaZa,
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
                binding.rvWarungPestisidaZa.setLayoutManager(new GridLayoutManager(ListWarungPestisida.this, 2));
                binding.rvWarungPestisidaZa.setAdapter(itemList);
                binding.rvWarungPestisidaZa.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPestisidaZa,
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

    public void sortHargaTerendah() {
        clearData();

        binding.rvWarungPestisida.setVisibility(View.GONE);
        binding.rvWarungPestisidaTerbaru.setVisibility(View.GONE);
        binding.rvWarungPestisidaTerlama.setVisibility(View.GONE);
        binding.rvWarungPestisidaAz.setVisibility(View.GONE);
        binding.rvWarungPestisidaZa.setVisibility(View.GONE);
        binding.rvWarungPestisidaTerdekat.setVisibility(View.GONE);
        binding.rvWarungPestisidaHargaTerendah.setVisibility(View.VISIBLE);
        binding.rvWarungPestisidaHargaTertinggi.setVisibility(View.GONE);

        urutanHarga.clear();
        if (listPestisida.size() > 0) {
            for (int a = 0; a < listPestisida.size(); a++) {
                urutanHarga.add(listPestisida.get(a).getHargaProduk());
            }
            Collections.sort(urutanHarga);

            for (int z = 0; z < urutanHarga.size(); z++) {
                // i=2
                Integer harga = urutanHarga.get(z);
                for (int x = 0; x < listPestisida.size(); x++) {
                    if (listPestisida.get(x).getHargaProduk() == harga) {
                        listPestisidaSortedHargaTerendah.add(listPestisida.get(x));
                    }
                }
            }

            if (mode.equalsIgnoreCase("Kotak")) {
                itemList = new AdapterListWarungPestisida(listPestisidaSortedHargaTerendah);
                binding.rvWarungPestisidaHargaTerendah.setLayoutManager(new GridLayoutManager(ListWarungPestisida.this, 2));
                binding.rvWarungPestisidaHargaTerendah.setAdapter(itemList);
                binding.rvWarungPestisidaHargaTerendah.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPestisidaHargaTerendah,
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
            } else if (mode.equalsIgnoreCase("Garis")) {
                itemListGaris = new AdapterListWarungPestisidaGaris(listPestisidaSortedHargaTerendah);
                binding.rvWarungPestisidaHargaTerendah.setLayoutManager(new LinearLayoutManager(ListWarungPestisida.this));
                binding.rvWarungPestisidaHargaTerendah.setAdapter(itemListGaris);
                binding.rvWarungPestisidaHargaTerendah.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPestisidaHargaTerendah,
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
            } else if (mode.equalsIgnoreCase("Monitor")) {
                itemListMonitor = new AdapterListWarungPestisidaMonitor(listPestisidaSortedHargaTerendah);
                binding.rvWarungPestisidaHargaTerendah.setLayoutManager(new LinearLayoutManager(ListWarungPestisida.this));
                binding.rvWarungPestisidaHargaTerendah.setAdapter(itemListMonitor);
                binding.rvWarungPestisidaHargaTerendah.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPestisidaHargaTerendah,
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
                binding.rvWarungPestisidaHargaTerendah.setLayoutManager(new GridLayoutManager(ListWarungPestisida.this, 2));
                binding.rvWarungPestisidaHargaTerendah.setAdapter(itemList);
                binding.rvWarungPestisidaHargaTerendah.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPestisidaHargaTerendah,
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

    public void sortHargaTertinggi() {
        clearData();

        binding.rvWarungPestisida.setVisibility(View.GONE);
        binding.rvWarungPestisidaTerbaru.setVisibility(View.GONE);
        binding.rvWarungPestisidaTerlama.setVisibility(View.GONE);
        binding.rvWarungPestisidaAz.setVisibility(View.GONE);
        binding.rvWarungPestisidaZa.setVisibility(View.GONE);
        binding.rvWarungPestisidaTerdekat.setVisibility(View.GONE);
        binding.rvWarungPestisidaHargaTerendah.setVisibility(View.GONE);
        binding.rvWarungPestisidaHargaTertinggi.setVisibility(View.VISIBLE);

        urutanHarga.clear();
        if (listPestisida.size() > 0) {
            for (int a = 0; a < listPestisida.size(); a++) {
                urutanHarga.add(listPestisida.get(a).getHargaProduk());
            }

            Collections.sort(urutanHarga);
            Collections.reverse(urutanHarga);

            for (int z = 0; z < urutanHarga.size(); z++) {
                // i=2
                Integer harga = urutanHarga.get(z);
                for (int x = 0; x < listPestisida.size(); x++) {
                    if (listPestisida.get(x).getHargaProduk() == harga) {
                        listPestisidaSortedHargaTertinggi.add(listPestisida.get(x));
                    }
                }
            }

            if (mode.equalsIgnoreCase("Kotak")) {
                itemList = new AdapterListWarungPestisida(listPestisidaSortedHargaTertinggi);
                binding.rvWarungPestisidaHargaTertinggi.setLayoutManager(new GridLayoutManager(ListWarungPestisida.this, 2));
                binding.rvWarungPestisidaHargaTertinggi.setAdapter(itemList);
                binding.rvWarungPestisidaHargaTertinggi.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPestisidaHargaTertinggi,
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
            } else if (mode.equalsIgnoreCase("Garis")) {
                itemListGaris = new AdapterListWarungPestisidaGaris(listPestisidaSortedHargaTertinggi);
                binding.rvWarungPestisidaHargaTertinggi.setLayoutManager(new LinearLayoutManager(ListWarungPestisida.this));
                binding.rvWarungPestisidaHargaTertinggi.setAdapter(itemListGaris);
                binding.rvWarungPestisidaHargaTertinggi.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPestisidaHargaTertinggi,
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
            } else if (mode.equalsIgnoreCase("Monitor")) {
                itemListMonitor = new AdapterListWarungPestisidaMonitor(listPestisidaSortedHargaTertinggi);
                binding.rvWarungPestisidaHargaTertinggi.setLayoutManager(new LinearLayoutManager(ListWarungPestisida.this));
                binding.rvWarungPestisidaHargaTertinggi.setAdapter(itemListMonitor);
                binding.rvWarungPestisidaHargaTertinggi.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPestisidaHargaTertinggi,
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
                binding.rvWarungPestisidaHargaTertinggi.setLayoutManager(new GridLayoutManager(ListWarungPestisida.this, 2));
                binding.rvWarungPestisidaHargaTertinggi.setAdapter(itemList);
                binding.rvWarungPestisidaHargaTertinggi.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), binding.rvWarungPestisidaHargaTertinggi,
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

    public void checkTampilan() {
        if (urutkan.equalsIgnoreCase("Normal")) {
            setDataPestisida();
        } else if (urutkan.equalsIgnoreCase("Tanggal Terbaru")) {
            sortTanggalTerbaru();
        } else if (urutkan.equalsIgnoreCase("Tanggal Terlama")) {
            sortTanggalTerlama();
        } else if (urutkan.equalsIgnoreCase("A-Z")) {
            sortNamaAZ();
        } else if (urutkan.equalsIgnoreCase("Z-A")) {
            sortNamaZA();
        } else if (urutkan.equalsIgnoreCase("Harga Terendah")) {
            sortHargaTerendah();
        } else if (urutkan.equalsIgnoreCase("Harga Tertinggi")) {
            sortHargaTertinggi();
        } else {
            setDataPestisida();
        }
    }

    public void goToTenagaKerja() {
        Intent a = new Intent(ListWarungPestisida.this, ListWarungTenagaKerja.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }

    public void goToSewaMesin() {
        Intent a = new Intent(ListWarungPestisida.this, ListWarungSewaMesin.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }

    public void goToBibit() {
        Intent a = new Intent(ListWarungPestisida.this, ListWarungBibitdanPupuk.class);
        startActivity(a);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();
    }

    public void goToPestisida() {
        Intent a = new Intent(ListWarungPestisida.this, ListWarungPestisida.class);
        startActivity(a);
        finish();
    }


    public void goToBeranda() {
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