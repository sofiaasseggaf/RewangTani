package com.rewangTani.rewangtani.adapter.adapterbottombar;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.model.modelakunprofil.DatumProfil;
import com.rewangTani.rewangtani.model.modelchatdaninbox.modelinbox.DatumInbox;
import com.rewangTani.rewangtani.model.modelchatdaninbox.modelinboxparticipant.DatumInboxParticipant;
import com.rewangTani.rewangtani.utility.Global;
import com.rewangTani.rewangtani.utility.PreferenceUtils;
import java.util.List;

public class AdapterInbox extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    List<DatumInbox> dataItemList;
    List<DatumInboxParticipant> listDataInboxParticipant;
    List<DatumProfil> listDataProfil;
    private OnInboxItemClickListener listener;
    Context context;

    public AdapterInbox( List<DatumInbox> dataItemList, List<DatumProfil> listDataProfil, List<DatumInboxParticipant> listDataInboxParticipant, Context context, OnInboxItemClickListener listener )
    {
        this.dataItemList = dataItemList;
        this.listDataProfil = listDataProfil;
        this.listDataInboxParticipant = listDataInboxParticipant;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType )
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.z_list_inbox, parent, false);
        Penampung penampung = new Penampung(view, listener);
        return penampung;
    }

    @Override
    public void onBindViewHolder( @NonNull RecyclerView.ViewHolder holder, int position )
    {
        DatumInbox inbox = dataItemList.get(position);
        String thisProfile = PreferenceUtils.getNamaDepan(context) + " " + PreferenceUtils.getNamaBelakang(context);
        String idProfile = PreferenceUtils.getIdProfil(context);
        String idLastSender = Global.STRING_DEFAULT_VALUE;
        String otherProfile = Global.STRING_DEFAULT_VALUE;

        ((Penampung) holder).lastText.setText(inbox.getLastText());

        if ( dataItemList.get(position).getLastSender().equalsIgnoreCase(idProfile) )
        {
            ((Penampung) holder).sender.setText(thisProfile);
        }
        else
        {
            idLastSender = getLastSenderId(inbox.getLastSender());
            if (!idLastSender.equalsIgnoreCase(Global.STRING_DEFAULT_VALUE)) {
                otherProfile = getProfileNameById(idLastSender);
                ((Penampung) holder).sender.setText(otherProfile);
            }
        }

        setReadFlagVisibility(inbox, idProfile, (Penampung) holder);
    }

    @Override
    public int getItemCount()
    {
        return dataItemList == null ? 0 : dataItemList.size();
    }

    class Penampung extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView sender, lastText;
        public ImageView icon;
        private OnInboxItemClickListener listener; // Add a listener reference

        public Penampung( View itemView, OnInboxItemClickListener listener )
        {
            super(itemView);
            this.listener = listener;  // Initialize the listener
            sender = itemView.findViewById(R.id.sender);
            lastText = itemView.findViewById(R.id.lastText);
            icon = itemView.findViewById(R.id.icon);
            itemView.setOnClickListener(this); // Set the onClick listener for the itemView
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onInboxItemClick(dataItemList.get(getAdapterPosition())); // Pass the clicked item data
            }
        }
    }

    private String getLastSenderId(String lastSender) {
        for (DatumInboxParticipant participant : listDataInboxParticipant) {
            if (lastSender.equalsIgnoreCase(participant.getIdProfilA())) {
                return participant.getIdProfilA();
            } else if (lastSender.equalsIgnoreCase(participant.getIdProfilB())) {
                return participant.getIdProfilB();
            }
        }
        return Global.STRING_DEFAULT_VALUE;
    }

    private String getProfileNameById(String profileId) {
        for (DatumProfil profil : listDataProfil) {
            if (profil.getIdProfile().equalsIgnoreCase(profileId)) {
                return profil.getNamaDepan() + " " + profil.getNamaBelakang();
            }
        }
        return Global.STRING_DEFAULT_VALUE;
    }

    private void setReadFlagVisibility(DatumInbox inbox, String idProfile, Penampung holder) {
        if (inbox.getReadFlag().equalsIgnoreCase("Y") || inbox.getLastSender().equalsIgnoreCase(idProfile)) {
            holder.icon.setVisibility(View.GONE);
        } else if (inbox.getReadFlag().equalsIgnoreCase("N") && !inbox.getLastSender().equalsIgnoreCase(idProfile)) {
            holder.icon.setVisibility(View.VISIBLE);
        }
    }

    public interface OnInboxItemClickListener {
        void onInboxItemClick(DatumInbox datumInbox);
    }
}
