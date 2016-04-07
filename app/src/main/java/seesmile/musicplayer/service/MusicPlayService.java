package seesmile.musicplayer.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;

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
