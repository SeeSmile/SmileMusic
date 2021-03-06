package seesmile.musicplayer.activity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import seesmile.musicplayer.R;
import seesmile.musicplayer.adapter.MusicAdapter;
import seesmile.musicplayer.application.Mppcation;
import seesmile.musicplayer.base.BaseActivity;
import seesmile.musicplayer.data.MusicEntity;
import seesmile.musicplayer.db.MusicDB;
import seesmile.musicplayer.service.MusicBinder;
import seesmile.musicplayer.service.MusicPlayService;
import seesmile.musicplayer.util.FileUtils;
import seesmile.musicplayer.util.Mlog;

/**
 * Describe:
 * Created by FuPei on 2016/3/30.
 */
public class MainActivity extends BaseActivity {

    private static final int CODE_RESARCH = 1;

    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.tv_resarch)
    Button btn_resarch;
    @Bind(R.id.tv_music_name)
    TextView tv_music;
    private MusicAdapter musicAdapter;
    private ArrayList<MusicEntity> list_music;
    private MusicBinder musicBinder;
    private MusicDB db;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicBinder = (MusicBinder) service;
            if(musicBinder.getCurrentMusic() != null) {
                tv_music.setText(musicBinder.getCurrentMusic().getName());
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Mlog.i("MainActivity onServiceDisconnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bindService();
        initData();
        btn_resarch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, ResarchActivity.class), CODE_RESARCH);
            }
        });
        setListener();
    }

    private void initData() {
        db = new MusicDB(this);
        showMusicList(db.getMusicList());
    }

    private void bindService() {
        Intent intent = new Intent(MainActivity.this, MusicPlayService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    private void showMusicList(ArrayList<MusicEntity> list) {
        if(list_music != null) {
            list_music.clear();
        } else {
            list_music = new ArrayList<>();
            list_music.addAll(list);
        }
        if(musicAdapter == null) {
            musicAdapter = new MusicAdapter(this, list_music);
            listView.setAdapter(musicAdapter);
        } else {
            musicAdapter.notifyDataSetChanged();
        }
        getApp().setMusicList(list_music);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_RESARCH) {
            if (resultCode == ResarchActivity.RESULT_SUCCESS) {
                ArrayList<String> paths = (ArrayList<String>) data.getSerializableExtra(ResarchActivity.DATA_FILE);
                ArrayList<File> mFiles = new ArrayList<>();
                for (String s : paths) {
                    mFiles.add(new File(s));
                }
                list_music = FileUtils.scanMusicFiles(mFiles);
                for (MusicEntity e : list_music) {
                    db.addMusic(e);
                }
                showMusicList(list_music);
            }
        }
    }

    private void setListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    MusicEntity entity = list_music.get(position);
                    musicBinder.playMusic(MainActivity.this, entity);
                    tv_music.setText(list_music.get(position).getName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        tv_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PlayActivity.class));
            }
        });
    }
}
