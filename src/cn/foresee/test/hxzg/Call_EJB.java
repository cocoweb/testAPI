package cn.foresee.test.hxzg;

import java.util.Comparator;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;

import com.foresee.test.util.lang.DateUtil;

public class Call_EJB {
    static Logger log  = Logger.getLogger(Call_EJB.class );
    
    public Call_EJB() {
    }



    public static Options cmdCLIOptions() {
        Options options = new Options();

        // options.addOption(Option.builder("").desc(
        // "Command is :LIST or FROMSVN or FROMZIP" ).hasArg()
        // .argName("Command").required().build());

        OptionGroup og1 = new OptionGroup();
        og1.setRequired(true);
        og1.addOption(Option.builder("h").longOpt("help").desc("显示这里的参数信息").hasArg(false).required(false).build());
        og1.addOption(Option.builder("d").longOpt("display").desc("显示输出参数化报文").hasArg(false).required(false).build());
        og1.addOption(Option.builder("c").longOpt("call").desc("调用EJB接口\n").hasArg(false).required(false).build());
        og1.addOption(Option.builder("e").longOpt("excel").desc("按照excel定义进行调用\n").hasArg(false).required(false).build());

        //og1.addOption(Option.builder("b").longOpt("batchcall").desc("批量调用EJB接口，配置中所有接口").hasArg(false).required(false).build());
        
//        og1.addOption(Option.builder("l").longOpt("list").desc("for list; 显示待处理文件清单和版本").hasArg(false).required(false).build());
//        og1.addOption(Option.builder("s").longOpt("fromsvn")
//                .desc("from svn to; 从svn库svn.tofolder导出到临时目录svn.tofolder，或者workspace").hasArg(false).required(false).build());
//        og1.addOption(Option.builder("z").longOpt("fromzip").desc("from zip to; 从指定压缩文件war、zip、jar 导出到临时目录").hasArg(false)
//                .required(false).build());
//        og1.addOption(Option
//                .builder("d")
//                .longOpt("fordiffver")
//                .desc("for version diffrent; 根据起始版本号svndiff.startversion  svndiff.endversion，获取文件清单；从指定目录ci.workspace 导出到输出目录ci.tofolder;  其中的.java 将替换为.class")
//                .hasArg(false).required(false).build());
        options.addOptionGroup(og1);
        
        options.addOption(Option.builder("k").longOpt("key").desc("指定要执行的报文(默认为文件中default的报文)\n")
                .hasArg(true).argName("keyName").optionalArg(false).required(false).build());

        OptionGroup og0 = new OptionGroup();
        og0.setRequired(false);
        og0.addOption(Option.builder("P").desc("Use value for given property: -Pp1=v1 -Pp2=v2 ...").argName("name=value")
                .valueSeparator().hasArgs().optionalArg(false).required(false).build());
        og0.addOption(Option.builder().longOpt("propfile").desc("Properties File Name, Default is [file.properties]")
                .hasArg(true).argName("PropertiesFile").optionalArg(false).required(false).build());
        options.addOptionGroup(og0);

//        OptionGroup og2 = new OptionGroup();
//        og2.setRequired(false);
//        og2.addOption(Option.builder("B").longOpt("bebatch").desc("[默认]批量处理excel，扫描目录file.excel.folder下的所有").build());
//        og2.addOption(Option.builder().longOpt("befile").desc("指定excel文件，扫描file.excel清单").build());
//        options.addOptionGroup(og2);
        

        return options;

    }

    static HelpFormatter hf = new HelpFormatter();

    public static void helpPrint(Options options) {

        hf.setWidth(120);
        hf.setOptionComparator(new Comparator<Option>() { // 比较排序参数

            @Override
            public int compare(Option arg0, Option arg1) {
                // return arg0.getKey().compareToIgnoreCase(arg1.getKey());

                // 返回0，直接使用加入options的顺序
                return 0;
            }
        });

        hf.setLeftPadding(4);
        hf.setDescPadding(1);
        hf.printHelp("Call_EJB", "  参数说明：", options
              , "\n  Example: \n"
                + "    java -jar call_ejb.jar --display or  java Call_EJB -d \n"
                + "    java -jar call_ejb.jar --call    or  java Call_EJB -c \n" 
                + "    java -jar call_ejb.jar -d -propfile custom.properties \n"
                + "    java -jar call_ejb.jar -c -Pnsrsbh=123123123 -Psssq=2016-05-01 \n" 
                ,true);

    }

