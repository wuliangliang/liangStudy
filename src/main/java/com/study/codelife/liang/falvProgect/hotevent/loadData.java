package com.study.codelife.liang.falvProgect.hotevent;

import com.study.codelife.liang.falvProgect.parseWeibo.Util.JDBCTemp;
import com.study.codelife.liang.falvProgect.parseWeibo.Util.WeiBo;
import com.study.codelife.liang.falvProgect.parseWeibo.Util.WeiBoDomain;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liang on 2017/3/7.
 */
public class loadData {
    private static final JdbcTemplate jdbcTemplate =  JDBCTemp.createMysqlTemplate("mysql1",
            "jdbc:mysql://localhost/ruclaw?useUnicode=true&characterEncoding=utf8",
            "root", "root", 5, 30);
    private static final JdbcTemplate jdbcTemplate1 =  JDBCTemp.createMysqlTemplate("mysql1",
            "jdbc:mysql://222.29.197.238/EventTeller?useUnicode=true&characterEncoding=utf8",
            "dbdm", "mysql@ET453", 5, 30);

    public static List<Event> selectALl(){
        return  jdbcTemplate.query("select * from hotevent where day BETWEEN 17171 AND 17202 and topic = 4096", new RowMapper<Event>() {
            public Event mapRow(ResultSet resultSet, int i) throws SQLException {
                Event event  =new Event();
                event.setId(resultSet.getInt("id"));
                event.setTitle(resultSet.getString("title"));
                event.setPubTime(resultSet.getDate("pubtime"));
                event.setContent(resultSet.getString("content"));
                event.setNumber(resultSet.getInt("number"));
                event.setDay(resultSet.getInt("day"));
                event.setTopic(resultSet.getInt("topic"));
                return event;
            }
        } );


    }
    public static List<Event> selectALlEvent() {
        return jdbcTemplate1.query("select * from Event where day BETWEEN 17171 AND 17202 and topic = 4096", new RowMapper<Event>() {
            public Event mapRow(ResultSet resultSet, int i) throws SQLException {
                Event event = new Event();
                event.setId(resultSet.getInt("id"));
                event.setTitle(resultSet.getString("title"));
                event.setPubTime(resultSet.getDate("pubtime"));
                event.setContent(resultSet.getString("content"));
                event.setNumber(resultSet.getInt("number"));
                event.setDay(resultSet.getInt("day"));
                event.setTopic(resultSet.getInt("topic"));
                return event;
            }
        });
    }


    @Test
    public void test(){
        List<Event> ll= selectALlEvent();
        System.out.println(ll.size());
        ArrayList<Event> weibos  = new ArrayList<Event>();
        for(Event event:ll){
            int times =event.getNumber();
            for(int i=0 ;i<times;i++) {
                weibos.add(event);
            }
        }

        insert(weibos);
    }

    public void insert(ArrayList<Event> weibos){
        System.out.println(weibos.size());
        for(final Event weibo :weibos) {
            //Assert.isNull(weibo,"weibo is not null");
            System.out.println(weibo.getId());
            jdbcTemplate.update("insert into hotevent(title,pubtime,content,source,imgs,number,day,topic) values(?,?,?,?,?,?,?,?)", new PreparedStatementSetter() {
                public void setValues(PreparedStatement preparedStatement) throws SQLException {
                    preparedStatement.setString(1,weibo.getTitle());
                    preparedStatement.setDate(2, weibo.getPubTime());
                    preparedStatement.setString(3,weibo.getContent());
                    preparedStatement.setString(4,weibo.getSource());
                    preparedStatement.setString(5,weibo.getImgs());
                    preparedStatement.setInt(6,weibo.getNumber());
                    preparedStatement.setInt(7,weibo.getDay());
                    preparedStatement.setInt(8,weibo.getTopic());

                }
            });
        }
    }
}
