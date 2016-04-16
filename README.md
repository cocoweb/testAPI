#接口报文测试工具

	通过定义报文和相关参数，可以执行报文进行接口调用测试

###调用方式
"D:\Java\jdk1.8.0_25\bin\java" -jar call_ejb_0.1.jar -h
=usage: Call_EJB -c | -d |-h  [ -k<keyName>] 
                 [ -P <name =value> | -- propfile < PropertiesFile > ]
  参数说明：
  
    -c,--call                      调用EJB接口
    -d,--display                   显示输出参数化报文
    -h,--help                      显示这里的参数信息
    -k,--key <keyName>             指定要执行的报文(默认为文件中default的报文)
    -P <name=value>                Use value for given property: -Pp1=v1 -Pp2=v2 ...
       --propfile <PropertiesFile> Properties File Name, Default is [file.properties]

	  Example:
	    java -jar call_ejb.jar --display or  java Call_EJB -d
	    java -jar call_ejb.jar --call    or  java Call_EJB -c
	    java -jar call_ejb.jar -d -propfile custom.properties
	    java -jar call_ejb.jar -c -Pnsrsbh=123123123 -Psssq=2016-05-01
    
    
###file.properties
	定义报文的说明、对应的xml文件名字、及内含参数名字列表
	使用#lrTools.loadXmlByKey("SWZJ.HXZG.SB.BCZZSYBRSB");
	可以返回插入参数后的报文内容
	形如：key:para   or   <value>|
	tran_seq,djxh:para_djxh,skssqq:pp_sssqq,skssqz
	默认参数形式  {p_tran_seq}
	可自定义    {para_djxh} 自定义参数，将不使用default的值替换
	
	可以定义多种文件参数：
	1.xml报文文件  ——默认
	2.文本文件  csvtext
	3.excel文件  excel
	
###para.properties
	参数定义的配置文件，相关参数值做为{aaaaa}模版形式替换报文中的数据
	
	系统参数：
	<RANDOM,6>代表系统将用6位的随机数字，替换
	
###ejbclient.properties
	ejb调用的配置文件
	
	
	
##Release Note:


	**v0.1-20160416**
	 初试版本
	 相关的报文，需要重新获取（dat目录中）