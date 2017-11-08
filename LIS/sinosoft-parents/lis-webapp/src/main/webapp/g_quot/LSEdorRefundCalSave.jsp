<%
/***************************************************************
 * <p>ProName��LSEdorRefundCalSave.jsp</p>
 * <p>Title����ȫ�˷��㷨ά��</p>
 * <p>Description����ȫ�˷��㷨ά��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-04-23
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.lis.schema.LWMissionSchema"%>
<%@page import="com.sinosoft.lis.schema.LSQuotGetCalModeSchema"%>
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
			String tRiskCode = request.getParameter("RiskCode2");//����
			String tGetType = request.getParameter("GetType2");//�˷�����
			String tValPeriod = request.getParameter("ValPeriod");//��λ
			String tValStartDate = request.getParameter("ValStartDate");//��ʼֵ
			String tValEndDate = request.getParameter("ValEndDate");//��ֵֹ
			String tFeeValue = request.getParameter("FeeValue");//���ñ���

			LSQuotGetCalModeSchema tLSQuotGetCalModeSchema = new LSQuotGetCalModeSchema();
			if ("INSERT".equals(tOperate) || "UPDATE".equals(tOperate) ) {
				
				tLSQuotGetCalModeSchema.setSerialNo(tSerialNo);
				tLSQuotGetCalModeSchema.setQuotNo(tQuotNo);
				tLSQuotGetCalModeSchema.setQuotBatNo(tQuotBatNo);
				tLSQuotGetCalModeSchema.setRiskCode(tRiskCode);
				tLSQuotGetCalModeSchema.setGetType(tGetType);
				tLSQuotGetCalModeSchema.setValPeriod(tValPeriod);
				tLSQuotGetCalModeSchema.setValStartDate(tValStartDate);
				tLSQuotGetCalModeSchema.setValEndDate(tValEndDate);
				tLSQuotGetCalModeSchema.setFeeValue(tFeeValue);
				
			} else if ("DELETE".equals(tOperate)) {
				
				tLSQuotGetCalModeSchema.setSerialNo(tSerialNo);
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
			tVData.add(tLSQuotGetCalModeSchema);
		
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LSEdorRefundCalUI")) {
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
