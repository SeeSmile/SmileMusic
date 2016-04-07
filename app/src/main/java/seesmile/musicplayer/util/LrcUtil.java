package seesmile.musicplayer.util;

import android.content.Context;
import android.os.Environment;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.File;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import seesmile.musicplayer.data.Constant;
import seesmile.musicplayer.data.LrcEntity;

/**
 * Describe:
 * Created by FuPei on 2016/4/7.
 */
public class LrcUtil {

    private static final String URL_LRC = "http://geci.me/api/lyric/";
    private static final String URL_PIC = "http://geci.me/api/cover/";

    public static String getLrcUrl(String song) {
        String url = URL_LRC + song;
        return url;
    }

    public static void getLrc(String song, String singer) {

    }

    public static String getSongPicUrl( LrcEntity entity) {
        String url = URL_PIC + entity.getAid();
        return url;
    }
}
