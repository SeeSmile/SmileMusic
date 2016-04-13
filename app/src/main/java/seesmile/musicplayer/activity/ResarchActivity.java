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
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

    private String path_sd;
    private FileAdapter mAdapter;
    private ArrayList<String> list_path;
    private ArrayList<File> list_file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resarch);
        ButterKnife.bind(this);
        //如果有SD卡
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = Environment.getExternalStorageDirectory();
            if (file.isDirectory()) {
                path_sd = file.getAbsolutePath();
                if (file.listFiles() != null) {
                    File[] files = file.listFiles();
                    list_file = new ArrayList<>();
                    for(File f : files) {
                        list_file.add(f);
                    }
                    Collections.sort(list_file, new Comparator<File>() {
                        @Override
                        public int compare(File lhs, File rhs) {
                            if(lhs.isDirectory() && !rhs.isDirectory()) {
                                return -1;
                            } else {
                                if(lhs.isDirectory() && rhs.isDirectory()) {
                                    return -1;
                                }
                                return 0;
                            }
                        }
                    });
                    mAdapter = new FileAdapter(this, list_file);
                    listView.setAdapter(mAdapter);
                    setPathName(file);
                    initListener();
                }
            }
        } else {
            toast("检测不到SD卡");
        }
    }

    private void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File file = (File) mAdapter.getItem(position);
                if (file.isDirectory()) {
                    setPathName(file);
                    //选中的目标文件夹有没有文件列表
                    if(file.listFiles() != null) {
                        mAdapter.setData(file.listFiles());
                    } else {
                        mAdapter.setData(new File[]{});
                    }
                    mAdapter.notifyDataSetChanged();
                } else {

                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = (File) tv_path.getTag();
                /**
                 * 判断是否在SD卡根目录
                 */
                if (!file.getAbsolutePath().equals(Environment.getExternalStorageDirectory().getAbsolutePath())) {
                    setPathName(file.getParentFile().getAbsoluteFile());
                    mAdapter.setData(file.getParentFile().listFiles());
                    mAdapter.notifyDataSetChanged();
                } else {
                    toast("已经是根目录");
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

    /**
     * 设置显示当前目录的名字
     * @param file 当前目录文件
     */
    private void setPathName(File file) {
        tv_path.setText(file.getAbsolutePath().replaceAll(path_sd, "SD卡"));
        //将当前文件目录的路径保存到tag
        tv_path.setTag(file);
    }
}
