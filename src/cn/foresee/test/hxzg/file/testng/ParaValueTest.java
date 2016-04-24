package cn.foresee.test.hxzg.file.testng;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import cn.foresee.test.hxzg.file.ParaValue;

public class ParaValueTest {
  @BeforeClass
  public void beforeClass() {
  }
  @Test
  public void ParserValue(){
      System.out.println(ParaValue.parserValue("sdfkhkj<RANDOM,8>jjjjj"));
      System.out.println(ParaValue.parserValue("sdfkhkj<DATE,yyyy-MM-dd>jjjjj"));
      
      System.out.println(ParaValue.parserValue("sdfkhkj<XML,SWZJ.HXZG.DJ.BCBGSWJDBXXJYSJX>jjjjj"));
      
  }
  

}
