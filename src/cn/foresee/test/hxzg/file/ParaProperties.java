package cn.foresee.test.hxzg.file;

import java.util.Map;

import com.foresee.test.loadrunner.lrapi4j.lr;
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
            lr.save_string(ParaValue.parserValue(retmap.get(skey)), skey);
        }
        
        return retmap;
    }

}
