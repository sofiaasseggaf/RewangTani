
package com.rewangTani.rewangtani.model.modelnoneditable.obat;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Parcelable
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
    @SerializedName("idObat")
    @Expose
    private String idObat;
    @SerializedName("idSubkategori")
    @Expose
    private String idSubkategori;
    @SerializedName("namaObat")
    @Expose
    private String namaObat;
    @SerializedName("kandunganObat")
    @Expose
    private String kandunganObat;
    @SerializedName("kegunaanObat")
    @Expose
    private String kegunaanObat;
    @SerializedName("produsenObat")
    @Expose
    private String produsenObat;
    @SerializedName("ketObat")
    @Expose
    private String ketObat;
    public final static Creator<Data> CREATOR = new Creator<Data>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Data createFromParcel(android.os.Parcel in) {
            return new Data(in);
        }

        public Data[] newArray(int size) {
            return (new Data[size]);
        }

    }
    ;

    protected Data(android.os.Parcel in) {
        this.createdBy = ((String) in.readValue((String.class.getClassLoader())));
        this.createdDate = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedBy = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedDate = ((String) in.readValue((String.class.getClassLoader())));
        this.idObat = ((String) in.readValue((String.class.getClassLoader())));
        this.idSubkategori = ((String) in.readValue((String.class.getClassLoader())));
        this.namaObat = ((String) in.readValue((String.class.getClassLoader())));
        this.kandunganObat = ((String) in.readValue((String.class.getClassLoader())));
        this.kegunaanObat = ((String) in.readValue((String.class.getClassLoader())));
        this.produsenObat = ((String) in.readValue((String.class.getClassLoader())));
        this.ketObat = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param idObat
     * @param produsenObat
     * @param createdDate
     * @param updatedBy
     * @param createdBy
     * @param namaObat
     * @param updatedDate
     * @param kegunaanObat
     * @param idSubkategori
     * @param ketObat
     * @param kandunganObat
     */
    public Data(String createdBy, String createdDate, String updatedBy, String updatedDate, String idObat, String idSubkategori, String namaObat, String kandunganObat, String kegunaanObat, String produsenObat, String ketObat) {
        super();
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
        this.idObat = idObat;
        this.idSubkategori = idSubkategori;
        this.namaObat = namaObat;
        this.kandunganObat = kandunganObat;
        this.kegunaanObat = kegunaanObat;
        this.produsenObat = produsenObat;
        this.ketObat = ketObat;
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

    public String getIdObat() {
        return idObat;
    }

    public void setIdObat(String idObat) {
        this.idObat = idObat;
    }

    public String getIdSubkategori() {
        return idSubkategori;
    }

    public void setIdSubkategori(String idSubkategori) {
        this.idSubkategori = idSubkategori;
    }

    public String getNamaObat() {
        return namaObat;
    }

    public void setNamaObat(String namaObat) {
        this.namaObat = namaObat;
    }

    public String getKandunganObat() {
        return kandunganObat;
    }

    public void setKandunganObat(String kandunganObat) {
        this.kandunganObat = kandunganObat;
    }

    public String getKegunaanObat() {
        return kegunaanObat;
    }

    public void setKegunaanObat(String kegunaanObat) {
        this.kegunaanObat = kegunaanObat;
    }

    public String getProdusenObat() {
        return produsenObat;
    }

    public void setProdusenObat(String produsenObat) {
        this.produsenObat = produsenObat;
    }

    public String getKetObat() {
        return ketObat;
    }

    public void setKetObat(String ketObat) {
        this.ketObat = ketObat;
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
        sb.append("idObat");
        sb.append('=');
        sb.append(((this.idObat == null)?"<null>":this.idObat));
        sb.append(',');
        sb.append("idSubkategori");
        sb.append('=');
        sb.append(((this.idSubkategori == null)?"<null>":this.idSubkategori));
        sb.append(',');
        sb.append("namaObat");
        sb.append('=');
        sb.append(((this.namaObat == null)?"<null>":this.namaObat));
        sb.append(',');
        sb.append("kandunganObat");
        sb.append('=');
        sb.append(((this.kandunganObat == null)?"<null>":this.kandunganObat));
        sb.append(',');
        sb.append("kegunaanObat");
        sb.append('=');
        sb.append(((this.kegunaanObat == null)?"<null>":this.kegunaanObat));
        sb.append(',');
        sb.append("produsenObat");
        sb.append('=');
        sb.append(((this.produsenObat == null)?"<null>":this.produsenObat));
        sb.append(',');
        sb.append("ketObat");
        sb.append('=');
        sb.append(((this.ketObat == null)?"<null>":this.ketObat));
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
        dest.writeValue(idObat);
        dest.writeValue(idSubkategori);
        dest.writeValue(namaObat);
        dest.writeValue(kandunganObat);
        dest.writeValue(kegunaanObat);
        dest.writeValue(produsenObat);
        dest.writeValue(ketObat);
    }

    public int describeContents() {
        return  0;
    }

}
