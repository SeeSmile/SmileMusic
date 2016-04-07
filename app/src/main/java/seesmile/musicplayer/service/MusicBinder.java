package seesmile.musicplayer.service;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;

import java.io.IOException;

import seesmile.musicplayer.interfaces.MusicPlayListener;
import seesmile.musicplayer.data.MusicEntity;
import seesmile.musicplayer.util.Mlog;

/**
 * Describe:
 * Created by FuPei on 2016/4/6.
 */
public class MusicBinder extends Binder implements Runnable{

    private MediaPlayer mediaPlayer;
    private MusicEntity musicEntity;
    private MusicPlayListener listener;
    private Thread progressThread;
    private static MusicBinder binder;

    private MusicBinder() {

    }

    public MusicEntity getCurrentMusic() {
        return musicEntity;
    }

    public static MusicBinder getInstance() {
        if(binder == null) {
            binder = new MusicBinder();
        }
        return binder;
    }

    public void setPlayListener(MusicPlayListener listener) {
        this.listener = listener;
        if(mediaPlayer.isPlaying()) {
            listener.onMusicPlay();
        } else {
            listener.onMusicPause();
        }
        if(progressThread == null) {
            progressThread = new Thread(this);
        }
        if(!progressThread.isAlive()) {
            progressThread.start();
        }
    }

    public void playMusic(Context context, MusicEntity entity) throws IOException {
        if(mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        } else {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
        this.musicEntity = entity;
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setDataSource(context, Uri.fromFile(musicEntity.getmFile()));
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

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    listener.onMusicProgress(0, 0);
                }
                Mlog.i("播放完了，大哥");
            }
        });
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

    @Override
    public void run() {
        while(true) {
            if(mediaPlayer != null) {
                if(mediaPlayer.isPlaying()) {
                    listener.onMusicProgress(mediaPlayer.getCurrentPosition(), mediaPlayer.getDuration());
                }
                try {
                    Thread.sleep(5 * 100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
