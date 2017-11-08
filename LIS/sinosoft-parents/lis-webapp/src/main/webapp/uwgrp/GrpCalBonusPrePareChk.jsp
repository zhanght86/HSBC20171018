<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�CutBonusChk.jsp
//�����ܣ��ֺ촦��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
  
<!--�û�У����-->
<%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
  
  String busiName="cbcheckgrpBonusGrpPolParmSaveUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  CErrors tError = null;
  String strFlag = "Fail";
  String strContent = "";
  
try
{
  GlobalInput tG = (GlobalInput)session.getValue("GI");
	if(tG == null) 
	{
		out.println("session has expired");
		return;
	}
	
	String tFiscalYear = request.getParameter("FiscalYear");
	String tGrpContNo = request.getParameter("GrpContNo");
	String tBDate = request.getParameter("BDate");
	String tEDate = request.getParameter("EDate");
	String tActuRate = request.getParameter("ActuRate");
	String tEnsuRateDefault = request.getParameter("EnsuRateDefault");
	String tAssignRate = request.getParameter("AssignRate");
	String tRiskCode = request.getParameter("RiskCode");
	
	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("FiscalYear",tFiscalYear);
	tTransferData.setNameAndValue("GrpContNo",tGrpContNo);
	tTransferData.setNameAndValue("RiskCode",tRiskCode);
	tTransferData.setNameAndValue("BDate",tBDate);
	tTransferData.setNameAndValue("EDate",tEDate);
	tTransferData.setNameAndValue("ActuRate",tActuRate);
	tTransferData.setNameAndValue("EnsuRateDefault",tEnsuRateDefault);
	tTransferData.setNameAndValue("AssignRate",tAssignRate);
	
	String action = request.getParameter("fmtransact");

	// ׼���������� VData
	VData tVData = new VData();
	tVData.add( tG );
	tVData.add( tTransferData );

	// ���ݴ���
	
	if(tBusinessDelegate.submitData(tVData,action,busiName))
	{
  	strFlag = "Succ";
  	strContent = "�����������óɹ�! ";
	}
	else
		strFlag = "Fail";

	//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
	if (strFlag == "Fail")
	{
	    tError = tBusinessDelegate.getCErrors();
	    if (!tError.needDealError())
	    {                          
		    	strContent = "�����������óɹ�! ";
		    	strFlag = "Succ";
	    }
	    else                                                                           
	    {
  			if (tError.getErrorCount() > 0)
  			{	
	          strContent = "�������������쳣 �� " + tError.getError(0).errorMessage.trim() ;
						loggerDebug("GrpCalBonusPrePareChk","Error: " + strContent);
				}
	    	strFlag = "Fail";
	    }
	}
}
catch(Exception e)
{
	e.printStackTrace();
	strContent = strContent.trim() +" �쳣�˳� : " + e.toString();
}
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=strFlag%>","<%=strContent%>");
</script>
</html>
