package cn.foresee.test.hxzg.file;

import java.util.Date;

import com.foresee.test.loadrunner.lrapi4j.lr;
import com.foresee.test.util.lang.DateUtil;
import com.foresee.test.util.lang.StrUtils;
import com.foresee.test.util.lang.StringUtil;

public class ParaValue {

    public ParaValue() {
        // TODO Auto-generated constructor stub
    }

    public static String parserValue(String svalue) {
        String retstr = "";

        // 读取其中的定义的命令 <RANDOM,x>
        if (svalue.contains("<RANDOM")) {
            retstr = ParserValue_Random(svalue);
        } else if (svalue.contains("<DATE")) {
            retstr = ParserValue_Date(svalue);
        } else
            retstr = svalue;

        return lr.eval_string(retstr);
    }

    /**
     * 处理随机数的系统参数 <RANDOM,8>
     * 
     * @param svalue
     * @return
     */
    public static String ParserValue_Random(String svalue) {
        String retstr = svalue.substring(0, svalue.indexOf("<RANDOM"));
        int x = Integer.parseInt(StringUtil.locateString(svalue, "<RANDOM,", ">"));

        return retstr + StrUtils.getRandom(x) + svalue.substring(svalue.indexOf(">") + 1);
    }

    /**
     * 处理日期的系统参数 <DATE,yyyy-MM-dd>
     * 
     * @param svalue
     * @return
     */
    public static String ParserValue_Date(String svalue) {
        String retstr = svalue.substring(0, svalue.indexOf("<DATE"));
        String format = StringUtil.locateString(svalue, "<DATE,", ">");

        return retstr + DateUtil.format(new Date(), format) + svalue.substring(svalue.indexOf(">") + 1);

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
