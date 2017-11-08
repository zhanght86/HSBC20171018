<%
/***************************************************************
 * <p>ProName��PolicyRateSave.jsp</p>
 * <p>Title����������ά��</p>
 * <p>Description����������ά��</p>
 * <p>Copyright��Copyright (c) 2013</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���θ�
 * @version  : 8.0
 * @date     : 2014-07-01
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LDPolicyInsuAccRateSchema"%>
<%@page import="com.sinosoft.lis.vschema.LDPolicyInsuAccRateSet"%>
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
			String tGrpContNo = request.getParameter("GrpContNo");
			
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("PolicyNo", tGrpContNo);
			
			String tGridNo[] = request.getParameterValues("PolicyRateGridNo");
			String tRiskCode[] = request.getParameterValues("PolicyRateGrid1");
			String tInsuAccNo[] = request.getParameterValues("PolicyRateGrid3");
			String tRateIntv[] = request.getParameterValues("PolicyRateGrid5");
			String tRate[] = request.getParameterValues("PolicyRateGrid7");
			
			LDPolicyInsuAccRateSet tLDPolicyInsuAccRateSet = new LDPolicyInsuAccRateSet();
			if (tGridNo!=null && tGridNo.length>0) {
				
				for (int i=0;i<tGridNo.length;i++) {
					
					LDPolicyInsuAccRateSchema tLDPolicyInsuAccRateSchema = new LDPolicyInsuAccRateSchema();
					tLDPolicyInsuAccRateSchema.setPolicyNo(tGrpContNo);
					tLDPolicyInsuAccRateSchema.setRiskCode(tRiskCode[i]);
					tLDPolicyInsuAccRateSchema.setInsuAccNo(tInsuAccNo[i]);
					tLDPolicyInsuAccRateSchema.setRateIntv(tRateIntv[i]);
					tLDPolicyInsuAccRateSchema.setRate(tRate[i]);
					tLDPolicyInsuAccRateSet.add(tLDPolicyInsuAccRateSchema);
				}
			}
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tTransferData);
			tVData.add(tLDPolicyInsuAccRateSet);
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "InsuAccRateUI")) {
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
