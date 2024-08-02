
package com.rewangTani.rewangtani.model.modelinfo;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelResultNotification implements Parcelable
{

    @SerializedName("multicast_id")
    @Expose
    private Long multicastId;
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("failure")
    @Expose
    private Integer failure;
    @SerializedName("canonical_ids")
    @Expose
    private Integer canonicalIds;
    @SerializedName("results")
    @Expose
    private List<Result> results = null;
    public final static Creator<ModelResultNotification> CREATOR = new Creator<ModelResultNotification>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ModelResultNotification createFromParcel(android.os.Parcel in) {
            return new ModelResultNotification(in);
        }

        public ModelResultNotification[] newArray(int size) {
            return (new ModelResultNotification[size]);
        }

    }
    ;

    protected ModelResultNotification(android.os.Parcel in) {
        this.multicastId = ((Long) in.readValue((Long.class.getClassLoader())));
        this.success = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.failure = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.canonicalIds = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.results, (Result.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public ModelResultNotification() {
    }

    /**
     * 
     * @param multicastId
     * @param success
     * @param failure
     * @param canonicalIds
     * @param results
     */
    public ModelResultNotification(Long multicastId, Integer success, Integer failure, Integer canonicalIds, List<Result> results) {
        super();
        this.multicastId = multicastId;
        this.success = success;
        this.failure = failure;
        this.canonicalIds = canonicalIds;
        this.results = results;
    }

    public Long getMulticastId() {
        return multicastId;
    }

    public void setMulticastId(Long multicastId) {
        this.multicastId = multicastId;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Integer getFailure() {
        return failure;
    }

    public void setFailure(Integer failure) {
        this.failure = failure;
    }

    public Integer getCanonicalIds() {
        return canonicalIds;
    }

    public void setCanonicalIds(Integer canonicalIds) {
        this.canonicalIds = canonicalIds;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ModelResultNotification.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("multicastId");
        sb.append('=');
        sb.append(((this.multicastId == null)?"<null>":this.multicastId));
        sb.append(',');
        sb.append("success");
        sb.append('=');
        sb.append(((this.success == null)?"<null>":this.success));
        sb.append(',');
        sb.append("failure");
        sb.append('=');
        sb.append(((this.failure == null)?"<null>":this.failure));
        sb.append(',');
        sb.append("canonicalIds");
        sb.append('=');
        sb.append(((this.canonicalIds == null)?"<null>":this.canonicalIds));
        sb.append(',');
        sb.append("results");
        sb.append('=');
        sb.append(((this.results == null)?"<null>":this.results));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(multicastId);
        dest.writeValue(success);
        dest.writeValue(failure);
        dest.writeValue(canonicalIds);
        dest.writeList(results);
    }

    public int describeContents() {
        return  0;
    }

}
