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
import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import seesmile.musicplayer.R;
import seesmile.musicplayer.base.BaseActivity;
import seesmile.musicplayer.util.Mlog;

/**
 * Describe:
 * Created by FuPei on 2016/3/30.
 */
public class FileAdapter extends BaseAdapter {

    private File[] mFiles;
    private Context mContext;
    private ArrayList<String> list_path;

    public FileAdapter(Context context, File[] files) {
        this.mContext = context;
        this.mFiles = files;
        list_path = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mFiles.length;
    }

    @Override
    public Object getItem(int position) {
        return mFiles[position];
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
        hold.tv_name.setText(mFiles[position].getName());
        hold.cb_choose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    list_path.add(mFiles[position].getAbsolutePath());
                } else {
                    for (int i = 0; i < list_path.size(); i++) {
                        if (mFiles[i].equals(mFiles[position].getName())) {
                            list_path.remove(position);
                        }
                    }
                }
            }
        });

        hold.tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        hold.iv_icon.setOnClickListener(null);
        return convertView;
    }

    public class FileHold {
        @Bind(R.id.tv_name)
        TextView tv_name;
        @Bind(R.id.iv_icon)
        ImageView iv_icon;
        @Bind(R.id.cb_choose)
        CheckBox cb_choose;
    }

    public void setData(File[] files) {
        this.mFiles = files;
    }

    public ArrayList<String> getFilePath() {
        return list_path;
    }
}
