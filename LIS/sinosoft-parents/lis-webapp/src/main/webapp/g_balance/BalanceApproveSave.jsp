<%
/***************************************************************
 * <p>ProName��BalanceApproveSave.jsp</p>
 * <p>Title����������</p>
 * <p>Description����������</p>
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
<%@page import="com.sinosoft.lis.schema.LPBalanceAppSchema"%>
<%@page import="com.sinosoft.lis.vschema.LPBalanceAppSet"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.utility.TransferData"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tOperate = "";
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
			TransferData tTransferData = new TransferData();
			LPBalanceAppSet mLPBalanceAppSet = new LPBalanceAppSet();
			if(tOperate.equals("APPROVE")){
			
				String tApproveFlag = request.getParameter("ApproveFlag");
				String tApproveDesc = request.getParameter("ApproveDesc");
				String tGrid2 [] = request.getParameterValues("ContInfoGrid2");
				String tGrid3 [] = request.getParameterValues("ContInfoGrid3");
				LPBalanceAppSchema mLPBalanceAppSchema = new LPBalanceAppSchema();
				mLPBalanceAppSchema.setGrpContNo(tGrid3[0]);
				mLPBalanceAppSchema.setState("1");
				mLPBalanceAppSchema.setBalanceAppNo(tGrid2[0]);
				mLPBalanceAppSchema.setApproveFlag(tApproveFlag);
				mLPBalanceAppSchema.setApproveDesc(tApproveDesc);
					
				mLPBalanceAppSet.add(mLPBalanceAppSchema);
				
			}
			
			tTransferData.setNameAndValue("BalanceType", "2");
			tTransferData.setNameAndValue("LPBalanceAppSet", mLPBalanceAppSet);
			
			tVData.add(mLPBalanceAppSet);
			tVData.add(tTransferData);	
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "BalanceManualUI")) {
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
