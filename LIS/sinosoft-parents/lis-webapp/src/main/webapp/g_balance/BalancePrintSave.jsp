<%
/***************************************************************
 * <p>ProName��BalancePrintSave.jsp</p>
 * <p>Title�����㵥��ӡ</p>
 * <p>Description�����㵥��ӡ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : JingDian
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
	String tFileName="";
	String tFilePath1="";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "ҳ��ʧЧ,�����µ�½";
	} else {
		try {
			//��ȡ�������͵�ǰ̨����
			tOperate = request.getParameter("Operate");
			VData tVData = new VData();
			tVData.add(tGI);
			TransferData mTransferData = new TransferData();
			
			String mGrpContNo = request.getParameter("GrpContNo");
			String mBalanceNo = request.getParameter("BalanceNo");
			
			mTransferData.setNameAndValue("GrpContNo",mGrpContNo);
			mTransferData.setNameAndValue("BalanceNo",mBalanceNo);
			
			tVData.add(mTransferData);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "BalancePrintUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tFlagStr = "Succ";
				tContent = "����ɹ���";
				if("PRINT".equals(tOperate)){
					
					TransferData trd = (TransferData)tBusinessDelegate.getResult().getObjectByObjectName("TransferData", 0);
					tFilePath1 = (String) trd.getValueByName("FilePath");
					tFileName = (String) trd.getValueByName("FileName");
					
				}
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
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>","<%=tFilePath1%>","<%=tFileName%>");
</script>
</html>
