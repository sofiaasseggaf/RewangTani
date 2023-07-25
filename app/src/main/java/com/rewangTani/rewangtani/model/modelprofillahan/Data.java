
package com.rewangTani.rewangtani.model.modelprofillahan;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
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
    @SerializedName("idProfileTanah")
    @Expose
    private String idProfileTanah;
    @SerializedName("idUser")
    @Expose
    private String idUser;
    @SerializedName("idAlamat")
    @Expose
    private Object idAlamat;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("luasGarapan")
    @Expose
    private Double luasGarapan;
    @SerializedName("idSistemIrigasi")
    @Expose
    private String idSistemIrigasi;
    @SerializedName("kemiringanTanah")
    @Expose
    private Integer kemiringanTanah;
    @SerializedName("phTanah")
    @Expose
    private Integer phTanah;
    @SerializedName("namaProfilTanah")
    @Expose
    private String namaProfilTanah;
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
        this.idProfileTanah = ((String) in.readValue((String.class.getClassLoader())));
        this.idUser = ((String) in.readValue((String.class.getClassLoader())));
        this.idAlamat = ((Object) in.readValue((Object.class.getClassLoader())));
        this.latitude = ((String) in.readValue((String.class.getClassLoader())));
        this.longitude = ((String) in.readValue((String.class.getClassLoader())));
        this.luasGarapan = ((Double) in.readValue((Double.class.getClassLoader())));
        this.idSistemIrigasi = ((String) in.readValue((String.class.getClassLoader())));
        this.kemiringanTanah = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.phTanah = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.namaProfilTanah = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param updatedBy
     * @param namaProfilTanah
     * @param kemiringanTanah
     * @param latitude
     * @param updatedDate
     * @param luasGarapan
     * @param phTanah
     * @param idUser
     * @param idAlamat
     * @param idSistemIrigasi
     * @param createdDate
     * @param createdBy
     * @param idProfileTanah
     * @param longitude
     */
    public Data(String createdBy, String createdDate, String updatedBy, String updatedDate, String idProfileTanah, String idUser, Object idAlamat, String latitude, String longitude, Double luasGarapan, String idSistemIrigasi, Integer kemiringanTanah, Integer phTanah, String namaProfilTanah) {
        super();
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
        this.idProfileTanah = idProfileTanah;
        this.idUser = idUser;
        this.idAlamat = idAlamat;
        this.latitude = latitude;
        this.longitude = longitude;
        this.luasGarapan = luasGarapan;
        this.idSistemIrigasi = idSistemIrigasi;
        this.kemiringanTanah = kemiringanTanah;
        this.phTanah = phTanah;
        this.namaProfilTanah = namaProfilTanah;
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

    public String getIdProfileTanah() {
        return idProfileTanah;
    }

    public void setIdProfileTanah(String idProfileTanah) {
        this.idProfileTanah = idProfileTanah;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public Object getIdAlamat() {
        return idAlamat;
    }

    public void setIdAlamat(Object idAlamat) {
        this.idAlamat = idAlamat;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Double getLuasGarapan() {
        return luasGarapan;
    }

    public void setLuasGarapan(Double luasGarapan) {
        this.luasGarapan = luasGarapan;
    }

    public String getIdSistemIrigasi() {
        return idSistemIrigasi;
    }

    public void setIdSistemIrigasi(String idSistemIrigasi) {
        this.idSistemIrigasi = idSistemIrigasi;
    }

    public Integer getKemiringanTanah() {
        return kemiringanTanah;
    }

    public void setKemiringanTanah(Integer kemiringanTanah) {
        this.kemiringanTanah = kemiringanTanah;
    }

    public Integer getPhTanah() {
        return phTanah;
    }

    public void setPhTanah(Integer phTanah) {
        this.phTanah = phTanah;
    }

    public String getNamaProfilTanah() {
        return namaProfilTanah;
    }

    public void setNamaProfilTanah(String namaProfilTanah) {
        this.namaProfilTanah = namaProfilTanah;
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
        sb.append("idProfileTanah");
        sb.append('=');
        sb.append(((this.idProfileTanah == null)?"<null>":this.idProfileTanah));
        sb.append(',');
        sb.append("idUser");
        sb.append('=');
        sb.append(((this.idUser == null)?"<null>":this.idUser));
        sb.append(',');
        sb.append("idAlamat");
        sb.append('=');
        sb.append(((this.idAlamat == null)?"<null>":this.idAlamat));
        sb.append(',');
        sb.append("latitude");
        sb.append('=');
        sb.append(((this.latitude == null)?"<null>":this.latitude));
        sb.append(',');
        sb.append("longitude");
        sb.append('=');
        sb.append(((this.longitude == null)?"<null>":this.longitude));
        sb.append(',');
        sb.append("luasGarapan");
        sb.append('=');
        sb.append(((this.luasGarapan == null)?"<null>":this.luasGarapan));
        sb.append(',');
        sb.append("idSistemIrigasi");
        sb.append('=');
        sb.append(((this.idSistemIrigasi == null)?"<null>":this.idSistemIrigasi));
        sb.append(',');
        sb.append("kemiringanTanah");
        sb.append('=');
        sb.append(((this.kemiringanTanah == null)?"<null>":this.kemiringanTanah));
        sb.append(',');
        sb.append("phTanah");
        sb.append('=');
        sb.append(((this.phTanah == null)?"<null>":this.phTanah));
        sb.append(',');
        sb.append("namaProfilTanah");
        sb.append('=');
        sb.append(((this.namaProfilTanah == null)?"<null>":this.namaProfilTanah));
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
        dest.writeValue(idProfileTanah);
        dest.writeValue(idUser);
        dest.writeValue(idAlamat);
        dest.writeValue(latitude);
        dest.writeValue(longitude);
        dest.writeValue(luasGarapan);
        dest.writeValue(idSistemIrigasi);
        dest.writeValue(kemiringanTanah);
        dest.writeValue(phTanah);
        dest.writeValue(namaProfilTanah);
    }

    public int describeContents() {
        return  0;
    }

}
