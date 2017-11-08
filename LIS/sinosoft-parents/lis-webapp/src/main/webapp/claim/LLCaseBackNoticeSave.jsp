<%
//文件名称：LLCaseBackNoticeSave.jsp
//文件功能：理赔回退费用通知书
//创建人：wl
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
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.service.*" %>

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
    loggerDebug("LLCaseBackNoticeSave","页面失效,请重新登陆");    
}
    String transact = request.getParameter("fmtransact"); //获取操作
	//String tPath = application.getRealPath("f1print/MStemplate") + "/";
    String tPath = request.getSession().getServletContext().getResource("/").getPath()+"f1print/MStemplate/";
    loggerDebug("LLCaseBackNoticeSave","tPath="+tPath);
	
    //String使用TransferData打包后提交-----用于传送 赔案号、回退原因、通知书类型
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("ClmNoQ", request.getParameter("ClmNo")); //赔案号
    mTransferData.setNameAndValue("BackReasonQ", request.getParameter("BackReason")); //回退原因
    mTransferData.setNameAndValue("PrtNoticeNo", request.getParameter("PrtNoticeNo")); //通知书类型
      
    VData tVData = new VData(); //准备往传输数据 VData
    VData tResult = new VData(); //接受后台的数据
     XmlExport txmlExport = new XmlExport();
    tVData.add(tGI);
    tVData.add(mTransferData);
   
//	LLCaseBackNoticeUI tLLCaseBackNoticeUI=new LLCaseBackNoticeUI();
//    if (!tLLCaseBackNoticeUI.submitData(tVData,transact))
//    {
//        Content = " 数据提交失败，原因是: " + tLLCaseBackNoticeUI.mErrors.getError(0).errorMessage;
//        FlagStr = "Fail";
//    }
	String busiName="LLCaseBackNoticeUI";
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
	    //tResult = tLLCaseBackNoticeUI.getResult();
	    tResult = tBusinessDelegate.getResult();	    
	    txmlExport=(XmlExport)tResult.getObjectByObjectName("XmlExport",0);
	    FlagStr="Succ";
	    if(txmlExport==null)
	    {
	        FlagStr="FAIL";
	        Content="没有得到要显示的数据文件";
            loggerDebug("LLCaseBackNoticeSave","没有得到要显示的数据文件");
	    }
    }
	if (FlagStr.equals("Succ"))
	{
		ExeSQL tExeSQL = new ExeSQL();
		//获取临时文件名
		/*String strSql = "select SysVarValue from ldsysvar where Sysvar='VTSFilePath'";
		String strFilePath = tExeSQL.getOneValue(strSql);
		String strVFFileName = strFilePath+tGI.Operator+"_" + FileQueue.getFileName()+".vts";*/
		String strVFFileName="";
    	VData vtsVData=new VData();
    	vtsVData.add( tGI );
    	BusinessDelegate tvtsBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    	  if(tvtsBusinessDelegate.submitData(vtsVData,"","BqFileNameUI"))
    	  {
    		  strVFFileName = (String)tvtsBusinessDelegate.getResult().get(0);
    	  }
		//获取存放临时文件的路径
		String strRealPath = application.getRealPath("/").replace('\\','/');
		//String strRealPath = request.getSession().getServletContext().getResource("/").getPath().replace('\\','/');
		loggerDebug("LLCaseBackNoticeSave","strRealPath="+strRealPath);
		String strVFPathName = strRealPath + strVFFileName;
		CombineVts tcombineVts = null;
		//合并VTS文件
		String strTemplatePath = application.getRealPath("f1print/MStemplate/") + "/";
		//String strTemplatePath = request.getSession().getServletContext().getResource("/").getPath()+"f1print/MStemplate/";
		tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);
		ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		tcombineVts.output(dataStream);
		//把dataStream存储到磁盘文件
		loggerDebug("LLCaseBackNoticeSave","存储文件到"+strVFPathName);
		AccessVtsFile.saveToFile(dataStream,strVFPathName);
		loggerDebug("LLCaseBackNoticeSave","==> Write VTS file to disk ");
		loggerDebug("LLCaseBackNoticeSave","===strVFFileName : "+strVFFileName);
		session.putValue("RealPath", strVFPathName);
		//本来打算采用get方式来传递文件路径
        request.getRequestDispatcher("GetF1PrintJ1.jsp").forward(request,response);
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
