package com.study.codelife.liang.crawl.study.weibo;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.net.HttpRequest;
import cn.edu.hfut.dmic.webcollector.net.HttpResponse;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * 利用WebCollector和获取的cookie爬取新浪微博并抽取数据
 * Created by doubling_ruc on 2017/1/18.
 */
public class WeiboCrawler extends BreadthCrawler{
    String cookie;

    public WeiboCrawler(String crawlPath, boolean autoParse) throws Exception {
        super(crawlPath, autoParse);
        /*获取新浪微博的cookie，账号密码以明文形式传输，请使用小号*/
        cookie = WeiboCN.getSinaCookie("weibo_abb_123@163.com", "weiboabb123");
    }

    @Override
    public HttpResponse getResponse(CrawlDatum crawlDatum) throws Exception {
        HttpRequest request = new HttpRequest(crawlDatum);
        request.setCookie(cookie);
        return request.getResponse();
    }


    public void visit(Page page, CrawlDatums next) {
        int pageNum = Integer.valueOf(page.getMetaData("pageNum"));
        /*抽取微博*/
        Elements weibos = page.select("div.c");
        for (Element weibo : weibos) {
            System.out.println("第" + pageNum + "页\t" + weibo.text());
        }
    }

    public static void main(String[] args) throws Exception {
        WeiboCrawler crawler = new WeiboCrawler("weibo_crawler", false);
        crawler.setThreads(3);
        /*对某人微博前5页进行爬取*/
        for (int i = 1; i <= 5; i++) {
            crawler.addSeed(new CrawlDatum("http://weibo.cn/zhouhongyi?vt=4&page=" + i)
                    .putMetaData("pageNum", i + ""));
        }
        crawler.start(1);
    }
}
