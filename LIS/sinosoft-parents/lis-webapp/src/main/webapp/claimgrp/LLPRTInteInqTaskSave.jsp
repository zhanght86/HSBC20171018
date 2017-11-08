<%
//文件名称：LLPRTInteInqTaskSave.jsp
//文件功能：调查任务通知单打印
//创建人：yuejw
//创建日期:
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

<%
//输入参数
//输出参数
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
    loggerDebug("LLPRTInteInqTaskSave","页面失效,请重新登陆");    
}
//else
//{
    String transact = request.getParameter("fmtransact"); //获取操作
	String tPath = application.getRealPath("f1print/MStemplate") + "/";
    loggerDebug("LLPRTInteInqTaskSave","tPath="+tPath);
	
    //String使用TransferData打包后提交-----用于传送 赔案号、客户号
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("ClmNo", request.getParameter("ClmNo3")); //赔案号
    mTransferData.setNameAndValue("CustNo", request.getParameter("customerNo")); //客户号
    mTransferData.setNameAndValue("InqNo", request.getParameter("InqNo")); //调查序号
//   	mTransferData.setNameAndValue("Path",tPath);
      
    VData tVData = new VData(); //准备往传输数据 VData
    VData tResult = new VData(); //接受后台的数据
     XmlExport txmlExport = new XmlExport();
    tVData.add(tGI);
    tVData.add(mTransferData);
   
	LLPRTInteInqTaskUI tLLPRTInteInqTaskUI=new LLPRTInteInqTaskUI();
    if (!tLLPRTInteInqTaskUI.submitData(tVData,transact))
    {
        Content = " 数据提交失败，原因是: " + tLLPRTInteInqTaskUI.mErrors.getError(0).errorMessage;
        FlagStr = "Fail";
    }
    else
    {
    	Content = " 数据提交成功! ";
	    FlagStr = "Succ";
	    tResult = tLLPRTInteInqTaskUI.getResult();	    
	    txmlExport=(XmlExport)tResult.getObjectByObjectName("XmlExport",0);
	    FlagStr="Succ";
	    if(txmlExport==null)
	    {
	        FlagStr="FAIL";
	        Content="没有得到要显示的数据文件";
            loggerDebug("LLPRTInteInqTaskSave","没有得到要显示的数据文件");
	    }
    }
	if (FlagStr.equals("Succ"))
	{
		ExeSQL tExeSQL = new ExeSQL();
		//获取临时文件名
		String strSql = "select SysVarValue from ldsysvar where Sysvar='VTSFilePath'";
		String strFilePath = tExeSQL.getOneValue(strSql);
		String strVFFileName = strFilePath+tGI.Operator+"_" + FileQueue.getFileName()+".vts";
		//获取存放临时文件的路径
		String strRealPath = application.getRealPath("/").replace('\\','/');
		loggerDebug("LLPRTInteInqTaskSave","strRealPath="+strRealPath);
		String strVFPathName = strRealPath + strVFFileName;
		CombineVts tcombineVts = null;
		//合并VTS文件
		String strTemplatePath = application.getRealPath("f1print/MStemplate/") + "/";
		tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);
		ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		tcombineVts.output(dataStream);
		//把dataStream存储到磁盘文件
		loggerDebug("LLPRTInteInqTaskSave","存储文件到"+strVFPathName);
		AccessVtsFile.saveToFile(dataStream,strVFPathName);
		loggerDebug("LLPRTInteInqTaskSave","==> Write VTS file to disk ");
		loggerDebug("LLPRTInteInqTaskSave","===strVFFileName : "+strVFFileName);
		session.putValue("RealPath", strVFPathName);
		//本来打算采用get方式来传递文件路径
//		response.sendRedirect("../f1print/GetF1PrintJ1.jsp?RealPath="+strVFPathName);
		response.sendRedirect("../f1print/GetF1PrintJ1.jsp");
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
