package com.study.codelife.liang.crawler.xinlangCrawler.simple;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.study.codelife.liang.crawler.study.xiaowenDemo.BaiduCrawl;
import com.study.codelife.liang.crawler.util.FileUtil;
import com.study.codelife.liang.crawler.util.Util;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Created by doubling_ruc on 2017/2/24.
 */
public class GetPage extends BreadthCrawler {

    private static BufferedWriter bw = FileUtil.writeToFile("reslut.txt");

    private static int num =0 ;

    public GetPage(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
    }

    public void visit(Page page, CrawlDatums next) {

        System.out.println(page.getUrl());




    }

    public void parseFirst(Page page, CrawlDatums next){
        System.out.println(page.getHtml());
        String json = page.getHtml();
        JSONObject jsonObject = JSON.parseObject(json);
        JSONArray jsonArray = jsonObject.getJSONObject("result").getJSONArray("data");
        for(Object object:jsonArray.toArray()){
            JSONObject jsonObject1 = (JSONObject) object;
            String title = jsonObject1.getString("title");
            String timeUnix=  jsonObject1.getString("ctime");
            String time  = Util.unixToTime(timeUnix);
            String url = jsonObject1.getString("url");
            System.out.println(time+title+url);
            next.add(url);
            num++;
        }
        try {
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("num :"+num);
    }
    public  void parseScond(Page page, CrawlDatums next){

    }
}