<%
//�������ƣ�LDExOtherRateSave.jsp
//�����ܣ�
//�������ڣ�2009-10-13 9:42:43
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
  LDExOtherRateSchema tLDExOtherRateSchema   = new LDExOtherRateSchema();


  //�������
  CErrors tError = null;
  String tOperate=request.getParameter("hideOperate");
  tOperate=tOperate.trim();
  String tRela  = "";                
  String FlagStr = "Fail";
  String Content = "";

	GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");

  tLDExOtherRateSchema.setCurrCode(request.getParameter("CurrCode"));
  tLDExOtherRateSchema.setPer(request.getParameter("Per"));
  tLDExOtherRateSchema.setDestCurrCode(request.getParameter("DestCurrCode"));
  tLDExOtherRateSchema.setExchRate(request.getParameter("ExchRate"));
  tLDExOtherRateSchema.setStartDate(request.getParameter("StartDate"));
  tLDExOtherRateSchema.setEndDate(request.getParameter("EndDate"));


  // ׼���������� VData
  VData tVData = new VData();
  FlagStr="";
	tVData.addElement(tLDExOtherRateSchema);
	tVData.add(tG);
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  try
  {
	 if(!tBusinessDelegate.submitData(tVData, tOperate, "LDExOtherRateUI")){
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

