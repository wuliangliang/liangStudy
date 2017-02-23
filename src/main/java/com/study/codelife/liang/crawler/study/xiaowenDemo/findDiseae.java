package com.study.codelife.liang.crawler.study.xiaowenDemo;

import com.study.codelife.liang.crawler.util.FileUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Created by doubling_ruc on 2017/2/20.
 */
public class findDiseae {
    public static void main(String[] args) throws IOException {

        BufferedWriter bw = FileUtil.writeToFile("/Users/doubling_ruc/Desktop/disease_result_distinct.txt");

        Scanner scanner = new Scanner(new File("/Users/doubling_ruc/Desktop/disease_result.txt"));
        HashSet<String>  strSet = new HashSet<String>();
        while(scanner.hasNext()){
            String line = scanner.nextLine();
            if (line==null){
                continue;
            }
            for(String str :line.split(",")){
                if(str!=null) {
                    strSet.add(str);
                }
            }
        }
        for(String str: strSet){
            bw.write(str);
            bw.write("\n");
        }
        bw.flush();
    }
}
