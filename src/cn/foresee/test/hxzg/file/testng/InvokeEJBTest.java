package cn.foresee.test.hxzg.file.testng;

import org.testng.annotations.Test;

import cn.foresee.test.hxzg.file.InvokeEJB;

public class InvokeEJBTest {
    InvokeEJB iejb =new InvokeEJB("");

  @Test
  public void callApi() {
    throw new RuntimeException("Test not implemented");
  }

  @Test
  public void reportOut() {
    throw new RuntimeException("Test not implemented");
  }
  @Test
  public void createConn() {
    iejb.createConn();
  }
}
