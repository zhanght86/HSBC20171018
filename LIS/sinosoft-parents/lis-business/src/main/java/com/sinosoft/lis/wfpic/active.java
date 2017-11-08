/*
*@author:lk
*@date:20080904 
*/
package com.sinosoft.lis.wfpic;
import org.apache.log4j.Logger;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.utility.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
public class active 
{
private static Logger logger = Logger.getLogger(active.class);

   private String Action="";
   private String ActionId="";
   private String Para="";
   private String Script="";
   private String flowVersion="";
   
   
   /*
    *  设置操作类型
    * */
   public void setAction(String action)
   {
	   Action=action;
   }
   /*
    * 设置操作ID
    * 
    * */
   public void setActionId(String actionId)
   {
	   ActionId=actionId;   
   }
   /*
    * 设置参数
    * */
   public void setPara(String para)
   {
	   Para=para;
   }
   /*
    * 执行操作
    * */
   public boolean doAction()
   {
       if("QUERY".equals(Action))
       {
    	   //查询一个串
    	   if("STRING".equals(ActionId))
    	   {
        	   ExeSQL tExeSQL=new ExeSQL();
        	   String temp=tExeSQL.getOneValue(Para);
        	   if(temp!=null&&!temp.equals(""))
        	   {
        		   Script="var _S='"+temp+"';";   
        	   }
        	   else
        	   {
        		   Script="var _S='';";
        	   }
    	   }
    	   //查询一个数组
    	   if("ARRAY".equals(ActionId))
    	   {
        	   ExeSQL tExeSQL=new ExeSQL();
        	   SSRS tSSRS=tExeSQL.execSQL(Para);
        	   if(tSSRS.MaxRow>0)
        	   {
        		   Script="var _A ="+formatSSRS(tSSRS)+";";
        	   }
        	   else
        	   {
        		   Script="var _A='';";
        	   }
    	   }
    	   //查询一个blob
    	   if("BLOB".equals(ActionId))
    	   {
    		   if(SysConst.DBTYPE.equals("ORACLE"))
    		   {
        		   String[] Paras=Para.split("::");
        		   COracleBlob tCOracleBlob = new COracleBlob();
        		   CMySQLBlob tCMySQLBlob = new CMySQLBlob();
        		   try
        		   {
        		      Blob tBlob = null;
        		      Connection conn=DBConnPool.getConnection();
        		      if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
        		    	  tBlob = tCOracleBlob.SelectBlob(Paras[1],Paras[0],Paras[2],conn);
	           		   }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
	           			 tBlob = tCMySQLBlob.SelectBlob(Paras[1],Paras[0],Paras[2],conn);
	           		   }
        		      Script="var _XML='"+inputStream2String(tBlob)+"';";
        		      conn.close();
        		   }
        		   catch(Exception ex)
        		   {
        			   Script="var _XML='';";
        			   logger.debug("查询失败！");
        		   }
    		   }
    	   }
    	   if("REBUILD".equals(ActionId))
    	   {        		   
       		   VData tVData=new VData();
       		   String[] Paras=Para.split("::");
    		   String operate="REBUILD||FLOW";
    		   TransferData tTransferData=new TransferData();
    		   GlobalInput tGlobalInput=new GlobalInput();
    		   tTransferData.setNameAndValue("ProcessId", Paras[0]);
    		   tTransferData.setNameAndValue("version", Paras[1]);
    		   tVData.add(tGlobalInput);
    		   tVData.add(tTransferData);
    		   LWFlowBL tLWFlowBL=new LWFlowBL();
    		   if(tLWFlowBL.submitData(tVData, operate))
    		   {
    			   String xml=(String)tLWFlowBL.getResult().get(0);
    			   Script="var _XML='"+xml+"'";
    		   }
    		   else
    		   {
    			   String err=tLWFlowBL.mErrors.getFirstError();
    			   Script="var result='"+err+"'";
    		   }	       		   
    	   }
    	   if("DEGREE".equals(ActionId))
    	   {
       		   VData tVData=new VData();
    		   String operate="DEGREE||FLOW";
    		   TransferData tTransferData=new TransferData();
    		   GlobalInput tGlobalInput=new GlobalInput();
    		   String[] Paras=Para.split("::");
    		   tTransferData.setNameAndValue("ProcessId", Paras[0]);
    		   tTransferData.setNameAndValue("Degree", Paras[1]);
    		   //zhaojiawei
    		   tTransferData.setNameAndValue("version", Paras[2]);
    		   tVData.add(tGlobalInput);
    		   tVData.add(tTransferData);
    		   LWFlowBL tLWFlowBL=new LWFlowBL();
    		   if(tLWFlowBL.submitData(tVData, operate))
    		   {
    			   String xml=(String)tLWFlowBL.getResult().get(0);
    			   Script="var _XML='"+xml+"'";
    		   }
    		   else
    		   {
    			   String err=tLWFlowBL.mErrors.getFirstError();
    			   Script="var result='"+err+"'";
    		   }	       		   
    	   }    	   
       }
       else if("INSERT".equals(Action))
       {
    	   //生成流程编号
    	   if("FlowId".equals(ActionId))
    	   {
    		   Script="var flowId="+ PubFun1.CreateMaxNo("ProcessID", Para)+";";
    		   //tongmeng 2012-08-24 add
    		   //增加处理工作流版本
    		   String tVersion = "0";
    		  // String tSQL = "select max(version)+1 from  Lwprocessxml where busitype='"+Para+"' ";
    		   //String tVersion = (new ExeSQL()).getOneValue(tSQL);
    		   Script = Script + " var versionId="+tVersion+";";
    				   
    	   }
    	   if("SaveFlow".equals(ActionId))
    	   {
    		   VData tVData=new VData();
    		   String operate="INSERT||FLOW";
    		   TransferData tTransferData=new TransferData();
    		   GlobalInput tGlobalInput=new GlobalInput();
    		   String[] Paras=Para.split("::");
    		   tTransferData.setNameAndValue("FlowXML", Paras[0]);
    		   tGlobalInput.Operator=Paras[1];
    		   tGlobalInput.ManageCom=Paras[2];
    		   //tGlobalInput.Operator="00000";
    		   //tGlobalInput.ManageCom="1111";
    		   tVData.add(tGlobalInput);
    		   tVData.add(tTransferData);
    		   LWFlowBL tLWFlowBL=new LWFlowBL();
    		   if(tLWFlowBL.submitData(tVData, operate))
    		   {
    			   Script="var result='success'";
    		   }
    		   else
    		   {
    			   String err=tLWFlowBL.mErrors.getFirstError();
    			   Script="var result='"+err+"'";
    		   }	   
    	   }
    	   if("RebuildFlow".equals(ActionId))
    	   {
    		   VData tVData=new VData();
    		   String operate="RESAVE||FLOW";
    		   TransferData tTransferData=new TransferData();
    		   GlobalInput tGlobalInput=new GlobalInput();
    		   String[] Paras=Para.split("::");
    		   tTransferData.setNameAndValue("FlowXML", Paras[0]);
    		   tGlobalInput.Operator=Paras[1];
    		   tGlobalInput.ManageCom=Paras[2];
    		   //tGlobalInput.Operator="00000";
    		   //tGlobalInput.ManageCom="1111";
    		   tVData.add(tGlobalInput);
    		   tVData.add(tTransferData);
    		   LWFlowBL tLWFlowBL=new LWFlowBL();
    		   if(tLWFlowBL.submitData(tVData, operate))
    		   {
    			   Script="var result='success'";
    		   }
    		   else
    		   {
    			   String err=tLWFlowBL.mErrors.getFirstError();
    			   Script="var result='"+err+"'";
    		   }	   
    	   }    	   
       } 
       else if("DELETE".equals(Action))
       {
       	   if("DelFlow".equals(ActionId))
    	   {
    		   VData tVData=new VData();
    		   String operate="DELETE||FLOW";
    		   TransferData tTransferData=new TransferData();
    		   GlobalInput tGlobalInput=new GlobalInput();
    		   String[] Paras=Para.split("::");
    		   tTransferData.setNameAndValue("FlowXML", Paras[0]);
    		   tGlobalInput.Operator=Paras[1];
    		   tGlobalInput.ManageCom=Paras[2];
    		   //tGlobalInput.Operator="00000";
    		   //tGlobalInput.ManageCom="1111";
    		   tVData.add(tGlobalInput);
    		   tVData.add(tTransferData);
    		   LWFlowBL tLWFlowBL=new LWFlowBL();
    		   if(tLWFlowBL.submitData(tVData, operate))
    		   {
    			   Script="var result='success'";
    		   }
    		   else
    		   {
    			   String err=tLWFlowBL.mErrors.getFirstError();
    			   Script="var result='"+err+"'";
    		   }	   
    	   }    	   
       }
       else
       {
    	   logger.debug("不支持此种操作！");
       }
	   return true;
   }
   /*
    * 得到结果
    * */
   public String getResult()
   {
	   return Script;
   }
   /*
    * 格式化结果
    * */
   public String formatSSRS(SSRS tSSRS)
   {
	   StringBuffer sb = new StringBuffer(10000);
	   sb.append("[");
	   
	   for(int i=1;i<=tSSRS.MaxRow;i++)
	   {
		   sb.append("[");
		   for(int j=1;j<=tSSRS.MaxCol;j++)
		   {
			   sb.append("\""+tSSRS.GetText(i, j)+"\"");
			   if(j!=tSSRS.MaxCol)
			   {
				   sb.append(",");
			   }
		   }
		   sb.append("]");
		   if(i!=tSSRS.MaxRow)
		   {
			   sb.append(",");
		   }
	   }
	   sb.append("]");
	   return sb.toString();
   }
   /*
    * 格式化结果
    * */
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
   
   private String inputStream2String(Blob blob)
   {
   	try
   	{
   		return new String(blob.getBytes(1, (int) blob.length()),"GBK");   
//           BufferedReader in = new BufferedReader(new InputStreamReader(is));
//           StringBuffer buffer = new StringBuffer();
//           String line = "";
//           while ((line = in.readLine()) != null)
//           {
//             buffer.append(line);
//           }
//           return buffer.toString();
   	}
   	catch(Exception ex)
   	{
   		logger.debug(ex.toString());
   		return null;
   	}
   }


   public static void main(String[] args)
   {
       active tt=new active();
       tt.setAction("QUERY");
       tt.setActionId("REBUILD");
       tt.setPara("13::6130000001");
       tt.doAction();
   } 
}
