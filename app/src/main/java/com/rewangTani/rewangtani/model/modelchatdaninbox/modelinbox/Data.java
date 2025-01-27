
package com.rewangTani.rewangtani.model.modelchatdaninbox.modelinbox;

import java.io.Serializable;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Serializable, Parcelable
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
    public final static Creator<Data> CREATOR = new Creator<Data>() {


        public Data createFromParcel(android.os.Parcel in) {
            return new Data(in);
        }

        public Data[] newArray(int size) {
            return (new Data[size]);
        }

    }
    ;
    private final static long serialVersionUID = 2492618054535144487L;

    @SuppressWarnings({
        "unchecked"
    })
    protected Data(android.os.Parcel in) {
        this.createdBy = ((String) in.readValue((String.class.getClassLoader())));
        this.createdDate = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedBy = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedDate = ((String) in.readValue((String.class.getClassLoader())));
        this.idInbox = ((String) in.readValue((String.class.getClassLoader())));
        this.idInboxParticipant = ((String) in.readValue((String.class.getClassLoader())));
        this.lastText = ((String) in.readValue((String.class.getClassLoader())));
        this.lastSender = ((String) in.readValue((String.class.getClassLoader())));
        this.readFlag = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    public Data(String createdBy, String createdDate, String updatedBy, String updatedDate, String idInbox, String idInboxParticipant, String lastText, String lastSender, String readFlag) {
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Data.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("createdBy");
        sb.append('=');
        sb.append(((this.createdBy == null)?"<null>":this.createdBy));
        sb.append(',');
        sb.append("createdDate");
        sb.append('=');
        sb.append(((this.createdDate == null)?"<null>":this.createdDate));
        sb.append(',');
        sb.append("updatedBy");
        sb.append('=');
        sb.append(((this.updatedBy == null)?"<null>":this.updatedBy));
        sb.append(',');
        sb.append("updatedDate");
        sb.append('=');
        sb.append(((this.updatedDate == null)?"<null>":this.updatedDate));
        sb.append(',');
        sb.append("idInbox");
        sb.append('=');
        sb.append(((this.idInbox == null)?"<null>":this.idInbox));
        sb.append(',');
        sb.append("idInboxParticipant");
        sb.append('=');
        sb.append(((this.idInboxParticipant == null)?"<null>":this.idInboxParticipant));
        sb.append(',');
        sb.append("lastText");
        sb.append('=');
        sb.append(((this.lastText == null)?"<null>":this.lastText));
        sb.append(',');
        sb.append("lastSender");
        sb.append('=');
        sb.append(((this.lastSender == null)?"<null>":this.lastSender));
        sb.append(',');
        sb.append("readFlag");
        sb.append('=');
        sb.append(((this.readFlag == null)?"<null>":this.readFlag));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(createdBy);
        dest.writeValue(createdDate);
        dest.writeValue(updatedBy);
        dest.writeValue(updatedDate);
        dest.writeValue(idInbox);
        dest.writeValue(idInboxParticipant);
        dest.writeValue(lastText);
        dest.writeValue(lastSender);
        dest.writeValue(readFlag);
    }

    public int describeContents() {
        return  0;
    }

}
