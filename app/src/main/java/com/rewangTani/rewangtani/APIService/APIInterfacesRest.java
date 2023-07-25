package com.rewangTani.rewangtani.APIService;

/**
 * Created by user on 22/03/2022.
 */


import com.rewangTani.rewangtani.model.modelakun.ModelAkun;
import com.rewangTani.rewangtani.model.modelakunprofil.DataProfilById;
import com.rewangTani.rewangtani.model.modelakunprofil.DatumProfil;
import com.rewangTani.rewangtani.model.modelakunprofil.ModelProfilAkun;
import com.rewangTani.rewangtani.model.modelchatdaninbox.modelchat.ModelChat;
import com.rewangTani.rewangtani.model.modelchatdaninbox.modelinbox.DatumInbox;
import com.rewangTani.rewangtani.model.modelchatdaninbox.modelinbox.ModelInbox;
import com.rewangTani.rewangtani.model.modelchatdaninbox.modelinboxparticipant.ModelInboxParticipant;
import com.rewangTani.rewangtani.model.modelinfo.ModelInfo;
import com.rewangTani.rewangtani.model.modelinfo.ModelResultNotification;
import com.rewangTani.rewangtani.model.modelnoneditable.alamat.ModelAlamat;
import com.rewangTani.rewangtani.model.modelnoneditable.obat.ModelObat;
import com.rewangTani.rewangtani.model.modelnoneditable.obat.ResponseSendObat;
import com.rewangTani.rewangtani.model.modelnoneditable.statuspekerja.ModelStatusPekerja;
import com.rewangTani.rewangtani.model.modelnoneditable.tipeproduk.ModelTipeProduk;
import com.rewangTani.rewangtani.model.modelnoneditable.varietas.ModelVarietas;
import com.rewangTani.rewangtani.model.modelphoto.DataPhotoById;
import com.rewangTani.rewangtani.model.modelproduk.DataProdukById;
import com.rewangTani.rewangtani.model.modelproduk.ModelProduk;
import com.rewangTani.rewangtani.model.modelprofillahan.DataProfilLahanById;
import com.rewangTani.rewangtani.model.modelprofillahan.ModelProfilLahan;
import com.rewangTani.rewangtani.model.modelnoneditable.sistemirigasi.ModelSistemIrigasi;
import com.rewangTani.rewangtani.model.modelphoto.DatumPhoto;
import com.rewangTani.rewangtani.model.modelupperbar.kendalapertumbuhan.ModelKendalaPertumbuhan;
import com.rewangTani.rewangtani.model.modelupperbar.panen.ModelPanen;
import com.rewangTani.rewangtani.model.modelnoneditable.harga.ModelHarga;
import com.rewangTani.rewangtani.model.modelnoneditable.kategoriharga.ModelKategoriHarga;
import com.rewangTani.rewangtani.model.modelnoneditable.komoditas.ModelKomoditas;
import com.rewangTani.rewangtani.model.modelupperbar.outputrencanatanam.ModelOutputRencanaTanam;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.ModelRencanaTanam;
import com.rewangTani.rewangtani.model.modelnoneditable.subkategoriharga.ModelSubKategoriHarga;
import com.rewangTani.rewangtani.model.modelupperbar.rencanatanam.ResponseRencanaTanam;
import com.rewangTani.rewangtani.model.modelupperbar.sudahtanam.ModelSudahTanam;
import com.rewangTani.rewangtani.model.modelwarungwarung.modelpupukpestisida.DataBppById;
import com.rewangTani.rewangtani.model.modelwarungwarung.modelpupukpestisida.ModelPupukPestisida;
import com.rewangTani.rewangtani.model.modelwarungwarung.modelsewamesin.DataSewaMesinById;
import com.rewangTani.rewangtani.model.modelwarungwarung.modelsewamesin.ModelSewaMesin;
import com.rewangTani.rewangtani.model.modelwarungwarung.modeltenagakerja.DataTenagaKerjaById;
import com.rewangTani.rewangtani.model.modelwarungwarung.modeltenagakerja.ModelTenagaKerja;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;


