package com.study.codelife.liang.falvProgect.parseWeibo.parse;

import com.study.codelife.liang.Util.fileUtil;
import com.study.codelife.liang.falvProgect.parseWeibo.Util.WeiBo;
import com.study.codelife.liang.falvProgect.parseWeibo.Util.WeiBoDomain;
import com.study.codelife.liang.falvProgect.parseWeibo.dao.ParseDao;

import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by liang on 2017/3/1.
 */
public class LoadDada {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public TreeMap<Long,ArrayList<WeiBo>>  loaddate() throws SQLException, IOException, ParseException {
        HashSet<String> biaoqing = new HashSet<String>();
        /*
        *测试的东西
        * */
        BufferedWriter bw = fileUtil.writeToFile("C:\\Users\\liang\\Desktop\\Crawl\\temp\\biaoqing.txt");
        /*
            正式开始
         */
        ParseDao parseDao = new ParseDao();
        TreeMap<Long,ArrayList<WeiBo>> weiBoMap= new TreeMap<Long, ArrayList<WeiBo>>();
        List<WeiBoDomain> weiBos= parseDao.selectALl();
        for(WeiBoDomain weibo :weiBos){
            WeiBo wb = new WeiBo();
            System.out.println("weibo.getId()"+weibo.getId());
            System.out.println("weibo.getUuid()"+weibo.getUuid());
            System.out.println("weibo.getTime()"+weibo.getTime());
            System.out.println("weibo.getContentLook()"+weibo.getContentLook());
            System.out.println("weibo.getRetweetLook()"+weibo.getRetweetLook());
            //****
            String contenLooked = weibo.getContentLook();
            String retweetLooked = weibo.getRetweetLook();
            if(contenLooked!=null){
                String[] look = contenLooked.split(",");
                for(String str :look){
                    biaoqing.add(str);
                }
            }
            if(retweetLooked!=null){
                String[] look = retweetLooked.split(",");
                for(String str :look){
                    biaoqing.add(str);
                }
            }
            //****
            Blob contentBlob = weibo.getContent();
            Blob retweetBlob = weibo.getRetweet();
            int blobContentlen = (int)contentBlob.length();
            int blobretweetlen = (int)retweetBlob.length();
            String content = new String(contentBlob.getBytes(1,blobContentlen));
            String retweet= new String(retweetBlob.getBytes(1,blobretweetlen));
            System.out.println("content"+ content);
            System.out.println("retweet"+retweet);
            wb.setId(weibo.getId());
            wb.setUuid(weibo.getUuid());
            wb.setTime(weibo.getTime());
            wb.setContent(content.replaceAll("<a.*?</a>","").replaceAll("<span.*?</span>","").replaceAll("<img.*?>","").replaceAll("<i.*?</i>","").replaceAll("//:","").replaceAll("查看帮助：http://t.cn/.{0,10}",""));
            wb.setContentLook(weibo.getContentLook());
            wb.setRetweet(retweet.replaceAll("<a.*?</a>","").replaceAll("<span.*?</span>","").replaceAll("<img.*?>","").replaceAll("<i.*?</i>","").replaceAll("//:","").replaceAll("查看帮助：http://t.cn/.{0,10}",""));
            wb.setRetweetLook(weibo.getRetweetLook());
            Long TDate = sdf.parse(weibo.getTime()).getTime();
           if(weiBoMap.get(TDate)==null){
               ArrayList<WeiBo> weiboList = new ArrayList<WeiBo>();
               weiboList.add(wb);
               weiBoMap.put(TDate,weiboList);
           }else{
               ArrayList<WeiBo> weiboList = weiBoMap.get(TDate);
               weiboList.add(wb);
               weiBoMap.put(TDate,weiboList);
           }
            //测试
            /*
            bw.write(content.replaceAll("<a.*?</a>","").replaceAll("<span.*?</span>","").replaceAll("<img.*?>","").replaceAll("<i.*?</i>","").replaceAll("//:","").replaceAll("查看帮助：http://t.cn/.{0,10}",""));
            bw.write("\n");
            bw.write(retweet.replaceAll("<a.*?</a>","").replaceAll("<span.*?</span>","").replaceAll("<img.*?>","").replaceAll("<i.*?</i>","").replaceAll("//:","").replaceAll("查看帮助：http://t.cn/.{0,10}",""));
            bw.write("\n");
            */
        }

        //for循环结束
        for(String str : biaoqing){
            bw.write(str);
            bw.write("\n");
        }

        bw.flush();
        return weiBoMap;
    }
    public static void main(String[] args) throws SQLException, IOException, ParseException {
        LoadDada loadDada  = new LoadDada();
        TreeMap<Long,ArrayList<WeiBo>> weiBoList=loadDada.loaddate();
       // for( )
    }


}
