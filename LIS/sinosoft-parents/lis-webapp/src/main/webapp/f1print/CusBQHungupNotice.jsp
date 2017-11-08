<%@include file="../common/jsp/UsrCheck.jsp"%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.bq.*"%>
<%
	loggerDebug("CusBQHungupNotice","start");
	String tManageCom="";
	String tComType="";
	String tComCode="";
 
	String tStartDate="";
	String tEndDate="";
 
	String tPolState="";
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
	CError cError = new CError( );
	//后面要执行的动作：添加，修改，删除	  
	String FlagStr = "";
	String Content = "";
	
    LPEdorAppSchema tLPEdorAppSchema=new LPEdorAppSchema(); 
    tLPEdorAppSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo")); //保全受理号
    tLPEdorAppSchema.setOtherNo(request.getParameter("OtherNo_Read")); //申请号码
    tLPEdorAppSchema.setOtherNoType(request.getParameter("OtherNoType_Read")); //申请号码类型
    tLPEdorAppSchema.setEdorAppName(request.getParameter("CMCustomerName")); //借用字段存储客户名称
    tLPEdorAppSchema.setUWOperator(request.getParameter("CMCustomerNo")); //借用字段存储客户号
    tLPEdorAppSchema.setAppType(request.getParameter("AppType")); //申请方式    
	tLPEdorAppSchema.setEdorAppDate(request.getParameter("EdorAppDate")); //批改申请日期
	
	VData tVData = new VData();
	VData mResult = new VData();
	CErrors mErrors = new CErrors();
	tVData.addElement(tLPEdorAppSchema);
    tVData.addElement(tG);
  	CusBQHungupPrint tCusBQHungupPrint = new CusBQHungupPrint();
  	try{
  	
		if(!tCusBQHungupPrint.submitData(tVData,""))
		{
			loggerDebug("CusBQHungupNotice","@@@@@@@@@@@@@$$$$$$$$$$");
		        mErrors.copyAllErrors(tCusBQHungupPrint.mCErrors);
		      	cError.moduleName = "ReAgentXQMoneyRatePrt.jsp";
		        cError.functionName = "submitData";
		        cError.errorMessage = "tRePlanPremRateUI";
		        mErrors.addOneError(cError);          
		}
		mResult = tCusBQHungupPrint.getResult();
		XmlExport txmlExport = new XmlExport();
		txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
		if (txmlExport==null)
		{
			loggerDebug("CusBQHungupNotice","null");
		}
		session.putValue("PrintStream", txmlExport.getInputStream());
		loggerDebug("CusBQHungupNotice","put session value");
		response.sendRedirect("../f1print/GetF1Print.jsp");
	}
	catch(Exception ex)
	{
		Content = "失败，原因是:" + ex.toString();
    		FlagStr = "Fail";
%>
<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<script language="javascript">	
	alert("<%=Content%>");
	top.close();	
	//window.opener.afterSubmit("<%=FlagStr%>","<%=Content%>");		
</script>
</html>
<%
  	}
%>
