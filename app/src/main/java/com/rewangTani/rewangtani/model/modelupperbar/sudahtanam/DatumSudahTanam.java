
package com.rewangTani.rewangtani.model.modelupperbar.sudahtanam;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatumSudahTanam implements Parcelable
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
    @SerializedName("idSudahTanam")
    @Expose
    private String idSudahTanam;
    @SerializedName("idRencanaTanam")
    @Expose
    private String idRencanaTanam;
    @SerializedName("idPertumbuhanNormal")
    @Expose
    private String idPertumbuhanNormal;
    @SerializedName("idKendalaPertumbuhan")
    @Expose
    private String idKendalaPertumbuhan;
    @SerializedName("idBiayaburuhTanam")
    @Expose
    private String idBiayaburuhTanam;
    @SerializedName("idBiayaburuhBajak")
    @Expose
    private String idBiayaburuhBajak;
    @SerializedName("idBiayaburuhSemprot")
    @Expose
    private String idBiayaburuhSemprot;
    @SerializedName("idBiayaburuhMenyiangirumput")
    @Expose
    private String idBiayaburuhMenyiangirumput;
    @SerializedName("idBiayaburuhGalangan")
    @Expose
    private String idBiayaburuhGalangan;
    @SerializedName("idBiayaburuhPupuk")
    @Expose
    private String idBiayaburuhPupuk;
    @SerializedName("idBiayaburuhPanen")
    @Expose
    private String idBiayaburuhPanen;
    @SerializedName("idSewamesinBajak")
    @Expose
    private String idSewamesinBajak;
    @SerializedName("idSewamesinTanam")
    @Expose
    private String idSewamesinTanam;
    @SerializedName("idSewamesinPanen")
    @Expose
    private String idSewamesinPanen;
    @SerializedName("idSewamesinPompa")
    @Expose
    private String idSewamesinPompa;
    @SerializedName("idSewamesinPompaBbm")
    @Expose
    private String idSewamesinPompaBbm;
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
    @SerializedName("idBiayapupukKimiaUrea")
    @Expose
    private String idBiayapupukKimiaUrea;
    @SerializedName("idBiayapupukKimiaFosfat")
    @Expose
    private String idBiayapupukKimiaFosfat;
    @SerializedName("idBiayapupukOrganik")
    @Expose
    private String idBiayapupukOrganik;
    @SerializedName("idObatKimiaLocal")
    @Expose
    private String idObatKimiaLocal;
    @SerializedName("idObatKimiaSubsidi")
    @Expose
    private String idObatKimiaSubsidi;
    @SerializedName("idObatOrganik")
    @Expose
    private String idObatOrganik;
    @SerializedName("idBiayaobatKimiaLocalHet")
    @Expose
    private String idBiayaobatKimiaLocalHet;
    @SerializedName("idBiayaobatKimiaSubsidi")
    @Expose
    private String idBiayaobatKimiaSubsidi;
    @SerializedName("idBiayaobatOrganik")
    @Expose
    private String idBiayaobatOrganik;
    private boolean withPompa;
    String idProfilLahan, namaObatOrganik;

    public final static Creator<DatumSudahTanam> CREATOR = new Creator<DatumSudahTanam>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DatumSudahTanam createFromParcel(android.os.Parcel in) {
            return new DatumSudahTanam(in);
        }

        public DatumSudahTanam[] newArray(int size) {
            return (new DatumSudahTanam[size]);
        }

    }
    ;

    protected DatumSudahTanam(android.os.Parcel in) {
        this.createdBy = ((String) in.readValue((String.class.getClassLoader())));
        this.createdDate = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedBy = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedDate = ((String) in.readValue((String.class.getClassLoader())));
        this.idSudahTanam = ((String) in.readValue((String.class.getClassLoader())));
        this.idRencanaTanam = ((String) in.readValue((String.class.getClassLoader())));
        this.idPertumbuhanNormal = ((String) in.readValue((String.class.getClassLoader())));
        this.idKendalaPertumbuhan = ((String) in.readValue((String.class.getClassLoader())));
        this.idBiayaburuhTanam = ((String) in.readValue((String.class.getClassLoader())));
        this.idBiayaburuhBajak = ((String) in.readValue((String.class.getClassLoader())));
        this.idBiayaburuhSemprot = ((String) in.readValue((String.class.getClassLoader())));
        this.idBiayaburuhMenyiangirumput = ((String) in.readValue((String.class.getClassLoader())));
        this.idBiayaburuhGalangan = ((String) in.readValue((String.class.getClassLoader())));
        this.idBiayaburuhPupuk = ((String) in.readValue((String.class.getClassLoader())));
        this.idBiayaburuhPanen = ((String) in.readValue((String.class.getClassLoader())));
        this.idSewamesinBajak = ((String) in.readValue((String.class.getClassLoader())));
        this.idSewamesinTanam = ((String) in.readValue((String.class.getClassLoader())));
        this.idSewamesinPanen = ((String) in.readValue((String.class.getClassLoader())));
        this.idSewamesinPompa = ((String) in.readValue((String.class.getClassLoader())));
        this.idSewamesinPompaBbm = ((String) in.readValue((String.class.getClassLoader())));
        this.idBiayabibitLocalHet = ((String) in.readValue((String.class.getClassLoader())));
        this.idBiayabibitSubsidi = ((String) in.readValue((String.class.getClassLoader())));
        this.idBiayapupukKimiaLocalHet = ((String) in.readValue((String.class.getClassLoader())));
        this.idBiayapupukKimiaPhonska = ((String) in.readValue((String.class.getClassLoader())));
        this.idBiayapupukKimiaUrea = ((String) in.readValue((String.class.getClassLoader())));
        this.idBiayapupukKimiaFosfat = ((String) in.readValue((String.class.getClassLoader())));
        this.idBiayapupukOrganik = ((String) in.readValue((String.class.getClassLoader())));
        this.idObatKimiaLocal = ((String) in.readValue((String.class.getClassLoader())));
        this.idObatKimiaSubsidi = ((String) in.readValue((String.class.getClassLoader())));
        this.idObatOrganik = ((String) in.readValue((String.class.getClassLoader())));
        this.idBiayaobatKimiaLocalHet = ((String) in.readValue((String.class.getClassLoader())));
        this.idBiayaobatKimiaSubsidi = ((String) in.readValue((String.class.getClassLoader())));
        this.idBiayaobatOrganik = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public DatumSudahTanam() {
    }

    /**
     * 
     * @param idObatKimiaLocal
     * @param idObatOrganik
     * @param idBiayaobatKimiaLocalHet
     * @param updatedDate
     * @param idPertumbuhanNormal
     * @param idSewamesinBajak
     * @param idBiayaobatKimiaSubsidi
     * @param idObatKimiaSubsidi
     * @param idBiayapupukKimiaPhonska
     * @param idBiayapupukKimiaUrea
     * @param idRencanaTanam
     * @param idBiayapupukKimiaLocalHet
     * @param idBiayaburuhTanam
     * @param idSudahTanam
     * @param idBiayaburuhPanen
     * @param updatedBy
     * @param idSewamesinPompaBbm
     * @param idBiayaburuhGalangan
     * @param idBiayaburuhMenyiangirumput
     * @param createdDate
     * @param idKendalaPertumbuhan
     * @param idBiayapupukKimiaFosfat
     * @param createdBy
     * @param idBiayaburuhBajak
     * @param idBiayapupukOrganik
     * @param idSewamesinTanam
     * @param idBiayaobatOrganik
     * @param idBiayaburuhPupuk
     * @param idBiayabibitSubsidi
     * @param idBiayaburuhSemprot
     * @param idSewamesinPanen
     * @param idSewamesinPompa
     * @param idBiayabibitLocalHet
     */
    public DatumSudahTanam(String createdBy, String createdDate, String updatedBy, String updatedDate, String idSudahTanam, String idRencanaTanam, String idPertumbuhanNormal, String idKendalaPertumbuhan, String idBiayaburuhTanam, String idBiayaburuhBajak, String idBiayaburuhSemprot, String idBiayaburuhMenyiangirumput, String idBiayaburuhGalangan, String idBiayaburuhPupuk, String idBiayaburuhPanen, String idSewamesinBajak, String idSewamesinTanam, String idSewamesinPanen, String idSewamesinPompa, String idSewamesinPompaBbm, String idBiayabibitLocalHet, String idBiayabibitSubsidi, String idBiayapupukKimiaLocalHet, String idBiayapupukKimiaPhonska, String idBiayapupukKimiaUrea, String idBiayapupukKimiaFosfat, String idBiayapupukOrganik, String idObatKimiaLocal, String idObatKimiaSubsidi, String idObatOrganik, String idBiayaobatKimiaLocalHet, String idBiayaobatKimiaSubsidi, String idBiayaobatOrganik) {
        super();
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
        this.idSudahTanam = idSudahTanam;
        this.idRencanaTanam = idRencanaTanam;
        this.idPertumbuhanNormal = idPertumbuhanNormal;
        this.idKendalaPertumbuhan = idKendalaPertumbuhan;
        this.idBiayaburuhTanam = idBiayaburuhTanam;
        this.idBiayaburuhBajak = idBiayaburuhBajak;
        this.idBiayaburuhSemprot = idBiayaburuhSemprot;
        this.idBiayaburuhMenyiangirumput = idBiayaburuhMenyiangirumput;
        this.idBiayaburuhGalangan = idBiayaburuhGalangan;
        this.idBiayaburuhPupuk = idBiayaburuhPupuk;
        this.idBiayaburuhPanen = idBiayaburuhPanen;
        this.idSewamesinBajak = idSewamesinBajak;
        this.idSewamesinTanam = idSewamesinTanam;
        this.idSewamesinPanen = idSewamesinPanen;
        this.idSewamesinPompa = idSewamesinPompa;
        this.idSewamesinPompaBbm = idSewamesinPompaBbm;
        this.idBiayabibitLocalHet = idBiayabibitLocalHet;
        this.idBiayabibitSubsidi = idBiayabibitSubsidi;
        this.idBiayapupukKimiaLocalHet = idBiayapupukKimiaLocalHet;
        this.idBiayapupukKimiaPhonska = idBiayapupukKimiaPhonska;
        this.idBiayapupukKimiaUrea = idBiayapupukKimiaUrea;
        this.idBiayapupukKimiaFosfat = idBiayapupukKimiaFosfat;
        this.idBiayapupukOrganik = idBiayapupukOrganik;
        this.idObatKimiaLocal = idObatKimiaLocal;
        this.idObatKimiaSubsidi = idObatKimiaSubsidi;
        this.idObatOrganik = idObatOrganik;
        this.idBiayaobatKimiaLocalHet = idBiayaobatKimiaLocalHet;
        this.idBiayaobatKimiaSubsidi = idBiayaobatKimiaSubsidi;
        this.idBiayaobatOrganik = idBiayaobatOrganik;
    }

    public DatumSudahTanam(String idRencanaTanam, String idProfilLahan, String idPertumbuhanNormal, String idKendalaPertumbuhan, String idBiayaburuhTanam, String idBiayaburuhBajak, String idBiayaburuhSemprot, String idBiayaburuhMenyiangirumput, String idBiayaburuhGalangan, String idBiayaburuhPupuk, String idBiayaburuhPanen, String idSewamesinBajak, String idSewamesinTanam, String idSewamesinPanen, String idSewamesinPompa, String idSewamesinPompaBbm, String idBiayabibitLocalHet, String idBiayabibitSubsidi, String idBiayapupukKimiaLocalHet, String idBiayapupukKimiaPhonska, String idBiayapupukKimiaUrea, String idBiayapupukKimiaFosfat, String idBiayapupukOrganik, String idObatKimiaLocal, String idObatKimiaSubsidi, String idObatOrganik, String idBiayaobatKimiaLocalHet, String idBiayaobatKimiaSubsidi, String idBiayaobatOrganik, boolean withPompa, String namaObatOrganik) {
        super();
        this.idRencanaTanam = idRencanaTanam;
        this.idProfilLahan = idProfilLahan;
        this.idPertumbuhanNormal = idPertumbuhanNormal;
        this.idKendalaPertumbuhan = idKendalaPertumbuhan;
        this.idBiayaburuhTanam = idBiayaburuhTanam;
        this.idBiayaburuhBajak = idBiayaburuhBajak;
        this.idBiayaburuhSemprot = idBiayaburuhSemprot;
        this.idBiayaburuhMenyiangirumput = idBiayaburuhMenyiangirumput;
        this.idBiayaburuhGalangan = idBiayaburuhGalangan;
        this.idBiayaburuhPupuk = idBiayaburuhPupuk;
        this.idBiayaburuhPanen = idBiayaburuhPanen;
        this.idSewamesinBajak = idSewamesinBajak;
        this.idSewamesinTanam = idSewamesinTanam;
        this.idSewamesinPanen = idSewamesinPanen;
        this.idSewamesinPompa = idSewamesinPompa;
        this.idSewamesinPompaBbm = idSewamesinPompaBbm;
        this.idBiayabibitLocalHet = idBiayabibitLocalHet;
        this.idBiayabibitSubsidi = idBiayabibitSubsidi;
        this.idBiayapupukKimiaLocalHet = idBiayapupukKimiaLocalHet;
        this.idBiayapupukKimiaPhonska = idBiayapupukKimiaPhonska;
        this.idBiayapupukKimiaUrea = idBiayapupukKimiaUrea;
        this.idBiayapupukKimiaFosfat = idBiayapupukKimiaFosfat;
        this.idBiayapupukOrganik = idBiayapupukOrganik;
        this.idObatKimiaLocal = idObatKimiaLocal;
        this.idObatKimiaSubsidi = idObatKimiaSubsidi;
        this.idObatOrganik = idObatOrganik;
        this.idBiayaobatKimiaLocalHet = idBiayaobatKimiaLocalHet;
        this.idBiayaobatKimiaSubsidi = idBiayaobatKimiaSubsidi;
        this.idBiayaobatOrganik = idBiayaobatOrganik;
        this.withPompa = withPompa;
        this.namaObatOrganik = namaObatOrganik;
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

    public String getIdSudahTanam() {
        return idSudahTanam;
    }

    public void setIdSudahTanam(String idSudahTanam) {
        this.idSudahTanam = idSudahTanam;
    }

    public String getIdRencanaTanam() {
        return idRencanaTanam;
    }

    public void setIdRencanaTanam(String idRencanaTanam) {
        this.idRencanaTanam = idRencanaTanam;
    }

    public String getIdPertumbuhanNormal() {
        return idPertumbuhanNormal;
    }

    public void setIdPertumbuhanNormal(String idPertumbuhanNormal) {
        this.idPertumbuhanNormal = idPertumbuhanNormal;
    }

    public String getIdKendalaPertumbuhan() {
        return idKendalaPertumbuhan;
    }

    public void setIdKendalaPertumbuhan(String idKendalaPertumbuhan) {
        this.idKendalaPertumbuhan = idKendalaPertumbuhan;
    }

    public String getIdBiayaburuhTanam() {
        return idBiayaburuhTanam;
    }

    public void setIdBiayaburuhTanam(String idBiayaburuhTanam) {
        this.idBiayaburuhTanam = idBiayaburuhTanam;
    }

    public String getIdBiayaburuhBajak() {
        return idBiayaburuhBajak;
    }

    public void setIdBiayaburuhBajak(String idBiayaburuhBajak) {
        this.idBiayaburuhBajak = idBiayaburuhBajak;
    }

    public String getIdBiayaburuhSemprot() {
        return idBiayaburuhSemprot;
    }

    public void setIdBiayaburuhSemprot(String idBiayaburuhSemprot) {
        this.idBiayaburuhSemprot = idBiayaburuhSemprot;
    }

    public String getIdBiayaburuhMenyiangirumput() {
        return idBiayaburuhMenyiangirumput;
    }

    public void setIdBiayaburuhMenyiangirumput(String idBiayaburuhMenyiangirumput) {
        this.idBiayaburuhMenyiangirumput = idBiayaburuhMenyiangirumput;
    }

    public String getIdBiayaburuhGalangan() {
        return idBiayaburuhGalangan;
    }

    public void setIdBiayaburuhGalangan(String idBiayaburuhGalangan) {
        this.idBiayaburuhGalangan = idBiayaburuhGalangan;
    }

    public String getIdBiayaburuhPupuk() {
        return idBiayaburuhPupuk;
    }

    public void setIdBiayaburuhPupuk(String idBiayaburuhPupuk) {
        this.idBiayaburuhPupuk = idBiayaburuhPupuk;
    }

    public String getIdBiayaburuhPanen() {
        return idBiayaburuhPanen;
    }

    public void setIdBiayaburuhPanen(String idBiayaburuhPanen) {
        this.idBiayaburuhPanen = idBiayaburuhPanen;
    }

    public String getIdSewamesinBajak() {
        return idSewamesinBajak;
    }

    public void setIdSewamesinBajak(String idSewamesinBajak) {
        this.idSewamesinBajak = idSewamesinBajak;
    }

    public String getIdSewamesinTanam() {
        return idSewamesinTanam;
    }

    public void setIdSewamesinTanam(String idSewamesinTanam) {
        this.idSewamesinTanam = idSewamesinTanam;
    }

    public String getIdSewamesinPanen() {
        return idSewamesinPanen;
    }

    public void setIdSewamesinPanen(String idSewamesinPanen) {
        this.idSewamesinPanen = idSewamesinPanen;
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

    public String getIdBiayapupukOrganik() {
        return idBiayapupukOrganik;
    }

    public void setIdBiayapupukOrganik(String idBiayapupukOrganik) {
        this.idBiayapupukOrganik = idBiayapupukOrganik;
    }

    public String getIdObatKimiaLocal() {
        return idObatKimiaLocal;
    }

    public void setIdObatKimiaLocal(String idObatKimiaLocal) {
        this.idObatKimiaLocal = idObatKimiaLocal;
    }

    public String getIdObatKimiaSubsidi() {
        return idObatKimiaSubsidi;
    }

    public void setIdObatKimiaSubsidi(String idObatKimiaSubsidi) {
        this.idObatKimiaSubsidi = idObatKimiaSubsidi;
    }

    public String getIdObatOrganik() {
        return idObatOrganik;
    }

    public void setIdObatOrganik(String idObatOrganik) {
        this.idObatOrganik = idObatOrganik;
    }

    public String getIdBiayaobatKimiaLocalHet() {
        return idBiayaobatKimiaLocalHet;
    }

    public void setIdBiayaobatKimiaLocalHet(String idBiayaobatKimiaLocalHet) {
        this.idBiayaobatKimiaLocalHet = idBiayaobatKimiaLocalHet;
    }

    public String getIdBiayaobatKimiaSubsidi() {
        return idBiayaobatKimiaSubsidi;
    }

    public void setIdBiayaobatKimiaSubsidi(String idBiayaobatKimiaSubsidi) {
        this.idBiayaobatKimiaSubsidi = idBiayaobatKimiaSubsidi;
    }

    public String getIdBiayaobatOrganik() {
        return idBiayaobatOrganik;
    }

    public void setIdBiayaobatOrganik(String idBiayaobatOrganik) {
        this.idBiayaobatOrganik = idBiayaobatOrganik;
    }

    public boolean isWithPompa() {
        return withPompa;
    }

    public void setWithPompa(boolean withPompa) {
        this.withPompa = withPompa;
    }

    public String getIdProfilLahan() {
        return idProfilLahan;
    }

    public void setIdProfilLahan(String idProfilLahan) {
        this.idProfilLahan = idProfilLahan;
    }

    public String getNamaObatOrganik() {
        return namaObatOrganik;
    }

    public void setNamaObatOrganik(String namaObatOrganik) {
        this.namaObatOrganik = namaObatOrganik;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(DatumSudahTanam.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
        sb.append("idSudahTanam");
        sb.append('=');
        sb.append(((this.idSudahTanam == null)?"<null>":this.idSudahTanam));
        sb.append(',');
        sb.append("idRencanaTanam");
        sb.append('=');
        sb.append(((this.idRencanaTanam == null)?"<null>":this.idRencanaTanam));
        sb.append(',');
        sb.append("idPertumbuhanNormal");
        sb.append('=');
        sb.append(((this.idPertumbuhanNormal == null)?"<null>":this.idPertumbuhanNormal));
        sb.append(',');
        sb.append("idKendalaPertumbuhan");
        sb.append('=');
        sb.append(((this.idKendalaPertumbuhan == null)?"<null>":this.idKendalaPertumbuhan));
        sb.append(',');
        sb.append("idBiayaburuhTanam");
        sb.append('=');
        sb.append(((this.idBiayaburuhTanam == null)?"<null>":this.idBiayaburuhTanam));
        sb.append(',');
        sb.append("idBiayaburuhBajak");
        sb.append('=');
        sb.append(((this.idBiayaburuhBajak == null)?"<null>":this.idBiayaburuhBajak));
        sb.append(',');
        sb.append("idBiayaburuhSemprot");
        sb.append('=');
        sb.append(((this.idBiayaburuhSemprot == null)?"<null>":this.idBiayaburuhSemprot));
        sb.append(',');
        sb.append("idBiayaburuhMenyiangirumput");
        sb.append('=');
        sb.append(((this.idBiayaburuhMenyiangirumput == null)?"<null>":this.idBiayaburuhMenyiangirumput));
        sb.append(',');
        sb.append("idBiayaburuhGalangan");
        sb.append('=');
        sb.append(((this.idBiayaburuhGalangan == null)?"<null>":this.idBiayaburuhGalangan));
        sb.append(',');
        sb.append("idBiayaburuhPupuk");
        sb.append('=');
        sb.append(((this.idBiayaburuhPupuk == null)?"<null>":this.idBiayaburuhPupuk));
        sb.append(',');
        sb.append("idBiayaburuhPanen");
        sb.append('=');
        sb.append(((this.idBiayaburuhPanen == null)?"<null>":this.idBiayaburuhPanen));
        sb.append(',');
        sb.append("idSewamesinBajak");
        sb.append('=');
        sb.append(((this.idSewamesinBajak == null)?"<null>":this.idSewamesinBajak));
        sb.append(',');
        sb.append("idSewamesinTanam");
        sb.append('=');
        sb.append(((this.idSewamesinTanam == null)?"<null>":this.idSewamesinTanam));
        sb.append(',');
        sb.append("idSewamesinPanen");
        sb.append('=');
        sb.append(((this.idSewamesinPanen == null)?"<null>":this.idSewamesinPanen));
        sb.append(',');
        sb.append("idSewamesinPompa");
        sb.append('=');
        sb.append(((this.idSewamesinPompa == null)?"<null>":this.idSewamesinPompa));
        sb.append(',');
        sb.append("idSewamesinPompaBbm");
        sb.append('=');
        sb.append(((this.idSewamesinPompaBbm == null)?"<null>":this.idSewamesinPompaBbm));
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
        sb.append("idBiayapupukKimiaUrea");
        sb.append('=');
        sb.append(((this.idBiayapupukKimiaUrea == null)?"<null>":this.idBiayapupukKimiaUrea));
        sb.append(',');
        sb.append("idBiayapupukKimiaFosfat");
        sb.append('=');
        sb.append(((this.idBiayapupukKimiaFosfat == null)?"<null>":this.idBiayapupukKimiaFosfat));
        sb.append(',');
        sb.append("idBiayapupukOrganik");
        sb.append('=');
        sb.append(((this.idBiayapupukOrganik == null)?"<null>":this.idBiayapupukOrganik));
        sb.append(',');
        sb.append("idObatKimiaLocal");
        sb.append('=');
        sb.append(((this.idObatKimiaLocal == null)?"<null>":this.idObatKimiaLocal));
        sb.append(',');
        sb.append("idObatKimiaSubsidi");
        sb.append('=');
        sb.append(((this.idObatKimiaSubsidi == null)?"<null>":this.idObatKimiaSubsidi));
        sb.append(',');
        sb.append("idObatOrganik");
        sb.append('=');
        sb.append(((this.idObatOrganik == null)?"<null>":this.idObatOrganik));
        sb.append(',');
        sb.append("idBiayaobatKimiaLocalHet");
        sb.append('=');
        sb.append(((this.idBiayaobatKimiaLocalHet == null)?"<null>":this.idBiayaobatKimiaLocalHet));
        sb.append(',');
        sb.append("idBiayaobatKimiaSubsidi");
        sb.append('=');
        sb.append(((this.idBiayaobatKimiaSubsidi == null)?"<null>":this.idBiayaobatKimiaSubsidi));
        sb.append(',');
        sb.append("idBiayaobatOrganik");
        sb.append('=');
        sb.append(((this.idBiayaobatOrganik == null)?"<null>":this.idBiayaobatOrganik));
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
        dest.writeValue(idSudahTanam);
        dest.writeValue(idRencanaTanam);
        dest.writeValue(idPertumbuhanNormal);
        dest.writeValue(idKendalaPertumbuhan);
        dest.writeValue(idBiayaburuhTanam);
        dest.writeValue(idBiayaburuhBajak);
        dest.writeValue(idBiayaburuhSemprot);
        dest.writeValue(idBiayaburuhMenyiangirumput);
        dest.writeValue(idBiayaburuhGalangan);
        dest.writeValue(idBiayaburuhPupuk);
        dest.writeValue(idBiayaburuhPanen);
        dest.writeValue(idSewamesinBajak);
        dest.writeValue(idSewamesinTanam);
        dest.writeValue(idSewamesinPanen);
        dest.writeValue(idSewamesinPompa);
        dest.writeValue(idSewamesinPompaBbm);
        dest.writeValue(idBiayabibitLocalHet);
        dest.writeValue(idBiayabibitSubsidi);
        dest.writeValue(idBiayapupukKimiaLocalHet);
        dest.writeValue(idBiayapupukKimiaPhonska);
        dest.writeValue(idBiayapupukKimiaUrea);
        dest.writeValue(idBiayapupukKimiaFosfat);
        dest.writeValue(idBiayapupukOrganik);
        dest.writeValue(idObatKimiaLocal);
        dest.writeValue(idObatKimiaSubsidi);
        dest.writeValue(idObatOrganik);
        dest.writeValue(idBiayaobatKimiaLocalHet);
        dest.writeValue(idBiayaobatKimiaSubsidi);
        dest.writeValue(idBiayaobatOrganik);
    }

    public int describeContents() {
        return  0;
    }

}
