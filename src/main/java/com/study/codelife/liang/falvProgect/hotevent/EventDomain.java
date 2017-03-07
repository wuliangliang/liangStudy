package com.study.codelife.liang.falvProgect.hotevent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.ansj.domain.Result;

public class EventDomain {
	private ArrayList<ArrayList<String>> allN ; //每个新闻名词的集合
	private HashSet<String> day ;  //时间的集合
	private HashMap<Integer,ArrayList<ArrayList<String>>> allStr;   //某一个时间所有的新闻
	private HashSet<String> nounSet; //所有名词的集合
	private HashMap<String, HashMap<Integer, Double>> rij;//计算rij
	private HashMap<String, HashMap<Integer, Integer>> rijInt;//计算rijInt
	private HashMap<String ,ArrayList<String>> getResult;//根据得到相应的内同容
	private HashMap<String ,HashMap<Integer,ArrayList<String>>> rijWord;//每个词每个时间的新闻
	
	public HashMap<String, HashMap<Integer, ArrayList<String>>> getRijWord() {
		return rijWord;
	}
	public void setRijWord(
			HashMap<String, HashMap<Integer, ArrayList<String>>> rijWord) {
		this.rijWord = rijWord;
	}
	public HashMap<String, ArrayList<String>> getGetResult() {
		return getResult;
	}
	public void setGetResult(HashMap<String, ArrayList<String>> getResult) {
		this.getResult = getResult;
	}
	public HashMap<String, HashMap<Integer, Integer>> getRijInt() {
		return rijInt;
	}
	public void setRijInt(HashMap<String, HashMap<Integer, Integer>> rijInt) {
		this.rijInt = rijInt;
	}
	public HashSet<String> getNounSet() {
		return nounSet;
	}
	public void setNounSet(HashSet<String> nounSet) {
		this.nounSet = nounSet;
	}
	public ArrayList<ArrayList<String>> getAllN() {
		return allN;
	}
	public void setAllN(ArrayList<ArrayList<String>> allN) {
		this.allN = allN;
	}
	public HashSet<String> getDay() {
		return day;
	}
	public void setDay(HashSet<String> day) {
		this.day = day;
	}
	
	public HashMap<Integer, ArrayList<ArrayList<String>>> getAllStr() {
		return allStr;
	}
	public void setAllStr(HashMap<Integer, ArrayList<ArrayList<String>>> allStr) {
		this.allStr = allStr;
	}
	public HashMap<String, HashMap<Integer, Double>> getRij() {
		return rij;
	}
	public void setRij(HashMap<String, HashMap<Integer, Double>> rij) {
		this.rij = rij;
	}
	
	
}
