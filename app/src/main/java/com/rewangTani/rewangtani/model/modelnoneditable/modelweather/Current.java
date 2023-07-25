
package com.rewangTani.rewangtani.model.modelnoneditable.modelweather;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Current implements Parcelable
{

    @SerializedName("last_updated_epoch")
    @Expose
    private Integer lastUpdatedEpoch;
    @SerializedName("last_updated")
    @Expose
    private String lastUpdated;
    @SerializedName("temp_c")
    @Expose
    private Double tempC;
    @SerializedName("temp_f")
    @Expose
    private Double tempF;
    @SerializedName("is_day")
    @Expose
    private Integer isDay;
    @SerializedName("condition")
    @Expose
    private Condition condition;
    @SerializedName("wind_mph")
    @Expose
    private Double windMph;
    @SerializedName("wind_kph")
    @Expose
    private Double windKph;
    @SerializedName("wind_degree")
    @Expose
    private Integer windDegree;
    @SerializedName("wind_dir")
    @Expose
    private String windDir;
    @SerializedName("pressure_mb")
    @Expose
    private Double pressureMb;
    @SerializedName("pressure_in")
    @Expose
    private Double pressureIn;
    @SerializedName("precip_mm")
    @Expose
    private Double precipMm;
    @SerializedName("precip_in")
    @Expose
    private Double precipIn;
    @SerializedName("humidity")
    @Expose
    private Integer humidity;
    @SerializedName("cloud")
    @Expose
    private Integer cloud;
    @SerializedName("feelslike_c")
    @Expose
    private Double feelslikeC;
    @SerializedName("feelslike_f")
    @Expose
    private Double feelslikeF;
    @SerializedName("vis_km")
    @Expose
    private Double visKm;
    @SerializedName("vis_miles")
    @Expose
    private Double visMiles;
    @SerializedName("uv")
    @Expose
    private Double uv;
    @SerializedName("gust_mph")
    @Expose
    private Double gustMph;
    @SerializedName("gust_kph")
    @Expose
    private Double gustKph;
    public final static Creator<Current> CREATOR = new Creator<Current>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Current createFromParcel(android.os.Parcel in) {
            return new Current(in);
        }

        public Current[] newArray(int size) {
            return (new Current[size]);
        }

    }
    ;

    protected Current(android.os.Parcel in) {
        this.lastUpdatedEpoch = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.lastUpdated = ((String) in.readValue((String.class.getClassLoader())));
        this.tempC = ((Double) in.readValue((Double.class.getClassLoader())));
        this.tempF = ((Double) in.readValue((Double.class.getClassLoader())));
        this.isDay = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.condition = ((Condition) in.readValue((Condition.class.getClassLoader())));
        this.windMph = ((Double) in.readValue((Double.class.getClassLoader())));
        this.windKph = ((Double) in.readValue((Double.class.getClassLoader())));
        this.windDegree = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.windDir = ((String) in.readValue((String.class.getClassLoader())));
        this.pressureMb = ((Double) in.readValue((Double.class.getClassLoader())));
        this.pressureIn = ((Double) in.readValue((Double.class.getClassLoader())));
        this.precipMm = ((Double) in.readValue((Double.class.getClassLoader())));
        this.precipIn = ((Double) in.readValue((Double.class.getClassLoader())));
        this.humidity = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.cloud = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.feelslikeC = ((Double) in.readValue((Double.class.getClassLoader())));
        this.feelslikeF = ((Double) in.readValue((Double.class.getClassLoader())));
        this.visKm = ((Double) in.readValue((Double.class.getClassLoader())));
        this.visMiles = ((Double) in.readValue((Double.class.getClassLoader())));
        this.uv = ((Double) in.readValue((Double.class.getClassLoader())));
        this.gustMph = ((Double) in.readValue((Double.class.getClassLoader())));
        this.gustKph = ((Double) in.readValue((Double.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Current() {
    }

    /**
     * 
     * @param tempF
     * @param precipMm
     * @param uv
     * @param feelslikeC
     * @param gustMph
     * @param gustKph
     * @param windDir
     * @param pressureIn
     * @param precipIn
     * @param isDay
     * @param cloud
     * @param lastUpdated
     * @param condition
     * @param windMph
     * @param visKm
     * @param windKph
     * @param humidity
     * @param feelslikeF
     * @param windDegree
     * @param visMiles
     * @param pressureMb
     * @param lastUpdatedEpoch
     * @param tempC
     */
    public Current(Integer lastUpdatedEpoch, String lastUpdated, Double tempC, Double tempF, Integer isDay, Condition condition, Double windMph, Double windKph, Integer windDegree, String windDir, Double pressureMb, Double pressureIn, Double precipMm, Double precipIn, Integer humidity, Integer cloud, Double feelslikeC, Double feelslikeF, Double visKm, Double visMiles, Double uv, Double gustMph, Double gustKph) {
        super();
        this.lastUpdatedEpoch = lastUpdatedEpoch;
        this.lastUpdated = lastUpdated;
        this.tempC = tempC;
        this.tempF = tempF;
        this.isDay = isDay;
        this.condition = condition;
        this.windMph = windMph;
        this.windKph = windKph;
        this.windDegree = windDegree;
        this.windDir = windDir;
        this.pressureMb = pressureMb;
        this.pressureIn = pressureIn;
        this.precipMm = precipMm;
        this.precipIn = precipIn;
        this.humidity = humidity;
        this.cloud = cloud;
        this.feelslikeC = feelslikeC;
        this.feelslikeF = feelslikeF;
        this.visKm = visKm;
        this.visMiles = visMiles;
        this.uv = uv;
        this.gustMph = gustMph;
        this.gustKph = gustKph;
    }

    public Integer getLastUpdatedEpoch() {
        return lastUpdatedEpoch;
    }

    public void setLastUpdatedEpoch(Integer lastUpdatedEpoch) {
        this.lastUpdatedEpoch = lastUpdatedEpoch;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Double getTempC() {
        return tempC;
    }

    public void setTempC(Double tempC) {
        this.tempC = tempC;
    }

    public Double getTempF() {
        return tempF;
    }

    public void setTempF(Double tempF) {
        this.tempF = tempF;
    }

    public Integer getIsDay() {
        return isDay;
    }

    public void setIsDay(Integer isDay) {
        this.isDay = isDay;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Double getWindMph() {
        return windMph;
    }

    public void setWindMph(Double windMph) {
        this.windMph = windMph;
    }

    public Double getWindKph() {
        return windKph;
    }

    public void setWindKph(Double windKph) {
        this.windKph = windKph;
    }

    public Integer getWindDegree() {
        return windDegree;
    }

    public void setWindDegree(Integer windDegree) {
        this.windDegree = windDegree;
    }

    public String getWindDir() {
        return windDir;
    }

    public void setWindDir(String windDir) {
        this.windDir = windDir;
    }

    public Double getPressureMb() {
        return pressureMb;
    }

    public void setPressureMb(Double pressureMb) {
        this.pressureMb = pressureMb;
    }

    public Double getPressureIn() {
        return pressureIn;
    }

    public void setPressureIn(Double pressureIn) {
        this.pressureIn = pressureIn;
    }

    public Double getPrecipMm() {
        return precipMm;
    }

    public void setPrecipMm(Double precipMm) {
        this.precipMm = precipMm;
    }

    public Double getPrecipIn() {
        return precipIn;
    }

    public void setPrecipIn(Double precipIn) {
        this.precipIn = precipIn;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Integer getCloud() {
        return cloud;
    }

    public void setCloud(Integer cloud) {
        this.cloud = cloud;
    }

    public Double getFeelslikeC() {
        return feelslikeC;
    }

    public void setFeelslikeC(Double feelslikeC) {
        this.feelslikeC = feelslikeC;
    }

    public Double getFeelslikeF() {
        return feelslikeF;
    }

    public void setFeelslikeF(Double feelslikeF) {
        this.feelslikeF = feelslikeF;
    }

    public Double getVisKm() {
        return visKm;
    }

    public void setVisKm(Double visKm) {
        this.visKm = visKm;
    }

    public Double getVisMiles() {
        return visMiles;
    }

    public void setVisMiles(Double visMiles) {
        this.visMiles = visMiles;
    }

    public Double getUv() {
        return uv;
    }

    public void setUv(Double uv) {
        this.uv = uv;
    }

    public Double getGustMph() {
        return gustMph;
    }

    public void setGustMph(Double gustMph) {
        this.gustMph = gustMph;
    }

    public Double getGustKph() {
        return gustKph;
    }

    public void setGustKph(Double gustKph) {
        this.gustKph = gustKph;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Current.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("lastUpdatedEpoch");
        sb.append('=');
        sb.append(((this.lastUpdatedEpoch == null)?"<null>":this.lastUpdatedEpoch));
        sb.append(',');
        sb.append("lastUpdated");
        sb.append('=');
        sb.append(((this.lastUpdated == null)?"<null>":this.lastUpdated));
        sb.append(',');
        sb.append("tempC");
        sb.append('=');
        sb.append(((this.tempC == null)?"<null>":this.tempC));
        sb.append(',');
        sb.append("tempF");
        sb.append('=');
        sb.append(((this.tempF == null)?"<null>":this.tempF));
        sb.append(',');
        sb.append("isDay");
        sb.append('=');
        sb.append(((this.isDay == null)?"<null>":this.isDay));
        sb.append(',');
        sb.append("condition");
        sb.append('=');
        sb.append(((this.condition == null)?"<null>":this.condition));
        sb.append(',');
        sb.append("windMph");
        sb.append('=');
        sb.append(((this.windMph == null)?"<null>":this.windMph));
        sb.append(',');
        sb.append("windKph");
        sb.append('=');
        sb.append(((this.windKph == null)?"<null>":this.windKph));
        sb.append(',');
        sb.append("windDegree");
        sb.append('=');
        sb.append(((this.windDegree == null)?"<null>":this.windDegree));
        sb.append(',');
        sb.append("windDir");
        sb.append('=');
        sb.append(((this.windDir == null)?"<null>":this.windDir));
        sb.append(',');
        sb.append("pressureMb");
        sb.append('=');
        sb.append(((this.pressureMb == null)?"<null>":this.pressureMb));
        sb.append(',');
        sb.append("pressureIn");
        sb.append('=');
        sb.append(((this.pressureIn == null)?"<null>":this.pressureIn));
        sb.append(',');
        sb.append("precipMm");
        sb.append('=');
        sb.append(((this.precipMm == null)?"<null>":this.precipMm));
        sb.append(',');
        sb.append("precipIn");
        sb.append('=');
        sb.append(((this.precipIn == null)?"<null>":this.precipIn));
        sb.append(',');
        sb.append("humidity");
        sb.append('=');
        sb.append(((this.humidity == null)?"<null>":this.humidity));
        sb.append(',');
        sb.append("cloud");
        sb.append('=');
        sb.append(((this.cloud == null)?"<null>":this.cloud));
        sb.append(',');
        sb.append("feelslikeC");
        sb.append('=');
        sb.append(((this.feelslikeC == null)?"<null>":this.feelslikeC));
        sb.append(',');
        sb.append("feelslikeF");
        sb.append('=');
        sb.append(((this.feelslikeF == null)?"<null>":this.feelslikeF));
        sb.append(',');
        sb.append("visKm");
        sb.append('=');
        sb.append(((this.visKm == null)?"<null>":this.visKm));
        sb.append(',');
        sb.append("visMiles");
        sb.append('=');
        sb.append(((this.visMiles == null)?"<null>":this.visMiles));
        sb.append(',');
        sb.append("uv");
        sb.append('=');
        sb.append(((this.uv == null)?"<null>":this.uv));
        sb.append(',');
        sb.append("gustMph");
        sb.append('=');
        sb.append(((this.gustMph == null)?"<null>":this.gustMph));
        sb.append(',');
        sb.append("gustKph");
        sb.append('=');
        sb.append(((this.gustKph == null)?"<null>":this.gustKph));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(lastUpdatedEpoch);
        dest.writeValue(lastUpdated);
        dest.writeValue(tempC);
        dest.writeValue(tempF);
        dest.writeValue(isDay);
        dest.writeValue(condition);
        dest.writeValue(windMph);
        dest.writeValue(windKph);
        dest.writeValue(windDegree);
        dest.writeValue(windDir);
        dest.writeValue(pressureMb);
        dest.writeValue(pressureIn);
        dest.writeValue(precipMm);
        dest.writeValue(precipIn);
        dest.writeValue(humidity);
        dest.writeValue(cloud);
        dest.writeValue(feelslikeC);
        dest.writeValue(feelslikeF);
        dest.writeValue(visKm);
        dest.writeValue(visMiles);
        dest.writeValue(uv);
        dest.writeValue(gustMph);
        dest.writeValue(gustKph);
    }

    public int describeContents() {
        return  0;
    }

}
