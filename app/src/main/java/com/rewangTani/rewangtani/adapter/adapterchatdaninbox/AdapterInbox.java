package com.rewangTani.rewangtani.adapter.adapterchatdaninbox;

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
    Context context;

    public AdapterInbox( List<DatumInbox> dataItemList, List<DatumProfil> listDataProfil, List<DatumInboxParticipant> listDataInboxParticipant, Context context )
    {
        this.dataItemList = dataItemList;
        this.listDataProfil = listDataProfil;
        this.listDataInboxParticipant = listDataInboxParticipant;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType )
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.z_list_inbox, parent, false);
        Penampung penampung = new Penampung(view);
        return penampung;
    }

    @Override
    public void onBindViewHolder( @NonNull RecyclerView.ViewHolder holder, int position )
    {
        String thisProfile = PreferenceUtils.getNamaDepan(context) + " " + PreferenceUtils.getNamaBelakang(context);
        String idProfile = PreferenceUtils.getIdProfil(context);
        String idLastSender = Global.STRING_DEFAULT_VALUE;
        String otherProfile = Global.STRING_DEFAULT_VALUE;

        ((Penampung) holder).lastText.setText(dataItemList.get(position).getLastText());

        if ( dataItemList.get(position).getLastSender().equalsIgnoreCase(idProfile) )
        {
            ((Penampung) holder).sender.setText(thisProfile);
        }
        else
        {
            if ( !dataItemList.get(position).getLastSender().equalsIgnoreCase(idProfile) )
            {
                for ( int i = 0; i < listDataInboxParticipant.size(); i++ )
                {
                    if ( dataItemList.get(position).getLastSender().equalsIgnoreCase(listDataInboxParticipant.get(i).getIdProfilA()) )
                    {
                        idLastSender = listDataInboxParticipant.get(i).getIdProfilA();
                        break;
                    }
                    else if ( dataItemList.get(position).getLastSender().equalsIgnoreCase(listDataInboxParticipant.get(i).getIdProfilB()) )
                    {
                        idLastSender = listDataInboxParticipant.get(i).getIdProfilB();
                        break;
                    }
                }

                if ( !idLastSender.equalsIgnoreCase(Global.STRING_DEFAULT_VALUE) )
                {
                    for ( int i = 0; i < listDataProfil.size(); i++ )
                    {
                        if ( listDataProfil.get(i).getIdProfile().equalsIgnoreCase(idLastSender) )
                        {
                            otherProfile = listDataProfil.get(i).getNamaDepan() + " " + listDataProfil.get(i).getNamaBelakang();
                            ((Penampung) holder).sender.setText(otherProfile);
                            break;
                        }
                    }
                }
            }
        }

        if ( dataItemList.get(position).getReadFlag().equalsIgnoreCase("Y") ||
                dataItemList.get(position).getLastSender().equalsIgnoreCase(idProfile) )
        {
            ((Penampung) holder).icon.setVisibility(View.GONE);
        }
        else if ( dataItemList.get(position).getReadFlag().equalsIgnoreCase("N") &&
                !dataItemList.get(position).getLastSender().equalsIgnoreCase(idProfile) )
        {
            ((Penampung) holder).icon.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount()
    {
        return dataItemList == null ? 0 : dataItemList.size();
    }

    static class Penampung extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView sender, lastText;
        public ImageView icon;

        public Penampung( View itemView )
        {
            super(itemView);
            sender = itemView.findViewById(R.id.sender);
            lastText = itemView.findViewById(R.id.lastText);
            icon = itemView.findViewById(R.id.icon);
        }

        @Override
        public void onClick( View v )
        {
            Log.d("onclick", "onClick " + getLayoutPosition() + " " + lastText.getText());
        }
    }

}
