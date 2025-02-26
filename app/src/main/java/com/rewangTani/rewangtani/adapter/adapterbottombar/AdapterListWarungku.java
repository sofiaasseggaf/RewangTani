package com.rewangTani.rewangtani.adapter.adapterbottombar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.model.modelproduk.DatumProduk;
import com.rewangTani.rewangtani.model.modelwarungwarung.modelpupukpestisida.DatumPupukPestisida;
import com.rewangTani.rewangtani.model.modelwarungwarung.modelsewamesin.DatumSewaMesin;
import com.rewangTani.rewangtani.model.modelwarungwarung.modeltenagakerja.DatumTenagaKerja;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AdapterListWarungku extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<DatumProduk> dataItemList;
    List<DatumSewaMesin> sewaMesinList;
    List<DatumTenagaKerja> tenagaKerjaList;
    List<DatumPupukPestisida> pupukPestisidaList;
    DecimalFormat formatter;

    public AdapterListWarungku(List<DatumProduk> dataItemList, List<DatumSewaMesin> sewaMesinList, List<DatumTenagaKerja> tenagaKerjaList, List<DatumPupukPestisida> pupukPestisidaList) {
        this.dataItemList = dataItemList;
        this.sewaMesinList = sewaMesinList;
        this.tenagaKerjaList = tenagaKerjaList;
        this.pupukPestisidaList = pupukPestisidaList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.z_warungku, parent, false);
        Penampung penampung = new Penampung(view);
        return penampung;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            if (sewaMesinList.size()>0) {
                for (int j = 0; j < sewaMesinList.size(); j++) {
                    if (dataItemList.get(position).getIdProduk().equalsIgnoreCase(sewaMesinList.get(j).getIdProduk())) {
                        ((Penampung) holder).nama_warung.setText(sewaMesinList.get(j).getNamaProduk());
                        String a = checkDesimal(sewaMesinList.get(j).getHargaProduk().toString());
                        ((Penampung)holder).biaya_warung.setText("Rp. " + a);
                        if(sewaMesinList.get(j).getJmlTerjual()!=null){
                            ((Penampung) holder).jml_terjual.setText("Jumlah Terjual : "+sewaMesinList.get(j).getJmlTerjual().toString());
                        } else {
                            ((Penampung) holder).jml_terjual.setText("Jumlah Terjual : 0");
                        }
                    }
                }
            }

            if (tenagaKerjaList.size()>0){
                for (int j=0; j<tenagaKerjaList.size(); j++){
                    if (dataItemList.get(position).getIdProduk().equalsIgnoreCase(tenagaKerjaList.get(j).getIdProduk())){
                        ((Penampung)holder).nama_warung.setText(tenagaKerjaList.get(j).getNamaTenagaKerja());
                        String a = checkDesimal(tenagaKerjaList.get(j).getBiaya().toString());
                        ((Penampung)holder).biaya_warung.setText("Rp. " + a + " / Hari");
                        ((Penampung) holder).jml_terjual.setText("Jumlah Terjual : "+tenagaKerjaList.get(j).getJmlTerjual().toString());
                    }
                }
            }

            if (pupukPestisidaList.size()>0){
                for (int j=0; j<pupukPestisidaList.size(); j++){
                    if (dataItemList.get(position).getIdProduk().equalsIgnoreCase(pupukPestisidaList.get(j).getIdProduk())){
                        ((Penampung)holder).nama_warung.setText(pupukPestisidaList.get(j).getNamaProduk());
                        String a = checkDesimal(pupukPestisidaList.get(j).getHargaProduk().toString());
                        ((Penampung)holder).biaya_warung.setText("Rp. " + a);
                        ((Penampung) holder).jml_terjual.setText("Jumlah Terjual : "+pupukPestisidaList.get(j).getJmlTerjual().toString());
                    }
                }
            }

        if ( dataItemList.get(position).getFoto() != null ){
            String imageUri = "http://167.172.72.217:8080/tanampadi/v1/photo/read?id="+ dataItemList.get(position).getFoto() + "0";
            Picasso.get().load(imageUri).networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(((Penampung) holder).img_warung);
        }

    }

    @Override
    public int getItemCount() {
        return dataItemList == null ? 0 : dataItemList.size();
    }

    static class Penampung extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nama_warung, biaya_warung, jml_terjual;
        //public TextView lokasi_warung;
        public ImageView img_warung;
        public Penampung(View itemView) {
            super(itemView);
            nama_warung = itemView.findViewById(R.id.namaWarung);
            biaya_warung = itemView.findViewById(R.id.biayaWarung);
            //lokasi_warung = itemView.findViewById(R.id.lokasiWarung);
            jml_terjual = itemView.findViewById(R.id.jmlTerjual);
            img_warung = itemView.findViewById(R.id.imgWarung);
        }
        @Override
        public void onClick(View v) {
            Log.d("onclick", "onClick " + getLayoutPosition() + " " + nama_warung.getText());
        }
    }

    private String checkDesimal(String a){

        formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator('.');
        formatter = new DecimalFormat("###,###.##", symbols);

        if(a!=null || !a.equalsIgnoreCase("")){
            if(a.length()>3){
                a = formatter.format(Double.valueOf(a));
            }
        }
        return a;
    }

}
