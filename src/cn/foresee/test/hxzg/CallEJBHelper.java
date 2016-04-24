package cn.foresee.test.hxzg;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.foresee.test.loadrunner.lrTools;
import com.foresee.test.loadrunner.lrapi4j.lr;

import cn.foresee.test.hxzg.file.InvokeEJB;
import cn.foresee.test.hxzg.file.InvokeExcel;
import cn.foresee.test.hxzg.file.ParaProperties;

public class CallEJBHelper {
    public CallEJBHelper() {
        
    }
    
    public CallEJBHelper(String propFileName) {
        // TODO Auto-generated constructor stub
    }

    
    @SuppressWarnings("rawtypes")
    public void setArgsProp(Properties argsProp) {
        Iterator<Entry<Object, Object>> it=argsProp.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry entry=(Map.Entry)it.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            
            lr.save_string(value,key);
            //System.out.println(key +":"+value);
        }
        
        
    }
    
    public String getDefaultKey(){
        return lrTools.getDefault();
    }
    
    private void initParameters(){
        
        ParaProperties.getInstance().loadToEval("key");
        
//        lr.save_string("2016-04-16", "para_skssqq");
//        lr.save_string("2016-04-16", "para_skssqz");
//        
//        lr.save_string(StrUtils.getRandom(8),"lsh");
//        lr.save_string(lr.eval_string("C000BNFZC15001201412149{lsh}"), "p_tran_seq");
        //lr.save_string(lr.eval_string("12440600009982560172"), "p_djxh");
        // lr.save_string(lr.eval_string("44040299{lsh}"),"p_nsrsbh");
        
    }
    
    private String createXML(String tran_key){
        return  lrTools.loadXmlByKey(tran_key);
 
    }
    
    public String[] parserKey(String skey){
        return skey.isEmpty()?lrTools.getDefaults():skey.split(",");
    }
    
    public void displayMess(String skey){
        //初始化参数
        initParameters();
                
        for(String tran_key:parserKey(skey)){
            //构建报文
            String sXML = createXML(tran_key);
            
            //显示
            System.out.println(lr.eval_string(lr.eval_string(sXML)));
            
        }      
        
    }
    
    public void callEJB(String skey){
        InvokeEJB iejb = new InvokeEJB(skey);
        //初始化参数
        initParameters();
       
        
        //建立连接
        iejb.createConn();
        
        for(String tran_key:parserKey(skey)){
            //构建报文
            String sXML = createXML(tran_key);
            
            //调用
            iejb.callApi(sXML,tran_key);
            
        }      
        
        
    }
    
    public void callExcel(String trankey){
        new InvokeExcel().scanExcel();
    }


}
