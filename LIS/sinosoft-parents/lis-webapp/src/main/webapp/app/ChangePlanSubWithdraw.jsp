<%
//�������ƣ�ChangePlanSubWithdraw.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��HST
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="java.lang.reflect.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  String polNo = request.getParameter("polNo");
  String prtNo = request.getParameter("prtNo");
  String riskCode = request.getParameter("riskCode");
  loggerDebug("ChangePlanSubWithdraw","polNo:" + polNo + " prtNo:" + prtNo + " riskCode:" + riskCode);
  
  GlobalInput tG = new GlobalInput();	
	tG = ( GlobalInput )session.getValue( "GI" );
  
  LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
  tLJTempFeeSchema.setRiskCode(riskCode);
  tLJTempFeeSchema.setOtherNo(prtNo);
  //����TempFeeNo�����ݱ�����
  tLJTempFeeSchema.setTempFeeNo(polNo);

  // ׼���������� VData
  VData tVData = new VData();
	tVData.addElement(tLJTempFeeSchema);
	tVData.addElement(tG);

  // ���ݴ���
  ChangePlanSubWithdrawUI tChangePlanSubWithdrawUI   = new ChangePlanSubWithdrawUI();
	if (!tChangePlanSubWithdrawUI.submitData(tVData, "INSERT||MAIN")) {    
      Content = "��ѯʧ�ܣ�ԭ����: " + tChangePlanSubWithdrawUI.mErrors.getError(0).errorMessage;
      FlagStr = "Fail";
	}	else {
		tVData.clear();
		tVData = tChangePlanSubWithdrawUI.getResult();
		
		Content = " ��ѯ�ɹ�! ";
    FlagStr = "Succ";
	} // end of if

  
loggerDebug("ChangePlanSubWithdraw",FlagStr);
loggerDebug("ChangePlanSubWithdraw",Content);
%> 

<script>
  try { top.close(); } catch(e) {}
</script>
