<%
//程序名称：CreatBankCode.jsp
//程序功能：
//创建日期：2007-01-11
//创建人  ：Lujun
//更新记录：  更新人    更新日期     更新原因/内容
//
%>
	<%@include file="../common/jsp/UsrCheck.jsp"%>
	<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
	<!--用户校验类-->
  	<%@page import="com.sinosoft.utility.*"%>
  	<%@page import="com.sinosoft.lis.tbgrp.*"%>
  	<%@page import="com.sinosoft.lis.pubfun.*"%>
  	  <%@page import="com.sinosoft.service.*" %>
<%
	//输出参数
	String FlagStr = "";
	String Content = "";
	String BankCode="";
	VData tVData = new VData();
	TransferData tTransferData = new TransferData();
  	
	String ManageCom = request.getParameter("ManageCom");
	String BankName = request.getParameter("BankCodeName");
	String BankType = request.getParameter("BankType");
	loggerDebug("CreatBankCode","银行名称============"+ BankName);
	 String busiName="tbgrpCreatBankCodeUI";
	   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	//CreatBankCodeUI tCreatBankCodeUI = new CreatBankCodeUI();
	
	tTransferData.setNameAndValue("ManageCom", ManageCom);
	tTransferData.setNameAndValue("BankName", BankName);
	tTransferData.setNameAndValue("BankType", BankType);
	loggerDebug("CreatBankCode","管理机构============"+ ManageCom);
	loggerDebug("CreatBankCode","银行类型============"+ BankType);
	
	tVData.add(tTransferData);
	
	if(!tBusinessDelegate.submitData(tVData,"",busiName))
	{
  		FlagStr = "Fail";
  		Content = "银行编码生成失败！";
	}
	else
	{
		tVData.clear();
  		tVData = tBusinessDelegate.getResult();

  		BankCode = (String)tVData.getObjectByObjectName("String",0);

  		if(BankCode==null)
  		{
  			FlagStr = "Fail";
  			Content = "银行编码生成失败！";
  		}
	}

	if (FlagStr=="")
	{
		FlagStr = "Succ";
	}

	if(FlagStr.equals("Fail"))
	{	
		%>
			<script language="javascript">
				parent.fraInterface.alert("银行编码生成失败！");
			</script>
		<%
	}
	else
	{
		%>
			<script language="javascript">
				parent.fraInterface.fm.all('BankCode').value = "<%=BankCode%>";
				parent.fraInterface.fm.all("Confirm").disabled=true;
			</script>
		<%
	}
%>
