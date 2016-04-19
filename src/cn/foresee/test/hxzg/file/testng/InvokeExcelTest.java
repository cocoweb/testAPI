package cn.foresee.test.hxzg.file.testng;

import java.util.Iterator;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.foresee.test.loadrunner.lrapi4j.lr;

import cn.foresee.test.hxzg.file.InvokeExcel;

public class InvokeExcelTest {
    InvokeExcel invokeexcel=null;
    @BeforeClass
    public void beforeClass() {
        invokeexcel = new InvokeExcel("foreseeapitestcase.excel","foreseeapikeyvalue.excel");
    }

  @Test
  public void InvokeExcel() {
      invokeexcel.scanExcel();
  }

  @Test
  public void getDataIterator(){
      Iterator<Object[]> iter =invokeexcel.getDataIterator("apitestcase.excel");
      while(iter.hasNext()){
          iter.next();
          //System.out.println(lr.eval_string(lr.eval_string("{功能},{用例},{预期结果},{请求方式},{API},{请求参数},{抓取参数},{抓取另命名},{登录态账号}")));
          System.out.println(lr.eval_string(lr.eval_string("{casename},{method},{api},{params},{catchparams}")));
          
          //System.out.println(lr.eval_string(lr.eval_string("{DJXH},{FPZL_DM},{FP_DM},{FS,FPQSHM},{FPZT_DM}")));
      }
  }
}
