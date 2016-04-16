package cn.foresee.test.hxzg.file;

import java.util.Map;

import com.foresee.test.loadrunner.lrapi4j.lr;
import com.foresee.test.util.lang.StrUtils;
import com.foresee.test.util.lang.StringUtil;
import com.foresee.xdeploy.utils.base.ParamPropValue;

public class ParaProperties extends ParamPropValue{

    public ParaProperties(String strFileName) {
        super(strFileName);
    }

    private static ParaProperties SingletonPV=null;
    

    public static synchronized ParaProperties getInstance(String strFileName){
        if (SingletonPV==null){
            SingletonPV = new ParaProperties(strFileName);
        }
        
        return SingletonPV;
    }
    
    public static synchronized ParaProperties getInstance(){
        return getInstance("/para.properties");
    }
    
    /**
     * 存入参数EvalString 
     */
    public Map<String, String> loadToEval(String sectionName){
        Map<String, String> retmap= this.getSectionItems(sectionName);
        for(String skey:retmap.keySet()){
            lr.save_string(parserValue(retmap.get(skey)), skey);
        }
        
        return retmap;
    }
    
    public String parserValue(String svalue){
        String retstr="";
        
        //读取其中的定义的命令  <RANDOM,x>
        if(svalue.contains("<RANDOM")){
           retstr= ParserValue_Random(svalue);
        }else
            retstr=svalue;
        
        return retstr;
    }
    
    public String ParserValue_Random(String svalue){
        String retstr = svalue.substring(0, svalue.indexOf("<RANDOM"));
        int x= Integer.parseInt(StringUtil.locateString(svalue, "<RANDOM,", ">"));
        
        return retstr+StrUtils.getRandom(x);
    }

}
