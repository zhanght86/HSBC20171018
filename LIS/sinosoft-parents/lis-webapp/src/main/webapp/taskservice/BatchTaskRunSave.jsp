<%@include file="/i18n/language.jsp"%>
<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.sinosoft.lis.taskservice.BatchTaskRunBL"%>
<%@ include file="../common/jsp/UsrCheck.jsp"%>
<%@ page import="com.sinosoft.lis.bq.*"%>
<%@ page import="com.sinosoft.lis.operfee.*"%>
<%@ page import="com.sinosoft.lis.pubfun.*"%>
<%@ page import="com.sinosoft.lis.schema.*"%>
<%@ page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.*" %>  

<%
	//输出参数
	String FlagStr = "";  
	String Content = ""; 
	CErrors tErrors = new CErrors();;

	GlobalInput tG = new GlobalInput(); 
	tG = (GlobalInput) session.getValue("GI");

	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("TaskCode", request.getParameter("tTaskCode"));
	tTransferData.setNameAndValue("ExeDate", request.getParameter("tExeDate"));
	
	VData tVData = new VData();
	tVData.add(tG);		
	tVData.add(tTransferData);

    //调用后台处理
    BatchTaskRunBL tBatchTaskRunBL = new BatchTaskRunBL();
    if (!tBatchTaskRunBL.submitData(tVData, ""))
    {
        tErrors.copyAllErrors(tBatchTaskRunBL.mErrors);
    }

    //页面反馈信息
    try {
    	 if (!tErrors.needDealError())
    	    {
    	        FlagStr = "Success";
    	        Content = "批处理执行完成！";
    	    }
    	    else
    	    {
    	        FlagStr = "Fail";
    	        Content = tErrors.getFirstError();
    	    }
    } catch(Exception e) {
    	e.printStackTrace();
    }
%>

<html>
 <script language="JavaScript">
   try
	{
    	parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
	}
   catch (ex)
    {
    	myAlert('<%=Content%>');
    }
 </script>
</html>
