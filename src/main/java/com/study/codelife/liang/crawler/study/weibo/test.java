package com.study.codelife.liang.crawler.study.weibo;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.study.codelife.liang.crawler.util.FileUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

/**
 * Created by doubling_ruc on 2017/2/23.
 */
public class test {
    public static void main(String[] args) throws IOException {
        final WebClient webClient=new WebClient();
        final HtmlPage page=webClient.getPage("http://www.yanyulin.info");
        System.out.println(page.asText());
        webClient.closeAllWindows();


        BufferedWriter bw =  FileUtil.writeToFile("pachong.xml");
        bw.write("你是大坏蛋");
        bw.flush();


    }
}
