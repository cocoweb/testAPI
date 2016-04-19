package cn.foresee.test.hxzg;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.foresee.etax.ejbclient.EjbClient;
import com.foresee.test.loadrunner.lrTools;
import com.foresee.test.loadrunner.lrapi4j.lr;
import com.foresee.test.util.lang.StringUtil;

import cn.foresee.test.hxzg.file.ParaProperties;
import gt3.esb.ejb.adapter.client.IEsbXmlMessageReceiver;

public class CallEJBHelper {
    private static IEsbXmlMessageReceiver esbClient = null;
    public CallEJBHelper() {
        
    }
    
    public CallEJBHelper(String propFileName) {
        // TODO Auto-generated constructor stub
    }

    public void createConn(){
        lr.start_transaction("连接EJB...");
        esbClient = new EjbClient().getCurrentEsb();
        lr.end_transaction("连接EJB...",lr.AUTO);
        

    }
    
    public String callApi(String sXML,String tran_key){
        String tran_name = lrTools.getTranNameByKey(tran_key);
        
        lr.start_transaction(tran_name);
        
        String retstr ="";
        boolean bsuss=false;
        try{
            retstr= esbClient.receiveMessageXML(lr.eval_string(lr.eval_string(sXML)));
            bsuss = StringUtil.findXML(retstr, "<rtn_code>0</rtn_code><rtn_msg><Code>000</Code>");
            
        }catch(Exception e){
            e.printStackTrace();
        }
        //lr.end_transaction(tran_key, lr.AUTO);
        
        reportOut(bsuss, tran_name, retstr
                , lr.getCurrentTransaction().beginTime);
        return retstr;
    }
    
    /**
     * 输出报告
     * 
     * @param bSuccess   成功标志
     * @param transName   事务名
     * @param retXML      返回的字符串
     * @param beginTimer       原来的起始时间
     */
    private void reportOut(boolean bSuccess, String transName, String retXML, long beginTimer) {
        long ltime =System.currentTimeMillis() - beginTimer;
        String stime = ltime<10000 ? Long.toString(ltime):"==too long=="+Long.toString(ltime)+" ";
        if(bSuccess){
            lr.message(stime+" "+lr.eval_string(transName+" 成功!:NSRSBH:[{para_nsrsbh}]:返回:")+ retXML  );
            lr.end_transaction(transName, lr.AUTO);
        }else{
            lr.error_message(stime+" "+lr.eval_string(transName+" 失败:NSRSBH:[{para_nsrsbh}]:返回:["+retXML+"]"));
            lr.end_transaction(transName, lr.FAIL);
    
        }
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
        initParameters();
        
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
        //初始化参数
        initParameters();
       
        
        //建立连接
        createConn();
        
        for(String tran_key:parserKey(skey)){
            //构建报文
            String sXML = createXML(tran_key);
            
            //调用
            callApi(sXML,tran_key);
            
        }      
        
        
    }


}
