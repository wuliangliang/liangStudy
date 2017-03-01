package com.study.codelife.liang.falvProgect.parseWeibo.Util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by doubling on 2016/12/2.
 */
public class FileUtil {
    public static BufferedWriter writeToFile(String fileName) {
        File file = new File(fileName);
        // if file doesnt exists, then create it
        BufferedWriter bw=null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            bw = new BufferedWriter(new FileWriter(file, true));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Done");
        return bw;
    }

    public static void closeFile(BufferedWriter bw){
        if(bw!=null){
            try {
                bw.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
