package com.study.codelife.liang.falvProgect.hotevent;
import com.study.codelife.liang.falvProgect.parseWeibo.Util.Util;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.recognition.impl.FilterRecognition;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.apdplat.word.analysis.*;
import org.apdplat.word.*;
import org.apdplat.word.segmentation.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.study.codelife.liang.falvProgect.parseWeibo.parse.ParseWeiBo.fenciNlp;

/**
 * Created by doubling on 2017/3/7.
 */
public class findevent {
    private static final int N = 40;
    private static final double X = 0;  //相似度
    private static final double Y = 0;  //相似度

    public static void main(String[] args) throws IOException {
        findevent hotEvent = new findevent();
        ArrayList<Event> eventList = hotEvent.getSourseDoc(); // 找到所有的新闻
        System.out.println("eventList  " + eventList.size());
        HashMap<String,Event> idDoc = hotEvent.idDoc(eventList);
        EventDomain eventDomain = hotEvent.findN(eventList);

        ArrayList<ArrayList<String>> allN = eventDomain.getAllN(); // 每个新闻名词的集合
        HashSet<String> day = eventDomain.getDay(); // 时间的集合
        HashMap<Integer, ArrayList<ArrayList<String>>> allStr = eventDomain
                .getAllStr(); // 某一个时间所有的新闻
        HashSet<String> nounSet = eventDomain.getNounSet(); // 所有名词的集合
        HashMap<String, HashMap<Integer, Double>> rij = eventDomain.getRij();
        HashMap<String, HashMap<Integer, ArrayList<String>>> rijWord = eventDomain
                .getRijWord();
        HashMap<String, ArrayList<String>> getResult = eventDomain
                .getGetResult();// 通过id来找到相应的新闻

        HashMap<String, ArrayList<String>> kurts = hotEvent.kurt(rij, rijWord);// 每个词，对应很多文档的id


    }

    public ArrayList<Event> getSourseDoc() {
        return (ArrayList<Event>) loadData.selectALl();
    }
    //找到热点词，计算TF—IDF
    public HashMap<String,Float> hotWord(ArrayList<String> ids,HashMap<String, ArrayList<String>> getResult){
        HashMap<String,Float> word = new HashMap<String,Float>();
        HashMap<String,ArrayList<String>> wordResult = new HashMap<String,ArrayList<String>>();

        for(String id:ids ){
            wordResult.put(id, getResult.get(id));
        }

        HashMap<String, HashMap<String, Float>> tfidf=TF_IDF.TF_IDF(wordResult);
        for(Map.Entry<String, HashMap<String, Float>> entry:tfidf.entrySet()){
            for( Map.Entry<String, Float> entryT:entry.getValue().entrySet()){
                word.put(entryT.getKey(),entryT.getValue());
            }
        }
        HashMap<String,Float> maa =(HashMap<String, Float>) Util.sortMapByFloatValue(word);
        return maa;
    }

    //合并集合
    public ArrayList<ArrayList<String>> hotMat(HashMap<String, HashMap<Integer,ArrayList<String>>> wordLeiFen,HashMap<String, ArrayList<String>> getResult){
        ArrayList<ArrayList<String>> hotMat = new ArrayList<ArrayList<String>> ();
        ConcurrentHashMap<ArrayList<String>,ArrayList<String>> hotMap= new ConcurrentHashMap<ArrayList<String>,ArrayList<String>>();
        HashMap<String, ArrayList<ArrayList<String>>> wordHot= toTo(wordLeiFen);

        //句子，组成这些句子的doc ID
        HashMap<ArrayList<String>,ArrayList<String>> idToStr = idToStr(wordHot,getResult);

        for(Map.Entry<ArrayList<String>,ArrayList<String>> entry:idToStr.entrySet()){
            if(hotMap.size()==0){
                hotMap.put(entry.getKey(), entry.getValue());
            }else{
                for(Map.Entry<ArrayList<String>,ArrayList<String>> entryT:hotMap.entrySet()){
                    HashMap<String,ArrayList<String>> wordResult = new HashMap<String,ArrayList<String>>();
                    wordResult.put("first",entryT.getKey());
                    wordResult.put("second",entry.getKey());
                    HashMap<String, HashMap<String, Float>> tfidf=TF_IDF.TF_IDF(wordResult);

                    Double sim = simlrity.cosSim(tfidf);
                    if(sim>=X){
                        ArrayList<String> temp = entryT.getValue();
                        temp.addAll(entry.getValue());
                        hotMap.put(entryT.getKey(),temp);
                    }else{
                        hotMap.put(entry.getKey(),entry.getValue());
                    }
                }
            }
        }
        for(Map.Entry<ArrayList<String>,ArrayList<String>> entry:hotMap.entrySet()){
            if(entry.getValue().size()>10){
                hotMat.add(entry.getValue());
            }
        }
        return hotMat;
    }


