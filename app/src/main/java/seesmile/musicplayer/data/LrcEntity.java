package seesmile.musicplayer.data;

/**
 * Describe:
 * Created by FuPei on 2016/4/7.
 */
public class LrcEntity {

    /**
     * aid : 2848529
     * lrc : http://s.geci.me/lrc/344/34435/3443588.lrc
     * song : 海阔天空
     * artist_id : 2
     * sid : 3443588
     */

    private int aid;
    private String lrc;
    private String song;
    private int artist_id;
    private int sid;

    public void setAid(int aid) {
        this.aid = aid;
    }

    public void setLrc(String lrc) {
        this.lrc = lrc;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public void setArtist_id(int artist_id) {
        this.artist_id = artist_id;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getAid() {
        return aid;
    }

    public String getLrc() {
        return lrc;
    }

    public String getSong() {
        return song;
    }

    public int getArtist_id() {
        return artist_id;
    }

    public int getSid() {
        return sid;
    }
}
