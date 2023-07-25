
package com.rewangTani.rewangtani.model.modelnoneditable.subkategoriharga;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatumSubKategoriHarga implements Parcelable
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
    @SerializedName("idSubkategori")
    @Expose
    private String idSubkategori;
    @SerializedName("idkategori")
    @Expose
    private String idkategori;
    @SerializedName("namaSubkategori")
    @Expose
    private String namaSubkategori;
    public final static Creator<DatumSubKategoriHarga> CREATOR = new Creator<DatumSubKategoriHarga>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DatumSubKategoriHarga createFromParcel(android.os.Parcel in) {
            return new DatumSubKategoriHarga(in);
        }

        public DatumSubKategoriHarga[] newArray(int size) {
            return (new DatumSubKategoriHarga[size]);
        }

    }
    ;

    protected DatumSubKategoriHarga(android.os.Parcel in) {
        this.createdBy = ((String) in.readValue((String.class.getClassLoader())));
        this.createdDate = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedBy = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedDate = ((String) in.readValue((String.class.getClassLoader())));
        this.idSubkategori = ((String) in.readValue((String.class.getClassLoader())));
        this.idkategori = ((String) in.readValue((String.class.getClassLoader())));
        this.namaSubkategori = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public DatumSubKategoriHarga() {
    }

    /**
     * 
     * @param namaSubkategori
     * @param createdDate
     * @param updatedBy
     * @param createdBy
     * @param idkategori
     * @param updatedDate
     * @param idSubkategori
     */
    public DatumSubKategoriHarga(String createdBy, String createdDate, String updatedBy, String updatedDate, String idSubkategori, String idkategori, String namaSubkategori) {
        super();
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
        this.idSubkategori = idSubkategori;
        this.idkategori = idkategori;
        this.namaSubkategori = namaSubkategori;
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

    public String getIdSubkategori() {
        return idSubkategori;
    }

    public void setIdSubkategori(String idSubkategori) {
        this.idSubkategori = idSubkategori;
    }

    public String getIdkategori() {
        return idkategori;
    }

    public void setIdkategori(String idkategori) {
        this.idkategori = idkategori;
    }

    public String getNamaSubkategori() {
        return namaSubkategori;
    }

    public void setNamaSubkategori(String namaSubkategori) {
        this.namaSubkategori = namaSubkategori;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(DatumSubKategoriHarga.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        sb.append("idSubkategori");
        sb.append('=');
        sb.append(((this.idSubkategori == null)?"<null>":this.idSubkategori));
        sb.append(',');
        sb.append("idkategori");
        sb.append('=');
        sb.append(((this.idkategori == null)?"<null>":this.idkategori));
        sb.append(',');
        sb.append("namaSubkategori");
        sb.append('=');
        sb.append(((this.namaSubkategori == null)?"<null>":this.namaSubkategori));
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
        dest.writeValue(idSubkategori);
        dest.writeValue(idkategori);
        dest.writeValue(namaSubkategori);
    }

    public int describeContents() {
        return  0;
    }

}
