package seesmile.musicplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import seesmile.musicplayer.R;
import seesmile.musicplayer.base.BaseActivity;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2 * 1000);
                } catch (InterruptedException e) {
                    finish();
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                }
                handler.sendEmptyMessage(1);
            }
        }).start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            finish();
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        }
    };
}
