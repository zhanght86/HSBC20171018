<%
/***************************************************************
 * <p>ProName��LSQuotPlanCombiSave.jsp</p>
 * <p>Title�������������</p>
 * <p>Description�������������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-04-02
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LSQuotPlanCombiSchema"%>
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
			String tMissionID = request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID");
			String tActivityID = request.getParameter("ActivityID");
			
			LSQuotPlanCombiSchema tLSQuotPlanCombiSchema = new LSQuotPlanCombiSchema();
			
			if ("INSERT".equals(tOperate)) {
				
				String tLimitType = request.getParameter("LimitType");//���Ʒ�Χ
				String tCombiType = request.getParameter("CombiType");//������� 
				
				//���������룬ƴ�������ϸ
				String tCombiDetail = "";
				String tChk [] = request.getParameterValues("InpPlanListGridChk"); //����е�����ֵ
				String tGrid1 [] = request.getParameterValues("PlanListGrid1"); //�õ���1�е�����ֵ
				for(int i=0;i<tChk.length;i++){
					
					if ("1".equals(tChk[i])) {
						tCombiDetail +=  tGrid1[i] + ",";
					}
				}
				if (tCombiDetail!=null && !"".equals(tCombiDetail)) {
					tCombiDetail = tCombiDetail.substring(0, tCombiDetail.length() - 1);
				}
				
				tLSQuotPlanCombiSchema.setQuotNo(tQuotNo);
				tLSQuotPlanCombiSchema.setQuotBatNo(tQuotBatNo);
				tLSQuotPlanCombiSchema.setLimitType(tLimitType);
				tLSQuotPlanCombiSchema.setCombiType(tCombiType);
				tLSQuotPlanCombiSchema.setCombiDetail(tCombiDetail);
			} else if ("DELETE".equals(tOperate)) {
			
				String tSerialNo = request.getParameter("SerialNo");//���
				tLSQuotPlanCombiSchema.setSerialNo(tSerialNo);
			}
				
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("QuotType", tQuotType);
			tTransferData.setNameAndValue("QuotNo", tQuotNo);
			tTransferData.setNameAndValue("QuotBatNo", tQuotBatNo);
			tTransferData.setNameAndValue("MissionID", tMissionID);
			tTransferData.setNameAndValue("SubMissionID", tSubMissionID);
			tTransferData.setNameAndValue("ActivityID", tActivityID);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tTransferData);
			tVData.add(tLSQuotPlanCombiSchema);
			
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LSQuotPlanCombiUI")) {
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
