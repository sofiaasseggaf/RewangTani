
package com.rewangTani.rewangtani.model.modelnoneditable.varietas;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatumVarietas implements Parcelable
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
    @SerializedName("idVarietas")
    @Expose
    private String idVarietas;
    @SerializedName("idKomoditas")
    @Expose
    private String idKomoditas;
    @SerializedName("namaVarietas")
    @Expose
    private String namaVarietas;
    @SerializedName("umurVarietas")
    @Expose
    private Integer umurVarietas;
    @SerializedName("ketahananHama")
    @Expose
    private String ketahananHama;
    @SerializedName("ketahananPenyakit")
    @Expose
    private String ketahananPenyakit;
    @SerializedName("anjuranTanam")
    @Expose
    private String anjuranTanam;
    @SerializedName("anjuranKetinggian")
    @Expose
    private Integer anjuranKetinggian;
    @SerializedName("potensiHasil")
    @Expose
    private Object potensiHasil;
    @SerializedName("rataHasil")
    @Expose
    private Double rataHasil;
    public final static Creator<DatumVarietas> CREATOR = new Creator<DatumVarietas>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DatumVarietas createFromParcel(android.os.Parcel in) {
            return new DatumVarietas(in);
        }

        public DatumVarietas[] newArray(int size) {
            return (new DatumVarietas[size]);
        }

    }
    ;

    protected DatumVarietas(android.os.Parcel in) {
        this.createdBy = ((String) in.readValue((String.class.getClassLoader())));
        this.createdDate = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedBy = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedDate = ((String) in.readValue((String.class.getClassLoader())));
        this.idVarietas = ((String) in.readValue((String.class.getClassLoader())));
        this.idKomoditas = ((String) in.readValue((String.class.getClassLoader())));
        this.namaVarietas = ((String) in.readValue((String.class.getClassLoader())));
        this.umurVarietas = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.ketahananHama = ((String) in.readValue((String.class.getClassLoader())));
        this.ketahananPenyakit = ((String) in.readValue((String.class.getClassLoader())));
        this.anjuranTanam = ((String) in.readValue((String.class.getClassLoader())));
        this.anjuranKetinggian = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.potensiHasil = ((Object) in.readValue((Object.class.getClassLoader())));
        this.rataHasil = ((Double) in.readValue((Double.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public DatumVarietas() {
    }

    /**
     * 
     * @param potensiHasil
     * @param idVarietas
     * @param updatedBy
     * @param umurVarietas
     * @param anjuranKetinggian
     * @param rataHasil
     * @param updatedDate
     * @param anjuranTanam
     * @param idKomoditas
     * @param createdDate
     * @param createdBy
     * @param ketahananHama
     * @param namaVarietas
     * @param ketahananPenyakit
     */
    public DatumVarietas(String createdBy, String createdDate, String updatedBy, String updatedDate, String idVarietas, String idKomoditas, String namaVarietas, Integer umurVarietas, String ketahananHama, String ketahananPenyakit, String anjuranTanam, Integer anjuranKetinggian, Object potensiHasil, Double rataHasil) {
        super();
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
        this.idVarietas = idVarietas;
        this.idKomoditas = idKomoditas;
        this.namaVarietas = namaVarietas;
        this.umurVarietas = umurVarietas;
        this.ketahananHama = ketahananHama;
        this.ketahananPenyakit = ketahananPenyakit;
        this.anjuranTanam = anjuranTanam;
        this.anjuranKetinggian = anjuranKetinggian;
        this.potensiHasil = potensiHasil;
        this.rataHasil = rataHasil;
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

    public String getIdVarietas() {
        return idVarietas;
    }

    public void setIdVarietas(String idVarietas) {
        this.idVarietas = idVarietas;
    }

    public String getIdKomoditas() {
        return idKomoditas;
    }

    public void setIdKomoditas(String idKomoditas) {
        this.idKomoditas = idKomoditas;
    }

    public String getNamaVarietas() {
        return namaVarietas;
    }

    public void setNamaVarietas(String namaVarietas) {
        this.namaVarietas = namaVarietas;
    }

    public Integer getUmurVarietas() {
        return umurVarietas;
    }

    public void setUmurVarietas(Integer umurVarietas) {
        this.umurVarietas = umurVarietas;
    }

    public String getKetahananHama() {
        return ketahananHama;
    }

    public void setKetahananHama(String ketahananHama) {
        this.ketahananHama = ketahananHama;
    }

    public String getKetahananPenyakit() {
        return ketahananPenyakit;
    }

    public void setKetahananPenyakit(String ketahananPenyakit) {
        this.ketahananPenyakit = ketahananPenyakit;
    }

    public String getAnjuranTanam() {
        return anjuranTanam;
    }

    public void setAnjuranTanam(String anjuranTanam) {
        this.anjuranTanam = anjuranTanam;
    }

    public Integer getAnjuranKetinggian() {
        return anjuranKetinggian;
    }

    public void setAnjuranKetinggian(Integer anjuranKetinggian) {
        this.anjuranKetinggian = anjuranKetinggian;
    }

    public Object getPotensiHasil() {
        return potensiHasil;
    }

    public void setPotensiHasil(Object potensiHasil) {
        this.potensiHasil = potensiHasil;
    }

    public Double getRataHasil() {
        return rataHasil;
    }

    public void setRataHasil(Double rataHasil) {
        this.rataHasil = rataHasil;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(DatumVarietas.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        sb.append("idVarietas");
        sb.append('=');
        sb.append(((this.idVarietas == null)?"<null>":this.idVarietas));
        sb.append(',');
        sb.append("idKomoditas");
        sb.append('=');
        sb.append(((this.idKomoditas == null)?"<null>":this.idKomoditas));
        sb.append(',');
        sb.append("namaVarietas");
        sb.append('=');
        sb.append(((this.namaVarietas == null)?"<null>":this.namaVarietas));
        sb.append(',');
        sb.append("umurVarietas");
        sb.append('=');
        sb.append(((this.umurVarietas == null)?"<null>":this.umurVarietas));
        sb.append(',');
        sb.append("ketahananHama");
        sb.append('=');
        sb.append(((this.ketahananHama == null)?"<null>":this.ketahananHama));
        sb.append(',');
        sb.append("ketahananPenyakit");
        sb.append('=');
        sb.append(((this.ketahananPenyakit == null)?"<null>":this.ketahananPenyakit));
        sb.append(',');
        sb.append("anjuranTanam");
        sb.append('=');
        sb.append(((this.anjuranTanam == null)?"<null>":this.anjuranTanam));
        sb.append(',');
        sb.append("anjuranKetinggian");
        sb.append('=');
        sb.append(((this.anjuranKetinggian == null)?"<null>":this.anjuranKetinggian));
        sb.append(',');
        sb.append("potensiHasil");
        sb.append('=');
        sb.append(((this.potensiHasil == null)?"<null>":this.potensiHasil));
        sb.append(',');
        sb.append("rataHasil");
        sb.append('=');
        sb.append(((this.rataHasil == null)?"<null>":this.rataHasil));
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
        dest.writeValue(idVarietas);
        dest.writeValue(idKomoditas);
        dest.writeValue(namaVarietas);
        dest.writeValue(umurVarietas);
        dest.writeValue(ketahananHama);
        dest.writeValue(ketahananPenyakit);
        dest.writeValue(anjuranTanam);
        dest.writeValue(anjuranKetinggian);
        dest.writeValue(potensiHasil);
        dest.writeValue(rataHasil);
    }

    public int describeContents() {
        return  0;
    }

}
