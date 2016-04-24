package cn.foresee.test.hxzg.testng;

import org.testng.annotations.Test;

import cn.foresee.test.hxzg.CallEJBHelper;

import org.testng.annotations.BeforeClass;

public class CallEJBHelperTest {
    
    CallEJBHelper callejb=null;
  @BeforeClass
  public void beforeClass() {
      callejb = new CallEJBHelper();
  }


  @Test
  public void callApi() {
    throw new RuntimeException("Test not implemented");
  }

  
  @Test
  public void getDefaultKey(){
      System.out.println(callejb.getDefaultKey());
  }
  
  @Test
  public void displayMess(){
      callejb.displayMess("");
  }

}
