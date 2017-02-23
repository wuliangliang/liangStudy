package com.study.codelife.liang.crawler.study.weibo;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Set;

/**
 * Created by doubling_ruc on 2017/2/23.
 *
 * 模拟微博登陆，获得cookie
 *
 * 很吊
 *
 * 邮箱：liang_ruc@163.com
 *
 * 于美团
 */
public class AnalogLogin {
    public String getCookie(String userName ,String passWord)throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException
    {
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
        usr.setValueAttribute(userName);
        //获取输入密码的控件
        HtmlInput pwd = (HtmlInput) page.getElementById("loginPassword");
        pwd.setValueAttribute(passWord);
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
        Set<Cookie> cookieSet = page.getWebClient().getCookies(page.getUrl());
        String cookie = cookieSet.toString();
        System.out.println("cookie: "+cookie.substring(1,cookie.length()-1));
        return cookie.substring(1,cookie.length()-1);
    }
    @Test
    public void test(){
        try {
            System.out.println(getCookie("weibo_abb_123@163.com","weiboabb123"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
