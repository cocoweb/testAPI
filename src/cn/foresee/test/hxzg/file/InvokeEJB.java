package cn.foresee.test.hxzg.file;

import com.foresee.etax.ejbclient.EjbClient;
import com.foresee.test.loadrunner.lrTools;
import com.foresee.test.loadrunner.lrapi4j.lr;
import com.foresee.test.util.lang.StringUtil;

import gt3.esb.ejb.adapter.client.IEsbXmlMessageReceiver;

public class InvokeEJB {

    private static IEsbXmlMessageReceiver esbClient = null;
    String transkey="";
    


    public InvokeEJB(String skey) {
        transkey =skey;
    }

    public InvokeEJB createConn(){
        if (esbClient==null){
            lr.start_transaction("连接EJB...");
            esbClient = new EjbClient().getCurrentEsb();
            lr.end_transaction("连接EJB...",lr.AUTO);
            
        }
        return this;
    
    }
    public String callApi(String sXML){
        return callApi(sXML,transkey);
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

}
