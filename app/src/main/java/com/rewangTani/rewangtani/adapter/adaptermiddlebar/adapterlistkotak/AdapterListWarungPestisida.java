package com.rewangTani.rewangtani.adapter.adaptermiddlebar.adapterlistkotak;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.model.modelwarungwarung.modelpupukpestisida.DatumPupukPestisida;
import com.rewangTani.rewangtani.model.modelwarungwarung.modeltenagakerja.DatumTenagaKerja;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AdapterListWarungPestisida extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<DatumPupukPestisida> dataItemList;
    DecimalFormat formatter;

    public AdapterListWarungPestisida(List<DatumPupukPestisida> dataItemList) {
        this.dataItemList = dataItemList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.z_warung, parent, false);
        Penampung penampung = new Penampung(view);
        return penampung;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((Penampung)holder).nama_warung.setText(dataItemList.get(position).getNamaProduk());
        String a = checkDesimal(dataItemList.get(position).getHargaProduk().toString());
        ((Penampung)holder).biaya_warung.setText("Rp " + a);
        ((Penampung)holder).lokasi_warung.setText(dataItemList.get(position).getKota());
        if (!dataItemList.get(position).getIdFoto().equalsIgnoreCase("")){
            String imageUri = "http://167.172.72.217:8080/tanampadi/v1/photo/read?id="+dataItemList.get(position).getIdFoto();
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
        public TextView nama_warung, biaya_warung, lokasi_warung;
        public ImageView img_warung;
        public Penampung(View itemView) {
            super(itemView);
            nama_warung = itemView.findViewById(R.id.nama_warung);
            biaya_warung = itemView.findViewById(R.id.biaya_warung);
            lokasi_warung = itemView.findViewById(R.id.lokasi_warung);
            img_warung = itemView.findViewById(R.id.img_warung);
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
