
package com.rewangTani.rewangtani.model.modelnoneditable.obat;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseSendObat implements Parcelable
{

    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("message")
    @Expose
    private String message;
    public final static Creator<ResponseSendObat> CREATOR = new Creator<ResponseSendObat>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ResponseSendObat createFromParcel(android.os.Parcel in) {
            return new ResponseSendObat(in);
        }

        public ResponseSendObat[] newArray(int size) {
            return (new ResponseSendObat[size]);
        }

    }
    ;

    protected ResponseSendObat(android.os.Parcel in) {
        this.data = ((Data) in.readValue((Data.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public ResponseSendObat() {
    }

    /**
     * 
     * @param data
     * @param message
     */
    public ResponseSendObat(Data data, String message) {
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
        sb.append(ResponseSendObat.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
