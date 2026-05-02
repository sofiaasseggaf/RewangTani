package com.rewangTani.rewangtani.utility;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.rewangTani.rewangtani.R;
import com.rewangTani.rewangtani.ui.home.Home;

public class PaymentSuccessActivity extends AppCompatActivity {

    private ImageView imgCheck;
    private TextView txtSuccess, txtDesc;
    private Button btnDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);

        imgCheck = findViewById(R.id.imgCheck);
        txtSuccess = findViewById(R.id.txtSuccess);
        txtDesc = findViewById(R.id.txtDesc);
        btnDone = findViewById(R.id.btnDone);

        playAnimation();

        btnDone.setOnClickListener(v -> {
            Intent a = new Intent(this, Home.class);
            startActivity(a);
            finish();
        });
    }

    private void playAnimation() {

        // Animate check icon (scale + fade)
        imgCheck.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(500)
                .setInterpolator(new DecelerateInterpolator())
                .start();

        // Animate text
        txtSuccess.animate()
                .alpha(1f)
                .setStartDelay(300)
                .setDuration(400)
                .start();

        txtDesc.animate()
                .alpha(1f)
                .setStartDelay(500)
                .setDuration(400)
                .start();

        // Animate button
        btnDone.animate()
                .alpha(1f)
                .setStartDelay(700)
                .setDuration(400)
                .start();
    }
}