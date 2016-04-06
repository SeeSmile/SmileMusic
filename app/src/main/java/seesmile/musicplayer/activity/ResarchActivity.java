package seesmile.musicplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import seesmile.musicplayer.R;
import seesmile.musicplayer.adapter.FileAdapter;
import seesmile.musicplayer.base.BaseActivity;
import seesmile.musicplayer.util.Mlog;

/**
 * Describe:
 * Created by FuPei on 2016/3/30.
 */
public class ResarchActivity extends BaseActivity {

    public static final String DATA_FILE = "file";
    public static final int RESULT_SUCCESS = 1;
    public static final int RESULT_NO = 2;

    @Bind(R.id.tv_path)
    TextView tv_path;
    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.btn_back)
    Button btn_back;
    @Bind(R.id.btn_finish)
    Button btn_finish;

    private FileAdapter mAdapter;
    private ArrayList<String> list_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resarch);
        ButterKnife.bind(this);
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = Environment.getExternalStorageDirectory();
            if (file.isDirectory()) {
//                Mlog.i("is dir");
                if (file.listFiles() != null) {
                    File[] files = file.listFiles();
                    mAdapter = new FileAdapter(this, files);
                    listView.setAdapter(mAdapter);
                    setPathName(file);
                    initListener();
                } else {
//                    Mlog.i("can't read");
                }
            } else {
//                Mlog.i("not dir");
            }
        } else {
//            Mlog.i("false");
        }
    }

    private void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File file = (File) mAdapter.getItem(position);
                if (file.isDirectory()) {
                    setPathName(file);
                    mAdapter.setData(file.listFiles());
                    mAdapter.notifyDataSetChanged();
                } else {
                    finish();
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = (File) tv_path.getTag();
                if (!file.getAbsolutePath().equals(Environment.getExternalStorageDirectory())) {
                    setPathName(file.getParentFile().getAbsoluteFile());
                    mAdapter.setData(file.getParentFile().listFiles());
                    mAdapter.notifyDataSetChanged();
                }
            }
        });

        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list_path = mAdapter.getFilePath();
                Intent intent = new Intent();
                intent.putExtra(DATA_FILE, list_path);
                if(list_path.size() > 0) {
                    setResult(RESULT_SUCCESS, intent);
                } else {
                    setResult(RESULT_NO, intent);
                }
                finish();
            }
        });
    }

    private void setPathName(File file) {
        tv_path.setText(file.getAbsolutePath());
        tv_path.setTag(file);
    }

}
