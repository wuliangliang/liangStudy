package com.study.codelife.liang.crawler.study.xiaowenDemo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by doubling_ruc on 2017/1/20.
 */
public class test {
    public static void main(String[] args) {
        String str =  "http://www.baidu.com/s\\?wd=(.*)%20(.*)&rsv_bp=0&rsv_spt=3&rsv_n=2&inputT=6391";

        Pattern pattern = Pattern.compile(str);
        String ss =  "http://www.baidu.com/s?wd=1型糖尿病%20就是&rsv_bp=0&rsv_spt=3&rsv_n=2&inputT=6391";
        Matcher m = pattern.matcher(ss);

        System.out.println(m.find());
        System.out.println(m.groupCount());
        System.out.println(m.group());
        System.out.println(m.group(1));
        System.out.println(m.group(2));

       // String sss=m.group(0);
        //System.out.println(sss);
    }
}
