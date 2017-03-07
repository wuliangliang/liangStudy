package com.study.codelife.liang.falvProgect.parseWeibo.Util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.io.BufferedOutputStream;
import  java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.junit.Test;


public class Util {
	@Test
//	public void test(){
//
//		HashMap<String,Float> ma  = new HashMap<>();
//		ma.put("dsadwa", (float) 0.6);
//		ma.put("dswawdwa", (float) 0.4);
//		ma.put("dswadwa", (float) 0.7);
//		ma.put("dsawwwwwda", (float) 0.1);
//		HashMap<String,Float> maa =(HashMap<String, Float>) sortMapByFloatValue(ma);
//		System.out.println(maa);
//	}
	

	
	
	public static void main(String[] args) {
		String filename = "C:\\Users\\liang\\Desktop\\falv\\result\\input.txt";
		BufferedWriter bwinput = Util.writeToFile(
				"C:\\Users\\liang\\Desktop\\falv\\result\\temp.txt", false);
		File file = new File(filename);
		BufferedReader reader = null;
		BufferedReader readerDa = null;
		try {
			//reader = new BufferedReader(new FileReader(file)); // 如果是读大文件
			readerDa = new BufferedReader(new FileReader(file),1*1024*1024);
			String tempString = null;
			while ((tempString = readerDa.readLine()) != null)
			{
				bwinput.write(tempString);
				System.out.println(tempString);
				// 进行操作.....
			}
			bwinput.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null)

			{
				try {
					reader.close();
					readerDa.close();

				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}


		
	/**
	 * 使用 Map按value进行排序
	 * 
	 * @param
	 * @return
	 */
	public static HashMap<HashMap<String, Double>, Double> sortMapByValueDouble(
			Map<HashMap<String, Double>, Double> oriMap) {
		if (oriMap == null || oriMap.isEmpty()) {
			return null;
		}
		HashMap<HashMap<String, Double>, Double> sortedMap = new LinkedHashMap<HashMap<String, Double>, Double>();
		List<Entry<HashMap<String, Double>, Double>> entryList = new ArrayList<Entry<HashMap<String, Double>, Double>>(
				oriMap.entrySet());
		Collections.sort(entryList, new MapValueComparatorDouble());

		Iterator<Entry<HashMap<String, Double>, Double>> iter = entryList
				.iterator();
		Entry<HashMap<String, Double>, Double> tmpEntry = null;
		while (iter.hasNext()) {
			tmpEntry = iter.next();
			sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
		}
		return sortedMap;
	}

	/**
	 * 使用 Map按value进行排序
	 * 
	 * @param
	 * @return
	 */
	public static Map<String, Integer> sortMapByValueInt(
			Map<String, Integer> oriMap) {
		if (oriMap == null || oriMap.isEmpty()) {
			return null;
		}
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		List<Entry<String, Integer>> entryList = new ArrayList<Entry<String, Integer>>(
				oriMap.entrySet());
		Collections.sort(entryList, new MapValueComparatorInt());

		Iterator<Entry<String, Integer>> iter = entryList.iterator();
		Entry<String, Integer> tmpEntry = null;
		while (iter.hasNext()) {
			tmpEntry = iter.next();
			sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
		}
		return sortedMap;
	}

	/**
	 * 使用 Map按value进行排序
	 * 
	 * @param
	 * @return
	 */
	public static Map<String, String> sortMapByValue(Map<String, String> oriMap) {
		if (oriMap == null || oriMap.isEmpty()) {
			return null;
		}
		Map<String, String> sortedMap = new LinkedHashMap<String, String>();
		List<Entry<String, String>> entryList = new ArrayList<Entry<String, String>>(
				oriMap.entrySet());
		Collections.sort(entryList, new MapValueComparator());

		Iterator<Entry<String, String>> iter = entryList.iterator();
		Entry<String, String> tmpEntry = null;
		while (iter.hasNext()) {
			tmpEntry = iter.next();
			sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
		}
		return sortedMap;
	}
	
	public static Map<String, Float> sortMapByFloatValue(Map<String, Float> oriMap) {
		if (oriMap == null || oriMap.isEmpty()) {
			return null;
		}
		Map<String, Float> sortedMap = new LinkedHashMap<String, Float>();
		List<Entry<String, Float>> entryList = new ArrayList<Entry<String, Float>>(
				oriMap.entrySet());
		Collections.sort(entryList, new MapValueComparatorFloat());

		Iterator<Entry<String, Float>> iter = entryList.iterator();
		Entry<String, Float> tmpEntry = null;
		while (iter.hasNext()) {
			tmpEntry = iter.next();
			sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
		}
		return sortedMap;
	}
	

	public static BufferedWriter writeToFile(String fileName, Boolean bool) {
		File file = new File(fileName);
		// if file doesnt exists, then create it
		BufferedWriter bw = null;
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			bw = new BufferedWriter(new FileWriter(file, bool));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bw;
	}

	public static void closeFile(BufferedWriter bw) {
		if (bw != null) {
			try {
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

class MapValueComparatorDouble implements
		Comparator<Entry<HashMap<String, Double>, Double>> {

	public int compare(Entry<HashMap<String, Double>, Double> me1,
			Entry<HashMap<String, Double>, Double> me2) {

		return me2.getValue() > me1.getValue() ? 1 : -1;
	}
}

class MapValueComparatorInt implements Comparator<Entry<String, Integer>> {


	public int compare(Entry<String, Integer> me1, Entry<String, Integer> me2) {

		return me2.getValue() - me1.getValue();
	}
}

class MapValueComparatorFloat implements Comparator<Entry<String, Float>> {


	public int compare(Entry<String, Float> me1, Entry<String, Float> me2) {

		return me2.getValue() - me1.getValue()>0?1:0;
	}
}


class MapValueComparator implements Comparator<Entry<String, String>> {

	public int compare(Entry<String, String> me1, Entry<String, String> me2) {

		return -(me1.getValue().compareTo(me2.getValue()));
	}
}
 

