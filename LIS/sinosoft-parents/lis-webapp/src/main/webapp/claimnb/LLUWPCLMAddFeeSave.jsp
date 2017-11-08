<%
//**************************************************************************************************
//Name：LLUWPCLMAddFeeSave.jsp
//Function：打印补费通知书
//Author：niuzj
//Date:   2006-01-24
//**************************************************************************************************
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
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.lis.claimnb.*"%>
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
	    loggerDebug("LLUWPCLMAddFeeSave","页面失效,请重新登陆");    
	}
	
    String transact = request.getParameter("fmtransact"); //获取操作
	  String tPath = application.getRealPath("f1print/MStemplate") + "/";
    loggerDebug("LLUWPCLMAddFeeSave","tPath="+tPath);
    
   
    //String使用TransferData打包后提交-----用于传送赔案号、通知书号
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("ContNo", request.getParameter("ContNo")); //合同号
    mTransferData.setNameAndValue("ClmNo", request.getParameter("ClmNo")); //赔案号
	  mTransferData.setNameAndValue("NoticeNo",request.getParameter("NoticeNo")); //通知书号
	  mTransferData.setNameAndValue("DoutyCode",request.getParameter("DoutyCode")); //责任编码
	  mTransferData.setNameAndValue("PayPlayCode",request.getParameter("PayPlayCode")); //交费计划编码
	  mTransferData.setNameAndValue("PolNo",request.getParameter("PolNo")); //保单号
   	//mTransferData.setNameAndValue("Path",tPath);
   	
   	VData tVData = new VData(); //准备往传输数据 VData
    VData tResult = new VData(); //接受后台的数据
    XmlExport txmlExport = new XmlExport();
    tVData.add(tGI);
    tVData.add(mTransferData);
    
//    LLUWPCLMAddFeeUI tLLUWPCLMAddFeeUI=new LLUWPCLMAddFeeUI();
//    if (!tLLUWPCLMAddFeeUI.submitData(tVData,transact))
//    {
//        Content = " 数据提交失败，原因是: " + tLLUWPCLMAddFeeUI.mErrors.getError(0).errorMessage;
//        FlagStr = "Fail";
//    }
	 String busiName="LLUWPCLMAddFeeUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	   if(!tBusinessDelegate.submitData(tVData,transact,busiName))
	   {    
	        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
	        { 
					   Content = "保存失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
					   FlagStr = "Fail";
			}
			else
			{
					   Content = "保存失败";
					   FlagStr = "Fail";				
			}
	   }

    else
    {
    	Content = " 数据提交成功! ";
	    FlagStr = "Succ";
	    //tResult = tLLUWPCLMAddFeeUI.getResult();	
	    tResult = tBusinessDelegate.getResult();    
	    txmlExport=(XmlExport)tResult.getObjectByObjectName("XmlExport",0);
	    FlagStr="Succ";
	    if(txmlExport==null)
	    {
	        FlagStr="FAIL";
	        Content="没有得到要显示的数据文件";
          loggerDebug("LLUWPCLMAddFeeSave","没有得到要显示的数据文件");
	    }
    }
if (FlagStr.equals("Succ"))
{
	/*ExeSQL tExeSQL = new ExeSQL();
	//获取临时文件名
	String strSql = "select SysVarValue from ldsysvar where Sysvar='VTSFilePath'";
	String strFilePath = tExeSQL.getOneValue(strSql);
	//String strVFFileName = strFilePath+tGI.Operator+"_" + FileQueue.getFileName()+".vts";
	String strVFFileName = strFilePath + FileQueue.getFileName() + ".vts";
	
	//获取存放临时文件的路径
	String strRealPath = application.getRealPath("/").replace('\\','/');
	loggerDebug("LLUWPCLMAddFeeSave","strRealPath="+strRealPath);
	String strVFPathName = strRealPath + strVFFileName;
	CombineVts tcombineVts = null;
	
	//合并VTS文件
	String strTemplatePath = application.getRealPath("f1print/MStemplate/") + "/";
	/*String strSql1 = "select * from ldsysvar where Sysvar='VTSFilePath'";
		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		LDSysVarSet tLDSysVarSet = tLDSysVarDB.executeQuery(strSql1);
		LDSysVarSchema tLDSysVarSchema = tLDSysVarSet.get(1);
		strFilePath = tLDSysVarSchema.getV("SysVarValue");
		strVFFileName = strFilePath + FileQueue.getFileName() + ".vts";*/
	/*tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);
	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);
	
	//把dataStream存储到磁盘文件
	loggerDebug("LLUWPCLMAddFeeSave","存储文件到"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("LLUWPCLMAddFeeSave","==> Write VTS file to disk ");
	loggerDebug("LLUWPCLMAddFeeSave","===strVFFileName : "+strVFFileName);
	session.putValue("RealPath", strVFPathName);
		
	//本来打算采用get方式来传递文件路径
//	response.sendRedirect("../f1print/GetF1PrintJ1.jsp");
    //request.getRequestDispatcher("GetF1Print.jsp").forward(request,response);
	request.getRequestDispatcher("../uw/GetF1Print.jsp").forward(request,response);*/
	
	//session.putValue("PrintNo",PrtSeq );
	//session.putValue("MissionID",tMissionID );
	//session.putValue("SubMissionID",tSubMissionID );
	//session.putValue("Code",tNoticeType );	//核保通知书类别
	//session.putValue("PrtNo",tPrtNo );
	//session.putValue("ContNo",tContNo );	
	session.putValue("PrintStream", txmlExport.getInputStream());
	loggerDebug("LLUWPCLMAddFeeSave","put session value");
   	loggerDebug("LLUWPCLMAddFeeSave","xxxxxxxxxx");
    //response.sendRedirect("../uw/GetF1Print.jsp");
    request.getRequestDispatcher("../uw/GetF1Print.jsp").forward(request,response);
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
