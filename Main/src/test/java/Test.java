import com.blacktree.utils.MD5Util;

/**
 * Created by wangqchf on 2016/9/30.
 */
public class Test {

    public static void main(String[] args){
        String str = "https://www.baidu.com/";
        String result = MD5Util.toMD5HexString(str);
        System.out.println(result);
        /*System.out.println((int)'0');
        System.out.println((int)'a');*/
    }

}
