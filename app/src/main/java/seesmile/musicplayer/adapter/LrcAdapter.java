package seesmile.musicplayer.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import seesmile.musicplayer.R;
import seesmile.musicplayer.data.SingleLrc;
import seesmile.musicplayer.util.Mlog;

/**
 * Describe:
 * Created by FuPei on 2016/4/7.
 */
public class LrcAdapter extends BaseAdapter {

    private ArrayList<SingleLrc> data;
    private Context context;
    private long musicTime;

    public LrcAdapter(Context context, ArrayList<SingleLrc> data) {
        this.data = data;
        this.context = context;
        musicTime = 0;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LrcHold hold;
        if(convertView == null) {
            hold = new LrcHold();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lrc, null);
            ButterKnife.bind(hold, convertView);
            convertView.setTag(hold);
        } else {
            hold = (LrcHold) convertView.getTag();
        }
        SingleLrc lrc = data.get(position);
        if(lrc != null) {
            if(lrc.getLrc() != null) {
                hold.tv_text.setText(lrc.getLrc());
            }
        }
        if(getMusicPosition() == position) {
            hold.tv_text.setTextColor(Color.parseColor("#fefefe"));
        }
        return convertView;
    }

    public class LrcHold {
        @Bind(R.id.tv_text)
        TextView tv_text;
    }

    public void setCurrentTime(long time) {
        musicTime = time;
    }

    private int getMusicPosition() {
        for(int i = 0; i< data.size(); i++) {
            SingleLrc lrc = data.get(i);
            if(musicTime > lrc.getTime()) {
                return i;
            }
        }
        return 0;
    }
}
