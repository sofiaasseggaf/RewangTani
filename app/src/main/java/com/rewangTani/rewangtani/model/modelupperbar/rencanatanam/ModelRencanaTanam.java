
package com.rewangTani.rewangtani.model.modelupperbar.rencanatanam;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelRencanaTanam implements Parcelable
{

    @SerializedName("data")
    @Expose
    private List<DatumRencanaTanam> data = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("totalData")
    @Expose
    private Integer totalData;
    public final static Creator<ModelRencanaTanam> CREATOR = new Creator<ModelRencanaTanam>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ModelRencanaTanam createFromParcel(android.os.Parcel in) {
            return new ModelRencanaTanam(in);
        }

        public ModelRencanaTanam[] newArray(int size) {
            return (new ModelRencanaTanam[size]);
        }

    }
    ;

    protected ModelRencanaTanam(android.os.Parcel in) {
        in.readList(this.data, (DatumRencanaTanam.class.getClassLoader()));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.totalData = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public ModelRencanaTanam() {
    }

    /**
     * 
     * @param totalData
     * @param data
     * @param message
     */
    public ModelRencanaTanam(List<DatumRencanaTanam> data, String message, Integer totalData) {
        super();
        this.data = data;
        this.message = message;
        this.totalData = totalData;
    }

    public List<DatumRencanaTanam> getData() {
        return data;
    }

    public void setData(List<DatumRencanaTanam> data) {
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
        sb.append(ModelRencanaTanam.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
