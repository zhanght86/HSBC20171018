<%
/***************************************************************
 * <p>ProName��LLClaimPreinvestSave.jsp</p>
 * <p>Title���������</p>
 * <p>Description���������</p>
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
<%@page import="com.sinosoft.lis.schema.LLInqApplySchema"%>
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
			LLInqApplySchema tLLInqApplySchema=new LLInqApplySchema();
			String tOperate = request.getParameter("Operate");
			String tAccpetMngCom=request.getParameter("ManageCom");//�������

			tLLInqApplySchema.setGrpRgtNo(request.getParameter("GrpRgtNo"));
			tLLInqApplySchema.setClmNo(request.getParameter("RgtNo"));//�ⰸ��
			tLLInqApplySchema.setInqNo(request.getParameter("InqNo"));//�������
			tLLInqApplySchema.setApplyDate(request.getParameter("ApplyDate"));//��������
			tLLInqApplySchema.setCustomerNo(request.getParameter("CustomerNo"));//�����˿ͻ���
			tLLInqApplySchema.setCustomerName(request.getParameter("CustomerName"));//����������
			tLLInqApplySchema.setAppntNo(request.getParameter("AppntNo"));//Ͷ���˱���
			tLLInqApplySchema.setInqType(request.getParameter("SurveyType"));//��������
			tLLInqApplySchema.setInitPhase(request.getParameter("InitPhase"));//����׶�
			tLLInqApplySchema.setInqRCode(request.getParameter("InqReason"));//����ԭ��
			tLLInqApplySchema.setInqItem(request.getParameter("InqPurpose"));//����Ŀ��
			tLLInqApplySchema.setInqDesc(request.getParameter("InqPlan"));//����ƻ�
			tLLInqApplySchema.setInqDept(request.getParameter("ManageCom"));//�������
			tLLInqApplySchema.setInqMngCom(request.getParameter("ManageCom"));//���������� Ĭ���������
			tLLInqApplySchema.setInitDept(request.getParameter("ManageCom"));//���鷢�����
			tLLInqApplySchema.setLocFlag(request.getParameter("LocFlag"));//���ر�־
			tLLInqApplySchema.setBatNo(request.getParameter("BatNO"));//���κ�
			tLLInqApplySchema.setRemark(request.getParameter("Remark"));//���鱸ע
			tLLInqApplySchema.setInqFlowState(request.getParameter("InqFlowState"));//����״̬
			tLLInqApplySchema.setInitiationType(request.getParameter("InitiationType"));//����ʽ
			tLLInqApplySchema.setInqCloseReason(request.getParameter("InqCloseReason"));//�ر�ԭ��
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLLInqApplySchema);
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LLClaimPreinvestUI")) {
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