public interface APIInterfacesRest {

    // -------------------------------------------- AKUN --------------------------------------------

    // get all data akun
    @GET("akun/read")
    Call<ModelAkun> getDataAkun();

    // create data akun
    @POST("akun/create")
    Call<ResponseBody> sendDataAkun(
            @Body RequestBody body
    );

    // update data akun
    @PUT("akun/update")
    Call<ResponseBody> updateDataAkun(
            @Body RequestBody body
    );

    @DELETE("akun/delete")
    Call<ModelAkun> deleteAkun(
            @Query("id") String id);

    // -------------------------------------------- PROFILE AKUN --------------------------------------------

    @GET("profile/read")
    Call<ModelProfilAkun> getDataProfilAkun();

    @GET("profile/read")
    Call<DataProfilById> getDatumProfilAkun(
            @Query("id") String id
    );

    @GET("profile/readbyid")
    Call<DatumProfil> getDatumProfilAkunById(
            @Query("id") String id
    );

    @POST("profile/create")
    Call<ResponseBody> sendDataProfilAkun(
            @Body RequestBody body
    );

    @PUT("profile/update")
    Call<ResponseBody> updateDataProfilAkun(
            @Body RequestBody body
    );

    @DELETE("profile/delete")
    Call<ModelProfilAkun> deleteProfilAkun(
            @Query("id") String id);


    // -------------------------------------------- PROFIL TANAH --------------------------------------------

    @GET("profile-tanah/read")
    Call<ModelProfilLahan> getDataProfilLahan();

    @GET("profile-tanah/read")
    Call<DataProfilLahanById> getDatumProfilLahan(
            @Query("id") String id
    );

    @POST("profile-tanah/create")
    Call<ResponseBody> sendDataProfilLahan(
            @Body RequestBody body
    );

    @PUT("profile-tanah/update")
    Call<ResponseBody> updateDataProfilLahan(
            @Body RequestBody body
    );

    @DELETE("profile-tanah/delete")
    Call<ModelProfilLahan> deleteProfilLahan(
            @Query("id") String id);


    // -------------------------------------------- RENCANA TANAM --------------------------------------------

    @GET("rencana-tanam/read")
    Call<ModelRencanaTanam> getDataRencanaTanam();

    @GET("rencana-tanam/readbyidprofil")
    Call<ModelRencanaTanam> getDataRencanaTanamByProfilId( @Query("idProfil") String idProfil);

    @POST("rencana-tanam/create")
    Call<ResponseRencanaTanam> sendDataRencanaTanam(
            @Body RequestBody body
    );

    @DELETE("rencana-tanam/delete")
    Call<ModelRencanaTanam> deleteRencanaTanam(
            @Query("id") String id);


    // -------------------------------------------- SUDAH TANAM --------------------------------------------

    @GET("sudah-tanam/read")
    Call<ModelSudahTanam> getDataSudahTanam();

    @GET("sudah-tanam/readbyrencanatanam")
    Call<ModelSudahTanam> getDataSudahTanamByRTid(@Query("idRencanaTanam") String idRencanaTanam);

    @POST("sudah-tanam/create")
    Call<ResponseBody> sendDataSudahTanam(
            @Body RequestBody body
    );

    @DELETE("sudah-tanam/delete")
    Call<ModelSudahTanam> deleteSudahTanam(
            @Query("id") String id);


    // -------------------------------------------- PANEN --------------------------------------------

    @GET("panen/read")
    Call<ModelPanen> getDataPanen();

    @POST("panen/create")
    Call<ResponseBody> sendDataPanen(
            @Body RequestBody body
    );

    @DELETE("panen/delete")
    Call<ModelPanen> deletePanen(
            @Query("id") String id);


    // -------------------------------------------- OUTPUT RENCANA TANAM --------------------------------------------

    @GET("output-rencana-tanam/read")
    Call<ModelOutputRencanaTanam> getDataOutputRT();

