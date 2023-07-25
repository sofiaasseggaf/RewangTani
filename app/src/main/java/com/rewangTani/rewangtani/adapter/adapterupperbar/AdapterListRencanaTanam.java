package com.rewangTani.rewangtani.adapter.adapterupperbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.DatumRencanaTanam;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AdapterListRencanaTanam extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<DatumRencanaTanam> dataItemList;
    DecimalFormat formatter;

    public AdapterListRencanaTanam(List<DatumRencanaTanam> dataItemList) {
        this.dataItemList = dataItemList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.z_list, parent, false);
        Penampung penampung = new Penampung(view);
        return penampung;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((Penampung)holder).nama.setText(dataItemList.get(position).getNamaRencanaTanam());
    }

    @Override
    public int getItemCount() {
        return dataItemList == null ? 0 : dataItemList.size();
     }

    static class Penampung extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nama;
        public Penampung(View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.nama);
        }
        @Override
        public void onClick(View v) {
            Log.d("onclick", "onClick " + getLayoutPosition() + " " + nama.getText());
        }
    }


}