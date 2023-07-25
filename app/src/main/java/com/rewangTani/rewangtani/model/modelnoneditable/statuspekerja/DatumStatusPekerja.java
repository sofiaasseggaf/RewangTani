
package com.rewangTani.rewangtani.model.modelnoneditable.statuspekerja;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatumStatusPekerja implements Parcelable
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
    @SerializedName("idStatusPekerja")
    @Expose
    private String idStatusPekerja;
    @SerializedName("namaStatusPekerja")
    @Expose
    private String namaStatusPekerja;
    @SerializedName("ketStatusPekerja")
    @Expose
    private String ketStatusPekerja;
    public final static Creator<DatumStatusPekerja> CREATOR = new Creator<DatumStatusPekerja>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DatumStatusPekerja createFromParcel(android.os.Parcel in) {
            return new DatumStatusPekerja(in);
        }

        public DatumStatusPekerja[] newArray(int size) {
            return (new DatumStatusPekerja[size]);
        }

    }
    ;

    protected DatumStatusPekerja(android.os.Parcel in) {
        this.createdBy = ((String) in.readValue((String.class.getClassLoader())));
        this.createdDate = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedBy = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedDate = ((String) in.readValue((String.class.getClassLoader())));
        this.idStatusPekerja = ((String) in.readValue((String.class.getClassLoader())));
        this.namaStatusPekerja = ((String) in.readValue((String.class.getClassLoader())));
        this.ketStatusPekerja = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public DatumStatusPekerja() {
    }

    /**
     * 
     * @param ketStatusPekerja
     * @param createdDate
     * @param updatedBy
     * @param createdBy
     * @param idStatusPekerja
     * @param updatedDate
     * @param namaStatusPekerja
     */
    public DatumStatusPekerja(String createdBy, String createdDate, String updatedBy, String updatedDate, String idStatusPekerja, String namaStatusPekerja, String ketStatusPekerja) {
        super();
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
        this.idStatusPekerja = idStatusPekerja;
        this.namaStatusPekerja = namaStatusPekerja;
        this.ketStatusPekerja = ketStatusPekerja;
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

    public String getIdStatusPekerja() {
        return idStatusPekerja;
    }

    public void setIdStatusPekerja(String idStatusPekerja) {
        this.idStatusPekerja = idStatusPekerja;
    }

    public String getNamaStatusPekerja() {
        return namaStatusPekerja;
    }

    public void setNamaStatusPekerja(String namaStatusPekerja) {
        this.namaStatusPekerja = namaStatusPekerja;
    }

    public String getKetStatusPekerja() {
        return ketStatusPekerja;
    }

    public void setKetStatusPekerja(String ketStatusPekerja) {
        this.ketStatusPekerja = ketStatusPekerja;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(DatumStatusPekerja.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        sb.append("idStatusPekerja");
        sb.append('=');
        sb.append(((this.idStatusPekerja == null)?"<null>":this.idStatusPekerja));
        sb.append(',');
        sb.append("namaStatusPekerja");
        sb.append('=');
        sb.append(((this.namaStatusPekerja == null)?"<null>":this.namaStatusPekerja));
        sb.append(',');
        sb.append("ketStatusPekerja");
        sb.append('=');
        sb.append(((this.ketStatusPekerja == null)?"<null>":this.ketStatusPekerja));
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
        dest.writeValue(idStatusPekerja);
        dest.writeValue(namaStatusPekerja);
        dest.writeValue(ketStatusPekerja);
    }

    public int describeContents() {
        return  0;
    }

}
