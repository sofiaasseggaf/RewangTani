package com.rewangTani.rewangtani.adapter.adapterchatdaninbox;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.model.modelchatdaninbox.modelchat.DatumChat;
import com.rewangTani.rewangtani.model.modelchatdaninbox.modelinbox.DatumInbox;

import java.util.List;

public class AdapterChat extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<DatumChat> dataItemList;
    String idSender;

    public AdapterChat(List<DatumChat> dataItemList,   String idSender) {
        this.dataItemList = dataItemList;
        this.idSender = idSender;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.z_list_chat, parent, false);
        Penampung penampung = new Penampung(view);
        return penampung;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((Penampung)holder).time.setText(dataItemList.get(position).getCreatedDate());
        ((Penampung)holder).lastText.setText(dataItemList.get(position).getText());
        if (dataItemList.get(position).getIdSender().equalsIgnoreCase(idSender)){
            ((Penampung)holder).root.setBackgroundResource(R.color.chatGrey);
            ((Penampung)holder).time.setTextColor(Color.parseColor("#919191"));
            ((Penampung)holder).lastText.setTextColor(Color.parseColor("#919191"));
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            params.setMargins(125,0, 0, 20);
            ((Penampung)holder).root.setLayoutParams(params);
        } else {
            ((Penampung)holder).root.setBackgroundResource(R.color.chatGreen);
            ((Penampung)holder).time.setTextColor(Color.parseColor("#ffffff"));
            ((Penampung)holder).lastText.setTextColor(Color.parseColor("#ffffff"));
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            params.setMargins(0,0, 125, 20);
            ((Penampung)holder).root.setLayoutParams(params);
        }
    }

    @Override
    public int getItemCount() {
        return dataItemList == null ? 0 : dataItemList.size();
    }

    static class Penampung extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView time, lastText;
        public RelativeLayout root;
        public Penampung(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.root);
            time = itemView.findViewById(R.id.time);
            lastText = itemView.findViewById(R.id.lastText);
        }
        @Override
        public void onClick(View v) {
            Log.d("onclick", "onClick " + getLayoutPosition() + " " + lastText.getText());
        }
    }

}