    @POST("output-rencana-tanam/create")
    Call<ResponseBody> sendDataOutputRT(
            @Body RequestBody body
    );

    @DELETE("output-rencana-tanam/delete")
    Call<ModelOutputRencanaTanam> deleteOutputRT(
            @Query("id") String id);


    // -------------------------------------------- HARGA --------------------------------------------

    @GET("harga/read")
    Call<ModelHarga> getDataHarga();

    @POST("harga/create")
    Call<ResponseBody> sendDataHarga(
            @Body RequestBody body
    );


    // -------------------------------------------- ALAMAT --------------------------------------------

    @GET("alamat/read")
    Call<ModelAlamat> getDataAlamat();


    // -------------------------------------------- STATUS PEKERJA --------------------------------------------

    @GET("status-pekerja/read")
    Call<ModelStatusPekerja> getStatusPekerja();


    // -------------------------------------------- KOMODITAS --------------------------------------------

    @GET("komoditas/read")
    Call<ModelKomoditas> getDataKomoditas();

    // -------------------------------------------- VARIETAS --------------------------------------------

    @GET("varietas/read")
    Call<ModelVarietas> getDataVarietas();

    // -------------------------------------------- SISTEM IRIGASI --------------------------------------------

    @GET("sistem-irigasi/read")
    Call<ModelSistemIrigasi> getDataSistemIrigasi();

    // -------------------------------------------- KATEGORI --------------------------------------------

    @GET("kategori/read")
    Call<ModelKategoriHarga> getDataKategoriHarga();

    // -------------------------------------------- SUB KATEGORI --------------------------------------------

    @GET("sub-kategori/read")
    Call<ModelSubKategoriHarga> getDataSubKategoriHarga();

    // ----------------------------------------------   OBAT  ----------------------------------------------

    @GET("obat/read")
    Call<ModelObat> getDataObat();

    @POST("obat/create")
    Call<ResponseSendObat> sendObat(
            @Body RequestBody body
    );

    // ----------------------------------------------  HARGA OBAT  ----------------------------------------------

    @POST("harga-obat/create")
    Call<ResponseBody> sendHargaObat(
            @Body RequestBody body
    );

    // -------------------------------------------- PHOTO --------------------------------------------

    @GET("photo/read")
    Call<DatumPhoto> getPhoto(
            @Query("id") String id
    );

    @POST("photo/create")
    Call<ResponseBody> sendPhoto(
            @Body RequestBody body
    );

    @POST("photo/create")
    Call<DataPhotoById> sendPhoto2(
            @Body RequestBody body
    );


    @DELETE("photo/delete")
    Call<DatumPhoto> deletePhoto(
            @Query("id") String id);


    // -------------------------------------------- PRODUK --------------------------------------------


    @GET("produk/read")
    Call<ModelProduk> getDataProduk();

    @GET("produk/read")
    Call<DataProdukById> getProdukById(
            @Query("id") String id
    );

    @POST("produk/create")
    Call<DataProdukById> sendProduk(
            @Body RequestBody body
    );

    @PUT("produk/update")
    Call<ResponseBody> updateDataProduk(
            @Body RequestBody body
    );

    @DELETE("produk/delete")
    Call<ModelProduk> deleteProduk(
            @Query("id") String id);



    // -------------------------------------------- TIPE PRODUK --------------------------------------------

    @GET("tipe-produk/read")
    Call<ModelTipeProduk> getDataTipeProduk();

    // -------------------------------------------- WARUNG TENAGA KERJA --------------------------------------------


    @GET("tenaga-kerja/read")
    Call<ModelTenagaKerja> getDataWarungTenagaKerja();

    @POST("tenaga-kerja/create")
    Call<DataTenagaKerjaById> sendTenagaKerja(
            @Body RequestBody body
    );

    @PUT("tenaga-kerja/update")
    Call<ResponseBody> updateDataTenagaKerja(
            @Body RequestBody body
    );

