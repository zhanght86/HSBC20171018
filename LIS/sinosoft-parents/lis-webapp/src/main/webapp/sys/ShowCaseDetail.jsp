<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
/*******************************************************************************
 * Name     ：ShowcheckInfo.jsp
 * Function ：显示综合查询之审核信息查询
 * Author   :LiuYansong
 * Date     :2004-2-19
 */
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
  //输出参数
  String Content = "";
  CErrors tError = null;
  String FlagStr = "Succ";
	String RgtNo = request.getParameter("RgtNo_1");
	String ClmUWNo =request.getParameter("ClmUWNo");
  String InsuredName = request.getParameter("InsuredName");
	String PolNo = request.getParameter("PolNo");
	String LPJC = StrTool.unicodeToGBK(request.getParameter("LPJC"));

	String LPZT = "";

	//初始化接受的文件
	 LLReportSchema mLLReportSchema = new LLReportSchema();
   LLRegisterSchema mLLRegisterSchema = new LLRegisterSchema();
   LLClaimSchema mLLClaimSchema = new LLClaimSchema();
   LLClaimUWMDetailSchema mLLClaimUWMDetailSchema = new LLClaimUWMDetailSchema();
   LLSubReportSchema mLLSubReportSchema = new LLSubReportSchema();
   LLCasePolicySchema mLLCasePolicySchema = new LLCasePolicySchema();
   LJAGetClaimSchema mLJAGetClaimSchema = new LJAGetClaimSchema();
   LLClaimUnderwriteSchema mLLClaimUnderwriteSchema = new LLClaimUnderwriteSchema();

  VData tVData = new VData();
  tVData.addElement(RgtNo);
  tVData.addElement(ClmUWNo);
  tVData.addElement(InsuredName);
  tVData.addElement(PolNo);
  loggerDebug("ShowCaseDetail","2004-4-14日，2理赔进程是"+LPJC);
//  ShowCaseDetailUI mShowCaseDetailUI = new ShowCaseDetailUI();
//  if (!mShowCaseDetailUI.submitData(tVData,"QUERY"))
//  {
//    Content = " 没有符合条件的信息，原因是: " + mShowCaseDetailUI.mErrors.getError(0).errorMessage;
//    FlagStr = "Fail";
//  }
String busiName="ShowCaseDetailUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
if(!tBusinessDelegate.submitData(tVData,"QUERY",busiName))
{    
    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
    { 
		Content = "没有符合条件的信息，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
	}
	else
	{
		Content = "没有符合条件的信息";
		FlagStr = "Fail";				
	}
}

  else
  {
    tVData.clear();
   // tVData = mShowCaseDetailUI.getResult();
     tVData = tBusinessDelegate.getResult();
    mLLReportSchema.setSchema((LLReportSchema)tVData.getObjectByObjectName("LLReportSchema",0));
    mLLRegisterSchema.setSchema((LLRegisterSchema)tVData.getObjectByObjectName("LLRegisterSchema",0));
    mLLClaimSchema.setSchema((LLClaimSchema)tVData.getObjectByObjectName("LLClaimSchema",0));
    mLLClaimUWMDetailSchema.setSchema((LLClaimUWMDetailSchema)tVData.getObjectByObjectName("LLClaimUWMDetailSchema",0));
    mLLSubReportSchema.setSchema((LLSubReportSchema)tVData.getObjectByObjectName("LLSubReportSchema",0));
    mLLCasePolicySchema.setSchema((LLCasePolicySchema)tVData.getObjectByObjectName("LLCasePolicySchema",0));
    mLJAGetClaimSchema.setSchema((LJAGetClaimSchema)tVData.getObjectByObjectName("LJAGetClaimSchema",0));
    mLLClaimUnderwriteSchema.setSchema((LLClaimUnderwriteSchema)tVData.getObjectByObjectName("LLClaimUnderwriteSchema",0));
    if(mLLRegisterSchema.getRgtReason()==null)
    	mLLRegisterSchema.setRgtReason("");
    if(LPJC.trim().equals("签批退回"))
      LPZT = "签批退回";


    %>
     <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
     <script language="javascript">
      parent.fraInterface.document.all("RptNo").value = "<%=mLLReportSchema.getRptNo()%>";
      parent.fraInterface.document.all("RgtNo").value = "<%=mLLRegisterSchema.getRgtNo()%>";

      <%
        if(LPJC.trim().equals("签批退回"))
        {
          %>

          parent.fraInterface.document.all("CaseState").value = "<%=LPZT%>";
          <%
        }
        else
        {
          %>
          parent.fraInterface.document.all("CaseState").value = "<%=mLLCasePolicySchema.getGrpPolNo()%>";
          <%
        }
      %>
      parent.fraInterface.document.all("CustomerType").value = "<%=mLLCasePolicySchema.getContNo()%>";
      parent.fraInterface.document.all("CustomerName").value = "<%=mLLCasePolicySchema.getInsuredName()%>";
      parent.fraInterface.document.all("AccidentDate").value = "<%=mLLRegisterSchema.getAccidentDate()%>";
      parent.fraInterface.document.all("AccidentSite").value = "<%=mLLRegisterSchema.getAccidentSite()%>";
      parent.fraInterface.document.all("AccidentType").value = "<%=mLLSubReportSchema.getRemark()%>";
      parent.fraInterface.document.all("RptDate").value = "<%=mLLReportSchema.getMakeDate()%>";
      parent.fraInterface.document.all("RptOperatorName").value = "<%=mLLReportSchema.getRptorName()%>";
      parent.fraInterface.document.all("RgtDate").value = "<%=mLLRegisterSchema.getMakeDate()%>";
      parent.fraInterface.document.all("RgtantName").value = "<%=mLLRegisterSchema.getRgtantName()%>";
      parent.fraInterface.document.all("PDNo").value = "<%=mLJAGetClaimSchema.getGetNoticeNo()%>";
      parent.fraInterface.document.all("EndCaseDate").value = "<%=mLLClaimSchema.getEndCaseDate()%>";
      parent.fraInterface.document.all("InformDate").value = "<%=mLLClaimSchema.getModifyDate()%>";
      parent.fraInterface.document.all("InformName").value = "<%=mLLClaimSchema.getRgtNo()%>";
      parent.fraInterface.document.all("CaseGetMode").value = "<%=mLLRegisterSchema.getIDNo()%>";
      parent.fraInterface.document.all("TotalPayMoney").value = "<%=mLLClaimSchema.getRealPay()%>";
      parent.fraInterface.document.all("CaseNo").value = "<%=mLLCasePolicySchema.getCaseNo()%>";
      parent.fraInterface.document.all("InsuredNo").value = "<%=mLLCasePolicySchema.getInsuredNo()%>";
			parent.fraInterface.document.all("ClmDecision").value = "<%=mLLClaimUnderwriteSchema.getRemark()%>";
      var tAccidentReason = Conversion("<%=mLLReportSchema.getAccidentReason()%>");
      var tRgtReason = Conversion("<%=mLLRegisterSchema.getRgtReason()%>");
      parent.fraInterface.document.all("AccidentReason").value = tAccidentReason;
      parent.fraInterface.document.all("RgtReason").value = tRgtReason;
      parent.fraInterface.emptyUndefined();
    </script>
  <%
  }

loggerDebug("ShowCaseDetail","------end------");
loggerDebug("ShowCaseDetail",FlagStr);
loggerDebug("ShowCaseDetail",Content);
%>
