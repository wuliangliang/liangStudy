package com.study.codelife.liang.crawler.study.xiaowenDemo;

import com.study.codelife.liang.Util.fileUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultAttribute;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by doubling on 2017/1/21.
 */
public class XmlParse {
    public static void main(String[] args) throws DocumentException, IOException {

        BufferedWriter bw = fileUtil.writeToFile("/Users/doubling/Desktop/result适应症.txt");
        SAXReader sr = new SAXReader();//获取读取xml的对象。
        Document doc = sr.read("/Users/doubling/Desktop/Medicine.xml");//得到xml所在位置。然后开始读取。并将数据放入doc中
        Element root = doc.getRootElement();//获取根节点
        List list = root.elements("MedForum.dbo.drug");//根据根节点，将根节点下 row中的所有数据放到list容器中。
        System.out.println(list.size());
        for(Object obj:list) {//这种遍历方式，是jdk1.5以上的版本支持的遍历方式
            Element row = (Element) obj;
            List listRow = row.attributes();
            for(Object o :listRow){
                System.out.println(o);
                DefaultAttribute attribute = (DefaultAttribute)o;
                String name = attribute.getName();
                String value = attribute.getValue();
                if(name.equals("适应症")){
                    bw.write(value);
                    bw.write("\n");
                }
            }
        }
        bw.close();
    }
}
