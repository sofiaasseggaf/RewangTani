
package com.rewangTani.rewangtani.model.modelnoneditable.harga;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatumHarga implements Parcelable
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
    @SerializedName("idharga")
    @Expose
    private String idharga;
    @SerializedName("idSubkategori")
    @Expose
    private String idSubkategori;
    @SerializedName("harga")
    @Expose
    private Integer harga;
    @SerializedName("ket")
    @Expose
    private String ket;
    @SerializedName("idProfilTanah")
    @Expose
    private String idProfilTanah;
    public final static Creator<DatumHarga> CREATOR = new Creator<DatumHarga>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DatumHarga createFromParcel(android.os.Parcel in) {
            return new DatumHarga(in);
        }

        public DatumHarga[] newArray(int size) {
            return (new DatumHarga[size]);
        }

    }
    ;

    protected DatumHarga(android.os.Parcel in) {
        this.createdBy = ((String) in.readValue((String.class.getClassLoader())));
        this.createdDate = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedBy = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedDate = ((String) in.readValue((String.class.getClassLoader())));
        this.idharga = ((String) in.readValue((String.class.getClassLoader())));
        this.idSubkategori = ((String) in.readValue((String.class.getClassLoader())));
        this.harga = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.ket = ((String) in.readValue((String.class.getClassLoader())));
        this.idProfilTanah = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public DatumHarga() {
    }

    /**
     * 
     * @param idharga
     * @param createdDate
     * @param updatedBy
     * @param harga
     * @param createdBy
     * @param idProfilTanah
     * @param updatedDate
     * @param ket
     * @param idSubkategori
     */
    public DatumHarga(String createdBy, String createdDate, String updatedBy, String updatedDate, String idharga, String idSubkategori, Integer harga, String ket, String idProfilTanah) {
        super();
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
        this.idharga = idharga;
        this.idSubkategori = idSubkategori;
        this.harga = harga;
        this.ket = ket;
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

    public String getIdharga() {
        return idharga;
    }

    public void setIdharga(String idharga) {
        this.idharga = idharga;
    }

    public String getIdSubkategori() {
        return idSubkategori;
    }

    public void setIdSubkategori(String idSubkategori) {
        this.idSubkategori = idSubkategori;
    }

    public Integer getHarga() {
        return harga;
    }

    public void setHarga(Integer harga) {
        this.harga = harga;
    }

    public String getKet() {
        return ket;
    }

    public void setKet(String ket) {
        this.ket = ket;
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
        sb.append(DatumHarga.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        sb.append("idharga");
        sb.append('=');
        sb.append(((this.idharga == null)?"<null>":this.idharga));
        sb.append(',');
        sb.append("idSubkategori");
        sb.append('=');
        sb.append(((this.idSubkategori == null)?"<null>":this.idSubkategori));
        sb.append(',');
        sb.append("harga");
        sb.append('=');
        sb.append(((this.harga == null)?"<null>":this.harga));
        sb.append(',');
        sb.append("ket");
        sb.append('=');
        sb.append(((this.ket == null)?"<null>":this.ket));
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
        dest.writeValue(idharga);
        dest.writeValue(idSubkategori);
        dest.writeValue(harga);
        dest.writeValue(ket);
        dest.writeValue(idProfilTanah);
    }

    public int describeContents() {
        return  0;
    }

}
