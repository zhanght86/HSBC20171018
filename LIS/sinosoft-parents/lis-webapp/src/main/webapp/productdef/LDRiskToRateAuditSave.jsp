<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>

<%
//程序名称：LDRiskToRateTableSave.jsp
//创建日期：2012-09-19 15:13:22
//创建人  ：Guxin
//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.ibrms.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
 //接收信息，并作校验处理。
 //输入参数
 String FlagStr = "";
 String Content = "";
 
 String TableName = request.getParameter("TableName");
 String AuditConclusion = request.getParameter("AuditConclusion");
 String RiskCode = request.getParameter("RiskCode");
 String RiskName = request.getParameter("RiskName");
 String RateType = request.getParameter("RateType");
 String operator = request.getParameter("AuditFlag");
 //AuditConclusion = new String(AuditConclusion.getBytes("ISO8859-1"),"GBK");
 //RiskName = new String(RiskName.getBytes("ISO8859-1"),"GBK");
 System.out.println("AuditConclusion:"+AuditConclusion); 
 TransferData transferData = new TransferData();
 GlobalInput tG = new GlobalInput(); 
 tG=(GlobalInput)session.getAttribute("GI");
 
 transferData.setNameAndValue("AuditConclusion",AuditConclusion);
 transferData.setNameAndValue("TableName",TableName);
 transferData.setNameAndValue("RiskCode",RiskCode);
 transferData.setNameAndValue("RiskName",RiskName);
 transferData.setNameAndValue("RateType",RateType);
 
 try
 {
	  // 准备传输数据 VData
	  VData tVData = new VData();
	  tVData.add(tG);
	  tVData.add(transferData);
	  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	  if(tBusinessDelegate.submitData(tVData, operator, "LDRiskToRateAuditBL"))
	  {
		  Content=""+"保存成功"+"";
	  }
	  else
	  {
		  FlagStr = "Fail";
		  Content = ""+"保存失败，原因是:"+"" + tBusinessDelegate.getCErrors().getLastError();
	  }
 }
 catch(Exception ex)
 {
	 FlagStr = "Fail";
	 Content = ""+"处理失败！"+"";
	 ex.printStackTrace();
 }
%>
<html>
<script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

