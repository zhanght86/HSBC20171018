<%
/***************************************************************
 * <p>ProName��LLClaimHospitalSave.jsp</p>
 * <p>Title��ҽԺ��Ϣά��</p>
 * <p>Description��ҽԺ��Ϣά��</p>
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
<%@page import="com.sinosoft.lis.schema.LDHospitalSchema"%>
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
	
	try{
	
	String tOperate = request.getParameter("Operate");
	LDHospitalSchema tLDHospitalSchema=new LDHospitalSchema();
	tLDHospitalSchema.setHospitCode(request.getParameter("HospitalCode"));
	tLDHospitalSchema.setHospitalName(request.getParameter("HospitalName"));
	tLDHospitalSchema.setHosState(request.getParameter("HosState"));
	tLDHospitalSchema.setHospitalGrade(request.getParameter("HosGrade"));
	
	tLDHospitalSchema.setAddress(request.getParameter("HosAddress"));
	tLDHospitalSchema.setPhone(request.getParameter("HosPhone"));
	
	VData tVData = new VData();
	tVData.add(tGI);
	tVData.add(tLDHospitalSchema);
	BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
	if (!tBusinessDelegate.submitData(tVData, tOperate, "LLHospitalUI")) {
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
