package seesmile.musicplayer.db;

import android.app.ActionBar;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Describe:
 * Created by FuPei on 2016/4/5.
 */
public class DBMain extends SQLiteOpenHelper {

    public static final String DB_NAME = "seesmile";
    public static final int DB_VERSION = 1;
    public static final String TABLE_MUSIC = "music";
    public static final String SQL_CREATE = "create table " + TABLE_MUSIC + "(" +
            "name text, path text)";
    private Context mContext;

    public DBMain(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.mContext = context;
    }

    public Context getmContext() {
        return mContext;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
