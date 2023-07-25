package com.rewangTani.rewangtani.adapter.adapterbottombar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.model.modelprofillahan.DatumProfilLahan;

import java.util.List;

public class AdapterListProfilLahan extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<DatumProfilLahan> dataItemList;

    public AdapterListProfilLahan(List<DatumProfilLahan> dataItemList) {
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
        // id profil tanah nanti di ganti nama profil tanah
        ((Penampung)holder).nama.setText(dataItemList.get(position).getNamaProfilTanah());
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
