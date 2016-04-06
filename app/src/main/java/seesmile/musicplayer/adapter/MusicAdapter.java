package seesmile.musicplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import seesmile.musicplayer.R;
import seesmile.musicplayer.data.MusicEntity;

/**
 * Describe:
 * Created by FuPei on 2016/4/5.
 */
public class MusicAdapter extends BaseAdapter {

    private ArrayList<MusicEntity> mData;
    private Context mContext;

    public MusicAdapter(Context context, ArrayList<MusicEntity> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MusicHold hold;
        if(convertView == null) {
            hold = new MusicHold();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_music, null);
            ButterKnife.bind(hold, convertView);
            convertView.setTag(hold);
        } else {
            hold = (MusicHold) convertView.getTag();
        }
        hold.tv_name.setText(mData.get(position).getName());
        return convertView;
    }

    public class MusicHold {
        @Bind(R.id.tv_name)
        TextView tv_name;
    }
}
