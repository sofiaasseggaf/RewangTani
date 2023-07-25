package com.rewangTani.rewangtani.bottombar.profilakun;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.rewangTani.rewangtani.R;

public class Kontak extends AppCompatActivity {

    //ImageButton btn_kembali;
    LinearLayout ll_whatsapp, ll_google, ll_telepon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottombar_pa_kontak);

        ll_whatsapp = findViewById(R.id.ll_whatsapp);
        ll_google = findViewById(R.id.ll_google);
        ll_telepon = findViewById(R.id.ll_telepon);
        //btn_kembali = findViewById(R.id.btn_kembali);

        ll_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kontakWhatsapp();
            }
        });

        ll_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kontakEmail();
            }
        });

        ll_telepon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kontakTelepon();
            }
        });

       /* btn_kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToBerandaProfil();
            }
        });*/

    }

    public void kontakWhatsapp(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Hubungi whastapp ?")
                .setCancelable(false)
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        //hubungi whataspp
                    }
                })

                .setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog =builder.create();
        alertDialog.show();
    }

    public void kontakEmail(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Hubungi email ?")
                .setCancelable(false)
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        //hubungi email
                    }
                })

                .setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog =builder.create();
        alertDialog.show();
    }

    public void kontakTelepon(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Hubungi telepon ?")
                .setCancelable(false)
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        //hubungi telepon
                    }
                })

                .setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog =builder.create();
        alertDialog.show();
    }

    public void goToBerandaProfil(){
        Intent a = new Intent(Kontak.this, BerandaProfile.class);
        startActivity(a);
        finish();
    }

    public void onBackPressed() {
        goToBerandaProfil();
    }
}