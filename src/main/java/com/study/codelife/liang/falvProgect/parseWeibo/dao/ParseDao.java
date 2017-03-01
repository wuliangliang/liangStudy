package com.study.codelife.liang.falvProgect.parseWeibo.dao;
import com.study.codelife.liang.falvProgect.parseWeibo.Util.JDBCTemp;
import com.study.codelife.liang.falvProgect.parseWeibo.Util.WeiBoDomain;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by liang on 2017/2/25.
 */
public class ParseDao {
    private static final JdbcTemplate jdbcTemplate =  JDBCTemp.createMysqlTemplate("mysql1",
            "jdbc:mysql://localhost/mylaw?useUnicode=true&characterEncoding=utf8",
            "root", "root", 5, 30);
    public List<WeiBoDomain> selectALl(){
        return  jdbcTemplate.query("select * from weibo", new RowMapper<WeiBoDomain>() {
            public WeiBoDomain mapRow(ResultSet resultSet, int i) throws SQLException {
                WeiBoDomain weibo  =new WeiBoDomain();
                weibo.setId(resultSet.getInt("id"));
                weibo.setUuid(resultSet.getString("uuid"));
                weibo.setTime(resultSet.getString("time"));
                weibo.setContent(resultSet.getBlob("content"));
                weibo.setContentLook(resultSet.getString("content_look"));
                weibo.setRetweet(resultSet.getBlob("retweet"));
                weibo.setContentLook(resultSet.getString("retweet_look"));
                 return weibo;
            }
        } );
    }
    @Test
    public void test() throws SQLException {
        List<WeiBoDomain> weiBos= selectALl();
        for(WeiBoDomain weibo :weiBos){
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
        }
    }
}
