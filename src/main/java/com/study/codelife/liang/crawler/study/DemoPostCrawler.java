package com.study.codelife.liang.crawler.study;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.net.HttpRequest;
import cn.edu.hfut.dmic.webcollector.net.HttpResponse;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;

import org.json.JSONObject;
/**
 * Created by doubling_ruc on 2017/1/17.
 */
public class DemoPostCrawler extends BreadthCrawler{
    public DemoPostCrawler(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
    }

    @Override
    public HttpResponse getResponse(CrawlDatum crawlDatum) throws Exception {
        HttpRequest request = new HttpRequest(crawlDatum.getUrl());

        request.setMethod(crawlDatum.getMetaData("method"));
        String outputData=crawlDatum.getMetaData("outputData");
        if(outputData!=null){
            request.setOutputData(outputData.getBytes("utf-8"));
        }
        return request.getResponse();
        /*
        //通过下面方式可以设置Cookie、User-Agent等http请求头信息
        request.setCookie("xxxxxxxxxxxxxx");
        request.setUserAgent("WebCollector");
        request.addHeader("xxx", "xxxxxxxxx");
        */
    }


    public void visit(Page page, CrawlDatums next) {
        String jsonStr = page.getHtml();
        JSONObject json = new JSONObject(jsonStr);
        System.out.println("JSON信息：" + json);
    }
    /**
     * 假设我们要爬取三个链接 1)http://www.A.com/index.php 需要POST，并且需要附带数据id=a
     * 2)http://www.B.com/index.php?id=b 需要POST，不需要附带数据 3)http://www.C.com/
     * 需要GET
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        DemoPostCrawler crawler = new DemoPostCrawler("json_crawler", true);
        crawler.addSeed(new CrawlDatum("http://www.A.com/index.php")
                .putMetaData("method", "POST")
                .putMetaData("outputData", "id=a"));
        crawler.addSeed(new CrawlDatum("http://www.B.com/index.php")
                .putMetaData("method", "POST"));
        crawler.addSeed(new CrawlDatum("http://www.C.com/index.php")
                .putMetaData("method", "GET"));

        crawler.start(1);
    }
}
