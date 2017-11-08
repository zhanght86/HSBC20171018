<%
//文件名称：LLClaimEndCasePrintContSave.jsp
//文件功能：结案单证打印(PCT013)------批单-合同处理批注,单独处理
//创建人：yuejw
//创建日期:2005-08-15
%>

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
	    loggerDebug("LLClaimEndCasePrintContSave","页面失效,请重新登陆");    
	}
	
    String transact = request.getParameter("fmtransact"); //获取操作
	String tPath = application.getRealPath("f1print/MStemplate") + "/";
    loggerDebug("LLClaimEndCasePrintContSave","tPath="+tPath);
    
        //单证打印参数-----用于打印单个单证时传入“单证代码”
    LLParaPrintSchema tLLParaPrintSchema = new LLParaPrintSchema();
	tLLParaPrintSchema.setPrtCode(request.getParameter("PrtCode"));
	
    //String使用TransferData打包后提交-----用于传送 赔案号、客户号
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("ClmNo", request.getParameter("ClmNo")); //赔案号
    mTransferData.setNameAndValue("CustNo", request.getParameter("CustomerNo")); //客户号
   	mTransferData.setNameAndValue("Path",tPath);
   	mTransferData.setNameAndValue("PrtSeq",request.getParameter("PrtSeq"));
   	
   	VData tVData = new VData(); //准备往传输数据 VData
    VData tResult = new VData(); //接受后台的数据
    XmlExport txmlExport = new XmlExport();
    tVData.add(tGI);
    tVData.add(mTransferData);
    tVData.add(tLLParaPrintSchema);
    
//    LLParaPrintUI tLLParaPrintUI=new LLParaPrintUI();
//    if (!tLLParaPrintUI.submitData(tVData,transact))
//    {
//        Content = " 数据提交失败，原因是: " + tLLParaPrintUI.mErrors.getError(0).errorMessage;
//        FlagStr = "Fail";
//    }
		String busiName="grpLLParaPrintUI";
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
	    //tResult = tLLParaPrintUI.getResult();
	    tResult = tBusinessDelegate.getResult();	    
	    txmlExport=(XmlExport)tResult.getObjectByObjectName("XmlExport",0);
	    FlagStr="Succ";
	    if(txmlExport==null)
	    {
	        FlagStr="FAIL";
	        Content="没有得到要显示的数据文件";
            loggerDebug("LLClaimEndCasePrintContSave","没有得到要显示的数据文件");
	    }
    }
	if (FlagStr.equals("Succ"))
	{
		
		String strVFPathName = tPath+"new.vts";
		loggerDebug("LLClaimEndCasePrintContSave","strVFPathName====="+strVFPathName);	
		//用于预览页面，同时传入数据（流水号、补打标志、补打原因）
		session.putValue("RealPath", strVFPathName);
		session.putValue("PrtSeq", request.getParameter("PrtSeq"));
		loggerDebug("LLClaimEndCasePrintContSave","==流水号 : "+session.getValue("PrtSeq"));
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
