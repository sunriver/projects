package com.sunriver.common.utils;

import android.os.Build;
/**
 * Class containing some static utility methods.
 */
public class ApiLevel {

    /**
     * Build.VERSION.SDK_INT >= 8
     */
    public static boolean hasFroyo() {
        // Can use static final constants like FROYO, declared in later versions
        // of the OS since they are inlined at compile time. This is guaranteed behavior.
        return Build.VERSION.SDK_INT >= 8; //Build.VERSION_CODES.FROYO;
    }

    /**
     * Build.VERSION.SDK_INT >= 9
     */
    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= 9; //Build.VERSION_CODES.GINGERBREAD;
    }

    /**
     * Build.VERSION.SDK_INT >= 11
     */
    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= 11; //Build.VERSION_CODES.HONEYCOMB;
    }

    /**
     * Build.VERSION.SDK_INT >= 12
     */
    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= 12; //Build.VERSION_CODES.HONEYCOMB_MR1;
    }
    
    /**
     * Build.VERSION.SDK_INT >= 13
     */
    public static boolean hasHoneycombMR2() {
    	return Build.VERSION.SDK_INT >= 13; //Build.VERSION_CODES.HONEYCOMB_MR2;
    }

    /**
     * Build.VERSION.SDK_INT >= 16
     */
    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= 16; //Build.VERSION_CODES.JELLY_BEAN;
    }
}

