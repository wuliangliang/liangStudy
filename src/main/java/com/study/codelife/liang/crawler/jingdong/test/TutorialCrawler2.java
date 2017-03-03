package com.study.codelife.liang.crawler.jingdong.test;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import org.jsoup.nodes.Document;

import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;
/**
 * Created by doubling_ruc on 2017/1/19.
 */
public class TutorialCrawler2 extends BreadthCrawler {
    public TutorialCrawler2(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);

        /*BreadthCrawler可以直接添加URL正则规则*/
        this.addRegex("http://item.jd.com/.*.html");
//        this.addRegex("http://.*zhihu.com/.*");
//        this.addRegex("-.*jpg.*");
    }
    public void visit(Page page, CrawlDatums next) {
        Document doc  = page.doc();
        String title = doc.title();
        System.out.println("URL:" + page.getUrl() + "  标题:" + title);
    }
    public void visit(Page page, Links nextLinks) {

//        System.out.println(doc.html());

        /*
        //添加到nextLinks的链接会在下一层或下x层被爬取，爬虫会自动对URL进行去重，所以用户在编写爬虫时完全不必考虑生成重复URL的问题。
        //如果这里添加的链接已经被爬取过，则链接不会在后续任务中被爬取
        //如果需要强制添加已爬取过的链接，只能在爬虫启动（包括断点启动）时，通过Crawler.addForcedSeed强制加入URL。
         nextLinks.add("http://www.csdn.net");
        */
    }

    public static void main(String[] args) throws Exception {
         /*
        第一个参数是爬虫的crawlPath，crawlPath是维护URL信息的文件夹的路径，如果爬虫需要断点爬取，每次请选择相同的crawlPath
        第二个参数表示是否自动抽取符合正则的链接并加入后续任务
     */
        TutorialCrawler2 crawler = new TutorialCrawler2("D:/testTest/crawler/demo",true);
        crawler.setThreads(50);
        crawler.addSeed("http://list.jd.com/list.html?cat=1319,1523,7052&page=1&go=0&JL=6_0_0");
//     crawler.addSeed("http://www.zhihu.com/");
        crawler.setResumable(false);

     /*
     //requester是负责发送http请求的插件，可以通过requester中的方法来指定http/socks代理
     HttpRequesterImpl requester=(HttpRequesterImpl) crawler.getHttpRequester();

     //单代理
     requester.setProxy("127.0.0.1", 1080,Proxy.Type.SOCKS);

     //多代理随机
     RandomProxyGenerator proxyGenerator=new RandomProxyGenerator();
     proxyGenerator.addProxy("127.0.0.1",8080,Proxy.Type.SOCKS);
     requester.setProxyGenerator(proxyGenerator);
     */


     /*设置是否断点爬取*/
        crawler.setResumable(false);
     /*设置每层爬取爬取的最大URL数量*/
        crawler.setTopN(1000);

     /*如果希望尽可能地爬取，这里可以设置一个很大的数，爬虫会在没有待爬取URL时自动停止*/
        crawler.start(2);
    }


}
