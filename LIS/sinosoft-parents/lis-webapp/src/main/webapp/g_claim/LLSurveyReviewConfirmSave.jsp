<%
/***************************************************************
 * <p>ProName��LLSurveyReviewConfirmSave.jsp</p>
 * <p>Title���������¼��</p>
 * <p>Description���������¼��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
 * @version  : 8.0
 * @date     : 2014-02-26
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LLInqConclusionSchema"%>
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

			TransferData mTransferData = new TransferData(); 
			String  tOperate = request.getParameter("fmtransact");//��ò���״̬
			System.out.println("tOperate:"+tOperate);
				//������̱� 
			LLInqConclusionSchema tLLInqConclusionSchema=new LLInqConclusionSchema();
			tLLInqConclusionSchema.setClmNo(request.getParameter("ClmNo"));//�ⰸ��
			tLLInqConclusionSchema.setBatNo(request.getParameter("InqNo"));//�������
			tLLInqConclusionSchema.setConNo(request.getParameter("ConNo"));//�������
			tLLInqConclusionSchema.setInqConclusion(request.getParameter("InqConclusion"));
			tLLInqConclusionSchema.setFiniFlag(request.getParameter("CasetypeCode"));
	//	mTransferData.setNameAndValue("FeeTypeOld",request.getParameter("FeeTypeOld"));//����������ͣ�δ�޸�ǰ��
	//		System.out.println("FeeTypeOld"+request.getParameter("FeeTypeOld"));
	// ׼���������� VData
			VData tVData = new VData();	

			tVData.add(tGI);
			tVData.add(tLLInqConclusionSchema);
			tVData.add(mTransferData);
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LLSurveyReviewConfirmUI")) {
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
