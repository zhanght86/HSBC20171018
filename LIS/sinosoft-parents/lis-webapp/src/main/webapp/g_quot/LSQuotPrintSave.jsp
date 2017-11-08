<%
/***************************************************************
 * <p>ProName��LSQuotPrintSave.jsp</p>
 * <p>Title�����۵���ӡ</p>
 * <p>Description�����۵���ӡ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-05-04
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.lis.schema.LSQuotOfferListSchema"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tOfferListNo = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "ҳ��ʧЧ,�����µ�½";
	} else {
		
		try {
			
			String tOperate = request.getParameter("Operate");
			String tQuotNo = request.getParameter("QuotNo");
			String tQuotBatNo = request.getParameter("QuotBatNo");
			String tQuotType = request.getParameter("QuotType");
			String tProdType = request.getParameter("ProdType");
			String tPreCustomerNo = request.getParameter("PreCustomerNo");
			String tPreCustomerName = request.getParameter("PreCustomerName");
			
			LSQuotOfferListSchema tLSQuotOfferListSchema = new LSQuotOfferListSchema();
			
			tLSQuotOfferListSchema.setQuotNo(tQuotNo);
			tLSQuotOfferListSchema.setQuotBatNo(tQuotBatNo);
			tLSQuotOfferListSchema.setPreCustomerNo(tPreCustomerNo);
			tLSQuotOfferListSchema.setPreCustomerName(tPreCustomerName);
			
			TransferData nTransferData = new TransferData();
			nTransferData.setNameAndValue("QuotType", tQuotType);
			nTransferData.setNameAndValue("ProdType", tProdType);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(nTransferData);
			tVData.add(tLSQuotOfferListSchema);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LSQuotPrintUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tFlagStr = "Succ";
				tContent = "����ɹ���";
				
				TransferData tTransferData = (TransferData)tBusinessDelegate.getResult().getObjectByObjectName("TransferData", 0);
				tOfferListNo =(String)tTransferData.getValueByName("OfferListNo");
			}
		} catch (Exception ex) {
			tContent = tFlagStr + "�����쳣������ϵϵͳ��ά��Ա��";
			tFlagStr = "Fail";
		}
	}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>", "<%=tOfferListNo%>");
</script>
</html>
