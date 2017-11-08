<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
//程序名称：GEdorTypeMultiDetailSubmit.jsp
//程序功能：
//创建日期：2002-07-19 16:49:22
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>

<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.bqgrp.*"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
	String busiName="bqgrpPGrpEdorZTDetailUI";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	loggerDebug("GEdorTypeMultiDetailAllSubmit","=====This is GEdorTypeMultiDetailSubmit.jsp=====\n");
	
    //接收信息，并作校验处理
    String FlagStr  = "";
    String Content  = "";
    String transact = "";
    String Result   = "";
    double tZTMoney = 0.0;
    TransferData tTransferData = new TransferData();
    String tIfCheckd = request.getParameter("IfToPubInsu");   
    tTransferData.setNameAndValue("IfCheckd", tIfCheckd); 
    GlobalInput tG = new GlobalInput();
    tG = (GlobalInput)session.getValue("GI");    
    LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
    LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();    
    tLPGrpEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
    tLPGrpEdorItemSchema.setGrpContNo(request.getParameter("GrpContNo"));
    tLPGrpEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));        
    tLPGrpEdorItemSchema.setEdorType(request.getParameter("EdorType"));
    tLPGrpEdorItemSchema.setEdorTypeCal(request.getParameter("EdorTypeCal"));
    tLPGrpEdorItemSchema.setMakeDate(PubFun.getCurrentDate());
    tLPGrpEdorItemSchema.setMakeTime(PubFun.getCurrentTime());
    tLPGrpEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
    tLPGrpEdorItemSchema.setModifyTime(PubFun.getCurrentTime());  
    tLPGrpEdorItemSchema.setOperator(tG.Operator);
    tLPGrpEdorItemSchema.setManageCom(tG.ManageCom);
    transact=(String)request.getParameter("Transact");
    loggerDebug("GEdorTypeMultiDetailAllSubmit","=====Detail submit: " + transact);            
if (transact.equals("INSERT||SAVEEDOR"))
{
    String FORMATMODOL = "0.00"; //保费保额计算出来后的精确位数
    DecimalFormat mDecimalFormat = new DecimalFormat(FORMATMODOL); //数字转换对象
  LPEdorItemDB tLPEdorItemDB=new LPEdorItemDB();
  tLPEdorItemDB.setEdorNo(request.getParameter("EdorNo"));
  tLPEdorItemDB.setEdorType(request.getParameter("EdorType"));
  //tLPEdorItemDB.setEdorState("3");
  tLPEdorItemSet=tLPEdorItemDB.query();
  //if(tLPEdorItemSet.size()==0)
  //{
  //    Content = " 保存成功！";
  //    FlagStr = "Success";   
  //}
  //else
  //{           
  for(int i = 1; i <= tLPEdorItemSet.size(); i++)
  { 
            FlagStr = "";   
            LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
            tLPEdorItemSchema=tLPEdorItemSet.get(i);                
            tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
            tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
            tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
            tLPEdorItemSchema.setContNo(tLPEdorItemSchema.getContNo());
            tLPEdorItemSchema.setInsuredNo(tLPEdorItemSchema.getInsuredNo());
            tLPEdorItemSchema.setPolNo(tLPEdorItemSchema.getPolNo());            
            tLPEdorItemSchema.setMakeDate(PubFun.getCurrentDate());
            tLPEdorItemSchema.setMakeTime(PubFun.getCurrentTime());
            tLPEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
            tLPEdorItemSchema.setModifyTime(PubFun.getCurrentTime());  
            tLPEdorItemSchema.setOperator(tG.Operator);
            tLPEdorItemSchema.setManageCom(tG.ManageCom);
            TransferData  mTransferData =new TransferData();                
            mTransferData.setNameAndValue("EdorNo", request.getParameter("EdorNo"));
            mTransferData.setNameAndValue("PolNo", tLPEdorItemSchema.getPolNo());
            mTransferData.setNameAndValue("ContNo", tLPEdorItemSchema.getContNo());
            mTransferData.setNameAndValue("Action", "2");
            mTransferData.setNameAndValue("EdorType", request.getParameter("EdorType"));
            loggerDebug("GEdorTypeMultiDetailAllSubmit",mTransferData.getValueByName("EdorType"));
          
	   				
	    try 
	    {  
	    	VData tVData = new VData();  
	    	tVData.addElement(tG);
	    	tVData.addElement(tLPGrpEdorItemSchema);
	    	tVData.addElement(tLPEdorItemSchema); 
	    	tVData.addElement(tTransferData);   					
    	        tBusinessDelegate.submitData(tVData, transact,busiName);    	
	    }	
	    catch(Exception ex) 
	    {							
	    	Content = transact + "失败，原因是:" + ex.toString();
	    	FlagStr = "Fail";
	    }
	   //如果在Catch中发现异常，则不从错误类中提取错误信息
	   if (FlagStr == "") 
	   {
	   	CErrors tError = new CErrors(); 							
	   	tError = tBusinessDelegate.getCErrors();						
	   	if (!tError.needDealError()) 
	   	{  								
	     	VData aVData = new VData();        
	     	Content = " 保存成功! ";
	   		FlagStr = "Success";
	   		aVData  = tBusinessDelegate.getResult();                                           
	   		tTransferData = (TransferData) aVData.getObjectByObjectName("TransferData", 0);
	   		//tt = (String)tPGrpEdorICDetailUI.getResult().get(0);
	   		String tt = (String)(tTransferData.getValueByName("ZTMoney"));   																							
	   		tZTMoney = tZTMoney + Double.parseDouble(tt);
	   	} 
	   	else 
		{ 
			Content = " 保存失败，原因是:" + tError.getFirstError();
			FlagStr = "Fail";
		}							
	} 	    
	    }			
	 					
	                           
    //}
    //tTotalMoney = tZTMoney.toString();    
    Result = String.valueOf(mDecimalFormat.format(tZTMoney));             
}   	
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>", "<%=Result%>");
</script>
</html>

