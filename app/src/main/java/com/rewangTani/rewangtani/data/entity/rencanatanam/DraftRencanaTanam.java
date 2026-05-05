package com.rewangTani.rewangtani.data.entity.rencanatanam;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "draft_rencana_tanam")
public class DraftRencanaTanam
{

    @PrimaryKey
    public int id = 1; // single draft per user
    public String idUser;
    public String idProfil;

    public String namaRencanaTanam;
    public String idProfilTanah;
    public String idKomoditas;
    public String idVarietas;

    public String idBiayaBuruhTanam;
    public String idBiayaBuruhBajak;
    public String idBiayaBuruhSemprot;
    public String idBiayaBuruhMenyiangirumput;
    public String idBiayaBuruhGalangan;
    public String idBiayaBuruhPupuk;
    public String idBiayaBuruhPanen;

    public String idSewaMesinBajak;
    public String idSewaMesinTanam;
    public String idSewaMesinPanen;

    public String idSewamesinPompa;
    public String idSewamesinPompaBbm;

    public String idBiayabibitLocalHet;
    public String idBiayabibitSubsidi;

    public String idBiayapupukKimiaLocalHet;
    public String idBiayapupukKimiaPhonska;
    public String idBiayapupukOrganik;
    public String idBiayapupukKimiaUrea;
    public String idBiayapupukKimiaFosfat;

    public boolean withPompa;

    public String luasLahan;
    public String potensiHasilVarietas;
}
