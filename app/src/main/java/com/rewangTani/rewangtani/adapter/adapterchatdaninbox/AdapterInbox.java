package com.rewangTani.rewangtani.adapter.adapterchatdaninbox;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rewangTani.rewangtani.APIService.APIClient;
import com.rewangTani.rewangtani.APIService.APIInterfacesRest;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.model.modelakunprofil.DatumProfil;
import com.rewangTani.rewangtani.model.modelchatdaninbox.modelinbox.DatumInbox;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterInbox extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<DatumInbox> dataItemList;
    String idProfil;
    List<String> idProfilParticipant;
    String nama = "";

    public AdapterInbox(List<DatumInbox> dataItemList, String idProfil,  List<String> idProfilParticipant) {
        this.dataItemList = dataItemList;
        this.idProfil = idProfil;
        this.idProfilParticipant = idProfilParticipant;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.a_belum_z_list_inbox, parent, false);
        Penampung penampung = new Penampung(view);
        return penampung;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<DatumProfil> dataRT = apiInterface.getDatumProfilAkunById(dataItemList.get(position).getLastSender());
        dataRT.enqueue(new Callback<DatumProfil>() {
            @Override
            public void onResponse(Call<DatumProfil> call, Response<DatumProfil> response) {
                DatumProfil dataProfilById = response.body();
                if (response.body()!=null){
                    nama = dataProfilById.getNamaDepan()+" "+dataProfilById.getNamaBelakang();
                    ((Penampung) holder).sender.setText(nama);
                    ((Penampung) holder).lastText.setText(dataItemList.get(position).getLastText());
                    if (dataItemList.get(position).getReadFlag().equalsIgnoreCase("Y")) {
                        ((Penampung) holder).icon.setVisibility(View.GONE);
                    } else if (dataItemList.get(position).getReadFlag().equalsIgnoreCase("X") &&
                            !dataItemList.get(position).getLastSender().equalsIgnoreCase(idProfil)) {
                        ((Penampung) holder).icon.setVisibility(View.VISIBLE);
                    }
                }
            }
            @Override
            public void onFailure(Call<DatumProfil> call, Throwable t) {}
        });
    }

    @Override
    public int getItemCount() {
        return dataItemList == null ? 0 : dataItemList.size();
    }

    static class Penampung extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView sender, lastText;
        public ImageView icon;

        public Penampung(View itemView) {
            super(itemView);
            sender = itemView.findViewById(R.id.sender);
            lastText = itemView.findViewById(R.id.lastText);
            icon = itemView.findViewById(R.id.icon);
        }

        @Override
        public void onClick(View v) {
            Log.d("onclick", "onClick " + getLayoutPosition() + " " + lastText.getText());
        }
    }

    public String getDataProfil(String id) {
        final APIInterfacesRest apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        final Call<DatumProfil> dataRT = apiInterface.getDatumProfilAkunById(id);
        dataRT.enqueue(new Callback<DatumProfil>() {
            @Override
            public void onResponse(Call<DatumProfil> call, Response<DatumProfil> response) {
                DatumProfil dataProfilById = response.body();
                if (response.body()!=null){
                    nama = dataProfilById.getNamaDepan()+" "+dataProfilById.getNamaBelakang();
                }
            }
            @Override
            public void onFailure(Call<DatumProfil> call, Throwable t) {}
        });
        return nama;
    }
}
