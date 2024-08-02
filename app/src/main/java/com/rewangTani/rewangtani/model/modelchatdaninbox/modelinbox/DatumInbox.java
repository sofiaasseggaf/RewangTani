
package com.rewangTani.rewangtani.model.modelchatdaninbox.modelinbox;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DatumInbox implements Serializable {

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
    @SerializedName("idInbox")
    @Expose
    private String idInbox;
    @SerializedName("idInboxParticipant")
    @Expose
    private String idInboxParticipant;
    @SerializedName("lastText")
    @Expose
    private String lastText;
    @SerializedName("lastSender")
    @Expose
    private String lastSender;
    @SerializedName("readFlag")
    @Expose
    private String readFlag;
    private final static long serialVersionUID = -6501940233528638183L;

    /**
     * No args constructor for use in serialization
     */
    public DatumInbox() {
    }

    /**
     * @param idInbox
     * @param lastText
     * @param createdDate
     * @param updatedBy
     * @param lastSender
     * @param createdBy
     * @param updatedDate
     * @param idInboxParticipant
     */
    public DatumInbox(String createdBy, String createdDate, String updatedBy, String updatedDate, String idInbox, String idInboxParticipant, String lastText, String lastSender, String readFlag) {
        super();
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
        this.idInbox = idInbox;
        this.idInboxParticipant = idInboxParticipant;
        this.lastText = lastText;
        this.lastSender = lastSender;
        this.readFlag = readFlag;
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

    public String getIdInbox() {
        return idInbox;
    }

    public void setIdInbox(String idInbox) {
        this.idInbox = idInbox;
    }

    public String getIdInboxParticipant() {
        return idInboxParticipant;
    }

    public void setIdInboxParticipant(String idInboxParticipant) {
        this.idInboxParticipant = idInboxParticipant;
    }

    public String getLastText() {
        return lastText;
    }

    public void setLastText(String lastText) {
        this.lastText = lastText;
    }

    public String getLastSender() {
        return lastSender;
    }

    public void setLastSender(String lastSender) {
        this.lastSender = lastSender;
    }

    public String getReadFlag() {
        return readFlag;
    }

    public void setReadFlag(String readFlag) {
        this.readFlag = readFlag;
    }
}
