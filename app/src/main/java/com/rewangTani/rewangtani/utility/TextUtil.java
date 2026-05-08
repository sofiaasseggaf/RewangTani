package com.rewangTani.rewangtani.utility;

import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class TextUtil
{

    private static DecimalFormat formatter;

    public static String cleanNumber(EditText input)
    {
        if (input == null) return "";
        return input.getText().toString().replaceAll("[^0-9]", "");
    }

    public static String checkDesimal(String a){

        formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
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



    /**
     * Formats a number to ###,###.## format
     * @param value The number to format (double, int, or long)
     * @return Formatted string (e.g., 1.000.000.50)
     */
    public static String formatNumber(Object value)
    {
        if (value == null) return "0";

        if (formatter == null)
        {
            DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(Locale.US);
            symbols.setGroupingSeparator('.');
            symbols.setDecimalSeparator('.');
            formatter = new DecimalFormat("###,###.##", symbols);
        }

        try {
            return formatter.format(value);
        } catch (Exception e) {
            return "0";
        }
    }

}
