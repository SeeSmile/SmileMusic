package seesmile.musicplayer.application;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.ListIterator;
import seesmile.musicplayer.data.MusicEntity;

/**
 * Describe:
 * Created by FuPei on 2016/3/30.
 */
public class Mppcation extends Application {

    /**
     * 当前播放音乐列表
     */
    private ArrayList<MusicEntity> list_music;

    @Override
    public void onCreate() {
        super.onCreate();
        list_music = new ArrayList<>();
        initImageLoader();
    }

    /**
     * 设置当前播放音乐的列表
     * @param list 音乐列表
     */
    public void setMusicList(ArrayList<MusicEntity> list) {
        this.list_music = (ArrayList<MusicEntity>) list.clone();
    }

    /**
     * 获取当前播放的音乐列表
     * @return 当前播放列表
     */
    public ArrayList<MusicEntity> getMusicList() {
        return this.list_music;
    }

    /**
     * 获取指定列表音乐的下一首歌曲
     * @param list 指定的播放列表
     * @param musicEntity 当前播放的歌曲
     * @return 下一首歌曲
     */
    private MusicEntity getNextMusic(ArrayList<MusicEntity> list, MusicEntity musicEntity) {
        MusicEntity entity = null;
        String name = musicEntity.getName();
        ListIterator<MusicEntity> iterator = list.listIterator();
        while (iterator.hasNext()) {
            if (name.equals(iterator.next().getName())) {
                if (iterator.hasNext()) {
                    entity = iterator.next();
                }
                break;
            }
        }
        return entity;
    }

    /**
     * 获取当前播放音乐文件的下一首歌曲
     * @param musicEntity 当前播放的歌曲
     * @return 下一首歌曲
     */
    public MusicEntity getNextMusic(MusicEntity musicEntity) {
        return getNextMusic(getMusicList(), musicEntity);
    }

    /**
     * 获取当前播放音乐文件的前一首歌曲
     * @param musicEntity 当前播放的歌曲
     * @return 前一首歌曲
     */
    public MusicEntity getPreviousMusic(MusicEntity musicEntity) {
        return getPreviousMusic(list_music, musicEntity);
    }

    /**
     * 获取指定列表的音乐文件前一首歌曲
     * @param list 指定的播放列表
     * @param musicEntity 音乐文件
     * @return 前一首歌曲
     */
    private MusicEntity getPreviousMusic(ArrayList<MusicEntity> list, MusicEntity musicEntity) {
        MusicEntity entity = null;
        String name = musicEntity.getName();
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).getName().equals(name)) {
                if(i != 0) {
                    entity = list.get(i - 1);
                }
                break;
            }
        }
        return entity;
    }

    private void initImageLoader() {
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);
    }
}
