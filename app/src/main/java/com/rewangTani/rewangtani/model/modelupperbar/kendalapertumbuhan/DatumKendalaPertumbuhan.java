
package com.rewangTani.rewangtani.model.modelupperbar.kendalapertumbuhan;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatumKendalaPertumbuhan implements Parcelable
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
    @SerializedName("idKendalaPertumbuhan")
    @Expose
    private String idKendalaPertumbuhan;
    @SerializedName("idSudahTanam")
    @Expose
    private String idSudahTanam;
    @SerializedName("idProfilTanah")
    @Expose
    private String idProfilTanah;
    @SerializedName("kendalaHama")
    @Expose
    private String kendalaHama;
    @SerializedName("kendalaBencana")
    @Expose
    private String kendalaBencana;
    @SerializedName("kendalaLainnya")
    @Expose
    private String kendalaLainnya;
    public final static Creator<DatumKendalaPertumbuhan> CREATOR = new Creator<DatumKendalaPertumbuhan>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DatumKendalaPertumbuhan createFromParcel(android.os.Parcel in) {
            return new DatumKendalaPertumbuhan(in);
        }

        public DatumKendalaPertumbuhan[] newArray(int size) {
            return (new DatumKendalaPertumbuhan[size]);
        }

    }
    ;

    protected DatumKendalaPertumbuhan(android.os.Parcel in) {
        this.createdBy = ((String) in.readValue((String.class.getClassLoader())));
        this.createdDate = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedBy = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedDate = ((String) in.readValue((String.class.getClassLoader())));
        this.idKendalaPertumbuhan = ((String) in.readValue((String.class.getClassLoader())));
        this.idSudahTanam = ((String) in.readValue((String.class.getClassLoader())));
        this.idProfilTanah = ((String) in.readValue((String.class.getClassLoader())));
        this.kendalaHama = ((String) in.readValue((String.class.getClassLoader())));
        this.kendalaBencana = ((String) in.readValue((String.class.getClassLoader())));
        this.kendalaLainnya = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public DatumKendalaPertumbuhan() {
    }

    /**
     * 
     * @param createdDate
     * @param updatedBy
     * @param idKendalaPertumbuhan
     * @param kendalaBencana
     * @param createdBy
     * @param idProfilTanah
     * @param kendalaLainnya
     * @param updatedDate
     * @param kendalaHama
     * @param idSudahTanam
     */
    public DatumKendalaPertumbuhan(String createdBy, String createdDate, String updatedBy, String updatedDate, String idKendalaPertumbuhan, String idSudahTanam, String idProfilTanah, String kendalaHama, String kendalaBencana, String kendalaLainnya) {
        super();
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
        this.idKendalaPertumbuhan = idKendalaPertumbuhan;
        this.idSudahTanam = idSudahTanam;
        this.idProfilTanah = idProfilTanah;
        this.kendalaHama = kendalaHama;
        this.kendalaBencana = kendalaBencana;
        this.kendalaLainnya = kendalaLainnya;
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

    public String getIdKendalaPertumbuhan() {
        return idKendalaPertumbuhan;
    }

    public void setIdKendalaPertumbuhan(String idKendalaPertumbuhan) {
        this.idKendalaPertumbuhan = idKendalaPertumbuhan;
    }

    public String getIdSudahTanam() {
        return idSudahTanam;
    }

    public void setIdSudahTanam(String idSudahTanam) {
        this.idSudahTanam = idSudahTanam;
    }

    public String getIdProfilTanah() {
        return idProfilTanah;
    }

    public void setIdProfilTanah(String idProfilTanah) {
        this.idProfilTanah = idProfilTanah;
    }

    public String getKendalaHama() {
        return kendalaHama;
    }

    public void setKendalaHama(String kendalaHama) {
        this.kendalaHama = kendalaHama;
    }

    public String getKendalaBencana() {
        return kendalaBencana;
    }

    public void setKendalaBencana(String kendalaBencana) {
        this.kendalaBencana = kendalaBencana;
    }

    public String getKendalaLainnya() {
        return kendalaLainnya;
    }

    public void setKendalaLainnya(String kendalaLainnya) {
        this.kendalaLainnya = kendalaLainnya;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(DatumKendalaPertumbuhan.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        sb.append("idKendalaPertumbuhan");
        sb.append('=');
        sb.append(((this.idKendalaPertumbuhan == null)?"<null>":this.idKendalaPertumbuhan));
        sb.append(',');
        sb.append("idSudahTanam");
        sb.append('=');
        sb.append(((this.idSudahTanam == null)?"<null>":this.idSudahTanam));
        sb.append(',');
        sb.append("idProfilTanah");
        sb.append('=');
        sb.append(((this.idProfilTanah == null)?"<null>":this.idProfilTanah));
        sb.append(',');
        sb.append("kendalaHama");
        sb.append('=');
        sb.append(((this.kendalaHama == null)?"<null>":this.kendalaHama));
        sb.append(',');
        sb.append("kendalaBencana");
        sb.append('=');
        sb.append(((this.kendalaBencana == null)?"<null>":this.kendalaBencana));
        sb.append(',');
        sb.append("kendalaLainnya");
        sb.append('=');
        sb.append(((this.kendalaLainnya == null)?"<null>":this.kendalaLainnya));
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
        dest.writeValue(idKendalaPertumbuhan);
        dest.writeValue(idSudahTanam);
        dest.writeValue(idProfilTanah);
        dest.writeValue(kendalaHama);
        dest.writeValue(kendalaBencana);
        dest.writeValue(kendalaLainnya);
    }

    public int describeContents() {
        return  0;
    }

}
