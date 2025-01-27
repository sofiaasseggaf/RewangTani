package com.rewangTani.rewangtani.adapter.adapterbottombar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.model.chatrequest.ChatRequest;
import com.rewangTani.rewangtani.utility.PreferenceUtils;

import java.util.List;

public class AdapterChat extends RecyclerView.Adapter<AdapterChat.ChatViewHolder> {

    private List<ChatRequest> chatMessages;
    private Context context;

    public AdapterChat(List<ChatRequest> chatMessages, Context context) {
        this.chatMessages = chatMessages;
        this.context = context;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.z_list_chat, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        ChatRequest chatMessage = chatMessages.get(position);
//        holder.messageTextView.setText(chatMessage.getText());

        holder.time.setText(chatMessage.getSentAt());
        holder.lastText.setText(chatMessage.getText());

        String idProfile = PreferenceUtils.getIdProfil(context);

        if (idProfile.equals(chatMessage.getIdSender()))
        {
            holder.root.setBackground(context.getResources().getDrawable(R.drawable.bg_chat_green));
            holder.time.setTextColor(context.getResources().getColor(R.color.fontChatGreen));
            holder.lastText.setTextColor(context.getResources().getColor(R.color.fontChatGreen));
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            params.setMargins(125,0, 20, 30);
            holder.root.setLayoutParams(params);
        }
        else
        {
            holder.root.setBackground(context.getResources().getDrawable(R.drawable.bg_chat_gray));
            holder.time.setTextColor(context.getResources().getColor(R.color.fontChatGray));
            holder.lastText.setTextColor(context.getResources().getColor(R.color.fontChatGray));
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            params.setMargins(20,0, 125, 30);
            holder.root.setLayoutParams(params);
        }

    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {

        public TextView time, lastText;
        public RelativeLayout root;

        public ChatViewHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.root);
            time = itemView.findViewById(R.id.time);
            lastText = itemView.findViewById(R.id.lastText);
        }
    }
}
