
package com.rewangTani.rewangtani.model.modelupperbar.rencanatanam;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatumRencanaTanam implements Parcelable
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
    @SerializedName("idRencanaTanam")
    @Expose
    private String idRencanaTanam;
    @SerializedName("idUser")
    @Expose
    private String idUser;
    @SerializedName("idProfil")
    @Expose
    private String idProfil;
    @SerializedName("idProfilTanah")
    @Expose
    private String idProfilTanah;
    @SerializedName("idKomoditas")
    @Expose
    private String idKomoditas;
    @SerializedName("idVarietas")
    @Expose
    private String idVarietas;
    @SerializedName("idBiayaBuruhTanam")
    @Expose
    private String idBiayaBuruhTanam;
    @SerializedName("idBiayaBuruhBajak")
    @Expose
    private String idBiayaBuruhBajak;
    @SerializedName("idBiayaBuruhSemprot")
    @Expose
    private String idBiayaBuruhSemprot;
    @SerializedName("idBiayaBuruhMenyiangirumput")
    @Expose
    private String idBiayaBuruhMenyiangirumput;
    @SerializedName("idBiayaBuruhGalangan")
    @Expose
    private String idBiayaBuruhGalangan;
    @SerializedName("idBiayaBuruhPupuk")
    @Expose
    private String idBiayaBuruhPupuk;
    @SerializedName("idBiayaBuruhPanen")
    @Expose
    private String idBiayaBuruhPanen;
    @SerializedName("idSewaMesinBajak")
    @Expose
    private String idSewaMesinBajak;
    @SerializedName("idSewaMesinTanam")
    @Expose
    private String idSewaMesinTanam;
    @SerializedName("idSewaMesinPanen")
    @Expose
    private String idSewaMesinPanen;
    @SerializedName("idBiayabibitLocalHet")
    @Expose
    private String idBiayabibitLocalHet;
    @SerializedName("idBiayabibitSubsidi")
    @Expose
    private String idBiayabibitSubsidi;
    @SerializedName("idBiayapupukKimiaLocalHet")
    @Expose
    private String idBiayapupukKimiaLocalHet;
    @SerializedName("idBiayapupukKimiaPhonska")
    @Expose
    private String idBiayapupukKimiaPhonska;
    @SerializedName("idBiayapupukOrganik")
    @Expose
    private String idBiayapupukOrganik;
    @SerializedName("namaRencanaTanam")
    @Expose
    private String namaRencanaTanam;
    @SerializedName("idBiayapupukKimiaUrea")
    @Expose
    private String idBiayapupukKimiaUrea;
    @SerializedName("idBiayapupukKimiaFosfat")
    @Expose
    private String idBiayapupukKimiaFosfat;
    @SerializedName("idSewamesinPompa")
    @Expose
    private String idSewamesinPompa;
    @SerializedName("idSewamesinPompaBbm")
    @Expose
    private String idSewamesinPompaBbm;
    public final static Creator<DatumRencanaTanam> CREATOR = new Creator<DatumRencanaTanam>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DatumRencanaTanam createFromParcel(android.os.Parcel in) {
            return new DatumRencanaTanam(in);
        }

        public DatumRencanaTanam[] newArray(int size) {
            return (new DatumRencanaTanam[size]);
        }

    }
    ;

    protected DatumRencanaTanam(android.os.Parcel in) {
        this.createdBy = ((String) in.readValue((String.class.getClassLoader())));
        this.createdDate = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedBy = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedDate = ((String) in.readValue((String.class.getClassLoader())));
        this.idRencanaTanam = ((String) in.readValue((String.class.getClassLoader())));
        this.idUser = ((String) in.readValue((String.class.getClassLoader())));
        this.idProfil = ((String) in.readValue((String.class.getClassLoader())));
        this.idProfilTanah = ((String) in.readValue((String.class.getClassLoader())));
        this.idKomoditas = ((String) in.readValue((String.class.getClassLoader())));
        this.idVarietas = ((String) in.readValue((String.class.getClassLoader())));
        this.idBiayaBuruhTanam = ((String) in.readValue((String.class.getClassLoader())));
        this.idBiayaBuruhBajak = ((String) in.readValue((String.class.getClassLoader())));
        this.idBiayaBuruhSemprot = ((String) in.readValue((String.class.getClassLoader())));
        this.idBiayaBuruhMenyiangirumput = ((String) in.readValue((String.class.getClassLoader())));
        this.idBiayaBuruhGalangan = ((String) in.readValue((String.class.getClassLoader())));
        this.idBiayaBuruhPupuk = ((String) in.readValue((String.class.getClassLoader())));
        this.idBiayaBuruhPanen = ((String) in.readValue((String.class.getClassLoader())));
        this.idSewaMesinBajak = ((String) in.readValue((String.class.getClassLoader())));
        this.idSewaMesinTanam = ((String) in.readValue((String.class.getClassLoader())));
        this.idSewaMesinPanen = ((String) in.readValue((String.class.getClassLoader())));
        this.idBiayabibitLocalHet = ((String) in.readValue((String.class.getClassLoader())));
        this.idBiayabibitSubsidi = ((String) in.readValue((String.class.getClassLoader())));
        this.idBiayapupukKimiaLocalHet = ((String) in.readValue((String.class.getClassLoader())));
        this.idBiayapupukKimiaPhonska = ((String) in.readValue((String.class.getClassLoader())));
        this.idBiayapupukOrganik = ((String) in.readValue((String.class.getClassLoader())));
        this.namaRencanaTanam = ((String) in.readValue((String.class.getClassLoader())));
        this.idBiayapupukKimiaUrea = ((String) in.readValue((String.class.getClassLoader())));
        this.idBiayapupukKimiaFosfat = ((String) in.readValue((String.class.getClassLoader())));
        this.idSewamesinPompa = ((String) in.readValue((String.class.getClassLoader())));
        this.idSewamesinPompaBbm = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public DatumRencanaTanam() {
    }

    /**
     * 
     * @param idVarietas
     * @param idBiayaBuruhSemprot
     * @param idSewaMesinPanen
     * @param idBiayaBuruhPupuk
     * @param updatedDate
     * @param idBiayaBuruhTanam
     * @param idUser
     * @param idBiayapupukKimiaPhonska
     * @param idBiayapupukKimiaUrea
     * @param idRencanaTanam
     * @param idSewaMesinBajak
     * @param idBiayapupukKimiaLocalHet
     * @param updatedBy
     * @param idSewamesinPompaBbm
     * @param idProfilTanah
     * @param idSewaMesinTanam
     * @param idBiayaBuruhMenyiangirumput
     * @param idBiayaBuruhPanen
     * @param idBiayaBuruhGalangan
     * @param idKomoditas
     * @param createdDate
     * @param idBiayapupukKimiaFosfat
     * @param createdBy
     * @param idBiayapupukOrganik
     * @param namaRencanaTanam
     * @param idBiayaBuruhBajak
     * @param idBiayabibitSubsidi
     * @param idProfil
     * @param idBiayabibitLocalHet
     * @param idSewamesinPompa
     */
    public DatumRencanaTanam(String createdBy, String createdDate, String updatedBy, String updatedDate, String idRencanaTanam, String idUser, String idProfil, String idProfilTanah, String idKomoditas, String idVarietas, String idBiayaBuruhTanam, String idBiayaBuruhBajak, String idBiayaBuruhSemprot, String idBiayaBuruhMenyiangirumput, String idBiayaBuruhGalangan, String idBiayaBuruhPupuk, String idBiayaBuruhPanen, String idSewaMesinBajak, String idSewaMesinTanam, String idSewaMesinPanen, String idBiayabibitLocalHet, String idBiayabibitSubsidi, String idBiayapupukKimiaLocalHet, String idBiayapupukKimiaPhonska, String idBiayapupukOrganik, String namaRencanaTanam, String idBiayapupukKimiaUrea, String idBiayapupukKimiaFosfat, String idSewamesinPompa, String idSewamesinPompaBbm) {
        super();
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
        this.idRencanaTanam = idRencanaTanam;
        this.idUser = idUser;
        this.idProfil = idProfil;
        this.idProfilTanah = idProfilTanah;
        this.idKomoditas = idKomoditas;
        this.idVarietas = idVarietas;
        this.idBiayaBuruhTanam = idBiayaBuruhTanam;
        this.idBiayaBuruhBajak = idBiayaBuruhBajak;
        this.idBiayaBuruhSemprot = idBiayaBuruhSemprot;
        this.idBiayaBuruhMenyiangirumput = idBiayaBuruhMenyiangirumput;
        this.idBiayaBuruhGalangan = idBiayaBuruhGalangan;
        this.idBiayaBuruhPupuk = idBiayaBuruhPupuk;
        this.idBiayaBuruhPanen = idBiayaBuruhPanen;
        this.idSewaMesinBajak = idSewaMesinBajak;
        this.idSewaMesinTanam = idSewaMesinTanam;
        this.idSewaMesinPanen = idSewaMesinPanen;
        this.idBiayabibitLocalHet = idBiayabibitLocalHet;
        this.idBiayabibitSubsidi = idBiayabibitSubsidi;
        this.idBiayapupukKimiaLocalHet = idBiayapupukKimiaLocalHet;
        this.idBiayapupukKimiaPhonska = idBiayapupukKimiaPhonska;
        this.idBiayapupukOrganik = idBiayapupukOrganik;
        this.namaRencanaTanam = namaRencanaTanam;
        this.idBiayapupukKimiaUrea = idBiayapupukKimiaUrea;
        this.idBiayapupukKimiaFosfat = idBiayapupukKimiaFosfat;
        this.idSewamesinPompa = idSewamesinPompa;
        this.idSewamesinPompaBbm = idSewamesinPompaBbm;
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

    public String getIdRencanaTanam() {
        return idRencanaTanam;
    }

    public void setIdRencanaTanam(String idRencanaTanam) {
        this.idRencanaTanam = idRencanaTanam;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdProfil() {
        return idProfil;
    }

    public void setIdProfil(String idProfil) {
        this.idProfil = idProfil;
    }

    public String getIdProfilTanah() {
        return idProfilTanah;
    }

    public void setIdProfilTanah(String idProfilTanah) {
        this.idProfilTanah = idProfilTanah;
    }

    public String getIdKomoditas() {
        return idKomoditas;
    }

    public void setIdKomoditas(String idKomoditas) {
        this.idKomoditas = idKomoditas;
    }

    public String getIdVarietas() {
        return idVarietas;
    }

    public void setIdVarietas(String idVarietas) {
        this.idVarietas = idVarietas;
    }

    public String getIdBiayaBuruhTanam() {
        return idBiayaBuruhTanam;
    }

    public void setIdBiayaBuruhTanam(String idBiayaBuruhTanam) {
        this.idBiayaBuruhTanam = idBiayaBuruhTanam;
    }

    public String getIdBiayaBuruhBajak() {
        return idBiayaBuruhBajak;
    }

    public void setIdBiayaBuruhBajak(String idBiayaBuruhBajak) {
        this.idBiayaBuruhBajak = idBiayaBuruhBajak;
    }

    public String getIdBiayaBuruhSemprot() {
        return idBiayaBuruhSemprot;
    }

    public void setIdBiayaBuruhSemprot(String idBiayaBuruhSemprot) {
        this.idBiayaBuruhSemprot = idBiayaBuruhSemprot;
    }

    public String getIdBiayaBuruhMenyiangirumput() {
        return idBiayaBuruhMenyiangirumput;
    }

    public void setIdBiayaBuruhMenyiangirumput(String idBiayaBuruhMenyiangirumput) {
        this.idBiayaBuruhMenyiangirumput = idBiayaBuruhMenyiangirumput;
    }

    public String getIdBiayaBuruhGalangan() {
        return idBiayaBuruhGalangan;
    }

    public void setIdBiayaBuruhGalangan(String idBiayaBuruhGalangan) {
        this.idBiayaBuruhGalangan = idBiayaBuruhGalangan;
    }

    public String getIdBiayaBuruhPupuk() {
        return idBiayaBuruhPupuk;
    }

    public void setIdBiayaBuruhPupuk(String idBiayaBuruhPupuk) {
        this.idBiayaBuruhPupuk = idBiayaBuruhPupuk;
    }

    public String getIdBiayaBuruhPanen() {
        return idBiayaBuruhPanen;
    }

    public void setIdBiayaBuruhPanen(String idBiayaBuruhPanen) {
        this.idBiayaBuruhPanen = idBiayaBuruhPanen;
    }

    public String getIdSewaMesinBajak() {
        return idSewaMesinBajak;
    }

    public void setIdSewaMesinBajak(String idSewaMesinBajak) {
        this.idSewaMesinBajak = idSewaMesinBajak;
    }

    public String getIdSewaMesinTanam() {
        return idSewaMesinTanam;
    }

    public void setIdSewaMesinTanam(String idSewaMesinTanam) {
        this.idSewaMesinTanam = idSewaMesinTanam;
    }

    public String getIdSewaMesinPanen() {
        return idSewaMesinPanen;
    }

    public void setIdSewaMesinPanen(String idSewaMesinPanen) {
        this.idSewaMesinPanen = idSewaMesinPanen;
    }

    public String getIdBiayabibitLocalHet() {
        return idBiayabibitLocalHet;
    }

    public void setIdBiayabibitLocalHet(String idBiayabibitLocalHet) {
        this.idBiayabibitLocalHet = idBiayabibitLocalHet;
    }

    public String getIdBiayabibitSubsidi() {
        return idBiayabibitSubsidi;
    }

    public void setIdBiayabibitSubsidi(String idBiayabibitSubsidi) {
        this.idBiayabibitSubsidi = idBiayabibitSubsidi;
    }

    public String getIdBiayapupukKimiaLocalHet() {
        return idBiayapupukKimiaLocalHet;
    }

    public void setIdBiayapupukKimiaLocalHet(String idBiayapupukKimiaLocalHet) {
        this.idBiayapupukKimiaLocalHet = idBiayapupukKimiaLocalHet;
    }

    public String getIdBiayapupukKimiaPhonska() {
        return idBiayapupukKimiaPhonska;
    }

    public void setIdBiayapupukKimiaPhonska(String idBiayapupukKimiaPhonska) {
        this.idBiayapupukKimiaPhonska = idBiayapupukKimiaPhonska;
    }

    public String getIdBiayapupukOrganik() {
        return idBiayapupukOrganik;
    }

    public void setIdBiayapupukOrganik(String idBiayapupukOrganik) {
        this.idBiayapupukOrganik = idBiayapupukOrganik;
    }

    public String getNamaRencanaTanam() {
        return namaRencanaTanam;
    }

    public void setNamaRencanaTanam(String namaRencanaTanam) {
        this.namaRencanaTanam = namaRencanaTanam;
    }

    public String getIdBiayapupukKimiaUrea() {
        return idBiayapupukKimiaUrea;
    }

    public void setIdBiayapupukKimiaUrea(String idBiayapupukKimiaUrea) {
        this.idBiayapupukKimiaUrea = idBiayapupukKimiaUrea;
    }

    public String getIdBiayapupukKimiaFosfat() {
        return idBiayapupukKimiaFosfat;
    }

    public void setIdBiayapupukKimiaFosfat(String idBiayapupukKimiaFosfat) {
        this.idBiayapupukKimiaFosfat = idBiayapupukKimiaFosfat;
    }

    public String getIdSewamesinPompa() {
        return idSewamesinPompa;
    }

    public void setIdSewamesinPompa(String idSewamesinPompa) {
        this.idSewamesinPompa = idSewamesinPompa;
    }

    public String getIdSewamesinPompaBbm() {
        return idSewamesinPompaBbm;
    }

    public void setIdSewamesinPompaBbm(String idSewamesinPompaBbm) {
        this.idSewamesinPompaBbm = idSewamesinPompaBbm;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(DatumRencanaTanam.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("createdBy");
        sb.append('=');
        sb.append(((this.createdBy == null)?"<null>":this.createdBy));
        sb.append(',');
        sb.append("createdDate");
        sb.append('=');
        sb.append(((this.createdDate == null)?"<null>":this.createdDate));
        sb.append(',');
        sb.append("updatedBy");
        sb.append('=');
        sb.append(((this.updatedBy == null)?"<null>":this.updatedBy));
        sb.append(',');
        sb.append("updatedDate");
        sb.append('=');
        sb.append(((this.updatedDate == null)?"<null>":this.updatedDate));
        sb.append(',');
        sb.append("idRencanaTanam");
        sb.append('=');
        sb.append(((this.idRencanaTanam == null)?"<null>":this.idRencanaTanam));
        sb.append(',');
        sb.append("idUser");
        sb.append('=');
        sb.append(((this.idUser == null)?"<null>":this.idUser));
        sb.append(',');
        sb.append("idProfil");
        sb.append('=');
        sb.append(((this.idProfil == null)?"<null>":this.idProfil));
        sb.append(',');
        sb.append("idProfilTanah");
        sb.append('=');
        sb.append(((this.idProfilTanah == null)?"<null>":this.idProfilTanah));
        sb.append(',');
        sb.append("idKomoditas");
        sb.append('=');
        sb.append(((this.idKomoditas == null)?"<null>":this.idKomoditas));
        sb.append(',');
        sb.append("idVarietas");
        sb.append('=');
        sb.append(((this.idVarietas == null)?"<null>":this.idVarietas));
        sb.append(',');
        sb.append("idBiayaBuruhTanam");
        sb.append('=');
        sb.append(((this.idBiayaBuruhTanam == null)?"<null>":this.idBiayaBuruhTanam));
        sb.append(',');
        sb.append("idBiayaBuruhBajak");
        sb.append('=');
        sb.append(((this.idBiayaBuruhBajak == null)?"<null>":this.idBiayaBuruhBajak));
        sb.append(',');
        sb.append("idBiayaBuruhSemprot");
        sb.append('=');
        sb.append(((this.idBiayaBuruhSemprot == null)?"<null>":this.idBiayaBuruhSemprot));
        sb.append(',');
        sb.append("idBiayaBuruhMenyiangirumput");
        sb.append('=');
        sb.append(((this.idBiayaBuruhMenyiangirumput == null)?"<null>":this.idBiayaBuruhMenyiangirumput));
        sb.append(',');
        sb.append("idBiayaBuruhGalangan");
        sb.append('=');
        sb.append(((this.idBiayaBuruhGalangan == null)?"<null>":this.idBiayaBuruhGalangan));
        sb.append(',');
        sb.append("idBiayaBuruhPupuk");
        sb.append('=');
        sb.append(((this.idBiayaBuruhPupuk == null)?"<null>":this.idBiayaBuruhPupuk));
        sb.append(',');
        sb.append("idBiayaBuruhPanen");
        sb.append('=');
        sb.append(((this.idBiayaBuruhPanen == null)?"<null>":this.idBiayaBuruhPanen));
        sb.append(',');
        sb.append("idSewaMesinBajak");
        sb.append('=');
        sb.append(((this.idSewaMesinBajak == null)?"<null>":this.idSewaMesinBajak));
        sb.append(',');
        sb.append("idSewaMesinTanam");
        sb.append('=');
        sb.append(((this.idSewaMesinTanam == null)?"<null>":this.idSewaMesinTanam));
        sb.append(',');
        sb.append("idSewaMesinPanen");
        sb.append('=');
        sb.append(((this.idSewaMesinPanen == null)?"<null>":this.idSewaMesinPanen));
        sb.append(',');
        sb.append("idBiayabibitLocalHet");
        sb.append('=');
        sb.append(((this.idBiayabibitLocalHet == null)?"<null>":this.idBiayabibitLocalHet));
        sb.append(',');
        sb.append("idBiayabibitSubsidi");
        sb.append('=');
        sb.append(((this.idBiayabibitSubsidi == null)?"<null>":this.idBiayabibitSubsidi));
        sb.append(',');
        sb.append("idBiayapupukKimiaLocalHet");
        sb.append('=');
        sb.append(((this.idBiayapupukKimiaLocalHet == null)?"<null>":this.idBiayapupukKimiaLocalHet));
        sb.append(',');
        sb.append("idBiayapupukKimiaPhonska");
        sb.append('=');
        sb.append(((this.idBiayapupukKimiaPhonska == null)?"<null>":this.idBiayapupukKimiaPhonska));
        sb.append(',');
        sb.append("idBiayapupukOrganik");
        sb.append('=');
        sb.append(((this.idBiayapupukOrganik == null)?"<null>":this.idBiayapupukOrganik));
        sb.append(',');
        sb.append("namaRencanaTanam");
        sb.append('=');
        sb.append(((this.namaRencanaTanam == null)?"<null>":this.namaRencanaTanam));
        sb.append(',');
        sb.append("idBiayapupukKimiaUrea");
        sb.append('=');
        sb.append(((this.idBiayapupukKimiaUrea == null)?"<null>":this.idBiayapupukKimiaUrea));
        sb.append(',');
        sb.append("idBiayapupukKimiaFosfat");
        sb.append('=');
        sb.append(((this.idBiayapupukKimiaFosfat == null)?"<null>":this.idBiayapupukKimiaFosfat));
        sb.append(',');
        sb.append("idSewamesinPompa");
        sb.append('=');
        sb.append(((this.idSewamesinPompa == null)?"<null>":this.idSewamesinPompa));
        sb.append(',');
        sb.append("idSewamesinPompaBbm");
        sb.append('=');
        sb.append(((this.idSewamesinPompaBbm == null)?"<null>":this.idSewamesinPompaBbm));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(createdBy);
        dest.writeValue(createdDate);
        dest.writeValue(updatedBy);
        dest.writeValue(updatedDate);
        dest.writeValue(idRencanaTanam);
        dest.writeValue(idUser);
        dest.writeValue(idProfil);
        dest.writeValue(idProfilTanah);
        dest.writeValue(idKomoditas);
        dest.writeValue(idVarietas);
        dest.writeValue(idBiayaBuruhTanam);
        dest.writeValue(idBiayaBuruhBajak);
        dest.writeValue(idBiayaBuruhSemprot);
        dest.writeValue(idBiayaBuruhMenyiangirumput);
        dest.writeValue(idBiayaBuruhGalangan);
        dest.writeValue(idBiayaBuruhPupuk);
        dest.writeValue(idBiayaBuruhPanen);
        dest.writeValue(idSewaMesinBajak);
        dest.writeValue(idSewaMesinTanam);
        dest.writeValue(idSewaMesinPanen);
        dest.writeValue(idBiayabibitLocalHet);
        dest.writeValue(idBiayabibitSubsidi);
        dest.writeValue(idBiayapupukKimiaLocalHet);
        dest.writeValue(idBiayapupukKimiaPhonska);
        dest.writeValue(idBiayapupukOrganik);
        dest.writeValue(namaRencanaTanam);
        dest.writeValue(idBiayapupukKimiaUrea);
        dest.writeValue(idBiayapupukKimiaFosfat);
        dest.writeValue(idSewamesinPompa);
        dest.writeValue(idSewamesinPompaBbm);
    }

    public int describeContents() {
        return  0;
    }

}
