package com.rewangTani.rewangtani.bottombar.profilakun;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.databinding.BottombarPaKeluarBinding;
import com.rewangTani.rewangtani.starter.SplashScreen;
import com.rewangTani.rewangtani.utility.PreferenceUtils;

public class Keluar extends AppCompatActivity {

    BottombarPaKeluarBinding binding;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.bottombar_pa_keluar);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("878909563548-6a6d4mj5uqmqm4hlea3sa73agsv10fhm.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        firebaseAuth = FirebaseAuth.getInstance();

        binding.btnKeluar.setOnClickListener(v->{
            checkGoogle();
        });

        binding.btnKembali.setOnClickListener(v->{
            goToBerandaProfil();
        });

    }

    public void checkGoogle(){
        if (!PreferenceUtils.getIDGoogle(getApplicationContext()).equalsIgnoreCase("")){
            mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    // Check condition
                    if(task.isSuccessful())
                    {
                        // When task is successful
                        // Sign out from firebase
                        firebaseAuth.signOut();
                        hapusDataAkun();
                    }
                }
            });
        } else {
            hapusDataAkun();
        }
    }

    public void hapusDataAkun(){
        PreferenceUtils.saveIdAkun("", getApplicationContext());
        PreferenceUtils.savePassword("", getApplicationContext());
        PreferenceUtils.saveToken("", getApplicationContext());
        PreferenceUtils.saveIdProfil("", getApplicationContext());
        PreferenceUtils.saveIdAlamat("", getApplicationContext());
        PreferenceUtils.saveUsername("", getApplicationContext());
        PreferenceUtils.saveNamaDepan("", getApplicationContext());
        PreferenceUtils.saveNamaBelakang("", getApplicationContext());
        PreferenceUtils.saveIDPhoto("", getApplicationContext());
        PreferenceUtils.saveIDGoogle("", getApplicationContext());
        gotoSplashScreen();
    }

    public void gotoSplashScreen(){
        Intent a = new Intent(Keluar.this, SplashScreen.class);
        startActivity(a);
        finish();
    }

    public void goToBerandaProfil(){
        Intent a = new Intent(Keluar.this, BerandaProfile.class);
        startActivity(a);
        finish();
    }

    public void onBackPressed() {
        goToBerandaProfil();
    }
}