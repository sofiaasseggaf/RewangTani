
package com.rewangTani.rewangtani.model.modelchatdaninbox.modelinboxparticipant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DatumInboxParticipant implements Serializable
{

    @SerializedName("createdBy")
    @Expose
    private String createdBy;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("updatedBy")
    @Expose
    private String updatedBy;
    @SerializedName("updatedDate")
    @Expose
    private String updatedDate;
    @SerializedName("idInboxParticipant")
    @Expose
    private String idInboxParticipant;
    @SerializedName("idProfilA")
    @Expose
    private String idProfilA;
    @SerializedName("idProfilB")
    @Expose
    private String idProfilB;
    private final static long serialVersionUID = 8826045814093483884L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DatumInboxParticipant() {
    }

    /**
     * 
     * @param createdDate
     * @param updatedBy
     * @param idProfilA
     * @param createdBy
     * @param idProfilB
     * @param updatedDate
     * @param idInboxParticipant
     */
    public DatumInboxParticipant(String createdBy, String createdDate, String updatedBy, String updatedDate, String idInboxParticipant, String idProfilA, String idProfilB) {
        super();
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
        this.idInboxParticipant = idInboxParticipant;
        this.idProfilA = idProfilA;
        this.idProfilB = idProfilB;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getIdInboxParticipant() {
        return idInboxParticipant;
    }

    public void setIdInboxParticipant(String idInboxParticipant) {
        this.idInboxParticipant = idInboxParticipant;
    }

    public String getIdProfilA() {
        return idProfilA;
    }

    public void setIdProfilA(String idProfilA) {
        this.idProfilA = idProfilA;
    }

    public String getIdProfilB() {
        return idProfilB;
    }

    public void setIdProfilB(String idProfilB) {
        this.idProfilB = idProfilB;
    }

}
