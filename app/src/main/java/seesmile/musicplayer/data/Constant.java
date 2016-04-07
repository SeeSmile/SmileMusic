package seesmile.musicplayer.data;

import android.os.Environment;

import java.io.File;

/**
 * Describe:
 * Created by FuPei on 2016/4/7.
 */
public class Constant {

    public static final String DIR_APP = "SmilePlayer";

    public static final String DIR_LRC = "lrc";

    public static File getAppDir() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = Environment.getExternalStorageDirectory();
            if (file.isDirectory()) {
                File dirFile = new File(file.getAbsolutePath(), DIR_APP);
                if(!dirFile.exists()) {
                    dirFile.mkdirs();
                }
                return dirFile;
            }
        }
        return null;
    }

    public static File getLrcDir() {
        if(getAppDir() != null) {
            File file = new File(getAppDir(), DIR_LRC);
            if(!file.exists()) {
                file.mkdirs();
            }
            return file;
        }
        return null;
    }
}
