
package com.rewangTani.rewangtani.model.modelakunprofil;

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
    @SerializedName("idProfile")
    @Expose
    private String idProfile;
    @SerializedName("idAkun")
    @Expose
    private String idAkun;
    @SerializedName("namaDepan")
    @Expose
    private String namaDepan;
    @SerializedName("namaBelakang")
    @Expose
    private String namaBelakang;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("idAlamat")
    @Expose
    private String idAlamat;
    @SerializedName("nik")
    @Expose
    private String nik;
    @SerializedName("tglLahir")
    @Expose
    private String tglLahir;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("telepon")
    @Expose
    private String telepon;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("idStatusPekerja")
    @Expose
    private String idStatusPekerja;
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
        this.idProfile = ((String) in.readValue((String.class.getClassLoader())));
        this.idAkun = ((String) in.readValue((String.class.getClassLoader())));
        this.namaDepan = ((String) in.readValue((String.class.getClassLoader())));
        this.namaBelakang = ((String) in.readValue((String.class.getClassLoader())));
        this.alamat = ((String) in.readValue((String.class.getClassLoader())));
        this.idAlamat = ((String) in.readValue((String.class.getClassLoader())));
        this.nik = ((String) in.readValue((String.class.getClassLoader())));
        this.tglLahir = ((String) in.readValue((String.class.getClassLoader())));
        this.gender = ((String) in.readValue((String.class.getClassLoader())));
        this.telepon = ((String) in.readValue((String.class.getClassLoader())));
        this.foto = ((String) in.readValue((String.class.getClassLoader())));
        this.idStatusPekerja = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param idProfile
     * @param namaDepan
     * @param updatedBy
     * @param gender
     * @param telepon
     * @param namaBelakang
     * @param updatedDate
     * @param alamat
     * @param idAlamat
     * @param nik
     * @param tglLahir
     * @param createdDate
     * @param idAkun
     * @param foto
     * @param createdBy
     * @param idStatusPekerja
     */
    public Data(String createdBy, String createdDate, String updatedBy, String updatedDate, String idProfile, String idAkun, String namaDepan, String namaBelakang, String alamat, String idAlamat, String nik, String tglLahir, String gender, String telepon, String foto, String idStatusPekerja) {
        super();
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
        this.idProfile = idProfile;
        this.idAkun = idAkun;
        this.namaDepan = namaDepan;
        this.namaBelakang = namaBelakang;
        this.alamat = alamat;
        this.idAlamat = idAlamat;
        this.nik = nik;
        this.tglLahir = tglLahir;
        this.gender = gender;
        this.telepon = telepon;
        this.foto = foto;
        this.idStatusPekerja = idStatusPekerja;
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

    public String getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(String idProfile) {
        this.idProfile = idProfile;
    }

    public String getIdAkun() {
        return idAkun;
    }

    public void setIdAkun(String idAkun) {
        this.idAkun = idAkun;
    }

    public String getNamaDepan() {
        return namaDepan;
    }

    public void setNamaDepan(String namaDepan) {
        this.namaDepan = namaDepan;
    }

    public String getNamaBelakang() {
        return namaBelakang;
    }

    public void setNamaBelakang(String namaBelakang) {
        this.namaBelakang = namaBelakang;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getIdAlamat() {
        return idAlamat;
    }

    public void setIdAlamat(String idAlamat) {
        this.idAlamat = idAlamat;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getTglLahir() {
        return tglLahir;
    }

    public void setTglLahir(String tglLahir) {
        this.tglLahir = tglLahir;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getIdStatusPekerja() {
        return idStatusPekerja;
    }

    public void setIdStatusPekerja(String idStatusPekerja) {
        this.idStatusPekerja = idStatusPekerja;
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
        sb.append("idProfile");
        sb.append('=');
        sb.append(((this.idProfile == null)?"<null>":this.idProfile));
        sb.append(',');
        sb.append("idAkun");
        sb.append('=');
        sb.append(((this.idAkun == null)?"<null>":this.idAkun));
        sb.append(',');
        sb.append("namaDepan");
        sb.append('=');
        sb.append(((this.namaDepan == null)?"<null>":this.namaDepan));
        sb.append(',');
        sb.append("namaBelakang");
        sb.append('=');
        sb.append(((this.namaBelakang == null)?"<null>":this.namaBelakang));
        sb.append(',');
        sb.append("alamat");
        sb.append('=');
        sb.append(((this.alamat == null)?"<null>":this.alamat));
        sb.append(',');
        sb.append("idAlamat");
        sb.append('=');
        sb.append(((this.idAlamat == null)?"<null>":this.idAlamat));
        sb.append(',');
        sb.append("nik");
        sb.append('=');
        sb.append(((this.nik == null)?"<null>":this.nik));
        sb.append(',');
        sb.append("tglLahir");
        sb.append('=');
        sb.append(((this.tglLahir == null)?"<null>":this.tglLahir));
        sb.append(',');
        sb.append("gender");
        sb.append('=');
        sb.append(((this.gender == null)?"<null>":this.gender));
        sb.append(',');
        sb.append("telepon");
        sb.append('=');
        sb.append(((this.telepon == null)?"<null>":this.telepon));
        sb.append(',');
        sb.append("foto");
        sb.append('=');
        sb.append(((this.foto == null)?"<null>":this.foto));
        sb.append(',');
        sb.append("idStatusPekerja");
        sb.append('=');
        sb.append(((this.idStatusPekerja == null)?"<null>":this.idStatusPekerja));
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
        dest.writeValue(idProfile);
        dest.writeValue(idAkun);
        dest.writeValue(namaDepan);
        dest.writeValue(namaBelakang);
        dest.writeValue(alamat);
        dest.writeValue(idAlamat);
        dest.writeValue(nik);
        dest.writeValue(tglLahir);
        dest.writeValue(gender);
        dest.writeValue(telepon);
        dest.writeValue(foto);
        dest.writeValue(idStatusPekerja);
    }

    public int describeContents() {
        return  0;
    }

}
