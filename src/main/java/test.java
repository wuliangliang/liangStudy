import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by doubling_ruc on 2017/1/16.
 */
public class test {
    public static void main(String[] args) {
        result(32875028,45627200);
        result(715426,689238);
        result(46960999,74874981);
        result(1601521,1721155);
    }

    public static void result(int a ,int b){
        BigDecimal aa = new BigDecimal(a);
        BigDecimal bb = new BigDecimal(b);
        DecimalFormat df = new DecimalFormat("#.0000");
        System.out.println(df.format((aa.doubleValue()-bb.doubleValue())/aa.doubleValue()));
    }
}
