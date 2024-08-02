
package com.rewangTani.rewangtani.model.modelnoneditable.alamat;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelAlamat implements Parcelable
{

    @SerializedName("data")
    @Expose
    private List<DatumAlamat> data = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("totalData")
    @Expose
    private Integer totalData;
    public final static Creator<ModelAlamat> CREATOR = new Creator<ModelAlamat>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ModelAlamat createFromParcel(android.os.Parcel in) {
            return new ModelAlamat(in);
        }

        public ModelAlamat[] newArray(int size) {
            return (new ModelAlamat[size]);
        }

    }
    ;

    protected ModelAlamat(android.os.Parcel in) {
        in.readList(this.data, (DatumAlamat.class.getClassLoader()));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.totalData = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public ModelAlamat() {
    }

    /**
     * 
     * @param totalData
     * @param data
     * @param message
     */
    public ModelAlamat(List<DatumAlamat> data, String message, Integer totalData) {
        super();
        this.data = data;
        this.message = message;
        this.totalData = totalData;
    }

    public List<DatumAlamat> getData() {
        return data;
    }

    public void setData(List<DatumAlamat> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getTotalData() {
        return totalData;
    }

    public void setTotalData(Integer totalData) {
        this.totalData = totalData;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ModelAlamat.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("data");
        sb.append('=');
        sb.append(((this.data == null)?"<null>":this.data));
        sb.append(',');
        sb.append("message");
        sb.append('=');
        sb.append(((this.message == null)?"<null>":this.message));
        sb.append(',');
        sb.append("totalData");
        sb.append('=');
        sb.append(((this.totalData == null)?"<null>":this.totalData));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeList(data);
        dest.writeValue(message);
        dest.writeValue(totalData);
    }

    public int describeContents() {
        return  0;
    }

}
