package com.study.codelife.liang.falvProgect.hotevent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class TF_IDF {
	private static ArrayList<String> FileList =null; // the
																			// list
																			// of
																			// file

	public static HashMap<String, Integer> normalTF(ArrayList<String> cutwords) {
		HashMap<String, Integer> resTF = new HashMap<String, Integer>();

		for (String word : cutwords) {
			if (resTF.get(word) == null) {
				resTF.put(word, 1);
				//System.out.println(word);
			} else {
				resTF.put(word, resTF.get(word) + 1);
				//System.out.println(word.toString());
			}
		}
		return resTF;
	}

	public static  HashMap<String, Float> tf(ArrayList<String> cutwords) {
		HashMap<String, Float> resTF = new HashMap<String, Float>();

		int wordLen = cutwords.size();
		HashMap<String, Integer> intTF = TF_IDF.normalTF(cutwords);

		Iterator iter = intTF.entrySet().iterator(); // iterator for that get
														// from TF
		while (iter.hasNext()) {
			Entry entry = (Entry) iter.next();
			resTF.put(entry.getKey().toString(),
					Float.parseFloat(entry.getValue().toString()) / wordLen);
//			System.out.println(entry.getKey().toString() + " = "
//					+ Float.parseFloat(entry.getValue().toString()) / wordLen);
		}
		return resTF;
	}

	public static  HashMap<String, Float> idf(
			HashMap<String, HashMap<String, Float>> all_tf) {
		HashMap<String, Float> resIdf = new HashMap<String, Float>();
		HashMap<String, Integer> dict = new HashMap<String, Integer>();
		int docNum = FileList.size();

		for (int i = 0; i < docNum; i++) {
			HashMap<String, Float> temp = all_tf.get(FileList.get(i));
			Iterator iter = temp.entrySet().iterator();
			while (iter.hasNext()) {
				Entry entry = (Entry) iter.next();
				String word = entry.getKey().toString();
				if (dict.get(word) == null) {
					dict.put(word, 1);
				} else {
					dict.put(word, dict.get(word) + 1);
				}
			}
		}
		//System.out.println("IDF for every word is:");
		Iterator iter_dict = dict.entrySet().iterator();
		while (iter_dict.hasNext()) {
			Entry entry = (Entry) iter_dict.next();
			float value = (float) Math.log(docNum
					/ Float.parseFloat(entry.getValue().toString()));
			resIdf.put(entry.getKey().toString(), value);
			//System.out.println(entry.getKey().toString() + " = " + value);
		}
		return resIdf;
	}

	public static  HashMap<String, HashMap<String, Float>> tf_idf(HashMap<String, HashMap<String, Float>> all_tf,
			HashMap<String, Float> idfs) {
		HashMap<String, HashMap<String, Float>> resTfIdf = new HashMap<String, HashMap<String, Float>>();

		int docNum = FileList.size();
		for (int i = 0; i < docNum; i++) {
			String filepath = FileList.get(i);
			HashMap<String, Float> tfidf = new HashMap<String, Float>();
			HashMap<String, Float> temp = all_tf.get(filepath);
			Iterator iter = temp.entrySet().iterator();
			while (iter.hasNext()) {
				Entry entry = (Entry) iter.next();
				String word = entry.getKey().toString();
				Float value = (float) Float.parseFloat(entry.getValue()
						.toString()) * idfs.get(word);
				tfidf.put(word, value);
			}
			resTfIdf.put(filepath, tfidf);
		}
		//System.out.println("TF-IDF for Every file is :");
		//DisTfIdf(resTfIdf);
		return resTfIdf;
	}

	public static  void DisTfIdf(HashMap<String, HashMap<String, Float>> tfidf) {
		Iterator iter1 = tfidf.entrySet().iterator();
		while (iter1.hasNext()) {
			Entry entrys = (Entry) iter1.next();
			System.out.println("FileName: " + entrys.getKey().toString());
			System.out.print("{");
			HashMap<String, Float> temp = (HashMap<String, Float>) entrys
					.getValue();
			Iterator iter2 = temp.entrySet().iterator();
			while (iter2.hasNext()) {
				Entry entry = (Entry) iter2.next();
				System.out.print(entry.getKey().toString() + " = "
						+ entry.getValue().toString() + ", ");
			}
			System.out.println("}");
		}

	}

	public  ArrayList<ArrayList<String>> idToString(ArrayList<String> ids,HashMap<String ,ArrayList<String>> getResult){
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		for(String id :ids){
			if(getResult.get(id)!=null){
				result.add(getResult.get(id));
			}
		}
		return result;
	}
	
	public static  HashMap<String,HashMap<String, Float>> tfAllFiles(HashMap<String,ArrayList<String>> docs) {
		FileList= new ArrayList<String>();
        HashMap<String, HashMap<String, Float>> allTF = new HashMap<String, HashMap<String, Float>>();
        for(Entry<String,ArrayList<String>> doc: docs.entrySet()){
        	FileList.add(doc.getKey());
            HashMap<String, Float> dict = new HashMap<String, Float>();
            ArrayList<String> cutwords = doc.getValue();
            dict = TF_IDF.tf(cutwords);
            allTF.put(doc.getKey(), dict);
        }
        return allTF;
	}
	
	public static HashMap<String, HashMap<String, Float>> TF_IDF(HashMap<String,ArrayList<String>> wordResult) {
		HashMap<String, HashMap<String, Float>> all_tf = tfAllFiles(wordResult);
		HashMap<String, Float> idfs = idf(all_tf);
		return tf_idf(all_tf, idfs);
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		HashMap<String, HashMap<String,ArrayList<String>>> wordResult = new HashMap<String, HashMap<String,ArrayList<String>>>();
		for (Entry<String, HashMap<String,ArrayList<String>>> entry : wordResult.entrySet()) {
			HashMap<String, HashMap<String, Float>> all_tf = tfAllFiles(entry.getValue());
			//System.out.println();	
			HashMap<String, Float> idfs = idf(all_tf);
			//System.out.println();
			tf_idf(all_tf, idfs);
		}
	}
}
