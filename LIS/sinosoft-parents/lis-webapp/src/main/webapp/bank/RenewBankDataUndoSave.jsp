<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：按险种打印操作员日结
//程序功能：
//创建日期：2002-12-12
//创建人  ：lh
//更新记录：  更新人    更新日期     更新原因/内容
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
		String tChk[] = request.getParameterValues("InpCodeGridChk");    //参数格式="MulLine对象名+Chk"
		String tPrtNo [] = request.getParameterValues("CodeGrid1");  //得到第1列的所有值
		String tContNo [] = request.getParameterValues("CodeGrid2");  //得到第2列的所有值
		String tAppntName [] = request.getParameterValues("CodeGrid3");  //得到第3列的所有值
		String tRiskCode [] = request.getParameterValues("CodeGrid4");  //得到第4列的所有值
		String tPaytoDate [] = request.getParameterValues("CodeGrid5");  //得到第5列的所有值
		String tGetNoticeNo [] = request.getParameterValues("CodeGrid6");  //得到第6列的所有值
		String tSerialNo [] = request.getParameterValues("CodeGrid7");  //得到第7列的所有值
		
		int count = tChk.length; //得到接受到的记录数
		LYRenewBankInfoSet tLYRenewBankInfoSet = new LYRenewBankInfoSet();
		for(int index = 0; index < count; index++)
		{
			if(tChk[index].equals("1"))
			{
				//选中的行
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
		
		loggerDebug("RenewBankDataUndoSave","共" + tLYRenewBankInfoSet.size() + "条数据");
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
				Content = "操作失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
		}
	  else 
	  {
		  	FlagStr = "Success";
		    Content = "操作完成！ ";
	  }	
%>
<html>
<script language="javascript">
		parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
</script>
</html>
