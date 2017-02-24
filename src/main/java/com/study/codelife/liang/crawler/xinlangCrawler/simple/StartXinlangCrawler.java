package com.study.codelife.liang.crawler.xinlangCrawler.simple;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import com.study.codelife.liang.crawler.util.FileUtil;

import java.io.BufferedWriter;
import java.util.Random;

/**
 * Created by doubling_ruc on 2017/2/24.
 */
public class StartXinlangCrawler {
    private static Random random = new Random();

    public static void main(String[] args) {
        GetPage crawler = new GetPage("GetPage", false);
        crawler.setThreads(20);
        crawler.setExecuteInterval(2000+random.nextInt(5000));

        CrawlDatums crawlDatums = new CrawlDatums();
//        for(int i =0 ; i<10000;i++){
//            crawler.addSeed();
//        }
        for(int i = 0;i<10000 ; i++) {
            crawler.addSeed("http://feed.mix.sina.com.cn/api/roll/get?pageid=354&lid=2120&num=50&versionNumber=1.2.8&page=" + i);
        }
        try {
            crawler.start(1);
        } catch (Exception e) {
            System.out.println("crawler.start(1); 失败");
        }
    }
}
