package seesmile.musicplayer.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seesmile.musicplayer.util.Mlog;

public class LrcInfo {

    private ArrayList<SingleLrc> lrclist;

    public LrcInfo(File file) {
        try {
            getLrc(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getLrc(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String line;
        lrclist = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            setInfo(line);
        }
        br.close();
        isr.close();
        fis.close();
    }

    private void setInfo(String text) {
        String rule_time = "\\[\\d+:\\d+.\\d+\\]";
        String rule_text = "[\\u4e00-\\u9fa5]+";
        Pattern pattern_time = Pattern.compile(rule_time);
        Pattern pattern_text = Pattern.compile(rule_text);
        Matcher matcher_time = pattern_time.matcher(text);
        Matcher matcher_text = pattern_text.matcher(text);

        if (matcher_text.find()) {
            String text_lrc = matcher_text.group();
            if(text_lrc == null) {
                text_lrc = "";
            }
            while (matcher_time.find()) {
                SingleLrc lrc = new SingleLrc(getTextTime(matcher_time.group()), text_lrc);
                lrclist.add(lrc);
            }
        }
    }

    private long getTextTime(String text) {
        Mlog.i("text:" + text);
        String[] ts = text.substring(1, text.length() - 1).split(":");
        long time = 0;
//        time += Integer.valueOf(ts[0]) * 60 * 1000;
//        time += Integer.valueOf(ts[1].split(".")[0]) * 1000;
//        time += Integer.valueOf(ts[1].split(".")[1]) * 10;
        time += Integer.valueOf(text.substring(1, 3)) * 60 * 1000;
        time += Integer.valueOf(text.substring(4, 6)) * 1000;
        if (text.length() > 6) {
            time += Integer.valueOf(text.substring(7, 9)) * 10;
        }
//        time += Integer.valueOf(ts[1]);
        return time;
    }

    public ArrayList<SingleLrc> getLrclist() {
        Collections.sort(lrclist, new Comparator<SingleLrc>() {
            @Override
            public int compare(SingleLrc lhs, SingleLrc rhs) {
                if(lhs.getTime() > rhs.getTime()) {
                    return 1;
                }
                return -1;
            }
        });
        return lrclist;
    }
}
