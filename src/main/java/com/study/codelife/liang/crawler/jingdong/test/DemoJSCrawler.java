package com.study.codelife.liang.crawler.jingdong.test;
import java.util.List;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;

import cn.edu.hfut.dmic.webcollector.model.Page;
/**
 * Created by doubling_ruc on 2017/1/19.
 */
public class DemoJSCrawler extends BreadthCrawler {

    public DemoJSCrawler(String crawlPath, boolean autoParse){
        super( crawlPath,  autoParse);
    }
    public void visit(Page page, CrawlDatums next) {
        handleByPhantomJsDriver(page);
    }

    protected void handleByHtmlUnitDriver(Page page){
         /*HtmlUnitDriver可以抽取JS生成的数据*/
        HtmlUnitDriver driver=PageUtils.getDriver(page,BrowserVersion.CHROME);
      /*HtmlUnitDriver也可以像Jsoup一样用CSS SELECTOR抽取数据
        关于HtmlUnitDriver的文档请查阅selenium相关文档*/
        print(driver);
    }

    /**
     * phantomjs driver测试
     *
     * @param page
     */
    protected void handleByPhantomJsDriver(Page page){
        WebDriver driver=PageUtils.getWebDriver(page);
        print(driver);
        driver.quit();
    }

    protected void print(WebDriver driver) {
        List<WebElement> divInfos = driver.findElements(By.cssSelector("li.gl-item"));
        for (WebElement divInfo : divInfos) {
            WebElement price = divInfo.findElement(By.className("J_price"));
            System.out.println(price + ":" + price.getText());
        }
    }

}