    public static void parserCLICmd(Options options, String[] args) {

        CommandLine cmds = null;
        Properties prop = null;
        DefaultParser parser = new DefaultParser();
        try {

            // ListToFile [-B | --befile] -d | -h | -l | -s | -z [-P
            // <name=value> | --propertiesfile <PropertiesFile>]
            cmds = parser.parse(options, args, prop, true);

            if (cmds.hasOption('h')) {
                // 打印使用帮助
                helpPrint(options);
                return ;
            }

            CallEJBHelper callEjbHelper = null;

            // 属性组 [-P <name=value> | --propertiesfile <PropertiesFile>]
            if (cmds.hasOption("propfile")) {
                callEjbHelper = new CallEJBHelper(cmds.getOptionValue("propfile"));

            } else {
                callEjbHelper = new CallEJBHelper();

                if (cmds.hasOption("P")) {  //提取参数
                    callEjbHelper.setArgsProp(cmds.getOptionProperties("P"));
                    //PropValue.setArgsProp(cmds.getOptionProperties("P"));

                    //System.out.println(listTofileHelper.pv.argsProp);
                }
            }

//            // 是否批量清单 [-B | --befile]
//            if (cmds.hasOption("befile")) {
//                callEjbHelper.pv.scanOption = callEjbHelper.FILE;
//            } else
//                callEjbHelper.pv.scanOption = callEjbHelper.BATCH;
            
            String trankey ="";
            if (cmds.hasOption("k")) {  //设置了key
                trankey = cmds.getOptionValue("k");
            }
            
            // 互斥命令组 -d | -h | -l | -s | -z
            if (cmds.hasOption('d')) {
                callEjbHelper.displayMess(trankey);
            } else if (cmds.hasOption('c')) {
                callEjbHelper.callEJB(trankey);
            } else if(cmds.hasOption('e')) {
                callEjbHelper.callExcel(trankey);;
            }
//            else if (cmds.hasOption('s')) {
//                callEjbHelper.scanSvnToPath();
//            } else if (cmds.hasOption('z')) {
//                callEjbHelper.scanWarToZip();
//            }
//            
// 
//            // 打印opts的名称和值
//            System.out.println("--------------------------------------");
//           System.out.println("   args:  svn.url            ="+callEjbHelper.pv.getProperty("svn.url"));
//            System.out.println("   args:  file.excel.merge   ="+callEjbHelper.pv.getProperty("file.excel.merge"));
//            Option[] opts = cmds.getOptions();
//            if (opts != null) {
//                for (Option opt1 : opts) {
//                    String name = opt1.getOpt();
//                    String lname= opt1.getLongOpt();
//                    if (StringUtils.isEmpty(lname))
//                        lname = name;
//                    String value = cmds.getOptionValue(lname);
//                    System.out.println(lname + "=>" + value);
//                }
//            }
//
//            String[] xargs = cmds.getArgs();
//            if (Array.getLength(xargs) > 0) {
//                System.out.println("Arguments : + Arrays.toString(xargs)" + xargs[0]);
//            }

        } catch (ParseException e) {
            String mess = e.getLocalizedMessage();
            hf.printHelp("Error==>" + mess + "\ntestAPP", options, true);
            // hf.printHelp("\nError==>"+e.getLocalizedMessage(),);
            // System.out.println("\nError==>"+e.getLocalizedMessage());
            // e.printStackTrace();.substring(0,mess.indexOf("[")-2)

        }

    }

    public static void main(String[] args) {
        //ConsoleTextArea.showForm();
        //ConsoleString.createConsole();
        
        if (args.length <=0 ){
            helpPrint(cmdCLIOptions());
            //Mainwin2.showForm();
        }else 
            if(args[0].startsWith("-")) {
            // 使用带- 的命令行模式
            parserCLICmd(cmdCLIOptions(), args);
        } else
            helpPrint(cmdCLIOptions());

        System.out.println("\nRun at >>> " + DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"));

        // System.out.print(PropFile.getExtPropertiesInstance().getProperty("file.excel"));
        
       //log.info(ConsoleString.Content.toString()); 
       //log.info(ConsoleTextArea.getString());
       //System.exit(0);

    }

}
