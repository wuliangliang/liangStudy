package com.study.codelife.liang.crawler.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by doubling_ruc on 2017/2/24.
 */
public class Util {
    private static SimpleDateFormat ps = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static String unixToTime(String unix){
        return ps.format(new Date(Long.parseLong(unix)*1000));
    }
}
