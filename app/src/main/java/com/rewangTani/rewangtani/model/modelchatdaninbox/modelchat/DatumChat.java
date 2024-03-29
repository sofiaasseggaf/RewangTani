
package com.rewangTani.rewangtani.model.modelchatdaninbox.modelchat;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatumChat implements Serializable
{

    @SerializedName("createdBy")
    @Expose
    private String createdBy;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("updatedBy")
    @Expose
    private String updatedBy;
    @SerializedName("updatedDate")
    @Expose
    private String updatedDate;
    @SerializedName("idChat")
    @Expose
    private String idChat;
    @SerializedName("idInbox")
    @Expose
    private String idInbox;
    @SerializedName("idSender")
    @Expose
    private String idSender;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("sentAt")
    @Expose
    private String sentAt;
    @SerializedName("isRead")
    @Expose
    private String isRead;
    private final static long serialVersionUID = 5182612160784361894L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DatumChat() {
    }

    /**
     * 
     * @param idInbox
     * @param createdDate
     * @param updatedBy
     * @param createdBy
     * @param isRead
     * @param updatedDate
     * @param text
     * @param sentAt
     * @param idChat
     * @param idSender
     */
    public DatumChat(String createdBy, String createdDate, String updatedBy, String updatedDate, String idChat, String idInbox, String idSender, String text, String sentAt, String isRead) {
        super();
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
        this.idChat = idChat;
        this.idInbox = idInbox;
        this.idSender = idSender;
        this.text = text;
        this.sentAt = sentAt;
        this.isRead = isRead;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

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
