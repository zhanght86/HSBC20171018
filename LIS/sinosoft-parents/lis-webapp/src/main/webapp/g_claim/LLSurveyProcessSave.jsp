<%
/***************************************************************
 * <p>ProName��LLClaimSurveyInput.jsp</p>
 * <p>Title������¼��</p>
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
<%@page import="com.sinosoft.lis.schema.LLInqCourseSchema"%>
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
			String  tOperate = request.getParameter("fmtransact");//��ò���״̬
				//������̱� 
			LLInqCourseSchema tLLInqCourseSchema = new LLInqCourseSchema(); 
			tLLInqCourseSchema.setClmNo(request.getParameter("ClmNo"));//�ⰸ��
			tLLInqCourseSchema.setInqNo(request.getParameter("InqNo"));//�������
			tLLInqCourseSchema.setInqDate(request.getParameter("InqDate"));//��������
			tLLInqCourseSchema.setInqMode(request.getParameter("InqMode")==null?null:request.getParameter("InqMode").trim());//���鷽ʽ
			tLLInqCourseSchema.setInqSite(request.getParameter("InqSite"));//����ص�
			tLLInqCourseSchema.setInqByPer(request.getParameter("InqByPer"));//��������
			tLLInqCourseSchema.setInqDept(request.getParameter("InqDept"));//�������
			tLLInqCourseSchema.setInqPer1(request.getParameter("InqPer1")); //��һ������
			tLLInqCourseSchema.setInqPer2(request.getParameter("InqPer2"));//�ڶ�������
			tLLInqCourseSchema.setCouNo(request.getParameter("CourseCouNo"));//����������	
			tLLInqCourseSchema.setInqCourse(request.getParameter("InqCourse"));//�������	
			System.out.println("AffixNumber:"+request.getParameter("AffixNumber"));
	// ׼���������� VData
			VData tVData = new VData();	
			tVData.add(tGI);
			tVData.add(tLLInqCourseSchema);
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LLSurveyCourseUI")) {
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
