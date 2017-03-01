package com.study.codelife.liang.falvProgect.parseWeibo.parse;

import com.study.codelife.liang.falvProgect.parseWeibo.Util.WeiBo;
import com.study.codelife.liang.falvProgect.parseWeibo.Util.WeiBoDomain;
import com.study.codelife.liang.falvProgect.parseWeibo.dao.ParseDao;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liang on 2017/3/1.
 */
public class LoadDada {
    public List<WeiBo> loaddate()throws SQLException {
        ParseDao parseDao = new ParseDao();
        ArrayList<WeiBo> weiBoList = new ArrayList<WeiBo>();
        List<WeiBoDomain> weiBos= parseDao.selectALl();
        for(WeiBoDomain weibo :weiBos){
            WeiBo wb = new WeiBo();
            System.out.println("weibo.getId()"+weibo.getId());
            System.out.println("weibo.getUuid()"+weibo.getUuid());
            System.out.println("weibo.getTime()"+weibo.getTime());
            //System.out.println("weibo.getContent()"+weibo.getContent());
            System.out.println("weibo.getContentLook()"+weibo.getContentLook());
            //System.out.println("weibo.getRetweet()"+weibo.getRetweet());
            System.out.println("weibo.getRetweetLook()"+weibo.getRetweetLook());
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
            wb.setContent(content);
            wb.setContentLook(weibo.getContentLook());
            wb.setRetweet(retweet);
            wb.setRetweetLook(weibo.getRetweetLook());
            weiBoList.add(wb);
        }
        return weiBoList;
    }


    public static void main(String[] args) throws SQLException {
        LoadDada loadDada  = new LoadDada();
        loadDada.loaddate();
    }


}
