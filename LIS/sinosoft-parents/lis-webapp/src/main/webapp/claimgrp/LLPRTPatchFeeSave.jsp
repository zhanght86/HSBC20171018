<%
//**************************************************************************************************
//Name：LLPRTPatchFeeSave.jsp
//Function：补交保费通知书
//Author：wsz
//Date:   2005-8-8
//**************************************************************************************************
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
 
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="java.io.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
	CErrors tError = null;
	String FlagStr = "Fail";
	String Content = "";
	GlobalInput tGI = new GlobalInput(); 
	tGI=(GlobalInput)session.getValue("GI"); 
	//页面有效
	if(tGI == null)
	{
	    FlagStr = "Fail";
	    Content = "页面失效,请重新登陆";
	    loggerDebug("LLPRTPatchFeeSave","页面失效,请重新登陆");    
	}
	
    String transact = request.getParameter("fmtransact"); //获取操作
	String tPath = application.getRealPath("f1print/NCLtemplate") + "/";
    loggerDebug("LLPRTPatchFeeSave","tPath="+tPath);
    

    //String使用TransferData打包后提交-----用于传送 赔案号、客户号
    TransferData mTransferData = new TransferData();
//    mTransferData.setNameAndValue("ClmNo", request.getParameter("ClmNo")); //赔案号
//    mTransferData.setNameAndValue("PrtCode", "PCT008");
//    mTransferData.setNameAndValue("CustNo", request.getParameter("CustomerNo")); //客户号
	mTransferData.setNameAndValue("PrtSeq",request.getParameter("PrtSeq"));
   	mTransferData.setNameAndValue("Path",tPath);
   	
   	VData tVData = new VData(); //准备往传输数据 VData
    VData tResult = new VData(); //接受后台的数据
    XmlExport txmlExport = new XmlExport();
    tVData.add(tGI);
    tVData.add(mTransferData);
    //tVData.add(tLLPRTPatchFeeSchema);
    
//    LLPRTPatchFeeUI tLLPRTPatchFeeUI=new LLPRTPatchFeeUI();
//    if (!tLLPRTPatchFeeUI.submitData(tVData,transact))
//    {
//        Content = " 数据提交失败，原因是: " + tLLPRTPatchFeeUI.mErrors.getError(0).errorMessage;
//        FlagStr = "Fail";
//    }
String busiName="grpLLPRTPatchFeeUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
if(!tBusinessDelegate.submitData(tVData,transact,busiName))
{    
    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
    { 
		Content = "数据提交失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
	}
	else
	{
		Content = "数据提交失败";
		FlagStr = "Fail";				
	}
}

    else
    {
    	Content = " 数据提交成功! ";
	    FlagStr = "Succ";
	   // tResult = tLLPRTPatchFeeUI.getResult();	  
	     tResult = tBusinessDelegate.getResult();	    
	    txmlExport=(XmlExport)tResult.getObjectByObjectName("XmlExport",0);
	    FlagStr="Succ";
	    if(txmlExport==null)
	    {
	        FlagStr="FAIL";
	        Content="没有得到要显示的数据文件";
            loggerDebug("LLPRTPatchFeeSave","没有得到要显示的数据文件");
	    }
    }
	if (FlagStr.equals("Succ"))
	{
		
		String strVFPathName = tPath+"new.vts";
		loggerDebug("LLPRTPatchFeeSave","strVFPathName====="+strVFPathName);
		//用于预览页面，同时传入数据（流水号、补打标志、补打原因）
		session.putValue("RealPath", strVFPathName);
		session.putValue("PrtSeq", request.getParameter("PrtSeq"));
		loggerDebug("LLPRTPatchFeeSave","==流水号 : "+session.getValue("PrtSeq"));
		//本来打算采用get方式来传递文件路径----传递路径读取文件
		response.sendRedirect("../claim/LLClaimGetF1PrintJ.jsp");	
	}
 else
    {
%>
<html>
<script language="javascript">  
    alert("<%=Content%>");
    top.close();  
</script>
</html>
<%
  }
%>
