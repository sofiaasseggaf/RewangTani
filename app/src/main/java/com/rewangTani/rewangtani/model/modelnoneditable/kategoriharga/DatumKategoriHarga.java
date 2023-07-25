
package com.rewangTani.rewangtani.model.modelnoneditable.kategoriharga;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatumKategoriHarga implements Parcelable
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
    @SerializedName("idKategori")
    @Expose
    private String idKategori;
    @SerializedName("namaKategori")
    @Expose
    private String namaKategori;
    @SerializedName("idKomoditas")
    @Expose
    private String idKomoditas;
    public final static Creator<DatumKategoriHarga> CREATOR = new Creator<DatumKategoriHarga>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DatumKategoriHarga createFromParcel(android.os.Parcel in) {
            return new DatumKategoriHarga(in);
        }

        public DatumKategoriHarga[] newArray(int size) {
            return (new DatumKategoriHarga[size]);
        }

    }
    ;

    protected DatumKategoriHarga(android.os.Parcel in) {
        this.createdBy = ((String) in.readValue((String.class.getClassLoader())));
        this.createdDate = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedBy = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedDate = ((String) in.readValue((String.class.getClassLoader())));
        this.idKategori = ((String) in.readValue((String.class.getClassLoader())));
        this.namaKategori = ((String) in.readValue((String.class.getClassLoader())));
        this.idKomoditas = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public DatumKategoriHarga() {
    }

    /**
     * 
     * @param idKategori
     * @param idKomoditas
     * @param createdDate
     * @param updatedBy
     * @param createdBy
     * @param updatedDate
     * @param namaKategori
     */
    public DatumKategoriHarga(String createdBy, String createdDate, String updatedBy, String updatedDate, String idKategori, String namaKategori, String idKomoditas) {
        super();
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
        this.idKategori = idKategori;
        this.namaKategori = namaKategori;
        this.idKomoditas = idKomoditas;
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

    public String getIdKategori() {
        return idKategori;
    }

    public void setIdKategori(String idKategori) {
        this.idKategori = idKategori;
    }

    public String getNamaKategori() {
        return namaKategori;
    }

    public void setNamaKategori(String namaKategori) {
        this.namaKategori = namaKategori;
    }

    public String getIdKomoditas() {
        return idKomoditas;
    }

    public void setIdKomoditas(String idKomoditas) {
        this.idKomoditas = idKomoditas;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(DatumKategoriHarga.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        sb.append("idKategori");
        sb.append('=');
        sb.append(((this.idKategori == null)?"<null>":this.idKategori));
        sb.append(',');
        sb.append("namaKategori");
        sb.append('=');
        sb.append(((this.namaKategori == null)?"<null>":this.namaKategori));
        sb.append(',');
        sb.append("idKomoditas");
        sb.append('=');
        sb.append(((this.idKomoditas == null)?"<null>":this.idKomoditas));
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
        dest.writeValue(idKategori);
        dest.writeValue(namaKategori);
        dest.writeValue(idKomoditas);
    }

    public int describeContents() {
        return  0;
    }

}
