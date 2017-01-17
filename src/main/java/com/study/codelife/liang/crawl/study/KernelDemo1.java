package com.study.codelife.liang.crawl.study;
import cn.edu.hfut.dmic.webcollector.crawldb.DBManager;
import cn.edu.hfut.dmic.webcollector.crawler.Crawler;
import cn.edu.hfut.dmic.webcollector.fetcher.Executor;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BerkeleyDBManager;
import cn.edu.hfut.dmic.webcollector.util.CharsetDetector;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by doubling_ruc on 2017/1/17.
 * 该教程为WebCollector内核使用教程
 * 内核适合需要深度定制http请求的开发者使用
 * 普通业务可使用已封装好的BreadthCrawler等插件
 *
 * 一个爬取器Crawler需要定制一个DBManager和一个Executor
 * DBManager用于维护爬取历史，以做到去重、断点爬取等功能
 * Executor用于定制每次的爬取和抽取操作
 *
 * 本教程利用HttpClient定制http请求，并使用伯克利DB维护爬取历史
 *
 *
 */
public class KernelDemo1 {
    public static void main(String[] args) throws Exception {
        /*定制Executor*/
        Executor executor=new Executor() {

            /*execute应该包含对一个页面从http请求到抽取的过程
              如果在execute中发生异常并抛出，例如http请求超时，
              爬虫会在后面的任务中继续爬取execute失败的任务。
              如果一个任务重试次数太多，超过Config.MAX_EXECUTE_COUNT，
              爬虫会忽略这个任务。Config.MAX_EXECUTE_COUNT的值可以被修改*/
            public void execute(CrawlDatum datum, CrawlDatums next) throws Exception {
                CloseableHttpClient client= HttpClients.createDefault();
                String url=datum.getUrl();
                try{
                    HttpGet get=new HttpGet(url);
                    HttpResponse response=client.execute(get);
                    HttpEntity entity=response.getEntity();
                    /*利用HttpClient获取网页的字节数组，
                      通过CharsetDetector判断网页的编码 */
                    byte[] content= EntityUtils.toByteArray(entity);
                    String charset= CharsetDetector.guessEncoding(content);
                    String html=new String(content,charset);
                    /*利用Jsoup解析网页，并执行抽取等操作*/
                    Document doc= Jsoup.parse(html,url);
                    System.out.println(doc.title());
                    Elements links=doc.select("a[href]");
                    for(int i=0;i<links.size();i++){
                        Element link=links.get(i);
                        /*抽取超链接的绝对路径*/
                        String href=link.attr("abs:href");
                        /*将新链接加入后续任务，这里只加入以http://news.hfut.edu.cn/开头的链接
                          用户不用考虑去重的问题，爬虫内核会自动去重*/
                        if(href.startsWith("http://news.hfut.edu.cn/")){
                            next.add(href);
                        }
                    }
                }finally {
                    client.close();
                }
            }
        };

        /*基于伯克利DB的DBManager*/
        DBManager dbManager=new BerkeleyDBManager("crawl");

        /*构建一个Crawler*/
        Crawler crawler=new Crawler(dbManager,executor);
        /*线程数*/
        crawler.setThreads(20);

        crawler.addSeed("http://news.hfut.edu.cn/");

        //设置爬虫是否以断点模式爬取
        //如果设置为true，爬虫会在启动时继续以前的任务爬取
        //默认为false，如果为false，每次启动爬虫都会重新爬取
        //crawler.setResumable(true);

        //爬取3层，层与网站拓扑无关，它是遍历树中的层
        crawler.start(3);


    }
}
