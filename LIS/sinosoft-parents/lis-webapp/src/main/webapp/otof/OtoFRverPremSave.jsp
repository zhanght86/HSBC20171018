<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.otof.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.utility.*"%>

<%

	String Content = "";
	String FlagStr ="";
	GlobalInput tG = new GlobalInput(); 
	tG = (GlobalInput)session.getValue("GI");
	
  

 
  TransferData tTransferData = new TransferData();
  String bDate = request.getParameter("Bdate");
  String eDate = request.getParameter("Edate");
  String accountDate = request.getParameter("AccountDate");
  String tVouchNo = request.getParameter("VouchNo");
  Integer itemp = new Integer(tVouchNo) ;
  String tManagecom =request.getParameter("ManageCom");
  String tOpt = request.getParameter("Opt");
  tTransferData.setNameAndValue("bDate", bDate);
  tTransferData.setNameAndValue("eDate", eDate);
  tTransferData.setNameAndValue("accountDate", accountDate);
  tTransferData.setNameAndValue("itemp", itemp);
  tTransferData.setNameAndValue("Managecom", tManagecom);

	VData tVData = new VData();
	tVData.add(tG);
	tVData.add(tTransferData);  
  loggerDebug("OtoFRverPremSave","begin OtoFReverPremBL---");
  if (tOpt.equals("Prem"))
	{
    OtoFReverPremBL tOtoFReverPremBL = new OtoFReverPremBL();
		if(tOtoFReverPremBL.submitData(tVData,"Reverse"))
		{
			Content = "Ӧ�ձ���ƾ֤�����ɹ�! ";
			FlagStr = "Success";
		}
		else
		{
			Content = "Ӧ�ձ���ƾ֤����ʧ�ܣ�ԭ����:" + tOtoFReverPremBL.mErrors.getFirstError();
			FlagStr = "Fail";
		}
	}
else
	{
	  OtoFReverYComBL tOtoFReverYComBL = new OtoFReverYComBL();
		if(tOtoFReverYComBL.submitData(tVData,"Reverse"))
		{
			Content = "Ԥ��Ӷ��ƾ֤�����ɹ�! ";
			FlagStr = "Success";
		}
		else
		{
			Content = "Ԥ��Ӷ��ƾ֤����ʧ�ܣ�ԭ����:" + tOtoFReverYComBL.mErrors.getFirstError();
			FlagStr = "Fail";
		}
	
	}
%>                      

<html>
<script language="javascript">
		parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
