package seesmile.musicplayer.db;

import android.content.Context;
import android.database.Cursor;
import android.util.MutableInt;

import java.io.File;
import java.util.ArrayList;

import seesmile.musicplayer.data.MusicEntity;
import seesmile.musicplayer.util.Mlog;

/**
 * Describe:
 * Created by FuPei on 2016/4/6.
 */
public class MusicDB extends DBMain {

    public MusicDB(Context context) {
        super(context);
    }

    public void addMusic(MusicEntity entity) {
        Mlog.i("addMusic:" + entity.toString());
        String sql = "insert into " + TABLE_MUSIC + "(name,path) values(?,?)";
        getWritableDatabase().execSQL(sql, new String[]{entity.getName(), entity.getmFile().getAbsolutePath()});
    }

    public ArrayList<MusicEntity> getMusicList() {
        String sql = "select distinct * from " + TABLE_MUSIC;
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        ArrayList<MusicEntity> list = new ArrayList<>();

        while(cursor.moveToNext()) {
            MusicEntity entity = new MusicEntity();
            File file = new File(cursor.getString(1));
            entity.setmFile(file);
            entity.setName(file.getName());
            list.add(entity);
        }
        return list;
    }

}
