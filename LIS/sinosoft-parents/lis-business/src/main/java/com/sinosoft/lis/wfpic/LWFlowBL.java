package com.sinosoft.lis.wfpic;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.sql.Blob;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import com.sinosoft.lis.db.LWActivityDB;
import com.sinosoft.lis.db.LWConditionDB;
import com.sinosoft.lis.db.LWProcessDB;
import com.sinosoft.lis.db.LWProcessTransDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LWActivitySchema;
import com.sinosoft.lis.schema.LWProcessSchema;
import com.sinosoft.lis.schema.LWProcessTransSchema;
import com.sinosoft.lis.schema.LWProcessXMLSchema;
import com.sinosoft.lis.schema.LWTransTimeSchema;
import com.sinosoft.lis.vschema.LWActivitySet;
import com.sinosoft.lis.vschema.LWConditionSet;
import com.sinosoft.lis.vschema.LWProcessTransSet;
import com.sinosoft.lis.vschema.LWTransTimeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.CMySQLBlob;
import com.sinosoft.utility.COracleBlob;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class LWFlowBL 
{
private static Logger logger = Logger.getLogger(LWFlowBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    private VData mResult = new VData();
    private MMap map = new MMap();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();
    /** 全局数据 */
    private GlobalInput mGlobalInput;
    private TransferData mTransferData ;
    private Reflections mReflections = new Reflections();
    /** 数据操作字符串 */
    private String mOperate;
    private String FlowXML="";
    private String RebuildXML="";
    private String ProcessId="";
    private String Version = "";
//    private String StrErr="";

    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *         cOperate 数据操作
     * @return:
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        //将操作数据拷贝到本类中
        this.mOperate = cOperate;
        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData)) 
        {
            CError tError = new CError();
            tError.moduleName = "LWFlowBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "数据处理失败LWFlowBL-->dealData!";
            this.mErrors.addOneError(tError);
            return false;
        }
        logger.debug("End getInputData!");

        //进行业务处理
        if (!dealData())
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LWFlowBL";
            tError.functionName = "dealData";
            tError.errorMessage = "数据处理失败LWFlowBL-->dealData!";
            this.mErrors.addOneError(tError);
            return false;
        }
        if("REBUILD||FLOW".equals(mOperate))
        {
        	return true;
        }
                
        logger.debug("over dealData");
        //准备往后台的数据
        if (!prepareOutputData()) 
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LWFlowBL";
            tError.functionName = "prepareOutputData";
            tError.errorMessage =
                    "数据处理失败LWFlowBL-->prepareOutputData!";
            this.mErrors.addOneError(tError);
            return false;
        }
        PubSubmit tSubmit = new PubSubmit();
        if (!tSubmit.submitData(mInputData, "")) 
        { //数据提交
            // @@错误处理
            this.mErrors.copyAllErrors(tSubmit.mErrors);
            CError tError = new CError();
            tError.moduleName = "LWFlowBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    /**
     * 根据前面的输入数据，进行BL逻辑处理
     * 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean dealData() 
    {
        if (this.mOperate.equals("INSERT||FLOW"))
        {
           
    		try 
    		{
                SAXBuilder builder = new SAXBuilder(false);
     		   
    		    Document doc=builder.build(new StringReader(FlowXML));

    			Element root=doc.getRootElement();
    			Element flowConfig=root.getChild("FlowConfig");
    			

				//流程定制
				//先判断是否已经存过，有，再看有没有使用过，没用，先删，再存,有用过 生成新的processid,作为一个新的流程，保留历史轨迹 
				//这里对已经存在的不考虑进行update的方式，只新增
				//zhaojiawei 这里已经存在且正在使用的版本进行新增版本。
				Element flowbase=flowConfig.getChild("BaseProperties");
				String busitype=flowbase.getAttributeValue("flowType");
				String processid=flowbase.getAttributeValue("flowId");
				String version =flowbase.getAttributeValue("version");
				//zhaojiawei 版本控制
				Version = flowbase.getAttributeValue("version");
				String tCurrentVersion = Version;
				Element flowflowprop=flowConfig.getChild("FlowProperties");	
				String sql="select count(*) from LWprocessxml where processid ='?processid?'and version ='?Version?'";
				SQLwithBindVariables sqlbv=new SQLwithBindVariables();
				sqlbv.sql(sql);
				sqlbv.put("processid", processid);
				sqlbv.put("Version", Version);
				ExeSQL tExeSQL=new ExeSQL();
				String count=tExeSQL.getOneValue(sqlbv);
				
				String valuedFlag="0";
				
				//zhaojiawei已经存过，表示不是新建而是修改
				if(count!=null&&!count.equals("")&&!count.equals("0"))
				{
					boolean isDelete=true;	
					isDelete = false;
					//zhaojiawei查看lwmission,lbmission表，如有记录表示此流程正在使用， 不能修改当前流程版本只能增加一个新版本
//					sql="select count(*) from  lwmission where processid='"+processid+"'and version ='"+Version+"'";
//					count=tExeSQL.getOneValue(sql);
//    				if(count!=null&&!count.equals("")&&!count.equals("0"))
//    				{  
//    					JOptionPane.showMessageDialog(null, "该流程正在使用不能被修改，只能作为一条新的版本数据被保存");
//    					isDelete=false;
//    				}
//    				else
//    				{
//    					sql="select count(*) from lbmission where processid='"+processid+"'and version ='"+Version+"'";
//    					count=tExeSQL.getOneValue(sql);
//        				if(count!=null&&!count.equals("")&&!count.equals("0"))
//        				{
//        					isDelete=false;
//        				}
//    				}
    									
                    if(isDelete)
                    {
        				LWProcessXMLSchema tLWProcessXMLSchema=new LWProcessXMLSchema();           			
            			tLWProcessXMLSchema.setProcessID(processid);
            			tLWProcessXMLSchema.setVersion(version);
            			
    					sql="select valuedflag from  lwprocessxml where processid='?processid?'and version ='?Version?'";
    					SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
    					sqlbv1.sql(sql);
    					sqlbv1.put("processid", processid);
    					sqlbv1.put("Version", Version);
    					valuedFlag=tExeSQL.getOneValue(sqlbv1);
    					
                        map.put(tLWProcessXMLSchema, "BLOBDELETE");   
                        SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
                        sqlbv2.sql("delete from LWProcess where  processid='?processid?'and version ='?Version?'");
                        sqlbv2.put("processid", processid);
                        sqlbv2.put("Version", Version);
                        SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
                        sqlbv3.sql("delete from LWProcessTrans where  processid='?processid?'and version ='?Version?'");
                        sqlbv3.put("processid", processid);
                        sqlbv3.put("Version", Version);
                        SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
                        sqlbv4.sql("delete from LWTransTime where timeid=(select timeid from LWProcess where  processid='?processid?' and version ='?Version?')");
                        sqlbv4.put("processid", processid);
                        sqlbv4.put("Version", Version);
                        map.put(sqlbv2,"DELETE");
        				map.put(sqlbv3,"DELETE"); 
        				map.put(sqlbv4, "DELETE");
                    }
                    else
                    {   
                    	//processid=PubFun1.CreateMaxNo("ProcessID", busitype);
                    	//生成新的VERSION
//                    	LWProcessSchema ttLWProcessSchema = new LWProcessSchema();
//                    	LWProcessDB tLWProcessDB = ttLWProcessSchema.getDB();
                        ExeSQL ttExeSQL = new ExeSQL();
                        SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
                        sqlbv5.sql("SELECT (case when max(VERSION/1) is not null then max(VERSION/1) else 0 end)+1 v FROM LWProcess  WHERE  ProcessID = '?processid?'");
                        sqlbv5.put("processid", processid);
            			String Versiontemp = ttExeSQL.getOneValue(sqlbv5);
                        //String Versiontemp = ttExeSQL.getOneValue("SELECT nvl(max(VERSION),0)+1 v FROM LWProcess  WHERE  ProcessID = '"+processid+"'");
                    	
                    	//String Versiontemp = tLWProcessDB.getVersion(processid);
                    	Version = Versiontemp ;
                    	flowbase.getAttribute("flowId").setValue(processid);
                    	flowbase.getAttribute("version").setValue(Version);
                    	//如果是新生成Version,需要更新lwcondition
                    	LWConditionSet  tLWConditionSet = new LWConditionSet();
                    	LWConditionDB tLWConditionDB = new LWConditionDB();
                    	tLWConditionDB.setProcessID(processid);
                    	tLWConditionDB.setVersion(tCurrentVersion);
                    	tLWConditionSet = tLWConditionDB.query();
                    	if(tLWConditionSet.size()>0)
                    	{
                    		for(int i=1;i<=tLWConditionSet.size();i++)
                    		{
                    			tLWConditionSet.get(i).setVersion(Version);
                    		}
                    		map.put(tLWConditionSet, "INSERT");
                    	}
                    	
                    	
                    }
				}         
				//新建也新生成
				ExeSQL ttExeSQL = new ExeSQL();
				SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
				sqlbv6.sql("SELECT (case when max(VERSION/1) is not null then max(VERSION/1) else 0 end)+1 v FROM LWProcess  WHERE ProcessID = '?processid?'");
				sqlbv6.put("processid", processid);
      			String Versiontemp = ttExeSQL.getOneValue(sqlbv6);
              	Version = Versiontemp ;
              	flowbase.getAttribute("flowId").setValue(processid);
              	flowbase.getAttribute("version").setValue(Version);
              	
                LWTransTimeSet tLWTransTimeSet=new LWTransTimeSet();
                Element flownodes=root.getChild("Nodes");
                List nodes=flownodes.getChildren("node");
                String timeId="";
                for(int i=1;i<=nodes.size();i++)
                {
                	Element flownode=(Element)nodes.get(i-1);
                	Element nodeflowprop= flownode.getChild("FlowProperties");
                	String nodeType=nodeflowprop.getAttributeValue("flowType");
                	//把图形的开始结束去掉
                	if(nodeType.equals("begin")||nodeType.equals("end"))
                	{
                          continue;
                	}
                	
                	String time=nodeflowprop.getAttributeValue("time");
                	String timeType=nodeflowprop.getAttributeValue("timeType");
                	if(time==null||time.equals(""))
                	{
                		continue;
                	}

            		if(timeId.equals(""))
            		{
            			timeId=PubFun1.CreateMaxNo("TIMEID", 10);
            			flowflowprop.getAttribute("timeId").setValue(timeId);
            		}

                	LWTransTimeSchema tLWTransTimeSchema=new LWTransTimeSchema();
                	tLWTransTimeSchema.setTimeID(timeId);
                	tLWTransTimeSchema.setProcessID(processid);
                	tLWTransTimeSchema.setActivityID(flownode.getChild("BaseProperties").getAttributeValue("id"));
                	tLWTransTimeSchema.setBusiType(busitype);
                	tLWTransTimeSchema.setVersion(Version);
                	tLWTransTimeSchema.setTimeFlag(timeType);
                	tLWTransTimeSchema.setTransitionTime(time);
                	tLWTransTimeSchema.setManageCom(mGlobalInput.ManageCom);
                 	tLWTransTimeSchema.setOperator(mGlobalInput.Operator);
                	tLWTransTimeSchema.setMakeDate(PubFun.getCurrentDate());
                	tLWTransTimeSchema.setMakeTime(PubFun.getCurrentTime());
                	tLWTransTimeSchema.setModifyDate(tLWTransTimeSchema.getMakeDate());
                	tLWTransTimeSchema.setModifyTime(tLWTransTimeSchema.getMakeTime());
                	tLWTransTimeSet.add(tLWTransTimeSchema);
                }
                if(tLWTransTimeSet.size()>0)
                {
                   map.put(tLWTransTimeSet, "INSERT");
                }
				//存
				LWProcessXMLSchema tLWProcessXMLSchema=new LWProcessXMLSchema();

                tLWProcessXMLSchema.setProcessID(processid);
                tLWProcessXMLSchema.setTimeID(timeId);
                tLWProcessXMLSchema.setProcessName(flowbase.getAttributeValue("flowName"));
                tLWProcessXMLSchema.setBusiType(busitype);
                tLWProcessXMLSchema.setProcessDesc(flowbase.getAttributeValue("flowName"));
                tLWProcessXMLSchema.setManageCom(mGlobalInput.ManageCom);
                tLWProcessXMLSchema.setOperator(mGlobalInput.Operator);
                tLWProcessXMLSchema.setMakeDate(PubFun.getCurrentDate());
                tLWProcessXMLSchema.setMakeTime(PubFun.getCurrentTime());
                tLWProcessXMLSchema.setModifyDate(tLWProcessXMLSchema.getMakeDate());
                tLWProcessXMLSchema.setModifyTime(tLWProcessXMLSchema.getMakeTime());
                tLWProcessXMLSchema.setVersion(Version);
                XMLOutputter xmlout=new XMLOutputter();
                tLWProcessXMLSchema.setWFXML(String2InputStream(xmlout.outputString(doc)));
                tLWProcessXMLSchema.setSysFlag("L");
                
                
                //对于已经生效的流程的修改，在其修改后应该还是有效
                tLWProcessXMLSchema.setValuedFlag(valuedFlag);
                map.put(tLWProcessXMLSchema, "BLOBINSERT");   				

                //后续解析生成页面相关的表
				//存
				LWProcessSchema tLWProcessSchema=new LWProcessSchema();
    			
				tLWProcessSchema.setProcessID(processid);
				tLWProcessSchema.setProcessName(flowbase.getAttributeValue("flowName"));
				tLWProcessSchema.setBusiType(busitype);
				tLWProcessSchema.setOperator(mGlobalInput.Operator);
				tLWProcessSchema.setMakeDate(tLWProcessXMLSchema.getMakeDate());
				tLWProcessSchema.setMakeTime(tLWProcessXMLSchema.getMakeTime());
				tLWProcessSchema.setModifyDate(tLWProcessSchema.getMakeDate());
				tLWProcessSchema.setModifyTime(tLWProcessSchema.getMakeTime());
				tLWProcessSchema.setSysFlag("L");
				tLWProcessSchema.setValuedFlag(valuedFlag);
				tLWProcessSchema.setTimeID(timeId);
				tLWProcessSchema.setVERSION(Version);
				
                map.put(tLWProcessSchema, "INSERT");       
                
                LWProcessTransSet tLWProcessTransSet=new LWProcessTransSet();
                Element flowlines=root.getChild("Lines");
                List lines=flowlines.getChildren("line");
	            
	            sql="select ActivityID from LWActivity where ActivityFlag='1'";
	            SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
	            sqlbv7.sql(sql);
	            String activityid=tExeSQL.getEncodedResult(sqlbv7);
	            //java.util.HashMap map=new java.util.HashMap();
	            //for()
	            
                for(int i=1;i<=lines.size();i++)
                {
                	Element flowline=(Element)lines.get(i-1);
                	Element linebase=flowline.getChild("BaseProperties");
                	String from=linebase.getAttributeValue("from");
                	String to=linebase.getAttributeValue("to");
                	//把图形的开始结束去掉
                	if(from.equals("begin")||to.equals("end"))
                	{
                          continue;
                	}

                	LWProcessTransSchema tLWProcessTransSchema=new LWProcessTransSchema();    	                            	
                	tLWProcessTransSchema.setTransitionID(PubFun1.CreateMaxNo("TransitionID",10));
                	tLWProcessTransSchema.setProcessID(processid);
                	tLWProcessTransSchema.setVersion(Version);
                	tLWProcessTransSchema.setTransitionStart(from);
                	tLWProcessTransSchema.setTransitionEnd(to);
                   	sql="select transitioncond,TransitionCondT from LWcondition where busitype='?busitype?' and transitionstart='?from?' and transitionend='?to?'"
                   	  + " and version='?tCurrentVersion?' and processid='?processid?' "
                   			;
                   	SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
                   	sqlbv8.sql(sql);
                   	sqlbv8.put("busitype", busitype);
                   	sqlbv8.put("from", from);
                   	sqlbv8.put("to", to);
                   	sqlbv8.put("tCurrentVersion", tCurrentVersion);
                   	sqlbv8.put("processid", processid);
                    SSRS tSSRS=tExeSQL.execSQL(sqlbv8);
                    if(tSSRS.MaxRow>0)
                    {
//                	   tLWProcessTransSchema.setTransitionCond(tSSRS.GetText(1, 1).replaceAll("'", "''"));
                	   tLWProcessTransSchema.setTransitionCond(tSSRS.GetText(1, 1));
                	
                	   tLWProcessTransSchema.setTransitionCondT(tSSRS.GetText(1, 2));
                    }
                	//防止多次请求库
                    
                    java.util.regex.Pattern pattern=java.util.regex.Pattern.compile(from);
                    java.util.regex.Matcher matcher = pattern.matcher(activityid);

                    
                	if(matcher.find())
                	{	
                	   tLWProcessTransSchema.setStartType("0");
                	}
                	else
                	{
                	   tLWProcessTransSchema.setStartType("2");
                	}
                	tLWProcessTransSet.add(tLWProcessTransSchema);
                }
                
                map.put(tLWProcessTransSet, "INSERT");    				

    		}
    		catch(Exception ex)
    		{
    			logger.debug(ex.toString());
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LWFlowBL";
                tError.functionName = "getInputData";
                tError.errorMessage = ex.toString();
                this.mErrors.addOneError(tError);
                return false;    			
    		}
        }
        //重构保存
        if (this.mOperate.equals("RESAVE||FLOW"))
        {
           
    		try 
    		{
                SAXBuilder builder = new SAXBuilder(false);
    		   
    		    Document doc=builder.build(new StringReader(FlowXML));

    			Element root=doc.getRootElement();
    			Element flowConfig=root.getChild("FlowConfig");

				
				Element flowbase=flowConfig.getChild("BaseProperties");
				String busitype=flowbase.getAttributeValue("flowType");
				String processid=flowbase.getAttributeValue("flowId");
				String Version=flowbase.getAttributeValue("version");
				Element flowflowprop=flowConfig.getChild("FlowProperties");
				
				String sql="select count(*) from LWprocessxml where processid ='?processid?'and version ='?Version?'";
				SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
				sqlbv9.sql(sql);
				sqlbv9.put("processid", processid);
				sqlbv9.put("Version", Version);
				ExeSQL tExeSQL=new ExeSQL();
				String count=tExeSQL.getOneValue(sqlbv9);
				
				String valuedFlag="0";
				
				//已经存过
				if(count!=null&&!count.equals("")&&!count.equals("0"))
				{
        				LWProcessXMLSchema tLWProcessXMLSchema=new LWProcessXMLSchema();           			
            			tLWProcessXMLSchema.setProcessID(processid);
            			tLWProcessXMLSchema.setVersion(Version);
                        map.put(tLWProcessXMLSchema, "BLOBDELETE");
                        SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
                        sqlbv10.sql("delete from LWTransTime where timeid=(select timeid from LWProcess where  processid='?processid?'and version ='?Version?')");
                        sqlbv10.put("processid", processid);
                        sqlbv10.put("Version", Version);
                        SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
                        sqlbv11.sql("update lwprocess set timeid='' where processid='?processid?'and version ='?Version?'");
                        sqlbv11.put("processid", processid);
                        sqlbv11.put("Version", Version);
        				map.put(sqlbv10, "DELETE");
        				map.put(sqlbv11, "UPDATE");
				}

                
                LWTransTimeSet tLWTransTimeSet=new LWTransTimeSet();
                Element flownodes=root.getChild("Nodes");
                List nodes=flownodes.getChildren("node");
                String timeId="";
                for(int i=1;i<=nodes.size();i++)
                {
                	Element flownode=(Element)nodes.get(i-1);
                	Element nodeflowprop= flownode.getChild("FlowProperties");
                	String nodeType=nodeflowprop.getAttributeValue("flowType");
                	//把图形的开始结束去掉
                	if(nodeType.equals("begin")||nodeType.equals("end"))
                	{
                          continue;
                	}
                	
                	String time=nodeflowprop.getAttributeValue("time");
                	String timeType=nodeflowprop.getAttributeValue("timeType");
                	if(time==null||time.equals(""))
                	{
                		continue;
                	}

            		if(timeId.equals(""))
            		{
            			timeId=PubFun1.CreateMaxNo("TIMEID", 10);
            			flowflowprop.getAttribute("timeId").setValue(timeId);
            			SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
            			sqlbv12.sql("update lwprocess set timeid='?timeId?' where processid='?processid?'and version ='?Version?'");
            			sqlbv12.put("timeId", timeId);
            			sqlbv12.put("processid", processid);
            			sqlbv12.put("Version", Version);
            			map.put(sqlbv12, "UPDATE");
            		}

                	LWTransTimeSchema tLWTransTimeSchema=new LWTransTimeSchema();
                	tLWTransTimeSchema.setTimeID(timeId);
                	tLWTransTimeSchema.setProcessID(processid);
                	tLWTransTimeSchema.setVersion(Version);
                	tLWTransTimeSchema.setActivityID(flownode.getChild("BaseProperties").getAttributeValue("id"));
                	tLWTransTimeSchema.setBusiType(busitype);               	
                	tLWTransTimeSchema.setTimeFlag(timeType);
                	tLWTransTimeSchema.setTransitionTime(time);
                	tLWTransTimeSchema.setManageCom(mGlobalInput.ManageCom);
                 	tLWTransTimeSchema.setOperator(mGlobalInput.Operator);
                	tLWTransTimeSchema.setMakeDate(PubFun.getCurrentDate());
                	tLWTransTimeSchema.setMakeTime(PubFun.getCurrentTime());
                	tLWTransTimeSchema.setModifyDate(tLWTransTimeSchema.getMakeDate());
                	tLWTransTimeSchema.setModifyTime(tLWTransTimeSchema.getMakeTime());
                	tLWTransTimeSet.add(tLWTransTimeSchema);
                }
                if(tLWTransTimeSet.size()>0)
                {
                   map.put(tLWTransTimeSet, "INSERT");    			
                }
				//存
				LWProcessXMLSchema tLWProcessXMLSchema=new LWProcessXMLSchema();

                tLWProcessXMLSchema.setProcessID(processid);
                tLWProcessXMLSchema.setTimeID(timeId);
                tLWProcessXMLSchema.setVersion(Version);
                tLWProcessXMLSchema.setProcessName(flowbase.getAttributeValue("flowName"));
                tLWProcessXMLSchema.setBusiType(busitype);
                tLWProcessXMLSchema.setProcessDesc(flowbase.getAttributeValue("flowName"));
                tLWProcessXMLSchema.setManageCom(mGlobalInput.ManageCom);
                tLWProcessXMLSchema.setOperator(mGlobalInput.Operator);
                tLWProcessXMLSchema.setMakeDate(PubFun.getCurrentDate());
                tLWProcessXMLSchema.setMakeTime(PubFun.getCurrentTime());
                tLWProcessXMLSchema.setModifyDate(tLWProcessXMLSchema.getMakeDate());
                tLWProcessXMLSchema.setModifyTime(tLWProcessXMLSchema.getMakeTime());
                
                XMLOutputter xmlout=new XMLOutputter();
                tLWProcessXMLSchema.setWFXML(String2InputStream(xmlout.outputString(doc)));
                tLWProcessXMLSchema.setSysFlag("L");
                
                
                //对于已经生效的流程的修改，在其修改后应该还是有效
                tLWProcessXMLSchema.setValuedFlag(valuedFlag);
                map.put(tLWProcessXMLSchema, "BLOBINSERT");    				

    		}
    		catch(Exception ex)
    		{
    			logger.debug(ex.toString());
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LWFlowBL";
                tError.functionName = "getInputData";
                tError.errorMessage = ex.toString();
                this.mErrors.addOneError(tError);
                return false;    			
    		}
        }        
        if (this.mOperate.equals("DELETE||FLOW"))
        {
           
    		try 
    		{
                SAXBuilder builder = new SAXBuilder(false);

    		    Document doc=builder.build(new StringReader(FlowXML));

    			Element root=doc.getRootElement();
    			Element flowConfig=root.getChild("FlowConfig");

    				//流程定制
    				//先判断是否已经存过，有，再看有没有使用过，没用，先删，再存
    				//这里对已经存在的不考虑进行update的方式
    				
    				Element flowbase=flowConfig.getChild("BaseProperties");
    				String busitype=flowbase.getAttributeValue("flowType");
    				String processid=flowbase.getAttributeValue("flowId");
    				String Version =flowbase.getAttributeValue("version");
    				String sql="select count(*) from LWprocessxml where processid ='?processid?'and version ='?Version?'";
    				SQLwithBindVariables sqlbv13=new SQLwithBindVariables();
    				sqlbv13.sql(sql);
    				sqlbv13.put("processid", processid);
    				sqlbv13.put("Version", Version);
    				ExeSQL tExeSQL=new ExeSQL();
    				String count=tExeSQL.getOneValue(sqlbv13);
    				
    				//已经存过
    				if(count!=null&&!count.equals("")&&!count.equals("0"))
    				{
    					//看有没有用过  正在用
    					sql="select count(*) from  lwmission where processid='?processid?'and version ='?Version?'";
    					SQLwithBindVariables sqlbv14=new SQLwithBindVariables();
    					sqlbv14.sql(sql);
    					sqlbv14.put("processid", processid);
    					sqlbv14.put("Version", Version);
    					count=tExeSQL.getOneValue(sqlbv14);
        				if(count!=null&&!count.equals("")&&!count.equals("0"))
        				{
        	                CError tError = new CError();
        	                tError.moduleName = "LWFlowBL";
        	                tError.functionName = "dealData";
        	                tError.errorMessage = "该流程已经被使用，不能删除";
        	                this.mErrors.addOneError(tError);
        	                return false;
        				}
        				else
        				{
        					sql="select count(*) from lbmission where processid='?processid?'and version ='?Version?'";
        					SQLwithBindVariables sqlbv15=new SQLwithBindVariables();
        					sqlbv15.sql(sql);
        					sqlbv15.put("processid", processid);
        					sqlbv15.put("Version", Version);
        					count=tExeSQL.getOneValue(sqlbv15);
            				if(count!=null&&!count.equals("")&&!count.equals("0"))
            				{
            	                CError tError = new CError();
            	                tError.moduleName = "LWFlowBL";
            	                tError.functionName = "dealData";
            	                tError.errorMessage = "该流程已经被使用，不能删除";
            	                this.mErrors.addOneError(tError);
            	                return false;
            				}
        				}
        				
        				LWProcessXMLSchema tLWProcessXMLSchema=new LWProcessXMLSchema();           			
            			tLWProcessXMLSchema.setProcessID(processid);
            			tLWProcessXMLSchema.setVersion(Version);

                        map.put(tLWProcessXMLSchema, "BLOBDELETE");  
                        SQLwithBindVariables sqlbv16=new SQLwithBindVariables();
                        sqlbv16.sql("delete from LWProcess where  processid='?processid?'and version ='?Version?'");
                        sqlbv16.put("processid", processid);
                        sqlbv16.put("Version", Version);
                        SQLwithBindVariables sqlbv17=new SQLwithBindVariables();
                        sqlbv17.sql("delete from LWProcessTrans where  processid='?processid?'and version ='?Version?'");
                        sqlbv17.put("processid", processid);
                        sqlbv17.put("Version", Version);
                        SQLwithBindVariables sqlbv18=new SQLwithBindVariables();
                        sqlbv18.sql("delete from LWTransTime where timeid=(select timeid from LWProcess where  processid='?processid?'and version ='?Version?')");
                        sqlbv18.put("processid", processid);
                        sqlbv18.put("Version", Version);
                        map.put(sqlbv16,"DELETE");
                        
        				map.put(sqlbv17,"DELETE");  
        				
        				map.put(sqlbv18, "DELETE");
     				}

    		}
    		catch(Exception ex)
    		{
    			logger.debug(ex.toString());
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LWFlowBL";
                tError.functionName = "getInputData";
                tError.errorMessage = ex.toString();
                this.mErrors.addOneError(tError);
                return false;    			
    		}
        }        
        //重构读出
        if (this.mOperate.equals("REBUILD||FLOW"))
        {
           
    		try 
    		{
    			int x=50;
    			int y=100;
    			
    			LWProcessDB tLWProcessDB=new LWProcessDB();
    			tLWProcessDB.setProcessID(ProcessId);
    			//zhaojiawei
    			tLWProcessDB.setVERSION(Version);
    			if(!tLWProcessDB.getInfo())
    			{
                    CError tError = new CError();
                    tError.moduleName = "LWFlowBL";
                    tError.functionName = "DealData";
                    tError.errorMessage = tLWProcessDB.mErrors.getFirstError();
                    this.mErrors.addOneError(tError);
                    return false;    			    				
    			}
    			LWProcessSchema tLWProcessSchema=tLWProcessDB.getSchema();
    			Element root = new Element("WorkFlow");

    			Document myDocument = new Document(root);
    		   
                //工作流
    			Element FlowConfig = new Element("FlowConfig");
    			Element flowbase = new Element("BaseProperties");
    			flowbase.addAttribute("flowId",tLWProcessSchema.getProcessID());
    			flowbase.addAttribute("flowName",tLWProcessSchema.getProcessName());
    			flowbase.addAttribute("flowType",tLWProcessSchema.getBusiType());
    			flowbase.addAttribute("version",tLWProcessSchema.getVERSION());
    			FlowConfig.addContent(flowbase);
    			
    			Element flowvmlproperties = new Element("VMLProperties");
    			flowvmlproperties.addAttribute("nodeTextColor","black");
    			flowvmlproperties.addAttribute("nodeFillColor","#EEEEEE");
    			flowvmlproperties.addAttribute("nodeStrokeColor","#333333");
    			flowvmlproperties.addAttribute("nodeShadowColor","#black");
    			flowvmlproperties.addAttribute("nodeFocusedColor","#8E83F5");
    			flowvmlproperties.addAttribute("nodeStrokeWeight","1pt");
    			flowvmlproperties.addAttribute("isNodeShadow","T");
    			flowvmlproperties.addAttribute("lineStrokeColor","#000000");
    			flowvmlproperties.addAttribute("lineFocusedColor","#FF0000");
    			flowvmlproperties.addAttribute("lineStrokeWeight","1pt");
    			flowvmlproperties.addAttribute("fontColor","black");
    			flowvmlproperties.addAttribute("fontSize","10pt");
    			flowvmlproperties.addAttribute("fontFace","宋体");
    			flowvmlproperties.addAttribute("scale","1");
    			FlowConfig.addContent(flowvmlproperties);
    			
    			Element flowflowproperties = new Element("FlowProperties");
    			flowflowproperties.addAttribute("timeId","");
    			FlowConfig.addContent(flowflowproperties);
    			
    			root.addContent(FlowConfig);
    			
    			//节点
    			Element Nodes = new Element("Nodes");
    			
    			
    			Element node = new Element("node");
    			
    			Element nodebase = new Element("BaseProperties");
    			nodebase.addAttribute("id", "begin");
    			nodebase.addAttribute("nodeName", "开始");
    			nodebase.addAttribute("nodeType", "oval");
    			node.addContent(nodebase);
    			
    			Element nodevmlproperties  = new Element("VMLProperties");
    			nodevmlproperties.addAttribute("width", "50px");
    			nodevmlproperties.addAttribute("height", "40px");
    			nodevmlproperties.addAttribute("x", x+"px");
    			nodevmlproperties.addAttribute("y", y+"px");
    			node.addContent(nodevmlproperties);
    			
    			Element nodeflowproperties  = new Element("FlowProperties");
    			nodeflowproperties.addAttribute("flowType", "begin");
    			node.addContent(nodeflowproperties);   	
    			
    			Nodes.addContent(node);
    			
    			
    			LWActivityDB tLWActivityDB=new LWActivityDB();
    			String sql="select * from LWactivity where activityid in (select  transitionstart from LWProcessTrans where processid='?ProcessId?'and version ='?Version?' ) or activityid in (select  transitionend from LWProcessTrans where processid='?ProcessId?'and version ='?Version?' )";
    			SQLwithBindVariables sqlbv19=new SQLwithBindVariables();
    			sqlbv19.sql(sql);
    			sqlbv19.put("ProcessId", ProcessId);
    			sqlbv19.put("Version", Version);
    			LWActivitySet tLWActivitySet=tLWActivityDB.executeQuery(sqlbv19);
    			if(tLWActivitySet==null||tLWActivitySet.size()==0)
    			{
                    CError tError = new CError();
                    tError.moduleName = "LWFlowBL";
                    tError.functionName = "DealData";
                    tError.errorMessage = tLWActivityDB.mErrors.getFirstError();
                    this.mErrors.addOneError(tError);
                    return false;       				
    			}    			
    			
    			String beginId="";
    			String beginName="";
    			String endId="";
    			String endName="";
    			HashMap hashmap=new HashMap();
    			
    			for(int i=1;i<=tLWActivitySet.size();i++)
    			{
    				
    				y=y+100;
    				if(y>500)
    				{
    					y=100;
    					x=x+150;
    				}
    				
    				LWActivitySchema tLWActivitySchema=tLWActivitySet.get(i);
    				
        		    node = new Element("node");
        			
        		    nodebase = new Element("BaseProperties");
        			nodebase.addAttribute("id", tLWActivitySchema.getActivityID());
        			nodebase.addAttribute("nodeName", tLWActivitySchema.getActivityName());
        			nodebase.addAttribute("nodeType", "RoundRect");
        			node.addContent(nodebase);
        			
        		    nodevmlproperties  = new Element("VMLProperties");
        			nodevmlproperties.addAttribute("width", "100px");
        			nodevmlproperties.addAttribute("height", "50px");
        			nodevmlproperties.addAttribute("x", x+"px");
        			nodevmlproperties.addAttribute("y", y+"px");
        			node.addContent(nodevmlproperties);
        			
        		    nodeflowproperties  = new Element("FlowProperties");
        			nodeflowproperties.addAttribute("flowType", "course");
        			nodeflowproperties.addAttribute("timeType", "");
        			nodeflowproperties.addAttribute("time", "");
        			node.addContent(nodeflowproperties);   	
        			
        			Nodes.addContent(node);
        			
       				hashmap.put(tLWActivitySchema.getActivityID(), tLWActivitySchema.getActivityName());
    			}    
				y=y+100;
				if(y>500)
				{
					y=100;
					x=x+150;
				}
				
   			    node = new Element("node");
    			
    		    nodebase = new Element("BaseProperties");
    			nodebase.addAttribute("id", "end");
    			nodebase.addAttribute("nodeName", "结束");
    			nodebase.addAttribute("nodeType", "oval");
    			node.addContent(nodebase);
    			
    			nodevmlproperties  = new Element("VMLProperties");
    			nodevmlproperties.addAttribute("width", "50px");
    			nodevmlproperties.addAttribute("height", "40px");
    			nodevmlproperties.addAttribute("x", x+"px");
    			nodevmlproperties.addAttribute("y", y+"px");
    			node.addContent(nodevmlproperties);
    			
    			nodeflowproperties  = new Element("FlowProperties");
    			nodeflowproperties.addAttribute("flowType", "end");
    			node.addContent(nodeflowproperties);   	
    			
    			Nodes.addContent(node);    			
    			
    			root.addContent(Nodes);
    			
    			//连线
    			Element Lines = new Element("Lines");
    			
    			
    			LWProcessTransDB tLWProcessTransDB=new LWProcessTransDB();
    		    sql="select * from LWProcessTrans where processid='?ProcessId?'and version ='?Version?'  order by transitionid";
    		    SQLwithBindVariables sqlbv20=new SQLwithBindVariables();
    		    sqlbv20.sql(sql);
    		    sqlbv20.put("ProcessId", ProcessId);
    		    sqlbv20.put("Version", Version);
    			LWProcessTransSet tLWProcessTransSet=tLWProcessTransDB.executeQuery(sqlbv20);
    			if(tLWProcessTransSet==null||tLWProcessTransSet.size()==0)
    			{
                    CError tError = new CError();
                    tError.moduleName = "LWFlowBL";
                    tError.functionName = "DealData";
                    tError.errorMessage = tLWProcessTransDB.mErrors.getFirstError();
                    this.mErrors.addOneError(tError);
                    return false;       				
    			}
    			
    			
    			
    			for(int i=1;i<=tLWActivitySet.size();i++)
    			{    			
    				LWActivitySchema tLWActivitySchema=tLWActivitySet.get(i);
				    if("1".equals(tLWActivitySchema.getActivityFlag()))
				    {
				    	Element line = new Element("line");
		    			Element linebase = new Element("BaseProperties");
		    			linebase.addAttribute("id", "begin"+"and"+tLWActivitySchema.getActivityID());
		    			linebase.addAttribute("lineName", "开始"+" 到 "+tLWActivitySchema.getActivityName());
		    			linebase.addAttribute("lineType", "PolyLine");
		    			linebase.addAttribute("from", "begin");
		    			linebase.addAttribute("to", tLWActivitySchema.getActivityID());
		    			
		    			line.addContent(linebase);
		    			
		    			Element linevmlproperties  = new Element("VMLProperties");
		    			linevmlproperties.addAttribute("startArrow", "diamond");
		    			linevmlproperties.addAttribute("endArrow", "classic");
		    			line.addContent(linevmlproperties);
		    			
		    			Element lineflowproperties  = new Element("FlowProperties");
		    			lineflowproperties.addAttribute("flowType", "connect");
		    			lineflowproperties.addAttribute("conditon", "");
		    			line.addContent(lineflowproperties);   	
		    			
		    			Lines.addContent(line);    	
				    }
    			}
    			ExeSQL tExeSQL =new ExeSQL();
    			for(int i=1;i<=tLWProcessTransSet.size();i++)
    			{
    				LWProcessTransSchema tLWProcessTransSchema=tLWProcessTransSet.get(i);
    				Element line = new Element("line");
        			
        		    Element linebase = new Element("BaseProperties");
        			linebase.addAttribute("id", tLWProcessTransSchema.getTransitionStart()+"and"+tLWProcessTransSchema.getTransitionEnd());
        			linebase.addAttribute("lineName", hashmap.get(tLWProcessTransSchema.getTransitionStart())+" 到 "+ hashmap.get(tLWProcessTransSchema.getTransitionEnd()));
        			linebase.addAttribute("lineType", "PolyLine");
        			linebase.addAttribute("from", tLWProcessTransSchema.getTransitionStart());
        			linebase.addAttribute("to", tLWProcessTransSchema.getTransitionEnd());
        			
        			line.addContent(linebase);
        			
        			Element linevmlproperties  = new Element("VMLProperties");
        			linevmlproperties.addAttribute("startArrow", "diamond");
        			linevmlproperties.addAttribute("endArrow", "classic");
        			line.addContent(linevmlproperties);
        			
        			Element lineflowproperties  = new Element("FlowProperties");
        			lineflowproperties.addAttribute("flowType", "connect");
	    			
                   	sql="select conddesc from LWcondition where busitype='?busitype?' and transitionstart='?transitionstart?' and transitionend='?transitionend?'";      			
        			SQLwithBindVariables sqlbv21=new SQLwithBindVariables();
        			sqlbv21.sql(sql);
        			sqlbv21.put("busitype", tLWProcessSchema.getBusiType());
        			sqlbv21.put("transitionstart", tLWProcessTransSchema.getTransitionStart());
        			sqlbv21.put("transitionend", tLWProcessTransSchema.getTransitionEnd());
        			lineflowproperties.addAttribute("conditon", tExeSQL.getOneValue(sqlbv21));
        			line.addContent(lineflowproperties);   	
        			
        			Lines.addContent(line);    			    				
    				
    			}
    			
    			for(int i=1;i<=tLWActivitySet.size();i++)
    			{    			
    				LWActivitySchema tLWActivitySchema=tLWActivitySet.get(i);
				    if("3".equals(tLWActivitySchema.getActivityFlag()))
				    {
				    	Element line = new Element("line");
				    	Element linebase = new Element("BaseProperties");
		    			linebase.addAttribute("id", tLWActivitySchema.getActivityID()+"and"+"end");
		    			linebase.addAttribute("lineName", tLWActivitySchema.getActivityName()+" 到 "+"结束");
		    			linebase.addAttribute("lineType", "PolyLine");
		    			linebase.addAttribute("from", tLWActivitySchema.getActivityID());
		    			linebase.addAttribute("to", "end");
		    			
		    			line.addContent(linebase);
		    			
		    			Element linevmlproperties  = new Element("VMLProperties");
		    			linevmlproperties.addAttribute("startArrow", "diamond");
		    			linevmlproperties.addAttribute("endArrow", "classic");
		    			line.addContent(linevmlproperties);
		    			
		    			Element lineflowproperties  = new Element("FlowProperties");
		    			lineflowproperties.addAttribute("flowType", "connect");
		    			
		    			lineflowproperties.addAttribute("conditon", "");
		    			line.addContent(lineflowproperties);   	
		    			
		    			Lines.addContent(line);     	
				    }
    			}    			
 	    			
    			root.addContent(Lines);
	
    			XMLOutputter  xo=new  XMLOutputter("  ",false,"GBK"); 
    			ByteArrayOutputStream bo = new ByteArrayOutputStream();
    			xo.output(myDocument, bo);
    			RebuildXML = bo.toString();
    			//logger.debug(RebuildXML);
    		}
    		catch(Exception ex)
    		{
    		    ex.printStackTrace();
    			logger.debug(ex.toString());
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LWFlowBL";
                tError.functionName = "getInputData";
                tError.errorMessage = ex.toString();
                this.mErrors.addOneError(tError);
                return false;    			
    		}
        }                
        if (this.mOperate.equals("DEGREE||FLOW"))
        {
           
    		try 
    		{
     		    COracleBlob tCOracleBlob = new COracleBlob();
     		   CMySQLBlob tCMySQLBlob = new CMySQLBlob();
     		  
     		    ProcessId=(String) mTransferData.getValueByName("ProcessId");
     		    Version=(String) mTransferData.getValueByName("version");
  		        Blob tBlob = null;
		        Connection conn=DBConnPool.getConnection();
		        if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
		        	tBlob = tCOracleBlob.SelectBlob("LWPROCESSXML","WFXML", "and processid='"+ProcessId+"'",conn);
	     		  }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
	     			tBlob = tCMySQLBlob.SelectBlob("LWPROCESSXML","WFXML", "and processid='"+ProcessId+"'",conn);	
	     		  }
		        String _XML= inputStream2String(tBlob.getBinaryStream());
		        conn.close();
		        String degree=(String) mTransferData.getValueByName("Degree");
 
                //查询所有需要剔除的点
		        LWActivityDB tLWActivityDB=new LWActivityDB();
    			String sql="select * from LWactivity where (activityid in (select  transitionstart from LWProcessTrans where processid='?ProcessId?'and version ='?Version?' ) or activityid in (select  transitionend from LWProcessTrans where processid='?ProcessId?' )) and impdegree>'?degree?'";
    			SQLwithBindVariables sqlbv22=new SQLwithBindVariables();
    			sqlbv22.sql(sql);
    			sqlbv22.put("ProcessId", ProcessId);
    			sqlbv22.put("Version", Version);
    			sqlbv22.put("degree", degree);
    			LWActivitySet mergeLWActivitySet=tLWActivityDB.executeQuery(sqlbv22);
    			if(mergeLWActivitySet==null||mergeLWActivitySet.size()==0)
    			{
		        	//最低一级全部展现
		        	RebuildXML =_XML; 
		        	return true;	
    			} 
    			
    		    sql="select * from LWactivity where activityid in (select  transitionstart from LWProcessTrans where processid='?ProcessId?'and version ='?Version?' ) or activityid in (select  transitionend from LWProcessTrans where processid='?ProcessId?'and version ='?Version?' )";
    		    SQLwithBindVariables sqlbv23=new SQLwithBindVariables();
    		    sqlbv23.sql(sql);
    		    sqlbv23.put("ProcessId", ProcessId);
    		    sqlbv23.put("Version", Version);
    			LWActivitySet tLWActivitySet=tLWActivityDB.executeQuery(sqlbv23);
    			if(tLWActivitySet==null||tLWActivitySet.size()==0)
    			{
                    CError tError = new CError();
                    tError.moduleName = "LWFlowBL";
                    tError.functionName = "DealData";
                    tError.errorMessage = tLWActivityDB.mErrors.getFirstError();
                    this.mErrors.addOneError(tError);
                    return false;       				
    			}    					        
		        TransferData tTransferData=new TransferData();
		        for(int i=1;i<=tLWActivitySet.size();i++)
		        {
		        	tTransferData.setNameAndValue(tLWActivitySet.get(i).getActivityID(), tLWActivitySet.get(i));
		        }
		          
		        Graph tGraph=new Graph();
                SAXBuilder builder = new SAXBuilder(false);
                
    		    Document doc=builder.build(new StringReader(_XML));
                
    			Element root=doc.getRootElement();
    			Element flowConfig=root.getChild("FlowConfig");
     				
                Element flownodes=root.getChild("Nodes");
                List nodes=flownodes.getChildren("node");
                String timeId="";
                for(int i=1;i<=nodes.size();i++)
                {
                	Element flownode=(Element)nodes.get(i-1);
                	Element nodebaseprop= flownode.getChild("BaseProperties");
                	String nodeid=nodebaseprop.getAttributeValue("id");
                	Node node=new Node();
                	
                	node.setNodeID(nodeid);
                	node.setNodeName(nodebaseprop.getAttributeValue("nodeName"));
                	node.setNodeType(nodebaseprop.getAttributeValue("nodeType"));
                	Element nodevmlprop= flownode.getChild("VMLProperties");
                	node.setWidth(nodevmlprop.getAttributeValue("width"));
                	node.setHeight(nodevmlprop.getAttributeValue("height"));
                	node.setX(nodevmlprop.getAttributeValue("x"));
                	node.setY(nodevmlprop.getAttributeValue("y"));
                	Element nodeflowprop= flownode.getChild("FlowProperties");
                	node.setFlowType(nodeflowprop.getAttributeValue("flowType"));
                	node.setTimeType(nodeflowprop.getAttributeValue("timeType"));
                	node.setTime(nodeflowprop.getAttributeValue("time"));
                	if(nodeflowprop.getAttributeValue("flowType").equals("begin")||nodeflowprop.getAttributeValue("flowType").equals("end"))
                	{   
                		node.setDegree(0);
                	}
                	else
                	{
                		node.setDegree(((LWActivitySchema)tTransferData.getValueByName(nodeid)).getImpDegree());
                	}
                	tGraph.addNode(node);  
                }	
                Element flowlines=root.getChild("Lines");
                List lines=flowlines.getChildren("line");
 
                for(int i=1;i<=lines.size();i++)
                {
                	Element flowline=(Element)lines.get(i-1);
                	Element linebaseprop= flowline.getChild("BaseProperties");
             	
				    Edge tEdge=new Edge();
					tEdge.setEdgeID(linebaseprop.getAttributeValue("id"));
					tEdge.setEdgeName(linebaseprop.getAttributeValue("lineName"));
					tEdge.setLineType(linebaseprop.getAttributeValue("lineType"));
					tEdge.setFrom(linebaseprop.getAttributeValue("from"));
					tEdge.setTo(linebaseprop.getAttributeValue("to"));
					Element lineflowprop= flowline.getChild("FlowProperties");
					tEdge.setFlowType(lineflowprop.getAttributeValue("flowType"));
					tEdge.setConditon(lineflowprop.getAttributeValue("conditon"));
					tGraph.addEdge(tEdge);
                }   

	            for(int i=1;i<=mergeLWActivitySet.size();i++)
	            {
	            	int v=tGraph.getGraphNodeID(mergeLWActivitySet.get(i).getActivityID());
	            	tGraph.mergeNode(v);
	            	tGraph.delNode(v);
	            }

	            Element Nodes = new Element("Nodes");
	            for(int i=0;i<tGraph.getNodeCount();i++)
	            {
    			    //节点
	            	Node gnode=tGraph.getAllNode()[i];
	    			Element node = new Element("node");
	    			
	    			Element nodebase = new Element("BaseProperties");
	    			nodebase.addAttribute("id", gnode.getNodeID());
	    			nodebase.addAttribute("nodeName", gnode.getNodeName());
	    			nodebase.addAttribute("nodeType", gnode.getNodeType());
	    			node.addContent(nodebase);
	    			
	    			Element nodevmlproperties  = new Element("VMLProperties");
	    			nodevmlproperties.addAttribute("width", gnode.getWidth());
	    			nodevmlproperties.addAttribute("height", gnode.getHeight());
	    			nodevmlproperties.addAttribute("x", gnode.getX());
	    			nodevmlproperties.addAttribute("y", gnode.getY());
	    			node.addContent(nodevmlproperties);
	    			
	    			Element nodeflowproperties  = new Element("FlowProperties");
        			nodeflowproperties.addAttribute("flowType", gnode.getFlowType());
        			nodeflowproperties.addAttribute("timeType", gnode.getTimeType());
        			nodeflowproperties.addAttribute("time", gnode.getTime());
	    			node.addContent(nodeflowproperties);   	
	    			
	    			Nodes.addContent(node);
	            }
    			
    			Element Lines = new Element("Lines");
    			for(int i=0;i<tGraph.getEdgeCount();i++)
    			{
      				Element line = new Element("line");
        			Edge edge=tGraph.getAllEdge()[i];
        			
        		    Element linebase = new Element("BaseProperties");
        			linebase.addAttribute("id",edge.getEdgeID());
        			linebase.addAttribute("lineName",edge.getEdgeName());
        			linebase.addAttribute("lineType", "PolyLine");
        			linebase.addAttribute("from", edge.getFrom());
        			linebase.addAttribute("to", edge.getTo());
        			line.addContent(linebase);
        			
        			Element linevmlproperties  = new Element("VMLProperties");
        			linevmlproperties.addAttribute("startArrow", "diamond");
        			linevmlproperties.addAttribute("endArrow", "classic");
        			line.addContent(linevmlproperties);
        			
        			Element lineflowproperties  = new Element("FlowProperties");
        			lineflowproperties.addAttribute("flowType", edge.getFlowType());
        			lineflowproperties.addAttribute("conditon", edge.getConditon());
        			line.addContent(lineflowproperties);   	    			
        			Lines.addContent(line);  
    			}
    			root.removeChild("Nodes");
    			root.removeChild("Lines");
    			root.addContent(Nodes);
    			root.addContent(Lines);
    			
    			XMLOutputter  xo=new  XMLOutputter("  ",false,"GBK"); 
    			ByteArrayOutputStream bo = new ByteArrayOutputStream();
    			xo.output(doc, bo);
    			RebuildXML = bo.toString();   	
    			
    		}
    		catch(Exception ex)
    		{
    			logger.debug(ex.toString());
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LWFlowBL";
                tError.functionName = "getInputData";
                tError.errorMessage = ex.toString();
                this.mErrors.addOneError(tError);
                return false;    			
    		}
        }
        return true;
    }   
    
    /**
     * 从输入数据中得到所有对象
     *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData) 
    {
        //全局变量
        mGlobalInput=(GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0);
        mTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData", 0);        
        if (mGlobalInput == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LWFlowBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "没有得到足够的信息！";
            this.mErrors.addOneError(tError);
            return false;
        }//
        if(!mOperate.equals("REBUILD||FLOW"))
        {
        	FlowXML = (String) mTransferData.getValueByName("FlowXML");
        }
        else
        {
        	ProcessId=(String) mTransferData.getValueByName("ProcessId");
        	Version =(String) mTransferData.getValueByName("version");
        }
        
        
        return true;

    }

    /**
     * 准备往后层输出所需要的数据
     * 输出：如果准备数据时发生错误则返回false,否则返回true
     */
    private boolean prepareOutputData() 
    {
        try 
        {
            mInputData.clear();
            mInputData.add(map);
        } 
        catch (Exception ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LWFlowBL";
            tError.functionName = "prepareOutputData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    public VData getResult()
    {
    	mResult.add(RebuildXML);
        return this.mResult;
    }
    
    private InputStream String2InputStream(String str)
    {
        ByteArrayInputStream stream = new ByteArrayInputStream(str.getBytes());
        return stream;
    }

     
    private String inputStream2String(InputStream is)
    {
    	try
    	{
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = in.readLine()) != null)
            {
              buffer.append(line);
            }
            return buffer.toString();
    	}
    	catch(Exception ex)
    	{
    		logger.debug(ex.toString());
    		return null;
    	}
    }
    
}
