<%
/***************************************************************
 * <p>ProName��LJGetManualAppSubSave.jsp</p>
 * <p>Title: �ֶ�����������ϸ</p>
 * <p>Description���ֶ�����������ϸ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ������
 * @version  : 8.0
 * @date     : 2014-06-10
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LJGetManualSubSchema"%>
<%@page import="com.sinosoft.lis.vschema.LJGetManualSubSet"%>
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
			String tApplyBatNo = request.getParameter("ApplyBatchNo");
			String tAppCom = request.getParameter("AppCom");
			LJGetManualSubSet tLJGetManualSubSet = new LJGetManualSubSet();
			
			VData tVData = new VData();
			tVData.add(tGI);
			if ("CHOOSECLICK".equals(tOperate)) {
			
				String tGetDealType = request.getParameter("GetDealType");
				TransferData tTransferData = new TransferData();
				tTransferData.setNameAndValue("GetDealType", tGetDealType);
				tVData.add(tTransferData);
				
				String tChk[] = request.getParameterValues("InpGetInfoGridChk");//��ȡ��ƥ����Ϣ
				if (tChk!=null && tChk.length>0) {
					
					String tPayCode[] = request.getParameterValues("GetInfoGrid1");//���Ѻ�
					String tMainBussNo[] = request.getParameterValues("GetInfoGrid2");//���屣����
					String tBussType[] = request.getParameterValues("GetInfoGrid5");//ҵ������
					String tBussNo[] = request.getParameterValues("GetInfoGrid7");//ҵ���
					String tCustomerNo[] = request.getParameterValues("GetInfoGrid3");//Ͷ���˺���
					String tMoney[] = request.getParameterValues("GetInfoGrid8");//���
					String tShouldDate[] = request.getParameterValues("GetInfoGrid9");//Ӧ������
					String tCustBankCode[] = request.getParameterValues("GetInfoGrid12");//�ͻ�������
					String tBankCode[] = request.getParameterValues("GetInfoGrid14");//���б���
					String tCustBankProvince[] = request.getParameterValues("GetInfoGrid15");//����ʡ
					String tCustBankCity[] = request.getParameterValues("GetInfoGrid17");//�ͻ���
					String tCustBankAccNo[] = request.getParameterValues("GetInfoGrid19");//�˺�
					String tCustAccName[] = request.getParameterValues("GetInfoGrid20");//�˻�
					String tInsuranceCom[] = request.getParameterValues("GetInfoGrid10");//������˾
					String tGetObj[] = request.getParameterValues("GetInfoGrid21");//������˾

					for (int i=0; i<tChk.length; i++) {
						
						if ("1".equals(tChk[i])) {
						
							LJGetManualSubSchema tLJGetManualSubSchema = new LJGetManualSubSchema();
							tLJGetManualSubSchema.setApplyBatNo(tApplyBatNo);
							tLJGetManualSubSchema.setPayCode(tPayCode[i]);
							tLJGetManualSubSchema.setMainBussNo(tMainBussNo[i]);
							tLJGetManualSubSchema.setBussType(tBussType[i]);
							tLJGetManualSubSchema.setBussNo(tBussNo[i]);
							tLJGetManualSubSchema.setCustomerNo(tCustomerNo[i]);
							tLJGetManualSubSchema.setMoney(tMoney[i]);
							tLJGetManualSubSchema.setShouldDate(tShouldDate[i]);
							tLJGetManualSubSchema.setCustBankCode(tCustBankCode[i]);
							tLJGetManualSubSchema.setBankCode(tBankCode[i]);
							tLJGetManualSubSchema.setCustBankProvince(tCustBankProvince[i]);
							tLJGetManualSubSchema.setCustBankCity(tCustBankCity[i]);
							tLJGetManualSubSchema.setCustBankAccNo(tCustBankAccNo[i]);
							tLJGetManualSubSchema.setCustAccName(tCustAccName[i]);
							tLJGetManualSubSchema.setInsuranceCom(tInsuranceCom[i]);
							tLJGetManualSubSchema.setBussCom(tAppCom);
							tLJGetManualSubSchema.setModifyTime(tGetObj[i]);//���ø��ֶδ��ݶԹ�/��˽��ʶ
							
							tLJGetManualSubSet.add(tLJGetManualSubSchema);
						}
					}
				}
				tVData.add(tLJGetManualSubSet);
			} else if ("CANCELCLICK".equals(tOperate) || "MODIFYBANK".equals(tOperate)) {
				
				
				String tChk[] = request.getParameterValues("InpApplyGetInfoGridChk");//��ȡ��ƥ����Ϣ
				if (tChk!=null && tChk.length>0) {
					
					String tTransNo[] = request.getParameterValues("ApplyGetInfoGrid1");//���̺�
					String tGetDealType[] = request.getParameterValues("ApplyGetInfoGrid3");//add by songsz ����ת������ݴ���
					
					for (int i=0; i<tChk.length; i++) {
						
						if ("1".equals(tChk[i])) {
						
							LJGetManualSubSchema tLJGetManualSubSchema = new LJGetManualSubSchema();
							tLJGetManualSubSchema.setTransNo(tTransNo[i]);
							tLJGetManualSubSchema.setGetDealType(tGetDealType[i]);
							
							tLJGetManualSubSet.add(tLJGetManualSubSchema);
						}
					}
				}
				
				tVData.add(tLJGetManualSubSet);
			} else if ("CONFIRM".equals(tOperate)){
			
				TransferData tTransferData = new TransferData();
				tTransferData.setNameAndValue("ApplyBatNo", tApplyBatNo);
				
				tVData.add(tTransferData);
			}

			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LJGetManualAppSubUI")) {
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
