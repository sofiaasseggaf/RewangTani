
package com.rewangTani.rewangtani.model.modelproduk;

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
    @SerializedName("idProduk")
    @Expose
    private String idProduk;
    @SerializedName("idTipeProduk")
    @Expose
    private String idTipeProduk;
    @SerializedName("idProfil")
    @Expose
    private String idProfil;
    @SerializedName("hargaProduk")
    @Expose
    private Integer hargaProduk;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("kota")
    @Expose
    private String kota;
    @SerializedName("jmlProduk")
    @Expose
    private Integer jmlProduk;
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
        this.idProduk = ((String) in.readValue((String.class.getClassLoader())));
        this.idTipeProduk = ((String) in.readValue((String.class.getClassLoader())));
        this.idProfil = ((String) in.readValue((String.class.getClassLoader())));
        this.hargaProduk = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.foto = ((String) in.readValue((String.class.getClassLoader())));
        this.kota = ((String) in.readValue((String.class.getClassLoader())));
        this.jmlProduk = ((Integer) in.readValue((Integer.class.getClassLoader())));
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
     * @param createdDate
     * @param updatedBy
     * @param kota
     * @param jmlProduk
     * @param foto
     * @param createdBy
     * @param idTipeProduk
     * @param jmlTerjual
     * @param hargaProduk
     * @param updatedDate
     * @param idProfil
     */
    public Data(String createdBy, String createdDate, String updatedBy, String updatedDate, String idProduk, String idTipeProduk, String idProfil, Integer hargaProduk, String foto, String kota, Integer jmlProduk, Integer jmlTerjual) {
        super();
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
        this.idProduk = idProduk;
        this.idTipeProduk = idTipeProduk;
        this.idProfil = idProfil;
        this.hargaProduk = hargaProduk;
        this.foto = foto;
        this.kota = kota;
        this.jmlProduk = jmlProduk;
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

    public String getIdProfil() {
        return idProfil;
    }

    public void setIdProfil(String idProfil) {
        this.idProfil = idProfil;
    }

    public Integer getHargaProduk() {
        return hargaProduk;
    }

    public void setHargaProduk(Integer hargaProduk) {
        this.hargaProduk = hargaProduk;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public Integer getJmlProduk() {
        return jmlProduk;
    }

    public void setJmlProduk(Integer jmlProduk) {
        this.jmlProduk = jmlProduk;
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
        sb.append("idProduk");
        sb.append('=');
        sb.append(((this.idProduk == null)?"<null>":this.idProduk));
        sb.append(',');
        sb.append("idTipeProduk");
        sb.append('=');
        sb.append(((this.idTipeProduk == null)?"<null>":this.idTipeProduk));
        sb.append(',');
        sb.append("idProfil");
        sb.append('=');
        sb.append(((this.idProfil == null)?"<null>":this.idProfil));
        sb.append(',');
        sb.append("hargaProduk");
        sb.append('=');
        sb.append(((this.hargaProduk == null)?"<null>":this.hargaProduk));
        sb.append(',');
        sb.append("foto");
        sb.append('=');
        sb.append(((this.foto == null)?"<null>":this.foto));
        sb.append(',');
        sb.append("kota");
        sb.append('=');
        sb.append(((this.kota == null)?"<null>":this.kota));
        sb.append(',');
        sb.append("jmlProduk");
        sb.append('=');
        sb.append(((this.jmlProduk == null)?"<null>":this.jmlProduk));
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
        dest.writeValue(idProduk);
        dest.writeValue(idTipeProduk);
        dest.writeValue(idProfil);
        dest.writeValue(hargaProduk);
        dest.writeValue(foto);
        dest.writeValue(kota);
        dest.writeValue(jmlProduk);
        dest.writeValue(jmlTerjual);
    }

    public int describeContents() {
        return  0;
    }

}
