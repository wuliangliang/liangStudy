package com.study.codelife.liang.falvProgect.WeiboCrawler;

import com.study.codelife.liang.Util.fileUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;


public class splitUserId {
	public static void main(String[] args) throws IOException {
		String xiangxi = "C:\\Users\\liang\\Desktop\\Crawl\\UserId.txt" ;//文件保存路径  2
		BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(xiangxi), "utf-8"));
		String line = "";
		int idNum = 0;
		int fileNum = 1;
		LinkedList<String> list  = new LinkedList<String>();
		while((line = input.readLine()) != null){
			if(idNum == fileNum * 100){
				writeToFile(list, fileNum);
				list.clear();
				fileNum++;
			}
			list.add(line);
			idNum++;
			System.out.println(idNum);
			
		}//while((line = input.readLine()) != null){

	}
	
	public static void writeToFile(LinkedList<String> list, int fileNum) throws IOException{
		String outPut = "C:\\Users\\liang\\Desktop\\Crawl\\userId\\UserId"+fileNum+".txt";
		BufferedWriter bw  = fileUtil.writeToFile(outPut);
		for(String line : list ){
			bw.write(line);
			bw.write("\n");
		}
		bw.flush();
		if(bw==null){
			bw.close();
		}
		System.out.println("第"+fileNum+"个文件写入完毕");
	}
}
