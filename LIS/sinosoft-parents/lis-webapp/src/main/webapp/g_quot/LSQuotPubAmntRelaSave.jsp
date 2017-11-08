<%
/***************************************************************
 * <p>ProName��LSQuotPubAmntRelaSave.jsp</p>
 * <p>Title����������ʹ�ù���</p>
 * <p>Description����������ʹ�ù���</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-05-08
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LSQuotPubAmntRelaSchema"%>
<%@page import="com.sinosoft.lis.schema.LSQuotPubAmntRelaSubSchema"%>
<%@page import="com.sinosoft.lis.vschema.LSQuotPubAmntRelaSubSet"%>
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
			
			String tQuotNo = request.getParameter("QuotNo");
			String tQuotBatNo = request.getParameter("QuotBatNo");
			String tSysPlanCode = request.getParameter("SysPlanCode");
			String tPlanCode = request.getParameter("PlanCode");
			String tLimitAmnt = request.getParameter("LimitAmnt");
			
			LSQuotPubAmntRelaSchema tLSQuotPubAmntRelaSchema = new LSQuotPubAmntRelaSchema();
			LSQuotPubAmntRelaSubSet tLSQuotPubAmntRelaSubSet = new LSQuotPubAmntRelaSubSet();
			
			//ѯ�۹��������������
			tLSQuotPubAmntRelaSchema.setQuotNo(tQuotNo);
			tLSQuotPubAmntRelaSchema.setQuotBatNo(tQuotBatNo);
			tLSQuotPubAmntRelaSchema.setSysPlanCode(tSysPlanCode);
			tLSQuotPubAmntRelaSchema.setPlanCode(tPlanCode);
			
			if ("SAVEPUBAMNT".equals(tOperate)) {
				
				if (tLimitAmnt==null || "".equals(tLimitAmnt)) {//modify by songsz 20140714 �Ըô��������⴦��,Ϊ�վʹ�-1
					tLimitAmnt = "-1";
				}
				
				tLSQuotPubAmntRelaSchema.setLimitAmnt(tLimitAmnt);
				
				//ѯ�۹�����������ӱ�
				String tChk [] = request.getParameterValues("PubAmntRelaDutyGridNo"); //����е�����ֵ
				String tRiskCode [] = request.getParameterValues("PubAmntRelaDutyGrid1"); 
				String tDutyCode [] = request.getParameterValues("PubAmntRelaDutyGrid3"); 
				String tPubFlag [] = request.getParameterValues("PubAmntRelaDutyGrid5"); 
				String tDutyLimitAmnt [] = request.getParameterValues("PubAmntRelaDutyGrid7"); 
				for(int index=0;index<tChk.length;index++){
				
					LSQuotPubAmntRelaSubSchema tLSQuotPubAmntRelaSubSchema = new LSQuotPubAmntRelaSubSchema();
					
					tLSQuotPubAmntRelaSubSchema.setQuotNo(tQuotNo);
					tLSQuotPubAmntRelaSubSchema.setQuotBatNo(tQuotBatNo);
					tLSQuotPubAmntRelaSubSchema.setSysPlanCode(tSysPlanCode);
					tLSQuotPubAmntRelaSubSchema.setPlanCode(tPlanCode);
					tLSQuotPubAmntRelaSubSchema.setRiskCode(tRiskCode[index]);
					tLSQuotPubAmntRelaSubSchema.setDutyCode(tDutyCode[index]);
					tLSQuotPubAmntRelaSubSchema.setPubFlag(tPubFlag[index]);
					
					if ("0".equals(tPubFlag[index])) {
						tLSQuotPubAmntRelaSubSchema.setDutyLimitAmnt("-1");
					} else {
						tLSQuotPubAmntRelaSubSchema.setDutyLimitAmnt(tDutyLimitAmnt[index]);
					}
										
					tLSQuotPubAmntRelaSubSet.add(tLSQuotPubAmntRelaSubSchema);
			
				}
			} 
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLSQuotPubAmntRelaSchema);
			tVData.add(tLSQuotPubAmntRelaSubSet);
			
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LSQuotPubAmntRelaUI")) {
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
