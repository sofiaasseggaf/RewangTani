
package com.rewangTani.rewangtani.model.modelnoneditable.sistemirigasi;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatumSistemIrigasi implements Parcelable
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
    @SerializedName("idSistemIrigasi")
    @Expose
    private String idSistemIrigasi;
    @SerializedName("namaSistemIrigasi")
    @Expose
    private String namaSistemIrigasi;
    @SerializedName("ketSistemIrigasi")
    @Expose
    private String ketSistemIrigasi;
    public final static Creator<DatumSistemIrigasi> CREATOR = new Creator<DatumSistemIrigasi>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DatumSistemIrigasi createFromParcel(android.os.Parcel in) {
            return new DatumSistemIrigasi(in);
        }

        public DatumSistemIrigasi[] newArray(int size) {
            return (new DatumSistemIrigasi[size]);
        }

    }
    ;

    protected DatumSistemIrigasi(android.os.Parcel in) {
        this.createdBy = ((String) in.readValue((String.class.getClassLoader())));
        this.createdDate = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedBy = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedDate = ((String) in.readValue((String.class.getClassLoader())));
        this.idSistemIrigasi = ((String) in.readValue((String.class.getClassLoader())));
        this.namaSistemIrigasi = ((String) in.readValue((String.class.getClassLoader())));
        this.ketSistemIrigasi = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public DatumSistemIrigasi() {
    }

    /**
     * 
     * @param namaSistemIrigasi
     * @param idSistemIrigasi
     * @param createdDate
     * @param updatedBy
     * @param createdBy
     * @param updatedDate
     * @param ketSistemIrigasi
     */
    public DatumSistemIrigasi(String createdBy, String createdDate, String updatedBy, String updatedDate, String idSistemIrigasi, String namaSistemIrigasi, String ketSistemIrigasi) {
        super();
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
        this.idSistemIrigasi = idSistemIrigasi;
        this.namaSistemIrigasi = namaSistemIrigasi;
        this.ketSistemIrigasi = ketSistemIrigasi;
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

    public String getIdSistemIrigasi() {
        return idSistemIrigasi;
    }

    public void setIdSistemIrigasi(String idSistemIrigasi) {
        this.idSistemIrigasi = idSistemIrigasi;
    }

    public String getNamaSistemIrigasi() {
        return namaSistemIrigasi;
    }

    public void setNamaSistemIrigasi(String namaSistemIrigasi) {
        this.namaSistemIrigasi = namaSistemIrigasi;
    }

    public String getKetSistemIrigasi() {
        return ketSistemIrigasi;
    }

    public void setKetSistemIrigasi(String ketSistemIrigasi) {
        this.ketSistemIrigasi = ketSistemIrigasi;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(DatumSistemIrigasi.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        sb.append("idSistemIrigasi");
        sb.append('=');
        sb.append(((this.idSistemIrigasi == null)?"<null>":this.idSistemIrigasi));
        sb.append(',');
        sb.append("namaSistemIrigasi");
        sb.append('=');
        sb.append(((this.namaSistemIrigasi == null)?"<null>":this.namaSistemIrigasi));
        sb.append(',');
        sb.append("ketSistemIrigasi");
        sb.append('=');
        sb.append(((this.ketSistemIrigasi == null)?"<null>":this.ketSistemIrigasi));
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
        dest.writeValue(idSistemIrigasi);
        dest.writeValue(namaSistemIrigasi);
        dest.writeValue(ketSistemIrigasi);
    }

    public int describeContents() {
        return  0;
    }

}
