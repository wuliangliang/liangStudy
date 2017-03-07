package com.study.codelife.liang.falvProgect.parseWeibo.parse;

import com.study.codelife.liang.falvProgect.parseWeibo.Util.WeiBo;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.recognition.impl.FilterRecognition;
import org.ansj.splitWord.analysis.NlpAnalysis;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

/**
 * Created by liang on 2017/3/2.
 */
public class ParseWeiBo {
    public static void main(String[] args) throws ParseException, SQLException, IOException {
        String filePath = "C:\\Users\\liang\\Desktop\\Crawl\\temp\\weiboDate.txt";

    }

    //返回跟热点事件相关的微博
    public  TreeMap<Long,ArrayList<WeiBo>> findWeibo(ArrayList<String> hotvent) throws ParseException, SQLException, IOException {
        TreeMap<Long,ArrayList<WeiBo>> weiboHot = new TreeMap<Long,ArrayList<WeiBo>>();
        LoadDada loadDada = new LoadDada();
        TreeMap<Long,ArrayList<WeiBo>> dataMap =  loadDada.loaddate();
        for(Map.Entry<Long,ArrayList<WeiBo>> weiboEntry:dataMap.entrySet()){
            //处理每一天的微博
            for(WeiBo weibo:weiboEntry.getValue()){
                //处理每一篇微博
                String content = weibo.getContent();
                String retweet = weibo.getRetweet();
                Result con = fenciNlp(content);
                Result ret = fenciNlp(retweet);
                ArrayList<String> contentList= new ArrayList<String>();
                ArrayList<String> retweetList = new ArrayList<String>();
                for(Term term:con){
                    contentList.add(term.getName());
                }
                for(Term term:ret){
                    retweetList.add(term.getName());
                }
                if(isHot(contentList,hotvent)||isHot(retweetList,contentList)){
                    weibo.setContentList(contentList);
                }
                if(isHot(retweetList,hotvent)){
                    weibo.setRetweetList(retweetList);
                }

            }
        }
        return weiboHot;
    }

    //统计总共的情绪
    public TreeMap<Long,HashMap<String,Integer>> qxCount(TreeMap<Long,ArrayList<WeiBo>> hotMap){
        TreeMap<Long,HashMap<String,Integer>> dataCount = new TreeMap<Long, HashMap<String,Integer>>();
        for(Map.Entry<Long,ArrayList<WeiBo>> entry:hotMap.entrySet()){
            //每天的表情
            for(WeiBo weibo:entry.getValue()){
                
            }
        }
        return dataCount;
    }


    public static Result fenciNlp(String str){
        FilterRecognition filter = new FilterRecognition();
        filter.insertStopNatures("null");
        return NlpAnalysis.parse(str).recognition(filter);
    }

    public boolean isHot(ArrayList<String>weibo,ArrayList<String> hotEvent){
        for(String event : hotEvent){
            if(weibo.contains(event)){
                return true;
            }
        }
        return false;
    }
}
