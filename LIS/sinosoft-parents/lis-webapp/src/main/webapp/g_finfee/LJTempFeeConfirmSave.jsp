<%
/***************************************************************
 * <p>ProName��LJTempFeeConfirmSave.js</p>
 * <p>Title���������</p>
 * <p>Description���������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ������
 * @version  : 8.0
 * @date     : 2014-06-10
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LJTempFeeSchema"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tOperate = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	String cTempFeeNo = "";
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "ҳ��ʧЧ,�����µ�½";
	} else {
		
		try {
			
			tOperate = request.getParameter("Operate");
			String tTempFeeNo = request.getParameter("TempFeeNo");
			String tConfirmConclusion = request.getParameter("ConfirmConclusion");
			String tConfirmDesc = request.getParameter("ConfirmDesc");
			
			LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
			if ("CONFIRMTEMPFEE".equals(tOperate)) {
				
				tLJTempFeeSchema.setTempFeeNo(tTempFeeNo);
				tLJTempFeeSchema.setConfirmConclusion(tConfirmConclusion);
				tLJTempFeeSchema.setConfirmDesc(tConfirmDesc);
			}
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLJTempFeeSchema);
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LJTempFeeConfirmUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				
				tFlagStr = "Succ";
				tContent = "����ɹ���";
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
