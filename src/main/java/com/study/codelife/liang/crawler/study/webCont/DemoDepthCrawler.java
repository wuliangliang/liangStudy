package com.study.codelife.liang.crawler.study.webCont;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
/**
 * Created by doubling_ruc on 2017/1/17.
 */
public class DemoDepthCrawler extends BreadthCrawler{
    public DemoDepthCrawler(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
    }


    public void visit(Page page, CrawlDatums next) {
        System.out.println("visiting:"+page.getUrl()+"\tdepth="+page.meta("depth"));
    }

    @Override
    protected void afterParse(Page page, CrawlDatums next) {
        //当前页面的depth为x，则从当前页面解析的后续任务的depth为x+1
        int depth;
        //如果在添加种子时忘记添加depth信息，可以通过这种方式保证程序不出错
        if(page.meta("depth")==null){
            depth=1;
        }else{
            depth=Integer.valueOf(page.meta("depth"));
        }
        depth++;
        for(CrawlDatum datum:next){
            datum.meta("depth", depth+"");
        }
    }



    public static void main(String[] args) throws Exception {
        DemoDepthCrawler crawler=new DemoDepthCrawler("depth_crawler", true);
        for(int i=1;i<=5;i++){
            crawler.addSeed(new CrawlDatum("http://news.hfut.edu.cn/list-1-"+i+".html")
                    .meta("depth", "1"));
        }
        /*正则规则用于控制爬虫自动解析出的链接，用户手动添加的链接，例如添加的种子、或
          在visit方法中添加到next中的链接并不会参与正则过滤*/
        /*自动爬取类似"http://news.hfut.edu.cn/show-xxxxxxhtml"的链接*/
        crawler.addRegex("http://news.hfut.edu.cn/show-.*html");
        /*不要爬取jpg|png|gif*/
        crawler.addRegex("-.*\\.(jpg|png|gif).*");
        /*不要爬取包含"#"的链接*/
        crawler.addRegex("-.*#.*");

        crawler.setTopN(5);

        crawler.start(3);
    }

}
