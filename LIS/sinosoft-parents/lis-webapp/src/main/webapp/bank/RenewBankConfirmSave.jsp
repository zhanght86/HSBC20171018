<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ������ִ�ӡ����Ա�ս�
//�����ܣ�
//�������ڣ�2002-12-12
//������  ��lh
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.bank.*"%>
<%@page import="com.sinosoft.service.*"%>
<%
	  String Content = "";
	  String FlagStr = "";
		
//		TransferData tTransferData = new TransferData();
//		tTransferData.setNameAndValue("StartDate",request.getParameter("StartDate"));
		String tChk[] = request.getParameterValues("InpCodeGridChk");    //������ʽ="MulLine������+Chk"
		String tPrtNo [] = request.getParameterValues("CodeGrid2");  //�õ���2�е�����ֵ
		String tContNo [] = request.getParameterValues("CodeGrid3");  //�õ���3�е�����ֵ
		String tAppntName [] = request.getParameterValues("CodeGrid4");  //�õ���4�е�����ֵ
		String tRiskCode [] = request.getParameterValues("CodeGrid5");  //�õ���5�е�����ֵ
		String tPaytoDate [] = request.getParameterValues("CodeGrid6");  //�õ���6�е�����ֵ
		String tBankAccNo [] = request.getParameterValues("CodeGrid7");  //�õ���7�е�����ֵ
		String tGetNoticeNo [] = request.getParameterValues("CodeGrid8");  //�õ���8�е�����ֵ
		String tManageCom [] = request.getParameterValues("CodeGrid9");  //�õ���9�е�����ֵ
		String tAgentCom [] = request.getParameterValues("CodeGrid10");  		//�õ���10�е�����ֵ
		
		int count = tChk.length; //�õ����ܵ��ļ�¼��
		LYRenewBankInfoSet tLYRenewBankInfoSet = new LYRenewBankInfoSet();
		for(int index = 0; index < count; index++)
		{
			if(tChk[index].equals("1"))
			{
				//ѡ�е���
				LYRenewBankInfoSchema tLYRenewBankInfoSchema   = new LYRenewBankInfoSchema();
				tLYRenewBankInfoSchema.setGetNoticeNo(tGetNoticeNo[index]);
				tLYRenewBankInfoSchema.setPrtNo(tPrtNo[index]);
				tLYRenewBankInfoSchema.setContNo(tContNo[index]);
				tLYRenewBankInfoSchema.setAppntName(tAppntName[index]);
				tLYRenewBankInfoSchema.setRiskCode(tRiskCode[index]);
				tLYRenewBankInfoSchema.setPaytoDate(tPaytoDate[index]);
				tLYRenewBankInfoSchema.setBankAccNo(tBankAccNo[index]);
				tLYRenewBankInfoSchema.setManageCom(tManageCom[index]);
				tLYRenewBankInfoSchema.setAgentCom(tAgentCom[index]);
				tLYRenewBankInfoSet.add(tLYRenewBankInfoSchema);
			}
		}
		
		loggerDebug("RenewBankConfirmSave","��" + tLYRenewBankInfoSet.size() + "������");
		GlobalInput tG1 = (GlobalInput)session.getValue("GI");
		VData tVData = new VData();
		tVData.addElement(tG1);
		tVData.addElement(tLYRenewBankInfoSet);
		//RenewBankManageUI tRenewBankManageUI = new RenewBankManageUI();
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		//if(!tRenewBankManageUI.submitData(tVData,"Confirm"))
		if(!tBusinessDelegate.submitData(tVData,"Confirm","RenewBankManageUI"))
		{
				FlagStr = "Fail";
				Content = "����ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
		}
	  else 
	  {
		  	FlagStr = "Success";
		    Content = "������ɣ� ";
	  }	
%>
<html>
<script language="javascript">
		parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
</script>
</html>
