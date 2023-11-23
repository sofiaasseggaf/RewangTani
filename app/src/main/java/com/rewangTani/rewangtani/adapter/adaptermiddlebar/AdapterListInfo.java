package com.rewangTani.rewangtani.adapter.adaptermiddlebar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.model.modelinfo.DatumInfo;
import com.rewangTani.rewangtani.model.modelwarungwarung.modelsewamesin.DatumSewaMesin;

import java.util.List;

public class AdapterListInfo extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<DatumInfo> dataItemList;

    public AdapterListInfo(List<DatumInfo> dataItemList) {
        this.dataItemList = dataItemList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.z_list_info, parent, false);
        Penampung penampung = new Penampung(view);
        return penampung;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((Penampung)holder).judul_info.setText(dataItemList.get(position).getJudulInfo());
        ((Penampung)holder).ket_info.setText(dataItemList.get(position).getKetInfo());
        ((Penampung)holder).sumber_info.setText("Sumber : "+dataItemList.get(position).getNamaSumber());
        ((Penampung)holder).tgl_info.setText(dataItemList.get(position).getCreatedDate());
    }

    @Override
    public int getItemCount() {
        return dataItemList == null ? 0 : dataItemList.size();
    }

    static class Penampung extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView judul_info, ket_info, sumber_info, tgl_info;
        public Penampung(View itemView) {
            super(itemView);
            judul_info = itemView.findViewById(R.id.judulInfo);
            ket_info = itemView.findViewById(R.id.ketInfo);
            sumber_info = itemView.findViewById(R.id.sumberInfo);
            tgl_info = itemView.findViewById(R.id.tglInfo);
        }
        @Override
        public void onClick(View v) {
            Log.d("onclick", "onClick " + getLayoutPosition() + " " + judul_info.getText());
        }
    }

}
