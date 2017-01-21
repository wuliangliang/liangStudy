package com.study.codelife.liang.crawler.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by doubling_ruc on 2017/1/20.
 */
public class FileUtil {
    public static BufferedWriter writeToFile(String path)  {
        File file = new File(path);
        // if file doesnt exists, then create it
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("文件创建失败");
            }
        }
        FileWriter fw = null;
        try {
            fw = new FileWriter(file.getAbsoluteFile(),false);
        } catch (IOException e) {
            System.out.println("写入文件失败");
        }
        BufferedWriter bw = new BufferedWriter(fw);
       return bw;
    }
}
