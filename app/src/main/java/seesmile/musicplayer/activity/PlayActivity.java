package seesmile.musicplayer.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.Image;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import seesmile.musicplayer.MusicPlayListener;
import seesmile.musicplayer.R;
import seesmile.musicplayer.base.BaseActivity;
import seesmile.musicplayer.service.MusicPlayService;

/**
 * Describe:
 * Created by FuPei on 2016/4/5.
 */
public class PlayActivity extends BaseActivity implements MusicPlayListener{


    @Bind(R.id.iv_play)
    ImageView iv_play;
    @Bind(R.id.iv_next)
    ImageView iv_next;
    @Bind(R.id.iv_last)
    ImageView iv_last;
    @Bind(R.id.pg_progress)
    ProgressBar pg_progress;

    private File musicFile;
    private MusicPlayService.MusicBinder musicBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicBinder = (MusicPlayService.MusicBinder) service;
            musicBinder.setPlayListener(PlayActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);
        bindService();
        setListener();
    }

    private void bindService() {
        Intent intent = new Intent(PlayActivity.this, MusicPlayService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    private void setListener() {
        iv_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicBinder.pause();
            }
        });
    }

    @Override
    public void onMusicPause() {
        iv_play.setImageResource(R.mipmap.icon_play);
    }

    @Override
    public void onMusicStop() {
        iv_play.setImageResource(R.mipmap.icon_stop);
    }

    @Override
    public void onMusicPlay() {
        iv_play.setImageResource(R.mipmap.icon_pause);
    }

    @Override
    public void onMusicProgress(int current, int progress) {
        pg_progress.setMax(progress);
        pg_progress.setProgress(current);
    }
}
