<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�LDCode1Save.jsp
//�����ܣ�
//�������ڣ�2005-01-26 11:24:08
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.config.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  
<%
  //������Ϣ������У�鴦��
  //�������
  LDCode1Schema tLDCode1Schema   = new LDCode1Schema();
  OLDCode1UI tOLDCode1UI   = new OLDCode1UI();
  //�������
  CErrors tError = null;
  String tRela  = "";                
  String FlagStr = "";
  String Content = "";
  String transact = "";
  GlobalInput tG = new GlobalInput(); 
  tG=(GlobalInput)session.getValue("GI");
	
  //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
  transact = request.getParameter("fmtransact");
    tLDCode1Schema.setCodeType(request.getParameter("CodeType"));
    tLDCode1Schema.setCode(request.getParameter("Code"));
    tLDCode1Schema.setCode1(request.getParameter("Code1"));
    tLDCode1Schema.setCodeName(request.getParameter("CodeName"));
    tLDCode1Schema.setCodeAlias(request.getParameter("CodeAlias"));
    tLDCode1Schema.setComCode(request.getParameter("ComCode"));
    tLDCode1Schema.setOtherSign(request.getParameter("OtherSign"));
  try
  {
  // ׼���������� VData
  	VData tVData = new VData();
	  tVData.add(tLDCode1Schema);
  	tVData.add(tG);
    tOLDCode1UI.submitData(tVData,transact);
  }
  catch(Exception ex)
  {
    Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }
  
//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr=="")
  {
    tError = tOLDCode1UI.mErrors;
    if (!tError.needDealError())
    {                          
    	Content = " ����ɹ�! ";
    	FlagStr = "Success";
    }
    else                                                                           
    {
    	Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
  
  //��Ӹ���Ԥ����
%>                      
<%=Content%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
