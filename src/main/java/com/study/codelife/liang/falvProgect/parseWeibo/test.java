package com.study.codelife.liang.falvProgect.parseWeibo;

import com.csvreader.CsvReader;
import com.study.codelife.liang.Util.fileUtil;
import com.study.codelife.liang.falvProgect.parseWeibo.Util.ReadFile;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liang on 2017/1/3.
 */
public class test {
    public static void main(String[] args) {
        String str1="<a href='http://m.weibo.cn/n/宋建芬律师'>@宋建芬律师</a>";
        String str2="<i class=\"\"face face_1 icon_19\"\">[笑cry]</i>\n";
        System.out.println(str1.replaceAll("<a.*href='http://m.weibo.cn/n.*>",""));
    }
    @Test
    public  void test() throws IOException {
//        LinkedList<File> fileList = ReadFile.traverseFolder1("C:\\Users\\liang\\Desktop\\Crawl\\resultFile").get("notEmpty");
        String fileName = "C:\\Users\\liang\\Desktop\\Crawl\\resultFile\\reslut1050089427.csv";
        String outputFile = "C:\\Users\\liang\\Desktop\\Crawl\\output.txt";
        BufferedWriter bw = fileUtil.writeToFile(outputFile);
        CsvReader reader = new CsvReader(fileName,',', Charset.forName("utf-8"));
        while(reader.readRecord()){
            String content= ReadFile.filterOffUtf8Mb4( reader.get(2));
            String contenta = content.replaceAll("<a.*?</a>","").replaceAll("<span.*?</span>","").replaceAll("<img.*?>","").replaceAll("<i.*?</i>","").replaceAll("//:","").replaceAll("查看帮助：http://t.cn/.{0,10}","");
            bw.write(contenta);
            bw.write("  ===   ");
            String retweet = ReadFile.filterOffUtf8Mb4( reader.get(3));
            String retweeta = retweet.replaceAll("<a.*?</a>","").replaceAll("<span.*?</span>","").replaceAll("<img.*?>","").replaceAll("<i.*?</i>","").replaceAll("//:","").replaceAll("查看帮助：http://t.cn/.{0,10}","");
            bw.write(retweeta);
            bw.write("\n");
           bw.flush();
        }
    }
    @Test
    public void test2() throws IOException {
        BufferedWriter bw = fileUtil.writeToFile("C:\\Users\\liang\\Desktop\\Crawl\\notFile");
        String sss = "reslut(.*?).csv";
        Pattern p = Pattern.compile(sss);
        LinkedList<File> fileList = ReadFile.traverseFolder1("C:\\Users\\liang\\Desktop\\Crawl\\resultFile").get("notEmpty");
        LinkedList<String> notempty = new LinkedList<String>();
        LinkedList<String> all = new LinkedList<String>();
        LinkedList<String> empty = new LinkedList<String>();
        for(File file:fileList){
            String fileName= file.getName();
            Matcher m = p.matcher(fileName);
            while (m.find()) {
                String uuid = m.group(1);
                notempty.add(uuid);
            }
        }
        Scanner scanner =new Scanner(new File("C:\\Users\\liang\\Desktop\\Crawl\\UserId.txt"));
        while(scanner.hasNext()){
            all.add(scanner.nextLine());
        }
        for(String str : all){
            if(!notempty.contains(str)){
                empty.add(str);
                bw.write(str);
                bw.write("\n");
            }
        }
        bw.flush();
    }
}
