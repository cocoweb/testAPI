package cn.foresee.test.hxzg.tmp;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import com.foresee.etax.ejbclient.EjbClient;
import com.foresee.etax.ejbclient.Test;
import com.foresee.test.loadrunner.lrTools;
import com.foresee.test.loadrunner.lrapi4j.lr;
import com.foresee.test.util.lang.StrUtils;
import com.foresee.test.util.lang.StringUtil;

import gt3.esb.ejb.adapter.client.IEsbXmlMessageReceiver;

public class test {

    public  test() {
        // TODO Auto-generated constructor stub
    }

    public void test0() {
        EjbClient ejb = new EjbClient();
        // PropertiesContext prop = new PropertiesContext();
        // IEsbXmlMessageReceiver esb =
        // ejb.getEjb(prop.getProperties("esb.contextFactory"),
        // prop.getProperties("esb.providerUrl"),prop.getProperties("esb.principal"),prop.getProperties("esb.crenentials"),prop.getProperties("esb.ejbHome"));
        long beginTime = System.currentTimeMillis();
        
        IEsbXmlMessageReceiver esb = ejb.getCurrentEsb();
        
        System.out.println("Time:" + String.valueOf(System.currentTimeMillis() - beginTime));

        InputStream inputStream = Test.class.getResourceAsStream("/SWZJ.HXZG.FP.BCFPYJJXX-保存发票验交旧信息-request.xml");
        if (inputStream == null) {
            System.out.println("未找到文件！");
        }

        String xml = "";
        try {
            int length = inputStream.available();
            byte[] bytes = new byte[length];
            inputStream.read(bytes);
            xml = new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        for (int i = 1; i < 10; i++) {
            beginTime = System.currentTimeMillis();
            String retXml = esb.receiveMessageXML(xml);
            System.out.println("=====request:\n " + xml);
            System.out.println("=====response:\n " + retXml);
            System.out.println("Time" + i + ":" + String.valueOf(System.currentTimeMillis() - beginTime));
        }

    }
    // 报文名字
    private static String tran_key = "SWZJ.HXZG.ZS.CXNSRWQJQSFXX";
    private static String tran_name = "";

    private static IEsbXmlMessageReceiver esbClient = null;

    public int test1()  {
        lr.start_transaction("连接EJB...");
        //esbClient = new EjbClient().getCurrentEsb();
        lr.end_transaction("连接EJB...",lr.AUTO);
        
        String sXML = lrTools.loadXmlByKey(tran_key);

        lr.save_string("2016-04-16", "para_skssqq");
        lr.save_string("2016-04-16", "para_skssqz");
        long timer = lr.start_timer();
        lr.save_string(StrUtils.getRandom(8),"lsh");
        lr.save_string(lr.eval_string("C000BNFZC15001201412149{lsh}"), "p_tran_seq");
        //lr.save_string(lr.eval_string("12440600009982560172"), "p_djxh");
        // lr.save_string(lr.eval_string("44040299{lsh}"),"p_nsrsbh");

        lr.start_transaction(tran_key);

         System.out.println(lr.eval_string(lr.eval_string(sXML)));
        String retXML ="";//= esbClient.receiveMessageXML(lr.eval_string(sXML));
        // lr.think_time(1);

        long ltime = lr.end_timer(timer);
        String stime = ltime > 5 ? Long.toString(ltime) : "=too long=" + Long.toString(ltime) + " ";
        if (StringUtil.findXML(retXML, "<rtn_code>0</rtn_code><rtn_msg><Code>000</Code>")) {
            String pzxh = StringUtil.getXMLNote(retXML, "pzxh"); // 获取pzxh
            lr.message(stime + lr.eval_string("成功!:DJXH:[{p_djxh}]:PZXH:" + pzxh + ":head:")
                    + StringUtil.getXMLNote(retXML, "head"));
        } else {
            lr.error_message(stime + lr.eval_string("申报失败:DJXH:[{p_djxh}]:返回:[" + retXML + "]"));
        }

        lr.end_transaction(tran_key, lr.AUTO);

        return 0;
    }// end of init

    public static void main(String[] args) {
        
        new test().test1();
    }

}
