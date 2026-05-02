package com.rewangTani.rewangtani.utility;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import com.rewangTani.rewangtani.R;

public class DialogUtil
{

    public interface DialogCallback {
        void onPositive();
    }

    public static void showConfirmDialog(Context context, DialogCallback callback)
    {
        new AlertDialog.Builder(context)
                .setMessage(context.getString(R.string.confirm_exit))
                .setCancelable(false)
                .setPositiveButton(context.getString(R.string.confirm_yes), (dialog, which) -> {
                    if (callback != null) callback.onPositive();
                })
                .setNegativeButton(context.getString(R.string.confirm_no), (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }
}