    public HashMap<ArrayList<String>,ArrayList<String>> idToStr(HashMap<String, ArrayList<ArrayList<String>>> wordHot,HashMap<String, ArrayList<String>> getResult){
        HashMap<ArrayList<String>,ArrayList<String>> res = new HashMap<ArrayList<String>,ArrayList<String>>();
        for(Map.Entry<String, ArrayList<ArrayList<String>>> entry:wordHot.entrySet()){
            for(ArrayList<String> list:entry.getValue()){
                ArrayList<String> idlist= new ArrayList<String>();
                ArrayList<String> doclist= new ArrayList<String>();
                for(String str:list){
                    idlist.add(str);
                    doclist.addAll(getResult.get(str));
                }
                res.put(doclist,idlist );
            }

        }
        return res;
    }
    //转化
    public HashMap<String, ArrayList<ArrayList<String>>> toTo(HashMap<String, HashMap<Integer,ArrayList<String>>> wordLeiFen){
        HashMap<String, ArrayList<ArrayList<String>>> to = new HashMap<String, ArrayList<ArrayList<String>>>();
        for(Map.Entry<String, HashMap<Integer,ArrayList<String>>> entry:wordLeiFen.entrySet()){
            ArrayList<ArrayList<String>> temp = new ArrayList<ArrayList<String>>();
            for(Map.Entry<Integer,ArrayList<String>> entryT:entry.getValue().entrySet()){
                temp.add(entryT.getValue());
            }
            to.put(entry.getKey(), temp);
        }
        return to;
    }
    //对于每一个词找到一定的相似度集合

    public HashMap<String, HashMap<Integer,ArrayList<String>>> wordDocSim(HashMap<String, ArrayList<String>> kurts,HashMap<String,Event> getResult){
        HashMap<String, HashMap<Integer,ArrayList<String>>> wordLeiFen = new HashMap<String, HashMap<Integer,ArrayList<String>>>();
        for (Map.Entry<String, ArrayList<String>> entry : kurts.entrySet()) { // key为每个词
            HashMap<Integer,ArrayList<String>> docColl = new HashMap<Integer,ArrayList<String>>();
            for (String str : entry.getValue()) {  //每一篇文档
                String word = entry.getKey();
                if (wordLeiFen.get(word) == null) {
                    ArrayList<String> docList = new ArrayList<String>();
                    docList.add(str);
                    docColl.put(0,docList);
                    wordLeiFen.put(word, docColl);
                }else{
                    boolean flag= false;
                    for(Map.Entry<Integer,ArrayList<String>> list:wordLeiFen.get(word).entrySet()){
                        String log = list.getValue().get(0);
                        Event first=getResult.get(list.getValue().get(0));
                        Event second=getResult.get(str);
//                        HashMap<String,ArrayList<String>> wordResult = new HashMap<String,ArrayList<String>>();
//                        wordResult.put(list.getValue().get(0),first);
//                        wordResult.put(str,second);
//                        HashMap<String, HashMap<String, Float>> tfidf=TF_IDF.TF_IDF(wordResult);wordResult
//						//=========测试开始
//						BufferedWriter bw = Util.writeToFile("c:/Users/liang/Desktop/falv/log/TF_IDF.txt",false);
//						for(Entry<String, HashMap<String, Float>> kurt:tfidf.entrySet()){
//							try {
//								bw.write(kurt.getKey());
//								bw.write("\n");
//								bw.write(kurt.getValue().entrySet().toString());
//								bw.write("\n");
//							} catch (IOException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//						}
//						try {
//							bw.flush();
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//						//===============测试结束
                        Double sim = similarity(first.getContent(),second.getContent());
                        if(sim>=Y){
                            ArrayList<String> temp =new ArrayList(list.getValue());
                            temp.add(str);
                            HashMap<Integer,ArrayList<String>> tempMap =wordLeiFen.get(word);
                            tempMap.put(list.getKey(),temp);
                            wordLeiFen.put(word,tempMap);
                            flag = true;
                            break;
                        }
                    }
                    if(flag ==false){
                        ArrayList<String> tem =  new ArrayList<String>();
                        tem.add(str);
                        HashMap<Integer,ArrayList<String>> tempMap =wordLeiFen.get(word);
                        tempMap.put(wordLeiFen.size(),tem);
                        wordLeiFen.put(word, tempMap);
                    }
                }
            }
        }
        return wordLeiFen;
    }

    public HashMap<String,Event> idDoc(ArrayList<Event> eventList){
        HashMap<String,Event> map = new HashMap<String, Event>();
        for(Event event:eventList){
            map.put(String.valueOf(event.getId()),event);
        }
        return map;
    }


