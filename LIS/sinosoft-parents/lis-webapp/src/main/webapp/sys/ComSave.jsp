<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�ComInput.jsp
//�����ܣ�
//�������ڣ�2002-08-16 17:44:40
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.sys.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
//������Ϣ������У�鴦��
//�������
LDComSchema tLDComSchema   = new LDComSchema();

//ComUI tComUI = new ComUI();
String busiName="ComUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

//�������
CErrors tError = null;

String tRela  = "";                
String FlagStr = "";
String Content = "";
String transact = "";

transact = request.getParameter("fmtransact");
loggerDebug("ComSave","------transact:"+transact);
tLDComSchema.setComCode(request.getParameter("ComCode"));
tLDComSchema.setOutComCode(request.getParameter("OutComCode"));
tLDComSchema.setName(request.getParameter("Name"));
tLDComSchema.setShortName(request.getParameter("ShortName"));
tLDComSchema.setComGrade(request.getParameter("ComGrade"));
tLDComSchema.setUpComCode(request.getParameter("UpComCode"));
//tLDComSchema.setComAreaType(request.getParameter("ComAreaType"));
tLDComSchema.setComCitySize(request.getParameter("ComCitySize"));
tLDComSchema.setAddress(request.getParameter("Address"));
tLDComSchema.setZipCode(request.getParameter("ZipCode"));
tLDComSchema.setPhone(request.getParameter("Phone"));
tLDComSchema.setFax(request.getParameter("Fax"));
tLDComSchema.setEMail(request.getParameter("EMail"));
tLDComSchema.setWebAddress(request.getParameter("WebAddress"));
tLDComSchema.setSatrapName(request.getParameter("SatrapName"));
tLDComSchema.setSign(request.getParameter("Sign"));
tLDComSchema.setIsDirUnder(request.getParameter("IsDirUnder"));

try {
	// ׼���������� VData
	VData tVData = new VData();
	tVData.addElement(tLDComSchema);
	//tComUI.submitData(tVData,transact);
	if(!tBusinessDelegate.submitData(tVData,transact,busiName))
	{    
	    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
	    { 
			Content = "����ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		else
		{
			Content = "����ʧ��";
			FlagStr = "Fail";				
		}
	}
	else{
		Content = "����ɹ�";
		FlagStr = "Success";	
	}
	
}
catch(Exception ex) {
	Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
	FlagStr = "Fail";
}
  
//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
if (FlagStr=="") {
	//tError = tComUI.mErrors;
	tError = tBusinessDelegate.getCErrors();
	if (!tError.needDealError()) {                          
		Content = " ����ɹ�! ";
		FlagStr = "Success";
	}
	else {
		Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
		FlagStr = "Fail";
	}
}
%>                      
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
