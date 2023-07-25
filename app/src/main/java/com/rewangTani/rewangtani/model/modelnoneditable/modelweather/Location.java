
package com.rewangTani.rewangtani.model.modelnoneditable.modelweather;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Location implements Parcelable
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lon")
    @Expose
    private Double lon;
    @SerializedName("tz_id")
    @Expose
    private String tzId;
    @SerializedName("localtime_epoch")
    @Expose
    private Integer localtimeEpoch;
    @SerializedName("localtime")
    @Expose
    private String localtime;
    public final static Creator<Location> CREATOR = new Creator<Location>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Location createFromParcel(android.os.Parcel in) {
            return new Location(in);
        }

        public Location[] newArray(int size) {
            return (new Location[size]);
        }

    }
    ;

    protected Location(android.os.Parcel in) {
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.region = ((String) in.readValue((String.class.getClassLoader())));
        this.country = ((String) in.readValue((String.class.getClassLoader())));
        this.lat = ((Double) in.readValue((Double.class.getClassLoader())));
        this.lon = ((Double) in.readValue((Double.class.getClassLoader())));
        this.tzId = ((String) in.readValue((String.class.getClassLoader())));
        this.localtimeEpoch = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.localtime = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Location() {
    }

    /**
     * 
     * @param localtime
     * @param country
     * @param tzId
     * @param localtimeEpoch
     * @param name
     * @param lon
     * @param region
     * @param lat
     */
    public Location(String name, String region, String country, Double lat, Double lon, String tzId, Integer localtimeEpoch, String localtime) {
        super();
        this.name = name;
        this.region = region;
        this.country = country;
        this.lat = lat;
        this.lon = lon;
        this.tzId = tzId;
        this.localtimeEpoch = localtimeEpoch;
        this.localtime = localtime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getTzId() {
        return tzId;
    }

    public void setTzId(String tzId) {
        this.tzId = tzId;
    }

    public Integer getLocaltimeEpoch() {
        return localtimeEpoch;
    }

    public void setLocaltimeEpoch(Integer localtimeEpoch) {
        this.localtimeEpoch = localtimeEpoch;
    }

    public String getLocaltime() {
        return localtime;
    }

    public void setLocaltime(String localtime) {
        this.localtime = localtime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Location.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("region");
        sb.append('=');
        sb.append(((this.region == null)?"<null>":this.region));
        sb.append(',');
        sb.append("country");
        sb.append('=');
        sb.append(((this.country == null)?"<null>":this.country));
        sb.append(',');
        sb.append("lat");
        sb.append('=');
        sb.append(((this.lat == null)?"<null>":this.lat));
        sb.append(',');
        sb.append("lon");
        sb.append('=');
        sb.append(((this.lon == null)?"<null>":this.lon));
        sb.append(',');
        sb.append("tzId");
        sb.append('=');
        sb.append(((this.tzId == null)?"<null>":this.tzId));
        sb.append(',');
        sb.append("localtimeEpoch");
        sb.append('=');
        sb.append(((this.localtimeEpoch == null)?"<null>":this.localtimeEpoch));
        sb.append(',');
        sb.append("localtime");
        sb.append('=');
        sb.append(((this.localtime == null)?"<null>":this.localtime));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(region);
        dest.writeValue(country);
        dest.writeValue(lat);
        dest.writeValue(lon);
        dest.writeValue(tzId);
        dest.writeValue(localtimeEpoch);
        dest.writeValue(localtime);
    }

    public int describeContents() {
        return  0;
    }

}