    // 根据文档id得到相应的文档
    public Result getReslutById(String id) {
        return null;
    }

    // 遍历文档计算很多值
    public EventDomain findN(ArrayList<Event> eventList) throws IOException {
        HashSet<String> day = new HashSet<String>();
        HashMap<Integer, ArrayList<ArrayList<String>>> allStr = new HashMap<Integer, ArrayList<ArrayList<String>>>();
        HashSet<String> nounSet = new HashSet<String>();
        HashMap<String, HashMap<Integer, Integer>> rij = new HashMap<String, HashMap<Integer, Integer>>();
        HashMap<String, HashMap<Integer, ArrayList<String>>> rijWord = new HashMap<String, HashMap<Integer, ArrayList<String>>>();
        HashSet<String> setTemp = new HashSet<String>();
        HashMap<String, ArrayList<String>> getResult = new HashMap<String, ArrayList<String>>();
        for (Event event : eventList) {
            day.add(String.valueOf(event.getDay()));
            String id = String.valueOf(event.getId());
            ArrayList<String> eventAll = null; // 每个文档分完后的词
            setTemp.clear();
            if (event.getContent() != null) {
                eventAll = new ArrayList<String>();
                Result listNlp = fenciNlp(event.getContent()); // 对每一个文档分词
                for (Term term : listNlp) {
                    eventAll.add(term.getName());
                    // 统计名词集合
                    if (term.getNatureStr().startsWith("n")) {
                        nounSet.add(term.getName());
                        if (!setTemp.contains(term.getName())) { // 得到rijWord,没个词每天的所有的新闻
                            setTemp.add(term.getName());
                            if (rijWord.containsKey(term.getName())) {
                                if (rijWord.get(term.getName()).containsKey(
                                        event.getDay())) {
                                    rijWord.get(term.getName())
                                            .get(event.getDay()).add(id);
                                } else {
                                    HashMap<Integer, ArrayList<String>> map = rijWord
                                            .get(term.getName());
                                    ArrayList<String> list = new ArrayList<String>();
                                    list.add(id);
                                    map.put(event.getDay(), list);
                                    rijWord.put(term.getName(), map);
                                }
                            } else {
                                HashMap<Integer, ArrayList<String>> map = new HashMap<Integer, ArrayList<String>>();
                                ArrayList<String> list = new ArrayList<String>();
                                list.add(id);
                                map.put(event.getDay(), list);
                                rijWord.put(term.getName(), map);
                            }

                            if (rij.get(term.getName()) != null) { // 统计没个词的每天出现次数
                                if (rij.get(term.getName()).get(event.getDay()) != null) {
                                    HashMap<Integer, Integer> maptemp = rij
                                            .get(term.getName());
                                    maptemp.put(event.getDay(),
                                            maptemp.get(event.getDay()) + 1);
                                    rij.put(term.getName(), maptemp);
                                } else {
                                    HashMap<Integer, Integer> maptemp = rij
                                            .get(term.getName());
                                    maptemp.put(event.getDay(), 1);
                                    rij.put(term.getName(), maptemp);
                                }
                            } else {
                                HashMap map = new HashMap<Integer, Integer>();
                                map.put(event.getDay(), 1);
                                rij.put(term.getName(), map);
                            }
                        }
                    }
                }
                // 每天的所有的新闻
                if (allStr.containsKey(event.getDay())) {
                    allStr.get(event.getDay()).add(eventAll);
                } else {
                    ArrayList<ArrayList<String>> temp = new ArrayList<ArrayList<String>>();
                    temp.add(eventAll);
                    allStr.put(event.getDay(), temp);
                }
            }
            getResult.put(id, eventAll);

        }
        // 因为这个代码太长了所以要加一个注释
        // 第一个for循环退出
        HashMap<String, HashMap<Integer, Double>> rijRes = new HashMap<String, HashMap<Integer, Double>>(); // 每个词，每天的分数
        for (Map.Entry<String, HashMap<Integer, Integer>> entry : rij.entrySet()) {
            if (entry.getValue().size() < N * 0.2) {
                continue;
            }
            HashMap<Integer, Double> fenshuMap = fenshuMap = new HashMap<Integer, Double>();
            for (Map.Entry<Integer, Integer> entryT : entry.getValue().entrySet()) {

                double fenshu = (double) entryT.getValue()
                        / allStr.get(entryT.getKey()).size();
                fenshuMap.put(entryT.getKey(), fenshu);
            }
            rijRes.put(entry.getKey(), fenshuMap);
        }
        EventDomain eventDomain = new EventDomain();
        eventDomain.setRijInt(rij);
        eventDomain.setAllStr(allStr);
        eventDomain.setDay(day);
        eventDomain.setNounSet(nounSet);
        eventDomain.setRij(rijRes);
        eventDomain.setRijWord(rijWord);
        eventDomain.setGetResult(getResult);
        return eventDomain;
    }


