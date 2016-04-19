package cn.foresee.test.hxzg.file.testng;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.foresee.test.loadrunner.lrapi4j.lr;

import cn.foresee.test.hxzg.file.ParaProperties;

public class ParaPropertiesTest {
  @BeforeClass
  public void beforeClass() {
  }


  @Test
  public void loadToEval() {
      System.out.println(ParaProperties.getInstance().loadToEval("key"));
      System.out.println(lr.eval_string("{p_djxh};{para_skssqz}"));
  }
  
}
