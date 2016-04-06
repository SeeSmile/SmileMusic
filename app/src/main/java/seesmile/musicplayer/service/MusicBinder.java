package seesmile.musicplayer.service;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;

import java.io.File;
import java.io.IOException;

import seesmile.musicplayer.MusicPlayListener;

/**
 * Describe:
 * Created by FuPei on 2016/4/6.
 */
public class MusicBinder extends Binder implements Runnable{

    private MediaPlayer mediaPlayer;
    private File mfile;
    private MusicPlayListener listener;
    private Thread progressThread;
    private static MusicBinder binder;

    private MusicBinder() {

    }

    public static MusicBinder getInstance() {
        if(binder == null) {
            binder = new MusicBinder();
        }
        return binder;
    }

    public void setPlayListener(final MusicPlayListener listener) {
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

    /**
     * 播放指定音乐文件
     * @param file
     * @throws IOException
     */
    public void playMusic(Context context, File file) throws IOException {
        if(mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        } else {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
        this.mfile = file;
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setDataSource(context, Uri.fromFile(file));
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
