package seesmile.musicplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import seesmile.musicplayer.R;

/**
 * Describe:
 * Created by FuPei on 2016/3/30.
 */
public class FileAdapter extends BaseAdapter {

    private ArrayList<File> mFiles;
    private Context mContext;
    private ArrayList<String> list_path;
    private SimpleDateFormat format;

    public FileAdapter(Context context, ArrayList<File> files) {
        this.mContext = context;
        this.mFiles = files;
        format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        list_path = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mFiles.size();
    }

    @Override
    public Object getItem(int position) {
        return mFiles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        FileHold hold;
        if (convertView == null) {
            hold = new FileHold();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_files, null);
            ButterKnife.bind(hold, convertView);
            convertView.setTag(hold);
        } else {
            hold = (FileHold) convertView.getTag();
        }
        File file = mFiles.get(position);
        hold.tv_name.setText(file.getName());
        if(file.isDirectory()) {
            hold.iv_icon.setImageResource(R.mipmap.icon_dir);
        } else {
            if(file.getName().endsWith(".mp3")) {
                hold.iv_icon.setImageResource(R.mipmap.icon_mp3);
            } else {
                hold.iv_icon.setImageResource(R.mipmap.icon_question);
            }
        }
        hold.tv_time.setText(getFileTime(file.lastModified()));
        hold.cb_choose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    list_path.add(mFiles.get(position).getAbsolutePath());
                } else {
                    for (int i = 0; i < list_path.size(); i++) {
                        if (mFiles.get(i).equals(mFiles.get(position).getName())) {
                            list_path.remove(position);
                        }
                    }
                }
            }
        });
        return convertView;
    }

    public class FileHold {
        @Bind(R.id.tv_name)
        TextView tv_name;
        @Bind(R.id.iv_icon)
        ImageView iv_icon;
        @Bind(R.id.cb_choose)
        CheckBox cb_choose;
        @Bind(R.id.tv_time)
        TextView tv_time;
    }

    private String getFileTime(long time) {
        return format.format(new Date(time));
    }

    public void setData(File[] files) {
        if(mFiles != null) {
            mFiles.clear();
        }
        for(File file : files) {
            mFiles.add(file);
        }
    }

    public ArrayList<String> getFilePath() {
        return list_path;
    }
}
