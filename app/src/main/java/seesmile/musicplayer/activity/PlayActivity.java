package seesmile.musicplayer.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;


import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import seesmile.musicplayer.adapter.LrcAdapter;
import seesmile.musicplayer.data.Constant;
import seesmile.musicplayer.data.LrcInfo;
import seesmile.musicplayer.interfaces.MusicPlayListener;
import seesmile.musicplayer.R;
import seesmile.musicplayer.base.BaseActivity;
import seesmile.musicplayer.data.LrcEntity;
import seesmile.musicplayer.data.MusicEntity;
import seesmile.musicplayer.service.MusicBinder;
import seesmile.musicplayer.service.MusicPlayService;
import seesmile.musicplayer.interfaces.JsonLoadListener;
import seesmile.musicplayer.util.FileUtils;
import seesmile.musicplayer.util.LrcUtil;
import seesmile.musicplayer.util.Mlog;
import seesmile.musicplayer.util.WebUtil;

/**
 * Describe:
 * Created by FuPei on 2016/4/5.
 */
public class PlayActivity extends BaseActivity implements MusicPlayListener {


    @Bind(R.id.iv_play)
    ImageView iv_play;
    @Bind(R.id.iv_next)
    ImageView iv_next;
    @Bind(R.id.iv_last)
    ImageView iv_last;
    @Bind(R.id.pg_progress)
    ProgressBar pg_progress;
    @Bind(R.id.iv_play_backgournd)
    ImageView iv_background;
    @Bind(R.id.listView)
    ListView listView;

    private File musicFile;
    private MusicBinder musicBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicBinder = (MusicBinder) service;
            Mlog.i("onServiceConnected");
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
        getLrc();
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

        iv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MusicEntity entity = getApp().getNextMusic(musicBinder.getCurrentMusic());
                    if (entity != null) {
                        musicBinder.playMusic(PlayActivity.this, getApp().getNextMusic(musicBinder.getCurrentMusic()));
                    } else {
                        toast("没有下一首了");
                        Mlog.i("播放的音乐为 null ");
                    }
                } catch (IOException e) {
                    toast("播放的音乐文件不存在！");
                    Mlog.i("播放出错:" + e.toString());
                }
            }
        });

        iv_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MusicEntity entity = getApp().getPreviousMusic(musicBinder.getCurrentMusic());
                    if (entity != null) {
                        musicBinder.playMusic(PlayActivity.this, entity);
                    } else {
                        toast("这已经是第一首歌了!");
                    }
                } catch (IOException e) {
                    toast("播放的音乐文件不存在！");
                    Mlog.i("播放出错:" + e.toString());
                }
            }
        });
    }

    private void initLrc() {
        File file = new File(Constant.getLrcDir(), "aa.txt");
        LrcInfo info = new LrcInfo(file);
        LrcAdapter adapter = new LrcAdapter(this, info.getLrclist());
        listView.setAdapter(adapter);

    }

    private void getLrc() {
        WebUtil.doJsonGet(this, LrcUtil.getLrcUrl("海阔天空"), new JsonLoadListener() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                JSONArray array = jsonObject.optJSONArray("result");

                try {
                    LrcEntity entity = new Gson().fromJson(array.getJSONObject(0).toString(), LrcEntity.class);
                    FileUtils.saveLrcFile(PlayActivity.this, entity.getLrc());
                    WebUtil.doJsonGet(PlayActivity.this, LrcUtil.getSongPicUrl(entity), new JsonLoadListener() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) {
                            String url = jsonObject.optJSONObject("result").optString("cover");
                            ImageLoader.getInstance().loadImage(url, new ImageLoadingListener() {
                                @Override
                                public void onLoadingStarted(String s, View view) {

                                }

                                @Override
                                public void onLoadingFailed(String s, View view, FailReason failReason) {

                                }

                                @Override
                                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                                    iv_background.setImageBitmap(bitmap);
                                    initLrc();
                                }

                                @Override
                                public void onLoadingCancelled(String s, View view) {

                                }
                            });
                        }

                        @Override
                        public void onFail(int code) {

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFail(int code) {
                Mlog.i("onFail");
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
        pg_progress.setMax(progress / 100);
        pg_progress.setProgress(current / 100);
    }
}
