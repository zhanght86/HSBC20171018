<%
//程序名称:保全报表打印
//程序功能：
//创建日期：2003-11-17
//创建人  ：guoxiang
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%
    loggerDebug("BQXReportPrint","开始执行打印操作");
    GlobalInput tG = new GlobalInput();
    tG = (GlobalInput)session.getValue("GI");
    //报表种类
    String tReportType = request.getParameter("ReportName");
    loggerDebug("BQXReportPrint","报表类型代码:"+tReportType);
 

    //保全项目

    XmlExport txmlExport = new XmlExport();
    
    BQGrpReportBL tBQGrpReportBL = new BQGrpReportBL();
    tBQGrpReportBL.setGlobalInput(tG);
    
    if("GrpSingleInfo".equals(tReportType)){
    	//团单下个单信息打印
    	String tGrpContNo = request.getParameter("GSGrpContNo");
    	String tAppFlag = request.getParameter("GSAppFlag");
    	String tStartDate = request.getParameter("GSStartCValidate");
    	String tEndDate = request.getParameter("GSEndCValidate");
    	if(!tBQGrpReportBL.printGrpSingleInfo(tGrpContNo,tAppFlag,tStartDate,tEndDate)){

    	}
    }else if("GrpInfo".equals(tReportType)){
    	//团单下个单信息打印
    	String tGrpContNo = request.getParameter("GIGrpContNo");
    	String tAppFlag = request.getParameter("GIAppFlag");
    	String tRiskCode = request.getParameter("GIRiskCode");
    	String tManagecom = request.getParameter("GIManageCom");
    	String tStartDate = request.getParameter("GIStartCValidate");
    	String tEndDate = request.getParameter("GIEndCValidate");
    	if(!tBQGrpReportBL.printGrpInfo(tGrpContNo,tAppFlag,tRiskCode,tManagecom,tStartDate,tEndDate)){

    	}
    }else if("GrpSingleAcc".equals(tReportType)){
    	//团单下个单信息打印
    	String tGrpContNo = request.getParameter("GSAGrpContNo");
    	String tContNo = request.getParameter("GSAContNo");
    	String tStartDate = request.getParameter("GSAStartPayDate");
    	String tEndDate = request.getParameter("GSAEndPayDate");
    	if(!tBQGrpReportBL.printGrpSingleAcc(tGrpContNo,tContNo,tStartDate,tEndDate)){

    	}
    }else if("GrpLoanInfo".equals(tReportType)){
    	//团单借款信息打印
    	String tManageCom = request.getParameter("GLManageCom");
    	String tPayOffFlag = request.getParameter("GLPayOffFlag");
    	String tStartDate = request.getParameter("GLStartLoanDate");
    	String tEndDate = request.getParameter("GLEndLoanDate");
    	if(!tBQGrpReportBL.printGrpLoanInfo(tManageCom,tPayOffFlag,tStartDate,tEndDate)){

    	}
    }else if("GrpEdorInfo".equals(tReportType)){
    	//团单借款信息打印
    	String tManageCom = request.getParameter("GEManageCom");
    	String tEdorType = request.getParameter("GEEdorType");
    	String tGrpContNo = request.getParameter("GEGrpContNo");
    	String tOperator = request.getParameter("GEdorOperator");
    	String tStartDate = request.getParameter("GEdorConfStartDate");
    	String tEndDate = request.getParameter("GEdorConfEndDate");
    	if(!tBQGrpReportBL.printGrpEdorInfo(tGrpContNo,tOperator,tEdorType,tManageCom,tStartDate,tEndDate)){

    	}
    }else if("GrpEdorUser".equals(tReportType)){
    	//团单借款信息打印
    	String tEdorPopedom = request.getParameter("GEdorPopedom");
    	String tManageCom = request.getParameter("GEUManageCom");
    	String tUserCode = request.getParameter("GEdorUserCode");
    	String tUserName = request.getParameter("GEdorUserName");
    	String tUserState = request.getParameter("GEUserState");
    	String tStartDate = request.getParameter("GEdorUserStartDate");
    	String tEndDate = request.getParameter("GEdorUserEndDate");
    	if(!tBQGrpReportBL.printGrpEdorUserInfo(tEdorPopedom,tManageCom,tUserCode,tUserName,tUserState,tStartDate,tEndDate)){

    	}
    }
    txmlExport = tBQGrpReportBL.getXmlExport();
    
    if (txmlExport==null){
       loggerDebug("BQXReportPrint","null没有正确的打印数据！！！");
    }else{
       session.putValue("PrintStream", txmlExport.getInputStream());
       loggerDebug("BQXReportPrint","put session value");
       response.sendRedirect("../f1print/GetF1Print.jsp");
    }

%> 
<html>
  <script language="javascript">
    top.opener.focus();
    top.close();
   </script>
</html>

