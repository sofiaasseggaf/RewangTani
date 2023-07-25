
package com.rewangTani.rewangtani.model.modelnoneditable.komoditas;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatumKomoditas implements Parcelable
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
    @SerializedName("idKomoditas")
    @Expose
    private String idKomoditas;
    @SerializedName("namaKomoditas")
    @Expose
    private String namaKomoditas;
    public final static Creator<DatumKomoditas> CREATOR = new Creator<DatumKomoditas>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DatumKomoditas createFromParcel(android.os.Parcel in) {
            return new DatumKomoditas(in);
        }

        public DatumKomoditas[] newArray(int size) {
            return (new DatumKomoditas[size]);
        }

    }
    ;

    protected DatumKomoditas(android.os.Parcel in) {
        this.createdBy = ((String) in.readValue((String.class.getClassLoader())));
        this.createdDate = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedBy = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedDate = ((String) in.readValue((String.class.getClassLoader())));
        this.idKomoditas = ((String) in.readValue((String.class.getClassLoader())));
        this.namaKomoditas = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public DatumKomoditas() {
    }

    /**
     * 
     * @param idKomoditas
     * @param createdDate
     * @param updatedBy
     * @param createdBy
     * @param namaKomoditas
     * @param updatedDate
     */
    public DatumKomoditas(String createdBy, String createdDate, String updatedBy, String updatedDate, String idKomoditas, String namaKomoditas) {
        super();
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
        this.idKomoditas = idKomoditas;
        this.namaKomoditas = namaKomoditas;
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

    public String getIdKomoditas() {
        return idKomoditas;
    }

    public void setIdKomoditas(String idKomoditas) {
        this.idKomoditas = idKomoditas;
    }

    public String getNamaKomoditas() {
        return namaKomoditas;
    }

    public void setNamaKomoditas(String namaKomoditas) {
        this.namaKomoditas = namaKomoditas;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(DatumKomoditas.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        sb.append("idKomoditas");
        sb.append('=');
        sb.append(((this.idKomoditas == null)?"<null>":this.idKomoditas));
        sb.append(',');
        sb.append("namaKomoditas");
        sb.append('=');
        sb.append(((this.namaKomoditas == null)?"<null>":this.namaKomoditas));
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
        dest.writeValue(idKomoditas);
        dest.writeValue(namaKomoditas);
    }

    public int describeContents() {
        return  0;
    }

}
