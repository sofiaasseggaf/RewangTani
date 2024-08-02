
package com.rewangTani.rewangtani.model.modelwarungwarung.modelpupukpestisida;

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
    @SerializedName("idWarungBpp")
    @Expose
    private String idWarungBpp;
    @SerializedName("idProfil")
    @Expose
    private String idProfil;
    @SerializedName("idProduk")
    @Expose
    private String idProduk;
    @SerializedName("idTipeProduk")
    @Expose
    private String idTipeProduk;
    @SerializedName("namaProduk")
    @Expose
    private String namaProduk;
    @SerializedName("hargaProduk")
    @Expose
    private Integer hargaProduk;
    @SerializedName("deskProduk")
    @Expose
    private String deskProduk;
    @SerializedName("beratProduk")
    @Expose
    private Double beratProduk;
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
        this.idWarungBpp = ((String) in.readValue((String.class.getClassLoader())));
        this.idProfil = ((String) in.readValue((String.class.getClassLoader())));
        this.idProduk = ((String) in.readValue((String.class.getClassLoader())));
        this.idTipeProduk = ((String) in.readValue((String.class.getClassLoader())));
        this.namaProduk = ((String) in.readValue((String.class.getClassLoader())));
        this.hargaProduk = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.deskProduk = ((String) in.readValue((String.class.getClassLoader())));
        this.beratProduk = ((Double) in.readValue((Double.class.getClassLoader())));
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
     * @param namaProduk
     * @param kota
     * @param idTipeProduk
     * @param jmlTerjual
     * @param hargaProduk
     * @param updatedDate
     * @param beratProduk
     * @param idFoto
     * @param createdDate
     * @param createdBy
     * @param deskProduk
     * @param idWarungBpp
     * @param idProfil
     */
    public Data(String createdBy, String createdDate, String updatedBy, String updatedDate, String idWarungBpp, String idProfil, String idProduk, String idTipeProduk, String namaProduk, Integer hargaProduk, String deskProduk, Double beratProduk, String idFoto, String kota, Integer jmlTerjual) {
        super();
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
        this.idWarungBpp = idWarungBpp;
        this.idProfil = idProfil;
        this.idProduk = idProduk;
        this.idTipeProduk = idTipeProduk;
        this.namaProduk = namaProduk;
        this.hargaProduk = hargaProduk;
        this.deskProduk = deskProduk;
        this.beratProduk = beratProduk;
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

    public String getIdWarungBpp() {
        return idWarungBpp;
    }

    public void setIdWarungBpp(String idWarungBpp) {
        this.idWarungBpp = idWarungBpp;
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

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public Integer getHargaProduk() {
        return hargaProduk;
    }

    public void setHargaProduk(Integer hargaProduk) {
        this.hargaProduk = hargaProduk;
    }

    public String getDeskProduk() {
        return deskProduk;
    }

    public void setDeskProduk(String deskProduk) {
        this.deskProduk = deskProduk;
    }

    public Double getBeratProduk() {
        return beratProduk;
    }

    public void setBeratProduk(Double beratProduk) {
        this.beratProduk = beratProduk;
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
        sb.append("idWarungBpp");
        sb.append('=');
        sb.append(((this.idWarungBpp == null)?"<null>":this.idWarungBpp));
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
        sb.append("namaProduk");
        sb.append('=');
        sb.append(((this.namaProduk == null)?"<null>":this.namaProduk));
        sb.append(',');
        sb.append("hargaProduk");
        sb.append('=');
        sb.append(((this.hargaProduk == null)?"<null>":this.hargaProduk));
        sb.append(',');
        sb.append("deskProduk");
        sb.append('=');
        sb.append(((this.deskProduk == null)?"<null>":this.deskProduk));
        sb.append(',');
        sb.append("beratProduk");
        sb.append('=');
        sb.append(((this.beratProduk == null)?"<null>":this.beratProduk));
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
        dest.writeValue(idWarungBpp);
        dest.writeValue(idProfil);
        dest.writeValue(idProduk);
        dest.writeValue(idTipeProduk);
        dest.writeValue(namaProduk);
        dest.writeValue(hargaProduk);
        dest.writeValue(deskProduk);
        dest.writeValue(beratProduk);
        dest.writeValue(idFoto);
        dest.writeValue(kota);
        dest.writeValue(jmlTerjual);
    }

    public int describeContents() {
        return  0;
    }

}
