package com.rewangTani.rewangtani.utility;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.ImageButton;

import com.rewangTani.rewangtani.R;

public class ViewUtil
{

    public static void togglePassword(EditText editText, ImageButton button)
    {
        if (editText.getTransformationMethod() instanceof PasswordTransformationMethod)
        {
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            button.setImageResource(R.drawable.icon_password_off);
        } else {
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            button.setImageResource(R.drawable.icon_password_on);
        }

        editText.setSelection(editText.getText().length());
    }
}
