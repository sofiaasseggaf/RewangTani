package com.rewangTani.rewangtani.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

public class PreferenceUtils extends AppCompatActivity {


    public PreferenceUtils() {
    }

    // --------------- DATA AKUN ---------------

    public static boolean saveIdAkun(String idakun, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_ID_AKUN, idakun);
        prefsEditor.apply();
        return true;
    }
    public static String getIdAkun(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_ID_AKUN, "");
    }
    public static boolean saveIDGoogle(String idgoogle, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_ID_GOOGLE, idgoogle);
        prefsEditor.apply();
        return true;
    }
    public static String getIDGoogle(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_ID_GOOGLE, "");
    }
    public static boolean saveIdProfil(String idprofile, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_ID_PROFIL, idprofile);
        prefsEditor.apply();
        return true;
    }
    public static String getIdProfil(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_ID_PROFIL, "");
    }

    public static boolean saveIdAlamat(String idalamat, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_ID_ALAMAT, idalamat);
        prefsEditor.apply();
        return true;
    }
    public static String getIdAlamat(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_ID_ALAMAT, "");
    }

    public static boolean savePassword(String password, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_PASSWORD, password);
        prefsEditor.apply();
        return true;
    }

    public static String getPassword(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_PASSWORD, "");
    }
    public static boolean saveNamaDepan(String namadepan, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_NAMA_DEPAN, namadepan);
        prefsEditor.apply();
        return true;
    }
    public static String getNamaDepan(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_NAMA_DEPAN, "");
    }
    public static boolean saveNamaBelakang(String namabelakang, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_NAMA_BELAKANG, namabelakang);
        prefsEditor.apply();
        return true;
    }
    public static String getNamaBelakang(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_NAMA_BELAKANG, "");
    }
    public static boolean saveUsername(String username, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_USERNAME, username);
        prefsEditor.apply();
        return true;
    }
    public static String getUsername(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_USERNAME, "");
    }
    public static boolean saveIDPhoto(String idphoto, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_ID_PHOTO, idphoto);
        prefsEditor.apply();
        return true;
    }
    public static String getIDPhoto(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_ID_PHOTO, "");
    }


    public static boolean saveIdRt(String idrt, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_ID_RT, idrt);
        prefsEditor.apply();
        return true;
    }
    public static String getIdRt(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_ID_RT, "");
    }

    public static boolean saveOIdRt(String oidrt, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_O_ID_RT, oidrt);
        prefsEditor.apply();
        return true;
    }
    public static String getOIdRt(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_O_ID_RT, "");
    }

    public static boolean saveToken(String token, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_TOKEN, token);
        prefsEditor.apply();
        return true;
    }

    public static String getToken(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_TOKEN, "");
    }

    public static boolean saveTitle(String title, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_TITLE, title);
        prefsEditor.apply();
        return true;
    }

    public static String getTitle(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_TITLE, "");
    }



    // --------------- DATA PROFIL LAHAN SEMENTARA ---------------


//        jsonParams.put("idUser", PreferenceUtils.getIdAkun(getApplicationContext()));
//        jsonParams.put("idAlamat", idAlamat);
//        jsonParams.put("latitude", lat2); // gak boleh kosong
//        jsonParams.put("longitude", longt2); // gak boleh kosong
//        jsonParams.put("luasGarapan", et_luasgarapan.getText().toString());
//        jsonParams.put("idSistemIrigasi", idSistemIrigasi);
//        jsonParams.put("kemiringanTanah", et_kemiringantanah.getText().toString());
//        jsonParams.put("phTanah", ph);
//        jsonParams.put("namaProfilTanah", et_namaprofillahan.getText().toString());

    public static boolean savePLidAlamat(String idAlamat, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_PL_ID_ALAMAT, idAlamat);
        prefsEditor.apply();
        return true;
    }

    public static String getPLidAlamat(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_PL_ID_ALAMAT, "");
    }

    public static boolean savePLlatitude(String lat, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_PL_LAT, lat);
        prefsEditor.apply();
        return true;
    }

    public static String getPLlatitude(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_PL_LAT, "");
    }

    public static boolean savePLlongitude(String longt, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_PL_LONG, longt);
        prefsEditor.apply();
        return true;
    }

    public static String getPLlongitude(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_PL_LONG, "");
    }

    public static boolean savePLnamaProfilLahan(String namaPL, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_PL_NAMA_PL, namaPL);
        prefsEditor.apply();
        return true;
    }

    public static String getPLnamaProfilLahan(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_PL_NAMA_PL, "");
    }

    public static boolean savePLProvinsi(String provinsi, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_PL_PROVINSI, provinsi);
        prefsEditor.apply();
        return true;
    }

    public static String getPLProvinsi(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_PL_PROVINSI, "");
    }

    public static boolean savePLKabupaten(String kabupaten, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_PL_KABUPATEN, kabupaten);
        prefsEditor.apply();
        return true;
    }

    public static String getPLKabupaten(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_PL_KABUPATEN, "");
    }

    public static boolean savePLKecamatan(String kecamatan, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_PL_KECAMATAN, kecamatan);
        prefsEditor.apply();
        return true;
    }

    public static String getPLKecamatan(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_PL_KECAMATAN, "");
    }

    public static boolean savePLKelurahan(String kelurahan, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_PL_KELURAHAN, kelurahan);
        prefsEditor.apply();
        return true;
    }

    public static String getPLKelurahan(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_PL_KELURAHAN, "");
    }

    public static boolean savePLKodepos(String kodepos, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_PL_KODEPOS, kodepos);
        prefsEditor.apply();
        return true;
    }

    public static String getPLKodepos(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_PL_KODEPOS, "");
    }



    // --------------- DATA RENCANA TANAM SEMENTARA ---------------


