package seesmile.musicplayer.data;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;

/**
 * Describe:音乐
 * Created by FuPei on 2016/3/30.
 */
public class MusicEntity implements Parcelable {

    private File mFile;
    private String name;
    private long time;
    private Bitmap icon;

    public Bitmap getIcon() {
        return icon;
    }

    public MusicEntity setIcon(Bitmap icon) {
        this.icon = icon;
        return this;
    }

    public long getTime() {
        return time;
    }

    public MusicEntity setTime(long time) {
        this.time = time;
        return this;
    }

    public String getName() {
        return name;
    }

    public MusicEntity setName(String name) {
        this.name = name;
        return this;
    }

    public File getmFile() {
        return mFile;
    }

    public MusicEntity setmFile(File mFile) {
        this.mFile = mFile;
        return this;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.mFile);
        dest.writeString(this.name);
        dest.writeLong(this.time);
        dest.writeParcelable(this.icon, 0);
    }

    public MusicEntity() {
    }

    protected MusicEntity(Parcel in) {
        this.mFile = (File) in.readSerializable();
        this.name = in.readString();
        this.time = in.readLong();
        this.icon = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Parcelable.Creator<MusicEntity> CREATOR = new Parcelable.Creator<MusicEntity>() {
        public MusicEntity createFromParcel(Parcel source) {
            return new MusicEntity(source);
        }

        public MusicEntity[] newArray(int size) {
            return new MusicEntity[size];
        }
    };

    @Override
    public String toString() {
        return "path:" + getmFile().getAbsolutePath();
    }
}
