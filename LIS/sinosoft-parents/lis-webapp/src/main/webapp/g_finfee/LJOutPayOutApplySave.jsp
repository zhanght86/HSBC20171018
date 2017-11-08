<%
/***************************************************************
 * <p>ProName��LJOutPayOutApplySave.jsp</p>
 * <p>Title������˷�����</p>
 * <p>Description������˷�����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ������
 * @version  : 8.0
 * @date     : 2014-06-20
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LJRefundConfirmSchema"%>
<%@page import="com.sinosoft.lis.schema.LJRefundConfirmSubSchema"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tOperate = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	String cTempFeeNo = "";
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "ҳ��ʧЧ,�����µ�½";
	} else {
		
		try {
			
			tOperate = request.getParameter("Operate");
			
			VData tVData = new VData();
			tVData.add(tGI);
			
			if ("APPLYCLICK".equals(tOperate)) {
				
				LJRefundConfirmSchema tLJRefundConfirmSchema = new LJRefundConfirmSchema();
				LJRefundConfirmSubSchema tLJRefundConfirmSubSchema = new LJRefundConfirmSubSchema();
				
				String tOutPaySel[] = request.getParameterValues("InpOutPayInfoGridSel");//��ȡ��ƥ����Ϣ
				if (tOutPaySel!=null && tOutPaySel.length>0) {
					
					String tBussCom[] = request.getParameterValues("OutPayInfoGrid1");//BussCom ҵ�����
					String tBussNo[] = request.getParameterValues("OutPayInfoGrid3");//BussNo ҵ���(������)
					String tCustomerNo[] = request.getParameterValues("OutPayInfoGrid4");//����ͻ��� GrpName
					String tGrpName[] = request.getParameterValues("OutPayInfoGrid5");//����ͻ��� GrpName
					String tMoney[] = request.getParameterValues("OutPayInfoGrid6");//��� RefundMoney
					String tHeadBankCode[] = request.getParameterValues("OutPayInfoGrid7");//�ͻ������� ����
					String tProvince[] = request.getParameterValues("OutPayInfoGrid9");//�ͻ�������ʡ
					String tCity[] = request.getParameterValues("OutPayInfoGrid11");//�ͻ���������
					String tBankCode[] = request.getParameterValues("OutPayInfoGrid13");//���б��� ldbank.bankcode
					String tBankAccNo[] = request.getParameterValues("OutPayInfoGrid14");//�ͻ������˺�
					String tAccName[] = request.getParameterValues("OutPayInfoGrid15");//�ͻ��˻���
					
					for (int i=0; i<tOutPaySel.length; i++) {
						
						if ("1".equals(tOutPaySel[i])) {
						
							tLJRefundConfirmSchema.setBussNo(tBussNo[i]);
							tLJRefundConfirmSchema.setCustomerNo(tCustomerNo[i]);
							tLJRefundConfirmSchema.setGrpName(tGrpName[i]);
							tLJRefundConfirmSchema.setBussCom(tBussCom[i]);
							
							tLJRefundConfirmSubSchema.setRefundMoney(tMoney[i]);
							tLJRefundConfirmSubSchema.setCustBankCode(tHeadBankCode[i]);
							tLJRefundConfirmSubSchema.setBankCode(tBankAccNo[i]);
							tLJRefundConfirmSubSchema.setCustBankProvince(tProvince[i]);
							tLJRefundConfirmSubSchema.setCustBankCity(tCity[i]);
							tLJRefundConfirmSubSchema.setCustBankAccNo(tBankAccNo[i]);
							tLJRefundConfirmSubSchema.setCustAccName(tAccName[i]);
							
							tVData.add(tLJRefundConfirmSchema);
							tVData.add(tLJRefundConfirmSubSchema);
						}
					}
				}
			} else {
				
				String tApplyBatNo = request.getParameter("ApplyBatNo");
			
				TransferData tTransferData = new TransferData();
				tTransferData.setNameAndValue("ApplyBatNo", tApplyBatNo);
				
				tVData.add(tTransferData);
			}

			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LJOutPayOutApplyUI")) {
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
