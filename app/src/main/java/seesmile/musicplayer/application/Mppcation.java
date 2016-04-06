package seesmile.musicplayer.application;

import android.app.Application;
import android.util.ArraySet;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

import seesmile.musicplayer.data.MusicEntity;

/**
 * Describe:
 * Created by FuPei on 2016/3/30.
 */
public class Mppcation extends Application {

    private LinkedList<MusicEntity> link_music;
    private ArrayList<MusicEntity> list_music;

    @Override
    public void onCreate() {
        super.onCreate();
        list_music = new ArrayList<>();
    }

    public void setMusicList(ArrayList<MusicEntity> list) {
        this.list_music = list;
    }

    public ArrayList<MusicEntity> getMusicList() {
        return this.list_music;
    }

    public MusicEntity getNextMusic(String name) {
        MusicEntity entity = new MusicEntity();
        for(MusicEntity e : list_music) {

        }
        ListIterator<MusicEntity> iterator = link_music.listIterator();
        while(iterator.next() != null) {

        }
        return entity;
    }

    public MusicEntity getPreviousMusic(String name) {
        MusicEntity entity = new MusicEntity();
        return entity;
    }
}
