package com.study.codelife.liang.crawler.study.xiaowenDemo;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.net.HttpRequest;
import cn.edu.hfut.dmic.webcollector.net.HttpResponse;
import cn.edu.hfut.dmic.webcollector.net.Proxys;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import com.study.codelife.liang.crawler.util.FileUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by doubling_ruc on 2017/1/20.
 */
public class BaiduCrawl extends BreadthCrawler {
    static BufferedWriter bw = FileUtil.writeToFile("/Users/doubling_ruc/Desktop/retultD.txt");
    static BufferedWriter bwe = FileUtil.writeToFile("/Users/doubling_ruc/Desktop/error.txt");

    static Random random = new Random();
    int wwww=0;

    Proxys proxys = new Proxys();

    public BaiduCrawl(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
//        proxys.add("58.213.19.233", 10081);
//        proxys.add("171.126.133.143", 8888);
//        proxys.add("220.248.230.217", 3128);
        proxys.addEmpty();
    }

    @Override
    public HttpResponse getResponse(CrawlDatum crawlDatum) throws Exception {
        HttpRequest request = new HttpRequest(crawlDatum);
        request.setProxy(proxys.nextRandom());
        return request.getResponse();
    }

    public void visit(Page page, CrawlDatums next) {
        synchronized (BaiduCrawl.class) {
            wwww+=1;
            System.out.println(wwww);
            try {
                String str = "http://www.baidu.com/s\\?wd=(.*)%20(.*)&rsv_bp=0&rsv_spt=3&rsv_n=2&inputT=6391";
                Pattern pattern = Pattern.compile(str);
                String strrr = page.getUrl().toString().trim();
                Matcher m = pattern.matcher(strrr);
                if (m.find()) {
                    System.out.println(m.group());
                    String name = m.group(1);
                    String patt = m.group(2);
                    bw.write(String.valueOf(wwww));
                    bw.write("  ");
                    bw.write(name);
                    bw.write("  ");
                    bw.write(patt);
                    bw.write("  ===  ");

                } else {
                    bwe.write(page.getUrl().toString().trim());
                    bwe.write("\n");
                }
                //m.group(1);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Document doc = page.doc();
            Elements elements = page.select("div[class=result c-container ]");
            for (Element element : elements) {
                String title = element.select("div[id=" + element.id() + "]>h3").text();
                String abstract_ = element.select("div[class=c-abstract]").text();
                String abstract_name = abstract_.replaceAll("\\d{0,4}年\\d{0,2}月\\d{0,2}日|\\t|-", "");
                try {
                    bw.write(title);
                    bw.write("@@@@@@");
                    bw.write(abstract_name);
                    bw.write("  ");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //System.out.println("title: " + title);
                //System.out.println("abstract_: " + abstract_name);
            }
            try {
                bw.write("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                bw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //System.out.println(page.getHtml());
    }

    public static void main(String[] args) throws Exception {

        int i = 1;

        BaiduCrawl crawler = new BaiduCrawl("baiduCrawl", false);
        crawler.setThreads(1);
        Scanner pattern_file = new Scanner(new File("/Users/doubling_ruc/Desktop/patterns.txt"));
        Scanner disease_file = new Scanner(new File("/Users/doubling_ruc/Desktop/disease1.txt"));
        Set<String> patternSet = new HashSet<String>();
        while (pattern_file.hasNext()) {
            patternSet.add(pattern_file.nextLine().trim());
        }
        //System.out.println(patternSet);
        while (disease_file.hasNext()) {
            String disease_name = disease_file.nextLine();
            for (String pattern : patternSet) {
                String pattern_name = pattern;
                String search_url = "http://www.baidu.com/s?wd=" + disease_name + "%20" + pattern_name + "&rsv_bp=0&rsv_spt=3&rsv_n=2&inputT=6391";
                crawler.addSeed(search_url);
                i++;
                System.out.println("正在打印第" + i + "个url，请等待。。。。。。。");
            }
        }
        crawler.setExecuteInterval(2000+random.nextInt(5000));
        crawler.start(1);
        try {
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bw.close();
    }
}
