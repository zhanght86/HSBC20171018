<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�LDCodeSave.jsp
//�����ܣ�
//�������ڣ�2005-01-26 13:18:17
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
  LDCodeSchema tLDCodeSchema   = new LDCodeSchema();
  OLDCodeUI tOLDCodeUI   = new OLDCodeUI();
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
    tLDCodeSchema.setCodeType(request.getParameter("CodeType"));
    tLDCodeSchema.setCode(request.getParameter("Code"));
    tLDCodeSchema.setCodeName(request.getParameter("CodeName"));
    tLDCodeSchema.setCodeAlias(request.getParameter("CodeAlias"));
    tLDCodeSchema.setComCode(request.getParameter("ComCode"));
    tLDCodeSchema.setOtherSign(request.getParameter("OtherSign"));
  try
  {
  // ׼���������� VData
  	VData tVData = new VData();
	tVData.add(tLDCodeSchema);
  	tVData.add(tG);
    tOLDCodeUI.submitData(tVData,transact);
  }
  catch(Exception ex)
  {
    Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }
  
//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr=="")
  {
    tError = tOLDCodeUI.mErrors;
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
