
package com.rewangTani.rewangtani.model.modelchatdaninbox.modelinboxparticipant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ModelInboxParticipant implements Serializable
{

    @SerializedName("data")
    @Expose
    private List<DatumInboxParticipant> data;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("totalData")
    @Expose
    private Integer totalData;
    private final static long serialVersionUID = -1913176480993007925L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ModelInboxParticipant() {
    }

    /**
     * 
     * @param totalData
     * @param data
     * @param message
     */
    public ModelInboxParticipant(List<DatumInboxParticipant> data, String message, Integer totalData) {
        super();
        this.data = data;
        this.message = message;
        this.totalData = totalData;
    }

    public List<DatumInboxParticipant> getData() {
        return data;
    }

    public void setData(List<DatumInboxParticipant> data) {
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

}
