<%
/***************************************************************
 * <p>ProName��LSQuotFeeSave.jsp</p>
 * <p>Title��������Ϣ</p>
 * <p>Description��������Ϣ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-18
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LSQuotOtherFeeSchema"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "ҳ��ʧЧ,�����µ�½";
	} else {
		
		try {
			
			String tOperate = request.getParameter("Operate");
			
			String tQuotType = request.getParameter("QuotType");
			String tQuotNo = request.getParameter("QuotNo");
			String tQuotBatNo = request.getParameter("QuotBatNo");
			String tActivityID = request.getParameter("ActivityID");
			
			String tSerialNo = request.getParameter("SerialNo");
			String tFeeType = request.getParameter("FeeType");
			String tOtherFeeDesc = request.getParameter("OtherFeeDesc");
			String tFeeValue = request.getParameter("FeeValue");
			String tRemark = request.getParameter("Remark");
			
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("QuotType", tQuotType);
			tTransferData.setNameAndValue("QuotNo", tQuotNo);
			tTransferData.setNameAndValue("QuotBatNo", tQuotBatNo);
			tTransferData.setNameAndValue("ActivityID", tActivityID);
			
			LSQuotOtherFeeSchema tLSQuotOtherFeeSchema = new LSQuotOtherFeeSchema();
			
			tLSQuotOtherFeeSchema.setSerialNo(tSerialNo);
			tLSQuotOtherFeeSchema.setQuotNo(tQuotNo);
			tLSQuotOtherFeeSchema.setQuotBatNo(tQuotBatNo);
			tLSQuotOtherFeeSchema.setFeeType(tFeeType);
			tLSQuotOtherFeeSchema.setOtherFeeDesc(tOtherFeeDesc);
			tLSQuotOtherFeeSchema.setFeeValue(tFeeValue);
			tLSQuotOtherFeeSchema.setRemark(tRemark);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tTransferData);
			tVData.add(tLSQuotOtherFeeSchema);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LSQuotFeeUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tContent = "����ɹ���";
				tFlagStr = "Succ";
			}
		} catch (Exception ex) {
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