//        jsonParams.put("idBiayaBuruhTanam", txt_buruh_tanam.getText().toString().replaceAll("[^0-9]", ""));
//        jsonParams.put("idBiayaBuruhBajak", txt_buruh_bajak.getText().toString().replaceAll("[^0-9]", ""));
//        jsonParams.put("idBiayaBuruhSemprot", txt_buruh_semprot.getText().toString().replaceAll("[^0-9]", ""));
//        jsonParams.put("idBiayaBuruhMenyiangirumput", txt_buruh_menyiangi.getText().toString().replaceAll("[^0-9]", ""));
//        jsonParams.put("idBiayaBuruhGalangan", txt_buruh_galengan.getText().toString().replaceAll("[^0-9]", ""));
//        jsonParams.put("idBiayaBuruhPupuk", txt_buruh_pupuk.getText().toString().replaceAll("[^0-9]", ""));
//        jsonParams.put("idBiayaBuruhPanen", txt_buruh_panen.getText().toString().replaceAll("[^0-9]", ""));
//        jsonParams.put("idSewaMesinBajak", txt_mesin_bajak.getText().toString().replaceAll("[^0-9]", ""));
//        jsonParams.put("idSewaMesinTanam", txt_mesin_tanam.getText().toString().replaceAll("[^0-9]", ""));
//        jsonParams.put("idSewaMesinPanen", txt_mesin_panen.getText().toString().replaceAll("[^0-9]", ""));
//        jsonParams.put("idBiayabibitLocalHet", txt_bibit_local.getText().toString().replaceAll("[^0-9]", ""));
//        jsonParams.put("idBiayabibitSubsidi", txt_bibit_subsidi.getText().toString().replaceAll("[^0-9]", ""));
//    //jsonParams.put("idBiayapupukKimiaLocalHet", txt_pupuk_kimia_local.getText().toString().replaceAll("[^0-9]", ""));
//        jsonParams.put("idBiayapupukKimiaLocalHet", "0");
//        jsonParams.put("idBiayapupukKimiaPhonska", txt_pupuk_kimia_phonska.getText().toString().replaceAll("[^0-9]", ""));
//        jsonParams.put("idBiayapupukOrganik", txt_pupuk_organik.getText().toString().replaceAll("[^0-9]", ""));
//        jsonParams.put("namaRencanaTanam", et_nama_rt.getText().toString());
//        jsonParams.put("idBiayapupukKimiaUrea", txt_pupuk_kimia_urea.getText().toString().replaceAll("[^0-9]", ""));
//        jsonParams.put("idBiayapupukKimiaFosfat", txt_pupuk_kimia_fosfat.getText().toString().replaceAll("[^0-9]", ""));
//        jsonParams.put("idSewamesinPompa", txt_pompa);
//    //jsonParams.put("idSewaMesinPompa", txt_mesin_pompa.getText().toString().replaceAll("[^0-9]", ""));
//        jsonParams.put("idSewamesinPompaBbm", txt_pompabbm);
//    //jsonParams.put("idSewaMesinPompaBbm", txt_mesin_pompa_bbm.getText().toString().replaceAll("[^0-9]", ""));

    public static boolean saveRTidProfilTanah(String idProfilTanah, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_RT_ID_PL, idProfilTanah);
        prefsEditor.apply();
        return true;
    }

    public static String getRTidProfilTanah(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_RT_ID_PL, "");
    }

    public static boolean saveRTnamaRT(String namaRT, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_PL_NAMA_RT, namaRT);
        prefsEditor.apply();
        return true;
    }

    public static String getRTnamaRT(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_PL_NAMA_RT, "");
    }

    public static boolean saveRTidKomoditas(String idKomoditas, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_RT_ID_KOMODITAS, idKomoditas);
        prefsEditor.apply();
        return true;
    }

    public static String getRTidKomoditas(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_RT_ID_KOMODITAS, "");
    }

    public static boolean saveRTidVarietas(String idVarietas, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_RT_ID_VARIETAS, idVarietas);
        prefsEditor.apply();
        return true;
    }

    public static String getRTidVarietas(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_RT_ID_VARIETAS, "");
    }

    public static boolean saveRTBuruhTanam(String buruhtanam, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_RT_ID_BURUH_TANAM, buruhtanam);
        prefsEditor.apply();
        return true;
    }

    public static String getRTBuruhTanam(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_RT_ID_BURUH_TANAM, "");
    }

    // ----------------- IMAGE BITMAP ---------------

    /*

    // method for bitmap to base64
    public static String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }

    public static boolean savePhotoSiswa(String namaimage, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.NAMA_IMAGE_SISWA, namaimage);
        //prefsEditor.putString(Constants.KEY_IMAGE_SISWA, encodeTobase64(image));
        prefsEditor.commit();
        return true;
    }

    public static String getPhotoSiswa(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_IMAGE_SISWA, null);
    }

    // method for base64 to bitmap (decode base64)
    public static Bitmap getPhotoSiswa(Context context) {
        byte[] decodedByte = Base64.decode(Constants.KEY_IMAGE_SISWA, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public static boolean savePhotoGuru(String namaimage, Bitmap image, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.NAMA_IMAGE_GURU, namaimage);
        prefsEditor.putString(Constants.KEY_IMAGE_GURU, encodeTobase64(image));
        prefsEditor.commit();
        return true;
    }

    // method for base64 to bitmap (decode base64)
    public static Bitmap getPhotoGuru(Context context) {
        byte[] decodedByte = Base64.decode(Constants.KEY_IMAGE_GURU, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    */


}
