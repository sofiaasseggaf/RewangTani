
package com.rewangTani.rewangtani.model.modelwarungwarung.modeltenagakerja;

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
    @SerializedName("idTenagaKerja")
    @Expose
    private String idTenagaKerja;
    @SerializedName("idProfil")
    @Expose
    private String idProfil;
    @SerializedName("idProduk")
    @Expose
    private String idProduk;
    @SerializedName("idTipeProduk")
    @Expose
    private String idTipeProduk;
    @SerializedName("namaTenagaKerja")
    @Expose
    private String namaTenagaKerja;
    @SerializedName("namaTipeKerja")
    @Expose
    private String namaTipeKerja;
    @SerializedName("biaya")
    @Expose
    private Integer biaya;
    @SerializedName("deskripsi")
    @Expose
    private String deskripsi;
    @SerializedName("keahlian")
    @Expose
    private String keahlian;
    @SerializedName("pengalamanKerja")
    @Expose
    private String pengalamanKerja;
    @SerializedName("idFoto")
    @Expose
    private String idFoto;
    @SerializedName("kota")
    @Expose
    private String kota;
    @SerializedName("jmlTerjual")
    @Expose
    private Integer jmlTerjual;
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
        this.idTenagaKerja = ((String) in.readValue((String.class.getClassLoader())));
        this.idProfil = ((String) in.readValue((String.class.getClassLoader())));
        this.idProduk = ((String) in.readValue((String.class.getClassLoader())));
        this.idTipeProduk = ((String) in.readValue((String.class.getClassLoader())));
        this.namaTenagaKerja = ((String) in.readValue((String.class.getClassLoader())));
        this.namaTipeKerja = ((String) in.readValue((String.class.getClassLoader())));
        this.biaya = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.deskripsi = ((String) in.readValue((String.class.getClassLoader())));
        this.keahlian = ((String) in.readValue((String.class.getClassLoader())));
        this.pengalamanKerja = ((String) in.readValue((String.class.getClassLoader())));
        this.idFoto = ((String) in.readValue((String.class.getClassLoader())));
        this.kota = ((String) in.readValue((String.class.getClassLoader())));
        this.jmlTerjual = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param idProduk
     * @param updatedBy
     * @param kota
     * @param pengalamanKerja
     * @param idTipeProduk
     * @param jmlTerjual
     * @param namaTenagaKerja
     * @param updatedDate
     * @param idTenagaKerja
     * @param keahlian
     * @param idFoto
     * @param createdDate
     * @param biaya
     * @param createdBy
     * @param namaTipeKerja
     * @param deskripsi
     * @param idProfil
     */
    public Data(String createdBy, String createdDate, String updatedBy, String updatedDate, String idTenagaKerja, String idProfil, String idProduk, String idTipeProduk, String namaTenagaKerja, String namaTipeKerja, Integer biaya, String deskripsi, String keahlian, String pengalamanKerja, String idFoto, String kota, Integer jmlTerjual) {
        super();
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
        this.idTenagaKerja = idTenagaKerja;
        this.idProfil = idProfil;
        this.idProduk = idProduk;
        this.idTipeProduk = idTipeProduk;
        this.namaTenagaKerja = namaTenagaKerja;
        this.namaTipeKerja = namaTipeKerja;
        this.biaya = biaya;
        this.deskripsi = deskripsi;
        this.keahlian = keahlian;
        this.pengalamanKerja = pengalamanKerja;
        this.idFoto = idFoto;
        this.kota = kota;
        this.jmlTerjual = jmlTerjual;
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

    public String getIdTenagaKerja() {
        return idTenagaKerja;
    }

    public void setIdTenagaKerja(String idTenagaKerja) {
        this.idTenagaKerja = idTenagaKerja;
    }

    public String getIdProfil() {
        return idProfil;
    }

    public void setIdProfil(String idProfil) {
        this.idProfil = idProfil;
    }

    public String getIdProduk() {
        return idProduk;
    }

    public void setIdProduk(String idProduk) {
        this.idProduk = idProduk;
    }

    public String getIdTipeProduk() {
        return idTipeProduk;
    }

    public void setIdTipeProduk(String idTipeProduk) {
        this.idTipeProduk = idTipeProduk;
    }

    public String getNamaTenagaKerja() {
        return namaTenagaKerja;
    }

    public void setNamaTenagaKerja(String namaTenagaKerja) {
        this.namaTenagaKerja = namaTenagaKerja;
    }

    public String getNamaTipeKerja() {
        return namaTipeKerja;
    }

    public void setNamaTipeKerja(String namaTipeKerja) {
        this.namaTipeKerja = namaTipeKerja;
    }

    public Integer getBiaya() {
        return biaya;
    }

    public void setBiaya(Integer biaya) {
        this.biaya = biaya;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getKeahlian() {
        return keahlian;
    }

    public void setKeahlian(String keahlian) {
        this.keahlian = keahlian;
    }

    public String getPengalamanKerja() {
        return pengalamanKerja;
    }

    public void setPengalamanKerja(String pengalamanKerja) {
        this.pengalamanKerja = pengalamanKerja;
    }

    public String getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(String idFoto) {
        this.idFoto = idFoto;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public Integer getJmlTerjual() {
        return jmlTerjual;
    }

    public void setJmlTerjual(Integer jmlTerjual) {
        this.jmlTerjual = jmlTerjual;
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
        sb.append("idTenagaKerja");
        sb.append('=');
        sb.append(((this.idTenagaKerja == null)?"<null>":this.idTenagaKerja));
        sb.append(',');
        sb.append("idProfil");
        sb.append('=');
        sb.append(((this.idProfil == null)?"<null>":this.idProfil));
        sb.append(',');
        sb.append("idProduk");
        sb.append('=');
        sb.append(((this.idProduk == null)?"<null>":this.idProduk));
        sb.append(',');
        sb.append("idTipeProduk");
        sb.append('=');
        sb.append(((this.idTipeProduk == null)?"<null>":this.idTipeProduk));
        sb.append(',');
        sb.append("namaTenagaKerja");
        sb.append('=');
        sb.append(((this.namaTenagaKerja == null)?"<null>":this.namaTenagaKerja));
        sb.append(',');
        sb.append("namaTipeKerja");
        sb.append('=');
        sb.append(((this.namaTipeKerja == null)?"<null>":this.namaTipeKerja));
        sb.append(',');
        sb.append("biaya");
        sb.append('=');
        sb.append(((this.biaya == null)?"<null>":this.biaya));
        sb.append(',');
        sb.append("deskripsi");
        sb.append('=');
        sb.append(((this.deskripsi == null)?"<null>":this.deskripsi));
        sb.append(',');
        sb.append("keahlian");
        sb.append('=');
        sb.append(((this.keahlian == null)?"<null>":this.keahlian));
        sb.append(',');
        sb.append("pengalamanKerja");
        sb.append('=');
        sb.append(((this.pengalamanKerja == null)?"<null>":this.pengalamanKerja));
        sb.append(',');
        sb.append("idFoto");
        sb.append('=');
        sb.append(((this.idFoto == null)?"<null>":this.idFoto));
        sb.append(',');
        sb.append("kota");
        sb.append('=');
        sb.append(((this.kota == null)?"<null>":this.kota));
        sb.append(',');
        sb.append("jmlTerjual");
        sb.append('=');
        sb.append(((this.jmlTerjual == null)?"<null>":this.jmlTerjual));
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
        dest.writeValue(idTenagaKerja);
        dest.writeValue(idProfil);
        dest.writeValue(idProduk);
        dest.writeValue(idTipeProduk);
        dest.writeValue(namaTenagaKerja);
        dest.writeValue(namaTipeKerja);
        dest.writeValue(biaya);
        dest.writeValue(deskripsi);
        dest.writeValue(keahlian);
        dest.writeValue(pengalamanKerja);
        dest.writeValue(idFoto);
        dest.writeValue(kota);
        dest.writeValue(jmlTerjual);
    }

    public int describeContents() {
        return  0;
    }

}
