package cn.foresee.test.hxzg.file;

import java.util.Iterator;

import com.foresee.test.fileprops.FileDefinition;
import com.foresee.test.loadrunner.helper.ApiMethod;
import com.foresee.test.loadrunner.helper.Arguments;
import com.foresee.test.loadrunner.lrapi4j.lr;
import com.foresee.test.loadrunner.lrapi4j.web;
import com.foresee.test.util.http.HttpException;
import com.foresee.test.util.lang.StringUtil;

public class InvokeExcel {
    public static final String ExcelKey_Main="foreseeapitestcase.excel";
    public static final String ExcelKey_KeyValue="foreseeapikeyvalue.excel";
    
    String mainKey;
    String valueKey;

    public InvokeExcel(String mainkey, String valuekey) {
        mainKey = mainkey;
        valueKey = valuekey;
    }

    public InvokeExcel() {
        this(FileDefinition.getExtPropertiesInstance().getProperty("callexcel.main", ExcelKey_Main)
            ,FileDefinition.getExtPropertiesInstance().getProperty("callexcel.keyvalue", ExcelKey_KeyValue));
    }

    /**
     * 获取参数文件的迭代子 iterator
     * 
     * @param skey
     * @return
     */
    public Iterator<Object[]> getDataIterator(String skey) {
        try {
            Arguments.getInstance().load(skey);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return Arguments.getInstance().getArgsIterator(skey);
    }

    public void scanExcel() {
        //读取excel中的参数sheet
        try {
            Arguments.getInstance().load(valueKey);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //扫描并执行
        scanExcel(mainKey);

    }

    /**
     * 网址：{Site_URL}
     * 
     * <br>
     * excel参数模版：
     * {casename},{method},{api},{params},{catchparams},{saveparams},{isenabled}
     * 
     * @param skey
     */
    public void scanExcel(String skey) {
        Iterator<Object[]> iter = getDataIterator(skey);
        while (iter.hasNext()) {
            iter.next();

            // 只有isEnabled才被执行
            if (lr.eval_string("{isenabled}").equals("YES")) {
                lr.start_transaction(lr.eval_string("{casename}"));

                System.out.println(lr.eval_string(
                        lr.eval_string("{casename},{method},{api},{params},{catchparams},{saveparams},{isenabled}")));

                // 判断是否需要保存参数
                if (StringUtil.isNotEmpty(lr.eval_string("{saveparams}"))) {
                    web.reg_save_param(lr.eval_string("{saveparams}"),
                            new String[] { lr.eval_string("LB=<{catchparams}>"), lr.eval_string("RB=</{catchparams}>") });
                }

//                System.out.println(lr.eval_string("URL={Site_URL}{api}")
//                        + (StringUtil.isNotEmpty(lr.eval_string("{params}")) ? lr.eval_string(lr.eval_string("?{params}")) : ""));
//
//                web.add_cookie("gt3nf_tycx=MQ0LJQvQnpn26k1RrzlT6CQ2YppM4znG4dzGJTpPLBlBJs20r0rS!572503777");
                try {

                    if (lr.eval_string("{method}").indexOf("GET") >= 0) {
                        // get
                        web.custom_request("",
                                lr.eval_string("URL={Site_URL}{api}") + (StringUtil.isNotEmpty(lr.eval_string("{params}"))
                                        ? lr.eval_string(lr.eval_string("?{params}")) : ""),
                                new String[] { lr.eval_string("Method={method}"), "RecContentType=text/html", "Mode=HTML",
                                        "EncType=text/xml; charset=UTF-8", "Accept=text/plain;charset=UTF-8" });
                    } else if (ApiMethod.isEquals(lr.eval_string("{method}"), ApiMethod.POST)) {
                        // post
                        web.custom_request("", lr.eval_string("URL={Site_URL}{api}"),
                                new String[] { lr.eval_string("Method={method}"), "RecContentType=text/html", "Mode=HTML",
                                        "EncType=text/xml; charset=UTF-8", "Accept=text/plain;charset=UTF-8",
                                        StringUtil.isNotEmpty(lr.eval_string("{params}"))
                                                ? lr.eval_string(lr.eval_string("Body={params}")) : "" });

                    } else if (ApiMethod.isEquals(lr.eval_string("{method}"), ApiMethod.LINK)) {
                        //link 点击
                        web.link("", lr.eval_string("{api}"), new String[] {});

                    } else if(lr.eval_string("{method}").indexOf("EJB")>=0){
                        //EJB调用
                        if (lr.eval_string("{api}").contains("<XML,")){  //判断参数是否包括<XML,xxxxx>
                            String tkey=StringUtil.locateString(lr.eval_string("{api}"), "<XML,", ">");
                            new InvokeEJB(tkey).createConn().callApi(ParaValue.parserValue(lr.eval_string("{api}")));
                        }
                        
                    }
                    
                    //如果有返回参数，即输出
                    if (StringUtil.isNotEmpty(lr.eval_string("{saveparams}"))) {
                        System.out.println(lr.eval_string(lr.eval_string("{{saveparams}}")));
                    }
                } catch (HttpException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                lr.think_time(0.5);

                lr.end_transaction(lr.eval_string("{casename}"), lr.AUTO);

            }

        }

    }
}
