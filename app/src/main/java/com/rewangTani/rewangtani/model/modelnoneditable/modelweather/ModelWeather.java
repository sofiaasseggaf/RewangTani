
package com.rewangTani.rewangtani.model.modelnoneditable.modelweather;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelWeather implements Parcelable
{

    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("current")
    @Expose
    private Current current;
    public final static Creator<ModelWeather> CREATOR = new Creator<ModelWeather>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ModelWeather createFromParcel(android.os.Parcel in) {
            return new ModelWeather(in);
        }

        public ModelWeather[] newArray(int size) {
            return (new ModelWeather[size]);
        }

    }
    ;

    protected ModelWeather(android.os.Parcel in) {
        this.location = ((Location) in.readValue((Location.class.getClassLoader())));
        this.current = ((Current) in.readValue((Current.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public ModelWeather() {
    }

    /**
     * 
     * @param current
     * @param location
     */
    public ModelWeather(Location location, Current current) {
        super();
        this.location = location;
        this.current = current;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ModelWeather.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("location");
        sb.append('=');
        sb.append(((this.location == null)?"<null>":this.location));
        sb.append(',');
        sb.append("current");
        sb.append('=');
        sb.append(((this.current == null)?"<null>":this.current));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(location);
        dest.writeValue(current);
    }

    public int describeContents() {
        return  0;
    }

}
