package seesmile.musicplayer.service;

import android.app.IntentService;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
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

    public class MusicBinder extends Binder {

        private MediaPlayer mediaPlayer;
        private File mfile;
        private MusicPlayListener listener;

        public void setPlayListener(final MusicPlayListener listener) {
            this.listener = listener;
            mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                @Override
                public void onSeekComplete(MediaPlayer mp) {
                    listener.onMusicProgress(mp.getCurrentPosition(), mp.getDuration());
                }
            });
        }

        /**
         * 播放指定音乐文件
         * @param file
         * @throws IOException
         */
        public void playMusic(File file) throws IOException {
            if(mediaPlayer == null) {
                mediaPlayer = new MediaPlayer();
            } else {
                if(mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                }
            }
            this.mfile = file;
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(getApplicationContext(), Uri.fromFile(file));
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                    if (listener != null) {
                        listener.onMusicPlay();
                    }
                }
            });
            this.mfile = file;


        }

        public void pause() {
            if(mediaPlayer != null) {
                if(mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    if(listener != null) {
                        listener.onMusicPause();
                    }
                } else {
                    mediaPlayer.start();
                    if(listener != null) {
                        listener.onMusicPlay();
                    }
                }
            }
        }

        public void stop() {
            if(mediaPlayer != null && mediaPlayer.isLooping()) {
                mediaPlayer.stop();
                if(listener != null) {
                    listener.onMusicStop();
                }
            }
        }

        public void onServiceDestory() {
            if(listener != null) {
                listener.onMusicStop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }

    }

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
        musicBinder = new MusicBinder();
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
        musicBinder.onServiceDestory();
        super.onDestroy();
    }
}
