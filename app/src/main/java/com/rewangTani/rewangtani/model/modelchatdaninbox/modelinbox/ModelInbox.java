
package com.rewangTani.rewangtani.model.modelchatdaninbox.modelinbox;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelInbox implements Serializable
{

    @SerializedName("data")
    @Expose
    private List<DatumInbox> data;
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
    public ModelInbox() {
    }

    /**
     * 
     * @param totalData
     * @param data
     * @param message
     */
    public ModelInbox(List<DatumInbox> data, String message, Integer totalData) {
        super();
        this.data = data;
        this.message = message;
        this.totalData = totalData;
    }

    public List<DatumInbox> getData() {
        return data;
    }

    public void setData(List<DatumInbox> data) {
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
