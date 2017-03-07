package com.study.codelife.liang.falvProgect.parseWeibo.todataBase;

import com.csvreader.CsvReader;
import com.study.codelife.liang.Util.fileUtil;
import com.study.codelife.liang.falvProgect.parseWeibo.Util.JDBCTemp;
import com.study.codelife.liang.falvProgect.parseWeibo.Util.ReadFile;
import com.study.codelife.liang.falvProgect.parseWeibo.Util.WeiBo;
import org.junit.Test;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liang on 2017/2/18.
 */
public class parseWeibo {
    private static final String sss = "<i class=\"face face_[0-9]{0,3} icon_[0-9]{0,3}\">\\[(.{1,5})\\]</i>";
    private static final Pattern p = Pattern.compile(sss);
    private static final JdbcTemplate jdbcTemplate =  JDBCTemp.createMysqlTemplate("mysql1",
            "jdbc:mysql://localhost/mylaw?useUnicode=true&characterEncoding=utf8",
            "root", "root", 5, 30);

    private static final SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm");

    @Test
    public void parseFile() throws IOException, ParseException {
        LinkedList<File> fileList = ReadFile.traverseFolder1("C:\\Users\\liang\\Desktop\\Crawl\\resultFile").get("notEmpty");
        System.out.println(fileList.size());
        String outputFile = "C:\\Users\\liang\\Desktop\\Crawl\\output.txt";
        BufferedWriter bw = fileUtil.writeToFile(outputFile);
        ArrayList<WeiBo> list = null;
        for (File file : fileList) {
            list= new ArrayList<WeiBo>();
            CsvReader reader = new CsvReader(file.getAbsolutePath(), ',', Charset.forName("utf-8"));
            while (reader.readRecord()) {
                WeiBo weibo = new WeiBo();
                String uuidll = reader.get(0);
                String uuid = ReadFile.filterOffUtf8Mb4(uuidll);
                String time = reader.get(1);
                String contentll=reader.get(2);
                String retweetll =reader.get(3);
                String content= ReadFile.filterOffUtf8Mb4(contentll);
                String retweet =ReadFile.filterOffUtf8Mb4(retweetll);
                String contentLook = "";
                String retweetLook = "";
                String contenta = content.replaceAll("<a href='http://m.weibo.cn/n/.{0,20}'>@.{0,20}</a>", "").replaceAll("\uD83D\uDC02",""); //加正则匹配不需要的标签
                Matcher m = p.matcher(contenta);
                while (m.find()) {
                    // System.out.println(m.group(1));
                    contentLook = contentLook + m.group(1) + ",";
                }
                String contentL = contenta.replaceAll(sss, ""); //加正则匹配不需要的标签
                String retweeta = retweet.replaceAll("<a href='http://m.weibo.cn/n/.{0,20}'>@.{0,20}</a>", "").replaceAll("\uD83D\uDC02",""); //加正则匹配不需要的标签
                Matcher m2 = p.matcher(retweeta);
                while (m2.find()) {
                    //System.out.println(m2.group(1));
                    retweetLook = retweetLook + m2.group(1) + ",";
                }
                String retweetL = retweeta.replaceAll(sss, ""); //加正则匹配不需要的标签
                weibo.setUuid(uuid);
                weibo.setTime(time);
                System.out.println(time);
                weibo.setContent(new String(contentL.getBytes("utf-8"),"utf-8"));
                weibo.setContentLook(contentLook);
                weibo.setRetweet(retweetL);
                weibo.setRetweetLook(retweetLook);
                list.add(weibo);
            }
        }
        insert(list);
    }

    public void insert(ArrayList<WeiBo> weibos){
        System.out.println(weibos.size());
        for(final WeiBo weibo :weibos) {
            //Assert.isNull(weibo,"weibo is not null");
            System.out.println(weibo.getUuid());
            jdbcTemplate.update("insert into weiboreal(uuid,time,content,content_look,retweet,retweet_look) values(?,?,?,?,?,?)", new PreparedStatementSetter() {
                public void setValues(PreparedStatement preparedStatement) throws SQLException {
                    preparedStatement.setString(1,weibo.getUuid());
                    preparedStatement.setString(2, weibo.getTime());
                    preparedStatement.setString(3,weibo.getContent());
                    preparedStatement.setString(4,weibo.getContentLook());
                    preparedStatement.setString(5,weibo.getRetweet());
                    preparedStatement.setString(6,weibo.getRetweetLook());
                }
            });
        }
    }
    public int[] batchUpdate(final List users) {
        int[] updateCounts = jdbcTemplate.batchUpdate(
                "update weibo set uuid=?,time=?,content=?,content_look=?,retweet=?,retweet_look=?",
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, ((WeiBo)users.get(i)).getUuid());
                        ps.setString(2, ((WeiBo)users.get(i)).getTime());
                        ps.setString(3,((WeiBo)users.get(i)).getContent());
                        ps.setString(4,((WeiBo)users.get(i)).getContentLook());
                        ps.setString(5,((WeiBo)users.get(i)).getRetweet());
                        ps.setString(6,((WeiBo)users.get(i)).getRetweetLook());
                    }
                    public int getBatchSize() {
                        return users.size();
                    }
                }
        );
        return updateCounts;
    }


    @Test
    public void createDataBase(){
        try {
    /*创建数据表*/
            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS tb_content ("
                    + "id int(11) NOT NULL AUTO_INCREMENT,"
                    + "title varchar(50),url varchar(200),html longtext,"
                    + "PRIMARY KEY (id)"
                    + ") ENGINE=MyISAM DEFAULT CHARSET=utf8;");
            System.out.println("成功创建数据表 tb_content");
        } catch (Exception ex) {
            System.out.println("mysql未开启或JDBCHelper.createMysqlTemplate中参数配置不正确!");
        }
    }
}
