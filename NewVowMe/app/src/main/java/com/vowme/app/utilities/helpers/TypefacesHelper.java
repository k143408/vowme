package com.vowme.app.utilities.helpers;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import java.util.Hashtable;

public class TypefacesHelper {
    private static final String TAG = "TypefacesHelper";
    private static final Hashtable<String, Typeface> cache = new Hashtable();

    public static Typeface get(Context c, String assetPath) {
        Typeface typeface;
        synchronized (cache) {
            if (!cache.containsKey(assetPath)) {
                try {
                    cache.put(assetPath, Typeface.createFromAsset(c.getAssets(), assetPath));
                } catch (Exception e) {
                    Log.e(TAG, "Could not get typeface '" + assetPath + "' because " + e.getMessage());
                    typeface = null;
                }
            }
            typeface = (Typeface) cache.get(assetPath);
        }
        return typeface;
    }
}
