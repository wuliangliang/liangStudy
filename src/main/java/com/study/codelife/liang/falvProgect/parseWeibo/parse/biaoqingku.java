package com.study.codelife.liang.falvProgect.parseWeibo.parse;

import com.study.codelife.liang.Util.fileUtil;
import org.apache.xpath.SourceTree;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by liang on 2017/3/2.
 */
public class biaoqingku {
    //得到表情
    public HashMap<String,Integer> look() throws FileNotFoundException {
        HashMap<String,Integer> lookMap = new HashMap<String, Integer>();
        Scanner scanner = new Scanner(new File("C:\\Users\\liang\\Desktop\\Crawl\\temp\\biaoqing.txt"));
        while(scanner.hasNext()){
            String line = scanner.nextLine();
            String[] lines = line.split("\\s+");
            lookMap.put(lines[0],Integer.valueOf(lines[1]));
        }
        return lookMap;
    }
    //通过表情到到分
    public int getFen(String str) throws FileNotFoundException {
        HashMap<String,Integer> fenMap = look();
        return fenMap.get(str);
    }
    @Test
    public void test() throws FileNotFoundException {
        HashMap<String,Integer> lookMap =look();
        for(Map.Entry<String,Integer> entry:lookMap.entrySet()){
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }
    }

}
