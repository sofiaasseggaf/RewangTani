package com.rewangTani.rewangtani.adapter.adapterupperbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.model.modelupperbar.panen.DatumPanen;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.DatumRencanaTanam;

import java.util.List;

public class AdapterListPanen extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<DatumPanen> dataItemList;
    List<DatumRencanaTanam> dataRT;

    public AdapterListPanen(List<DatumPanen> dataItemList, List<DatumRencanaTanam> dataRT) {
        this.dataItemList = dataItemList;
        this.dataRT = dataRT;
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
        ((Penampung)holder).nama.setText(dataRT.get(position).getNamaRencanaTanam());
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
