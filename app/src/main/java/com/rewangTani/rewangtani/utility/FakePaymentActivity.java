package com.rewangTani.rewangtani.utility;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rewangTani.rewangtani.R;

public class FakePaymentActivity extends AppCompatActivity {

    private Button btnPay;
    private ProgressBar progressBar;
    private RadioGroup radioGroup;
    private TextView txtTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_paymeny);

        String a = getIntent().getStringExtra("Harga");

        txtTotal = findViewById(R.id.txt_total);
        btnPay = findViewById(R.id.btnPay);
        progressBar = findViewById(R.id.progressBar);
        radioGroup = findViewById(R.id.radioGroupPayment);

        txtTotal.setText("Total = " + a);
        btnPay.setOnClickListener(v -> startFakePayment());
    }

    private void startFakePayment() {

        int selectedId = radioGroup.getCheckedRadioButtonId();

        if (selectedId == -1) {
            Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show();
            return;
        }

        btnPay.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(() -> {

            progressBar.setVisibility(View.GONE);
            btnPay.setEnabled(true);

            String method = getPaymentMethod(selectedId);

            // force success for demo (recommended)
            Intent intent = new Intent(this, PaymentSuccessActivity.class);
            startActivity(intent);

        }, 2000);
    }

    private String getPaymentMethod(int id) {
        if (id == R.id.rbGopay) return "GoPay";
        if (id == R.id.rbOvo) return "OVO";
        if (id == R.id.rbBank) return "Bank Transfer";
        return "Unknown";
    }
}