
package com.rewangTani.rewangtani.model.modelinfo;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatumInfo implements Parcelable
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
    @SerializedName("idInfo")
    @Expose
    private String idInfo;
    @SerializedName("idAkun")
    @Expose
    private String idAkun;
    @SerializedName("namaSumber")
    @Expose
    private String namaSumber;
    @SerializedName("judulInfo")
    @Expose
    private String judulInfo;
    @SerializedName("ketInfo")
    @Expose
    private String ketInfo;
    public final static Creator<DatumInfo> CREATOR = new Creator<DatumInfo>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DatumInfo createFromParcel(android.os.Parcel in) {
            return new DatumInfo(in);
        }

        public DatumInfo[] newArray(int size) {
            return (new DatumInfo[size]);
        }

    }
    ;

    protected DatumInfo(android.os.Parcel in) {
        this.createdBy = ((String) in.readValue((String.class.getClassLoader())));
        this.createdDate = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedBy = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedDate = ((String) in.readValue((String.class.getClassLoader())));
        this.idInfo = ((String) in.readValue((String.class.getClassLoader())));
        this.idAkun = ((String) in.readValue((String.class.getClassLoader())));
        this.namaSumber = ((String) in.readValue((String.class.getClassLoader())));
        this.judulInfo = ((String) in.readValue((String.class.getClassLoader())));
        this.ketInfo = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public DatumInfo() {
    }

    /**
     * 
     * @param ketInfo
     * @param createdDate
     * @param updatedBy
     * @param idAkun
     * @param namaSumber
     * @param judulInfo
     * @param createdBy
     * @param idInfo
     * @param updatedDate
     */
    public DatumInfo(String createdBy, String createdDate, String updatedBy, String updatedDate, String idInfo, String idAkun, String namaSumber, String judulInfo, String ketInfo) {
        super();
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
        this.idInfo = idInfo;
        this.idAkun = idAkun;
        this.namaSumber = namaSumber;
        this.judulInfo = judulInfo;
        this.ketInfo = ketInfo;
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

    public String getIdInfo() {
        return idInfo;
    }

    public void setIdInfo(String idInfo) {
        this.idInfo = idInfo;
    }

    public String getIdAkun() {
        return idAkun;
    }

    public void setIdAkun(String idAkun) {
        this.idAkun = idAkun;
    }

    public String getNamaSumber() {
        return namaSumber;
    }

    public void setNamaSumber(String namaSumber) {
        this.namaSumber = namaSumber;
    }

    public String getJudulInfo() {
        return judulInfo;
    }

    public void setJudulInfo(String judulInfo) {
        this.judulInfo = judulInfo;
    }

    public String getKetInfo() {
        return ketInfo;
    }

    public void setKetInfo(String ketInfo) {
        this.ketInfo = ketInfo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(DatumInfo.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        sb.append("idInfo");
        sb.append('=');
        sb.append(((this.idInfo == null)?"<null>":this.idInfo));
        sb.append(',');
        sb.append("idAkun");
        sb.append('=');
        sb.append(((this.idAkun == null)?"<null>":this.idAkun));
        sb.append(',');
        sb.append("namaSumber");
        sb.append('=');
        sb.append(((this.namaSumber == null)?"<null>":this.namaSumber));
        sb.append(',');
        sb.append("judulInfo");
        sb.append('=');
        sb.append(((this.judulInfo == null)?"<null>":this.judulInfo));
        sb.append(',');
        sb.append("ketInfo");
        sb.append('=');
        sb.append(((this.ketInfo == null)?"<null>":this.ketInfo));
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
        dest.writeValue(idInfo);
        dest.writeValue(idAkun);
        dest.writeValue(namaSumber);
        dest.writeValue(judulInfo);
        dest.writeValue(ketInfo);
    }

    public int describeContents() {
        return  0;
    }

}
