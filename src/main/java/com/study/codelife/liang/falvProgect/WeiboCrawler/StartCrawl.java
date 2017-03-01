package com.study.codelife.liang.falvProgect.WeiboCrawler;

import java.io.IOException;


public class StartCrawl {
	public static void main(String[] args) throws IOException, InterruptedException {
        //在找人页面get到uid，保存在uid的文件里面。
        String xiangxi = "C:\\Users\\liang\\Desktop\\Crawl\\userId\\UserId12.txt" ;//文件保存路径   acc+
     //   CrawlerHelper.getUrl(171,xiangxi);t
       //rawlerParse.init("C:\\Users\\liang\\Desktop\\Crawl\\result");//这个是初始化输出结果文件
        weiboCrawl.crawlInput(xiangxi);
        //
    }
}