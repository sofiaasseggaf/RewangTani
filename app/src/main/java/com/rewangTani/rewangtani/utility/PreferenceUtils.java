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

    public static boolean savePLluasGarapan(String luasGarapan, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_PL_LUAS_GARAPAN, luasGarapan);
        prefsEditor.apply();
        return true;
    }

    public static String getPLluasGarapan(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_PL_LUAS_GARAPAN, "");
    }

    public static boolean savePLidSistemIrigasi(String idSistemIrigasi, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_PL_ID_SISTEM_IRIGASI, idSistemIrigasi);
        prefsEditor.apply();
        return true;
    }

    public static String getPLidSistemIrigasi(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_PL_ID_SISTEM_IRIGASI, "");
    }

    public static boolean savePLkemiringanTanah(String kemiringanTanah, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_PL_KEMIRINGAN_TANAh, kemiringanTanah);
        prefsEditor.apply();
        return true;
    }

    public static String getPLkemiringanTanah(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_PL_KEMIRINGAN_TANAh, "");
    }

    public static boolean savePLphTanah(String phTanah, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_PL_PH_TANAH, phTanah);
        prefsEditor.apply();
        return true;
    }

    public static String getPLphTanah(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_PL_PH_TANAH, "");
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
