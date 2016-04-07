package seesmile.musicplayer.util;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import seesmile.musicplayer.data.Constant;
import seesmile.musicplayer.data.LrcInfo;
import seesmile.musicplayer.data.MusicEntity;

/**
 * Describe:
 * Created by FuPei on 2016/4/5.
 */
public class FileUtils {

    private final static String tag = "fileUtils";

    /**
     * scan CMF from list
     * @param files the CMF parent folder
     * @return list of MusicEntity
     */
    public static ArrayList<MusicEntity> scanMusicFiles(ArrayList<File> files) {
        Mlog.i(tag, "scan music and file count is : " + files.size());
        ArrayList<MusicEntity> list = new ArrayList<>();
        ArrayList<File> data = new ArrayList<>();
        for(File file : files) {
            getMusicFiles(data, file);
        }
        for(File file : data) {
            MusicEntity entity = new MusicEntity();
            entity.setName(file.getName());
            entity.setmFile(file);
            list.add(entity);
        }
        return list;
    }

    /**
     * get CMF from single folder
     * @param data the list of CMF
     * @param file CMf parent folder
     */
    private static void getMusicFiles(ArrayList<File> data, File file) {
        if(file.isDirectory()) {
            for(File f : file.listFiles()) {
                getMusicFiles(data, f);
            }
        } else {
            if(file.getName().endsWith(".mp3")) {
                data.add(file);
            }
        }
    }

    public static void saveLrcFile(Context context, String url) {
        new AsyncHttpClient().get(context, url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                File file = new File(Constant.getLrcDir(), "aa.txt");
                try {
                    OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
                    writer.write(new String(responseBody));
                    writer.close();
                    LrcInfo info = new LrcInfo(file);
                    Mlog.i("list:\n" + info.getLrclist().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

}
