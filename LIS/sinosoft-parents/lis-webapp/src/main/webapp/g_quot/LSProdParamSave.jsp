<%
/***************************************************************
 * <p>ProName��LSProdParamSave.jsp</p>
 * <p>Title����Ʒ������Ϣά��--�����ά��</p>
 * <p>Description����Ʒ������Ϣά��--�����ά��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-04-21
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.lis.schema.LWMissionSchema"%>
<%@page import="com.sinosoft.lis.schema.LSQuotManageFeeSchema"%>
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
			String tQuotNo = request.getParameter("QuotNo");//ѯ�ۺ�
			String tQuotBatNo = request.getParameter("QuotBatNo");//���κ�
			String tObjType = request.getParameter("ObjType");//
			
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
			
			LSQuotManageFeeSchema tLSQuotManageFeeSchema = new LSQuotManageFeeSchema();
			
			if ("INSERT".equals(tOperate) || "UPDATE".equals(tOperate) ) {
				
				tLSQuotManageFeeSchema.setSerialNo(tSerialNo);
				tLSQuotManageFeeSchema.setQuotNo(tQuotNo);
				tLSQuotManageFeeSchema.setQuotBatNo(tQuotBatNo);
				tLSQuotManageFeeSchema.setRiskCode(tRiskCode);
				tLSQuotManageFeeSchema.setAccType(tAccType);
				tLSQuotManageFeeSchema.setFeeType(tFeeType);
					
				if ("0".equals(tFeeType)) {//���������:0-��ʼ�����

					tLSQuotManageFeeSchema.setDeductType(tDeductType);
					tLSQuotManageFeeSchema.setFeeValue(tFeeValue);
				} else if ("1".equals(tFeeType)) {//���������:1-�¶ȹ����
					
					tLSQuotManageFeeSchema.setDeductType(tMonthFeeType);
					tLSQuotManageFeeSchema.setFeeValue(tMonthValue);
				} else if ("2".equals(tFeeType)) {//���������:2-��ȹ����
					
					tLSQuotManageFeeSchema.setDeductType(tYearFeeType);
					tLSQuotManageFeeSchema.setFeeValue(tYearValue);
					tLSQuotManageFeeSchema.setValStartDate(tYearStartValue);
					tLSQuotManageFeeSchema.setValEndDate(tYearEndValue);
				}
				
			} else if ("DELETE".equals(tOperate)) {
				
				tLSQuotManageFeeSchema.setSerialNo(tSerialNo);
			}
			
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("ObjType", tObjType);
			tTransferData.setNameAndValue("QuotNo", tQuotNo);
			tTransferData.setNameAndValue("QuotBatNo", tQuotBatNo);
			tTransferData.setNameAndValue("MissionID", tMissionID);
			tTransferData.setNameAndValue("SubMissionID", tSubMissionID);
			tTransferData.setNameAndValue("ActivityID", tActivityID);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tTransferData);
			tVData.add(tLSQuotManageFeeSchema);
		
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LSProdParamUI")) {
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
