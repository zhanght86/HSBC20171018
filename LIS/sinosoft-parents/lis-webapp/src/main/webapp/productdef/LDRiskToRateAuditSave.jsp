<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>

<%
//�������ƣ�LDRiskToRateTableSave.jsp
//�������ڣ�2012-09-19 15:13:22
//������  ��Guxin
//���¼�¼��  ������    ��������     ����ԭ��/����
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
 //������Ϣ������У�鴦��
 //�������
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
	  // ׼���������� VData
	  VData tVData = new VData();
	  tVData.add(tG);
	  tVData.add(transferData);
	  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	  if(tBusinessDelegate.submitData(tVData, operator, "LDRiskToRateAuditBL"))
	  {
		  Content=""+"����ɹ�"+"";
	  }
	  else
	  {
		  FlagStr = "Fail";
		  Content = ""+"����ʧ�ܣ�ԭ����:"+"" + tBusinessDelegate.getCErrors().getLastError();
	  }
 }
 catch(Exception ex)
 {
	 FlagStr = "Fail";
	 Content = ""+"����ʧ�ܣ�"+"";
	 ex.printStackTrace();
 }
%>
<html>
<script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

