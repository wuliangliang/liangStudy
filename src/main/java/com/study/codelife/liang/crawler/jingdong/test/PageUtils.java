package com.study.codelife.liang.crawler.jingdong.test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;

import cn.edu.hfut.dmic.webcollector.model.Page;
/**
 * Created by doubling_ruc on 2017/1/19.
 */
public class PageUtils {
    public static HtmlUnitDriver getDriver(Page page) {
        HtmlUnitDriver driver = new HtmlUnitDriver();
        driver.setJavascriptEnabled(true);
        driver.get(page.getUrl());
        return driver;
    }

    public static HtmlUnitDriver getDriver(Page page, BrowserVersion browserVersion) {
        HtmlUnitDriver driver = new HtmlUnitDriver(browserVersion);
        driver.setJavascriptEnabled(true);
        driver.get(page.getUrl());
        return driver;
    }


    /**
     * 获取PhantomJsDriver(可以爬取js动态生成的html)
     *
     * @param page
     * @return
     */
    public static WebDriver getWebDriver(Page page) {
//      WebDriver driver = new HtmlUnitDriver(true);

//      System.setProperty("webdriver.chrome.driver", "D:\\Installs\\Develop\\crawling\\chromedriver.exe");
//      WebDriver driver = new ChromeDriver();

       // System.setProperty("phantomjs.binary.path", "D:/Program Files/phantomjs-2.0.0-windows/bin/phantomjs.exe");
        WebDriver driver = new PhantomJSDriver();
        driver.get(page.getUrl());

//      JavascriptExecutor js = (JavascriptExecutor) driver;
//      js.executeScript("function(){}");
        return driver;
    }

    public static String getPhantomJSDriver(Page page) {
        Runtime rt = Runtime.getRuntime();
        Process process = null;
        try {
            process = rt.exec("D:/Program Files/phantomjs-2.0.0-windows/bin/phantomjs.exe" +
                    "D:/MyEclipseWorkSpace/WebCollectorDemo/src/main/resources/parser.js " +
                    page.getUrl().trim());
            InputStream in = process.getInputStream();
            InputStreamReader reader = new InputStreamReader(
                    in, "UTF-8");
            BufferedReader br = new BufferedReader(reader);
            StringBuffer sbf = new StringBuffer();
            String tmp = "";
            while((tmp = br.readLine())!=null){
                sbf.append(tmp);
            }
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
