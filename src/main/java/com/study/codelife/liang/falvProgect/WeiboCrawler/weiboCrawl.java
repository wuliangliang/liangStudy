package com.study.codelife.liang.falvProgect.WeiboCrawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Scanner;

import com.study.codelife.liang.Util.fileUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.csvreader.CsvWriter;


public class weiboCrawl {

	private static CsvWriter outputCsv = null;
	private static String urlRawPre = "http://m.weibo.com/";
	//	http://m.weibo.cn/page/json?containerid=1005051916655407_-_WEIBO_SECOND_PROFILE_WEIBO&page=2
    private static String todayDate = "";
	private static final String strA ="http://m.weibo.cn/container/getIndex?containerid=230413";
	private static final String strB ="_-_WEIBO_SECOND_PROFILE_MORE_WEIBO&page=";
	private static String jsonUrlPre = "http://m.weibo.cn/page/json?containerid=";
	private static String jsonUrlMiddle = "_-_WEIBO_SECOND_PROFILE_WEIBO&page=";
	private static String starttime = "2017-2-5";
	private static String endtime = "2017-1-5";
	private static long startTime=0;
	private static long endTime = 0;
	private static SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
	static{
		try {
			startTime =sd.parse(starttime).getTime();
			endTime = sd.parse(endtime).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	  public static void init(){
	        Date date=new Date();//取时间
	        Calendar calendar = new GregorianCalendar();
	        calendar.setTime(date);
	        date=calendar.getTime(); //这个时间就是日期往后推一天的结果
	        System.out.println(date);
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	        todayDate = formatter.format(date);
	    }

	public  static void init(String outputFile) throws UnsupportedEncodingException, FileNotFoundException {
		BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile, false),"utf-8"));
		outputCsv = new CsvWriter(output, ',');
	}

	//输入uid文件，为每个uid解析此处改为多线程以后改===============

	public static void crawlInput(String inputFile) throws IOException, InterruptedException {
		BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "utf-8"));
		String line = "";
		String fileName = "C:\\Users\\liang\\Desktop\\Crawl\\log.txt";
		BufferedWriter bw = fileUtil.writeToFile(fileName);
		bw.write(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		bw.write("\n");
		while((line = input.readLine()) != null){   //每一个人UID
			System.out.println(line);
			bw.write(line+"开始解析");
			bw.write("\n");
			crawlHelper(line);
			Thread.sleep(5000+(int)(Math.random()*4000));
			bw.write(line+"完成解析");
			bw.write("\n");

		}//while((line = input.readLine()) != null){
		bw.close();
		input.close();
	}


	//这个是根据每个uid进行解析
	public static void crawlHelper(String uid) throws IOException, InterruptedException {

		init("C:\\Users\\liang\\Desktop\\Crawl\\resultNew2\\reslut"+uid+".csv");
		String containId = strA+uid+strB; //get  Url
		for(int pageNum = 1;pageNum < 60;pageNum ++){ 
			System.out.println("uid: "+uid+"; 第"+pageNum+"页");
			//判断时间and终止条件//最后一页
			String jsonUrl = containId + pageNum;
			String jsonDoc =getReturnData(jsonUrl);
	
			if(jsonDoc.length() < 1000 || jsonDoc.contains("\"msg\":\"\u6ca1\u6709\u5185\u5bb9\"}") || jsonDoc.contains("\"msg\":\"没有内容\"")){
				continue;
			}
			//这个下面就是解析json得到多个time和content
			boolean con = parserJson(uid,jsonDoc,outputCsv);     //解析json
			if(con==false){
				break;
			}
			Thread.sleep(8000+(int)(Math.random()*8000));
		}
		
		outputCsv.flush();
		outputCsv.close();
		
	}
	
	
	 public static String getReturnData(String urlString) throws UnsupportedEncodingException, FileNotFoundException {
	        Scanner scanner = new Scanner(new File("C:\\Users\\liang\\Desktop\\Crawl\\cookieabb.txt"));
	        String cookie = null;
	        while(scanner.hasNext()){
	            cookie = scanner.nextLine();
	        }
	        String res = "";
	        try {
	            URL url = new URL(urlString);
	            java.net.HttpURLConnection conn = (java.net.HttpURLConnection)url.openConnection();
	            conn.setDoOutput(true);
	            conn.setRequestMethod("GET");
	            conn.setRequestProperty("Cookie",cookie);
	            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
	            conn.setReadTimeout(10000);
	            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
	            String line;
	            while ((line = in.readLine()) != null) {
	                res += line;
	            }
	            in.close();
	        } catch (Exception e) {
	            System.out.println("error in wapaction,and e is " + e.getMessage());
	        }
	        return res;
	    }

	    /**
	     * 解析json得到多个time和content 并写文件
	     * @param uid
	     * @param jsonStr
	     * @throws IOException
	     */

	    public static boolean parserJson(String uid,String jsonStr,CsvWriter outputCsv) throws IOException {
	        init();
	        JSONObject json = JSONObject.parseObject(jsonStr);
	        JSONArray dataArr = json.getJSONArray("cards");
	        String time = "";
	        String content = "";
	        String retweeted_statusText = "";
	        for(int i =1;i <dataArr.size();i++) {
	            JSONObject dataObj = dataArr.getJSONObject(i);
	            System.out.println(i);
	            if(dataObj.getJSONObject("mblog")==null){
	            	continue;
	            }
	            JSONObject mblog = dataObj.getJSONObject("mblog");
	            time = mblog.getString("created_at");
	            
	            if(time.contains("今天")||time.contains("分钟前")||time.contains("2015")||time.contains("2014")){
	            	continue;
	            }
	            time = time.replace("今天", todayDate);
	            if (!time.trim().startsWith("2016")) {
	                time = "2017-" + time;
	            }
	            long curTime =0;
	            try {
	    			curTime = sd.parse(time).getTime();
	    		} catch (ParseException e) {
	    			// TODO Auto-generated catch block
	    			curTime= 0;
	    		}
	            if(curTime>startTime){
	            	continue;
	            }
	            if(curTime<endTime){
	            	return false;
	            }
	            System.out.println(time);
	            content = mblog.getString("text");
	            System.out.println(content);
	            JSONObject retweeted_status = mblog.getJSONObject("retweeted_status");
	            try {
	                retweeted_statusText = retweeted_status.getString("text");
	            } catch (NullPointerException e) {
	                retweeted_statusText = "";
	            }
	            String[] record = new String[]{uid, time, content, retweeted_statusText};
	            System.out.println(record);
	            
	            //输出保存文件
	            outputCsv.writeRecord(record);
	        }
	        return true;
	    }
	    
	    
	    
	    
	    
	//从选人的页面选取要爬的人

	public static void getUrl(int page,String storeFile) throws IOException, InterruptedException {

		Scanner scanner = new Scanner(new File("C:\\Users\\liang\\Desktop\\Crawl\\cookieacc.txt"));
		String cookie=null;
		while(scanner.hasNext()){
			cookie = scanner.nextLine();
		}
		HashSet<String> urlSet = new HashSet<String>();
		// String xiangxi = "/Users/doubling/Desktop/pachong/urlUser3.txt";
		BufferedWriter bw = fileUtil.writeToFile(storeFile);
		for (int i = 1; i <= page; i++) {

			//爬去页面

			String jsonUrl = "http://d.weibo.com/1087030002_2975_5013_0?page=" + i + "#Pl_Core_F4RightUserList__4";
			Document doc = Jsoup.connect(jsonUrl).
					header("Host", "weibo.com").
					header("Cookie",cookie).
					userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36").timeout(10000).get();
			String buffer = doc.toString();

			//每隔1000mm爬取一次

			Thread.sleep(1000);

			//对页面进行解析

			Document document = Jsoup.parse(buffer);
			Elements nodes = document.getElementsByTag("script");
			String str = nodes.get(nodes.size() - 1).toString();
			int end = 0;
			while (str != null) {
				end = str.indexOf("usercard");
				if (end == -1) {
					break;
				}
				String uid = str.substring(end + 14, end + 24);
				if (!urlSet.contains(uid)) {
					urlSet.add(uid);
				}
				str = str.substring(end + 13);
			}
			System.out.println("第"+i+"页已经完成");
		}

		//最后将url写入文件

		for(String str:urlSet){
			bw.write(str);
			bw.write("\n");
		}
		bw.close();
		System.out.println(urlSet.size());
	}
	
}
