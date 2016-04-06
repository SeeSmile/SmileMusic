package seesmile.musicplayer;

/**
 * Describe:
 * Created by FuPei on 2016/4/5.
 */
public interface MusicPlayListener {
    void onMusicPause();
    void onMusicStop();
    void onMusicPlay();
    void onMusicProgress(int current, int all);
}
