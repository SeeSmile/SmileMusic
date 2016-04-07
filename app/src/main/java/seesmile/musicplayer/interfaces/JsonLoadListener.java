package seesmile.musicplayer.interfaces;

import org.json.JSONObject;

/**
 * Describe:
 * Created by FuPei on 2016/4/7.
 */
public interface JsonLoadListener {
    public void onSuccess(JSONObject jsonObject);
    public void onFail(int code);
}
