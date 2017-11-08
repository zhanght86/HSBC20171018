<%
/***************************************************************
 * <p>ProName��RenewalParaSave.jsp</p>
 * <p>Title�����ڲ�������</p>
 * <p>Description�����ڲ�������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
 * @version  : 8.0
 * @date     : 2014-06-17
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.VData"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.utility.TransferData"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tOperate = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	TransferData tTransferData = new TransferData();
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "ҳ��ʧЧ,�����µ�½";
	} else {
		try {
			//��ȡ�������͵�ǰ̨����
			tOperate = request.getParameter("Operate");
			VData tVData = new VData();
			
			String tSerialNo = request.getParameter("SerialNo");
			String tPSerialNo = request.getParameter("PSerialNo");
			String tManageCom=  request.getParameter("ManageCom");
			String tPayIntv = request.getParameter("PayIntv");
			String tPumpDays = request.getParameter("PumpDays");
			String tGracePeriod = request.getParameter("GracePeriod");
			String tPPolicyNo = request.getParameter("PPolicyNo");
			String tPGracePeriod = request.getParameter("PGracePeriod");
			
			tTransferData.setNameAndValue("SerialNo",tSerialNo);
			tTransferData.setNameAndValue("PSerialNo",tPSerialNo);
			tTransferData.setNameAndValue("ManageCom",tManageCom);
			tTransferData.setNameAndValue("PayIntv",tPayIntv);
			tTransferData.setNameAndValue("PumpDays",tPumpDays);
			tTransferData.setNameAndValue("GracePeriod",tGracePeriod);
			tTransferData.setNameAndValue("PPolicyNo",tPPolicyNo);
			tTransferData.setNameAndValue("PGracePeriod",tPGracePeriod);
			
			
			tVData.add(tGI);
			tVData.add(tTransferData);
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "RenewalParaUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tFlagStr = "Succ";
				tContent = "����ɹ���";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			tContent = tFlagStr + "�����쳣������ϵϵͳ��ά��Ա��";
			tFlagStr = "Fail";
		}
	}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>");
</script>
</html>
