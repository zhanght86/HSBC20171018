<%
/***************************************************************
 * <p>PreName��LLClaimSurveyAllotSave.jsp</p>
 * <p>Title�������������</p>
 * <p>Description�������������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
 * @version  : 8.0
 * @date     : 2014-02-21
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

			tLLInqApplySchema.setClmNo(request.getParameter("RgtNo"));//�ⰸ��
			tLLInqApplySchema.setInqNo(request.getParameter("InqNo"));//�������
			tLLInqApplySchema.setInqMngCom(request.getParameter("InqDept"));//���������� Ĭ���������
			tLLInqApplySchema.setInqPer(request.getParameter("InqPer"));//���������
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLLInqApplySchema);
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LLClaimSurveyAllotUI")) {
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
