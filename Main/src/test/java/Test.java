import com.blacktree.utils.MD5Util;
import com.blacktree.utils.StringUtil;

/**
 * Created by wangqchf on 2016/9/30.
 */
public class Test {

    public static void main(String[] args){
        String str = "https://www.baidu.com/";
        String result = MD5Util.toMD5HexString(str);
        System.out.println(result);
        String str2 = "http://service.weibo.com/share/share.php?url=http://lol.tuwan.com/342309/&title=2016全球总决赛9月30日 INTZ vs EDG录像&appkey=1343713053";
        System.out.println(StringUtil.encodingSpecialChaOfURL(str2));
        System.out.println(str2.length());
        System.out.println(str2.indexOf(94));

        /*String testStr = "shenmeqingkuang?";
        System.out.println(testStr.replaceAll("[sqiag]","&"));*/
        /*System.out.println((int)'0');
        System.out.println((int)'a');*/
    }

}
