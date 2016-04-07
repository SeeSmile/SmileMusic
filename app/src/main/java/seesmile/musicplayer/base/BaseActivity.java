package seesmile.musicplayer.base;

import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import seesmile.musicplayer.application.Mppcation;

/**
 * Describe:
 * Created by FuPei on 2016/3/30.
 */
public class BaseActivity extends FragmentActivity {
    public Mppcation getApp() {
        return (Mppcation)getApplication();
    }

    public void toast(String text) {
        if(text != null && text.length() > 0) {
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        }
    }
}
