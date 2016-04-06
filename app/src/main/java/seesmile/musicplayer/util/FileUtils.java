package seesmile.musicplayer.util;

import java.io.File;
import java.util.ArrayList;

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
}
