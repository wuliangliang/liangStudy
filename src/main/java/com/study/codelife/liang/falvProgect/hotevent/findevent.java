package com.study.codelife.liang.falvProgect.hotevent;
import org.apdplat.word.analysis.*;
import org.apdplat.word.*;
import org.apdplat.word.segmentation.*;

import java.util.List;

/**
 * Created by doubling on 2017/3/7.
 */
public class findevent {

    public static void main(String[] args) {
        String text1 = "我爱购物";
        String text2 = "我爱读书";
        String text3 = "他是黑客";
        TextSimilarity textSimilarity = new JaccardTextSimilarity();
        double score1pk1 = textSimilarity.similarScore(text1, text1);
        double score1pk2 = textSimilarity.similarScore(text1, text2);
        double score1pk3 = textSimilarity.similarScore(text1, text3);
        double score2pk2 = textSimilarity.similarScore(text2, text2);
        double score2pk3 = textSimilarity.similarScore(text2, text3);
        double score3pk3 = textSimilarity.similarScore(text3, text3);
        System.out.println(text1+" 和 "+text1+" 的相似度分值："+score1pk1);
        System.out.println(text1+" 和 "+text2+" 的相似度分值："+score1pk2);
        System.out.println(text1+" 和 "+text3+" 的相似度分值："+score1pk3);
        System.out.println(text2+" 和 "+text2+" 的相似度分值："+score2pk2);
        System.out.println(text2+" 和 "+text3+" 的相似度分值："+score2pk3);
        System.out.println(text3+" 和 "+text3+" 的相似度分值："+score3pk3);
        List<Word> words = WordSegmenter.seg("杨尚川是APDPlat应用级产品开发平台的作者");
    }
}
