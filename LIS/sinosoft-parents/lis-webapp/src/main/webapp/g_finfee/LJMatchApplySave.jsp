<%
/***************************************************************
 * <p>ProName��LJMatchApplySave.jsp</p>
 * <p>Title����������</p>
 * <p>Description����������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ������
 * @version  : 8.0
 * @date     : 2014-03-14
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LJPremMatchFeeSchema"%>
<%@page import="com.sinosoft.lis.schema.LJPremMatchPaySchema"%>
<%@page import="com.sinosoft.lis.vschema.LJPremMatchFeeSet"%>
<%@page import="com.sinosoft.lis.vschema.LJPremMatchPaySet"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	String tQuotNo = "";
	String tQuotBatNo = "";
	String tOperate = "";
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "ҳ��ʧЧ,�����µ�½";
	} else {
		
		try {
			
			VData tVData = new VData();
			tVData.add(tGI);
			
			tOperate = request.getParameter("Operate");
			if ("PREMMATCH".equals(tOperate)) {
				
				LJPremMatchFeeSet tLJPremMatchFeeSet = new LJPremMatchFeeSet();
				
				String tMatchFeeChk[] = request.getParameterValues("InpChoosedDataGridChk");//��ȡ��ƥ����Ϣ
				if (tMatchFeeChk!=null && tMatchFeeChk.length>0) {
					
					String tPremSource[] = request.getParameterValues("ChoosedDataGrid1");//������Դ
					String tSourceNo[] = request.getParameterValues("ChoosedDataGrid3");//��Դ����
					String tGrpContNo[] = request.getParameterValues("ChoosedDataGrid4");//���屣����
					String tBussType[] = request.getParameterValues("ChoosedDataGrid5");//ҵ�����ͱ���
					String tBussNo[] = request.getParameterValues("ChoosedDataGrid7");//ҵ�����
					String tCustBankCode[] = request.getParameterValues("ChoosedDataGrid8");//�ͻ������б���
					String tCustBankAccNo[] = request.getParameterValues("ChoosedDataGrid10");//�ͻ��˺�
					String tCustAccName[] = request.getParameterValues("ChoosedDataGrid11");//�ͻ��˻���
					String tAppntNo[] = request.getParameterValues("ChoosedDataGrid12");//Ͷ����λ����
					String tGrpName[] = request.getParameterValues("ChoosedDataGrid13");//Ͷ����λ����
					String tInBankCode[] = request.getParameterValues("ChoosedDataGrid14");//�շ����б���
					String tInBankAccNo[] = request.getParameterValues("ChoosedDataGrid16");//�շ��˺�
					String tEnterAccDate[] = request.getParameterValues("ChoosedDataGrid17");//��������
					String tMoney[] = request.getParameterValues("ChoosedDataGrid18");//���˽��
					String tCurOutPayMoney[] = request.getParameterValues("ChoosedDataGrid19");//��ɽ��
					
					for (int i=0; i<tMatchFeeChk.length; i++) {
						
						if ("1".equals(tMatchFeeChk[i])) {
						
							LJPremMatchFeeSchema tLJPremMatchFeeSchema = new LJPremMatchFeeSchema();
							tLJPremMatchFeeSchema.setDataSource(tPremSource[i]);
							tLJPremMatchFeeSchema.setSourceNo(tSourceNo[i]);
							tLJPremMatchFeeSchema.setGrpContNo(tGrpContNo[i]);
							tLJPremMatchFeeSchema.setBussType(tBussType[i]);
							tLJPremMatchFeeSchema.setBussNo(tBussNo[i]);
							tLJPremMatchFeeSchema.setCustBankCode(tCustBankCode[i]);
							tLJPremMatchFeeSchema.setCustBankAccNo(tCustBankAccNo[i]);
							tLJPremMatchFeeSchema.setCustAccName(tCustAccName[i]);
							tLJPremMatchFeeSchema.setAppntNo(tAppntNo[i]);
							tLJPremMatchFeeSchema.setGrpName(tGrpName[i]);
							tLJPremMatchFeeSchema.setInBankCode(tInBankCode[i]);
							tLJPremMatchFeeSchema.setInBankAccNo(tInBankAccNo[i]);
							tLJPremMatchFeeSchema.setEnterAccDate(tEnterAccDate[i]);
							tLJPremMatchFeeSchema.setMoney(tMoney[i]);
							tLJPremMatchFeeSchema.setCurOutPayMoney(tCurOutPayMoney[i]);
							
							tLJPremMatchFeeSet.add(tLJPremMatchFeeSchema);
						}
					}
					
					tVData.add(tLJPremMatchFeeSet);
				}
				
				LJPremMatchPaySet tLJPremMatchPaySet = new LJPremMatchPaySet();
				String tBussChk[] = request.getParameterValues("InpBusinessDataGridChk");//��ȡ��ƥ����Ϣ
				if (tBussChk!=null && tBussChk.length>0) {
				
					String tMainBussNo[] = request.getParameterValues("BusinessDataGrid1");//������(��ҵ�����)
					String tBussType[] = request.getParameterValues("BusinessDataGrid2");//ҵ�����ͱ���
					String tSubBussNo[] = request.getParameterValues("BusinessDataGrid4");//ҵ�����(��ҵ�����)
					String tAppntNo[] = request.getParameterValues("BusinessDataGrid5");//Ͷ����λ����
					String tGrpName[] = request.getParameterValues("BusinessDataGrid6");//Ͷ��������
					String tAgentName[] = request.getParameterValues("BusinessDataGrid7");//Ͷ��������
					String tBussDate[] = request.getParameterValues("BusinessDataGrid8");//ҵ������
					String tDuePayMoney[] = request.getParameterValues("BusinessDataGrid9");//Ӧ�ս��
					String tCurOutPayMoney[] = request.getParameterValues("BusinessDataGrid10");//��ɽ��
					String tInsurancecom[] = request.getParameterValues("BusinessDataGrid11");//������˾����
					String tInsurancecomName[] = request.getParameterValues("BusinessDataGrid12");//������˾
				
					for (int i=0; i<tBussChk.length; i++) {
					
						if ("1".equals(tBussChk[i])) {
							
							LJPremMatchPaySchema tLJPremMatchPaySchema = new LJPremMatchPaySchema();
							tLJPremMatchPaySchema.setMainBussNo(tMainBussNo[i]);
							tLJPremMatchPaySchema.setBussType(tBussType[i]);
							tLJPremMatchPaySchema.setSubBussNo(tSubBussNo[i]);
							tLJPremMatchPaySchema.setAppntNo(tAppntNo[i]);
							tLJPremMatchPaySchema.setGrpName(tGrpName[i]);
							tLJPremMatchPaySchema.setBussDate(tBussDate[i]);
							tLJPremMatchPaySchema.setDuePayMoney(tDuePayMoney[i]);
							tLJPremMatchPaySchema.setCurOutPayMoney(tCurOutPayMoney[i]);
							tLJPremMatchPaySchema.setInsurancecom(tInsurancecom[i]);
							tLJPremMatchPaySchema.setInsurancecom(tInsurancecom[i]);
							tLJPremMatchPaySchema.setInsurancecomName(tInsurancecomName[i]);
							tLJPremMatchPaySchema.setModifyTime(tAgentName[i]);//���ø��ֶδ洢�н��������
							
							tLJPremMatchPaySet.add(tLJPremMatchPaySchema);
						}
					}
				}
				
				tVData.add(tLJPremMatchPaySet);
			} else if ("APPLYCONFIRM".equals(tOperate)) {
				
				TransferData tTransferData = new TransferData();
				String tMatchSerialNo = request.getParameter("MatchSerialNo");
				tTransferData.setNameAndValue("MatchSerialNo", tMatchSerialNo);
				
				tVData.add(tTransferData);
			} else if ("CANCELCLICK".equals(tOperate)) {
				
				TransferData tTransferData = new TransferData();
				String tMatchSerialNo = request.getParameter("MatchSerialNo");
				tTransferData.setNameAndValue("MatchSerialNo", tMatchSerialNo);
				
				tVData.add(tTransferData);
			}
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LJMatchApplyUI")) {
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
