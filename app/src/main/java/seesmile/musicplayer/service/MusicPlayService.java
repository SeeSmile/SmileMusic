package seesmile.musicplayer.service;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.TimedText;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import java.io.File;
import java.io.IOException;

import seesmile.musicplayer.MusicPlayListener;
import seesmile.musicplayer.util.Mlog;

/**
 * Describe:
 * Created by FuPei on 2016/4/5.
 */
public class MusicPlayService extends IntentService {

    private MusicBinder musicBinder;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public MusicPlayService() {
        super("music player");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        musicBinder = MusicBinder.getInstance();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Mlog.i("onBind()");
        return musicBinder;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Mlog.i("onHandleIntent()");
    }

    @Override
    public void onDestroy() {
        Mlog.i("onDestroy");
//        musicBinder.onServiceDestory();
        super.onDestroy();
    }
}
