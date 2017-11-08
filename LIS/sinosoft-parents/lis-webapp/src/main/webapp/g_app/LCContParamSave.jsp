<%
/***************************************************************
 * <p>ProName��LCContParamSave.jsp</p>
 * <p>Title����Ʒ������Ϣά��--�����ά��</p>
 * <p>Description����Ʒ������Ϣά��--�����ά��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
 * @version  : 8.0
 * @date     : 2014-05-09
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.lis.schema.LCGrpFeeSchema"%>
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
			tOperate = request.getParameter("Operate");
			
			String tMissionID = request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID");
			String tActivityID = request.getParameter("ActivityID");
			String tBussNo = request.getParameter("BussNo");//
			String tBussType = request.getParameter("BussType");//
			String tContPlanType = request.getParameter("ContPlanType");//
			
			String tRiskCode = request.getParameter("RiskCode");//����
			String tAccType = request.getParameter("AccType");//�˻�����
			String tFeeType = request.getParameter("FeeType");//���������
			
			String tDeductType = request.getParameter("DeductType");//��ʼ����ѿ۳���ʽ
			String tFeeValue = request.getParameter("FeeValue");//��ʼ�����(Ԫ)/����
			String tMonthFeeType = request.getParameter("MonthFeeType");//�¶ȹ��������
			String tMonthValue = request.getParameter("MonthValue");//�¶ȹ����(Ԫ)/����
			String tYearFeeType = request.getParameter("YearFeeType");//��ȹ��������
			String tYearStartValue = request.getParameter("YearStartValue");//�����ʼֵ
			String tYearEndValue = request.getParameter("YearEndValue");//�����ֵֹ
			String tYearValue = request.getParameter("YearValue");//��ȹ����(Ԫ)/����
			
			LCGrpFeeSchema tLCGrpFeeSchema = new LCGrpFeeSchema();
			
			if ("INSERT".equals(tOperate) || "UPDATE".equals(tOperate) || "DELETE".equals(tOperate)) {
				
				tLCGrpFeeSchema.setGrpContNo(tBussNo);
				tLCGrpFeeSchema.setGrpPolNo(tBussNo);
				tLCGrpFeeSchema.setRiskCode(tRiskCode);
				tLCGrpFeeSchema.setInsuAccNo(tAccType);
				tLCGrpFeeSchema.setFeeCode(tFeeType);
					
				if ("0".equals(tFeeType)) {//���������:0-��ʼ�����

					tLCGrpFeeSchema.setFeeCalCode(tDeductType);
					tLCGrpFeeSchema.setFeeValue(tFeeValue);
				} else if ("1".equals(tFeeType)) {//���������:1-�¶ȹ����
					
					tLCGrpFeeSchema.setFeeCalCode(tMonthFeeType);
					tLCGrpFeeSchema.setFeeValue(tMonthValue);
				} else if ("2".equals(tFeeType)) {//���������:2-��ȹ����
					
					tLCGrpFeeSchema.setFeeCalCode(tYearFeeType);
					tLCGrpFeeSchema.setFeeValue(tYearValue);
					tLCGrpFeeSchema.setValStartDate(tYearStartValue);
					tLCGrpFeeSchema.setValEndDate(tYearEndValue);
				}
				
			}
			
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("MissionID", tMissionID);
			tTransferData.setNameAndValue("SubMissionID", tSubMissionID);
			tTransferData.setNameAndValue("ActivityID", tActivityID);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tTransferData);
			tVData.add(tLCGrpFeeSchema);
		
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LCGrpParamUI")) {
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
