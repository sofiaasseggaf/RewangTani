package com.rewangTani.rewangtani.utility;

import android.app.Activity;
import android.widget.EditText;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
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

    public static String checkDesimal(String a)
    {

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


    public interface TranslationCallback {
        void onTranslationSuccess(String translatedText);
        void onTranslationError(Exception e);
    }

    public static void translateText(Activity activity, String text, TranslationCallback callback)
    {
        new Thread(() -> {
            HttpURLConnection urlConnection = null;
            try {
                String urlString = "https://api.mymemory.translated.net/get?q="
                        + URLEncoder.encode(text, "UTF-8")
                        + "&langpair="
                        + URLEncoder.encode("en|id", "UTF-8");

                URL url = new URL(urlString);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setConnectTimeout(5000);
                urlConnection.setReadTimeout(5000);

                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK)
                {
                    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder responseStr = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        responseStr.append(inputLine);
                    }
                    in.close();

                    JSONObject jsonResponse = new JSONObject(responseStr.toString());
                    String translated = jsonResponse.getJSONObject("responseData").getString("translatedText");
                    activity.runOnUiThread(() -> callback.onTranslationSuccess(translated));
                }
                else
                {
                    activity.runOnUiThread(() -> callback.onTranslationError(new Exception("Server returned code: " + responseCode)));
                }
            }
            catch (Exception e)
            {
                activity.runOnUiThread(() -> callback.onTranslationError(e));
            }
            finally
            {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        }).start();
    }


}
