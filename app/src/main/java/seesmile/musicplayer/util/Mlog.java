package seesmile.musicplayer.util;

import android.util.Log;

/**
 * Describe:
 * Created by FuPei on 2016/3/30.
 */
public class Mlog {

    private final static boolean canLog = true;
    private final static String TAG_DEFAULT = "fupei";

    public static void i(String text) {
        if(text != null || canLog) {
            Log.i(TAG_DEFAULT, text);
        }
    }

    public static void i(String tag, String text) {
        if(text != null || canLog) {
            Log.i(tag, text);
        }
    }
}
