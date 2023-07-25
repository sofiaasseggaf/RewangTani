
package com.rewangTani.rewangtani.model.modelupperbar.outputrencanatanam;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatumOutputRencanaTanam implements Parcelable
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
    @SerializedName("idOutputRencanaTanam")
    @Expose
    private String idOutputRencanaTanam;
    @SerializedName("idRencanaTanam")
    @Expose
    private String idRencanaTanam;
    @SerializedName("estBiayaProduksi")
    @Expose
    private String estBiayaProduksi;
    @SerializedName("estHasil")
    @Expose
    private Double estHasil;
    @SerializedName("estPendapatan")
    @Expose
    private Integer estPendapatan;
    public final static Creator<DatumOutputRencanaTanam> CREATOR = new Creator<DatumOutputRencanaTanam>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DatumOutputRencanaTanam createFromParcel(android.os.Parcel in) {
            return new DatumOutputRencanaTanam(in);
        }

        public DatumOutputRencanaTanam[] newArray(int size) {
            return (new DatumOutputRencanaTanam[size]);
        }

    }
    ;

    protected DatumOutputRencanaTanam(android.os.Parcel in) {
        this.createdBy = ((String) in.readValue((String.class.getClassLoader())));
        this.createdDate = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedBy = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedDate = ((String) in.readValue((String.class.getClassLoader())));
        this.idOutputRencanaTanam = ((String) in.readValue((String.class.getClassLoader())));
        this.idRencanaTanam = ((String) in.readValue((String.class.getClassLoader())));
        this.estBiayaProduksi = ((String) in.readValue((String.class.getClassLoader())));
        this.estHasil = ((Double) in.readValue((Double.class.getClassLoader())));
        this.estPendapatan = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public DatumOutputRencanaTanam() {
    }

    /**
     * 
     * @param estPendapatan
     * @param createdDate
     * @param updatedBy
     * @param createdBy
     * @param updatedDate
     * @param idRencanaTanam
     * @param estBiayaProduksi
     * @param idOutputRencanaTanam
     * @param estHasil
     */
    public DatumOutputRencanaTanam(String createdBy, String createdDate, String updatedBy, String updatedDate, String idOutputRencanaTanam, String idRencanaTanam, String estBiayaProduksi, Double estHasil, Integer estPendapatan) {
        super();
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
        this.idOutputRencanaTanam = idOutputRencanaTanam;
        this.idRencanaTanam = idRencanaTanam;
        this.estBiayaProduksi = estBiayaProduksi;
        this.estHasil = estHasil;
        this.estPendapatan = estPendapatan;
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

    public String getIdOutputRencanaTanam() {
        return idOutputRencanaTanam;
    }

    public void setIdOutputRencanaTanam(String idOutputRencanaTanam) {
        this.idOutputRencanaTanam = idOutputRencanaTanam;
    }

    public String getIdRencanaTanam() {
        return idRencanaTanam;
    }

    public void setIdRencanaTanam(String idRencanaTanam) {
        this.idRencanaTanam = idRencanaTanam;
    }

    public String getEstBiayaProduksi() {
        return estBiayaProduksi;
    }

    public void setEstBiayaProduksi(String estBiayaProduksi) {
        this.estBiayaProduksi = estBiayaProduksi;
    }

    public Double getEstHasil() {
        return estHasil;
    }

    public void setEstHasil(Double estHasil) {
        this.estHasil = estHasil;
    }

    public Integer getEstPendapatan() {
        return estPendapatan;
    }

    public void setEstPendapatan(Integer estPendapatan) {
        this.estPendapatan = estPendapatan;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(DatumOutputRencanaTanam.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        sb.append("idOutputRencanaTanam");
        sb.append('=');
        sb.append(((this.idOutputRencanaTanam == null)?"<null>":this.idOutputRencanaTanam));
        sb.append(',');
        sb.append("idRencanaTanam");
        sb.append('=');
        sb.append(((this.idRencanaTanam == null)?"<null>":this.idRencanaTanam));
        sb.append(',');
        sb.append("estBiayaProduksi");
        sb.append('=');
        sb.append(((this.estBiayaProduksi == null)?"<null>":this.estBiayaProduksi));
        sb.append(',');
        sb.append("estHasil");
        sb.append('=');
        sb.append(((this.estHasil == null)?"<null>":this.estHasil));
        sb.append(',');
        sb.append("estPendapatan");
        sb.append('=');
        sb.append(((this.estPendapatan == null)?"<null>":this.estPendapatan));
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
        dest.writeValue(idOutputRencanaTanam);
        dest.writeValue(idRencanaTanam);
        dest.writeValue(estBiayaProduksi);
        dest.writeValue(estHasil);
        dest.writeValue(estPendapatan);
    }

    public int describeContents() {
        return  0;
    }

}
