package com.rewangTani.rewangtani.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import com.rewangTani.rewangtani.R;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class Utils {

    public static String checkDesimal(String a){

        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator('.');
        formatter = new DecimalFormat("###,###.##", symbols);

        if(a!=null || !a.equalsIgnoreCase("")){
            if(a.length()>3){
                a = formatter.format(Double.valueOf(a));
            }
        }
        return a;
    }

    public static void showCustomAlertDialog(
            Context context,
            String message,
            View.OnClickListener okListener
    ) {

        // Inflate the custom layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View customView = inflater.inflate(R.layout.custom_dialog, null);
        RelativeLayout okButton = customView.findViewById(R.id.btnOk);
        RelativeLayout cancelButton = customView.findViewById(R.id.btnCancel);
        TextView textMessage = customView.findViewById(R.id.textMessage);
        textMessage.setText(message);

        // Create the AlertDialog
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(customView)
                .setCancelable(false)
                .create();

        if (okButton != null) {
            okButton.setOnClickListener(v -> {
                if (okListener != null) okListener.onClick(v);
                dialog.dismiss(); // Dismiss the dialog
            });
        }

        if (cancelButton != null) {
            cancelButton.setOnClickListener(v -> {
                dialog.dismiss(); // Dismiss the dialog
            });
        }

        // Adjust dialog window margins
        dialog.setOnShowListener(dialogInterface -> {
            Window window = dialog.getWindow();
            if (window != null) {
                WindowManager.LayoutParams params = window.getAttributes();
                params.width = WindowManager.LayoutParams.MATCH_PARENT; // Match parent width
                window.setAttributes(params);
                window.setLayout((int) (context.getResources().getDisplayMetrics().widthPixels * 0.8), // 90% of screen width
                        WindowManager.LayoutParams.WRAP_CONTENT); // Wrap content height
            }
        });

        // Show the dialog
        dialog.show();
    }

}
