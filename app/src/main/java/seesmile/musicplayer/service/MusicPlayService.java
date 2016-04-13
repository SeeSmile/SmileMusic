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
        Mlog.i("Music Service super");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        musicBinder = MusicBinder.getInstance();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return musicBinder;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
