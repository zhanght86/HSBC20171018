<%
//文件名称：LLClaimEndCaseParaPrint.jsp
//文件功能：结案单证打印-----传入 “单证代码”、“赔案号、客户号”
//创建人：yuejw
//创建日期:2005-08-10
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
	if(tGI == null)
	{
	    FlagStr = "Fail";
	    Content = "页面失效,请重新登陆";
	    loggerDebug("LLClaimEndCaseParaPrint","页面失效,请重新登陆");    
	}

		String transact = request.getParameter("fmtransact"); //获取操作
		String tPath = application.getRealPath("f1print/NCLtemplate") + "/"; //模版所在路径
		loggerDebug("LLClaimEndCaseParaPrint","tPath="+tPath);
		
		//单证打印参数-----用于打印单个单证时传入“单证代码”
	    LLParaPrintSchema tLLParaPrintSchema = new LLParaPrintSchema();
		tLLParaPrintSchema.setPrtCode(request.getParameter("PrtCode"));
		
	    //String使用TransferData打包后提交-----用于传送 赔案号、客户号、模版路径
	    TransferData mTransferData = new TransferData();
	    mTransferData.setNameAndValue("ClmNo", request.getParameter("ClmNo")); //赔案号
	    mTransferData.setNameAndValue("CustNo", request.getParameter("CustomerNo")); //客户号
	   	mTransferData.setNameAndValue("Path",tPath);
	   	 mTransferData.setNameAndValue("PrtSeq",request.getParameter("PrtSeq"));

	   	VData tVData = new VData(); //准备往传输数据 VData
	    VData tResult = new VData(); //接收后台的数据
	    XmlExport txmlExport = new XmlExport();
	    tVData.add(tGI);
	    tVData.add(mTransferData);
	    tVData.add(tLLParaPrintSchema);
	    
//	    LLParaPrintUI tLLParaPrintUI=new LLParaPrintUI();
//	    if (!tLLParaPrintUI.submitData(tVData,transact))
//	    {
//	        Content = " 数据提交失败，原因是: " + tLLParaPrintUI.mErrors.getError(0).errorMessage;
//	        FlagStr = "Fail";
//	    }
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
		    if(txmlExport==null)
		    {
		        FlagStr="FAIL";
		        Content="没有得到要显示的数据文件";
	            loggerDebug("LLClaimEndCaseParaPrint","没有得到要显示的数据文件");
		    }
		}
		
		//生成临时文件-----打印
		if (FlagStr.equals("Succ"))
		{
			ExeSQL tExeSQL = new ExeSQL();
			//获取临时文件名
			String strSql = "select SysVarValue from ldsysvar where Sysvar='VTSFilePath'";
			String strFilePath = tExeSQL.getOneValue(strSql);
			loggerDebug("LLClaimEndCaseParaPrint","临时文件名strFilePath="+strFilePath);
			String strVFFileName = strFilePath+"new.vts";
			
			//获取存放临时文件的路径
			String strRealPath = application.getRealPath("/").replace('\\','/');
			loggerDebug("LLClaimEndCaseParaPrint","临时文件的路径strRealPath="+strRealPath);
			String strVFPathName = strRealPath + strVFFileName;
			
			//生成临时VTS文件
			CombineVts tcombineVts = null;
			String strTemplatePath = application.getRealPath("f1print/MStemplate/") + "/";
			loggerDebug("LLClaimEndCaseParaPrint","strTemplatePath====="+strTemplatePath);
			tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);
			ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
			tcombineVts.output(dataStream);
			
			//把数据流 dataStream存储到磁盘文件
			loggerDebug("LLClaimEndCaseParaPrint","存储文件到"+strVFPathName);
			AccessVtsFile.saveToFile(dataStream,strVFPathName);
			loggerDebug("LLClaimEndCaseParaPrint","===strVFFileName : "+strVFFileName);
			
			//用于预览页面，同时传入数据（流水号、补打标志、补打原因）
			session.putValue("RealPath", strVFPathName);
			session.putValue("PrtSeq", request.getParameter("PrtSeq"));
			loggerDebug("LLClaimEndCaseParaPrint","==流水号 : "+session.getValue("PrtSeq"));
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