    @DELETE("tenaga-kerja/delete")
    Call<ModelTenagaKerja> deleteTenagaKerja(
            @Query("id") String id);


    // -------------------------------------------- WARUNG SEWA MESIN --------------------------------------------


    @GET("sewa-mesin/read")
    Call<ModelSewaMesin> getDataWarungSewaMesin();

    @POST("sewa-mesin/create")
    Call<DataSewaMesinById> sendSewaMesin(
            @Body RequestBody body
    );

    @PUT("sewa-mesin/update")
    Call<ResponseBody> updateDataSewaMesin(
            @Body RequestBody body
    );

    @DELETE("sewa-mesin/delete")
    Call<ModelSewaMesin> deleteSewaMesin(
            @Query("id") String id);


    // -------------------------------------------- WARUNG PUPUK PESTISIDA --------------------------------------------


    // warung bibit pupuk pestisida
    @GET("warung-bpp/read")
    Call<ModelPupukPestisida> getDataWarungBibitPupukPestisida();

    @POST("warung-bpp/create")
    Call<DataBppById> sendBibitPestisida(
            @Body RequestBody body
    );

    @PUT("warung-bpp/update")
    Call<ResponseBody> updateDataBibitPestisida(
            @Body RequestBody body
    );

    @DELETE("warung-bpp/delete")
    Call<ModelPupukPestisida> deleteBibitPestisida(
            @Query("id") String id);


    // -------------------------------------------------- KENDALA PERTUMBUHAN ---------------------------------------------------


    @GET("kendala-pertumbuhan/read")
    Call<ModelKendalaPertumbuhan> getDataKendalaPertumbuhan();

    @POST("kendala-pertumbuhan/create")
    Call<ResponseBody> sendKendalaPertumbuhan(
            @Body RequestBody body
    );


    // -------------------------------------------- INFO --------------------------------------------


    @GET("info/read")
    Call<ModelInfo> getDataInfo();

    @POST("info/create")
    Call<ResponseBody> sendInfo(
            @Body RequestBody body
    );

    @POST("fcm/send")
    Call<ModelResultNotification> sendNotification(
            @Body RequestBody body
    );


    // ---------------------------------------- INBOX PARTICIPANT --------------------------------------

    @GET("inboxparticipant/read")
    Call<ModelInboxParticipant> getDataInboxParticipant();

    @GET("inboxparticipant/readbyid")
    Call<DatumInbox> getDataInboxParticipantById(
            @Query("id") String id);

    @POST("inboxparticipant/create")
    Call<ResponseBody> sendInboxParticipant(
            @Body RequestBody body
    );

    @DELETE("inboxparticipant/delete")
    Call<ModelInboxParticipant> deleteInboxParticipant(
            @Query("id") String id);


    // -------------------------------------------- INBOX --------------------------------------------

    @GET("inbox/read")
    Call<ModelInbox> getDataInbox();

    @GET("inbox/readbyid")
    Call<DatumInbox> getDataInboxById(
            @Query("id") String id);

    @POST("inbox/create")
    Call<ResponseBody> sendInbox(
            @Body RequestBody body
    );

    @DELETE("inbox/delete")
    Call<ModelInbox> deleteInbox(
            @Query("id") String id);

    @PUT("inbox/update")
    Call<ResponseBody> updateInbox(
            @Body RequestBody body
    );

    @PUT("inbox/updatereadflag")
    Call<ResponseBody> updateReadFlagInbox(
            @Query("id") String id);


    // -------------------------------------------- CHAT --------------------------------------------

    @GET("chat/read")
    Call<ModelChat> getDataChat();

    @GET("chat/readbyidinbox")
    Call<ModelChat> getDataChatByIdInbox(
            @Query("id_inbox") String idInbox);

    @POST("chat/create")
    Call<ResponseBody> sendChat(
            @Body RequestBody body
    );

    @DELETE("chat/delete")
    Call<ModelChat> deleteChat(
            @Query("id") String id);


}