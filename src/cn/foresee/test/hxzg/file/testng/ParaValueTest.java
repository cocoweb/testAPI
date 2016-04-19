package cn.foresee.test.hxzg.file.testng;

import org.testng.annotations.Test;

import com.foresee.test.loadrunner.lrapi4j.lr;

import cn.foresee.test.hxzg.file.InvokeExcel;
import cn.foresee.test.hxzg.file.ParaValue;

import java.util.Iterator;

import org.testng.annotations.BeforeClass;

public class ParaValueTest {
  @Test
  public void f() {
  }
  @BeforeClass
  public void beforeClass() {
  }
  @Test
  public void ParserValue(){
      System.out.println(ParaValue.parserValue("sdfkhkj<RANDOM,8>jjjjj"));
      System.out.println(ParaValue.parserValue("sdfkhkj<DATE,yyyy-MM-dd>jjjjj"));
      
  }
  

}
