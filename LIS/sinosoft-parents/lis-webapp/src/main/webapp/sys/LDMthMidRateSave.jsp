<%
//�������ƣ�LDMthMidRateSave.jsp
//�����ܣ�
//�������ڣ�2009-10-13 15:27:43
//������  ��ZhanPeng���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
  //������Ϣ������У�鴦��
  //�������
  LDMthMidRateSchema tLDMthMidRateSchema   = new LDMthMidRateSchema();


  //�������
  CErrors tError = null;
  String tOperate=request.getParameter("hideOperate");
  tOperate=tOperate.trim();
  String tRela  = "";                
  String FlagStr = "Fail";
  String Content = "";

	GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");

  tLDMthMidRateSchema.setCurrCode(request.getParameter("CurrCode"));
  tLDMthMidRateSchema.setPer(request.getParameter("Per"));
  tLDMthMidRateSchema.setDestCode(request.getParameter("DestCode"));
  tLDMthMidRateSchema.setAverage(request.getParameter("Average"));
  tLDMthMidRateSchema.setValidYear(request.getParameter("ValidYear"));
  tLDMthMidRateSchema.setValidMonth(request.getParameter("ValidMonth"));

  // ׼���������� VData
  VData tVData = new VData();
  FlagStr="";
	tVData.addElement(tLDMthMidRateSchema);
	tVData.add(tG);
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  try
  {
		 if(!tBusinessDelegate.submitData(tVData, tOperate, "LDMthMidRateUI")){
			 FlagStr = "Fail";
			 Content = "����ʧ��,ԭ����:"+tBusinessDelegate.getCErrors().getFirstError();
		}	
  }
  catch(Exception ex)
  {
    Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }
  
  if (!FlagStr.equals("Fail"))
  {

	  	Content = " ����ɹ�! ";
    	FlagStr = "Succ";
  }
  
  //��Ӹ���Ԥ����

%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

