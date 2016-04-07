package seesmile.musicplayer.util;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import seesmile.musicplayer.interfaces.JsonLoadListener;

/**
 * Describe:
 * Created by FuPei on 2016/4/7.
 */
public class WebUtil {

    private static AsyncHttpClient client;

    public static void doJsonGet(Context context, String url, final JsonLoadListener listener) {
        getClient().get(context, url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    listener.onSuccess(new JSONObject(new String(responseBody)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                listener.onFail(statusCode);
            }
        });
    }

    private static AsyncHttpClient getClient() {
        if(client == null) {
            client = new AsyncHttpClient();
        }
        return client;
    }
}
