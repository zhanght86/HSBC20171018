<%
/***************************************************************
 * <p>ProName��EdorMFSave.jsp</p>
 * <p>Title�����շ��ñ��</p>
 * <p>Description�����շ��ñ��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-08-16
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.lis.schema.LPGrpFeeSchema"%>
<%@page import="com.sinosoft.lis.schema.LPGetCalModeSchema"%>
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
			String tGrpContNo = request.getParameter("GrpContNo");
			String tEdorType = request.getParameter("EdorType");
			String tEdorNo = request.getParameter("EdorNo");
			String tEdorAppNo = request.getParameter("EdorAppNo");
			
			String tSerialNo = request.getParameter("SerialNo");//��ˮ��
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
			
			String tRiskCode2 = request.getParameter("RiskCode2");//����
			String tGetType = request.getParameter("GetType2");//�˷�����
			String tValPeriod = request.getParameter("ValPeriod");//��λ
			String tValStartDate = request.getParameter("ValStartDate");//��ʼֵ
			String tValEndDate = request.getParameter("ValEndDate");//��ֵֹ
			String tFeeValues = request.getParameter("FeeValues");//���ñ���

			LPGetCalModeSchema tLPGetCalModeSchema = new LPGetCalModeSchema();
			LPGrpFeeSchema tLPGrpFeeSchema = new LPGrpFeeSchema();
			if ("INSERT1".equals(tOperate) || "UPDATE1".equals(tOperate) ) {
				
				tLPGetCalModeSchema.setSerialNo(tSerialNo);
				tLPGetCalModeSchema.setGrpContNo(tGrpContNo);
				tLPGetCalModeSchema.setEdorNo(tEdorNo);
				tLPGetCalModeSchema.setEdorType(tEdorType);
				tLPGetCalModeSchema.setRiskCode(tRiskCode2);
				tLPGetCalModeSchema.setGetType(tGetType);
				tLPGetCalModeSchema.setValPeriod(tValPeriod);
				tLPGetCalModeSchema.setValStartDate(tValStartDate);
				tLPGetCalModeSchema.setValEndDate(tValEndDate);
				tLPGetCalModeSchema.setFeeValue(tFeeValues);
				
			} else if ("DELETE1".equals(tOperate)) {
				tLPGetCalModeSchema.setEdorNo(tEdorNo);
				tLPGetCalModeSchema.setEdorType(tEdorType);
				tLPGetCalModeSchema.setSerialNo(tSerialNo);
			}else if ("INSERT".equals(tOperate) || "UPDATE".equals(tOperate) || "DELETE".equals(tOperate) ) {
				
				tLPGrpFeeSchema.setGrpContNo(tGrpContNo);
				tLPGrpFeeSchema.setGrpPolNo(tGrpContNo);
				tLPGrpFeeSchema.setEdorNo(tEdorNo);
				tLPGrpFeeSchema.setEdorType(tEdorType);
				tLPGrpFeeSchema.setRiskCode(tRiskCode);
				tLPGrpFeeSchema.setInsuAccNo(tAccType);
				tLPGrpFeeSchema.setFeeCode(tFeeType);
					
				if ("0".equals(tFeeType)) {//���������:0-��ʼ�����

					tLPGrpFeeSchema.setFeeCalCode(tDeductType);
					tLPGrpFeeSchema.setFeeValue(tFeeValue);
				} else if ("1".equals(tFeeType)) {//���������:1-�¶ȹ����
					
					tLPGrpFeeSchema.setFeeCalCode(tMonthFeeType);
					tLPGrpFeeSchema.setFeeValue(tMonthValue);
				} else if ("2".equals(tFeeType)) {//���������:2-��ȹ����
					
					tLPGrpFeeSchema.setFeeCalCode(tYearFeeType);
					tLPGrpFeeSchema.setFeeValue(tYearValue);
					tLPGrpFeeSchema.setValStartDate(tYearStartValue);
					tLPGrpFeeSchema.setValEndDate(tYearEndValue);
				}
			}
			
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("MissionID", tMissionID);
			tTransferData.setNameAndValue("SubMissionID", tSubMissionID);
			tTransferData.setNameAndValue("ActivityID", tActivityID);
			tTransferData.setNameAndValue("GrpContNo", tGrpContNo);
			tTransferData.setNameAndValue("EdorNo", tEdorNo);
			tTransferData.setNameAndValue("EdorType", tEdorType);
			tTransferData.setNameAndValue("EdorAppNo", tEdorAppNo);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tTransferData);
			tVData.add(tLPGrpFeeSchema);
			tVData.add(tLPGetCalModeSchema);
		
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "EdorMFUI")) {
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
