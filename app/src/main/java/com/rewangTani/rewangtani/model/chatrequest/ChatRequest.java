package com.rewangTani.rewangtani.model.chatrequest;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import java.time.LocalDateTime;

public class ChatRequest
{

    private String idChat;       // Unique chat ID (can be generated on the server)
    private String idInbox;      // The inbox ID that the message belongs to
    private String idSender;     // The sender's ID (user sending the message)
    private String text;         // The message content
    private String sentAt;
    private String isRead;       // Read status of the message (e.g., "0" for unread, "1" for read)


    public ChatRequest(String idInbox, String idSender, String text, String sentAt, String isRead) {
        this.idInbox = idInbox;
        this.idSender = idSender;
        this.text = text;
        this.sentAt = sentAt;
        this.isRead = isRead;
    }

    // Getters and Setters
    public String getIdChat() {
        return idChat;
    }

    public void setIdChat(String idChat) {
        this.idChat = idChat;
    }

    public String getIdInbox() {
        return idInbox;
    }

    public void setIdInbox(String idInbox) {
        this.idInbox = idInbox;
    }

    public String getIdSender() {
        return idSender;
    }

    public void setIdSender(String idSender) {
        this.idSender = idSender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSentAt() {
        return sentAt;
    }

    public void setSentAt(String sentAt) {
        this.sentAt = sentAt;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

}
