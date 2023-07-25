
package com.rewangTani.rewangtani.model.modelupperbar.panen;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatumPanen implements Parcelable
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
    @SerializedName("idPanen")
    @Expose
    private String idPanen;
    @SerializedName("idRencanaTanam")
    @Expose
    private String idRencanaTanam;
    @SerializedName("tujuanJual")
    @Expose
    private String tujuanJual;
    @SerializedName("jenisHasilPanen")
    @Expose
    private String jenisHasilPanen;
    @SerializedName("hasilPanen")
    @Expose
    private Double hasilPanen;
    @SerializedName("hargaAktual")
    @Expose
    private Integer hargaAktual;
    public final static Creator<DatumPanen> CREATOR = new Creator<DatumPanen>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DatumPanen createFromParcel(android.os.Parcel in) {
            return new DatumPanen(in);
        }

        public DatumPanen[] newArray(int size) {
            return (new DatumPanen[size]);
        }

    }
    ;

    protected DatumPanen(android.os.Parcel in) {
        this.createdBy = ((String) in.readValue((String.class.getClassLoader())));
        this.createdDate = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedBy = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedDate = ((String) in.readValue((String.class.getClassLoader())));
        this.idPanen = ((String) in.readValue((String.class.getClassLoader())));
        this.idRencanaTanam = ((String) in.readValue((String.class.getClassLoader())));
        this.tujuanJual = ((String) in.readValue((String.class.getClassLoader())));
        this.jenisHasilPanen = ((String) in.readValue((String.class.getClassLoader())));
        this.hasilPanen = ((Double) in.readValue((Double.class.getClassLoader())));
        this.hargaAktual = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public DatumPanen() {
    }

    /**
     * 
     * @param hasilPanen
     * @param createdDate
     * @param updatedBy
     * @param idPanen
     * @param tujuanJual
     * @param createdBy
     * @param hargaAktual
     * @param jenisHasilPanen
     * @param updatedDate
     * @param idRencanaTanam
     */
    public DatumPanen(String createdBy, String createdDate, String updatedBy, String updatedDate, String idPanen, String idRencanaTanam, String tujuanJual, String jenisHasilPanen, Double hasilPanen, Integer hargaAktual) {
        super();
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
        this.idPanen = idPanen;
        this.idRencanaTanam = idRencanaTanam;
        this.tujuanJual = tujuanJual;
        this.jenisHasilPanen = jenisHasilPanen;
        this.hasilPanen = hasilPanen;
        this.hargaAktual = hargaAktual;
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

    public String getIdPanen() {
        return idPanen;
    }

    public void setIdPanen(String idPanen) {
        this.idPanen = idPanen;
    }

    public String getIdRencanaTanam() {
        return idRencanaTanam;
    }

    public void setIdRencanaTanam(String idRencanaTanam) {
        this.idRencanaTanam = idRencanaTanam;
    }

    public String getTujuanJual() {
        return tujuanJual;
    }

    public void setTujuanJual(String tujuanJual) {
        this.tujuanJual = tujuanJual;
    }

    public String getJenisHasilPanen() {
        return jenisHasilPanen;
    }

    public void setJenisHasilPanen(String jenisHasilPanen) {
        this.jenisHasilPanen = jenisHasilPanen;
    }

    public Double getHasilPanen() {
        return hasilPanen;
    }

    public void setHasilPanen(Double hasilPanen) {
        this.hasilPanen = hasilPanen;
    }

    public Integer getHargaAktual() {
        return hargaAktual;
    }

    public void setHargaAktual(Integer hargaAktual) {
        this.hargaAktual = hargaAktual;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(DatumPanen.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        sb.append("idPanen");
        sb.append('=');
        sb.append(((this.idPanen == null)?"<null>":this.idPanen));
        sb.append(',');
        sb.append("idRencanaTanam");
        sb.append('=');
        sb.append(((this.idRencanaTanam == null)?"<null>":this.idRencanaTanam));
        sb.append(',');
        sb.append("tujuanJual");
        sb.append('=');
        sb.append(((this.tujuanJual == null)?"<null>":this.tujuanJual));
        sb.append(',');
        sb.append("jenisHasilPanen");
        sb.append('=');
        sb.append(((this.jenisHasilPanen == null)?"<null>":this.jenisHasilPanen));
        sb.append(',');
        sb.append("hasilPanen");
        sb.append('=');
        sb.append(((this.hasilPanen == null)?"<null>":this.hasilPanen));
        sb.append(',');
        sb.append("hargaAktual");
        sb.append('=');
        sb.append(((this.hargaAktual == null)?"<null>":this.hargaAktual));
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
        dest.writeValue(idPanen);
        dest.writeValue(idRencanaTanam);
        dest.writeValue(tujuanJual);
        dest.writeValue(jenisHasilPanen);
        dest.writeValue(hasilPanen);
        dest.writeValue(hargaAktual);
    }

    public int describeContents() {
        return  0;
    }

}
