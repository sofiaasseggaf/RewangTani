
package com.rewangTani.rewangtani.model.modelakunprofil;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataProfilById implements Parcelable
{

    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("message")
    @Expose
    private String message;
    public final static Creator<DataProfilById> CREATOR = new Creator<DataProfilById>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DataProfilById createFromParcel(android.os.Parcel in) {
            return new DataProfilById(in);
        }

        public DataProfilById[] newArray(int size) {
            return (new DataProfilById[size]);
        }

    }
    ;

    protected DataProfilById(android.os.Parcel in) {
        this.data = ((Data) in.readValue((Data.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public DataProfilById() {
    }

    /**
     * 
     * @param data
     * @param message
     */
    public DataProfilById(Data data, String message) {
        super();
        this.data = data;
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(DataProfilById.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("data");
        sb.append('=');
        sb.append(((this.data == null)?"<null>":this.data));
        sb.append(',');
        sb.append("message");
        sb.append('=');
        sb.append(((this.message == null)?"<null>":this.message));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(data);
        dest.writeValue(message);
    }

    public int describeContents() {
        return  0;
    }

}
