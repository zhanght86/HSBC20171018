<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.tbgrp.*"%>
<%
		loggerDebug("GrpInterestSave","start calGrpInterestSave.jsp ... ");

		String FlagStr = "";
		String Content = "";
		String tFiscalYear = request.getParameter("FiscalYear");
		
		GlobalInput tG = new GlobalInput();
		tG = (GlobalInput)session.getValue("GI");

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("FiscalYear",tFiscalYear);
		tTransferData.setNameAndValue("GrpContNo",request.getParameter("GrpContNo"));
		tTransferData.setNameAndValue("CvalidateB",request.getParameter("BDate"));
		tTransferData.setNameAndValue("CvalidateE",request.getParameter("EDate"));
		
		VData tVData = new VData();
		tVData.addElement("1");                    //�˹�ִ��
		tVData.addElement(tG);
		tVData.addElement(tTransferData); 
		
		GrpInterestBL tGrpInterestBL = new GrpInterestBL();
		tGrpInterestBL.submitData(tVData,"");
		if(tGrpInterestBL.mErrors.needDealError() == true)
		{
				FlagStr = "Fail";
				Content = "��Ϣʧ�ܣ���δ��Ϣ��¼����鿴������־��";
		}
		else
		{   
				FlagStr = "Success";
				Content = "�ŵ��� " + tFiscalYear + "������ĩ��Ϣ������ɣ�";	
		}
		loggerDebug("GrpInterestSave","---end calGrpInterestSave.jsp --- ");
%>
<html>
<script language="javascript">	
		parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");	
</script>
</html>
