package cn.foresee.test.hxzg.testng;

import java.util.Iterator;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.foresee.test.loadrunner.helper.Arguments;
import com.foresee.test.loadrunner.lrapi4j.lr;

public class ArgumentsTest {
    String key0 = "keyvalue.excel";
    String key = "apitestcase0.excel";
    String key1 = "apitestcase.excel";
    String key2 = "textlist.csvtext";
    String keyxml = "SWZJ.HXZG.SB.BCZZSXGMSB";
    Arguments xargs;

    @BeforeTest
    public void beforeTest() {
        xargs=Arguments.getInstance();
        xargs.load(key);
        xargs.load(key1);
        xargs.load(key2);
        xargs.load(key0);
        xargs.load(keyxml);
        
     }
    @DataProvider(name = "iterator")
    public Iterator<Object[]> getData() {
        return Arguments.getInstance().getArgsIterator(key2);
    }

    @Test(dataProvider = "iterator")
    public void testIteraorData(Object oo1) {
        //System.out.println(lr.eval_string(lr.eval_string("{casename},{method},{api},{params},{catchparams}")));
        System.out.println(lr.eval_string(lr.eval_string("{DJXH},{FPZL_DM},{FP_DM},{FS,FPQSHM},{FPZT_DM}")));
    }

    @AfterTest
    public void afterTest() {
    }
    @Test
    public void showxml() throws Exception {
        System.out.println(xargs.getString(keyxml));
    }

    @Test
    public void ff() throws Exception {

        System.out.println(("{功能},{用例},{预期结果},{请求方式},{API},{请求参数},{抓取参数},{抓取另命名},{登录态账号}"));
        System.out.println("{casename},{method},{api},{params},{catchparams}");

        Iterator<?> iters = xargs.getArgsIterator(key);
        Iterator<?> iters1 = xargs.getArgsIterator(key1);
        while (iters.hasNext()) {
            iters.next();
            iters1.next();
            System.out.println(lr.eval_string(lr
                    .eval_string("{功能},{用例},{预期结果},{请求方式},{API},{请求参数},{抓取参数},{抓取另命名},{登录态账号}")));
            System.out.println(lr.eval_string(lr.eval_string("{casename},{method},{api},{params},{catchparams}")));

        }

        for (int i = 1; i < 5; i++) {
            // Arguments.save_paramStringByKey(key, i);
            // System.out.println(lr.eval_string("{功能},{用例},{预期结果},{请求方式},{API},{请求参数},{抓取参数},{抓取另命名},{登录态账号}"));
        }
    }

    @Test
    public void loadExcelArgumentsByName() throws Exception {
        Iterator<Object[]> xiters = xargs.getArgsIterator(key);
         while(xiters.hasNext()){
            System.out.println(xiters.next()[0].toString());
         }

//        ArrayList<ArgItem> argsnames = xargs.getArgsSet(key).;
//        System.out.println(argsnames.toString());
    }

    @Test
    public void loadKEYVALUE() throws Exception {
        //Arguments.load("keyvalue.excel");
        System.out.println(lr.eval_string("{admin_phone}{admin_wb}{wb_name}"));
    }


}
