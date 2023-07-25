
package com.rewangTani.rewangtani.model.modelnoneditable.alamat;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatumAlamat implements Parcelable
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
    @SerializedName("idAlamat")
    @Expose
    private String idAlamat;
    @SerializedName("provinsi")
    @Expose
    private String provinsi;
    @SerializedName("kota")
    @Expose
    private String kota;
    @SerializedName("kecamatan")
    @Expose
    private String kecamatan;
    @SerializedName("kelurahan")
    @Expose
    private String kelurahan;
    @SerializedName("rw")
    @Expose
    private Integer rw;
    @SerializedName("rt")
    @Expose
    private Integer rt;
    @SerializedName("kodepos")
    @Expose
    private Integer kodepos;
    public final static Creator<DatumAlamat> CREATOR = new Creator<DatumAlamat>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DatumAlamat createFromParcel(android.os.Parcel in) {
            return new DatumAlamat(in);
        }

        public DatumAlamat[] newArray(int size) {
            return (new DatumAlamat[size]);
        }

    }
    ;

    protected DatumAlamat(android.os.Parcel in) {
        this.createdBy = ((String) in.readValue((String.class.getClassLoader())));
        this.createdDate = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedBy = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedDate = ((String) in.readValue((String.class.getClassLoader())));
        this.idAlamat = ((String) in.readValue((String.class.getClassLoader())));
        this.provinsi = ((String) in.readValue((String.class.getClassLoader())));
        this.kota = ((String) in.readValue((String.class.getClassLoader())));
        this.kecamatan = ((String) in.readValue((String.class.getClassLoader())));
        this.kelurahan = ((String) in.readValue((String.class.getClassLoader())));
        this.rw = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.rt = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.kodepos = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public DatumAlamat() {
    }

    /**
     * 
     * @param idAlamat
     * @param provinsi
     * @param rt
     * @param createdDate
     * @param updatedBy
     * @param kota
     * @param createdBy
     * @param rw
     * @param kecamatan
     * @param kodepos
     * @param updatedDate
     * @param kelurahan
     */
    public DatumAlamat(String createdBy, String createdDate, String updatedBy, String updatedDate, String idAlamat, String provinsi, String kota, String kecamatan, String kelurahan, Integer rw, Integer rt, Integer kodepos) {
        super();
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
        this.idAlamat = idAlamat;
        this.provinsi = provinsi;
        this.kota = kota;
        this.kecamatan = kecamatan;
        this.kelurahan = kelurahan;
        this.rw = rw;
        this.rt = rt;
        this.kodepos = kodepos;
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

    public String getIdAlamat() {
        return idAlamat;
    }

    public void setIdAlamat(String idAlamat) {
        this.idAlamat = idAlamat;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getKelurahan() {
        return kelurahan;
    }

    public void setKelurahan(String kelurahan) {
        this.kelurahan = kelurahan;
    }

    public Integer getRw() {
        return rw;
    }

    public void setRw(Integer rw) {
        this.rw = rw;
    }

    public Integer getRt() {
        return rt;
    }

    public void setRt(Integer rt) {
        this.rt = rt;
    }

    public Integer getKodepos() {
        return kodepos;
    }

    public void setKodepos(Integer kodepos) {
        this.kodepos = kodepos;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(DatumAlamat.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        sb.append("idAlamat");
        sb.append('=');
        sb.append(((this.idAlamat == null)?"<null>":this.idAlamat));
        sb.append(',');
        sb.append("provinsi");
        sb.append('=');
        sb.append(((this.provinsi == null)?"<null>":this.provinsi));
        sb.append(',');
        sb.append("kota");
        sb.append('=');
        sb.append(((this.kota == null)?"<null>":this.kota));
        sb.append(',');
        sb.append("kecamatan");
        sb.append('=');
        sb.append(((this.kecamatan == null)?"<null>":this.kecamatan));
        sb.append(',');
        sb.append("kelurahan");
        sb.append('=');
        sb.append(((this.kelurahan == null)?"<null>":this.kelurahan));
        sb.append(',');
        sb.append("rw");
        sb.append('=');
        sb.append(((this.rw == null)?"<null>":this.rw));
        sb.append(',');
        sb.append("rt");
        sb.append('=');
        sb.append(((this.rt == null)?"<null>":this.rt));
        sb.append(',');
        sb.append("kodepos");
        sb.append('=');
        sb.append(((this.kodepos == null)?"<null>":this.kodepos));
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
        dest.writeValue(idAlamat);
        dest.writeValue(provinsi);
        dest.writeValue(kota);
        dest.writeValue(kecamatan);
        dest.writeValue(kelurahan);
        dest.writeValue(rw);
        dest.writeValue(rt);
        dest.writeValue(kodepos);
    }

    public int describeContents() {
        return  0;
    }

}
