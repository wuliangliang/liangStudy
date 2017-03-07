package com.study.codelife.liang.falvProgect.hotevent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class simlrity {
	
	//每个文档，每个词的得分
	public static ArrayList<LinkedHashMap<String, Float>> Cos(HashMap<String, HashMap<String, Float>> vecs){ //两个文档
		HashSet<String> wordSet= new HashSet<String>();
		for(Entry<String, HashMap<String,Float>> vec:vecs.entrySet()){
			for(Entry<String ,Float> word:vec.getValue().entrySet()){
				wordSet.add(word.getKey());
			}
		}
		
		ArrayList<LinkedHashMap<String, Float>> veclink = new ArrayList<LinkedHashMap<String, Float>>();
		for(Entry<String, HashMap<String,Float>> vec:vecs.entrySet()){
			LinkedHashMap<String, Float> ff= new LinkedHashMap<String, Float>();
			for(String str:wordSet){
				boolean flag = false;
				for(Entry<String ,Float> word:vec.getValue().entrySet()){
					if(str.equals(word.getKey())){
						ff.put(word.getKey(), word.getValue());
						flag=true;
						break;
					}
				}
				if(flag == false){
					ff.put(str, 0.f);
				}
			}
			veclink.add(ff);
		}
		
		return veclink;
	}
	
	/**
	 * 
	* @Title: calculateCos
	* @Description: 计算余弦相似性
	* @param @param first
	* @param @param second
	* @param @return    
	* @return Double   
	* @throws
	 */
	private static Double calculateCos(LinkedHashMap<String, Float> first,LinkedHashMap<String, Float> second){
 
		List<Entry<String, Float>> firstList = new ArrayList<Entry<String, Float>>(first.entrySet());
		List<Entry<String, Float>> secondList = new ArrayList<Entry<String, Float>>(second.entrySet());
		//计算相似度  
        double vectorFirstModulo = 0.00;//向量1的模  
        double vectorSecondModulo = 0.00;//向量2的模  
        double vectorProduct = 0.00; //向量积  
        int secondSize=second.size();
		for(int i=0;i<firstList.size();i++){
			if(i<secondSize){
				vectorSecondModulo+=secondList.get(i).getValue().doubleValue()*secondList.get(i).getValue().doubleValue();
				vectorProduct+=firstList.get(i).getValue().doubleValue()*secondList.get(i).getValue().doubleValue();
			}
			vectorFirstModulo+=firstList.get(i).getValue().doubleValue()*firstList.get(i).getValue().doubleValue();
		}
	   return vectorProduct/(Math.sqrt(vectorFirstModulo)*Math.sqrt(vectorSecondModulo));
	}

	public static  Double cosSim(HashMap<String, HashMap<String, Float>> vecs){
		ArrayList<LinkedHashMap<String, Float>> Cos =Cos(vecs);
		if(Cos.size()<2){
			return 0.0;
		}else{
		return calculateCos(Cos.get(0),Cos.get(1));
		}
	}
	public static void main(String[] args) {
		
	}
}

	

