package com.rewangTani.rewangtani.adapter.adapterupperbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.model.modelupperbar.kendalapertumbuhan.DatumKendalaPertumbuhan;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.DatumRencanaTanam;
import com.rewangTani.rewangtani.model.modelupperbar.sudahtanam.DatumSudahTanam;

import java.util.List;

public class AdapterListKendalaPertumbuhan extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<DatumKendalaPertumbuhan> dataItemList;
    List<DatumRencanaTanam> dataRT;

    public AdapterListKendalaPertumbuhan(List<DatumKendalaPertumbuhan> dataItemList, List<DatumRencanaTanam> dataRT) {
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
        // id rencana tanam, nanti diganti nama rencana tanam
        for(int j=0; j<dataRT.size(); j++){
            if (dataItemList.get(position).getIdSudahTanam().equalsIgnoreCase(dataRT.get(j).getIdRencanaTanam())){
                ((Penampung)holder).nama.setText(dataRT.get(j).getNamaRencanaTanam());
            }
        }
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
