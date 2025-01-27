
package com.rewangTani.rewangtani.model.modelchatdaninbox.modelinboxparticipant;

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
    @SerializedName("idInboxParticipant")
    @Expose
    private String idInboxParticipant;
    @SerializedName("idProfilA")
    @Expose
    private String idProfilA;
    @SerializedName("idProfilB")
    @Expose
    private String idProfilB;
    public final static Creator<Data> CREATOR = new Creator<Data>() {


        public Data createFromParcel(android.os.Parcel in) {
            return new Data(in);
        }

        public Data[] newArray(int size) {
            return (new Data[size]);
        }

    }
    ;
    private final static long serialVersionUID = -7598415417912235959L;

    @SuppressWarnings({
        "unchecked"
    })
    protected Data(android.os.Parcel in) {
        this.createdBy = ((String) in.readValue((String.class.getClassLoader())));
        this.createdDate = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedBy = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedDate = ((String) in.readValue((String.class.getClassLoader())));
        this.idInboxParticipant = ((String) in.readValue((String.class.getClassLoader())));
        this.idProfilA = ((String) in.readValue((String.class.getClassLoader())));
        this.idProfilB = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    public Data(String createdBy, String createdDate, String updatedBy, String updatedDate, String idInboxParticipant, String idProfilA, String idProfilB) {
        super();
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
        this.idInboxParticipant = idInboxParticipant;
        this.idProfilA = idProfilA;
        this.idProfilB = idProfilB;
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

    public String getIdInboxParticipant() {
        return idInboxParticipant;
    }

    public void setIdInboxParticipant(String idInboxParticipant) {
        this.idInboxParticipant = idInboxParticipant;
    }

    public String getIdProfilA() {
        return idProfilA;
    }

    public void setIdProfilA(String idProfilA) {
        this.idProfilA = idProfilA;
    }

    public String getIdProfilB() {
        return idProfilB;
    }

    public void setIdProfilB(String idProfilB) {
        this.idProfilB = idProfilB;
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
        sb.append("idInboxParticipant");
        sb.append('=');
        sb.append(((this.idInboxParticipant == null)?"<null>":this.idInboxParticipant));
        sb.append(',');
        sb.append("idProfilA");
        sb.append('=');
        sb.append(((this.idProfilA == null)?"<null>":this.idProfilA));
        sb.append(',');
        sb.append("idProfilB");
        sb.append('=');
        sb.append(((this.idProfilB == null)?"<null>":this.idProfilB));
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
        dest.writeValue(idInboxParticipant);
        dest.writeValue(idProfilA);
        dest.writeValue(idProfilB);
    }

    public int describeContents() {
        return  0;
    }

}