    // 返回每个词的kurt值(返回前N个kurt的值 )
    public HashMap<String, ArrayList<String>> kurt(
            HashMap<String, HashMap<Integer, Double>> rijs,
            HashMap<String, HashMap<Integer, ArrayList<String>>> rijWord)
            throws IOException {
        HashMap<HashMap<String, Double>, Double> kurtMap = new HashMap<HashMap<String, Double>, Double>();

        // 每个kurt值的那条直线的值
        for (Map.Entry<String, HashMap<Integer, Double>> rij : rijs.entrySet()) {
            HashMap<String, Double> sij = new HashMap<String, Double>();
            double array[] = new double[rij.getValue().size()];
            int i = 0;
            for (Map.Entry<Integer, Double> ij : rij.getValue().entrySet()) {
                array[i] = ij.getValue();
                i++;
            }
            double avg = getAverage(array);
            double sd = getStandardDevition(array);
            double l = avg + sd;
            sij.put(rij.getKey(), l);
            double kurti = (N * (N + 1)) / ((N - 1) * (N - 2) * (N - 3));
            for (Map.Entry<Integer, Double> ij : rij.getValue().entrySet()) {
                kurti = (sd == 0 ? 0 : kurti
                        * Math.pow(((ij.getValue() - avg) / sd), 4));
            }
            kurti = kurti - ((3 * Math.sqrt(N - 1)) / ((N - 2) * (N - 3)));
            kurtMap.put(sij, kurti);
        }
        HashMap<HashMap<String, Double>, Double> kurtNew = Util
                .sortMapByValueDouble(kurtMap);
        // 计算
        int i = 0;
        HashMap<String, ArrayList<Integer>> maptemp = new HashMap<String, ArrayList<Integer>>();
        for (Map.Entry<HashMap<String, Double>, Double> kurtEntry : kurtNew
                .entrySet()) {
            if (i >= 1000) {
                break;
            }
            for (Map.Entry<String, Double> entry : kurtEntry.getKey().entrySet()) {
                ArrayList<Integer> tempList = new ArrayList<Integer>();
                for (Map.Entry<Integer, Double> entryInt : rijs.get(entry.getKey())
                        .entrySet()) {
                    if (entryInt.getValue() > entry.getValue()) {
                        tempList.add(entryInt.getKey());
                    }
                }
                maptemp.put(entry.getKey(), tempList);
            }
            i++;
        }


        // //计算最终的词
        HashMap<String, ArrayList<String>> wordResult = new HashMap<String, ArrayList<String>>();
        for (Map.Entry<String, ArrayList<Integer>> entry : maptemp.entrySet()) {
            ArrayList<String> tempWord = new ArrayList<String>();
            for (Integer time : entry.getValue()) {
                rijWord.get(entry.getKey()).get(time);
                tempWord.addAll(rijWord.get(entry.getKey()).get(time));
            }
            wordResult.put(entry.getKey(), tempWord);
        }
        return wordResult;
    }

    public HashMap<String, HashMap<String, ArrayList<ArrayList<String>>>> kxinwen(
            TreeMap<Double, String> kurt) {
        HashMap<String, HashMap<String, ArrayList<ArrayList<String>>>> res = new HashMap<String, HashMap<String, ArrayList<ArrayList<String>>>>();
        return res;
    }

    // 获取平均值
    public static double getAverage(double[] array) {
        int num = array.length;
        double sum = 0;
        for (int i = 0; i < num; i++) {
            sum += array[i];
        }
        return (double) (sum / num);
    }
    // 获取标准差
    public static double getStandardDevition(double[] array) {
        int num = array.length;
        double avg = getAverage(array);
        double sum = 0;
        for (int i = 0; i < num; i++) {
            sum += Math.sqrt((array[i] - avg) * (array[i] - avg));

        }
        return (double) (sum / num);
    }

    public static List<Term> fenciTo(String str) {
        return (List<Term>) ToAnalysis.parse(str);
    }
    public static Result fenciNlp(String str) {
        FilterRecognition fitler = new FilterRecognition();
        fitler.insertStopNatures("null");
        return NlpAnalysis.parse(str).recognition(fitler);
    }

    public void timeUtil(Event event) {
        java.sql.Date sqlDate = event.getPubTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date time = null;
        try {
            time = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public double similarity(String text1,String text2){
        TextSimilarity textSimilarity = new JaccardTextSimilarity();
        return textSimilarity.similarScore(text1,text2);
    }

  }