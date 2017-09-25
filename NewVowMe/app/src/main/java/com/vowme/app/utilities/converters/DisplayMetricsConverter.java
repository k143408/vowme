package com.vowme.app.utilities.converters;

public class DisplayMetricsConverter {
    public static int dpToPx(float scale, int dp) {
        return (int) ((((float) dp) * scale) + 0.5f);
    }
}
