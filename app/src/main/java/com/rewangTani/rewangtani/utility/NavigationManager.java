package com.rewangTani.rewangtani.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class NavigationManager
{

    public static void startActivity(Context context, Class<? extends Activity> destination)
    {
        Intent intent = new Intent(context, destination);
        context.startActivity(intent);
        if (context instanceof Activity) {
            ((Activity) context).finish();
        }
    }

    public static void startActivityWithData(Context context, Class<? extends Activity> destination, Bundle data) {
        Intent intent = new Intent(context, destination);
        if (data != null) {
            intent.putExtras(data);
        }
        context.startActivity(intent);
    }

    public static void startActivityWithExtras(Context context, int extra1, String extra2, Activity activity)
    {
        Intent intent = new Intent(context, activity.getClass());
        intent.putExtra("EXTRA_1", extra1);
        intent.putExtra("EXTRA_2", extra2);
        context.startActivity(intent);
    }

    public static void startActivityWithFlags(Context context, Activity activity, int flag1, int flag2)
    {
        Intent intent = new Intent(context, activity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public static void startActivityWithAnimation(Activity context, Activity activity)
    {
        Intent intent = new Intent(context, activity.getClass());
        context.startActivity(intent);
        // Add smooth transitions if you like
        context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}

