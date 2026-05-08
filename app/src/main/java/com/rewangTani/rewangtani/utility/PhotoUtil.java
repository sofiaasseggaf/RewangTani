package com.rewangTani.rewangtani.utility;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class PhotoUtil
{

    public static String getScaledBase64(Bitmap bitmap)
    {
        // 1. Resize the bitmap (e.g., max 1024px width/height)
        int maxSize = 1024;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);

        // 2. Compress to JPEG (Quality 0-100)
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos); // 80 is a good balance
        byte[] b = baos.toByteArray();

        // 3. Convert to Base64
        return Base64.encodeToString(b, Base64.DEFAULT);
    }
}
