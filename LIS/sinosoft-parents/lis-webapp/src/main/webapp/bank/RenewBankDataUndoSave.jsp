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
		String tPrtNo [] = request.getParameterValues("CodeGrid1");  //�õ���1�е�����ֵ
		String tContNo [] = request.getParameterValues("CodeGrid2");  //�õ���2�е�����ֵ
		String tAppntName [] = request.getParameterValues("CodeGrid3");  //�õ���3�е�����ֵ
		String tRiskCode [] = request.getParameterValues("CodeGrid4");  //�õ���4�е�����ֵ
		String tPaytoDate [] = request.getParameterValues("CodeGrid5");  //�õ���5�е�����ֵ
		String tGetNoticeNo [] = request.getParameterValues("CodeGrid6");  //�õ���6�е�����ֵ
		String tSerialNo [] = request.getParameterValues("CodeGrid7");  //�õ���7�е�����ֵ
		
		int count = tChk.length; //�õ����ܵ��ļ�¼��
		LYRenewBankInfoSet tLYRenewBankInfoSet = new LYRenewBankInfoSet();
		for(int index = 0; index < count; index++)
		{
			if(tChk[index].equals("1"))
			{
				//ѡ�е���
				LYRenewBankInfoSchema tLYRenewBankInfoSchema   = new LYRenewBankInfoSchema();
				tLYRenewBankInfoSchema.setSerialNo(tSerialNo[index]);
				tLYRenewBankInfoSchema.setGetNoticeNo(tGetNoticeNo[index]);
				tLYRenewBankInfoSchema.setPrtNo(tPrtNo[index]);
				tLYRenewBankInfoSchema.setContNo(tContNo[index]);
				tLYRenewBankInfoSchema.setAppntName(tAppntName[index]);
				tLYRenewBankInfoSchema.setRiskCode(tRiskCode[index]);
				tLYRenewBankInfoSchema.setPaytoDate(tPaytoDate[index]);
				tLYRenewBankInfoSet.add(tLYRenewBankInfoSchema);
			}
		}
		
		loggerDebug("RenewBankDataUndoSave","��" + tLYRenewBankInfoSet.size() + "������");
		GlobalInput tG1 = (GlobalInput)session.getValue("GI");
		VData tVData = new VData();
		tVData.addElement(tG1);
		tVData.addElement(tLYRenewBankInfoSet);
		//RenewBankManageUI tRenewBankManageUI = new RenewBankManageUI();
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		//if(!tRenewBankManageUI.submitData(tVData,"Undo"))
		if(!tBusinessDelegate.submitData(tVData,"Undo","RenewBankManageUI"))
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
