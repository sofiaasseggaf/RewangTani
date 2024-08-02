
package com.rewangTani.rewangtani.model.modelchatdaninbox.modelchat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ModelChat implements Serializable
{

    @SerializedName("data")
    @Expose
    private List<DatumChat> data;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("totalData")
    @Expose
    private Integer totalData;
    private final static long serialVersionUID = 3102421400193766431L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ModelChat() {
    }

    /**
     * 
     * @param totalData
     * @param data
     * @param message
     */
    public ModelChat(List<DatumChat> data, String message, Integer totalData) {
        super();
        this.data = data;
        this.message = message;
        this.totalData = totalData;
    }

    public List<DatumChat> getData() {
        return data;
    }

    public void setData(List<DatumChat> data) {
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
