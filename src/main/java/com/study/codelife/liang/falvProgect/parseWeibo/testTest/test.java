package com.study.codelife.liang.falvProgect.parseWeibo.testTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by liang on 2017/3/2.
 */
public class test {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) throws ParseException {
        String data = "2017-01-07 15:25";
        System.out.println(sdf.parse(data).getTime()/1000);
    }
}
