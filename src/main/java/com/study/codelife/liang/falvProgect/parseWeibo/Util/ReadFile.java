package com.study.codelife.liang.falvProgect.parseWeibo.Util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by liang on 2017/2/18.
 */
public class ReadFile {
    public static void main(String[] args) throws IOException {

        ReadFile readFile = new ReadFile();
       // readFile.parseFile();

    }
    public static   HashMap<String,LinkedList<File>> traverseFolder1(String path) {
        int fileNum = 0, folderNum = 0;
        File file = new File(path);
        LinkedList<File> list =null;
        LinkedList<File> fileList=null;
        LinkedList<File> empty=null;
        HashMap<String,LinkedList<File>> map = new HashMap<String,LinkedList<File>>();
        if (file.exists()) {
            list = new LinkedList<File>();
            fileList = new LinkedList<File>();
            empty = new LinkedList<File>();
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
                    //System.out.println("文件夹:" + file2.getAbsolutePath());
                    list.add(file2);
                    fileNum++;
                } else if(file2.isFile()){
                    //System.out.println("文件:" + file2.getAbsolutePath());
                    if(file2.length()>0){
                        fileList.add(file2);
                    }else{
                        empty.add(file2);
                    }
                    folderNum++;
                }
            }
            File temp_file;
            while (!list.isEmpty()) {
                temp_file = list.removeFirst();
                files = temp_file.listFiles();
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        //System.out.println("文件夹:" + file2.getAbsolutePath());
                        list.add(file2);
                        fileNum++;
                    } else if(file2.isFile()){
                       // System.out.println("文件:" + file2.getAbsolutePath());
                        if(file.length()>0) {
                            fileList.add(file2);
                        }else{
                         empty.add(file2)   ;
                        }
                        folderNum++;
                    }
                }
            }

        } else {
            System.out.println("文件不存在!");
        }
        System.out.println("文件共有:" + folderNum + ",文件夹共有:" + fileNum);
        map.put("notEmpty",fileList);
        map.put("empty",empty);
        return map;
    }

    static public String filterOffUtf8Mb4(String text) throws UnsupportedEncodingException {
        byte[] bytes = text.getBytes("UTF-8");
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        int i = 0;
        while (i < bytes.length) {
            short b = bytes[i];
            if (b > 0) {
                buffer.put(bytes[i++]);
                continue;
            }
            b += 256;
            if ((b ^ 0xC0) >> 4 == 0) {
                buffer.put(bytes, i, 2);
                i += 2;
            }
            else if ((b ^ 0xE0) >> 4 == 0) {
                buffer.put(bytes, i, 3);
                i += 3;
            }
            else if ((b ^ 0xF0) >> 4 == 0) {
                i += 4;
            }
        }
        buffer.flip();
        return new String(buffer.array(), "utf-8");
    }


}
