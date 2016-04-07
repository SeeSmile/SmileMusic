package seesmile.musicplayer.data;

public class SingleLrc {
	
	private long time;
	private String lrc;
	
	public SingleLrc(long time, String lrc) {
		this.time = time;
		this.lrc = lrc;
	}

	public long getTime() {
		return time;
	}

	public SingleLrc setTime(long time) {
		this.time = time;
		return this;
	}

	public String getLrc() {
		return lrc;
	}

	public SingleLrc setLrc(String lrc) {
		this.lrc = lrc;
		return this;
	}

	@Override
	public String toString() {
		return "time:" + time + ", lrc:" + lrc;
	}
}
