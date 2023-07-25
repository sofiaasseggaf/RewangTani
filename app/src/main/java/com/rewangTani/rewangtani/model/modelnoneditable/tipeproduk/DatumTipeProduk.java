
package com.rewangTani.rewangtani.model.modelnoneditable.tipeproduk;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatumTipeProduk implements Parcelable
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
    @SerializedName("idTipeProduk")
    @Expose
    private String idTipeProduk;
    @SerializedName("namaTipeProduk")
    @Expose
    private String namaTipeProduk;
    @SerializedName("ketTipeProduk")
    @Expose
    private String ketTipeProduk;
    public final static Creator<DatumTipeProduk> CREATOR = new Creator<DatumTipeProduk>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DatumTipeProduk createFromParcel(android.os.Parcel in) {
            return new DatumTipeProduk(in);
        }

        public DatumTipeProduk[] newArray(int size) {
            return (new DatumTipeProduk[size]);
        }

    }
    ;

    protected DatumTipeProduk(android.os.Parcel in) {
        this.createdBy = ((String) in.readValue((String.class.getClassLoader())));
        this.createdDate = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedBy = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedDate = ((String) in.readValue((String.class.getClassLoader())));
        this.idTipeProduk = ((String) in.readValue((String.class.getClassLoader())));
        this.namaTipeProduk = ((String) in.readValue((String.class.getClassLoader())));
        this.ketTipeProduk = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public DatumTipeProduk() {
    }

    /**
     * 
     * @param namaTipeProduk
     * @param createdDate
     * @param updatedBy
     * @param createdBy
     * @param idTipeProduk
     * @param updatedDate
     * @param ketTipeProduk
     */
    public DatumTipeProduk(String createdBy, String createdDate, String updatedBy, String updatedDate, String idTipeProduk, String namaTipeProduk, String ketTipeProduk) {
        super();
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
        this.idTipeProduk = idTipeProduk;
        this.namaTipeProduk = namaTipeProduk;
        this.ketTipeProduk = ketTipeProduk;
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

    public String getIdTipeProduk() {
        return idTipeProduk;
    }

    public void setIdTipeProduk(String idTipeProduk) {
        this.idTipeProduk = idTipeProduk;
    }

    public String getNamaTipeProduk() {
        return namaTipeProduk;
    }

    public void setNamaTipeProduk(String namaTipeProduk) {
        this.namaTipeProduk = namaTipeProduk;
    }

    public String getKetTipeProduk() {
        return ketTipeProduk;
    }

    public void setKetTipeProduk(String ketTipeProduk) {
        this.ketTipeProduk = ketTipeProduk;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(DatumTipeProduk.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        sb.append("idTipeProduk");
        sb.append('=');
        sb.append(((this.idTipeProduk == null)?"<null>":this.idTipeProduk));
        sb.append(',');
        sb.append("namaTipeProduk");
        sb.append('=');
        sb.append(((this.namaTipeProduk == null)?"<null>":this.namaTipeProduk));
        sb.append(',');
        sb.append("ketTipeProduk");
        sb.append('=');
        sb.append(((this.ketTipeProduk == null)?"<null>":this.ketTipeProduk));
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
        dest.writeValue(idTipeProduk);
        dest.writeValue(namaTipeProduk);
        dest.writeValue(ketTipeProduk);
    }

    public int describeContents() {
        return  0;
    }

}
