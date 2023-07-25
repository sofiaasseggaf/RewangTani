package com.rewangTani.rewangtani.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;

public class StringDateComparator implements Comparator<String> {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    int i;

    public int compare(String lhs, String rhs) {
        try {
            i = dateFormat.parse(lhs).compareTo(dateFormat.parse(rhs));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return i;
    }
}


