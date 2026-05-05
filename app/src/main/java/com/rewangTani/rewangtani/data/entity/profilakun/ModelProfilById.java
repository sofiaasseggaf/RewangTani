
package com.rewangTani.rewangtani.data.entity.profilakun;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelProfilById implements Parcelable
{

    @SerializedName("data")
    @Expose
    private DatumProfil profilById;
    @SerializedName("message")
    @Expose
    private String message;
    public final static Creator<ModelProfilById> CREATOR = new Creator<ModelProfilById>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ModelProfilById createFromParcel(android.os.Parcel in) {
            return new ModelProfilById(in);
        }

        public ModelProfilById[] newArray(int size) {
            return (new ModelProfilById[size]);
        }

    }
    ;

    protected ModelProfilById(android.os.Parcel in) {
        this.profilById = ((DatumProfil) in.readValue((DatumProfil.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public ModelProfilById() {
    }

    /**
     * 
     * @param profilById
     * @param message
     */
    public ModelProfilById(DatumProfil profilById, String message) {
        super();
        this.profilById = profilById;
        this.message = message;
    }

    public DatumProfil getData() {
        return profilById;
    }

    public void setData(DatumProfil profilById) {
        this.profilById = profilById;
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
        sb.append(ModelProfilById.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("data");
        sb.append('=');
        sb.append(((this.profilById == null)?"<null>":this.profilById));
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
        dest.writeValue(profilById);
        dest.writeValue(message);
    }

    public int describeContents() {
        return  0;
    }

}
