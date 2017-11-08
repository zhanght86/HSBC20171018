<%
/***************************************************************
 * <p>ProName��LLClaimNoticePrintInput.jsp</p>
 * <p>Title�����鱨���ӡ</p>
 * <p>Description�����鱨���ӡ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
 * @version  : 8.0
 * @date     : 2012-01-01
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>

<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tFileName="";
	String tFilePath1="";
	GlobalInput tGI = new GlobalInput();
	TransferData tTransferData=new TransferData();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "ҳ��ʧЧ,�����µ�½";
	} else {
		
		try {
			System.out.println("��ʼ");
			String tOperate = request.getParameter("Operate");
			String tInqNo =request.getParameter("InqNo");
			String tRgtNo = request.getParameter("RgtNo");
			
			tTransferData.setNameAndValue("InqNo",tInqNo);
			tTransferData.setNameAndValue("RgtNo",tRgtNo);

			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tTransferData);

			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LLClaimSurvePrintUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tContent = "����ɹ���";
				tFlagStr = "Succ";
				TransferData trd = (TransferData)tBusinessDelegate.getResult().getObjectByObjectName("TransferData", 0);
				
				tFilePath1 = (String) trd.getValueByName("FilePath");
				tFileName = (String) trd.getValueByName("FileName");				
				System.out.println("==================");
			}
		} catch (Exception ex) {
			tContent = tFlagStr + "�����쳣������ϵϵͳ��ά��Ա��";
			tFlagStr = "Fail";
		}
	}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit1("<%=tFlagStr%>", "<%=tContent%>","<%=tFilePath1%>","<%=tFileName%>");
</script>
</html>
