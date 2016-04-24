package cn.foresee.test.hxzg.file;

import java.util.Date;

import com.foresee.test.loadrunner.lrTools;
import com.foresee.test.loadrunner.lrapi4j.lr;
import com.foresee.test.util.lang.DateUtil;
import com.foresee.test.util.lang.StrUtils;
import com.foresee.test.util.lang.StringUtil;

/**
 * 参数值处理，包括其中的<>系统级别参数
 * <DATE,yyyy-MM-dd>
 * <XML,SWZJ.HXZG.SB.ZZSYBRSBSQJKJHQQCSJ>
 * <RANDOM,8>
 * 
 * @author allan
 *
 */
public class ParaValue {
    public static final String SysParaKey_Random ="RANDOM";
    public static final String SysParaKey_XML ="XML";
    public static final String SysParaKey_Date ="DATE";
    

    public ParaValue() {
        // TODO Auto-generated constructor stub
    }
    
    public static String leftStr(String sysParaKey){
        return "<"+sysParaKey+",";
    }
    
    public static String rightStr(){
        return ">";
    }
    
    /**
     * @param svalue
     * @param sysParaKey
     * @return  返回被key包围的字符串   
     * <DATE,yyyy-MM-dd>   返回yyyy-MM-dd
     */
    public static String getRoundString(String svalue,String sysParaKey){
        return StringUtil.locateString(svalue, leftStr(sysParaKey), rightStr());
    }
    
    /**
     * 替换被字符包围的字符串
     * @param srcStr
     * @param sysParaKey
     * @param svalue
     * @return
     * "sdfkhkj<DATE,yyyy-MM-dd>jjjjj   = sdfkhkj2016-04-23jjjjj   
     */
    public static String setRoundString(String srcStr,String sysParaKey,String svalue){
        return setRoundString(srcStr,leftStr(sysParaKey),rightStr(),svalue);
//                srcStr.substring(0, srcStr.indexOf(leftStr(sysParaKey)))
//                + svalue 
//                + srcStr.substring(srcStr.indexOf(rightStr()) + 1);
        
    }
    
    public static String setRoundString(String srcStr,String LeftStr,String RightStr,String svalue){
        return srcStr.substring(0, srcStr.indexOf(LeftStr))
                + svalue 
                + srcStr.substring(srcStr.indexOf(RightStr) + 1);
        
    }
    
    public static boolean containsKey(String svalue,String sysParaKey){
        return svalue.contains(leftStr(sysParaKey));
    }

    public static String parserValue(String svalue) {
        String retstr = "";

        if (containsKey(svalue,SysParaKey_Random)) {
            // 读取其中的定义的命令 <RANDOM,x>
            retstr = parserValue_Random(svalue);
        } else if (containsKey(svalue,SysParaKey_Date)) {
            retstr = parserValue_Date(svalue);
        } else if(containsKey(svalue,SysParaKey_XML)) {
            retstr = parserValue_Xml(svalue);
        }else
            retstr = svalue;

        return lr.eval_string(retstr);
    }

    /**
     * 处理获取XML的系统参数 <XML,SWZJ.HXZG.SB.ZZSYBRSBSQJKJHQQCSJ>
     * @param svalue
     * @return
     */
    public static String parserValue_Xml(String svalue) {
        String xmlkey = getRoundString(svalue, SysParaKey_XML);
        
        return lrTools.loadXmlByKey(xmlkey);
    }

    /**
     * 处理随机数的系统参数 <RANDOM,8>
     * 
     * @param svalue
     * @return
     */
    public static String parserValue_Random(String svalue) {
        int x = Integer.parseInt(getRoundString(svalue,SysParaKey_Random));

        return setRoundString(svalue, SysParaKey_Random,StrUtils.getRandom(x));
    }

    /**
     * 处理日期的系统参数 <DATE,yyyy-MM-dd>
     * 
     * @param svalue
     * @return
     */
    public static String parserValue_Date(String svalue) {
        String format = getRoundString(svalue,SysParaKey_Date);

        return setRoundString(svalue, SysParaKey_Date, DateUtil.format(new Date(), format));

    }

    

    // public static void saveDataToEval(String skey){
    // Iterator iter = getDataIterator(skey);
    // while(iter.hasNext()){
    //
    //
    // }
    //
    // }



}
