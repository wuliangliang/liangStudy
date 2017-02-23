package com.study.codelife.liang.crawler.study.weibo;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;

/**
 * Created by doubling_ruc on 2017/1/18.
 */
public class tets {
    public static void main(String args[]) throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException{

        //新浪微博登录页面
        String baseUrl = "https://passport.weibo.cn/signin/login?entry=mweibo&res=wel&wm=3349&r=http%3A%2F%2Fm.weibo.cn%2F";


        //打开
        WebClient webClient = new WebClient(BrowserVersion.CHROME);

        webClient.addRequestHeader("User-Agent", "Mozilla/5.0 (iPad; CPU OS 7_0_2 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A501 Safari/9537.53");

        //webClient.addRequestHeader("User-Agent", "Mozilla/5.0 (Linux; Android 4.4.2; Nexus 4 Build/KOT49H) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.122 Mobile Safari/537.36");

        HtmlPage page = webClient.getPage(baseUrl);

        //等待页面加载
        Thread.sleep(1000);

        //获取输入帐号的控件
        HtmlInput usr = (HtmlInput) page.getElementById("loginName");

        usr.setValueAttribute("weibo_abb_123@163.com");

        //获取输入密码的控件
        HtmlInput pwd = (HtmlInput) page.getElementById("loginPassword");

        pwd.setValueAttribute("weiboabb123");

        //点击登录
        DomElement button = page.getElementById("loginAction");


        page =(HtmlPage) button.click();


        //等待页面加载
        Thread.sleep(1000);


        //获取到“写微博”这个按钮，因为这个按钮没有name和id,获取所有<a>标签
        DomNodeList<DomElement> button2 = page.getElementsByTagName("a");


        //跳转到发送微博页面
        page =(HtmlPage)button2.get(4).click();

        //等待页面加载
        Thread.sleep(1000);


        //得到cookie
        System.out.println("cookie: " +page.getWebClient().getCookies(page.getUrl()));


        /*
        *
        *
        *
        * 发送微博
        *
        *
        *
        * */


        //获取发送控件 标签为<a>中的2个
        DomNodeList<DomElement> button3 = page.getElementsByTagName("a");
        //获取文本宇
        HtmlTextArea content =(HtmlTextArea) page.getElementById("txt-publisher");

        DomElement fasong = button3.get(1);

        content.focus();

        Date date = new Date();

        //填写你要发送的内容
        content.setText("使用JAVA发送微博策四微博你哦是佛山南非可能林尼克斯分！！！！\n"+date);



        //改变发送按钮的属性，不能无法发送
        fasong.setAttribute("class", "fr txt-link");

        //发送！！！
        page = (HtmlPage)fasong.click();

        Thread.sleep(5000);

        System.out.println(page.asText());


    }
}
