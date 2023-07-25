
package com.rewangTani.rewangtani.model.modelnoneditable.hargaobat;

import java.util.List;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelHargaObat implements Parcelable
{

    @SerializedName("data")
    @Expose
    private List<DatumHargaObat> data = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("totalData")
    @Expose
    private Integer totalData;
    public final static Creator<ModelHargaObat> CREATOR = new Creator<ModelHargaObat>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ModelHargaObat createFromParcel(android.os.Parcel in) {
            return new ModelHargaObat(in);
        }

        public ModelHargaObat[] newArray(int size) {
            return (new ModelHargaObat[size]);
        }

    }
    ;

    protected ModelHargaObat(android.os.Parcel in) {
        in.readList(this.data, (DatumHargaObat.class.getClassLoader()));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.totalData = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public ModelHargaObat() {
    }

    /**
     * 
     * @param totalData
     * @param data
     * @param message
     */
    public ModelHargaObat(List<DatumHargaObat> data, String message, Integer totalData) {
        super();
        this.data = data;
        this.message = message;
        this.totalData = totalData;
    }

    public List<DatumHargaObat> getData() {
        return data;
    }

    public void setData(List<DatumHargaObat> data) {
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
        sb.append(ModelHargaObat.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
