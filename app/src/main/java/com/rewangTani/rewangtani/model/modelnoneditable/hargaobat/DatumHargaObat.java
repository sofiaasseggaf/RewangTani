
package com.rewangTani.rewangtani.model.modelnoneditable.hargaobat;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatumHargaObat implements Parcelable
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
    @SerializedName("idHargaObat")
    @Expose
    private String idHargaObat;
    @SerializedName("idObat")
    @Expose
    private String idObat;
    @SerializedName("hargaObat")
    @Expose
    private Integer hargaObat;
    @SerializedName("ketObat")
    @Expose
    private String ketObat;
    @SerializedName("idProfilTanah")
    @Expose
    private String idProfilTanah;
    public final static Creator<DatumHargaObat> CREATOR = new Creator<DatumHargaObat>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DatumHargaObat createFromParcel(android.os.Parcel in) {
            return new DatumHargaObat(in);
        }

        public DatumHargaObat[] newArray(int size) {
            return (new DatumHargaObat[size]);
        }

    }
    ;

    protected DatumHargaObat(android.os.Parcel in) {
        this.createdBy = ((String) in.readValue((String.class.getClassLoader())));
        this.createdDate = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedBy = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedDate = ((String) in.readValue((String.class.getClassLoader())));
        this.idHargaObat = ((String) in.readValue((String.class.getClassLoader())));
        this.idObat = ((String) in.readValue((String.class.getClassLoader())));
        this.hargaObat = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.ketObat = ((String) in.readValue((String.class.getClassLoader())));
        this.idProfilTanah = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public DatumHargaObat() {
    }

    /**
     * 
     * @param idObat
     * @param createdDate
     * @param updatedBy
     * @param createdBy
     * @param idProfilTanah
     * @param idHargaObat
     * @param updatedDate
     * @param ketObat
     * @param hargaObat
     */
    public DatumHargaObat(String createdBy, String createdDate, String updatedBy, String updatedDate, String idHargaObat, String idObat, Integer hargaObat, String ketObat, String idProfilTanah) {
        super();
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
        this.idHargaObat = idHargaObat;
        this.idObat = idObat;
        this.hargaObat = hargaObat;
        this.ketObat = ketObat;
        this.idProfilTanah = idProfilTanah;
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

    public String getIdHargaObat() {
        return idHargaObat;
    }

    public void setIdHargaObat(String idHargaObat) {
        this.idHargaObat = idHargaObat;
    }

    public String getIdObat() {
        return idObat;
    }

    public void setIdObat(String idObat) {
        this.idObat = idObat;
    }

    public Integer getHargaObat() {
        return hargaObat;
    }

    public void setHargaObat(Integer hargaObat) {
        this.hargaObat = hargaObat;
    }

    public String getKetObat() {
        return ketObat;
    }

    public void setKetObat(String ketObat) {
        this.ketObat = ketObat;
    }

    public String getIdProfilTanah() {
        return idProfilTanah;
    }

    public void setIdProfilTanah(String idProfilTanah) {
        this.idProfilTanah = idProfilTanah;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(DatumHargaObat.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        sb.append("idHargaObat");
        sb.append('=');
        sb.append(((this.idHargaObat == null)?"<null>":this.idHargaObat));
        sb.append(',');
        sb.append("idObat");
        sb.append('=');
        sb.append(((this.idObat == null)?"<null>":this.idObat));
        sb.append(',');
        sb.append("hargaObat");
        sb.append('=');
        sb.append(((this.hargaObat == null)?"<null>":this.hargaObat));
        sb.append(',');
        sb.append("ketObat");
        sb.append('=');
        sb.append(((this.ketObat == null)?"<null>":this.ketObat));
        sb.append(',');
        sb.append("idProfilTanah");
        sb.append('=');
        sb.append(((this.idProfilTanah == null)?"<null>":this.idProfilTanah));
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
        dest.writeValue(idHargaObat);
        dest.writeValue(idObat);
        dest.writeValue(hargaObat);
        dest.writeValue(ketObat);
        dest.writeValue(idProfilTanah);
    }

    public int describeContents() {
        return  0;
    }

}
